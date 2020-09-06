package pl.jdev.opes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.jdev.opes_commons.domain.instrument.*;
import pl.jdev.opes_commons.rest.client.IntegrationClient;
import pl.jdev.opes_commons.rest.message.event.CandlestickUpdatedPayload;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
import static pl.jdev.opes.helper.DateTimeHelper.now;
import static pl.jdev.opes.helper.DateTimeHelper.nowMinus;
import static pl.jdev.opes_commons.rest.HttpHeaders.EVENT_TYPE;
import static pl.jdev.opes_commons.rest.message.event.EventType.CANDLESTICKS_UPDATED;

@Service
public class CandlesJob {
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private IntegrationClient integrationClient;

    @Scheduled(fixedRateString = "${opes.poll.instrument}")
    void updateTrackedCandlesFromExternal() {
        List<Instrument> tracked = instrumentService.getTrackedInstruments();
        tracked.parallelStream()
                .map(Instrument::getName)
                .map(instrument ->
                        instrumentService.getCandlesFromExternal(instrument,
                                CandlestickPriceType.M,
                                CandlestickGranularity.M1,
                                nowMinus(2, MINUTES),
                                now()))
                .map(this::fillCandleGaps)
                .map(this::saveCandles)
                .forEach(this::postToIntegration);
    }


    private List<Candlestick> fillCandleGaps(List<Candlestick> list) {
        list.sort(Candlestick.TimeComparator);
        Candlestick last = list.get(list.size() - 1);
        Instant lastTime = Instant.parse(last.getTime());
        ChronoUnit timeUnit = last.getGranularity().getUnit();
        long timeUnitDiff = Duration.between(lastTime, now().truncatedTo(timeUnit)).toMillis() / last.getGranularity().getMillis();
        while (timeUnitDiff != 0) {
            try {
                Candlestick newCandle = (Candlestick) last.clone();
                Double newPrice = last.getMid().getC();
                newCandle.setMid(new CandlestickData(newPrice, newPrice, newPrice, newPrice));
                Instant newTime = Instant.parse(last.getTime()).plus(1, timeUnit);
                newCandle.setTime(newTime.toString());
                list.add(newCandle);
                last = newCandle;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            timeUnitDiff--;
        }
        return list;
    }

    private List<Candlestick> saveCandles(List<Candlestick> list) {
        list.forEach(instrumentService::insertCandles);
        return list;
    }

    private void postToIntegration(List<Candlestick> list) {
        Instrument instrument = instrumentService.getInstrument(list.get(0).getInstrument());
        CandlestickUpdatedPayload payload = new CandlestickUpdatedPayload(instrument, list);
        integrationClient.post(MessageBuilder
                .withPayload(payload)
                .setHeader(EVENT_TYPE, CANDLESTICKS_UPDATED.getKey())
                .build());
    }
}
