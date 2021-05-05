package com.niuniu.motion.model.dao.impl;

import com.niuniu.motion.model.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yawu
 * @version 1.0
 * @date 2021/5/4
 */

@SuppressWarnings("unchecked")
@Repository("BaseDaoImpl")
public class BaseDaoImpl implements BaseDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List findBySql(String sql) throws Exception {
        try {
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int executeBySql(String sql) throws Exception {
        try {
            jdbcTemplate.execute(sql);
            return 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
