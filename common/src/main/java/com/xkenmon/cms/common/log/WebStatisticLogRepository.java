package com.xkenmon.cms.common.log;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author bigmeng
 * @date 2018/8/20
 */
public interface WebStatisticLogRepository extends MongoRepository<WebStatisticLog, Long> {
}
