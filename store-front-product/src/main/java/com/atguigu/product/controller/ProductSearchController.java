package com.atguigu.product.controller;

import com.atguigu.clients.CategoryClient;
import com.atguigu.clients.SearchClient;
import com.atguigu.pojo.Product;
import com.atguigu.product.service.ProductService;
import com.atguigu.utils.R;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 搜索服务用的controller
 * @CreateDate 2022/11/15 19:36
 */
@RestController
@RequestMapping("/product")

public class ProductSearchController {

    @Resource
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> allList() {
        return productService.allList();
    }



}
