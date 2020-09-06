package pl.jdev.opes_abacus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jdev.opes_abacus.service.HealthCheckManager;

@RestController
@RequestMapping("/health_check")
public class HealthCheckController {
    private final HealthCheckManager healthCheckManager;

    public HealthCheckController(final HealthCheckManager healthCheckManager) {
        this.healthCheckManager = healthCheckManager;
    }

    @GetMapping
    public ResponseEntity<String> index() {
        if (healthCheckManager.checkHealth()) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
