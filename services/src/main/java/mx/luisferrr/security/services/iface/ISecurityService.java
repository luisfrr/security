package mx.luisferrr.security.services.iface;

import mx.luisferrr.security.business.domain.Application;
import mx.luisferrr.security.business.domain.User;

public interface ISecurityService {
    Application saveAplicacionInfo(String applicationName, String description, String version, User user);
    void updateControladoresAndPermisosByAplicacion(String scanPackage, String applicationName, User user) throws Exception;
}
