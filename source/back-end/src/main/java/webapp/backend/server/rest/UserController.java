package webapp.backend.server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import webapp.backend.server.user.User;
import webapp.backend.server.user.UserDAL;

import javax.validation.Valid;

/**
 * User controller API.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * User data access layer object.
     */
    @Autowired
    private UserDAL userDAL;

    /**
     * Get user by username.
     *
     * @param username of the user.
     * @return user with the matching username.
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User getUser(@PathVariable("username") final String username) {
        return userDAL.findByUsername(username);
    }

    /**
     * Insert new user object.
     *
     * @param user object to insert.
     * @return inserted user object.
     */
    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public User newUser(@Valid @RequestBody final User user) {
        return userDAL.saveUser(user);
    }

    /**
     * Update an existing user object.
     *
     * @param user object to update.
     * @return updated user object.
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public User updateUser(@Valid @RequestBody final User user) {
        return userDAL.updateUser(user);
    }

    /**
     * Delete the given user object.
     *
     * @param user object to delete.
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteUser(@Valid @RequestBody final User user) {
        userDAL.deleteUser(user);
    }
}
