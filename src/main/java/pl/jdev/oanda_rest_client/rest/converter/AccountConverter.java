package pl.jdev.oanda_rest_client.rest.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.domain.account.AccountDTO;

import java.io.IOException;


@Component
public class AccountConverter implements EntityDTOConverter<Account, AccountDTO> {
    @Autowired
    private ModelMapper modelMapper;

    PropertyMap<AccountDTO, Account> dtoToEntityMap = new PropertyMap<AccountDTO, Account>() {
        @Override
        protected void configure() {
//            map().setAccountId(source.getAccountId());
        }
    };

    PropertyMap<Account, AccountDTO> entityToDtoMap = new PropertyMap<Account, AccountDTO>() {
        @Override
        protected void configure() {
//            map().setAccountId(source.getAccountId());
        }
    };

    @Override
    public Account createFrom(AccountDTO dto) {
        return modelMapper.map(dto, Account.class);
    }

    public Account createFrom(String string) throws IOException {
        modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.CAMEL_CASE);
        JsonNode accountNode = new ObjectMapper().readTree(string).get("object");
        AccountDTO accountDTO = modelMapper.map(accountNode, AccountDTO.class);
        return createFrom(accountDTO);
    }

    @Override
    public AccountDTO createFrom(Account entity) {
        return modelMapper.map(entity, AccountDTO.class);
    }

    @Override
    public Account updateEntity(Account entity, AccountDTO dto) {
        return null;
    }

}
