package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 商品实体类 对应store-product库的product表
 * @JsonIgnoreProperties(ignoreUnknown = true) 该注解用于json属性封装时,忽略某些实体类没有属性导致封装失败的问题
 * @CreateDate 2022/11/15 8:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("product")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @JsonProperty("product_id")
    private Integer productId;

    /**
     * 商品名称
     */
    @JsonProperty("product_name")
    private String productName;

    /**
     * 类别id
     */
    @JsonProperty("category_id")
    private Integer categoryId;

    /**
     * 商品标题
     */
    @JsonProperty("product_title")
    private String productTitle;

    /**
     * 商品描述
     */
    @JsonProperty("product_intro")
    private String productIntro;

    /**
     * 商品图片
     */
    @JsonProperty("product_picture")
    private String productPicture;

    /**
     * 商品价格
     */
    @JsonProperty("product_price")
    private Double productPrice;

    /**
     * 售卖价格
     */
    @JsonProperty("product_selling_price")
    private Double productSellingPrice;

    /**
     * 商品数量
     */
    @JsonProperty("product_num")
    private Integer productNum;

    /**
     * 已卖数量
     */
    @JsonProperty("product_sales")
    private Integer productSales;

}
