package pl.jdev.oanda_rest_client.config;

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
public class MongoTemplateConfig {
    @Bean
    @Autowired
    public MongoTemplate mongoTemplate(MongoTemplate mongoTemplate,
                                       @Value("transaction.ttl") int transactionTTL) {
        mongoTemplate.indexOps(Transaction.class)
                .ensureIndex(new Index().on("_documentModifiedTime", Sort.Direction.ASC).expire(transactionTTL));
        return mongoTemplate;
    }
}
