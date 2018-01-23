package com.niuniu.motion.server.controller;

import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.config.AuthConfig;
import com.niuniu.motion.rest.RestTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class MyRestController {

    private static final Logger logger = LoggerFactory.getLogger(MyRestController.class);

    @Autowired
    AuthConfig authConfig;

    @RequestMapping(value = "/rest/weather", method = RequestMethod.GET)
    public Object searchWeather(@RequestParam String city,
                                      @RequestParam String province) throws NiuSvrException {
        RestTemplate restTemplate = RestTemplateFactory.getInstance();
        String weatherUrl = String.format("http://apicloud.mob.com/v1/weather/query?key=%s&city=%s&province=%s", authConfig.getMobAppKey(), city, province);
        String weather = restTemplate.getForObject(weatherUrl, String.class);
        return weather;
    }

    @RequestMapping(value = "/rest/environment", method = RequestMethod.GET)
    public Object searchEnvironment(@RequestParam String city,
                                  @RequestParam String province) throws NiuSvrException {
        RestTemplate restTemplate = RestTemplateFactory.getInstance();
        String environmentUrl = String.format("http://apicloud.mob.com/environment/query?key=%s&city=%s&province=%s", authConfig.getMobAppKey(), city, province);
        String environment = restTemplate.getForObject(environmentUrl, String.class);
        return environment;
    }

    @RequestMapping(value = "/rest/wx/article/category", method = RequestMethod.GET)
    public Object searchWxArticleCategory() throws NiuSvrException {
        RestTemplate restTemplate = RestTemplateFactory.getInstance();
        String wxUrl = String.format("http://apicloud.mob.com/wx/article/category/query?key=%s", authConfig.getMobAppKey());
        logger.info(wxUrl);
        String wxCategories = restTemplate.getForObject(wxUrl, String.class);
        logger.info(wxCategories);
        return wxCategories;
    }

    @RequestMapping(value = "/rest/wx/article/category/{cid}", method = RequestMethod.GET)
    public Object searchWxArticle(@PathVariable String cid) throws NiuSvrException {
        RestTemplate restTemplate = RestTemplateFactory.getInstance();
        String wxUrl = String.format("http://apicloud.mob.com/wx/article/search?key=%s&cid=%s", authConfig.getMobAppKey(), cid);
        logger.info(wxUrl);
        String wxCategories = restTemplate.getForObject(wxUrl, String.class);
        logger.info(wxCategories);
        return wxCategories;
    }
}
