package com.atguigu.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/14 9:57
 */
@Data
public class AddressListParam implements Serializable {

    @NotNull
    @JsonProperty("user_id") //该注解是将userId序列化成user_id,接口测试时参数就必须要按user_id提交,返回给前端也是user_id
    private Integer userId;
}
