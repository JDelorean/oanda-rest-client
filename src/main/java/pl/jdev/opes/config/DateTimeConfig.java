package pl.jdev.opes.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
@Log4j2
public class DateTimeConfig {

    @Value("${opes.date_time_format}")
    String dateFormat;

    @Bean
    SimpleDateFormat dateFormat() {
        log.info(String.format("Initializing date time format from pattern '%s'", dateFormat));
        return new SimpleDateFormat(dateFormat);
    }
}
