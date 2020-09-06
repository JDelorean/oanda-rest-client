package pl.jdev.opes.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

@Component
public
class DateTimeHelper {

    private static SimpleDateFormat df;

    @Autowired
    private SimpleDateFormat dateFormat;

    @PostConstruct
    private void init() {
        df = dateFormat;
    }

    public static Instant now() {
        return Instant.now();
    }

    public static Instant now(TemporalUnit truncatedTo) {
        return Instant.now().truncatedTo(truncatedTo);
    }

    public static Instant nowMinus(int unitAmt, ChronoUnit unit) {
        return now(unit).minus(unitAmt, unit);
    }

    public static String formatToString(Instant instant) {
        return df.format(Date.from(instant));
    }

    public static Date formatToDate(String time) throws ParseException {
        return df.parse(time);
    }

    public static Instant formatToInstant(String time) {
        return Instant.parse(time);
    }

}
