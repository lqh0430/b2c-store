package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 商品图标实体类 对应 store-product库里的product-picture表
 * @CreateDate 2022/11/15 15:21
 */
@Data
@TableName("product_picture")
public class ProductPicture implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("product_picture")
    private String productPicture;

    private String intro;

}
