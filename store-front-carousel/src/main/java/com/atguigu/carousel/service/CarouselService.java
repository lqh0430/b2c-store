package com.atguigu.carousel.service;

import com.atguigu.pojo.Carousel;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/14 14:23
 */
public interface CarouselService extends IService<Carousel> {
    /**
     * 查询轮播图
     *
     */
    R getList();
}
