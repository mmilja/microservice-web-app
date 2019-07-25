package webapp.backend.server;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import webapp.backend.server.article.Article;
import webapp.backend.server.article.ArticleDAL;
import webapp.backend.server.user.User;
import webapp.backend.server.user.UserDAL;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * Spring main class.
 * Initializes all spring objects.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    /**
     * Sleeping until mongodb is online!
     */
    private static final int SLEEP_TIME = 600000;

    /**
     * Mongo user controller.
     */
    private UserDAL userDAL;

    /**
     * Mongo article controller.
     */
    private ArticleDAL articleDAL;

    /**
     * Constructor.
     *
     * @param userDAL user data access layer object.
     * @param articleDAL  article data access layer object.
     */
    @Autowired
    public Application(final UserDAL userDAL, final ArticleDAL articleDAL) {
        this.userDAL = userDAL;
        this.articleDAL = articleDAL;
    }

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
     * @param args varagrgs sent to the function.
     * @throws Exception in case of an error.
     */
    @Override
    public final void run(final String... args) throws Exception {

        /**
         * Sleeping until mongodb is online!
         */
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (Exception exception) {
            System.err.println("Error during sleep!");
        }

        LOGGER.info("Executing mongodb operations");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String userPass = passwordEncoder.encode("Access");
        FileEnum path = FileEnum.valueOf("IMG_PATH");

        //rest api database access user!
        userDAL.saveUser(new User("Database", userPass));

        //some random articles
        articleDAL.saveArticle(new Article(
                LocalDateTime.now(),
                loadImageToString(path.getPath()),
                "ArticleTitle1",
                "ArticleContent1",
                "ArticleAuthor1",
                "ArticleCategory1"));

        articleDAL.saveArticle(new Article(
                LocalDateTime.now(),
                "",
                "ArticleTitle2",
                "ArticleContent2",
                "ArticleAuthor2",
                "ArticleCategory2"));

    }

    /**
     * Utility functiaon to load an image.
     *
     * @param path to the image.
     * @return image as a base64 encoded string.
     */
    private String loadImageToString(final String path) {
        String encodedString = null;
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(path));
            encodedString = Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException exception) {
            System.err.println("Failed to read image from a file!");
        }
        return encodedString;
    }
}
