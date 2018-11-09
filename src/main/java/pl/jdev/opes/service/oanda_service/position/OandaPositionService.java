package pl.jdev.opes.service.oanda_service.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes.config.Urls;
import pl.jdev.opes.service.oanda_service.AbstractOandaService;
import pl.jdev.opes_commons.domain.position.Position;

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
