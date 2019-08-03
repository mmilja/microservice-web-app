package webapp.backend.server.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mongo Controller class.
 */
@EnableMongoRepositories(mongoTemplateRef = "mainMongoTemplate")
@Repository
public class ArticleDALImpl implements ArticleDAL {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDALImpl.class);

    /**
     * Default number of articles to get in no number is provided.
     */
    private static final int DEFAULT_ARTICLE_NUMBER = 5;

    /**
     * Create ArticleRepository object instance and connect it to mongodb.
     */
    private final MongoTemplate mongoTemplate;

    /**
     * Constructor.
     *
     * @param mongoTemplate to communicate with mongodb.
     */
    @Autowired
    public ArticleDALImpl(final MongoTemplate mongoTemplate) {
        LOGGER.info("mongo template " + mongoTemplate.toString());
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Article saveArticle(final Article article) {
        mongoTemplate.save(article);
        return article;
    }

    @Override
    public List<Article> getRecentArticles() {
        return getRecentArticles(DEFAULT_ARTICLE_NUMBER);
    }

    @Override
    public List<Article> getRecentArticles(final int limit) {
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "time")));
        query.limit(limit);

        return mongoTemplate.find(query, Article.class);
    }

    @Override
    public List<Article> getAllArticle() {
        return mongoTemplate.findAll(Article.class);
    }

    @Override
    public List<Article> getAllArticlePaginated(final int pageNumber, final int pageSize) {
        Query query = new Query();
        long querySkipNumber = (long) pageNumber * pageSize;

        query.skip(querySkipNumber);
        query.limit(pageSize);

        return mongoTemplate.find(query, Article.class);
    }

    @Override
    public Article findByArticleTitle(final String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));

        return mongoTemplate.findOne(query, Article.class);
    }


    @Override
    public Article updateArticle(final Article article) {
        mongoTemplate.save(article);
        return article;
    }

    @Override
    public void deleteArticle(final Article article) {
        mongoTemplate.remove(article);
    }

    @Override
    public List<Article> getRecentArticlesByCategory(final String category) {
        return getRecentArticlesByCategory(category, DEFAULT_ARTICLE_NUMBER);
    }

    @Override
    public List<Article> getRecentArticlesByCategory(final String category, final int limit) {
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(category));
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "time")));
        query.limit(limit);

        return mongoTemplate.find(query, Article.class);
    }
}
