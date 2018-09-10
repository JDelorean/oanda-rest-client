package pl.jdev.oanda_rest_client.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:oanda.urls")
@Getter
public class Urls {
    @Value("${oanda.url.account.all}")
    public String ACCOUNT_LIST_URL;
    @Value("${oanda.url.account.single}")
    public String SINGLE_ACCOUNT_URL;
    @Value("${oanda.url.account.summary}")
    public String ACCOUNT_SUMMARY_URL;
    @Value("${oanda.url.account.instruments}")
    public String ACCOUNT_INSTRUMENTS_URL;
    @Value("${oanda.url.account.config}")
    public String ACCOUNT_CONFIG_URL;
    @Value("${oanda.url.account.changes}")
    public String ACCOUNT_CHANGES_URL;

    @Value("${oanda.url.position.list}")
    public String POSITION_LIST_URL;
    @Value("${oanda.url.position.single}")
    public String SINGLE_POSITION_URL;
    @Value("${oanda.url.position.single_close}")
    public String CLOSE_POSITION_URL;
    @Value("${oanda.url.position.open}")
    public String OPEN_POSITION_LIST_URL;

    @Value("${oanda.url.transaction.list}")
    public String TRANSACTION_LIST_URL;
    @Value("${oanda.url.transaction.single}")
    public String SINGLE_TRANSACTION_URL;
    @Value("${oanda.url.transaction.id_range}")
    public String TRANSACTION_ID_RANGE_URL;
    @Value("${oanda.url.transaction.since_id}")
    public String TRANSACTION_SINCE_ID_URL;
    @Value("${oanda.url.transaction.stream}")
    public String TRANSACTION_STREAM_URL;

    //Instrument

    //Pricing

    @Value("${oanda.url.account.order.list}")
    public String ORDER_LIST_URL;
    @Value("${oanda.url.account.order.pending}")
    public String PENDING_ORDER_LIST_URL;
    @Value("${oanda.url.account.order.single}")
    public String SINGLE_ORDER_URL;
    @Value("${oanda.url.account.order.single_cancel}")
    public String CANCEL_ORDER_URL;
    @Value("${oanda.url.account.order.single_client_extensions}")
    public String ORDER_CLIENT_EXT_URL;

}
