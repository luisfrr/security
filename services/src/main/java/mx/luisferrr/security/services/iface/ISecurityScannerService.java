package mx.luisferrr.security.services.iface;

import mx.luisferrr.security.business.domain.Application;
import mx.luisferrr.security.business.domain.Controller;
import mx.luisferrr.security.business.domain.User;

import java.util.List;

public interface ISecurityScannerService {
    List<Controller> getAllControladoresAndPermisos(String scanPackage, Application application, User user);
}
