package mx.luisferrr.security.services.iface;

import mx.luisferrr.security.business.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAllDynamic(User userParam);
    Optional<User> findById(Long id);
    User save(User userParam);
}
