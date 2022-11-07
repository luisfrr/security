package mx.luisferrr.security.services.iface;

import mx.luisferrr.security.business.domain.Application;

import java.util.List;
import java.util.Optional;

public interface IApplicationService {
    List<Application> findAllDynamic(Application applicationParam);
    List<Application> findAll();
    Optional<Application> findByName(String applicationName);
    Application save(Application applicationParam);
    Optional<Application> findFullByName(String applicationName);
}
