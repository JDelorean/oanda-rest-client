package pl.jdev.oanda_rest_client.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import pl.jdev.oanda_rest_client.domain.transaction.Transaction;

@Configuration
@PropertySource("classpath:mongodb.properties")
public class MongoDBConfig {
    @Bean
    public MongoClient mongoClient(@Value("${mongodb.url}") String mongoUrl) {
        return new MongoClient(mongoUrl);
    }

    @Bean
    @Autowired
    public MongoTemplate mongoTemplate(@Value("${mongodb.name}") String dbName,
                                       @Value("${transaction.ttl.sec}") String transactionTTL,
                                       MongoClient mongoClient) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, dbName);
        mongoTemplate.indexOps(Transaction.class)
                .ensureIndex(new Index()
                        .on("_modifiedDate", Sort.Direction.ASC)
                        .expire(Integer.valueOf(transactionTTL)));
        return mongoTemplate;
    }
}
