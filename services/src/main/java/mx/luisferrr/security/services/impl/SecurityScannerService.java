package mx.luisferrr.security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.luisferrr.security.business.domain.Application;
import mx.luisferrr.security.business.domain.Controller;
import mx.luisferrr.security.business.domain.User;
import mx.luisferrr.security.services.iface.ISecurityScannerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityScannerService implements ISecurityScannerService {

    @Override
    public List<Controller> getAllControladoresAndPermisos(String scanPackage, Application application, User user) {
        return null;
    }

}
