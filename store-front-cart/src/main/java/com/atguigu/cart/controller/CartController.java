package com.atguigu.cart.controller;

import com.atguigu.cart.service.CartService;
import com.atguigu.param.CartListParam;
import com.atguigu.param.CartSaveParam;
import com.atguigu.pojo.Cart;
import com.atguigu.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/17 20:14
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    /**
     * 购物车添加接口
     * 根据用户id和商品id添加进购物车中
     * @param cartSaveParam 用户id和商品id
     * @param result 校验后的结果
     * @return CartVo对象
     */
    @PostMapping("/save")
    public R save(@RequestBody @Validated CartSaveParam cartSaveParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("购物车添加参数异常");
        }
        return cartService.save(cartSaveParam);
    }

    /**
     * 购物车查询展示接口
     * 根据用户id查询购物车内的商品集合
     * @param cartListParam 用户id
     * @param result 校验后的结果
     * @return 封装的CartVo对象集合
     */
    @PostMapping("/list")
    public R list(@RequestBody @Validated CartListParam cartListParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("用户id异常");
        }
        return cartService.list(cartListParam.getUserId());
    }

    /**
     * 购物车更新修改接口
     * 根据用户id和商品id修改商品数量
     * @param cart 购物车对象
     * @return 状态信息
     */
    @PostMapping("/update")
    public R update(@RequestBody Cart cart) {
        return cartService.update(cart);
    }

    /**
     * 购物车删除接口
     * 根据用户id和商品id删除对应购物车
     * @param cartSaveParam 用户id和商品id
     * @param result 校验后的结果
     * @return 状态信息
     */
    @PostMapping("/remove")
    public R remove(@RequestBody @Validated CartSaveParam cartSaveParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("用户或商品参数异常,删除失败");
        }
        return cartService.remove(cartSaveParam);
    }

}
