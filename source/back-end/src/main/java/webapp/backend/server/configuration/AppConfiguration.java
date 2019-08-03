package webapp.backend.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class used for java spring boot configuration.
 */
@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    /**
     * Cors configurer for the project.
     *
     * @param registry corsRegistry.
     */
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
    }
}
