package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.Controller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IControllerRepository extends JpaRepository<Controller, Long>, JpaSpecificationExecutor<Controller> {
}
