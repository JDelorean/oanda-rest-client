package pl.jdev.opes_abacus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jdev.opes_abacus.controller.dto.InputArgs;
import pl.jdev.opes_abacus.domain.Result;
import pl.jdev.opes_abacus.service.Calculator;

@RestController
@RequestMapping("/calculate")
public class CalculatorController {

    private Calculator calculator;

    public CalculatorController(final Calculator calculator) {
        this.calculator = calculator;
    }

    @PostMapping
    public ResponseEntity<Result> calculate(@RequestBody final InputArgs inputArgs) {
        return new ResponseEntity<>(calculator.calculate(inputArgs), HttpStatus.OK);
    }
}
