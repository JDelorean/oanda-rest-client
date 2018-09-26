package pl.jdev.opes.rest.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes.domain.instrument.CandlestickGranularity;
import pl.jdev.opes.domain.instrument.CandlestickPriceType;
import pl.jdev.opes.rest.json.wrapper.JsonCandlestickListWrapper;
import pl.jdev.opes.service.oanda_service.instrument.OandaInstrumentService;

@RestController
@RequestMapping("/api/instruments/{instrument}/")
@Log
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
                oandaInstrumentService.getCandlestickList(instrument,
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
                oandaInstrumentService.getCandlestickList(instrument,
                        priceType,
                        granularity,
                        from,
                        to)
        );
    }
}
