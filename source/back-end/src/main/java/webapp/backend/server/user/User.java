package webapp.backend.server.user;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import webapp.backend.server.security.SecurityRoles;

import java.util.Arrays;
import java.util.List;

/**
 * Mongo object class definition.
 */
public final class User {

    /**
     * Id of the object.
     */
    @Id
    private ObjectId id;

    /**
     * User name.
     */
    private String username;

    /**
     * User password, assumed to be encrypted with BCrypt with 12 iterations.
     */
    private String password;

    /**
     * Additional information about the user. Shown if the user is the author of an Article.
     */
    private String userInfo;

    /**
     * List of autorities that the user posseses.
     */
    private List<SimpleGrantedAuthority> authorityList;

    /**
     * Empty public constructor.
     */
    public User() {

    }

    /**
     * Constructor with username and password argumments.
     *
     * @param username of the customer.
     * @param password of the customer.
     */
    public User(final String username, final String password) {
        this.username = username;
        this.password = password;
        this.authorityList = Arrays.asList(new SimpleGrantedAuthority(SecurityRoles.USER_ROLE));
    }

    /**
     * Constructor with username, password and user-about argumments.
     *
     * @param username of the user.
     * @param password of the user.
     * @param userInfo of the user.
     */
    public User(final String username, final String password, final String userInfo) {
        this.username = username;
        this.password = password;
        this.userInfo = userInfo;
        this.authorityList = Arrays.asList(new SimpleGrantedAuthority(SecurityRoles.USER_ROLE));
    }


    /**
     * Override toString for a pretty output.
     *
     * @return User full name and id.
     */
    @Override
    public String toString() {
        return String.format(
                "User=[id=%s, username='%s', password='%s']",
                id, username, password);
    }

    /**
     * Id field getter.
     *
     * @return get object id value.
     */
    public String getId() {
        return id.toHexString();
    }

    /**
     * Id field setter.
     *
     * @param id value to set.
     */
    public void setId(final ObjectId id) {
        this.id = id;
    }

    /**
     * Username field getter.
     *
     * @return get object firstName value.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Username field setter.
     *
     * @param username value to set.
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Password field getter.
     *
     * @return get object lastName value.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Password field setter.
     *
     * @param password value to set.
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * UserInfo filed getter.
     *
     * @return get object userInfo value.
     */
    public String getUserInfo() {
        return userInfo;
    }

    /**
     * UserInfo field setter.
     *
     * @param userInfo value to set.
     */
    public void setUserInfo(final String userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * AuthorityList field getter.
     *
     * @return the list of authorities connected with the user.
     */
    public List<SimpleGrantedAuthority> getAuthorityList() {
        return authorityList;
    }

    /**
     * AuthorityList field setter.
     *
     * @param authorityList value to be set.
     */
    public void setAuthorityList(final List<SimpleGrantedAuthority> authorityList) {
        this.authorityList = authorityList;
    }
}
