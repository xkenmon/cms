package com.xkenmon.cms.web.directive;

import com.xkenmon.cms.common.constant.AccessStatus;
import com.xkenmon.cms.dao.entity.Category;
import com.xkenmon.cms.dao.mapper.CategoryMapper;
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
@CmsDirective("cms_category")
public class CategoryDirective implements TemplateDirectiveModel {

    /**
     * 栏目ID
     */
    private static final String PARAM_ID = "id";

    private static final DefaultObjectWrapper wrapper = new DefaultObjectWrapperBuilder(Configuration.getVersion()).build();
    private final
    CategoryMapper categoryMapper;

    @Autowired
    public CategoryDirective(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars
            , TemplateDirectiveBody body) throws TemplateException, IOException {

        Integer id = DirectiveUtil.getInteger(PARAM_ID, params).orElseThrow(() -> new TemplateException("Must special id", env));

        Category category = categoryMapper.selectById(id);
        if (category.getCategoryStatus().equals(AccessStatus.ACCESS)) {
            env.setVariable("result", wrapper.wrap(category));
            body.render(env.getOut());
        }
    }
}
