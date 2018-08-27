package com.xkenmon.cms.web.directive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.dao.entity.Tag;
import com.xkenmon.cms.dao.mapper.TagMapper;
import com.xkenmon.cms.web.annotation.CmsDirective;
import com.xkenmon.cms.web.directive.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

@CmsDirective("cms_tag_list")
public class TagListDirective implements TemplateDirectiveModel {

    private static final String PARAM_PAGE = "page";

    private static final String PARAM_SIZE = "size";

    private static final DefaultObjectWrapper wrapper = new DefaultObjectWrapperBuilder(Configuration.getVersion()).build();

    private final TagMapper tagMapper;

    @Autowired
    public TagListDirective(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        Site site = DirectiveUtil.getSite(env).orElseThrow(() -> new TemplateException("site is null!!", env));

        //默认全选
        Integer size = DirectiveUtil.getInteger(PARAM_SIZE, params).orElse(0);

        Integer page = DirectiveUtil.getInteger(PARAM_PAGE, params).orElse(1);

        QueryWrapper<Tag> queryWrapper = new QueryWrapper<Tag>().eq("tag_site_id", site.getSiteId());

        IPage<Tag> result = tagMapper.selectPage(new Page<>(page, size), queryWrapper);

        env.setVariable("result", wrapper.wrap(result));

        body.render(env.getOut());
    }
}
