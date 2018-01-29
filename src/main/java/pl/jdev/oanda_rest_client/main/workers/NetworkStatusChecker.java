package pl.jdev.oanda_rest_client.main.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NetworkStatusChecker {

    final static Logger LOGGER = LoggerFactory.getLogger(NetworkStatusChecker.class);

    private RestTemplate restTemplate;

    public NetworkStatusChecker(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Scheduled(fixedRate = 5000)
    public void checkNetwork() {
        LOGGER.info("Checking network status...");
        LOGGER.info(this.restTemplate.getForEntity("https://www.google.com", String.class).getStatusCode().name());
    }

}