package pl.jdev.opes.service.calculator;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.jdev.opes.domain.instrument.Candlestick;
import pl.jdev.opes.domain.instrument.CandlestickData;
import pl.jdev.opes.rest.exception.CandlesValidationException;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Simple Moving Average Calculator
 */
@Component(value = "smaCalculator")
@Log4j2
public class SMACalculator {

    @Autowired
    DateFormat dateFormat;

    public double calculate(Collection<Candlestick> candles) throws CandlesValidationException {
        log.traceEntry(format("Calculating SMA from ", candles.toString()));
//        log.info(String.format("Calculating SMA based on: %s", candles));
//        try {
//            this.validate(candles);
//        } catch (Exception e) {
//            log.warning(e.getMessage());
//            throw e;
//        }
        Map<String, CandlestickData> candleMap = strip(candles);

        double candleCloseSum = candleMap.keySet()
                .stream()
                .sorted((strDate1, strDate2) -> {
                    int comp = 0;
                    try {
                        comp = dateFormat.parse(strDate1).compareTo(dateFormat.parse(strDate2));
                    } catch (ParseException e) {
                        log.error(e.getMessage());
                    }
                    return comp;
                })
                .peek(date -> log.trace(date))
                .map(candleMap::get)
                .peek(candle -> log.trace(candle))
                .mapToDouble(CandlestickData::getC)
                .sum();
        double sma = candleCloseSum / candleMap.size();
        log.traceExit(format("SMA = %f", sma));
        return sma;
    }

    private void validate(Collection<Candlestick> candles) throws CandlesValidationException {
        Collection<Optional<CandlestickData>> asks = candles.stream()
                .map(candlestick -> Optional.ofNullable(candlestick.getAsk()))
                .collect(Collectors.toList());
        Collection<Optional<CandlestickData>> bids = candles.stream()
                .map(candlestick -> Optional.ofNullable(candlestick.getBid()))
                .collect(Collectors.toList());
        Collection<Optional<CandlestickData>> mids = candles.stream()
                .map(candlestick -> Optional.ofNullable(candlestick.getMid()))
                .collect(Collectors.toList());
        boolean validAsks = asks.stream().allMatch(Optional::isPresent);
        boolean validBids = bids.stream().allMatch(Optional::isPresent);
        boolean validMids = mids.stream().allMatch(Optional::isPresent);
        if (!(validAsks && validBids && validMids)) {
            throw new CandlesValidationException(format("Candles validation failed: %s", candles));
        }
    }

    private Map<String, CandlestickData> strip(Collection<Candlestick> candles) {
        log.traceEntry(format("Stripping candlesticks %s", candles));
        Map<String, CandlestickData> stripped = candles.stream()
                .map(candlestick -> Map.entry(candlestick.getTime(),
                        Optional.ofNullable(candlestick.getAsk())
                                .orElse(Optional.ofNullable(candlestick.getBid())
                                        .orElse(Optional.ofNullable(candlestick.getMid())
                                                .get()))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        log.traceExit(format("Stripped to %s", stripped));
        return stripped;
    }
}