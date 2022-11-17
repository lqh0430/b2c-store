package com.atguigu.search.controller;

import com.atguigu.param.ProductSearchParam;
import com.atguigu.search.service.SearchService;
import com.atguigu.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/16 13:50
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    /**
     * 搜索业务
     * @param productSearchParam 搜索商品的参数(分页)
     * @return 商品对象集合
     */
    @PostMapping("/product")
    public R searchProduct(@RequestBody ProductSearchParam productSearchParam) throws Exception {
        return searchService.searchProduct(productSearchParam);
    }


}
