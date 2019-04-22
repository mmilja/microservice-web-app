package webapp.backend.server.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository interface that queries Customer objects.
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    /**
     * Queries the database by the first name string.
     *
     * @param firstName to match against in the database.
     * @return first successful match.
     */
    Customer findByFirstName(String firstName);

    /**
     * Queries the database by the last name string.
     *
     * @param lastName to match against in the database.
     * @return the list of all object that have the same lastName property.
     */
    List<Customer> findByLastName(String lastName);

}
