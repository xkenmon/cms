package com.xkenmon.cms.common.log;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bigmeng
 * @date 2018/8/20
 */
@Repository
public interface WebStatisticLogRepository extends MongoRepository<WebStatisticLog, Long> {
}
