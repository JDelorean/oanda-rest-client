package pl.jdev.oanda_rest_client.rest.json.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import pl.jdev.oanda_rest_client.config.JsonWrapperAlias;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;

@Data
public class JsonObjectWrapper<E extends AbstractEntity> {
    @JsonProperty()
    private E object;
    @Transient
    private JsonWrapperAlias jsonWrapperAlias;
//TODO: figure out dynamic json aliasing
}
