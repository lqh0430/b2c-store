package com.atguigu.clients;

import com.atguigu.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 搜索服务需要调用的商品客户端
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

}
