package com.xkenmon.cms.web.conf;

import com.xkenmon.cms.web.ApplicationContextProvider;
import com.xkenmon.cms.web.annotation.CmsDirective;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 自定义指令关键字定义类<br>
 * 所有的自定义指令都要在这里设置后生效<br>
 *
 * @author bigmeng
 */
@Component
public class DirectiveConfig {
    private final Configuration configuration;

    @Autowired
    public DirectiveConfig(Configuration configuration) {
        this.configuration = configuration;
    }

//    private final ContentListDirective contentListDirective;
//    private final TypeDirective typeDirective;
//    private final IndexArticleDirective indexArticleDirective;
//    private final IndexCategoryDirective indexCategoryDirective;
//    private final ArticleDirective articleDirective;
//    private final CategoryDirective categoryDirective;
//    private final FriendLinkDirective friendLinkDirective;


//    @Autowired
//    public DirectiveConfig(Configuration configuration
//            , ContentListDirective contentListDirective
//            , TypeDirective typeDirective
//            , IndexArticleDirective indexArticleDirective
//            , IndexCategoryDirective indexCategoryDirective
//            , ArticleDirective articleDirective
//            , CategoryDirective categoryDirective
//            , FriendLinkDirective friendLinkDirective) {
//        this.configuration = configuration;
//        this.contentListDirective = contentListDirective;
//        this.typeDirective = typeDirective;
//        this.indexArticleDirective = indexArticleDirective;
//        this.indexCategoryDirective = indexCategoryDirective;
//        this.articleDirective = articleDirective;
//        this.categoryDirective = categoryDirective;
//        this.friendLinkDirective = friendLinkDirective;
//    }

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();

        Map<String, Object> directiveMap = context.getBeansWithAnnotation(CmsDirective.class);

        for (Object directive : directiveMap.values()) {
            configuration.setSharedVariable(
                    directive.getClass().getAnnotation(CmsDirective.class).value()
                    , context.getBean(directive.getClass())
            );
        }
//        configuration.setSharedVariable("cms_content_list", contentListDirective);
//        configuration.setSharedVariable("cms_type_list", typeDirective);
//        configuration.setSharedVariable("cms_index_article", indexArticleDirective);
//        configuration.setSharedVariable("cms_index_category", indexCategoryDirective);
//        configuration.setSharedVariable("cms_article", articleDirective);
//        configuration.setSharedVariable("cms_category", categoryDirective);
//        configuration.setSharedVariable("cms_friend_link_list", friendLinkDirective);
    }
}
