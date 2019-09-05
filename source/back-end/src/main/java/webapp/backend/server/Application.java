package webapp.backend.server;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import webapp.backend.server.article.Article;
import webapp.backend.server.article.ArticleDAL;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * Spring main class.
 * Initializes all spring objects.
 */
@SpringBootApplication
@ComponentScan
public class Application implements CommandLineRunner {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);


    /**
     * Mongo article controller.
     */
    private ArticleDAL articleDAL;

    /**
     * Constructor.
     *
     * @param articleDAL  article data access layer object.
     */
    @Autowired
    public Application(final ArticleDAL articleDAL) {
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

        LOGGER.info("Executing mongodb operations with: {}", articleDAL.toString());

        FileEnum path1 = FileEnum.valueOf("IMG1_PATH");
        FileEnum path2 = FileEnum.valueOf("IMG2_PATH");
        FileEnum path3 = FileEnum.valueOf("IMG3_PATH");
        FileEnum path4 = FileEnum.valueOf("IMG4_PATH");
        FileEnum path5 = FileEnum.valueOf("IMG5_PATH");

        //some random articles
        articleDAL.saveArticle(new Article(
                LocalDateTime.now(),
                loadImageToString(path1.getPath()),
                "What is Valuation? A modern business GO-TO!",
                "ArticleContent1",
                "ArticleAuthor1",
                "News"));

        articleDAL.saveArticle(new Article(
                LocalDateTime.now(),
                loadImageToString(path2.getPath()),
                "What's new in STEM today?",
                "ArticleContent2",
                "ArticleAuthor2",
                "News"));

        articleDAL.saveArticle(new Article(
                LocalDateTime.now(),
                loadImageToString(path3.getPath()),
                "It's an unforgettable experience, a MISSION to...",
                "ArticleContent3",
                "ArticleAuthor3",
                "Sport"));


        articleDAL.saveArticle(new Article(
                LocalDateTime.now(),
                loadImageToString(path4.getPath()),
                "SENSATIONAL, a diagram that tells your future! ",
                "ArticleContent4",
                "ArticleAuthor4",
                "Lifestyle"));


        articleDAL.saveArticle(new Article(
                LocalDateTime.now(),
                loadImageToString(path5.getPath()),
                "Rise in inflation equates to a decrease in deflation.",
                "ArticleContent5",
                "ArticleAuthor5",
                "News"));


        LOGGER.info("Latest articles: " + articleDAL.getRecentArticles());

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
