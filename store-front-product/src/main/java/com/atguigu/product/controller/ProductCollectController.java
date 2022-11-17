package com.atguigu.product.controller;

import com.atguigu.param.ProductIdsParam;
import com.atguigu.product.service.ProductService;
import com.atguigu.utils.R;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 商品收藏控制类
 * @CreateDate 2022/11/17 14:13
 */
@RestController
@RequestMapping("/product")
public class ProductCollectController {

    @Resource
    private ProductService productService;

    /**
     * 根据多组商品id查询商品对象信息 (该接口供收藏服务调用)
     * @param productIdsParam 多组商品id参数
     * @param result 校验后的结果
     * @return 商品对象集合
     */
    @Cacheable(value = "list.product", key = "#productIdsParam.getProductIds()")
    @PostMapping("/collect/list")
    public R getProductListByIds(@RequestBody @Validated ProductIdsParam productIdsParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("商品id集合不能为空");
        }
        return productService.getProductListByIds(productIdsParam.getProductIds());
    }

}
