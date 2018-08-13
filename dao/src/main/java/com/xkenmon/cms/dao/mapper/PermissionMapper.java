package com.xkenmon.cms.dao.mapper;

import com.xkenmon.cms.dao.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkenmon.cms.dao.entity.Site;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author AutoGenerator
 * @since 2018-08-06
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 获得用户所有权限
     *
     * @param uid 用户id
     * @return 权限列表
     */
    List<Permission> getByUserId(@Param("uid") Integer uid);

    /**
     * 选择该用户获得授权的所有站点
     *
     * @param uid 用户id
     * @return 站点列表
     */
    List<Site> getPermittedSite(@Param("uid") Integer uid);

    /**
     * 选择该用户可访问的模块
     *
     * @param uid 用户id
     * @param sid 站点id
     * @return 模块名称列表
     */
    List<String> getPermittedModule(@Param("uid") Integer uid, @Param("sid") Integer sid);
}
