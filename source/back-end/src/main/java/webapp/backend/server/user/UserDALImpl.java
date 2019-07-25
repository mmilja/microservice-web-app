package webapp.backend.server.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserDALImpl implements UserDAL {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDALImpl.class);

    /**
     * Create UserRepository object instance and connect it to mongodb.
     */
    private final MongoTemplate mongoTemplate;

    /**
     * Constructor.
     *
     * @param mongoTemplate to communicate with mongodb.
     */
    @Autowired
    public UserDALImpl(final MongoTemplate mongoTemplate) {
        LOGGER.info("mongo template " + mongoTemplate.toString());
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User saveUser(final User user) {
        mongoTemplate.save(user);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public List<User> getAllUserPaginated(final int pageNumber, final int pageSize) {
        Query query = new Query();
        long querySkipNumber = (long) pageNumber * pageSize;

        query.skip(querySkipNumber);
        query.limit(pageSize);

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public User findByUsername(final String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        return mongoTemplate.findOne(query, User.class);
    }


    @Override
    public User updateUser(final User user) {
        mongoTemplate.save(user);
        return user;
    }

    @Override
    public void deleteUser(final User user) {
        mongoTemplate.remove(user);
    }
}
