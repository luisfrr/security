package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
}
