package pl.jdev.oanda_rest_client.service.oanda_service.instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;

public class OandaInstrumentService extends AbstractOandaService<Instrument> {

    @Autowired
    public OandaInstrumentService(MultiValueMap<String, String> headers,
                                  RestTemplate restTemplate,
                                  Urls urls) {
        super(headers, restTemplate, urls);
    }

    public Instrument[] getAll() {
        return new Instrument[0];
    }

    public Instrument get(String id) {
        return null;
    }
}
