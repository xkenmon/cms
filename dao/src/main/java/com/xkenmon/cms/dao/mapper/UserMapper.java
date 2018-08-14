package com.xkenmon.cms.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkenmon.cms.dao.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名判断用户是否存在
     * @param userName 用户名
     * @return 用户是否存在
     */
    @Select("select exists(select user_name from cms_user where user_name = #{userName})")
    Boolean isExistByUserName(String userName);
}
