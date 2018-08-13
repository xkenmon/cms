package com.xkenmon.cms.admin.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xkenmon.cms.admin.annotation.Auth;
import com.xkenmon.cms.admin.constant.ModuleNames;
import com.xkenmon.cms.admin.dto.ApiMessage;
import com.xkenmon.cms.admin.dto.ArticleUploadRequest;
import com.xkenmon.cms.admin.exception.ApiException;
import com.xkenmon.cms.admin.service.IArticleService;
import com.xkenmon.cms.dao.entity.Article;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author bigmeng
 */
@RestController
@RequestMapping("/article")
@Api(value = "article", description = "文章操作接口")
public class ArticleApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleApi.class);

    private final
    IArticleService articleService;

    @Autowired
    public ArticleApi(IArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("{id}")
    @ApiOperation(value = "get article by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = Article.class),
            @ApiResponse(code = 404, message = "article not found", response = ApiMessage.class),
            @ApiResponse(code = 500, message = "internal error", response = ApiMessage.class),
    })
    @Auth(siteId = "@articleServiceImpl.selectById(#id).getArticleSiteId()", modules = ModuleNames.CONTENT_MANAGE)
    public Article getById(@PathVariable("id") @ApiParam Integer id) throws ApiException {
        LOGGER.info("query article - id: {}", id);
        return articleService.selectById(id);
    }

    @GetMapping()
    @ApiOperation(value = "list article pagination")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "internal error", response = ApiMessage.class),
            @ApiResponse(code = 400, message = "pagination error, check your orderBy(lowerCamel) or order(asc|desc)", response = ApiMessage.class),
    })
    @Auth(siteId = "#siteId", modules = ModuleNames.CONTENT_MANAGE)
    public IPage<Article> articleList(
            @ApiParam("每页条目数")
            @RequestParam(value = "rowsPerPage", defaultValue = "10") Integer rowsPerPage,
            @ApiParam("页码数")
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "排序方式", allowableValues = "asc,desc")
            @RequestParam(value = "order", defaultValue = "asc") String order,
            @ApiParam(value = "排序字段，小驼峰命名", example = "articleTitle")
            @RequestParam(value = "orderBy", defaultValue = "articleId") String orderBy,
            @ApiParam(value = "站点ID")
            @RequestParam(value = "siteId") Integer siteId)
            throws ApiException {
        LOGGER.info("query article list - pageNumber: {}, rowsPerPage: {}, orderBy: {}, order: {}",
                pageNumber, rowsPerPage, orderBy, order);
        return articleService.queryArticleList(rowsPerPage, pageNumber, orderBy, order, siteId);
    }

    @PostMapping
    @ApiOperation(value = "create new article", notes = "添加一篇新文章，会进行必要字段完整性检查，添加成功后返回新添加文章的ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "internal error"),
            @ApiResponse(code = 400, message = "Some article fields are not complete"),
    })
    @Auth(siteId = "#uploadRequest.getArticle().getArticleSiteId()", modules = ModuleNames.CONTENT_MANAGE)
    public Integer createArticle(
            @ApiParam("article中的articleAuthor,articleTitle,articleType,articleCategoryId,articleSiteId为必填字段")
            @RequestBody ArticleUploadRequest uploadRequest) throws ApiException {
        LOGGER.info("insert article - title: {}"
                , uploadRequest.getArticle().getArticleTitle());
        return articleService.createArticle(uploadRequest);
    }

    @Auth(siteId = "#article.getArticleSiteId", modules = ModuleNames.CONTENT_MANAGE)
    @PutMapping
    @ApiOperation(value = "update a article", notes = "更新文章，article的ID字段必须指定，返回更新成功后的文章对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "internal error"),
            @ApiResponse(code = 400, message = "article is invalid")
    })
    public Article updateArticle(@RequestBody Article article) throws ApiException {
        LOGGER.info("update article - id: {}, title: {}", article.getArticleId(), article.getArticleTitle());
        return articleService.updateArticle(article);
    }

    @Auth(siteId = "@articleServiceImpl.selectById(#id).getArticleSiteId()", modules = ModuleNames.CONTENT_MANAGE)
    @DeleteMapping("{id}")
    @ApiOperation(value = "delete article by id", notes = "删除文章，返回删除的文章数")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "internal error"),
    })
    public Integer deleteArticle(@PathVariable("id") @ApiParam Integer id) throws ApiException {
        LOGGER.info("delete article - id: {}", id);
        return articleService.deleteArticle(id);
    }

}
