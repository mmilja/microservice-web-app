package webapp.backend.server.rest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreetingControllerTest {

    @Test
    public void testGreetingController() {
        GreetingController greetingController = new GreetingController();
        Greeting result = greetingController.greeting("world");
        assertEquals(result.getContent(),"Hello, world");

    }
}
