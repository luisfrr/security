package mx.luisferrr.security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.luisferrr.security.business.annotations.*;
import mx.luisferrr.security.business.domain.Application;
import mx.luisferrr.security.business.domain.Controller;
import mx.luisferrr.security.business.domain.Permission;
import mx.luisferrr.security.business.domain.User;
import mx.luisferrr.security.business.enums.AuditFlag;
import mx.luisferrr.security.business.enums.ControllerType;
import mx.luisferrr.security.business.enums.PermissionType;
import mx.luisferrr.security.services.iface.ISecurityScannerService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityScannerService implements ISecurityScannerService {

    @Override
    public List<Controller> getAllControllersAndPermissions(String scanPackage, Application application, User user) {
        List<Controller> controllers = new ArrayList<>();

        ClassPathScanningCandidateComponentProvider controllerProvider = createControllerScanner();
        ClassPathScanningCandidateComponentProvider viewProvider = createViewScanner();

        Set<BeanDefinition> controllerBeanDefinition = controllerProvider.findCandidateComponents(scanPackage);
        for(BeanDefinition beanDefinition : controllerBeanDefinition) {
            Controller controller = this.getControllerInfo(beanDefinition, application, user);
            controllers.add(controller);
        }

        Set<BeanDefinition> viewBeanDefinition = viewProvider.findCandidateComponents(scanPackage);
        for(BeanDefinition beanDefinition : viewBeanDefinition) {
            Controller controller = this.getViewInfo(beanDefinition, application, user);
            controllers.add(controller);
        }

        return controllers;
    }

    private ClassPathScanningCandidateComponentProvider createControllerScanner() {
        ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(ControllerUmmSec.class));
        return provider;
    }

    private ClassPathScanningCandidateComponentProvider createViewScanner() {
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(BackBeanUmmSec.class));
        return provider;
    }

    private Controller getControllerInfo(BeanDefinition beanDefinition, Application application, User user) {
        Controller controller = null;
        String packagePath;
        String className = "";
        try {
            Class<?> cl = Class.forName(beanDefinition.getBeanClassName());
            packagePath = cl.getPackageName();
            className = cl.getSimpleName();

            ControllerUmmSec controllerUmmSec = cl.getAnnotation(ControllerUmmSec.class);

            String name = controllerUmmSec.displayName();
            String securityKey = controllerUmmSec.securityName();
            String description = controllerUmmSec.description();
            String area = controllerUmmSec.grupoArea();
            String view = "";
            String endpoint = controllerUmmSec.endpointBase();
            ControllerType controllerType = ControllerType.API;
            AuditFlag auditFlag = AuditFlag.ACTIVE;

            controller = convertToController(name, securityKey, description,
                    area, packagePath, className, view, endpoint, application, controllerType, auditFlag, user);

            controller.setPermissions(this.getPermissions(controller, cl, user));

        }
        catch (Exception e) {
            log.error("SecurityScannerService - getControllerInfo - " +
                    "Error al obtener los metadatos de la clase {" + className + "} " +
                    "de la configuración de seguridad. Error: " + e);
        }

        return controller;
    }

    private Controller getViewInfo(BeanDefinition beanDefinition, Application application, User user) {
        Controller controlador = null;
        String packagePath;
        String className = "";
        try {
            Class<?> cl = Class.forName(beanDefinition.getBeanClassName());
            packagePath = cl.getPackageName();
            className = cl.getSimpleName();

            BackBeanUmmSec backBeanUmmSec = cl.getAnnotation(BackBeanUmmSec.class);

            String name = backBeanUmmSec.displayName();
            String securityKey = backBeanUmmSec.securityName();
            String description = backBeanUmmSec.description();
            String area = backBeanUmmSec.departamento();
            String view = backBeanUmmSec.view();
            String endpoint = "";
            ControllerType controllerType = ControllerType.WEB;
            AuditFlag auditFlag = AuditFlag.ACTIVE;

            controlador = convertToController(name, securityKey, description,
                    area, packagePath, className, view, endpoint, application, controllerType, auditFlag, user);

            controlador.setPermissions(this.getPermissions(controlador, cl, user));

        }
        catch (Exception e) {
            String mensaje = "Error al obtener los metadatos de la clase {" + className +
                    "} de la configuración de seguridad. Error: " + e;
            log.error("SecurityScannerService - getViewInfo - " + mensaje);
        }

        return controlador;
    }

    private Set<Permission> getPermissions(Controller controller, Class<?> cl, User user) {
        Set<Permission> permissions = new HashSet<>();
        String className = "";
        String methodName = "";
        try {
            className = cl.getSimpleName();

            for(Method method : cl.getDeclaredMethods()) {

                methodName = method.getName();

                if(method.isAnnotationPresent(GetUmmSec.class)) {

                    GetUmmSec getUmmSec = method.getAnnotation(GetUmmSec.class);

                    Permission permission = convertToPermission(getUmmSec.displayName(),
                            getUmmSec.securityName(), getUmmSec.description(), controller.getPackagePath(),
                            controller.getClassName(), method.getName(), getUmmSec.endpoint(), controller,
                            controller.getApplication(), PermissionType.HTTP_GET,
                            AuditFlag.ACTIVE, user);

                    permissions.add(permission);
                }

                if(method.isAnnotationPresent(PostUmmSec.class)) {

                    PostUmmSec postUmmSec = method.getAnnotation(PostUmmSec.class);

                    Permission permission = convertToPermission(postUmmSec.displayName(),
                            postUmmSec.securityName(), postUmmSec.description(), controller.getPackagePath(),
                            controller.getClassName(), method.getName(), postUmmSec.endpoint(), controller,
                            controller.getApplication(), PermissionType.HTTP_POST,
                            AuditFlag.ACTIVE, user);

                    permissions.add(permission);
                }

                if(method.isAnnotationPresent(PutUmmSec.class)) {

                    PutUmmSec putUmmSec = method.getAnnotation(PutUmmSec.class);

                    Permission permission = convertToPermission(putUmmSec.displayName(),
                            putUmmSec.securityName(), putUmmSec.description(), controller.getPackagePath(),
                            controller.getClassName(), method.getName(), putUmmSec.endpoint(), controller,
                            controller.getApplication(), PermissionType.HTTP_PUT,
                            AuditFlag.ACTIVE, user);

                    permissions.add(permission);
                }

                if(method.isAnnotationPresent(DeleteUmmSec.class)) {

                    DeleteUmmSec deleteUmmSec = method.getAnnotation(DeleteUmmSec.class);

                    Permission permission = convertToPermission(deleteUmmSec.displayName(),
                            deleteUmmSec.securityName(), deleteUmmSec.description(), controller.getPackagePath(),
                            controller.getClassName(), method.getName(), deleteUmmSec.endpoint(), controller,
                            controller.getApplication(), PermissionType.HTTP_DELETE,
                            AuditFlag.ACTIVE, user);

                    permissions.add(permission);
                }

                if(method.isAnnotationPresent(ActionUmmSec.class)) {
                    ActionUmmSec actionUmmSec = method.getAnnotation(ActionUmmSec.class);

                    Permission permission = convertToPermission(actionUmmSec.displayName(),
                            actionUmmSec.securityName(), actionUmmSec.description(), controller.getPackagePath(),
                            controller.getClassName(), method.getName(), "", controller,
                            controller.getApplication(), PermissionType.WRITE,
                            AuditFlag.ACTIVE, user);

                    permissions.add(permission);
                }

                if(method.isAnnotationPresent(SectionArrayUmmSec.class)) {
                    SectionArrayUmmSec sectionArrayUmmSec = method.getAnnotation(SectionArrayUmmSec.class);

                    for(SectionUmmSec sectionUmmSec : sectionArrayUmmSec.value()) {
                        Permission permission = convertToPermission(sectionUmmSec.displayName(),
                                sectionUmmSec.securityName(), sectionUmmSec.description(), controller.getPackagePath(),
                                controller.getClassName(), method.getName(), "", controller,
                                controller.getApplication(), PermissionType.READ,
                                AuditFlag.ACTIVE, user);
                        permissions.add(permission);
                    }
                }

                if(method.isAnnotationPresent(SectionUmmSec.class)) {

                    SectionUmmSec sectionUmmSec = method.getAnnotation(SectionUmmSec.class);

                    Permission permission = convertToPermission(sectionUmmSec.displayName(),
                            sectionUmmSec.securityName(), sectionUmmSec.description(), controller.getPackagePath(),
                            controller.getClassName(), method.getName(), "", controller,
                            controller.getApplication(), PermissionType.READ,
                            AuditFlag.ACTIVE, user);

                    permissions.add(permission);
                }

            }

        } catch (Exception e) {
            String mensaje = "Error al obtener los metadatos de la clase {" + className + "} y el método {" + methodName + "} " +
                    "de la configuración de seguridad. Error: " + e;
            log.error("SecurityScannerService - getPermissions - " + mensaje);
        }

        return permissions;
    }


    private Controller convertToController(String name, String securityKey, String description,
                                           String area, String packagePath, String className,
                                           String view, String endpoint,
                                           Application application, ControllerType controllerType,
                                           AuditFlag auditFlag, User user) {

        return Controller.builder()
                .name(name)
                .securityKey(securityKey)
                .description(description)
                .area(area)
                .packagePath(packagePath)
                .className(className)
                .view(view)
                .endpoint(endpoint)
                .application(application)
                .controllerType(controllerType)
                .auditFlag(auditFlag)
                .build();
    }

    private Permission convertToPermission(String name, String securityKey, String description,
                                           String packagePath, String className,
                                           String method, String endpoint, Controller controller,
                                           Application application, PermissionType permissionType,
                                           AuditFlag auditFlag, User user) {

        return Permission.builder()
                .name(name)
                .securityKey(securityKey)
                .description(description)
                .packgeName(packagePath)
                .className(className)
                .method(method)
                .endpoint(endpoint)
                .controller(controller)
                .application(application)
                .permissionType(permissionType)
                .auditFlag(auditFlag)
                .build();
    }


}
