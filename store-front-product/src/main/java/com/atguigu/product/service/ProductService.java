package com.atguigu.product.service;

import com.atguigu.param.*;
import com.atguigu.pojo.Product;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/15 8:42
 */
public interface ProductService extends IService<Product> {
    /**
     * 根据 单类别名称去查询商品信息
     * @param categoryName 类别名
     * @return 商品对象集合
     */
    R getProductByCategoryName(String categoryName);


    /**
     * 根据 热门商品的多类别名称去查询商品信息
     * @param productHotParam 类别名集合
     * @return 商品对象集合
     */
    R getProductByCategoryNames(ProductHotParam productHotParam);

    /**
     * 查询 全部商品对应的类别信息集合
     * @return 类别集合
     */
    R getProduct();

    /**
     * 分页查询商品对象集合
     * @param productParamInteger 分页参数
     * @return 商品对象集合和总条数
     */
    R byCategory(ProductParamInteger productParamInteger);

    /**
     * 查询全部商品对象集合
     * @param productParamInteger
     * @return
     */
    R all(ProductParamInteger productParamInteger);

    /**
     * 根据商品id查询商品对象信息
     * @param productID 商品id
     * @return 商品对象
     */
    R detail(Integer productID);

    /**
     * 根据商品id查询商品图片对象集合
     * @param productID 商品id
     * @return 商品图片对象集合
     */
    R pictures(Integer productID);

    /**
     * 搜索服务调用的接口,查询全部商品（用于数据同步）
     * @return
     */
    List<Product> allList();

    /**
     * 商品服务 调用搜索服务接口 进行 商品搜索
     * @param productSearchParam 商品搜索参数
     * @return 商品对象集合
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * 收藏服务 调用该接口 该接口根据多组商品id查询商品对象信息
     * @param productIds 多组商品id
     * @return 商品对象集合
     */
    R getProductListByIds(List<Integer> productIds);


    /**
     * 购物车服务 调用该接口 该接口根据多组商品id查询商品对象集合
     * @param productIds 多组商品id
     * @return 商品对象集合
     */
    List<Product> cartList(List<Integer> productIds);



}
