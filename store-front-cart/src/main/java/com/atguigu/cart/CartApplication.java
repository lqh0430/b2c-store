package com.atguigu.cart;

import com.atguigu.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/17 15:55
 */
@EnableFeignClients(clients = ProductClient.class)
@MapperScan(basePackages = "com.atguigu.cart.mapper")
@SpringBootApplication
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class,args);
    }
}
