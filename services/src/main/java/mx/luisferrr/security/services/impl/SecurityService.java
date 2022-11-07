package mx.luisferrr.security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.luisferrr.security.business.domain.Application;
import mx.luisferrr.security.business.domain.User;
import mx.luisferrr.security.services.iface.ISecurityService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService implements ISecurityService {

    @Override
    public Application saveAplicacionInfo(String applicationName, String description, String version, User user) {
        return null;
    }

    @Override
    public void updateControladoresAndPermisosByAplicacion(String scanPackage, String applicationName, User user) throws Exception {

    }

}
