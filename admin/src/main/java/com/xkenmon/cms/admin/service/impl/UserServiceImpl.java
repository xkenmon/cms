package com.xkenmon.cms.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkenmon.cms.admin.exception.ApiException;
import com.xkenmon.cms.admin.service.IUserService;
import com.xkenmon.cms.dao.entity.Permission;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.dao.entity.User;
import com.xkenmon.cms.dao.mapper.PermissionMapper;
import com.xkenmon.cms.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author bigmeng
 * @date 2018/8/9
 */
@Service
public class UserServiceImpl implements IUserService {

    private final
    UserMapper userMapper;

    private final
    PermissionMapper permissionMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PermissionMapper permissionMapper) {
        this.userMapper = userMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User selectByUserName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("user_name", name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public Collection<Permission> queryPermission(Integer uid) {
        return permissionMapper.getByUserId(uid);
    }

    @Override
    public Collection<String> queryModules(Integer uid, Integer sid) {
        return permissionMapper.getPermittedModule(uid, sid);
    }

    @Override
    public Collection<Site> querySite(Integer uid) {
        return permissionMapper.getPermittedSite(uid);
    }

    @Override
    public Integer createUser(User user) throws ApiException {
        if (userMapper.isExistByUserName(user.getUserName())) {
            throw new ApiException(400, "user is exists!");
        }
        return userMapper.insert(user);
    }
}
