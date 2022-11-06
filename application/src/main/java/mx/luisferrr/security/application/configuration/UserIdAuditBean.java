package mx.luisferrr.security.application.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class UserIdAuditBean implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        SecurityContext securityContex = SecurityContextHolder.getContext();

        if(securityContex != null &&
                securityContex.getAuthentication() != null &&
                securityContex.getAuthentication().getPrincipal() != null) {

            Object principal = securityContex.getAuthentication().getPrincipal();

            if (principal.getClass().isAssignableFrom(Long.class))
                return Optional.of((Long) principal);
            else
                return Optional.empty();

        }

        return Optional.empty();
    }
}
