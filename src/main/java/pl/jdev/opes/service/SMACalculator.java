package pl.jdev.opes.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.jdev.opes.domain.instrument.Candlestick;
import pl.jdev.opes.domain.instrument.CandlestickData;
import pl.jdev.opes.rest.exception.CandlesValidationException;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Simple Moving Average Calculator
 */
@Component
@Log
public class SMACalculator {
    public double calculate(Collection<Candlestick> candles) throws CandlesValidationException {
        try {
            this.validate(candles);
        } catch (Exception e) {
            log.warning(e.getMessage());
            throw e;
        }
        Map<String, CandlestickData> rawCandles = strip(candles);
        Map<String, CandlestickData> orderedCandles = new LinkedHashMap<>(rawCandles);
        double candleCloseSum = orderedCandles.keySet()
                .stream()
                .map(orderedCandles::get)
                .mapToDouble(CandlestickData::getC)
                .sum();
        return candleCloseSum / orderedCandles.size();
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
            throw new CandlesValidationException(String.format("Candles validation failed: %s", candles));
        }
    }

    private Map<String, CandlestickData> strip(Collection<Candlestick> candles) {
        return candles.stream()
                .map(candlestick -> Map.entry(candlestick.getTime(),
                        Optional.of(candlestick.getAsk())
                                .orElse(Optional.of(candlestick.getBid())
                                        .orElse(Optional.of(candlestick.getMid())
                                                .get()))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
