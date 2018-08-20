package com.xkenmon.cms.admin.log;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bigmeng
 * @date 2018/8/16
 */
@Repository
public interface AdminLogRepository extends MongoRepository<MongoAdminLog, Long> {
}
