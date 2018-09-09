package pl.jdev.oanda_rest_client.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.service.IReportable;

import java.util.Arrays;

@Component
@Slf4j
public class ServiceHealth implements HealthIndicator {

    @Autowired
    private IReportable[] reportableServices;

    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        String servicesStatus = Arrays.stream(reportableServices).map((IReportable::status)).reduce("", String::concat);
        return Health.up().withDetail("test", servicesStatus).build();
    }

    public int check() {
        return 0;
    }

}
