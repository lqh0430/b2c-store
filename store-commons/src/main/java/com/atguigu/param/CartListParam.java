package com.atguigu.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/18 14:57
 */
@Data
public class CartListParam implements Serializable {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

}
