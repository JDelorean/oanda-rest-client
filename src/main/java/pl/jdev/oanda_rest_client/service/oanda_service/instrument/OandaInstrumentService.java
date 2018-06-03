package pl.jdev.oanda_rest_client.service.oanda_service.instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.domain.candle.Candlestick;
import pl.jdev.oanda_rest_client.domain.candle.CandlestickGranularity;
import pl.jdev.oanda_rest_client.domain.candle.CandlestickPriceType;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;
import pl.jdev.oanda_rest_client.service.IOandaService;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.OandaRequestHeaderEnrichmentInterceptor;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.RestLoggingInterceptor;

import java.time.DayOfWeek;

public class OandaInstrumentService implements IOandaService<Instrument> {

    private RestTemplate restTemplate;
    @Autowired
    private OandaRequestHeaderEnrichmentInterceptor oandaRequestHeaderEnrichmentInterceptor;
    @Autowired
    private RestLoggingInterceptor restLoggingInterceptor;
    @Autowired
    private OandaInstrumentUrl oandaInstrumentUrl;

    public OandaInstrumentService(RestTemplateBuilder restTemplateBuilder,
                                  OandaRequestHeaderEnrichmentInterceptor oandaRequestHeaderEnrichmentInterceptor, RestLoggingInterceptor restLoggingInterceptor) {
        this.oandaRequestHeaderEnrichmentInterceptor = oandaRequestHeaderEnrichmentInterceptor;
        this.restLoggingInterceptor = restLoggingInterceptor;
        this.restTemplate = restTemplateBuilder.additionalInterceptors(this.oandaRequestHeaderEnrichmentInterceptor, this.restLoggingInterceptor)
                .build();
    }

    public Candlestick[] getCandlestickDataForInstrument(String instrument, CandlestickPriceType price,
                                                         CandlestickGranularity granularity, int count, String from, String to, boolean smooth, boolean includeFirst,
                                                         int dailyAlignment, String alignmentType, DayOfWeek weeklyAlignment) {
        // TODO impl
        return null;
    }

}
