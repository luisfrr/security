package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
}
