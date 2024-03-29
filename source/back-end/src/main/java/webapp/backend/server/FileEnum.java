package webapp.backend.server;

/**
 * Enum for static files.
 */
public enum FileEnum {
    /**
     * Path to image1.
     */
    IMG1_PATH("/images/image1.jpg"),

    /**
     * Path to image2.
     */
    IMG2_PATH("/images/image2.jpg"),

    /**
     * Path to image3.
     */
    IMG3_PATH("/images/image3.jpg"),

    /**
     * Path to image4.
     */
    IMG4_PATH("/images/image4.jpg"),

    /**
     * Path to image5.
     */
    IMG5_PATH("/images/image5.jpg");

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
