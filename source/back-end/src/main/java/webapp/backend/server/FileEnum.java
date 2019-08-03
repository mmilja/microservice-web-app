package webapp.backend.server;

/**
 * Enum for static files.
 */
public enum FileEnum {
    /**
     * Path to image1.
     */
    IMG1_PATH("/images/image1.jpeg"),

    /**
     * Path to image2.
     */
    IMG2_PATH("/images/image2.jpg");

    /**
     * Variable holding the enum value.
     */
    private String path;

    /**
     * Constructor.
     *
     * @param path to the image.
     */
    FileEnum(final String path) {
        this.path = path;
    }

    /**
     * Getter for the path field.
     *
     * @return the path value.
     */
    public String getPath() {
        return path;
    }
}
