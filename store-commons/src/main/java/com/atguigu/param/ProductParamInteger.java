package com.atguigu.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 全部商品所需参数
 * @CreateDate 2022/11/15 13:59
 */
@Data
public class ProductParamInteger extends PageParam implements Serializable {


    private List<Integer> categoryID;


}
