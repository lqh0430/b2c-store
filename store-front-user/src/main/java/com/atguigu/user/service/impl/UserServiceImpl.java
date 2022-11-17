package com.atguigu.user.service.impl;

import com.atguigu.constants.UserConstants;
import com.atguigu.param.UserCheckParam;
import com.atguigu.param.UserLoginParam;
import com.atguigu.pojo.User;
import com.atguigu.user.mapper.UserMapper;
import com.atguigu.user.service.UserService;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/13 20:14
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public R check(UserCheckParam userCheckParam) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName,userCheckParam.getUserName());


        /*QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userCheckParam.getUserName());*/

        Long total = userMapper.selectCount(userLambdaQueryWrapper);

        log.info("total={}",total);
        if (total > 0) {
            return R.fail("账号已存在,不可使用");
        }

        return R.ok("账号不存在,可以注册");
    }

    /**
     * 注册业务
     *  1.检查账号是否存在
     *      1.1存在,直接返回
     *      1.2不存在,继续
     *  2.对密码进行MD5加密处理
     *  3.保存到数据库
     *  4.返回状态信息
     * @param user
     * @return
     */
    @Override
    public R regisger(User user) {
        //构造条件
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName,user.getUserName());

        Long total = userMapper.selectCount(userLambdaQueryWrapper);
        if (total > 0) {
            return R.fail("账号已存在,不可注册");
        }
        //对密码加密
        String newPwd = MD5Util.encode(user.getPassword() + UserConstants.USER_SALT);
        log.info("加密后的新密码={}",newPwd);
        user.setPassword(newPwd);

        //保存进数据库
        int affectedRow = userMapper.insert(user);
        if (affectedRow == 0) {
            return R.fail("注册失败,请稍后再试");
        }

        return R.ok("注册成功");
    }

    /**
     * 登录业务
     *  0.对明文密码进行加密处理,构造查询条件
     *  1.查询用户是否已经注册
     *      1.1数据库没有用户信息,返回请注册
     *      1.2有,则继续
     *  2.返回user对象
     *  3.返回状态信息
     * @param userLoginParam 用户登录传来的参数
     * @return 返回user对象信息
     */
    @Override
    public R login(UserLoginParam userLoginParam) {

        String newPwd = MD5Util.encode(userLoginParam.getPassword() + UserConstants.USER_SALT);

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName,userLoginParam.getUserName());
        userLambdaQueryWrapper.eq(User::getPassword,newPwd);

        User user = userMapper.selectOne(userLambdaQueryWrapper);
        log.info("user={}",user);
        if (user == null) {
            return R.fail("账号或密码错误");
        }
        user.setPassword(null);
        //返回用户对象信息
        return R.ok("登录成功",user);
    }
}
