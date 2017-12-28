package com.niuniu.motion.model.dao;

import com.niuniu.motion.model.query.AccountDO;
import org.springframework.data.repository.CrudRepository;

public interface AccountDAO extends CrudRepository<AccountDO, Long> {

    AccountDO findByAccount(String account);
}
