package com.niuniu.motion.core.manager;

import com.niuniu.motion.dto.weather.ProvinceDTO;
import com.niuniu.motion.model.query.CityDO;

import java.util.List;

public interface RestCityManager {

    List<CityDO> saveProvinceDTO(List<ProvinceDTO> provinceDTOList);


}
