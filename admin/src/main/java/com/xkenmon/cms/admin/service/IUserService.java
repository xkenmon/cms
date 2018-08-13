package com.xkenmon.cms.admin.service;

import com.xkenmon.cms.admin.exception.ApiException;
import com.xkenmon.cms.dao.entity.Permission;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.dao.entity.User;

import java.util.Collection;

/**
 * @author bigmeng
 * @date 2018/8/9
 */
public interface IUserService {
    /**
     * 通过id选取用户
     *
     * @param id 用户ID
     * @return id对应的用户
     */
    User selectById(Integer id);

    /**
     * 通过用户名返回用户
     *
     * @param name 用户名
     * @return 对应的用户
     */
    User selectByUserName(String name);

    /**
     * 权限查询
     *
     * @param uid user id
     * @return user permission set
     */
    Collection<Permission> queryPermission(Integer uid);

    /**
     * 查询该用户在sid对应的站点下所拥有的模块权限
     *
     * @param uid 用户id
     * @param sid 站点ID
     * @return 模块列表
     */
    Collection<String> queryModules(Integer uid, Integer sid);

    /**
     * 查询该用户所管理的站点
     *
     * @param uid user id
     * @return site list
     */
    Collection<Site> querySite(Integer uid);

    /**
     * create user
     *
     * @param user new user
     * @return 修改条目数量
     * @throws ApiException user is exist
     */
    Integer createUser(User user) throws ApiException;
}
