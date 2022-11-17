package com.atguigu.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @version 1.0
 * @Author LQH02
 * @Description swagger接口文档(没用)
 *              http://localhost:3000/doc.html访问接口文档
 * @CreateDate 2022/11/14 15:50
 */
@Deprecated
@Configuration
@EnableSwagger2 //开启Swagger
@EnableKnife4j //对swagger的增强方案
public class Knife4jConfig {

    @Bean
    public Docket createRestApi() {
        //文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.atguigu.user.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("store-front-user")
                .version("1.0")
                .description("用户服务文档")
                .build();
    }
}