package pl.jdev.opes.rest.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes.rest.exception.NotFoundException;
import pl.jdev.opes.service.InstrumentService;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;
import pl.jdev.opes_commons.domain.instrument.Instrument;
import pl.jdev.opes_commons.rest.HttpHeaders;
import pl.jdev.opes_commons.rest.message.response.JsonEMAListWrapper;
import pl.jdev.opes_commons.rest.message.response.JsonEMAWrapper;
import pl.jdev.opes_commons.rest.message.response.JsonSMAListWrapper;
import pl.jdev.opes_commons.rest.message.response.JsonSMAWrapper;

import java.util.List;

import static pl.jdev.opes_commons.rest.HttpHeaders.REQUEST_TYPE;

@RestController
@RequestMapping("/instruments/{instrument}")
@Log4j2
public class InstrumentController extends AbstractEntityController<Instrument> {

    @Autowired
    private InstrumentService instrumentService;

    @GetMapping(value = "/candles", params = "count")
    @ResponseBody
    public List<Candlestick> getCandlesticksWithCount(@PathVariable(name = "instrument") final String instrument,
                                                      @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                                      @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                                      @RequestParam(value = "count") final Integer count) {
        return instrumentService.getInstrumentCandles(instrument, priceType, granularity, count, false);
    }

    @GetMapping(value = "/candles", params = {"from", "to"})
    @ResponseBody
    public List<Candlestick> getCandlesticksWithPeriod(@PathVariable(name = "instrument") final String instrument,
                                                       @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                                       @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                                       @RequestParam(value = "from") final String from,
                                                       @RequestParam(value = "to") final String to) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(REQUEST_TYPE, "candle");
//        return JsonCandlestickListWrapper.payloadOf(
//                (Collection<Candlestick>) integrationClient.requestData(
//                        candlesRequest,
//                        headers,
//                        JsonCandlestickListWrapper.class
//                ).getBody()
//        );
        return null;
    }

    @PostMapping(value = "/track")
    @ResponseStatus(HttpStatus.OK)
    public void trackInstrument(@PathVariable(name = "instrument") final String instrument) throws NotFoundException {
        instrumentService.trackInstrument(instrument, true);
    }

    @PostMapping(value = "/untrack")
    @ResponseStatus(HttpStatus.OK)
    public void untrackInstrument(@PathVariable(name = "instrument") final String instrument) throws NotFoundException {
        instrumentService.trackInstrument(instrument, false);
    }

    @GetMapping(value = "/sma", params = "amtOfPeriods")
    @ResponseBody
    public JsonSMAWrapper getLatestSma(@PathVariable(name = "instrument") final String instrument,
                                       @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                       @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                       @RequestParam(value = "amtOfPeriods") final Integer amtOfPeriods) {
//        Collection<Candlestick> candles = getCandlesticksWithCount(instrument, priceType, granularity, amtOfPeriods).getCandles();
//        DataRequest maRequest = new MARequest(candles, 1, candles.size());
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(DATA_TYPE, "sma");
//        return JsonSMAWrapper.payloadOf(
//                (Map<String, Double>) integrationClient.requestData(
//                        maRequest,
//                        headers,
//                        Map.class
//                ).getBody());
        return null;
    }

    @GetMapping(value = "/sma", params = {"amtOfIndics", "amtOfPeriods"})
    @ResponseBody
    public JsonSMAListWrapper getLatestSmaList(@PathVariable(name = "instrument") final String instrument,
                                               @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                               @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                               @RequestParam(value = "amtOfIndics") final Integer amtOfIndics,
                                               @RequestParam(value = "amtOfPeriods") final Integer amtOfPeriods) {
//        int count = amtOfIndics + amtOfPeriods + 1;
//        Collection<Candlestick> candles = getCandlesticksWithCount(instrument, priceType, granularity, count).getCandles();
//        DataRequest maRequest = new MARequest(candles, amtOfIndics, amtOfPeriods);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(DATA_TYPE, "sma");
//        return JsonSMAListWrapper.payloadOf((Map<String, Double>) integrationClient.requestData(
//                maRequest,
//                headers,
//                Map.class
//        ).getBody());
        return null;
    }

    @GetMapping(value = "/ema", params = "amtOfPeriods")
    @ResponseBody
    public JsonEMAWrapper getLatestEma(@PathVariable(name = "instrument") final String instrument,
                                       @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                       @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                       @RequestParam(value = "amtOfPeriods") final Integer amtOfPeriods) {
//        Collection<Candlestick> candles = getCandlesticksWithCount(instrument, priceType, granularity, amtOfPeriods).getCandles();
//        DataRequest maRequest = new MARequest(candles, 1, candles.size());
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(DATA_TYPE, "ema");
//        return JsonEMAWrapper.payloadOf((Map<String, Double>) integrationClient.requestData(
//                maRequest,
//                headers,
//                Map.class
//        ).getBody());
        return null;
    }

    @GetMapping(value = "/ema", params = {"amtOfIndics", "amtOfPeriods"})
    @ResponseBody
    public JsonEMAListWrapper getLatestEmaList(@PathVariable(name = "instrument") final String instrument,
                                               @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                               @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                               @RequestParam(value = "amtOfIndics") final Integer amtOfIndics,
                                               @RequestParam(value = "amtOfPeriods") final Integer amtOfPeriods) {
//        int count = amtOfIndics + amtOfPeriods + 1;
//        Collection<Candlestick> candles = getCandlesticksWithCount(instrument, priceType, granularity, count).getCandles();
//        DataRequest maRequest = new MARequest(candles, amtOfIndics, amtOfPeriods);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(DATA_TYPE, "ema");
//        return JsonEMAListWrapper.payloadOf((Map<String, Double>) integrationClient.requestData(
//                maRequest,
//                headers,
//                Map.class
//        ).getBody());
        return null;
    }
}
