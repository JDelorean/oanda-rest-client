package pl.jdev.opes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories
@EnableMongoAuditing
@EnableScheduling
public class Opes extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Opes.class, args);
    }
}