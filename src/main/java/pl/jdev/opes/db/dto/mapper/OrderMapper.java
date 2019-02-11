package pl.jdev.opes.db.dto.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes.db.dto.AccountDto;
import pl.jdev.opes.db.dto.OrderDto;
import pl.jdev.opes.db.repo.AccountRepository;
import pl.jdev.opes_commons.domain.order.Order;

import java.util.UUID;

@Component
public class OrderMapper extends AbstractMapper<Order, OrderDto> {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public OrderDto convertToDto(Order order) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        Converter<UUID, AccountDto> uuidToAccountDto = uuid -> accountRepository.findById(order.getAccountId()).get();
//        Provider<AccountDto> accountProvider = accountDto -> accountRepository.findById(order.getAccountId()).get();
        mapper.createTypeMap(Order.class, OrderDto.class)
                .addMappings(typeMap -> {
//                    typeMap.skip(OrderDto::setId);
                    typeMap.using(uuidToAccountDto).map(Order::getAccountId, OrderDto::setAccount);
                });
        return mapper.map(order, OrderDto.class);
    }

    @Override
    public Order convertToEntity(OrderDto orderDto) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.createTypeMap(OrderDto.class, Order.class)
                .addMappings(typeMap -> {
                    typeMap.map(src -> src.getAccount().getId(), Order::setAccountId);
                });
        return mapper.map(orderDto, Order.class);
    }
}