package com.atguigu.category.controller;

import com.atguigu.category.service.CategoryService;
import com.atguigu.param.ProductHotParam;
import com.atguigu.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/14 19:15
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 根据类别名查询类别信息
     * @param categoryName 传入的类别名称
     * @return 类别对象
     */
    @GetMapping("/promo/{categoryName}")
    public R getCategoryByName(@PathVariable("categoryName") String categoryName) {

        if (StringUtils.isBlank(categoryName)) {
            return R.fail("类别名不存在,查询失败");
        }

        return categoryService.getCategoryByName(categoryName);
    }

    /**
     * 根据热门类别名称集合 查询对应的热门类别id集合
     * @param productHotParam 传入的热门商品类别集合
     * @param result 内部校验后的对象
     * @return 类别id集合
     */
    @PostMapping("/hots")
    public R getCategoryIdListByName(@RequestBody @Validated ProductHotParam productHotParam, BindingResult result) {
        log.info("参数={}",productHotParam);
        if (result.hasErrors()) {
            return R.fail("热门类别名称参数异常");
        }
        return categoryService.getCategoryIdListByName(productHotParam);
    }


    /**
     * 类别查询
     * @return 类别对象集合
     */
    @GetMapping("/list")
    public R list() {
        return categoryService.getList();
    }

}
