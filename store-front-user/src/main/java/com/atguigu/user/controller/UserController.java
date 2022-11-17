package com.atguigu.user.controller;

import com.atguigu.param.UserCheckParam;
import com.atguigu.param.UserLoginParam;
import com.atguigu.pojo.User;
import com.atguigu.user.service.UserService;
import com.atguigu.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/13 20:08
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 检查账号是否可用
     * @param userCheckParam 用户账号传来的参数，里面包含内部后端校验注解
     * @param result 获取校验结果的实体对象
     * @return 状态信息
     */
    @PostMapping("/check")
    public R check(@RequestBody @Validated UserCheckParam userCheckParam, BindingResult result) {
        boolean b = result.hasErrors();
        if (b) {
            return R.fail("账号为Null,不可使用");
        }
        return userService.check(userCheckParam);
    }

    /**
     * 注册业务
     * @param user 用户实体
     * @param result
     * @return
     */
    @PostMapping("/register")
    public R check(@RequestBody @Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常,注册失败!");
        }
        return userService.regisger(user);
    }

    /**
     * 登录业务
     * @param userLoginParam 用户登录传来的参数
     * @param result 后端校验后的结果
     * @return 状态信息
     */
    @PostMapping("/login")
    public R login(@RequestBody @Validated UserLoginParam userLoginParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常,登录失败!");
        }
        return userService.login(userLoginParam);
    }

}
