package pl.jdev.oanda_rest_client.config;

import lombok.extern.java.Log;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Random;

@Log(topic = "test")
@Configuration
//@ConfigurationProperties("classpath:json.wrapper.alias.yml")
public class JsonWrapperBeanProvider {
//    //    @Value("${Account.singular}")
//    private String accountSingular;
//    //    @Value("${Account.plural}")
//    private String accountPlural;
//    //    @Value("${Instrument.singular}")
//    private String instrumentSingular;
//    //    @Value("${Instrument.plural}")
//    private String instrumentPlural;
//    //    @Value("${Order.singular}")
//    private String orderSingular;
//    //    @Value("${Order.plural}")
//    private String orderPlural;
//@Value("${Account.singular}") final String accountSingular,
//    @Value("${Account.plural}") final String accountPlural,
//    @Value("${Order.singular}") final String orderSingular,
//    @Value("${Order.plural}") final String orderPlural

    @PostConstruct
    public void generateJsonWrapperBeans() {
        log.info("post construct");
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition wrapperParentBean = BeanDefinitionBuilder.genericBeanDefinition("JsonWrapper")
                .setAbstract(true)
                .getBeanDefinition();

        Map<String, String> values = Map.of("AccountListWrapper", "accounts", "AccountWrapper", "account", "OrderListWrapper", "orders", "OrderWrapper", "order");
        values.entrySet().stream()
                .map(entry -> BeanDefinitionBuilder
                        .genericBeanDefinition(entry.getKey())
                        .setParentName("JsonWrapper")
                        .addPropertyValue(entry.getValue(), null))
                .map(BeanDefinitionBuilder::getBeanDefinition)
                .forEach(beanDefinition -> beanFactory.registerBeanDefinition(beanDefinition
                                .getBeanClassName(),
                        beanDefinition));
    }
}
