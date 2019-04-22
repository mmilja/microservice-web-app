package webapp.backend.server.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * REST API controller class.
 */
@RestController
public class GreetingController {

    /**
     * Template of the answer string.
     */
    private static final String TEMPLATE = "Hello, %s";

    /**
     * Number of calls to the API.
     */
    private final AtomicLong counter = new AtomicLong();

    /**
     * REST API call on path greeting.
     *
     * @param name that is provided by the URI.
     * @return Greeting object with set fields.
     */
    @RequestMapping("/greeting")
    public final Greeting greeting(@RequestParam(value = "name", defaultValue = "World") final String name) {
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));

    }

}
