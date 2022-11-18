package com.atguigu.cart.mapper;

import com.atguigu.pojo.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/17 20:14
 */
@Mapper
public interface CartMapper extends BaseMapper<Cart> {
}
