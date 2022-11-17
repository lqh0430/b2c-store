package com.atguigu.clients;

import com.atguigu.param.ProductHotParam;
import com.atguigu.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 注册 类别的调用接口, 供其他方调用
 *  @FeignClient("category-service") 调用的是类别服务
 * @CreateDate 2022/11/15 9:08
 */
@FeignClient("category-service")
public interface CategoryClient {


    /**
     * 远程调用 根据类别名查询类别信息
     * @param categoryName 类别名
     * @return 类别对象
     */
    @GetMapping("/category/promo/{categoryName}")
    R getCategoryByName(@PathVariable("categoryName") String categoryName);


    /**
     * 根据热门类别名称集合 查询对应的热门类别id集合
     * @param productHotParam 传入的热门商品类别名称
     * @return 类别id集合
     */
    @PostMapping("/category/hots")
    R getCategoryIdListByName(@RequestBody ProductHotParam productHotParam);


    /**
     * 类别查询
     * @return 类别对象集合
     */
    @GetMapping("/category/list")
    R list();

}
