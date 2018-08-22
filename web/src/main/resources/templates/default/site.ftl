<#-- @ftlvariable name="site" type="com.xkenmon.cms.dao.entity.Site" -->
<#-- @ftlvariable name="indexCategoryList" type="java.util.List<com.xkenmon.cms.dao.entity.Category>" -->
<#-- @ftlvariable name="indexArticleList" type="java.util.List<com.xkenmon.cms.dao.entity.Article>" -->
<#-- @ftlvariable name="baseSkinPath" type="java.lang.String" -->
<#-- @ftlvariable name="cdnDomain" type="java.lang.String" -->
<#-- @ftlvariable name="ctx" type="org.springframework.web.servlet.support.RequestContext" -->

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${ctx.contextPath}/${baseSkinPath}/style-site.css">
    <title>${site.siteName}</title>
</head>
<body>
<div class="main">
    <div class="ca-list">
        <ul>
            <#list indexCategoryList as ca>
                <li>
                    <a href="/view/category/${ca.categoryId}">${ca.categoryTitle}</a>
                </li>
            </#list>
        </ul>
    </div>
    <div class="a-list">
        <#list indexArticleList as art>
            <a class="a-show" href="/view/article/${art.articleId}">
                <p class="a-title">${art.articleTitle}</p>
                <p class="a-desc">${art.articleDesc}</p>
            </a>
        </#list>
    </div>
</div>
</body>
</html>
