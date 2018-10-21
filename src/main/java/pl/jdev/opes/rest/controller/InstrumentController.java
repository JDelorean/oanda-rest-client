package pl.jdev.opes.rest.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes.domain.instrument.CandlestickGranularity;
import pl.jdev.opes.domain.instrument.CandlestickPriceType;
import pl.jdev.opes.rest.json.wrapper.JsonCandlestickListWrapper;
import pl.jdev.opes.rest.json.wrapper.JsonSMAListWrapper;
import pl.jdev.opes.rest.json.wrapper.JsonSMAWrapper;
import pl.jdev.opes.service.oanda_service.instrument.OandaInstrumentService;

@RestController
@RequestMapping("/instruments/{instrument}/")
@Log4j2
public class InstrumentController {
    @Autowired
    OandaInstrumentService oandaInstrumentService;

    @GetMapping(value = "/candles", params = "count")
    @ResponseBody
    public JsonCandlestickListWrapper getCandlestricksWithCount(@PathVariable(name = "instrument") final String instrument,
                                                                @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                                                @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                                                @RequestParam(value = "count") final Integer count) {
        return JsonCandlestickListWrapper.payloadOf(
                oandaInstrumentService.getCandlestickList(
                        instrument,
                        priceType,
                        granularity,
                        count)
        );
    }

    @GetMapping(value = "/candles", params = "period")
    @ResponseBody
    public JsonCandlestickListWrapper getCandlestricksWithPeriod(@PathVariable(name = "instrument") final String instrument,
                                                                 @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                                                 @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                                                 @RequestParam(value = "from") final String from,
                                                                 @RequestParam(value = "to") final String to) {
        return JsonCandlestickListWrapper.payloadOf(
                oandaInstrumentService.getCandlestickList(
                        instrument,
                        priceType,
                        granularity,
                        from,
                        to)
        );
    }

    @GetMapping(value = "/sma", params = "count")
    @ResponseBody
    public JsonSMAWrapper getSmaWithCount(@PathVariable(name = "instrument") final String instrument,
                                          @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                          @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                          @RequestParam(value = "count") final Integer count) {
        return JsonSMAWrapper.payloadOf(oandaInstrumentService.getSma(instrument, priceType, granularity, count));
    }

    @GetMapping(value = "/sma", params = {"numOfSMAs", "numOfTimePeriods"})
    @ResponseBody
    public JsonSMAListWrapper getSmaListWithPeriods(@PathVariable(name = "instrument") final String instrument,
                                                    @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                                    @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                                    @RequestParam(value = "numOfSMAs") final Integer numOfSMAs,
                                                    @RequestParam(value = "numOfTimePeriods") final Integer numOfTimePeriods) {
        return JsonSMAListWrapper.payloadOf(oandaInstrumentService.getSmaList(instrument, priceType, granularity, numOfSMAs, numOfTimePeriods));
    }
}
