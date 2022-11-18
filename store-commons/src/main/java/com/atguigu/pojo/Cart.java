package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 购物车实体类 对应 store-cart库的cart表
 * @CreateDate 2022/11/17 16:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cart")
public class Cart implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("product_id")
    private Integer productId;

    private Integer num;

}
