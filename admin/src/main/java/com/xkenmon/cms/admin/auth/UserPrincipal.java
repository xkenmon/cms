package com.xkenmon.cms.admin.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xkenmon.cms.dao.entity.Permission;
import com.xkenmon.cms.dao.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author bigmeng
 * @date 2018/8/9
 */
public class UserPrincipal implements UserDetails {
    private Integer id;


    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

//    public static UserPrincipal create(User user) {
//        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
//                new SimpleGrantedAuthority(role.getName().name())
//        ).collect(Collectors.toList());

//    }

    UserPrincipal(User user, Collection<Permission> permissions) {
        //权限采用`siteId::moduleName`形式表达,不用太细分.
        this.authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getSiteId() + "::" + p.getModuleName()))
                .collect(Collectors.toList());
        this.email = user.getUserEmail();
        this.id = user.getUserId();
        this.password = user.getUserPassword();
        this.username = user.getUserName();
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
