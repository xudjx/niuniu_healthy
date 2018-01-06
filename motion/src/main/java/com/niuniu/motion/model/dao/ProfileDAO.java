package com.niuniu.motion.model.dao;

import com.niuniu.motion.model.query.ProfileDO;
import org.springframework.data.repository.CrudRepository;

public interface ProfileDAO extends CrudRepository<ProfileDO, Long> {
    ProfileDO findByAccount(String account);
}
