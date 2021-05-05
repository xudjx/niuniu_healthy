package com.niuniu.motion.server.controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.niuniu.motion.core.manager.SHLibraryManager;
import com.niuniu.motion.dto.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author yawu
 * @version 1.0
 * @date 2021/5/4
 *
 * desc：上图相关数据接口封装
 */
@RestController
public class SHLibraryController {

    private static final int PAGE_SIZE = 20;

    private static final Logger logger = LoggerFactory.getLogger(SHLibraryController.class);

    @Autowired
    Environment environment;
    @Autowired
    SHLibraryManager shLibraryManager;

    @RequestMapping("/shl/queryPage")
    public ResultDTO queryPage(@RequestParam(value="page", defaultValue="0") String page) {
        int p = Integer.parseInt(page);
        List data = shLibraryManager.queryPage(p, PAGE_SIZE);
        ResultDTO resultDTO = new ResultDTO(ResultDTO.SUCCESS);
        resultDTO.setResult(data);
        return resultDTO;
    }

    @RequestMapping(value = "/shl/update", method = RequestMethod.POST, produces = "application/json;charset/UTF-8")
    public ResultDTO update(@RequestBody String jsonParam) {
        logger.info(">>>>update body:" + jsonParam);
        Gson gson = new Gson();
        Map<String, Object> dataCO = gson.fromJson(jsonParam, new TypeToken<Map<String, Object>>(){}.getType());
        if (dataCO.isEmpty() || !dataCO.containsKey("id")) {
            return new ResultDTO(ResultDTO.FAILED, "invalid_params", "参数必须包含id");
        }
        int id = ((Double)dataCO.get("id")).intValue();
        List<String> fields = new ArrayList<>();
        for (Map.Entry<String, Object> entry: dataCO.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue().toString();
            if (mapKey.equals("id")) {
                continue;
            }
            fields.add(String.format("%s='%s'", mapKey, mapValue));
        }
        String formatFields = "";
        for (int i = 0; i < fields.size(); i++) {
            formatFields += fields.get(i);
            if (i != fields.size() - 1) {
                formatFields += ",";
            }
        }
        String updateSql = String.format("update t_cities set %s where city_id=%d", formatFields, id);
        int status = shLibraryManager.update(updateSql);
        return new ResultDTO(status == 1 ? ResultDTO.SUCCESS : ResultDTO.FAILED);
    }
}
