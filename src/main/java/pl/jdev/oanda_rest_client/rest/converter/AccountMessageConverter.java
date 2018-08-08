package pl.jdev.oanda_rest_client.rest.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import pl.jdev.oanda_rest_client.domain.account.Account;

import java.util.List;

public class AccountMessageConverter implements HttpMessageConverter<Account> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return null;
    }

    @Override
    public Account read(Class<? extends Account> clazz, HttpInputMessage inputMessage) {
        return null;
    }

    @Override
    public void write(Account account, MediaType contentType, HttpOutputMessage outputMessage) {

    }
}
