package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.ApplicationRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IApplicationRoleRepository extends JpaRepository<ApplicationRole, Long>, JpaSpecificationExecutor<ApplicationRole> {
}
