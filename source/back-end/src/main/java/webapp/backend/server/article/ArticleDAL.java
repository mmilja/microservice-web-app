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
     * Lists pageSize number of articles, by using a query.
     *
     * @param pageNumber number of the current page.
     * @param pageSize size of the page.
     * @return List of articles that fits on the page.
     */
    List<Article> getAllArticlePaginated(int pageNumber, int pageSize);

    /**
     * Queries the database by the articlename string.
     *
     * @param title to match against in the database.
     * @return first successful match.
     */
    Article findByArticleTitle(String title);

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
