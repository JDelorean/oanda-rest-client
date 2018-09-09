package pl.jdev.oanda_rest_client.service.oanda_service.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.position.Position;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;

public class OandaPositionService extends AbstractOandaService<Position> {
    @Autowired
    public OandaPositionService(MultiValueMap<String, String> headers,
                                RestTemplate restTemplate,
                                Urls urls) {
        super(headers, restTemplate, urls);
    }

    public Position[] getAll() {
        return new Position[0];
    }

    public Position get(String id) {
        return null;
    }
}
