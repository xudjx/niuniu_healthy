package com.niuniu.motion.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class NiuFilters {

    @Bean
    public Filter tokenFilter() {
        return new TokenFilter();
    }
}
