package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRolePermissionRepository extends JpaRepository<RolePermission, Long>, JpaSpecificationExecutor<RolePermission> {
}
