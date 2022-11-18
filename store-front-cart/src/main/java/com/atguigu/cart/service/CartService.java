package com.atguigu.cart.service;

import com.atguigu.param.CartSaveParam;
import com.atguigu.pojo.Cart;
import com.atguigu.utils.R;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/17 20:16
 */

public interface CartService {
    /**
     * 根据用户id和商品id 将商品对象添加进购物车中
     * @param cartSaveParam 用户id和商品id
     * @return CartVo对象
     */
    R save(CartSaveParam cartSaveParam);

    /**
     * 根据用户id 查询购物车中的商品信息
     * @param userId 用户id
     * @return 封装的CartVo对象集合
     */
    R list(Integer userId);

    /**
     * 根据用户id和商品id 修改更新商品数量
     * @param cart 购物车对象
     * @return 状态信息
     */
    R update(Cart cart);

    /**
     * 根据用户id和商品id 删除对应的购物车
     * @param cartSaveParam 用户id和商品id
     * @return 状态信息
     */
    R remove(CartSaveParam cartSaveParam);
}
