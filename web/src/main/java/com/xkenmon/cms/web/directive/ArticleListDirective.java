package com.xkenmon.cms.web.directive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkenmon.cms.common.constant.TableField;
import com.xkenmon.cms.dao.entity.Article;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.dao.mapper.ArticleMapper;
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
@CmsDirective("cms_article_list")
public class ArticleListDirective implements TemplateDirectiveModel {

    private final ArticleMapper articleMapper;

    private static final String PARAM_SIZE = "size";

    private static final String PARAM_PAGE = "page";

    private static final String PARAM_ORDER_BY = "orderBy";

    private static final String PARAM_ORDER = "order";

    private static final DefaultObjectWrapper wrapper = new DefaultObjectWrapperBuilder(Configuration.getVersion()).build();

    @Autowired
    public ArticleListDirective(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        //参数提取
        Integer size = DirectiveUtil.getInteger(PARAM_SIZE, params).orElse(10);

        Integer page = DirectiveUtil.getInteger(PARAM_PAGE, params).orElse(1);

        String order = DirectiveUtil.getString(PARAM_ORDER, params).orElse("asc");

        String orderBy = DirectiveUtil.getString(PARAM_ORDER_BY, params).orElse("article_create_time");

        Site site = DirectiveUtil.getSite(env).orElseThrow(() -> new TemplateException("site is null !!", env));

        QueryWrapper<Article> queryWrapper = new QueryWrapper<Article>().select(TableField.ARTICLE_FIELD_WITHOUT_BLOB)
                .eq("article_site_id", site.getSiteId())
                .orderByAsc("asc".equalsIgnoreCase(order), orderBy);

        IPage<Article> articlePage = articleMapper.selectPage(new Page<>(page, size), queryWrapper);

        env.setVariable("result", wrapper.wrap(articlePage));

        body.render(env.getOut());
    }
}
