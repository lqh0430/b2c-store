package com.atguigu.category.service;

import com.atguigu.param.ProductHotParam;
import com.atguigu.pojo.Category;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/14 19:17
 */
public interface CategoryService extends IService<Category> {


    /**
     * 根据类别名称查询类别信息
     * @param categoryName 传入的类别名
     * @return 类别对象
     */
    R getCategoryByName(String categoryName);

    /**
     * 根据热门类别名称集合 查询对应的热门类别id集合
     * @param productHotParam 传入的热门商品类别集合
     * @return 类别id集合
     */
    R getCategoryIdListByName(ProductHotParam productHotParam);

    /**
     * 类别查询
     * @return 类别对象集合
     */
    R getList();
}
