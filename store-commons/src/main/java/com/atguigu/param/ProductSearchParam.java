package com.atguigu.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 商品搜索需要的参数
 * @CreateDate 2022/11/16 13:45
 */
@Data
public class ProductSearchParam extends PageParam implements Serializable {

    /**
     * 搜索的关键字
     */
    private String search;

}
