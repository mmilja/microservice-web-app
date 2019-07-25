package webapp.backend.server.mongo;

import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Mongo config class.
 */
@Configuration
@EnableAutoConfiguration
public class MainMongoConfig {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MainMongoConfig.class);

    /**
     * MongoDB URI.
     */
    private static final String MONGO_URI = "mongodb://username:password@web-app-mongodb:27017/article";

    /**
     * Mongo Factory.
     *
     * @return a simple db factory.
     * @throws Exception in case of an error.
     */
    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        LOGGER.info("URI on which mongodb is located: " + MONGO_URI);
        return new SimpleMongoDbFactory(new MongoClientURI(MONGO_URI));
    }

    /**
     * Getter.
     *
     * @return mongodb template.
     * @throws Exception in case of an error.
     */
    @Primary
    @Bean(name = "mainMongoTemplate")
    public MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}
