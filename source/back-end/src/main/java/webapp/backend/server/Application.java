package webapp.backend.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import webapp.backend.server.mongo.Customer;
import webapp.backend.server.mongo.CustomerRepository;

/**
 * Spring main class.
 * Initializes all spring objects.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    /**
     * Create CustomerRepository object instance and connect it to mongodb.
     */
    @Autowired
    private CustomerRepository repository;

    /**
     * Entry function.
     *
     * @param args varargs passed from startup.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Executes some mongodb operations.
     *
     * @param args
     * @throws Exception
     */
    @Override
    public final void run(final String... args) throws Exception {

        repository.deleteAll();

        //save a couple of customers
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }
    }
}
