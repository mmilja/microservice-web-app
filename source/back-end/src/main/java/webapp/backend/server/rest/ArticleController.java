package webapp.backend.server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import webapp.backend.server.article.Article;
import webapp.backend.server.article.ArticleDAL;

import javax.validation.Valid;

/**
 * REST API controller class.
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    /**
     * Article data access layer object.
     */
    @Autowired
    private ArticleDAL articleDAL;

    /**
     * Get article by title.
     *
     * @param title that is provided by the URI.
     * @return Article object with set fields.
     */
    @RequestMapping(value = "/{title}", method = RequestMethod.GET)
    public final Article getArticle(@PathVariable("title") final String title) {
        return articleDAL.findByArticleTitle(title);

    }

    /**
     * Get article by title.
     *
     * @param title that is provided by the URI.
     * @return Article object with set fields.
     */
    @RequestMapping(value = "/recent", method = RequestMethod.GET)
    public final Article getRecentArticle(@PathVariable("title") final String title) {
        return articleDAL.findByArticleTitle(title);

    }

    /**
     * Create new article.
     *
     * @param article object to insert.
     * @return inserted article.
     */
    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public final Article newArticle(@Valid @RequestBody final Article article) {
        return articleDAL.saveArticle(article);

    }

    /**
     * Update an existing article.
     *
     * @param article object to update..
     * @return updated article object.
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public final Article updateArticle(@Valid @RequestBody final Article article) {
        return articleDAL.updateArticle(article);

    }

    /**
     * Delete an existing article.
     *
     * @param article object to delete.
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public final void deleteArticle(@Valid @RequestBody final Article article) {
         articleDAL.deleteArticle(article);
    }
}
