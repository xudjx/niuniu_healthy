package com.niuniu.motion.model.dao;


import com.niuniu.motion.model.bean.RecordWeightDO;
import org.springframework.data.repository.CrudRepository;

public interface RecordWeightDAO extends CrudRepository<RecordWeightDO, Long> {

    RecordWeightDO findByAccountId(long accountId);
}
