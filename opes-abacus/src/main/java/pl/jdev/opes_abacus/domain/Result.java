package pl.jdev.opes_abacus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jdev.opes_abacus.controller.dto.CalculationType;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result implements Serializable {
    private CalculationType type;
    private Object[] values;
}
