package com.atguigu.carousel.service.impl;

import com.atguigu.carousel.mapper.CarouselMapper;
import com.atguigu.carousel.service.CarouselService;
import com.atguigu.pojo.Carousel;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/14 14:23
 */
@Service
@Slf4j
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    /**
     * 查询轮播图
     * 根据优先级查询出轮播图集合
     *
     */
    @Override
    public R getList() {
        //构造条件
        LambdaQueryWrapper<Carousel> carouselLambdaQueryWrapper = new LambdaQueryWrapper<>();
        carouselLambdaQueryWrapper.orderByDesc(Carousel::getPriority);

        List<Carousel> carouselList = carouselMapper.selectList(carouselLambdaQueryWrapper);
        log.info("carouselList={}",carouselList);

        //按每6条切割一次集合
        List<Carousel> collect = carouselList.stream().limit(6).collect(Collectors.toList());
        log.info("切割后的轮播图集合={}",collect);

        return R.ok(collect);

    }
}
