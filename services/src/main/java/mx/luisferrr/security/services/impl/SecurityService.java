package mx.luisferrr.security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.luisferrr.security.business.domain.Application;
import mx.luisferrr.security.business.domain.Controller;
import mx.luisferrr.security.business.domain.User;
import mx.luisferrr.security.services.iface.IApplicationService;
import mx.luisferrr.security.services.iface.IControllerService;
import mx.luisferrr.security.services.iface.ISecurityScannerService;
import mx.luisferrr.security.services.iface.ISecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService implements ISecurityService {

    private final IApplicationService applicationService;
    private final IControllerService controllerService;
    private final ISecurityScannerService securityScannerService;

    @Override
    public Application saveApplicationInfo(String applicationName, String description, String version, User user) {

        Application aplicacion = Application.builder()
                .name(applicationName)
                .description(description)
                .version(version)
                .build();

        return applicationService.save(aplicacion);
    }

    @Override
    public void updateControllersAndPermissionByApplication(String scanPackage, String applicationName, User user) throws Exception {
        try {
            Optional<Application> aplicacion = applicationService.findByName(applicationName);

            if(aplicacion.isEmpty())
                throw new Exception("¡No se encontró la aplicación! Debe agregar el nombre y la descripción de su aplicación en la tabla de aplicaciones.");

            List<Controller> controllers = securityScannerService.getAllControllersAndPermissions(scanPackage, aplicacion.get(), user);

            List<Controller> controladoresSaved = controllerService.saveOrUpdate(controllers);

            if(controladoresSaved.isEmpty())
                throw new Exception("No se ha logrado guardar la información de controladores y permisos");

        }
        catch (Exception e) {
            log.error("SecurityService - updateControladoresAndPermisosByAplicacion - Error: " + e);
            throw e;
        }
    }

}
