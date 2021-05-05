package com.niuniu.motion.model.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yawu
 * @version 1.0
 * @date 2021/5/4
 */

@Repository("baseDao")
public interface BaseDao {

    // 执行sql查询
    public <T> List<T> findBySql(String sql) throws Exception;

    // 执行sql语句，实行增，删，改
    public int executeBySql(String sql) throws Exception;
}
