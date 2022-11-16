package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.Application;
import mx.luisferrr.security.business.domain.Controller;
import mx.luisferrr.security.business.domain.Role;
import mx.luisferrr.security.business.domain.RoleController;
import mx.luisferrr.security.business.enums.AccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRoleControllerRepository extends JpaRepository<RoleController, Long>, JpaSpecificationExecutor<RoleController> {

    @Query("select distinct r.controller from RoleController r " +
            "where r.role in ?1 " +
            "and r.accessLevel = ?2 " +
            "and r.controller.application = ?3")
    List<Controller> findControllersByRolesAndAccessStatusAndApplication(List<Role> userRolesAppList, AccessLevel enabled, Application application);

}

