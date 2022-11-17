package com.atguigu.clients;

import com.atguigu.param.ProductSearchParam;
import com.atguigu.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 商品服务搜索调用的 搜索客户端
 * @CreateDate 2022/11/16 15:16
 */
@FeignClient(value = "search-service")
public interface SearchClient {

    /**
     * 提供给商品服务调用的 搜索功能接口
     * @param productSearchParam 商品搜索需要的参数
     * @return 商品对象集合
     */
    @PostMapping("/search/product")
    R search(@RequestBody ProductSearchParam productSearchParam);

}
