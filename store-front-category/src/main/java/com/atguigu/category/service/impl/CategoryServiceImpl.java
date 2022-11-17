package com.atguigu.category.service.impl;

import com.atguigu.category.mapper.CategoryMapper;
import com.atguigu.category.service.CategoryService;
import com.atguigu.param.ProductHotParam;
import com.atguigu.param.ProductParamInteger;
import com.atguigu.pojo.Category;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;


/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/14 19:18
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 类别查询
     *
     * @return 类别对象集合
     */
    @Override
    public R getList() {

        List<Category> categoryList = categoryMapper.selectList(null);
        log.info("categoryList={}",categoryList);

        return R.ok(categoryList);
    }

    /**
     * 根据类别名称查询类别信息
     *
     * @param categoryName 传入的类别名
     * @return 类别对象
     */
    @Override
    public R getCategoryByName(String categoryName) {

        //构造条件
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.eq(Category::getCategoryName, categoryName);
        //查询
        Category category = categoryMapper.selectOne(categoryLambdaQueryWrapper);
        log.info("category={}",category);

        if (category == null) {
            return R.fail("类别查询失败");
        }

        return R.ok("类别查询成功",category);
    }

    /**
     * 根据热门类别名称集合 查询对应的热门类别id集合
     *
     * @param productHotParam 传入的热门商品类别集合
     * @return 类别id集合
     */
    @Override
    public R getCategoryIdListByName(ProductHotParam productHotParam) {

        //构造条件 sql: select id from category where category_name in(?,?,?)
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.in(Category::getCategoryName, productHotParam.getCategoryName());
        categoryLambdaQueryWrapper.select(Category::getCategoryId);

        List<Object> ids = categoryMapper.selectObjs(categoryLambdaQueryWrapper);
        log.info("ids={}",ids);

        return R.ok("热门类别id查询成功",ids);
    }



}
