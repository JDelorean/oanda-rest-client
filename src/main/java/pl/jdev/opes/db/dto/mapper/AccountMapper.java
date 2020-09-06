package pl.jdev.opes.db.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes.db.dto.AccountDto;
import pl.jdev.opes_commons.db.AbstractMapper;
import pl.jdev.opes_commons.domain.account.Account;

@Component
public class AccountMapper extends AbstractMapper<Account, AccountDto> {
    @Autowired
    private WebApplicationContext context;

    @Override
    public AccountDto convertToDto(Account account) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
//        Provider<Account> accountProvider = new AbstractProvider<Account>() {
//            @Override
//            protected Account get() {
//                return accountService.getAccount(order.getAccountId());
//            }
//        };
//        mapper.createTypeMap(Account.class, AccountDto.class)
//                .addMappings(typeMap -> {
//                    typeMap.with(accountProvider).map(Order::getAccountId, OrderDto::setAccount);
//                });
        return mapper.map(account, AccountDto.class);
    }

    @Override
    public Account convertToEntity(AccountDto dto) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
//        mapper.createTypeMap(OrderDto.class, Order.class)
//                .addMappings(typeMap -> {
//                    typeMap.map(src -> src.getAccount().getId(), Order::setAccountId);
//                });
        return mapper.map(dto, Account.class);
    }
}