package pl.jdev.opes_abacus.service;

import pl.jdev.opes_abacus.domain.MovingAverage;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickData;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SimpleCalculator {

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);

        default <V> TriFunction<A, B, C, V> andThen(
                Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (A a, B b, C c) -> after.apply(apply(a, b, c));
        }
    }

    static Function<Candlestick[], Candlestick[]> sortByDate() {
        return candlesticks -> {
            Arrays.sort(candlesticks, Candlestick.TimeComparator);
            return candlesticks;
        };
    }

    static BiFunction<Candlestick[], Integer, Candlestick[][]> divideByPeriod() {
        return (candlesticks, period) -> {
            int periodAmt = candlesticks.length - period + 1;
            Candlestick[][] res = new Candlestick[periodAmt][period];
            for (int i = 0; i < periodAmt; i++) {
                res[i] = Arrays.copyOfRange(candlesticks, i, i + period);
            }
            return res;
        };
    }

    static TriFunction<Candlestick, Integer, Double, MovingAverage> mergeToMA() {
        return (candlestick, periodLength, value) ->
                new MovingAverage(
                        candlestick.getInstrument(),
                        candlestick.getGranularity(),
                        periodLength,
                        candlestick.getTime(),
                        value);
    }

    public static Function<Candlestick[], MovingAverage> singleSma() {
        return candlesticks -> {
            int size = candlesticks.length;
            return Arrays.stream(candlesticks)
                    .map(Candlestick::getMid)
                    .map(CandlestickData::getC)
                    .reduce(Double::sum)
                    .map(s -> s / size)
                    .map(value -> mergeToMA().apply(candlesticks[size - 1], size, value))
                    .orElseThrow(IllegalArgumentException::new);
        };
    }

    public static BiFunction<Integer, Candlestick[], MovingAverage[]> mulitpleSma() {
        return (periodLength, candlesticks) -> {
            Candlestick[] sorted = sortByDate().apply(candlesticks);
            Candlestick[][] divided = divideByPeriod().apply(sorted, periodLength);
            return Arrays.stream(divided)
                    .map(array -> singleSma().apply(array))
                    .toArray(MovingAverage[]::new);
        };
    }

    static Function<Integer, Double> weightedMultiplier() {
        return period -> (double) (2 / (period + 1));
    }


    public static TriFunction<Double, Candlestick, Integer, MovingAverage> singleEma() {
        return (previousEma, currentCandle, period) -> {
            Double multiplier = weightedMultiplier().apply(period);
            Double currentPrice = currentCandle.getMid().getC();
            Double value = currentPrice * multiplier + previousEma * (1 - multiplier);
            return mergeToMA().apply(currentCandle, period, value);
        };
    }

    public static BiFunction<Integer, Candlestick[], MovingAverage[]> multipleEma() {
        return (periodLength, candlesticks) -> {
            Candlestick[] sorted = sortByDate().apply(candlesticks);
            Candlestick[][] divided = divideByPeriod().apply(sorted, periodLength);
            MovingAverage[] res = new MovingAverage[divided.length];
            Candlestick[] lastCandles = Arrays.stream(divided)
                    .map(array -> array[periodLength - 1])
                    .toArray(Candlestick[]::new);
            for (int i = 0; i < lastCandles.length; i++) {
                if (i == 0) {
                    res[0] = singleSma().apply(divided[0]);
                } else {
                    res[i] = singleEma().apply(res[i - 1].getValue(), lastCandles[i], periodLength);
                }
            }
            return res;
        };
    }
}
