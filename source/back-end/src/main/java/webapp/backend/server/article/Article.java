package webapp.backend.server.article;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * Article object definition.
 */
public final class Article {

    /**
     * Id of the object.
     */
    @Id
    private ObjectId id;


    /**
     * Article title.
     */
    private String title;

    /**
     * Article body content.
     */
    private String content;

    /**
     * Article author.
     */
    private String author;

    /**
     * Category the article belongs to.
     */
    private String categories;

    /**
     * Article image.
     */
    private String image;

    /**
     * Time article was written.
     */
    private LocalDateTime time;

    /**
     * Constructor.
     *
     * @param time of the object.
     * @param image of the object.
     * @param content of the object.
     * @param categories of the object.
     * @param author of the object.
     * @param title of the object.
     */
    public Article(final LocalDateTime time,
            final String image,
            final String title,
            final String content,
            final String author,
            final String categories) {
        this.time = time;
        this.image = image;
        this.title = title;
        this.content = content;
        this.author = author;
        this.categories = categories;
    }

    /**
     * Getter for id field.
     *
     * @return object id value.
     */
    public String getId() {
        return id.toHexString();
    }

    /**
     * Getter for the image field.
     *
     * @return image field value.
     */
    public String getImage() {
        return image;
    }

    /**
     * Setter for image field.
     *
     * @param image value to set.
     */
    public void setImage(final String image) {
        this.image = image;
    }

    /**
     * Getter for title field.
     *
     * @return title field value.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the title field.
     *
     * @param title value to set.
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Getter for the content field.
     *
     * @return object content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for content field.
     *
     * @param content value to set.
     */
    public  void setContent(final String content) {
        this.content = content;
    }

    /**
     * Getter for the author field.
     *
     * @return author field value.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter for author field.
     *
     * @param author value to set.
     */
    public void setAuthor(final String author) {
        this.author = author;
    }

    /**
     * Getter for categories field.
     *
     * @return categories field value.
     */
    public String getCategories() {
        return categories;
    }

    /**
     * Setter for categories field.
     *
     * @param categories value to set.
     */
    public void setCategories(final String categories) {
        this.categories = categories;
    }

    /**
     * Getter for the time field.
     *
     * @return time field value.
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Setter for the time field.
     *
     * @param time value to set.
     */
    public void setTime(final LocalDateTime time) {
        this.time = time;
    }
}
