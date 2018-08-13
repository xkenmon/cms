package com.xkenmon.cms.web.directive;

import com.xkenmon.cms.common.constant.CMSContentType;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author bigmeng
 */
@Component
public class IndexArticleDirective implements TemplateDirectiveModel {
    private static final String PARAM_ARTICLE_TYPE = "article_type";
    private final
    TypeDirective typeDirective;

    @Autowired
    public IndexArticleDirective(TypeDirective typeDirective) {
        this.typeDirective = typeDirective;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars
            , TemplateDirectiveBody body) throws TemplateException {
        params.put(PARAM_ARTICLE_TYPE, new SimpleScalar(CMSContentType.HOMEPAGE_ARTICLE_TYPE));
        typeDirective.execute(env, params, loopVars, body);
    }
}
