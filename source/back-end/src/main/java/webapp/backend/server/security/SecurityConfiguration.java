package webapp.backend.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import webapp.backend.server.services.MongoUserDetailsService;

/**
 * Security configuration for the API.
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Mongo user authentication object.
     */
    @Autowired
    private MongoUserDetailsService userDetailsService;

    /**
     * Setter for userDetailsService field.
     *
     * @return userDetailsService object.
     */
    public MongoUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    /**
     * Override default security properties.
     * Enable authentication; disable session management and CSRF protection.
     *
     * @param http object on which the configuration happens.
     * @throws Exception in case of an error.
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().disable();
    }

    /**
     * Creates a password encoder using BCrypt.
     *
     * @return a BCryptPasswordEncoder object.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Specifying which object to use for authentication.
     *
     * @param builder object to use for authentication
     * @throws Exception in case of an error.
     */
    @Override
    public void configure(final AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(getUserDetailsService()).passwordEncoder(passwordEncoder());
    }
}
