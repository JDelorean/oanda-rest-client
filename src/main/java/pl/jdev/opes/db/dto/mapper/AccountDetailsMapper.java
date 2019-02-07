package pl.jdev.opes.db.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes.db.dto.AccountDetailsDto;
import pl.jdev.opes_commons.domain.account.Account;

@Component
public class AccountDetailsMapper extends AbstractMapper<Account, AccountDetailsDto> {
    @Autowired
    private WebApplicationContext context;

    @Override
    public AccountDetailsDto convertToDto(Account entity) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.createTypeMap(Account.class, AccountDetailsDto.class)
                .addMappings(typeMap -> {
                    typeMap.skip(AccountDetailsDto::setId);
//                    typeMap.map(Account::getAccountId, AccountDetailsDto::setExtId);
//                    mapper.map(Account::getAlias, AccountDetailsDto::setAlias);
//                    mapper.map(Account::getCurrency, AccountDetailsDto::setCurrency);
//                    mapper.map(Account::getBalance, AccountDetailsDto::setBalance);
//                    mapper.map(Account::getCreatedByUserID, AccountDetailsDto::setCreatedByUserID);
//                    typeMap.map(Account::getCreatedTime, AccountDetailsDto::setExtCreatedTime);
//                    mapper.map(Account::getGuaranteedStopLossOrderMode, AccountDetailsDto::setGuaranteedStopLossOrderMode);
//                    mapper.map(Account::getPl, AccountDetailsDto::setPl);
//                    mapper.map(Account::getres, AccountDetailsDto::setPl);
                });
        return mapper.map(entity, AccountDetailsDto.class);
    }

    @Override
    public Account convertToEntity(AccountDetailsDto accountDetailsDto) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.createTypeMap(AccountDetailsDto.class, Account.class)
                .addMappings(typeMap -> {
//                    typeMap.map(AccountDetailsDto::getExtId, Account::setAccountId);
//                    typeMap.map(AccountDetailsDto::getExtCreatedTime, Account::setCreatedTime);
                });
        return mapper.map(accountDetailsDto, Account.class);
    }
}