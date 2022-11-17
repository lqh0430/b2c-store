package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 轮播图实体类对应store-carousel库的carousel表
 * @CreateDate 2022/11/14 13:43
 */
@Data
@TableName("carousel")
public class Carousel implements Serializable {

    private static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @JsonProperty("carousel_id")
    private Integer carouselId;

    private String imgPath;

    private String describes;

    @JsonProperty("product_id")
    private Integer productId;

    private Integer priority;

}
