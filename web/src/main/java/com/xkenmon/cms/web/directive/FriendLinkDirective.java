package com.xkenmon.cms.web.directive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkenmon.cms.dao.entity.FriendLink;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.dao.mapper.FriendLinkMapper;
import com.xkenmon.cms.web.annotation.CmsDirective;
import com.xkenmon.cms.web.directive.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

/**
 * @author bigmeng
 */
@CmsDirective("cms_friend_link")
public class FriendLinkDirective implements TemplateDirectiveModel {

    private final
    FriendLinkMapper friendLinkMapper;

    private static final String PARAM_SIZE = "size";

    private static final String PARAM_PAGE = "page";

    private static final DefaultObjectWrapper wrapper = new DefaultObjectWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).build();

    @Autowired
    public FriendLinkDirective(FriendLinkMapper friendLinkMapper) {
        this.friendLinkMapper = friendLinkMapper;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        Site site = DirectiveUtil.getSite(env).orElseThrow(() -> new TemplateException("can't find site", env));

        //默认全查询出来
        Integer size = DirectiveUtil.getInteger(PARAM_SIZE, params).orElse(0);

        Integer page = DirectiveUtil.getInteger(PARAM_PAGE, params).orElse(1);

        QueryWrapper<FriendLink> queryWrapper = new QueryWrapper<FriendLink>()
                .eq("friend_link_site_id", site);

        IPage<FriendLink> friendLinkList = friendLinkMapper.selectPage(new Page<>(page, size), queryWrapper);

        env.setVariable("result", wrapper.wrap(friendLinkList));

        body.render(env.getOut());
    }
}
