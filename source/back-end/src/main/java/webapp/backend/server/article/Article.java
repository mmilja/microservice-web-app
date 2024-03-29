package webapp.backend.server.article;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Article object definition.
 */
@Document(collection = "article")
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
    private String category;

    /**
     * Article image.
     */
    private String image;

    /**
     * Time article was written.
     */
    @Indexed(unique = true)
    private LocalDateTime time;

    /**
     * Constructor.
     *
     * @param time of the object.
     * @param image of the object.
     * @param content of the object.
     * @param category of the object.
     * @param author of the object.
     * @param title of the object.
     */
    public Article(final LocalDateTime time,
            final String image,
            final String title,
            final String content,
            final String author,
            final String category) {
        this.time = time;
        this.image = image;
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
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
    public String getCategory() {
        return category;
    }

    /**
     * Setter for categories field.
     *
     * @param categories value to set.
     */
    public void setCategory(final String categories) {
        this.category = categories;
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
