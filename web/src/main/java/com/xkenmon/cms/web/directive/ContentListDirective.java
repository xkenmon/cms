package com.xkenmon.cms.web.directive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkenmon.cms.common.constant.TableField;
import com.xkenmon.cms.dao.entity.Article;
import com.xkenmon.cms.dao.mapper.ArticleMapper;
import com.xkenmon.cms.web.directive.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author bigmeng
 */
@Component
public class ContentListDirective implements TemplateDirectiveModel {

    /**
     * 指定的栏目ID
     */
    private static final String PARAM_CID = "categoryId";

    /**
     * 每页的条目数，未指定默认为0
     */
    private static final String PARAM_SIZE = "size";

    /**
     * 页码数， 未指定默认为0
     */
    private static final String PARAM_PAGE = "page";

    /**
     * 排序字段，字符串形式，对应数据库中的字段，默认为{@link ContentListDirective#DEFAULT_ORDER_BY}
     */
    private static final String PARAM_ORDER_BY = "orderBy";

    /**
     * 排序方式，asc或desc
     */
    private static final String PARAM_ORDER = "order";

    /**
     * 默认排序方式：按创建时间排序
     */
    private static final String DEFAULT_ORDER_BY = "article_create_time";

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentListDirective.class);

    private final ArticleMapper articleMapper;

    @Autowired
    public ContentListDirective(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    /**
     * 返回指定目录的文章<br>
     */
    @Override
    public void execute(Environment env
            , Map params, TemplateModel[] loopVars
            , TemplateDirectiveBody body) throws TemplateException {
        Integer categoryId = DirectiveUtil.getInteger(PARAM_CID, params);
        Integer size = DirectiveUtil.getInteger(PARAM_SIZE, params);
        Integer page = DirectiveUtil.getInteger(PARAM_PAGE, params);
        String orderBy = DirectiveUtil.getString(PARAM_ORDER_BY, params);
        String order = DirectiveUtil.getString(PARAM_ORDER, params);

        if (categoryId == null) {
            throw new TemplateException("Must specify " + PARAM_CID + "param.", env);
        }

        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }

        if (page == null) {
            page = 1;
        }

        if (size == null) {
            size = 0;
        }

        QueryWrapper<Article> queryWrapper = new QueryWrapper<Article>()
                .select(TableField.ARTICLE_FIELD_WITHOUT_BLOB)
                .orderBy(true, "asc".equalsIgnoreCase(order), orderBy);

        List<Article> articleList = articleMapper.selectPage(new Page<>(page, size), queryWrapper).getRecords();
        DefaultObjectWrapper wrapper = new DefaultObjectWrapperBuilder(Configuration.getVersion()).build();

        try {
            for (Article article : articleList) {
                loopVars[0] = wrapper.wrap(article);
                body.render(env.getOut());
            }
        } catch (IOException e) {
            throw new TemplateException(e, env);
        }
    }
}
