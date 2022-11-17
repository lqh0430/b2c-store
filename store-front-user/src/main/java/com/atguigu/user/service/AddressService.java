package com.atguigu.user.service;

import com.atguigu.pojo.Address;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/14 10:05
 */
public interface AddressService extends IService<Address> {

    /**
     * 根据用户id查询地址信息
     * @param userId 用户id
     * @return 状态信息
     */
    R getAddressListByUserId(Integer userId);

    /**
     * 根据用户id去保存地址信息
     * @param address 地址对象
     * @return
     */
    R saveByUserId(Address address);

    /**
     * 根据地址id删除地址信息
     * @param id 地址id
     * @return
     */
    R deleteByAddressId(Long id);
}
