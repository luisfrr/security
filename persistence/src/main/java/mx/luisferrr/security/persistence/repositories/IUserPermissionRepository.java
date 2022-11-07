package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserPermissionRepository extends JpaRepository<UserPermission, Long>, JpaSpecificationExecutor<UserPermission> {
}
