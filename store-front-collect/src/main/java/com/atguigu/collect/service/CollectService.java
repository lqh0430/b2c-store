package com.atguigu.collect.service;

import com.atguigu.pojo.Collect;
import com.atguigu.utils.R;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/17 11:38
 */
public interface CollectService {
    /**
     * 根据用户id和商品id 添加商品到 收藏表中
     * @param collect 收藏实体类对象
     * @return 状态信息
     */
    R save(Collect collect);

    /**
     * 根据用户id和商品id 删除收藏夹中的商品
     * @param collect 收藏实体类对象
     * @return 状态信息
     */
    R delete(Collect collect);

    /**
     * 根据用户id去查询收藏夹中的商品对象集合
     * @param userId 用户id
     * @return 商品对象集合
     */
    R list(Integer userId);
}
