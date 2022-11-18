package com.atguigu.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 购物车Vo返回类
 * @CreateDate 2022/11/17 19:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartVo implements Serializable {

    /**
     * 购物车id
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer productID;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImg;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品购买数量
     */
    private Integer num;

    /**
     * 商品限购数量
     */
    private Integer maxNum;

    /**
     * 是否勾选
     */
    private Boolean check = false;

    /**
     * 传入product和cart对象,将属性都封装成CartVo类型
     * @param product 商品对象
     * @param cart 购物车对象
     */
    public CartVo(Product product,Cart cart) {
        this.id = cart.getId();
        this.productID = product.getProductId();
        this.productName = product.getProductName();
        this.productImg = product.getProductPicture();
        this.price = product.getProductSellingPrice();
        this.num = cart.getNum();
        this.maxNum = product.getProductNum();
        this.check = false;
    }
}
