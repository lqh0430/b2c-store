package com.atguigu.product.service.impl;

import com.atguigu.clients.CategoryClient;
import com.atguigu.clients.SearchClient;
import com.atguigu.param.ProductHotParam;
import com.atguigu.param.ProductParamInteger;
import com.atguigu.param.ProductPromoParam;
import com.atguigu.param.ProductSearchParam;
import com.atguigu.pojo.Category;
import com.atguigu.pojo.Product;
import com.atguigu.pojo.ProductPicture;
import com.atguigu.product.mapper.ProductMapper;
import com.atguigu.product.mapper.ProductPictureMapper;
import com.atguigu.product.service.ProductService;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/15 8:43
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    /**
     * 注入searchClient客户端,供商品服务远程调用其注册的接口
     */
    @Resource
    private SearchClient searchClient;

    /**
     * 注入categoryClient客户端,需要远程调用其接口 (引入该注解需要在启动类开启该功能)
     */
    @Resource
    private CategoryClient categoryClient;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductPictureMapper productPictureMapper;

    /**
     * 根据 【单类别名称】 去查询商品信息
     *  1.根据类别名称 调用feign客户端访问 类别服务获取类别对象信息
     *  2.调用返回成功,继续根据返回的类别对象信息（类别id）去查询商品对象信息
     *  3.按销售量倒序,每7条分页
     *  4.封装结果信息
     * @param categoryName 类别名称
     * @return 商品对象集合
     */
    @Override
    public R getProductByCategoryName(String categoryName) {

        //远程调用
        R r = categoryClient.getCategoryByName(categoryName);
        if (r.getCode().equals(R.FAIL_CODE)) {
            log.info("类别接口远程调用失败");
            return r;
        }
        //这样写会报错
        /*Category category = (Category) r.getData();
        Integer categoryId = category.getCategoryId();*/

        //类别服务中 data=category实体类 --> 但是经过feign远程调用后,变成json ---> product服务 jackson会把json变成LinkedHashMap
        LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) r.getData();
        log.info("linkedHashMap={}",linkedHashMap);
        //获取data中 category实体类的 categoryId
        Integer categoryId = (Integer) linkedHashMap.get("category_id");
        log.info("类别id={}",categoryId);


        //构造条件
        LambdaQueryWrapper<Product> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productLambdaQueryWrapper.eq(Product::getCategoryId,categoryId);
        productLambdaQueryWrapper.orderByDesc(Product::getProductSales);
        //构造分页器
        Page<Product> productPage = new Page<>(1, 7);
        //查询(返回的是包装数据,包括对应的商品对象集合和分页参数[总条数,总页数...])
        Page<Product> page = productMapper.selectPage(productPage, productLambdaQueryWrapper);
        log.info("page={}",page);

        //封装结果信息
        List<Product> productList = page.getRecords();
        log.info("productList={}",productList);

        return R.ok("单类别名称热门商品查询成功",productList);
    }

    /**
     * 根据 热门商品的多类别名称去查询商品信息
     *
     * @param productHotParam 类别名集合
     * @return 商品对象集合
     */
    @Override
    public R getProductByCategoryNames(ProductHotParam productHotParam) {

       //远程调用
        R r = categoryClient.getCategoryIdListByName(productHotParam);
        if (r.getCode().equals(R.FAIL_CODE)) {
            return R.fail("多类别接口远程调用失败");
        }

        log.info("r.getData()={} 运行类型={}",r.getData(),r.getData().getClass());
        //获取返回的categoryIds集合
        List<Object> categoryIds = (List<Object>) r.getData();
        log.info("categoryIds={}",categoryIds);

        //构造条件
        LambdaQueryWrapper<Product> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productLambdaQueryWrapper.in(Product::getCategoryId,categoryIds);
        productLambdaQueryWrapper.orderByDesc(Product::getProductSales);
        //构造分页器
        Page<Product> productPage = new Page<>(1, 7);
        //查询
        Page<Product> page = productMapper.selectPage(productPage, productLambdaQueryWrapper);
        log.info("page={}",page);

        //封装结果返回
        List<Product> productList = page.getRecords();
        log.info("productList={}",productList);

        return R.ok("多类别热门商品查询成功",productList);
    }

    /**
     * 查询 全部商品对应的类别信息集合
     *
     * @return 类别集合
     */
    @Override
    public R getProduct() {
        R r = categoryClient.list();
        if (r.getCode().equals(R.FAIL_CODE)) {
            return R.fail("查询失败");
        }
        log.info("r={}",r);
        return r;
    }

    /**
     * 分页查询商品对象集合
     *
     * @param productParamInteger 分页参数
     * @return 商品对象集合和总条数
     */
    @Override
    public R byCategory(ProductParamInteger productParamInteger) {

        //1.拆分获取参数
        List<Integer> categoryIDs = productParamInteger.getCategoryID();
        Integer currentPage = productParamInteger.getCurrentPage();
        Integer pageSize = productParamInteger.getPageSize();

        //2.构造条件
        LambdaQueryWrapper<Product> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (categoryIDs != null && categoryIDs.size() > 0) {
            productLambdaQueryWrapper.in(Product::getCategoryId,categoryIDs);
        }

        //3.构造分页
        Page<Product> productPage = new Page<>(currentPage, pageSize);
        //4.查询
        Page<Product> page = productMapper.selectPage(productPage, productLambdaQueryWrapper);

        //5.封装结果返回
        List<Product> productList = page.getRecords();
        long total = page.getTotal();
        log.info("查询商品集合={} 总条数={}",productList,total);

        return R.ok(null,productList,total);
    }

    /**
     * 查询全部对象集合
     *
     * @param productParamInteger
     * @return
     */
    @Override
    public R all(ProductParamInteger productParamInteger) {
        return byCategory(productParamInteger);
    }

    /**
     * 根据商品id查询商品对象信息
     *
     * @param productID 商品id
     * @return 商品对象
     */
    @Override
    public R detail(Integer productID) {

        log.info("productId={}",productID);

        Product product = productMapper.selectById(productID);
        if (product == null ) {
            return R.fail("查询商品不存在");
        }
        log.info("product={}",product);

        return R.ok("商品"+productID+"查询成功",product);
    }

    /**
     * 根据商品id查询商品图片对象集合
     *
     * @param productID 商品id
     * @return 商品图片对象集合
     */
    @Override
    public R pictures(Integer productID) {
        //构造条件
        LambdaQueryWrapper<ProductPicture> productPictureLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productPictureLambdaQueryWrapper.eq(ProductPicture::getProductId,productID);
        //查询
        List<ProductPicture> productPictureList = productPictureMapper.selectList(productPictureLambdaQueryWrapper);
        log.info("productPictureList={}",productPictureList);
        //封装结果返回
        return R.ok(productPictureList);
    }

    /**
     * 搜索服务调用的接口,查询全部商品（用于数据同步）
     *
     * @return
     */
    @Override
    public List<Product> allList() {
        List<Product> productList = productMapper.selectList(null);
        return productList;
    }

    /**
     * 商品服务 调用搜索服务接口 进行 商品搜索
     *
     * @param productSearchParam 商品搜索参数
     * @return 商品对象集合
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {

        R r = searchClient.search(productSearchParam);

        return r;
    }


}
