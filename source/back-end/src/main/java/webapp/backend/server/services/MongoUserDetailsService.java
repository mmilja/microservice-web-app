package webapp.backend.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import webapp.backend.server.user.User;
import webapp.backend.server.user.UserDAL;

/**
 * User authentication from mongodb.
 */
@Component
public class MongoUserDetailsService implements UserDetailsService {

    /**
     * Userobject data access layer.
     */
    @Autowired
    private UserDAL userDAL;

    /**
     * Get user by username.
     *
     * @param username of the user.
     * @return the user with the appropriate authority.
     * @throws UsernameNotFoundException if the user does not exist.
     */
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        User user = userDAL.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getAuthorityList());
    }
}
