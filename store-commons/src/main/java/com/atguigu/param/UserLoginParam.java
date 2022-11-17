package com.atguigu.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 用户登录的参数
 * @CreateDate 2022/11/14 9:20
 */
@Data
public class UserLoginParam implements Serializable {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

}
