package pl.jdev.opes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jdev.opes.db.dto.CandlestickDto;
import pl.jdev.opes.db.dto.InstrumentDto;
import pl.jdev.opes.db.dto.mapper.CandlestickMapper;
import pl.jdev.opes.db.dto.mapper.InstrumentMapper;
import pl.jdev.opes.db.repo.CandlesRepository;
import pl.jdev.opes.db.repo.InstrumentRepository;
import pl.jdev.opes.rest.exception.NotFoundException;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;
import pl.jdev.opes_commons.domain.instrument.Instrument;
import pl.jdev.opes_commons.rest.HttpHeaders;
import pl.jdev.opes_commons.rest.IntegrationClient;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static pl.jdev.opes_commons.rest.HttpHeaders.REQUEST_TYPE;
import static pl.jdev.opes_commons.rest.message.request.RequestType.CANDLES;

@Service
public class InstrumentService {
    @Autowired
    private InstrumentRepository instrumentRepository;
    @Autowired
    private CandlesRepository candlesRepository;
    @Autowired
    private IntegrationClient intC;
    @Value("${opes.oanda.host}")
    private String oandaHost;
    @Autowired
    private InstrumentMapper instrumentMapper;
    @Autowired
    private CandlestickMapper candlestickMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    public Instrument getInstrument(String name) throws NotFoundException {
        InstrumentDto dto = instrumentRepository.findByName(name).orElseThrow(NotFoundException::new);
        return instrumentMapper.convertToEntity(dto);
    }

    @CacheEvict(value = "trackedInstruments", allEntries = true)
    @Transactional
    public void trackInstrument(String name, boolean shouldTrack) throws NotFoundException {
        InstrumentDto dto = instrumentRepository.findByName(name).orElseThrow(NotFoundException::new);
        instrumentRepository.setIsTrackedFlag(dto.getId(), shouldTrack);
    }

    @Async
    @Transactional
    private void insertCandles(List<Candlestick> candles) {
        List<CandlestickDto> dtos = candles
                .stream()
                .map(candlestickMapper::convertToDto)
                .peek(System.out::println)
                .collect(Collectors.toList());
        candlesRepository.saveAll(dtos);
    }

    public List<Candlestick> getInstrumentCandles(String instrument,
                                                  CandlestickPriceType type,
                                                  CandlestickGranularity granularity,
                                                  int count,
                                                  boolean fromSource)
            throws NotFoundException {
        Message msg = MessageBuilder
                .withPayload("")
                .setHeader(REQUEST_TYPE, CANDLES)
                .setHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8)
                .setHeader(ACCEPT, APPLICATION_JSON_UTF8)
                .build();
        HttpHeaders headers = new HttpHeaders();
        msg.getHeaders().forEach((key, value) -> headers.set(key, value.toString()));
        List<Candlestick> candles = Arrays.stream(this.restTemplate
                .exchange(UriComponentsBuilder.newInstance()
                                .scheme(ReferenceUriSchemesSupported.HTTP.toString())
                                .host(oandaHost)
                                .path("/v1/instruments/{instrument}/candles")
                                .queryParam("priceType", type)
                                .queryParam("granularity", granularity)
                                .queryParam("count", count)
                                .buildAndExpand(instrument)
                                .toString(),
                        GET,
                        new HttpEntity<>(null, headers),
                        Candlestick[].class)
                .getBody())
                .collect(Collectors.toList());
        insertCandles(candles);
        return candles;
    }

    @Scheduled(fixedRateString = "${opes.poll.instrument}")
    void updateTrackedCandlesFromExternal() {
        List<InstrumentDto> tracked = instrumentRepository.findByIsTracked(true);
        tracked.parallelStream()
                .map(InstrumentDto::getName)
                .forEach(instrument -> getInstrumentCandles(instrument,
                        CandlestickPriceType.M,
                        CandlestickGranularity.M1,
                        2,
                        true));
    }
}
