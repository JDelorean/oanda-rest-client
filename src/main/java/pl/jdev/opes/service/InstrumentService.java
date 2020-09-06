package pl.jdev.opes.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jdev.opes.db.dto.CandlestickCompositeKey;
import pl.jdev.opes.db.dto.CandlestickDto;
import pl.jdev.opes.db.dto.InstrumentDto;
import pl.jdev.opes.db.dto.mapper.CandlestickMapper;
import pl.jdev.opes.db.dto.mapper.InstrumentMapper;
import pl.jdev.opes.db.repo.CandlesRepository;
import pl.jdev.opes.db.repo.InstrumentRepository;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;
import pl.jdev.opes_commons.domain.instrument.Instrument;
import pl.jdev.opes_commons.rest.HttpHeaders;
import pl.jdev.opes_commons.rest.client.nuntius.NuntiusClient;
import pl.jdev.opes_commons.rest.exception.NotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static pl.jdev.opes.helper.DateTimeHelper.formatToDate;
import static pl.jdev.opes.helper.DateTimeHelper.formatToString;
import static pl.jdev.opes_commons.rest.HttpHeaders.REQUEST_TYPE;
import static pl.jdev.opes_commons.rest.message.request.RequestType.CANDLES;

@Service
public class InstrumentService {
    private SimpleDateFormat dateFormat;
    private InstrumentRepository instrumentRepository;
    private CandlesRepository candlesRepository;
    private NuntiusClient nuntiusClient;
    private InstrumentMapper instrumentMapper;
    private CandlestickMapper candlestickMapper;

    public InstrumentService(SimpleDateFormat dateFormat, InstrumentRepository instrumentRepository, CandlesRepository candlesRepository, NuntiusClient nuntiusClient, InstrumentMapper instrumentMapper, CandlestickMapper candlestickMapper) {
        this.dateFormat = dateFormat;
        this.instrumentRepository = instrumentRepository;
        this.candlesRepository = candlesRepository;
        this.nuntiusClient = nuntiusClient;
        this.instrumentMapper = instrumentMapper;
        this.candlestickMapper = candlestickMapper;
    }

    public Instrument getInstrument(String name) throws NotFoundException {
        InstrumentDto dto = instrumentRepository.findByName(name).orElseThrow(NotFoundException::new);
        return instrumentMapper.convertToEntity(dto);
    }

    public List<Instrument> getTrackedInstruments() {
        return instrumentRepository.findByIsTracked(true)
                .stream()
                .map(instrumentMapper::convertToEntity)
                .collect(toList());
    }

    @CacheEvict(value = "trackedInstruments", allEntries = true)
    @Transactional
    public void trackInstrument(String name, boolean shouldTrack) throws NotFoundException {
        InstrumentDto dto = instrumentRepository.findByName(name).orElseThrow(NotFoundException::new);
        instrumentRepository.setIsTrackedFlag(dto.getId(), shouldTrack);
    }

    @Async
    @Transactional
    public void insertCandles(Candlestick candle) {
        candlesRepository.save(candlestickMapper.convertToDto(candle));
    }

    @Async
    @Transactional
    public void insertCandles(List<Candlestick> candles) {
        List<CandlestickDto> dtos = candles
                .stream()
                .map(candlestickMapper::convertToDto)
                .collect(toList());
        candlesRepository.saveAll(dtos);
    }

    public Candlestick getCandle(String instrument, CandlestickGranularity granularity, String time) throws NotFoundException, ParseException {
        InstrumentDto instrumentDto = instrumentRepository.findByName(instrument).orElseThrow(NotFoundException::new);
        Date date = formatToDate(time);
        CandlestickDto candlestickDto = candlesRepository.findById(new CandlestickCompositeKey(instrumentDto, granularity, date)).orElseThrow(NotFoundException::new);
        return candlestickMapper.convertToEntity(candlestickDto);
    }

    public List<Candlestick> getInstrumentCandles(String instrument,
                                                  CandlestickPriceType type,
                                                  CandlestickGranularity granularity,
                                                  int count)
            throws NotFoundException {
        TemporalUnit timeUnit = granularity.getUnit();
        Instant periodEnd = Instant.now().truncatedTo(timeUnit);
        int startOffset = granularity.getMillis() * count;
        Instant periodStart = periodEnd.minusMillis(startOffset).truncatedTo(timeUnit);
        return doGetInstrumentCandles(instrument, type, granularity, periodStart, periodEnd);
    }

    public List<Candlestick> getInstrumentCandles(String instrument,
                                                  CandlestickPriceType type,
                                                  CandlestickGranularity granularity,
                                                  String from,
                                                  String to)
            throws ParseException, NotFoundException {
        TemporalUnit timeUnit = granularity.getUnit();
        Instant periodStart = dateFormat.parse(from).toInstant().truncatedTo(timeUnit);
        Instant periodEnd = Instant.now().truncatedTo(timeUnit);
        if (to != null) periodEnd = dateFormat.parse(to).toInstant().truncatedTo(timeUnit);
        return doGetInstrumentCandles(instrument, type, granularity, periodStart, periodEnd);
    }

    private List<Candlestick> doGetInstrumentCandles(String instrument,
                                                     CandlestickPriceType type,
                                                     CandlestickGranularity granularity,
                                                     Instant from,
                                                     Instant to)
            throws NotFoundException {
        List<Instant> candleDates = new ArrayList<>();
        candleDates.add(to);
        Instant latest = to;
        long count = Duration.between(from, to).toMillis() / granularity.getMillis();
        for (int i = 0; i < count; i++) {
            Instant prevTS = latest.minusMillis(granularity.getMillis());
            candleDates.add(prevTS);
            latest = prevTS;
        }
        InstrumentDto dto = instrumentRepository.findByName(instrument).orElseThrow(NotFoundException::new);
        List<CandlestickCompositeKey> keys = candleDates.stream()
                .map(Date::from)
                .map(date -> new CandlestickCompositeKey(dto, granularity, date))
                .collect(toList());
        List<Candlestick> candles = candlesRepository.findAllById(keys)
                .stream()
                .map(candlestickMapper::convertToEntity)
                .collect(toList());
        if (candles.size() != count) {
            candles = getCandlesFromExternal(instrument, type, granularity, from, to);
            insertCandles(candles);
        }
        return candles;
    }

    public List<Candlestick> getCandlesFromExternal(String instrument, CandlestickPriceType type, CandlestickGranularity granularity, Instant from, Instant to) {
        Message msg = MessageBuilder
                .withPayload("")
                .setHeader(REQUEST_TYPE, CANDLES)
                .setHeader(CONTENT_TYPE, APPLICATION_JSON)
                .setHeader(ACCEPT, APPLICATION_JSON)
                .build();
        HttpHeaders headers = new HttpHeaders();
        msg.getHeaders().forEach((key, value) -> headers.set(key, value.toString()));
        String fromDate = formatToString(from);
        String toDate = formatToString(to);
        String url = UriComponentsBuilder.newInstance()
                .path("/instruments/{instrument}/candles")
                .queryParam("priceType", type)
                .queryParam("granularity", granularity)
                .queryParam("from", fromDate)
                .queryParam("to", toDate)
                .buildAndExpand(instrument)
                .toString();
        return Arrays.stream(
                (Candlestick[]) nuntiusClient.getAccount(null).getBody())
                .collect(toList());
    }

//                this.restTemplate
//                .exchange(UriComponentsBuilder.newInstance()
//                                .scheme(ReferenceUriSchemesSupported.HTTP.toString())
//                                .host(oandaHost)
//                                .path("/v1/instruments/{instrument}/candles")
//                                .queryParam("priceType", type)
//                                .queryParam("granularity", granularity)
//                                .queryParam("from", fromDate)
//                                .queryParam("to", toDate)
//                                .buildAndExpand(instrument)
//                                .toString(),
//                        GET,
//                        new HttpEntity<>(null, headers),
//                        Candlestick[].class)
//                .getBody()
}
