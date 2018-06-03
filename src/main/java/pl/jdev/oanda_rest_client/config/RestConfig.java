package pl.jdev.oanda_rest_client.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class RestConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Autowired
//    LastTransactionInterceptor lastTransactionInterceptor;
////    @Autowired
////    RestLoggingInterceptor restLoggingInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(lastTransactionInterceptor);
////        registry.addInterceptor(restLoggingInterceptor);
//    }
}