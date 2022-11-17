package com.atguigu.collect.controller;

import com.atguigu.collect.service.CollectService;
import com.atguigu.pojo.Collect;
import com.atguigu.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/17 11:29
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    private CollectService collectService;

    /**
     * 根据用户id和商品id添加到收藏表中
     * @param collect 收藏实体类对象
     * @return 状态信息
     */
    @PostMapping("/save")
    public R save(@RequestBody Collect collect) {

        return collectService.save(collect);

    }

    /**
     * 根据用户id和商品id 删除收藏夹中的商品
     * @param collect 收藏实体类对象
     * @return 状态信息
     */
    @PostMapping("/remove")
    public R delete(@RequestBody Collect collect) {
        return collectService.delete(collect);
    }

    /**
     * 根据用户id查询去查询商品对象信息
     * @param collect 收藏实体类
     * @return 商品对象集合
     */
    @PostMapping("/list")
    public R list(@RequestBody Collect collect) {
        return collectService.list(collect.getUserId());
    }

}
