package webapp.backend.server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import webapp.backend.server.article.Article;
import webapp.backend.server.article.ArticleDAL;

import javax.validation.Valid;
import java.util.List;

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
     * @param id that is provided by the URI.
     * @return Article object with set fields.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final Article getArticle(@PathVariable("id") final String id) {
        return articleDAL.findByArticleId(id);

    }

    /**
     * Get recent article.
     *
     * @return Article object with set fields.
     */
    @RequestMapping(value = "/recent", method = RequestMethod.GET)
    public final List<Article> getRecentArticle() {
        return articleDAL.getRecentArticles();
    }

    /**
     * Get article by title.
     *
     * @param category to filter for.
     * @return Article object with set fields.
     */
    @RequestMapping(value = "/recentCategory", method = RequestMethod.GET)
    public final List<Article> getRecentArticleWithCategory(
            @RequestParam("category") final String category) {
        return articleDAL.getRecentArticlesByCategory(category);
    }

    /**
     * Create new article.
     *
     * @param article object to insert.
     * @return inserted article.
     */
    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.CREATED)
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
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public final void deleteArticle(@Valid @RequestBody final Article article) {
         articleDAL.deleteArticle(article);
    }
}
