package pl.jdev.oanda_rest_client;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories
@Log
public class OandaRestClient {

    public static void main(String[] args) {
        SpringApplication.run(OandaRestClient.class, args);
        log.info("Initiating oanda_service-rest-client...");
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "oanda_service-rest-client";
    }
}