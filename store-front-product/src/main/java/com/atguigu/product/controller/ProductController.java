package com.atguigu.product.controller;

import com.atguigu.clients.CategoryClient;
import com.atguigu.clients.SearchClient;
import com.atguigu.param.ProductHotParam;
import com.atguigu.param.ProductParamInteger;
import com.atguigu.param.ProductPromoParam;
import com.atguigu.param.ProductSearchParam;
import com.atguigu.pojo.Product;
import com.atguigu.pojo.ProductPicture;
import com.atguigu.product.service.ProductService;
import com.atguigu.utils.R;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @version 1.0
 * @Author LQH02
 * @Description promo
 * @CreateDate 2022/11/15 8:41
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 根据 单类别名称去查询商品信息
     * @param productPromoParam 传入的商品参数
     * @param result 内部校验的结果
     * @return 商品对象集合
     * @Cacheable 添加缓存 value是缓存分区 key是方法中传入的参数 cacheManager是过期时间(不写默认从配置类中获取)
     */
    @Cacheable(value = "list.product",key = "#productPromoParam.categoryName",cacheManager = "cacheManagerDay")
    @PostMapping("/promo")
    public R getProductByCategoryName(@RequestBody @Validated ProductPromoParam productPromoParam, BindingResult result) {

        if (result.hasErrors()) {
            return R.fail("参数异常,查询商品失败");
        }

        return productService.getProductByCategoryName(productPromoParam.getCategoryName());
    }

    /**
     * 根据 多类别名称去查询商品对象信息
     * @param productHotParam 热门商品的类别名集合
     * @param result 校验后的结果
     * @return 商品对象集合
     */
    @Cacheable(value = "list.product", key = "#productHotParam.categoryName",cacheManager = "cacheManagerHour")
    @PostMapping("/hots")
    public R getProductByCategoryNames(@RequestBody @Validated ProductHotParam productHotParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常,查询失败");
        }
        return productService.getProductByCategoryNames(productHotParam);
    }

    /**
     * 查询全部商品对应的类别信息集合
     * @return 类别集合
     * key="#root.methodName" 获取方法名查询
     */
    @Cacheable(value = "list.category",key = "#root.methodName",cacheManager = "cacheManagerDay")
    @PostMapping("/category/list")
    public R getProduct() {
        return productService.getProduct();
    }

    /**
     * 根据多类别id 分页查询全部商品
     * 缓存分区value=list.product 缓存查询key=填入的参数
     * @param productParamInteger 分页查询的参数
     * @return 商品对象集合和总条数
     */
    @Cacheable(value = "list.product",key = "#productParamInteger.categoryID+'-'+" +
                                            "#productParamInteger.currentPage+'-'+" +
                                            "#productParamInteger.pageSize")
    @PostMapping("/bycategory")
    public R byCategory(@RequestBody ProductParamInteger productParamInteger) {
        return productService.byCategory(productParamInteger);
    }

    /**
     * 查询全部商品 （复用分页接口）
     * @param productParamInteger
     * @return
     */
    @Cacheable(value = "list.product",key = "#productParamInteger.currentPage+'-'+" +
                                            "#productParamInteger.pageSize")
    @PostMapping("/all")
    public R all(@RequestBody ProductParamInteger productParamInteger) {
        return productService.all(productParamInteger);
    }

    /**
     * 商品详情 根据商品id查询商品对象信息
     * 禁用缓存!!!不然前端页面会直接查询该缓存
     * @param param 接收前端提交传来的商品id
     * @return 商品对象
     */
    //@Cacheable(value = "product",key = "#root.methodName",cacheManager = "cacheManagerDay")
    @PostMapping("/detail")
    public R detail(@RequestBody Map<String,Integer> param) {
        Integer productID = param.get("productID");

        if (productID == null) {
            return R.fail("商品id异常");
        }
        return productService.detail(productID);
    }

    /**
     * 商品图片详情 根据商品id查询商品图片对象信息
     * 禁用缓存!!!不然前端页面会直接查询该缓存
     * @param param 接收前端提交传来的商品id
     * @return 商品图片对象集合
     */
    //@Cacheable(value = "picture",key = "#root.methodName",cacheManager = "cacheManagerDay")
    @PostMapping("/pictures")
    public R pictures(@RequestBody Map<String,Integer> param) {

        Integer productID = param.get("productID");

        if (productID == null) {
            return R.fail("商品id异常");
        }

        return productService.pictures(productID);
    }


    /**
     * 商品服务 调用搜索服务接口
     * @param productSearchParam
     * @return
     */
    @PostMapping("/search")
    public R search(@RequestBody ProductSearchParam productSearchParam) {
        return productService.search(productSearchParam);
    }

}
