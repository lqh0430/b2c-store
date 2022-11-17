package com.atguigu.collect.service.impl;

import com.atguigu.clients.ProductClient;
import com.atguigu.collect.mapper.CollectMapper;
import com.atguigu.collect.service.CollectService;
import com.atguigu.param.ProductIdsParam;
import com.atguigu.pojo.Collect;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/17 11:38
 */
@Service
@Slf4j
public class CollectServiceImpl implements CollectService {

    /**
     * 注入productClient,提供接口
     */
    @Resource
    private ProductClient productClient;

    @Resource
    private CollectMapper collectMapper;

    /**
     * 根据用户id和商品id 添加商品到 收藏表中
     *
     * @param collect 收藏实体类对象
     * @return 状态信息
     */
    @Override
    public R save(Collect collect) {

        //构造条件
        LambdaQueryWrapper<Collect> collectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        collectLambdaQueryWrapper.eq(Collect::getUserId,collect.getUserId());
        collectLambdaQueryWrapper.eq(Collect::getProductId,collect.getProductId());
        //查询
        Long count = collectMapper.selectCount(collectLambdaQueryWrapper);
        if (count > 0) {
            return R.fail("商品已经在收藏夹中,无需二次添加");
        }
        //更新实体类对象
        Collect newCollect = new Collect();
        newCollect.setUserId(collect.getUserId());
        newCollect.setProductId(collect.getProductId());
        newCollect.setCollectTime(System.currentTimeMillis());
        log.info("更新过后的newCollect={}",newCollect);
        //插入保存到数据库中
        collectMapper.insert(newCollect);

        //封装结果返回
        return R.ok("商品收藏成功");
    }

    /**
     * 根据用户id和商品id 删除收藏夹中的商品
     *
     * @param collect 收藏实体类对象
     * @return 状态信息
     */
    @Override
    public R delete(Collect collect) {

        //构造条件
        LambdaQueryWrapper<Collect> collectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        collectLambdaQueryWrapper.eq(Collect::getUserId,collect.getUserId());
        collectLambdaQueryWrapper.eq(Collect::getProductId,collect.getProductId());
        //查询
        Long count = collectMapper.selectCount(collectLambdaQueryWrapper);
        if (count == 0) {
            return R.fail("商品不存在");
        }
        //删除
        int row = collectMapper.delete(collectLambdaQueryWrapper);
        if (row == 0) {
            return R.fail("删除错误");
        }

        return R.ok("商品收藏删除成功");
    }

    /**
     * 根据用户id去查询收藏夹中的商品对象集合
     *  1.先根据用户id查询出商品id
     *  2.构造条件是按实体类查询出来的，要得到商品id 就需要进行 数据类型转型 即 对象实体类-->Integer
     *  3.将得到的ids作为入参,远程调用商品服务的查询接口
     *  4.封装结果返回
     * @param userId 用户id
     * @return 商品对象集合
     */
    @Override
    public R list(Integer userId) {

        /**
         * -- 合并,根据user_id查询商品信息
         * SELECT sp.*
         * FROM store_product.product sp
         * WHERE sp.product_id in (
         * 	SELECT sc.product_id
         * 	FROM store_collect.collect sc
         * 	WHERE sc.user_id = 13
         * );
         */

        //构造条件
        LambdaQueryWrapper<Collect> collectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        collectLambdaQueryWrapper.eq(Collect::getUserId,userId);
        collectLambdaQueryWrapper.select(Collect::getProductId);
        //查询
        List<Object> objects = collectMapper.selectObjs(collectLambdaQueryWrapper);
        log.info("查询出来的集合={} 运行类型={}",objects,objects.getClass());
        List<Integer> ids = new ArrayList<>();
        //逐条拷贝
        for (Object object : objects) {
            ids.add((Integer) object);
        }
        log.info("拷贝完成的商品ids={} 运行类型={}",ids,ids.getClass());

        //远程调用
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(ids);
        R r = productClient.getProductListByIds(productIdsParam);

        return r;
    }
}
