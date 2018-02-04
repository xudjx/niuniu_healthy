package com.niuniu.motion.rest;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateFactory {

    private static RestTemplate restTemplate;

    public static RestTemplate getInstance() {
        if (restTemplate == null) {
            synchronized (RestTemplateFactory.class) {
                if(restTemplate == null) {
                    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
                    factory.setReadTimeout(5000);
                    factory.setConnectTimeout(5000);
                    restTemplate = new RestTemplate(factory);
                }
            }
        }
        return restTemplate;
    }
}
