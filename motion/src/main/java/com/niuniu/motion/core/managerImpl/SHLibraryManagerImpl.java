package com.niuniu.motion.core.managerImpl;

import com.niuniu.motion.core.manager.SHLibraryManager;
import com.niuniu.motion.model.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yawu
 * @version 1.0
 * @date 2021/5/4
 */
@Component
public class SHLibraryManagerImpl implements SHLibraryManager {

    @Autowired
    Environment environment;
    @Autowired
    BaseDao baseDao;

    @Override
    public List<Object> queryPage(int page, int pageSize) {
        String sqlStr = String.format("select * from t_cities limit %d,%d", page*pageSize, pageSize);
        List list = null;
        try {
            list = baseDao.findBySql(sqlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int update(String sql) {
        try {
            return baseDao.executeBySql(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
