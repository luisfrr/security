package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.RoleController;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRoleControllerRepository extends JpaRepository<RoleController, Long>, JpaSpecificationExecutor<RoleController> {
}
