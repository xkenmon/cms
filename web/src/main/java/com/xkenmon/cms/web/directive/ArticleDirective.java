package com.xkenmon.cms.web.directive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkenmon.cms.common.constant.TableField;
import com.xkenmon.cms.dao.entity.Article;
import com.xkenmon.cms.dao.mapper.ArticleMapper;
import com.xkenmon.cms.web.annotation.CmsDirective;
import com.xkenmon.cms.web.directive.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bigmeng
 */
@CmsDirective("cms_article")
public class ArticleDirective implements TemplateDirectiveModel {

    /**
     * 文章ID
     */
    private static final String PARAM_ID = "id";

    /**
     * 查询结果是否包含文章内容等大字段，默认不包含
     */
    private static final String PARAM_BLOB = "blob";

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDirective.class);

private static final DefaultObjectWrapper wrapper = new DefaultObjectWrapperBuilder(Configuration.getVersion()).build();

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleDirective(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars
            , TemplateDirectiveBody body) throws TemplateException, IOException {

        Integer id = DirectiveUtil.getInteger(PARAM_ID, params)
                .orElseThrow(() -> new TemplateException("must special id", env));

        Boolean isBlob = DirectiveUtil.getBoolean(PARAM_BLOB, params).orElse(false);


        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("article_id", id);
        queryMap.put("article_status", 1);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<Article>()
                .select(isBlob ? TableField.ARTICLE_FIELD : TableField.ARTICLE_FIELD_WITHOUT_BLOB)
                .allEq(queryMap);

        Article article = articleMapper.selectOne(queryWrapper);

        if (article == null) {
            LOGGER.error("id为 {} 的文章不存在！", id);
        }

        env.setVariable("result", wrapper.wrap(article));

        body.render(env.getOut());
    }
}
