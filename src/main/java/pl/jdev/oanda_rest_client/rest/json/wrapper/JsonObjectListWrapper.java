package pl.jdev.oanda_rest_client.rest.json.wrapper;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;

import java.util.List;

@Data
public class JsonObjectListWrapper<E extends AbstractEntity> {
    @JsonAlias
    private List<E> objectList;

}
