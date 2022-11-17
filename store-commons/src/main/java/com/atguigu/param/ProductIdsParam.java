package com.atguigu.param;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/17 14:12
 */
@Data
public class ProductIdsParam implements Serializable {

    /**
     * 多组商品id
     */
    @NotEmpty
    private List<Integer> productIds;

}
