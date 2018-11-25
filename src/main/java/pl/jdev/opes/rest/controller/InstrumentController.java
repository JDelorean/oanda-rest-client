package pl.jdev.opes.rest.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes.rest.json.wrapper.*;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;
import pl.jdev.opes_commons.domain.instrument.Instrument;
import pl.jdev.opes_commons.rest.HttpHeaders;
import pl.jdev.opes_commons.rest.message.DataRequest;
import pl.jdev.opes_commons.rest.message.MARequest;
import pl.jdev.opes_commons.rest.message.PricingRequest;

import java.util.Collection;
import java.util.Map;

import static pl.jdev.opes_commons.rest.HttpHeaders.DATA_TYPE;

@RestController
@RequestMapping("/instruments/{instrument}/")
@Log4j2
public class InstrumentController extends AbstractEntityController<Instrument> {

    @GetMapping(value = "/candles", params = "count")
    @ResponseBody
    public JsonCandlestickListWrapper getCandlesticksWithCount(@PathVariable(name = "instrument") final String instrument,
                                                               @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                                               @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                                               @RequestParam(value = "count") final Integer count) {
        DataRequest pricingRequest = new PricingRequest(instrument, priceType, granularity, count);
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "pricing");
        return JsonCandlestickListWrapper.payloadOf(
                (Collection<Candlestick>) integrationClient.requestData(
                        pricingRequest,
                        headers
                ).getBody()
        );
    }

    @GetMapping(value = "/candles", params = {"from", "to"})
    @ResponseBody
    public JsonCandlestickListWrapper getCandlesticksWithPeriod(@PathVariable(name = "instrument") final String instrument,
                                                                @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                                                @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                                                @RequestParam(value = "from") final String from,
                                                                @RequestParam(value = "to") final String to) {
        DataRequest pricingRequest = new PricingRequest(instrument, priceType, granularity, from, to);
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "pricing");
        return JsonCandlestickListWrapper.payloadOf(
                (Collection<Candlestick>) integrationClient.requestData(
                        pricingRequest,
                        headers
                ).getBody()
        );
    }

    @GetMapping(value = "/sma", params = "amtOfPeriods")
    @ResponseBody
    public JsonSMAWrapper getLatestSma(@PathVariable(name = "instrument") final String instrument,
                                       @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                       @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                       @RequestParam(value = "amtOfPeriods") final Integer amtOfPeriods) {
        Collection<Candlestick> candles = getCandlesticksWithCount(instrument, priceType, granularity, amtOfPeriods).getCandles();
        DataRequest maRequest = new MARequest(candles, 1, candles.size());
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "sma");
        return JsonSMAWrapper.payloadOf(
                (Map<String, Double>) integrationClient.requestData(
                        maRequest,
                        headers
                ).getBody());
    }

    @GetMapping(value = "/sma", params = {"amtOfIndics", "amtOfPeriods"})
    @ResponseBody
    public JsonSMAListWrapper getLatestSmaList(@PathVariable(name = "instrument") final String instrument,
                                               @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                               @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                               @RequestParam(value = "amtOfIndics") final Integer amtOfIndics,
                                               @RequestParam(value = "amtOfPeriods") final Integer amtOfPeriods) {
        int count = amtOfIndics + amtOfPeriods + 1;
        Collection<Candlestick> candles = getCandlesticksWithCount(instrument, priceType, granularity, count).getCandles();
        DataRequest maRequest = new MARequest(candles, amtOfIndics, amtOfPeriods);
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "sma");
        return JsonSMAListWrapper.payloadOf((Map<String, Double>) integrationClient.requestData(
                maRequest,
                headers
        ).getBody());
    }

    @GetMapping(value = "/ema", params = "amtOfPeriods")
    @ResponseBody
    public JsonEMAWrapper getLatestEma(@PathVariable(name = "instrument") final String instrument,
                                       @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                       @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                       @RequestParam(value = "amtOfPeriods") final Integer amtOfPeriods) {
        Collection<Candlestick> candles = getCandlesticksWithCount(instrument, priceType, granularity, amtOfPeriods).getCandles();
        DataRequest maRequest = new MARequest(candles, 1, candles.size());
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "ema");
        return JsonEMAWrapper.payloadOf((Map<String, Double>) integrationClient.requestData(
                maRequest,
                headers
        ).getBody());
    }

    @GetMapping(value = "/ema", params = {"amtOfIndics", "amtOfPeriods"})
    @ResponseBody
    public JsonEMAListWrapper getLatestEmaList(@PathVariable(name = "instrument") final String instrument,
                                               @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                               @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                               @RequestParam(value = "amtOfIndics") final Integer amtOfIndics,
                                               @RequestParam(value = "amtOfPeriods") final Integer amtOfPeriods) {
        int count = amtOfIndics + amtOfPeriods + 1;
        Collection<Candlestick> candles = getCandlesticksWithCount(instrument, priceType, granularity, count).getCandles();
        DataRequest maRequest = new MARequest(candles, amtOfIndics, amtOfPeriods);
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "ema");
        return JsonEMAListWrapper.payloadOf((Map<String, Double>) integrationClient.requestData(
                maRequest,
                headers
        ).getBody());
    }
}
