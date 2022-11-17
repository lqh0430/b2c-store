package com.atguigu.param;

import com.atguigu.pojo.Category;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 热门商品参数接收对象
 * @CreateDate 2022/11/14 19:39
 */
@Data
public class ProductHotParam implements Serializable {

    @NotEmpty
    private List<String> categoryName;
}
