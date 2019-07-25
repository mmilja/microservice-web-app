package webapp.backend.server;

/**
 * Enum for static files.
 */
public enum FileEnum {
    /**
     * Path to the image.
     */
    IMG_PATH("/resources/images/image1.jpeg");

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
