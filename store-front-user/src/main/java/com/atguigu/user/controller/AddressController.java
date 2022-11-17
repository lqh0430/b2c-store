package com.atguigu.user.controller;

import com.atguigu.param.AddressListParam;
import com.atguigu.param.AddressRemoveParam;
import com.atguigu.pojo.Address;
import com.atguigu.user.service.AddressService;
import com.atguigu.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/14 10:00
 */
@RestController
@RequestMapping("/user/address")
public class AddressController {

    @Resource
    private AddressService addressService;


    /**
     * 地址查询
     * @param addressListParam 查询地址所需的参数(这里根据用户id查询)
     * @param result 内部校验后的结果
     * @return 状态信息
     */
    @PostMapping("/list")
    public R list(@RequestBody @Validated AddressListParam addressListParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常,地址查询失败");
        }

        return addressService.getAddressListByUserId(addressListParam.getUserId());
    }


    /**
     * 地址保存(根据用户id)
     * @param address 地址对象
     * @param result 内部校验后的结果
     * @return 地址集合
     */
    @PostMapping("/save")
    public R save(@RequestBody @Validated Address address, BindingResult result) {

        if (result.hasErrors()) {
            return R.fail("参数异常,地址保存失败");
        }

        return addressService.saveByUserId(address);

    }

    /**
     * 根据地址id删除地址
     * @param addressRemoveParam 删除地址所需的参数
     * @param result 校验后的结果
     * @return 状态信息
     */
    @PostMapping("/remove")
    public R remove(@RequestBody @Validated AddressRemoveParam addressRemoveParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常,地址删除失败");
        }
        return addressService.deleteByAddressId(addressRemoveParam.getId());
    }

}
