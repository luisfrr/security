package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.Application;
import mx.luisferrr.security.business.domain.Controller;
import mx.luisferrr.security.business.domain.User;
import mx.luisferrr.security.business.domain.UserController;
import mx.luisferrr.security.business.enums.AccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserControllerRepository extends JpaRepository<UserController, Long>, JpaSpecificationExecutor<UserController> {

    @Query("select distinct u.controller from UserController u " +
            "where u.user = ?1 " +
            "and u.accessLevel = ?2 " +
            "and u.controller.application = ?3")
    List<Controller> findControllersByUsuarioAndAccessStatusAndApplication(User user, AccessLevel bloked, Application application);

}
