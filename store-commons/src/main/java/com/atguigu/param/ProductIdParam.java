package com.atguigu.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/17 20:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductIdParam implements Serializable {

    @NotNull
    private Integer productID;

}
