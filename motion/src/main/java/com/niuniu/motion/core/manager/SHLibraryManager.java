package com.niuniu.motion.core.manager;

import java.util.List;

/**
 * @author yawu
 * @version 1.0
 * @date 2021/5/4
 */
public interface SHLibraryManager {

    List<Object> queryPage(int page, int pageSize);

    int update(String sql);
}
