package com.xkenmon.cms.admin.auth;

import com.xkenmon.cms.admin.service.IUserService;
import com.xkenmon.cms.dao.entity.Permission;
import com.xkenmon.cms.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author bigmeng
 * @date 2018/8/9
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final
    IUserService userService;

    @Autowired
    public CustomUserDetailsServiceImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    @Cacheable("userPrincipal")
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("username: " + username + " not found");
        }
        Collection<Permission> permissions = userService.queryPermission(user.getUserId());
        return new UserPrincipal(user, permissions);
    }

    @Cacheable("userPrincipal")
    public UserPrincipal loadUserById(Integer userId) {
        User user = userService.selectById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("user id: " + userId + " not found");
        }

        Collection<Permission> permissions = userService.queryPermission(user.getUserId());
        return new UserPrincipal(user, permissions);
    }
}
