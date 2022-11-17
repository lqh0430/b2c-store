package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 类别 实体类对应store-category库的category表
 * @CreateDate 2022/11/14 19:11
 */

@Data
@TableName("category")
public class Category implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("category_name")
    private String categoryName;

}
