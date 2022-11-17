package com.atguigu.product;

import com.atguigu.clients.CategoryClient;
import com.atguigu.clients.SearchClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @EnableFeignClients(clients = {CategoryClient.class}) 开启feign客户端,可远程调用其接口
 * @EnableCaching 开启缓存功能支持
 * @CreateDate 2022/11/14 18:57
 */
@EnableFeignClients(clients = {CategoryClient.class, SearchClient.class})
@MapperScan(basePackages = "com.atguigu.product.mapper")
@SpringBootApplication
@EnableCaching
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }
}
