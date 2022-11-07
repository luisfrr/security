package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {
}
