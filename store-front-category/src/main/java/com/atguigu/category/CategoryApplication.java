package com.atguigu.category;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/14 18:58
 */
@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.category.mapper")
public class CategoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryApplication.class,args);
    }
}
