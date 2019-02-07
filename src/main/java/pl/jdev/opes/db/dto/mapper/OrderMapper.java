package pl.jdev.opes.db.dto.mapper;

import org.modelmapper.AbstractProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes.db.dto.OrderDto;
import pl.jdev.opes.service.AccountService;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.domain.order.Order;

@Component
public class OrderMapper extends AbstractMapper<Order, OrderDto> {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private AccountService accountService;

    @Override
    public OrderDto convertToDto(Order order) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        Provider<Account> accountProvider = new AbstractProvider<Account>() {
            @Override
            protected Account get() {
                return accountService.getAccount(order.getAccountId());
            }
        };
        mapper.createTypeMap(Order.class, OrderDto.class)
                .addMappings(typeMap -> {
                    typeMap.with(accountProvider).map(Order::getAccountId, OrderDto::setAccount);
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
