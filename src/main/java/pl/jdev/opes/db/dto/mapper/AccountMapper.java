package pl.jdev.opes.db.dto.mapper;

import pl.jdev.opes.db.dto.AccountDto;
import pl.jdev.opes_commons.domain.account.Account;

public class AccountMapper extends EntityDtoMapper<Account, AccountDto> {
    @Override
    public AccountDto convertToDto(Account entity) {
        modelMapper.createTypeMap(Account.class, AccountDto.class)
                .addMappings(mapper -> {
                    mapper.map(Account::getAccountId, AccountDto::setExtId);
                    mapper.map(Account::getCreatedTime, AccountDto::setExtCreatedTime);
                });
        return modelMapper.map(entity, AccountDto.class);
    }

    @Override
    public Account convertToEntity(AccountDto accountDto) {
        modelMapper.createTypeMap(AccountDto.class, Account.class)
                .addMappings(mapper -> {
                    mapper.map(AccountDto::getExtId, Account::setAccountId);
                    mapper.map(AccountDto::getExtCreatedTime, Account::setCreatedTime);
                });
        return modelMapper.map(accountDto, Account.class);
    }
}