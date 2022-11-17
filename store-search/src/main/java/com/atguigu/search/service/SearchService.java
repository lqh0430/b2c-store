package com.atguigu.search.service;

import com.atguigu.param.ProductSearchParam;
import com.atguigu.utils.R;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/16 13:55
 */
public interface SearchService {

    /**
     * 根据搜索关键字分页查询商品
     * @param productSearchParam 商品搜索参数(搜索关键字+分页)
     * @return 商品对象集合
     */
    R searchProduct(ProductSearchParam productSearchParam) throws Exception;
}
