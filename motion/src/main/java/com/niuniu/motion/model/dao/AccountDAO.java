package com.niuniu.motion.model.dao;

import com.niuniu.motion.model.bean.AccountDO;
import org.springframework.data.repository.CrudRepository;

public interface AccountDAO extends CrudRepository<AccountDO, Long> {

    AccountDO findByAccountName(String accountName);
}
