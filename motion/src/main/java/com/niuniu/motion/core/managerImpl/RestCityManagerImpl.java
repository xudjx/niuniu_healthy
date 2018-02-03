package com.niuniu.motion.core.managerImpl;

import com.niuniu.motion.core.manager.RestCityManager;
import com.niuniu.motion.dto.weather.CityDTO;
import com.niuniu.motion.dto.weather.DistrictDTO;
import com.niuniu.motion.dto.weather.ProvinceDTO;
import com.niuniu.motion.model.dao.CityDAO;
import com.niuniu.motion.model.query.CityDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestCityManagerImpl implements RestCityManager {

    private static final Logger logger = LoggerFactory.getLogger(RestCityManagerImpl.class);

    @Autowired
    CityDAO cityDAO;

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
}
