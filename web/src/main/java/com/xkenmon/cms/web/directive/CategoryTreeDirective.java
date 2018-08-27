package com.xkenmon.cms.web.directive;

import com.xkenmon.cms.common.dto.Tree;
import com.xkenmon.cms.dao.entity.Category;
import com.xkenmon.cms.dao.entity.Site;
import com.xkenmon.cms.web.annotation.CmsDirective;
import com.xkenmon.cms.web.directive.util.DirectiveUtil;
import com.xkenmon.cms.web.util.CategoryTreeUtil;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author bigmeng
 */
@CmsDirective("cms_category_tree")
public class CategoryTreeDirective implements TemplateDirectiveModel {

    private static final DefaultObjectWrapper wrapper = new DefaultObjectWrapper(Configuration.getVersion());

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        Site site = DirectiveUtil.getSite(env).orElseThrow(() -> new TemplateException("site is null", env));

        Tree<Category> tree = CategoryTreeUtil.query(site.getSiteId());

        env.setVariable("result", wrapper.wrap(tree));

        body.render(env.getOut());
    }
}
