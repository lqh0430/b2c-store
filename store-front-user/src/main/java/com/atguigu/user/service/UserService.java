package com.atguigu.user.service;

import com.atguigu.param.UserCheckParam;
import com.atguigu.param.UserLoginParam;
import com.atguigu.pojo.User;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/13 20:13
 */
public interface UserService extends IService<User> {
    /**
     * 检查账号是否可用业务
     * @param userCheckParam
     * @return
     */
    R check(UserCheckParam userCheckParam);

    /**
     * 注册业务
     * @param user
     * @return
     */
    R regisger(User user);

    /**
     * 登录业务
     * @param userLoginParam 用户登录传来的参数
     * @return 状态信息
     */
    R login(UserLoginParam userLoginParam);
}
