package com.niuniu.motion.server.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.config.AuthConfig;
import com.niuniu.motion.core.manager.RestCityManager;
import com.niuniu.motion.dto.ResultDTO;
import com.niuniu.motion.dto.weather.*;
import com.niuniu.motion.model.query.CityDO;
import com.niuniu.motion.rest.RestTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@RestController
public class MyRestController {

    private static final Logger logger = LoggerFactory.getLogger(MyRestController.class);

    @Autowired
    AuthConfig authConfig;
    @Autowired
    Environment environment;
    @Autowired
    RestCityManager restManager;

    @RequestMapping(value = "/rest/weather", method = RequestMethod.GET)
    public Object searchWeather(@RequestParam String city,
                                      @RequestParam String province) throws NiuSvrException {
        RestTemplate restTemplate = RestTemplateFactory.getInstance();
        String weatherUrl = String.format(environment.getProperty("rest.weather.query"), authConfig.getMobAppKey(), province, city);
        String weather = restTemplate.getForObject(weatherUrl, String.class);
        return weather;
    }

    @RequestMapping(value = "/rest/weather/citys", method = RequestMethod.GET)
    public Object getCity() throws NiuSvrException {
        RestTemplate restTemplate = RestTemplateFactory.getInstance();
        String weatherCityUrl = String.format(environment.getProperty("rest.weather.cities"), authConfig.getMobAppKey());
        String citys = restTemplate.getForObject(weatherCityUrl, String.class);
        return citys;
    }

    /**
     * 拉取所有的城市信息
     */
    @RequestMapping(value = "/rest/weather/cities/batch", method = RequestMethod.GET)
    public List<CityDO> batchWeatherCities() throws NiuSvrException {
        RestTemplate restTemplate = RestTemplateFactory.getInstance();
        String weatherCityUrl = String.format(environment.getProperty("rest.weather.cities"), authConfig.getMobAppKey());
        String citysJson = restTemplate.getForObject(weatherCityUrl, String.class);
        Gson gson = new Gson();
        Type jsonType = new TypeToken<RestResultDTO<ProvinceDTO>>() {}.getType();
        RestResultDTO<ProvinceDTO> weatherCitysDTO = gson.fromJson(citysJson, jsonType);
        List<CityDO> cityDOList = restManager.saveProvinceDTO(weatherCitysDTO.result);
        return cityDOList;
    }

    /**
     * 拉取数据库中所存储的城市信息对应的天气信息
     */
    @RequestMapping(value = "/rest/weather/batch", method = RequestMethod.GET)
    public ResultDTO batchCityWeather() throws NiuSvrException {
        try {
            restManager.batchCitiesWeather();
        } catch (NiuSvrException e) {
            return new ResultDTO(ResultDTO.FAILED, e);
        }
        return new ResultDTO(ResultDTO.SUCCESS);
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
