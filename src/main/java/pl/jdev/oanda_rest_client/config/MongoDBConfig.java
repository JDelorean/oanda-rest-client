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
import pl.jdev.oanda_rest_client.domain.pricing.Price;
import pl.jdev.oanda_rest_client.domain.transaction.Transaction;

import static java.lang.Integer.valueOf;

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
                                       @Value("${transaction.ttl.enabled}") boolean isTransactionTtlEnabled,
                                       @Value("${transaction.ttl.sec}") String transactionTtlSec,
                                       @Value("${pricing.ttl.enabled}") boolean isPricingTtlEnabled,
                                       @Value("${pricing.ttl.sec}") String pricingTtlSec,
                                       MongoClient mongoClient) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, dbName);
        Index ttlIndex = new Index().on("_modifiedDate", Sort.Direction.ASC);
        if (isTransactionTtlEnabled) {
            mongoTemplate.indexOps(Transaction.class)
                    .ensureIndex(ttlIndex.expire(valueOf(transactionTtlSec)));
        }
        if (isPricingTtlEnabled) {
            mongoTemplate.indexOps(Price.class)
                    .ensureIndex(ttlIndex.expire(valueOf(pricingTtlSec)));
        }
        return mongoTemplate;
    }
}
