package com.xkenmon.cms.admin.service;

import com.xkenmon.cms.dao.entity.Permission;
import com.xkenmon.cms.dao.entity.User;

import java.util.Collection;

/**
 * @author bigmeng
 * @date 2018/8/9
 */
public interface IUserService {
    User selectById(Integer id);

    User selectByUserName(String name);

    Collection<Permission> queryPermission(Integer uid);

    Integer createUser(User user);
}
