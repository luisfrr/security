package mx.luisferrr.security.application.configuration;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class InitPageRedirectConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("forward:/index.jsf");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
