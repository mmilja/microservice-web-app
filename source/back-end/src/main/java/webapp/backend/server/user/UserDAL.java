package webapp.backend.server.user;

import java.util.List;

/**
 * Repository interface that queries user objects.
 */
public interface UserDAL {

    /**
     * Saves the user object in the database.
     *
     * @param user object to save.
     * @return the saved user object.
     */
    User saveUser(User user);

    /**
     * Lists all inserted users.
     *
     * @return all inserted users.
     */
    List<User> getAllUser();

    /**
     * Lists pageSize number of users, by using a query.
     *
     * @param pageNumber number of the current page.
     * @param pageSize size of the page.
     * @return List of users that fits on the page.
     */
    List<User> getAllUserPaginated(int pageNumber, int pageSize);

    /**
     * Queries the database by the username string.
     *
     * @param firstName to match against in the database.
     * @return first successful match.
     */
    User findByUsername(String firstName);

    /**
     * Updates the user object in the database.
     *
     * @param user object with updated information.
     * @return the updated user object.
     */
    User updateUser(User user);

    /**
     * Deletes the user object from the database.
     *
     * @param user object to delete.
     */
    void deleteUser(User user);
}
