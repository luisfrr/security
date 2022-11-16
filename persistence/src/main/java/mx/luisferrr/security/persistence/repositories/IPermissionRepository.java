package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.Controller;
import mx.luisferrr.security.business.domain.Permission;
import mx.luisferrr.security.business.enums.AuditFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface IPermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    @Query("select p from Permission p " +
            "where p.controller = ?1 " +
            "and p.securityKey = ?2 " +
            "and p.auditFlag = ?3 ")
    Permission findByControllerAndSecurityKey(Controller controllerTemp, String securityKey, AuditFlag active);

}
