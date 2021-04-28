package com.niuniu.motion.model.dao;

import com.niuniu.motion.model.query.AccessTokenDO;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenDAO extends CrudRepository<AccessTokenDO, Long> {

    AccessTokenDO findByAccountId(long accountId);
}
