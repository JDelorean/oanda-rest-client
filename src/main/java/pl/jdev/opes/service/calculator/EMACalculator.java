package pl.jdev.opes.service.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.jdev.opes.domain.instrument.Candlestick;

import java.text.DateFormat;
import java.util.Collection;

import static java.lang.String.format;

/**
 * Exponential Moving Average Calculator
 */
@Component(value = "emaCalculator")
public class EMACalculator {

    Logger logger = LogManager.getLogger(SMACalculator.class);
    @Autowired
    DateFormat dateFormat;

    public double calculate(Collection<Candlestick> candles){
        logger.traceEntry(format("Calculating EMA from ", candles.toString()));

        return 0;
    }

}
