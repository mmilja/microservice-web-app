package webapp.backend.server.rest;

/**
 * Greeting object definition.
 */
public final class Greeting {

    /**
     * Id of the object.
     */
    private final long id;

    /**
     * Object message.
     */
    private final String content;

    /**
     * Constructor.
     *
     * @param id of the object.
     * @param content of the object.
     */
    Greeting(final long id, final String content) {
        this.id = id;
        this.content = content;
    }

    /**
     * Getter for id field.
     *
     * @return object id
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for teh content field.
     *
     * @return object content.
     */
    public String getContent() {
        return content;
    }
}
