package com.atguigu.product.controller;

import com.atguigu.param.ProductIdParam;
import com.atguigu.param.ProductIdsParam;
import com.atguigu.pojo.Product;
import com.atguigu.product.service.ProductService;
import com.atguigu.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 供购物车服务使用的 商品控制类
 * @CreateDate 2022/11/17 19:59
 */
@RestController
@RequestMapping("/product")
public class ProductCartController {

    @Resource
    private ProductService productService;

    /**
     * 供【购物车服务】调用
     * 根据商品id查询商品对象
     * @param productIdParam 商品id
     * @param result 校验后的结果
     * @return 商品对象
     */
    @PostMapping("/cart/detail")
    public Product cDetail(@RequestBody @Validated ProductIdParam productIdParam, BindingResult result) {
        if (result.hasErrors()) {
            return null;
        }
        //复用之前的查询接口
        R r = productService.detail(productIdParam.getProductID());
        Product product = (Product) r.getData();
        return product;

    }

    /**
     * 供【购物车服务】调用
     * 根据多组商品id查询商品对象集合
     * @param productIdsParam 多组商品id
     * @param result 校验后的结果
     * @return 商品对象集合
     */
    @PostMapping("/cart/list")
    public List<Product> cartList(@RequestBody @Validated ProductIdsParam productIdsParam, BindingResult result) {
        if (result.hasErrors()) {
            //返回空数组
            return new ArrayList<Product>();
        }
        return productService.cartList(productIdsParam.getProductIds());
    }

}
