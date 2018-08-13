package com.xkenmon.cms.web.directive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkenmon.cms.dao.entity.FriendLink;
import com.xkenmon.cms.dao.mapper.FriendLinkMapper;
import com.xkenmon.cms.web.directive.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author bigmeng
 */
@Component
public class FriendLinkDirective implements TemplateDirectiveModel {
    private static final String PARAM_SITE_ID = "siteId";

    private final
    FriendLinkMapper friendLinkMapper;

    @Autowired
    public FriendLinkDirective(FriendLinkMapper friendLinkMapper) {
        this.friendLinkMapper = friendLinkMapper;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException {
        if (loopVars.length == 0) {
            throw new TemplateException("请指定一个循环变量，详情参看文档", env);
        }

        Integer siteId = DirectiveUtil.getInteger(PARAM_SITE_ID, params);
        QueryWrapper<FriendLink> queryWrapper = new QueryWrapper<FriendLink>().eq("friend_link_site_id", siteId);
        List<FriendLink> friendLinkList = friendLinkMapper.selectList(queryWrapper);
        DefaultObjectWrapper wrapper = new DefaultObjectWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).build();

        try {
            for (FriendLink link : friendLinkList) {
                loopVars[0] = wrapper.wrap(link);
                body.render(env.getOut());
            }
        } catch (IOException e) {
            throw new TemplateException(e, env);
        }
    }
}
