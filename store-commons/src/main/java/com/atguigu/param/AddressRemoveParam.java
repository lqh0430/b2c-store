package com.atguigu.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/14 11:18
 */
@Data
public class AddressRemoveParam implements Serializable {

    @NotNull
    private Long id;

}
