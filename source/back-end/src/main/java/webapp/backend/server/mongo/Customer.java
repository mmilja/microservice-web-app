package webapp.backend.server.mongo;

import org.springframework.data.annotation.Id;

/**
 * Mongo object class definition.
 */
public final class Customer {

    /**
     * Id of the object.
     */
    @Id
    private String id;

    /**
     * First name of the customer.
     */
    private String firstName;

    /**
     * Last name of the customer.
     */
    private String lastName;

    /**
     * Empty public constructor.
     */
    public Customer() {

    }

    /**
     * Constructor with first and last name argumments.
     *
     * @param firstName of the customer.
     * @param lastName of the customer.
     */
    public Customer(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Override toString for a pretty output.
     *
     * @return Customer full name and id.
     */
    @Override
    public String toString() {
        return String.format(
                "Customer=[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

    /**
     * Id field getter.
     *
     * @return get object id value.
     */
    public String getId() {
        return id;
    }

    /**
     * Id field setter.
     *
     * @param id value to set.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * FirstName field getter.
     *
     * @return get object firstName value.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * FirstName field setter.
     *
     * @param firstName value to set.
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * LastName field getter.
     *
     * @return get object lastName value.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * LastName field setter.
     *
     * @param lastName value to set.
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
}
