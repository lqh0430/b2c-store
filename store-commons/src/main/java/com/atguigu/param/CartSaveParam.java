package com.atguigu.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 购物车添加需要的参数
 * @CreateDate 2022/11/17 20:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartSaveParam implements Serializable {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    @NotNull
    @JsonProperty("product_id")
    private Integer productId;


}
