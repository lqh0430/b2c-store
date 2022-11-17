package com.atguigu.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 分页参数
 * @CreateDate 2022/11/16 13:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParam implements Serializable {

    /**
     * 当前页
     */
    private Integer currentPage = 1;

    /**
     * 页的大小
     */
    private Integer pageSize = 15;

}
