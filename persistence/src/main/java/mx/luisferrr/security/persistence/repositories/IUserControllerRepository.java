package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.UserController;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserControllerRepository extends JpaRepository<UserController, Long>, JpaSpecificationExecutor<UserController> {
}
