package com.xkenmon.cms.admin.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xkenmon.cms.admin.dto.ApiMessage;
import com.xkenmon.cms.admin.dto.Tree;
import com.xkenmon.cms.admin.exception.ApiException;
import com.xkenmon.cms.admin.service.ICategoryService;
import com.xkenmon.cms.dao.entity.Category;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author bigmeng
 * @date 2018/8/8
 */
@RestController
@RequestMapping("/category")
@Api(value = "category", description = "栏目操作接口")
public class CategoryApi {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryApi(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("{id}")
    @ApiOperation("get category by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 404, message = "category not found", response = ApiMessage.class),
            @ApiResponse(code = 500, message = "internal error", response = ApiMessage.class)
    })
    public Category getById(@PathVariable("id") @ApiParam Integer id) throws ApiException {
        return categoryService.selectById(id);
    }

    @GetMapping
    @ApiOperation("list category pagination")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "internal error", response = ApiMessage.class),
            @ApiResponse(code = 400, message = "pagination error, check your orderBy(lowerCamel) or order(asc|desc) params")
    })
    public IPage<Category> categoryList(
            @ApiParam("每页条目数")
            @RequestParam(value = "rowsPerPage", defaultValue = "10") Integer rowsPerPage,
            @ApiParam("页码数")
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam("排序方式")
            @RequestParam(value = "order", defaultValue = "asc") String order,
            @ApiParam(value = "排序字段，小驼峰命名", example = "categoryTitle")
            @RequestParam(value = "orderBy", defaultValue = "categoryId") String orderBy)
            throws ApiException {
        return categoryService.queryCategoryList(rowsPerPage, pageNumber, orderBy, order);
    }

    @PostMapping
    @ApiOperation(value = "create new category", notes = "添加一个新栏目，会进行必要字段完整性检查，添加成功后返回新添加栏目的ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "internal error"),
            @ApiResponse(code = 400, message = "Some category fields are not complete"),
    })
    public Integer createArticle(@RequestBody Category category) throws ApiException {
        return categoryService.createCategory(category);
    }

    @PutMapping
    @ApiOperation(value = "update a category", notes = "更新栏目，category的ID字段必须指定，返回更新成功后的栏目对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "internal error"),
            @ApiResponse(code = 400, message = "category is invalid")
    })
    public Category updateCategory(@RequestBody Category category) throws ApiException {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "delete category by id", notes = "删除栏目，返回删除的栏目数")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "internal error"),
    })
    public Integer deleteArticle(@PathVariable("id") @ApiParam Integer id) throws ApiException {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("tree")
    @ApiOperation("get category tree")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "data error"),
    })
    public List<Tree<Category>> categoryTree() throws ApiException {
        return categoryService.getCategoryTree();
    }

}
