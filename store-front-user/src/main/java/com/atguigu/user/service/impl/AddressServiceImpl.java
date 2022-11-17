package com.atguigu.user.service.impl;

import com.atguigu.pojo.Address;
import com.atguigu.user.mapper.AddressMapper;
import com.atguigu.user.service.AddressService;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/14 10:06
 */
@Service
@Slf4j
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Resource
    private AddressMapper addressMapper;

    /**
     * 根据用户id查询地址信息
     *
     * @param userId 用户id
     * @return 状态信息
     */
    @Override
    public R getAddressListByUserId(Integer userId) {

        //构造条件
        LambdaQueryWrapper<Address> addressLambdaQueryWrapper = new LambdaQueryWrapper<>();
        addressLambdaQueryWrapper.eq(Address::getUserId, userId);

        List<Address> addressList = addressMapper.selectList(addressLambdaQueryWrapper);
        if (addressList.size() == 0) {
            return R.fail("暂无地址信息,请添加");
        }
        //返回地址信息
        return R.ok("地址查询成功",addressList);
    }

    /**
     * 根据用户id去保存地址信息
     *
     * @param address 地址对象
     * @return 返回地址信息集合
     */
    @Override
    public R saveByUserId(Address address) {
        log.info("address={}",address);
        int rows = addressMapper.insert(address);
        if (rows == 0) {
            return R.fail("地址保存失败!");
        }
        //返回地址集合
        return getAddressListByUserId(address.getUserId());
    }

    /**
     * 根据地址id删除地址信息
     *
     * @param id 地址id
     * @return
     */
    @Override
    public R deleteByAddressId(Long id) {
        log.info("地址id={}",id);
        int rows = addressMapper.deleteById(id);
        if (rows == 0) {
            return R.fail("地址删除失败!");
        }
        return R.ok("地址成功删除");
    }


}
