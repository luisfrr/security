package mx.luisferrr.security.services.iface;

import mx.luisferrr.security.business.domain.Application;
import mx.luisferrr.security.business.domain.User;

public interface ISecurityService {
    Application saveApplicationInfo(String applicationName, String description, String version, User user);
    void updateControllersAndPermissionByApplication(String scanPackage, String applicationName, User user) throws Exception;
}
