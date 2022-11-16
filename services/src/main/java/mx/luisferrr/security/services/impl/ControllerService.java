package mx.luisferrr.security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.luisferrr.security.business.domain.*;
import mx.luisferrr.security.business.enums.AccessLevel;
import mx.luisferrr.security.business.enums.AuditFlag;
import mx.luisferrr.security.persistence.repositories.IControllerRepository;
import mx.luisferrr.security.persistence.repositories.IPermissionRepository;
import mx.luisferrr.security.persistence.repositories.IRoleControllerRepository;
import mx.luisferrr.security.persistence.repositories.IUserControllerRepository;
import mx.luisferrr.security.services.iface.IControllerService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ControllerService implements IControllerService {

    private final IControllerRepository controllerRepository;
    private final IPermissionRepository permissionRepository;
    private final IRoleControllerRepository roleControllerRepository;
    private final IUserControllerRepository userControllerRepository;

    @Override
    public List<Controller> saveOrUpdate(List<Controller> controllers) {
        List<Controller> controllersToSave = new ArrayList<>();
        for (Controller controller : controllers) {

            Controller controllerTemp = controllerRepository.findByApplicationNameAndSecurityKey(controller.getApplication().getName(),
                    controller.getSecurityKey(), AuditFlag.ACTIVE);

            if(controllerTemp == null) {
                controllersToSave.add(controller);
            }
            else {

                Set<Permission> permissions = new HashSet<>();
                for (Permission permission : controller.getPermissions()) {

                    Permission permissionTemp = permissionRepository.findByControllerAndSecurityKey(controllerTemp,
                            permission.getSecurityKey(), AuditFlag.ACTIVE);

                    permission.setController(controllerTemp);
                    if(permissionTemp == null) {
                        permissions.add(permission);
                    }
                    else {
                        permissions.add(this.updatePermission(permissionTemp, permission));
                    }

                }

                controllerTemp.setPermissions(permissions);

                controllersToSave.add(this.updateControlador(controllerTemp, controller));
            }
        }

        return controllerRepository.saveAll(controllersToSave);
    }

    @Override
    public List<Controller> getControllersByUserAndApplication(User user, Application application, List<Role> userRolesAppList) {

        // Consultar los controladores habilitados de cada rol
        List<Controller> roleControllersEnabled = roleControllerRepository.findControllersByRolesAndAccessStatusAndApplication(userRolesAppList, AccessLevel.ENABLED, application);

        // Consultar los controladores bloqueados del usuario
        List<Controller> userControllersBlocked = userControllerRepository.findControllersByUsuarioAndAccessStatusAndApplication(user, AccessLevel.BLOKED, application);

        // Consultar los controladores habilitados del usuario
        List<Controller> userControllersEnabled = userControllerRepository.findControllersByUsuarioAndAccessStatusAndApplication(user, AccessLevel.ENABLED, application);

        // Los controladores por usuario tienen prioridad por lo que
        // se tiene que purgar los controladores habilitados del rol
        // con respecto a los bloqueados por usuario
        if(roleControllersEnabled.size() > 0) {
            for (Controller controllerBlocked : userControllersBlocked) {
                roleControllersEnabled.removeIf(controladorSec -> Objects.equals(controladorSec.getId(), controllerBlocked.getId()));
            }
        }

        // Una vez que se hayan purgado los controladores por rol,
        // simplemente se agregan los controladores habilitados por rol y
        // los controladores habilitados por usuario, en una única lista
        List<Controller> controllers = new ArrayList<>(roleControllersEnabled);

        for (Controller controladorSec : userControllersEnabled) {
            boolean exists = controllers.stream()
                    .anyMatch(x -> Objects.equals(x.getId(), controladorSec.getId()));
            // Si el controlador habilitado por usuario no existe aún en lista única
            // entonces se agrega.
            if(!exists)
                controllers.add(controladorSec);
        }

        return controllers;
    }

    private Permission updatePermission(Permission permissionFromDb, Permission permissionUpdate) {
        permissionFromDb.setName(permissionUpdate.getName());
        permissionFromDb.setDescription(permissionUpdate.getDescription());
        permissionFromDb.setPackgeName(permissionUpdate.getPackgeName());
        permissionFromDb.setClassName(permissionUpdate.getClassName());
        permissionFromDb.setMethod(permissionUpdate.getMethod());
        permissionFromDb.setEndpoint(permissionUpdate.getEndpoint());
        permissionFromDb.setApplication(permissionUpdate.getApplication());
        permissionFromDb.setPermissionType(permissionUpdate.getPermissionType());

        return permissionFromDb;
    }

    private Controller updateControlador(Controller controllerFromDb, Controller controllerUpdate) {
        controllerFromDb.setName(controllerUpdate.getName());
        controllerFromDb.setDescription(controllerUpdate.getDescription());
        controllerFromDb.setPackagePath(controllerUpdate.getPackagePath());
        controllerFromDb.setClassName(controllerUpdate.getClassName());
        controllerFromDb.setArea(controllerUpdate.getArea());
        controllerFromDb.setView(controllerUpdate.getView());
        controllerFromDb.setEndpoint(controllerUpdate.getEndpoint());
        controllerFromDb.setApplication(controllerUpdate.getApplication());
        controllerFromDb.setControllerType(controllerUpdate.getControllerType());

        return controllerFromDb;
    }

}
