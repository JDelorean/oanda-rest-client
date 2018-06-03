package pl.jdev.oanda_rest_client.service.oanda_service.position;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import pl.jdev.oanda_rest_client.domain.position.Position;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.OandaRequestHeaderEnrichmentInterceptor;

public class OandaPositionRestConnector {

	private RestTemplate restTemplate;
	@Autowired
	private OandaRequestHeaderEnrichmentInterceptor oandaRequestHeaderEnrichmentInterceptor;
	@Autowired
	private OandaPositionUrl oandaPositionUrl;

	public OandaPositionRestConnector(RestTemplateBuilder restTemplateBuilder,
			OandaRequestHeaderEnrichmentInterceptor oandaRequestHeaderEnrichmentInterceptor) {
		this.oandaRequestHeaderEnrichmentInterceptor = oandaRequestHeaderEnrichmentInterceptor;
		this.restTemplate = restTemplateBuilder.additionalInterceptors(this.oandaRequestHeaderEnrichmentInterceptor)
				.build();
	}

	public List<Position> getListOfPositions(final String accountId) {
		return Arrays.asList(restTemplate.getForObject(oandaPositionUrl.getList(), Position[].class, accountId));
	}

	public List<Position> getListOfOpenPositions(final String accountId) {
		return Arrays.asList(restTemplate.getForObject(oandaPositionUrl.getOpen(), Position[].class, accountId));
	}

	public Position getPositionForInstrument(final String accountId, final String instrument) {
		return restTemplate.getForObject(oandaPositionUrl.getOpen(), Position.class, accountId, instrument);
	}

	public void closePositionForInstrument(final String accountId, final String instrument) {
		// TODO impl
	}
}
