package pl.jdev.opes.service;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Date;

@Service
@Data
@Log
public class NetworkCheckService {

    private RestTemplate restTemplate;
    private Date lastRun;
    private boolean isNetworkAvailable;

    @Autowired
    public NetworkCheckService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @SneakyThrows
    @Scheduled(fixedRateString = "${orc.service.network_status.interval}")
    public void checkNetwork() {
        log.info("Checking network status...");
        lastRun = Date.from(Instant.now());
        isNetworkAvailable = restTemplate.getForEntity("https://www.google.com", String.class).getStatusCode()
                .is2xxSuccessful();
        log.info(String.format("Last run timestamp: %tc, Network available: %b.", lastRun, isNetworkAvailable));
    }

    public String status() {
        return String.format("Last run timestamp: %tc, Network available: %b.", lastRun, isNetworkAvailable);
    }
}