package pl.jdev.opes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jdev.opes.db.dto.mapper.InstrumentMapper;
import pl.jdev.opes.db.repo.InstrumentRepository;
import pl.jdev.opes_commons.rest.IntegrationClient;

@Service
public class InstrumentService {
    @Autowired
    private InstrumentRepository repo;
    @Autowired
    private IntegrationClient intC;
    @Autowired
    private InstrumentMapper mapper;
}
