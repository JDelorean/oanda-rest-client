package pl.jdev.oanda_rest_client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class OandaRestClient {

	final static Logger LOGGER = LoggerFactory.getLogger(OandaRestClient.class);

	public static void main(String[] args) {
		LOGGER.info("Initiating oanda-rest-client...");
		SpringApplication.run(OandaRestClient.class, args);
	}

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "oanda-rest-client";
	}
}