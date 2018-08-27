package com.xkenmon.cms.web.directive.util;

import com.xkenmon.cms.dao.entity.Site;
import freemarker.core.Environment;
import freemarker.template.*;

import java.util.Map;
import java.util.Optional;


/**
 * 自定义指令工具类
 */
public class DirectiveUtil {
    private static final DefaultObjectWrapper wrapper = new DefaultObjectWrapper(
            Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    public static String getRetName(String retParam, Map params) {
        return getRetName(retParam, params, retParam);
    }

    public static String getRetName(String retParam, Map params, String defaultName) {
        if (params.containsKey(retParam)) {
            return params.get(retParam).toString();
        }
        return defaultName;
    }

    public static Optional<String> getString(String key, Map params) throws TemplateModelException {
        TemplateModel model = (TemplateModel) params.get(key);
        if (model == null) {
            return Optional.empty();
        }
        if (model instanceof TemplateScalarModel) {
            return Optional.ofNullable(((TemplateScalarModel) model).getAsString());
        }
        if (model instanceof TemplateNumberModel) {
            return Optional.ofNullable(((TemplateNumberModel) model).getAsNumber().toString());
        }
        throw new TemplateModelException(key + " must is String or Number");
    }

    public static Optional<Integer> getInteger(String key, Map params) throws TemplateModelException {
        TemplateModel model = (TemplateModel) params.get(key);
        if (model == null) {
            return Optional.empty();
        }
        if (model instanceof TemplateNumberModel) {
            return Optional.of(((TemplateNumberModel) model).getAsNumber().intValue());
        }
        if (model instanceof TemplateScalarModel) {
            String s = ((TemplateScalarModel) model).getAsString();
            if (s == null || s.equals("")) {
                return Optional.empty();
            }
            try {
                return Optional.of(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                throw new TemplateModelException(s + " must is Number");
            }
        }
        throw new TemplateModelException(key + "must is Number");
    }

    public static Optional<Boolean> getBoolean(String key, Map param) throws TemplateModelException {
        TemplateModel model = (TemplateModel) param.get(key);
        if (model == null) {
            return Optional.empty();
        }
        if (model instanceof TemplateBooleanModel) {
            return Optional.of(((TemplateBooleanModel) model).getAsBoolean());
        }
        if (model instanceof TemplateScalarModel) {
            String s = ((TemplateScalarModel) model).getAsString();
            if (!s.equals("true")) {
                if (s.equals("false")) {
                    return Optional.of(Boolean.FALSE);
                } else {
                    throw new TemplateModelException(key + "Must is Boolean");
                }
            } else {
                return Optional.of(Boolean.TRUE);
            }
        }
        throw new TemplateModelException(key + " must is bool type");
    }

    public static Optional<Site> getSite(Environment env) throws TemplateModelException {
        return Optional.of((Site) wrapper.unwrap(env.getVariable("site")));
    }
}
