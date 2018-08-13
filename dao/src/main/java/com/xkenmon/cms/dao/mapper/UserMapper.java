package com.xkenmon.cms.dao.mapper;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xkenmon.cms.dao.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名判断用户是否存在
     * @param userName 用户名
     * @return 用户是否存在
     */
    @Select("select exists(select user_name from cms_user where user_name = #{userName})")
    Boolean isExistByUserName(String userName);
}
