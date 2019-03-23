package pl.jdev.opes.db.dto.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes.db.dto.AccountDto;
import pl.jdev.opes.db.dto.InstrumentDto;
import pl.jdev.opes.db.dto.OrderDto;
import pl.jdev.opes.db.repo.AccountRepository;
import pl.jdev.opes.db.repo.InstrumentRepository;
import pl.jdev.opes_commons.domain.order.Order;

import java.util.UUID;

@Component
public class OrderMapper extends AbstractMapper<Order, OrderDto> {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private InstrumentRepository instrumentRepository;

    @Override
    public OrderDto convertToDto(Order order) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        Converter<UUID, AccountDto> uuidToAccountDto = uuid -> accountRepository.findById(order.getAccountId()).get();
        Converter<String, InstrumentDto> nameToInstrumentDto = name -> instrumentRepository.findByName(order.getInstrument()).get();
        mapper.createTypeMap(Order.class, OrderDto.class)
                .addMappings(typeMap -> {
                    typeMap.using(uuidToAccountDto).map(Order::getAccountId, OrderDto::setAccount);
                    typeMap.using(nameToInstrumentDto).map(Order::getInstrument, OrderDto::setInstrument);
                });
        return mapper.map(order, OrderDto.class);
    }

    @Override
    public Order convertToEntity(OrderDto orderDto) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.createTypeMap(OrderDto.class, Order.class)
                .addMappings(typeMap -> {
                    typeMap.map(src -> src.getAccount().getId(), Order::setAccountId);
                    typeMap.map(src -> src.getInstrument().getName(), Order::setInstrument);
                });
        return mapper.map(orderDto, Order.class);
    }
}