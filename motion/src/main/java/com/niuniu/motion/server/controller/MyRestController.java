package com.niuniu.motion.server.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.config.AuthConfig;
import com.niuniu.motion.core.manager.RestCityManager;
import com.niuniu.motion.dto.weather.*;
import com.niuniu.motion.model.query.CityDO;
import com.niuniu.motion.rest.RestTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    RestCityManager restManager;

    @RequestMapping(value = "/rest/weather", method = RequestMethod.GET)
    public Object searchWeather(@RequestParam String city,
                                      @RequestParam String province) throws NiuSvrException {
        RestTemplate restTemplate = RestTemplateFactory.getInstance();
        String weatherUrl = String.format("http://apicloud.mob.com/v1/weather/query?key=%s&city=%s&province=%s", authConfig.getMobAppKey(), city, province);
        String weather = restTemplate.getForObject(weatherUrl, String.class);
        return weather;
    }

    @RequestMapping(value = "/rest/weather/citys", method = RequestMethod.GET)
    public Object getCity() throws NiuSvrException {
        RestTemplate restTemplate = RestTemplateFactory.getInstance();
        String weatherCityUrl = String.format("http://apicloud.mob.com/v1/weather/citys?key=%s", authConfig.getMobAppKey());
        String citys = restTemplate.getForObject(weatherCityUrl, String.class);
        return citys;
    }

    @RequestMapping(value = "/rest/weather/batch", method = RequestMethod.GET)
    public List<CityDO> batchCityWeather() throws NiuSvrException {
        RestTemplate restTemplate = RestTemplateFactory.getInstance();
        String weatherCityUrl = String.format("http://apicloud.mob.com/v1/weather/citys?key=%s", authConfig.getMobAppKey());
        String citysJson = restTemplate.getForObject(weatherCityUrl, String.class);
        Gson gson = new Gson();
        Type jsonType = new TypeToken<RestResultDTO<ProvinceDTO>>() {}.getType();
        RestResultDTO<ProvinceDTO> weatherCitysDTO = gson.fromJson(citysJson, jsonType);
        List<CityDO> cityDOList = restManager.saveProinveDTO(weatherCitysDTO.result);

        /**
        if (weatherCitysDTO != null && weatherCitysDTO.result != null) {
            for (int i = 0; i < 1 && i < weatherCitysDTO.result.size(); i++) {
                ProvinceDTO provinceDTO = weatherCitysDTO.getResult().get(i);
                List<HashMap> cityMap = new ArrayList<>();
                for (CityDTO cityDTO : provinceDTO.city) {
                    HashMap map = new HashMap();
                    map.put("province", provinceDTO.province);
                    map.put("city", cityDTO.city);
                    cityMap.add(map);
                    for (DistrictDTO districtDTO : cityDTO.district) {
                        HashMap districtMap = new HashMap();
                        districtMap.put("province", provinceDTO.province);
                        districtMap.put("city", districtDTO.district);
                        cityMap.add(districtMap);
                    }
                }
                logger.info("所有的城市组：{}", cityMap);

                // 获取省市的天气信息
                for (int j = 0; j < 1 && j < cityMap.size(); j++) {
                    HashMap weatherParamMap = cityMap.get(j);
                    String province = (String) weatherParamMap.get("province");
                    String city = (String) weatherParamMap.get("city");
                    String weatherUrl = String.format("http://apicloud.mob.com/v1/weather/query?key=%s&city=%s&province=%s",
                            authConfig.getMobAppKey(), city, province);
                    String weatherJson = restTemplate.getForObject(weatherUrl, String.class);
                    Type weatherJsonType = new TypeToken<RestResultDTO<CityWeatherDTO>>() {
                    }.getType();
                    RestResultDTO<CityWeatherDTO> weatherDTO = gson.fromJson(weatherJson, weatherJsonType);
                    logger.info("{}{}的天气信息为{}", province, city, weatherDTO);
                }
            }
        }
        **/
        return cityDOList;
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
