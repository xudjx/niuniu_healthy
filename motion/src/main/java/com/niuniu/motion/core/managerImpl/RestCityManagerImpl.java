package com.niuniu.motion.core.managerImpl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.common.util.SingletonThreadPool;
import com.niuniu.motion.config.AuthConfig;
import com.niuniu.motion.core.manager.RestCityManager;
import com.niuniu.motion.dto.ResultDTO;
import com.niuniu.motion.dto.weather.*;
import com.niuniu.motion.model.dao.CityDAO;
import com.niuniu.motion.model.dao.CityWeatherDAO;
import com.niuniu.motion.model.query.CityDO;
import com.niuniu.motion.model.query.CityWeatherDO;
import com.niuniu.motion.rest.RestTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Component
public class RestCityManagerImpl implements RestCityManager {

    private static final Logger logger = LoggerFactory.getLogger(RestCityManagerImpl.class);

    @Autowired
    CityDAO cityDAO;
    @Autowired
    AuthConfig authConfig;
    @Autowired
    Environment environment;
    @Autowired
    CityWeatherDAO cityWeatherDAO;

    @Override
    public List<CityDO> saveProvinceDTO(List<ProvinceDTO> provinceDTOList) {
        if (provinceDTOList == null) {
            return null;
        }
        List<CityDO> cityDOList = new ArrayList<>();
        for (ProvinceDTO provinceDTO : provinceDTOList) {
            String province = provinceDTO.province;
            for (CityDTO cityDTO : provinceDTO.city) {
                String city = cityDTO.city;
                for (DistrictDTO district : cityDTO.district) {
                    CityDO cityDO = new CityDO();
                    cityDO.setProvince(province);
                    cityDO.setCity(city);
                    cityDO.setDistrict(district.district);
                    cityDOList.add(cityDO);
                }
            }
        }
        logger.info("The cities size:{}", cityDOList.size());
        long start = System.currentTimeMillis();
        for (CityDO cityDO : cityDOList) {
            cityDAO.save(cityDO);
        }
        logger.info("Save city time: {}", System.currentTimeMillis()- start);
        return cityDOList;
    }

    @Override
    public ResultDTO batchCitiesWeather() throws NiuSvrException {
        Iterable<CityDO> cityDOIterable = cityDAO.findAll();
        ExecutorService executorService = SingletonThreadPool.getExecutorService();
        Gson gson = new Gson();
        for (CityDO cityDO : cityDOIterable) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    RestTemplate restTemplate = RestTemplateFactory.getInstance();
                    String weatherUrl = String.format(environment.getProperty("rest.weather.query"), authConfig.getMobAppKey(), cityDO.getProvince(), cityDO.getDistrict());
                    String weatherJson = restTemplate.getForObject(weatherUrl, String.class);
                    Type weatherJsonType = new TypeToken<RestResultDTO<CityWeatherDTO>>() {
                    }.getType();
                    RestResultDTO<CityWeatherDTO> restResultDTO = gson.fromJson(weatherJson, weatherJsonType);
                    if (restResultDTO.retCode != HttpStatus.OK.value()) {
                        logger.error("The city {} query weather error: {}", gson.toJson(cityDO), restResultDTO);
                        return;
                    }
                    List<CityWeatherDTO> weathers = restResultDTO.getResult();
                    if (weathers != null && weathers.size() > 0) {
                        CityWeatherDTO cityWeatherDTO = weathers.get(0);
                        CityWeatherDO weatherDO = new CityWeatherDO();
                        BeanUtils.copyProperties(cityWeatherDTO, weatherDO);
                        weatherDO.setCityId(cityDO.getCityId());
                        cityWeatherDAO.save(weatherDO);
                    }
                }
            });
        }
        return new ResultDTO(ResultDTO.SUCCESS);
    }
}
