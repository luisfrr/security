package mx.luisferrr.security.persistence.repositories;

import mx.luisferrr.security.business.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
