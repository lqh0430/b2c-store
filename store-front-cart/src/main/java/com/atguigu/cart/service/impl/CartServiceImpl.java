package com.atguigu.cart.service.impl;

import com.atguigu.cart.mapper.CartMapper;
import com.atguigu.cart.service.CartService;
import com.atguigu.clients.ProductClient;
import com.atguigu.param.CartSaveParam;
import com.atguigu.param.ProductIdParam;
import com.atguigu.param.ProductIdsParam;
import com.atguigu.pojo.Cart;
import com.atguigu.pojo.CartVo;
import com.atguigu.pojo.Product;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/17 20:17
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    /**
     * 注入product客户端
     */
    @Resource
    private ProductClient productClient;

    @Resource
    private CartMapper cartMapper;

    /**
     * 根据用户id和商品id 将商品对象添加进购物车中
     * 1. 购物车服务 远程调用(product/cart/detail) 根据商品id查询商品对象的接口 ,将查询的product返回给购物车服务
     * 2. 购物车服务
     *    2.1 检查商品是否有库存
     *    2.2 检查购物车之前是否有添加过
     *       1. 添加过,数量+1,返回002状态码表示已经添加过
     *          . 添加超过数量,返回003状态码表示超出数量
     *       2. 没添加过,初始设为1
     *    2.3 构建购物车对象,封装好属性，插入保存到数据库中
     * 3. 结果以Vo类型封装返回
     * @param cartSaveParam 用户id和商品id
     * @return CartVo对象 001添加成功 002已经添加 003超过数量限制/无库存
     */
    @Override
    public R save(CartSaveParam cartSaveParam) {
        log.info("cartSaveParam={}",cartSaveParam);
        //远程调用
        Integer productId = cartSaveParam.getProductId();
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(productId);
        Product product = productClient.cDetail(productIdParam);
        log.info("远程调用获取的product={}",product);

        if (product == null) {
            return R.fail("商品已被删除,无法添加");
        }
        //检查商品库存
        Integer productNum = product.getProductNum();
        if (productNum == 0) {
            R ok = R.ok("商品库存为0,不能添加");
            ok.setCode("003");
            return ok;
        }

        //检查购物车是否添加过
        //构造条件
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        cartLambdaQueryWrapper.eq(Cart::getUserId,cartSaveParam.getUserId());
        cartLambdaQueryWrapper.eq(Cart::getProductId,cartSaveParam.getProductId());
        //查询
        Cart cart = cartMapper.selectOne(cartLambdaQueryWrapper);
        if (cart != null) {
            //添加过商品,原数量+1即可
            cart.setNum(cart.getNum()+1);
            //更新
            cartMapper.updateById(cart);
            //返回结果
            R ok = R.ok("购物车存在该商品,数量+1");
            ok.setCode("002");
            return ok;
        }

        //添加到购物车
        cart = new Cart();
        cart.setUserId(cartSaveParam.getUserId());
        cart.setProductId(cartSaveParam.getProductId());
        cart.setNum(1);
        //插入保存到数据库中
        cartMapper.insert(cart);

        //封装结果,以Vo对象返回
        CartVo cartVo = new CartVo(product, cart);
        log.info("cartVo={}",cartVo);

        return R.ok("购物车添加成功",cartVo);
    }

    /**
     * 根据用户id 查询购物车中的商品信息
     *  1.根据userId查询购物车表
     *    1.1 购物车为空返回提示信息
     *    1.2不为空,查询出商品id集合
     *  2.根据该productIds 远程调用接口 查询product表返回商品对象集合
     *  3.将集合转成map key=商品Id value=product
     *  4.封装成CartVo返回
     * @param userId 用户id
     * @return 封装的CartVo对象集合
     */
    @Override
    public R list(Integer userId) {

        //构造条件
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        cartLambdaQueryWrapper.eq(Cart::getUserId,userId);
        //查询购物车对象
        List<Cart> carts = cartMapper.selectList(cartLambdaQueryWrapper);

        if (carts == null || carts.size() == 0) {
            //必须返回空数据
            carts = new ArrayList<>();
            return R.ok("购物车空空如也",carts);
        }
        //遍历cart获取出productId,集合装起来
        List<Integer> productIds = new ArrayList<>();
        for (Cart cart : carts) {
            productIds.add(cart.getProductId());
        }

        //远程调用
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productIdsParam);
        log.info("productList={}",productList);

        //集合->map stream流收集
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //封装VO对象
        ArrayList<CartVo> cartVoList = new ArrayList<>();
        for (Cart cart : carts) {
            CartVo cartVo = new CartVo(productMap.get(cart.getProductId()), cart);
            cartVoList.add(cartVo);
        }
        log.info("cartVoList={}",cartVoList);

        return R.ok("购物车查询成功",cartVoList);
    }

    /**
     * 根据用户id和商品id 修改更新商品数量
     *
     * @param cart 购物车对象
     * @return 状态信息
     */
    @Override
    public R update(Cart cart) {

        //远程调用
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cart.getProductId());
        Product product = productClient.cDetail(productIdParam);

        if (cart.getNum() > product.getProductNum()) {
            return R.fail("数量修改失败,商品库存不足");
        }

        //构造条件
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        cartLambdaQueryWrapper.eq(Cart::getUserId,cart.getUserId());
        cartLambdaQueryWrapper.eq(Cart::getProductId,cart.getProductId());
        //查询
        Cart newCart = cartMapper.selectOne(cartLambdaQueryWrapper);
        //更新数量
        newCart.setNum(cart.getNum());
        log.info("newCart={}",newCart);
        //更新购物车对象
        int row = cartMapper.updateById(newCart);
        if (row == 0) {
            return R.fail("购物车商品数量更新失败");
        }

        return R.ok("购物车商品数量更新成功");
    }

    /**
     * 根据用户id和商品id 删除对应的购物车
     *
     * @param cartSaveParam 用户id和商品id
     * @return 状态信息
     */
    @Override
    public R remove(CartSaveParam cartSaveParam) {

        //构造条件
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        cartLambdaQueryWrapper.eq(Cart::getUserId,cartSaveParam.getUserId());
        cartLambdaQueryWrapper.eq(Cart::getProductId,cartSaveParam.getProductId());
        //删除
        int row = cartMapper.delete(cartLambdaQueryWrapper);
        if (row == 0) {
            return R.fail("删除异常");
        }
        return R.ok("购物车商品成功");
    }


}
