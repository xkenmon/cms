<#-- @ftlvariable name="article" type="com.xkenmon.cms.dao.entity.Article" -->
<#-- @ftlvariable name="category" type="com.xkenmon.cms.dao.entity.Category" -->
<#-- @ftlvariable name="site" type="com.xkenmon.cms.dao.entity.Site" -->
<#-- @ftlvariable name="tagList" type="java.util.List<com.xkenmon.cms.dao.entity.Tag>" -->
<#-- @ftlvariable name="fileKeys" type="java.util.List<java.lang.String>" -->
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
    <title>${article.articleTitle}</title>
    <link rel="stylesheet" href="${ctx.contextPath}/${baseSkinPath}/style-article.css">
</head>
<body>
<div class="nav">
    <a href="/${site.siteUrl}">${site.siteName}</a>
    >>
    <a href="/view/category/${category.categoryId}">${category.categoryTitle}</a>
</div>
<h1 class="article-title">${article.articleTitle}</h1>
<div class="author"></div>
<div>
    Author: ${article.articleAuthor},
    Tags: [
<#list tagList as tag>
        <a href="/view/tag/${tag.tagId}">${tag.tagName}</a>
</#list>
    ]
</div>
<p class="a-desc">${article.articleDesc}</p>
<div>
${article.articleContent}
</div>

<#if fileKeys?? && (fileKeys?size >0)>
<hr>
附件📎:
    <#list fileKeys as file>
        <a href="${cdnDomain}/${file}">
            ${file}
        </a>
    <br>
    </#list>
</#if>
<hr>
<@cms_article_list size=5 page=1 orderBy="article_create_time" order="desc">
    <#if result.size gt 0>
        <h5>最近更新：</h5>
        <#list result.records as a>
            <a href="/view/article/${a.articleId}">${a.articleTitle}</a>
        </#list>
    </#if>
</@cms_article_list>
</body>
</html>
