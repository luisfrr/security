package mx.luisferrr.security.services.iface;

import mx.luisferrr.security.business.domain.Application;
import mx.luisferrr.security.business.domain.Controller;
import mx.luisferrr.security.business.domain.Role;
import mx.luisferrr.security.business.domain.User;

import java.util.List;

public interface IControllerService {
    List<Controller> saveOrUpdate(List<Controller> controllers);
    List<Controller> getControllersByUserAndApplication(User user, Application application, List<Role> userRolesAppList);
}
