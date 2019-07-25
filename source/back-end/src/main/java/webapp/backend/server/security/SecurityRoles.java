package webapp.backend.server.security;

/**
 * Class containing security roles.
 */
public final class SecurityRoles {

    /**
     * Security role which has user rights.
     */
    public static final String USER_ROLE = "user";

    /**
     * Security role which has editor rights.
     */
    public static final String EDITOR_ROLE = "editor";

    /**
     * Security role which has administrative rights.
     */
    public static final String ADMIN_ROLE = "admin";

    /**
     * Private constructor to prevent instantiation of the class.
     */
    private SecurityRoles() {
    }
}
