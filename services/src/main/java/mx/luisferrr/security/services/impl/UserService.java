package mx.luisferrr.security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.luisferrr.security.business.domain.User;
import mx.luisferrr.security.persistence.repositories.IUserRepository;
import mx.luisferrr.security.services.iface.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService, IUserService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public List<User> findAllDynamic(User userParam) {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User userParam) {
        return null;
    }

}
