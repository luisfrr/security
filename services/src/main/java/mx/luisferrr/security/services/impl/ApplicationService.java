package mx.luisferrr.security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.luisferrr.security.business.domain.Application;
import mx.luisferrr.security.persistence.repositories.IApplicationRepository;
import mx.luisferrr.security.services.iface.IApplicationService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationService implements IApplicationService {

    private final IApplicationRepository applicationRepository;

    @Override
    public List<Application> findAllDynamic(Application applicationParam) {
        return null;
    }

    @Override
    public List<Application> findAll() {
        List<Application> applications = applicationRepository.findAll();

        if(applications.size() > 0)
            return applications.stream()
                    .sorted(Comparator.comparing(Application::getName))
                    .collect(Collectors.toList());

        return applications;
    }

    @Override
    public Optional<Application> findByName(String applicationName) {
        return applicationRepository.findByName(applicationName);
    }

    @Override
    public Application save(Application applicationParam) {

        Application application = findByName(applicationParam.getName()).orElse(null);

        if(application == null) {
            application = new Application();
            application.setName(applicationParam.getName());
            application.setDescription(applicationParam.getDescription());
            application.setVersion(applicationParam.getVersion());
        } else {
            application.setName(applicationParam.getName());
            application.setDescription(applicationParam.getDescription());
            application.setVersion(applicationParam.getVersion());
        }

        return applicationRepository.save(application);
    }

    @Override
    public Optional<Application> findFullByName(String applicationName) {
        return Optional.empty();
    }
}
