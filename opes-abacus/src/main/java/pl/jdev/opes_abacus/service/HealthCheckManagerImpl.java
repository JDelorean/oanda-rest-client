package pl.jdev.opes_abacus.service;

import org.springframework.stereotype.Service;

@Service
public class HealthCheckManagerImpl implements HealthCheckManager {
    @Override
    public boolean checkHealth() {
        //TODO: implement
        return true;
    }
}
