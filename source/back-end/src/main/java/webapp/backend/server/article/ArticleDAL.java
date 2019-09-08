package webapp.backend.server.article;

import java.util.List;

/**
 * Repository interface that queries article objects.
 */
public interface ArticleDAL {

    /**
     * Saves the article object in the database.
     *
     * @param article object to save.
     * @return the saved article object.
     */
    Article saveArticle(Article article);

    /**
     * Lists all inserted articles.
     *
     * @return all inserted articles.
     */
    List<Article> getAllArticle();

    /**
     * Lists 5 most recent articles.
     *
     * @return 5 most recent articles.
     */
    List<Article> getRecentArticles();

    /**
     * Lists recently inserted articles.
     *
     * @param limit the number of returned articles. (default 5)
     * @return recently inserted articles.
     */
    List<Article> getRecentArticles(int limit);

    /**
     * Lists 5 most recentl articles with given category.
     *
     * @param category of the articles to get.
     * @return recently inserted articles.
     */
    List<Article> getRecentArticlesByCategory(String category);

    /**
     * Lists recently inserted articles with category.
     *
     * @param category of the articles to get.
     * @param limit the number of returned articles. (default 5)
     * @return recently inserted articles.
     */
    List<Article> getRecentArticlesByCategory(String category, int limit);

    /**
     * Lists pageSize number of articles, by using a query.
     *
     * @param pageNumber number of the current page.
     * @param pageSize size of the page.
     * @return List of articles that fits on the page.
     */
    List<Article> getAllArticlePaginated(int pageNumber, int pageSize);

    /**
     * Queries the database by the article id string.
     *
     * @param id to match against in the database.
     * @return first successful match.
     */
    Article findByArticleId(String id);

    /**
     * Updates the article object in the database.
     *
     * @param article object with updated information.
     * @return the updated article object.
     */
    Article updateArticle(Article article);

    /**
     * Deletes the article object from the database.
     *
     * @param article object to delete.
     */
    void deleteArticle(Article article);
}
