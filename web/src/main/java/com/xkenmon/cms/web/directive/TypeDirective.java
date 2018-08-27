package com.xkenmon.cms.web.directive;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkenmon.cms.common.constant.AccessStatus;
import com.xkenmon.cms.dao.entity.Article;
import com.xkenmon.cms.dao.entity.Category;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.dao.mapper.ArticleMapper;
import com.xkenmon.cms.dao.mapper.CategoryMapper;
import com.xkenmon.cms.web.annotation.CmsDirective;
import com.xkenmon.cms.web.directive.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 获取指定类型的栏目或文章,栏目类型和文章类型只能指定一个
 *
 * @author bigmeng
 */
@CmsDirective("cms_type_list")
public class TypeDirective implements TemplateDirectiveModel {

    /**
     * 指定栏目类型
     */
    private static final String PARAM_CATEGORY_TYPE = "cate_type";

    /**
     * 指定文章类型
     */
    private static final String PARAM_ARTICLE_TYPE = "article_type";

    /**
     * 页面大小，默认为0，返回所有数据
     */
    private static final String PARAM_SIZE = "size";

    /**
     * 页码数，默认为0
     */
    private static final String PARAM_PAGE = "page";

    /**
     * 排序字段，字符串形式，对应数据库中的字段
     */
    private static final String PARAM_ORDER_BY = "orderBy";

    /**
     * 排序策略: asc|desc
     */
    private static final String PARAM_ORDER = "order";
    /**
     * 未指定文章排序方式时的默认值
     */
    private static final String DEFAULT_ARTICLE_SORT = "article_create_time";

    /**
     * 未指定栏目排序方式是的默认值
     */
    private static final String DEFAULT_CATE_SORT = "category_create_time";

    private static final DefaultObjectWrapper wrapper = new DefaultObjectWrapperBuilder(Configuration.getVersion()).build();

    private final ArticleMapper articleMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public TypeDirective(ArticleMapper articleMapper, CategoryMapper categoryMapper) {
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars
            , TemplateDirectiveBody body) throws TemplateException, IOException {
        Optional<String> cateType = DirectiveUtil.getString(PARAM_CATEGORY_TYPE, params);
        Optional<String> articleType = DirectiveUtil.getString(PARAM_ARTICLE_TYPE, params);
        Optional<String> orderBy = DirectiveUtil.getString(PARAM_ORDER_BY, params);
        Optional<String> order = DirectiveUtil.getString(PARAM_ORDER, params);
        Optional<Integer> size = DirectiveUtil.getInteger(PARAM_SIZE, params);
        Optional<Integer> page = DirectiveUtil.getInteger(PARAM_PAGE, params);
        Optional<Site> site = DirectiveUtil.getSite(env);

        if (!site.isPresent()) {
            throw new TemplateException("site can't found", env);
        }

        //异或非运算 有且仅有一个不为null
        if ((!cateType.isPresent()) == (!articleType.isPresent())) {
            throw new TemplateException(PARAM_ARTICLE_TYPE + " or " + PARAM_CATEGORY_TYPE + " must be specified one.", env);
        }

        IPage retList;
        if (cateType.isPresent()) {
            Wrapper<Category> queryWrapper = new QueryWrapper<Category>()
                    .eq("category_type", cateType.get())
                    .and(w -> w.eq("category_site_id", site.get().getSiteId()))
                    .orderBy(true, "asc".equalsIgnoreCase(order.orElse("category_create_time")), orderBy.orElse(DEFAULT_CATE_SORT));

            retList = categoryMapper.selectPage(new Page<>(page.orElse(1), size.orElse(10)), queryWrapper);
        } else {
            Map<String, Object> map = new HashMap<>(3);
            map.put("article_type", articleType.get());
            map.put("article_site_id", site.get().getSiteId());
            map.put("article_status", AccessStatus.ACCESS);
            Wrapper<Article> articleWrapper = new QueryWrapper<Article>()
                    .allEq(map)
                    .orderBy(true, "asc".equalsIgnoreCase(order.orElse("asc")), orderBy.orElse(DEFAULT_ARTICLE_SORT));
            retList = articleMapper.selectPage(new Page<>(page.orElse(1), size.orElse(10)), articleWrapper);
        }

        env.setVariable("result", wrapper.wrap(retList));

        body.render(env.getOut());
    }
}
