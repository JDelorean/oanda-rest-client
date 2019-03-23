package pl.jdev.opes.db.dto.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes.db.dto.AccountDto;
import pl.jdev.opes.db.dto.InstrumentDto;
import pl.jdev.opes.db.dto.TradeDto;
import pl.jdev.opes.db.repo.AccountRepository;
import pl.jdev.opes.db.repo.InstrumentRepository;
import pl.jdev.opes_commons.domain.trade.Trade;

import java.util.UUID;

@Component
public class TradeMapper extends AbstractMapper<Trade, TradeDto> {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private InstrumentRepository instrumentRepository;

    @Override
    public Trade convertToEntity(TradeDto tradeDto) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.createTypeMap(TradeDto.class, Trade.class)
                .addMappings(typeMap -> {
                    typeMap.map(src -> src.getAccount().getId(), Trade::setAccountId);
                    typeMap.map(src -> src.getInstrument().getName(), Trade::setInstrument);
                });
        return mapper.map(tradeDto, Trade.class);
    }

    @Override
    public TradeDto convertToDto(Trade entity) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        Converter<UUID, AccountDto> uuidToAccountDto = uuid -> accountRepository.findById(entity.getAccountId()).get();
        Converter<String, InstrumentDto> nameToInstrumentDto = name -> instrumentRepository.findByName(entity.getInstrument()).get();
        mapper.createTypeMap(Trade.class, TradeDto.class)
                .addMappings(typeMap -> {
                    typeMap.using(uuidToAccountDto).map(Trade::getAccountId, TradeDto::setAccount);
                    typeMap.using(nameToInstrumentDto).map(Trade::getInstrument, TradeDto::setInstrument);
                });
        return mapper.map(entity, TradeDto.class);
    }
}
