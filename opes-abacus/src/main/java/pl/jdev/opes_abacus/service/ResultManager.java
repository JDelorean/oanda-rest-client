package pl.jdev.opes_abacus.service;

import pl.jdev.opes_abacus.controller.dto.CalculationType;
import pl.jdev.opes_abacus.domain.Result;

import java.util.UUID;

public interface ResultManager {
    Result getResult(UUID resultId);

    Result storeResult(Object[] result);

    Result storeResult(CalculationType type, Object[] result);
}
