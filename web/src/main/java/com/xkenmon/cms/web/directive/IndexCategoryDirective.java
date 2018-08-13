package com.xkenmon.cms.web.directive;

import com.xkenmon.cms.common.constant.CMSContentType;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class IndexCategoryDirective implements TemplateDirectiveModel {
    private static final String PARAM_CATEGORY_TYPE = "cate_type";

    private final
    TypeDirective typeDirective;

    public IndexCategoryDirective(TypeDirective typeDirective) {
        this.typeDirective = typeDirective;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        params.put(PARAM_CATEGORY_TYPE, new SimpleScalar(CMSContentType.HOMEPAGE_CATEGORY_TYPE));
        typeDirective.execute(env, params, loopVars, body);
    }
}
