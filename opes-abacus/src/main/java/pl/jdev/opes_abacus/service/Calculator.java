package pl.jdev.opes_abacus.service;

import pl.jdev.opes_abacus.controller.dto.InputArgs;
import pl.jdev.opes_abacus.domain.Result;

public interface Calculator {
    Result calculate(final InputArgs inputArgs);

}
