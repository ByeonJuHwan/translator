package com.byeon.translator.config;

import com.byeon.translator.service.translate.DeeplFeignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeeplInterceptorConfig {

    @Bean
    public DeeplFeignInterceptor feignInterceptor() {
        return new DeeplFeignInterceptor();
    }
}
