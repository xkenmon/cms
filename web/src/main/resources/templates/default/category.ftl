<#-- @ftlvariable name="articleList" type="java.util.List<com.xkenmon.cms.dao.entity.Article>" -->
<#-- @ftlvariable name="category" type="com.xkenmon.cms.dao.entity.Category" -->
<#-- @ftlvariable name="site" type="com.xkenmon.cms.dao.entity.Site" -->
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
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="${ctx.contextPath}/${baseSkinPath}/style-category.css">
    <title>${category.categoryTitle}</title>
</head>
<body>
<div class="nav">
${site.siteName} >> <a href="/${site.siteUrl}">${site.siteName}</a>
</div>
<div class="a-list">
        <#list articleList as art>
            <a class="a-show" href="/view/article/${art.articleId}">
                <p class="a-title">${art.articleTitle}</p>
                <p class="a-desc">${art.articleDesc}</p>
            </a>
        </#list>
    <br>
</div>
</body>
</html>