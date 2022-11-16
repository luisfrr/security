package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.Controller;
import mx.luisferrr.security.business.enums.AuditFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface IControllerRepository extends JpaRepository<Controller, Long>, JpaSpecificationExecutor<Controller> {

    @Query("select c from Controller c " +
            "where c.application.name = ?1 " +
            "and c.securityKey = ?2 " +
            "and c.auditFlag = ?3 ")
    Controller findByApplicationNameAndSecurityKey(String name, String securityKey, AuditFlag active);


}
