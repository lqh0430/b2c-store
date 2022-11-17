package com.atguigu.clients;

import com.atguigu.param.ProductIdsParam;
import com.atguigu.pojo.Product;
import com.atguigu.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/15 17:24
 */
@FeignClient(value = "product-service")
public interface ProductClient {

    /**
     * 搜索服务调用,查询全部商品,用于进行数据同步
     * @return
     */
    @GetMapping("/product/list")
    List<Product> allList();

    /**
     * 收藏服务调用, 根据多组商品id查询商品信息
     * @param productIdsParam 多组商品id
     * @return 商品对象集合
     */
    @PostMapping("/product/collect/list")
    R getProductListByIds(@RequestBody @Validated ProductIdsParam productIdsParam);


}
