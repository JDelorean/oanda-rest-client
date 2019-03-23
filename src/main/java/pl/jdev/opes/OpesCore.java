package pl.jdev.opes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@EnableAsync
@EnableJpaRepositories
@EnableJpaAuditing
@EnableTransactionManagement
public class OpesCore extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(OpesCore.class, args);
    }
}