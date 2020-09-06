package pl.jdev.opes.db.dto.mapper;

import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes.db.dto.CandlestickCompositeKey;
import pl.jdev.opes.db.dto.CandlestickDto;
import pl.jdev.opes.db.repo.InstrumentRepository;
import pl.jdev.opes_commons.db.AbstractMapper;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickData;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;

import java.time.Instant;
import java.util.Date;


@Component
public class CandlestickMapper extends AbstractMapper<Candlestick, CandlestickDto> {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private InstrumentRepository instrumentRepository;

    @Override
    public Candlestick convertToEntity(CandlestickDto candlestickDto) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        Condition isMidType = mappingCtx -> ((CandlestickDto) mappingCtx.getSource()).getType() == CandlestickPriceType.M;
        Condition isAskType = mappingCtx -> ((CandlestickDto) mappingCtx.getSource()).getType() == CandlestickPriceType.A;
        Condition isBidType = mappingCtx -> ((CandlestickDto) mappingCtx.getSource()).getType() == CandlestickPriceType.B;
        Converter<CandlestickDto, CandlestickData> toCandleData = obj -> new CandlestickData(
                obj.getSource().getOpen(),
                obj.getSource().getHigh(),
                obj.getSource().getLow(),
                obj.getSource().getClose());
        mapper.createTypeMap(CandlestickDto.class, Candlestick.class)
                .addMappings(typeMap -> {
                    typeMap.map(src -> src.getId().getInstrument().getName(), Candlestick::setInstrument);
                    typeMap.map(src -> src.getId().getGranularity(), Candlestick::setGranularity);
                    typeMap.map(src -> src.getId().getTime(), Candlestick::setTime);
                    typeMap.using(toCandleData).map(src -> src, Candlestick::setMid);
//                    typeMap.when(isMidType).map(src -> new CandlestickData(
//                                    src.getOpen(),
//                                    src.getHigh(),
//                                    src.getLow(),
//                                    src.getClose()),
//                            Candlestick::setMid);
//                    typeMap.when(isAskType).map(src -> new CandlestickData(
//                                    src.getOpen(),
//                                    src.getHigh(),
//                                    src.getLow(),
//                                    src.getClose()),
//                            Candlestick::setAsk);
//                    typeMap.when(isBidType).map(src -> new CandlestickData(
//                                    src.getOpen(),
//                                    src.getHigh(),
//                                    src.getLow(),
//                                    src.getClose()),
//                            Candlestick::setBid);
                });
        return mapper.map(candlestickDto, Candlestick.class);
    }

    @Override
    public CandlestickDto convertToDto(Candlestick entity) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.getConfiguration().setAmbiguityIgnored(true);
        Condition<Double, Double> isNotNull = mappingCtx -> mappingCtx.getSource() != null;
        Converter<Candlestick, CandlestickCompositeKey> toCompositeKey = object -> {
            Candlestick candle = object.getSource();
            return new CandlestickCompositeKey(
                    instrumentRepository.findByName(candle.getInstrument()).get(),
                    candle.getGranularity(),
                    Date.from(Instant.parse(candle.getTime()))
            );
        };
        mapper.createTypeMap(Candlestick.class, CandlestickDto.class)
                .addMappings(typeMap -> {
                    typeMap.using(toCompositeKey).map(src -> src, CandlestickDto::setId);
                    typeMap.when(isNotNull).map(src -> src.getMid().getO(), CandlestickDto::setOpen);
                    typeMap.when(isNotNull).map(src -> src.getMid().getH(), CandlestickDto::setHigh);
                    typeMap.when(isNotNull).map(src -> src.getMid().getL(), CandlestickDto::setLow);
                    typeMap.when(isNotNull).map(src -> src.getMid().getC(), CandlestickDto::setClose);
                    //TODO: this is stupid and overrides mid prices. for now use only mid.
//                    typeMap.when(isNotNull).map(src -> src.getAsk().getO(), CandlestickDto::setOpen);
//                    typeMap.when(isNotNull).map(src -> src.getAsk().getH(), CandlestickDto::setHigh);
//                    typeMap.when(isNotNull).map(src -> src.getAsk().getL(), CandlestickDto::setLow);
//                    typeMap.when(isNotNull).map(src -> src.getAsk().getC(), CandlestickDto::setClose);
//                    typeMap.when(isNotNull).map(src -> src.getBid().getO(), CandlestickDto::setOpen);
//                    typeMap.when(isNotNull).map(src -> src.getBid().getH(), CandlestickDto::setHigh);
//                    typeMap.when(isNotNull).map(src -> src.getBid().getL(), CandlestickDto::setLow);
//                    typeMap.when(isNotNull).map(src -> src.getBid().getC(), CandlestickDto::setClose);
                });
        return mapper.map(entity, CandlestickDto.class);
    }
}