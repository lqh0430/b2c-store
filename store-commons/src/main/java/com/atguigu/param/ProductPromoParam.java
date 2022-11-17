package com.atguigu.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 类别热门商品接收参数
 * @CreateDate 2022/11/15 8:39
 */
@Data
public class ProductPromoParam implements Serializable {

    @NotBlank
    private String categoryName;

}
