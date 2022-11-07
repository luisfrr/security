package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {
    Optional<Application> findByName(String name);
}
