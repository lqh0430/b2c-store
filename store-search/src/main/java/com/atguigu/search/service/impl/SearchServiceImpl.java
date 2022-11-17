package com.atguigu.search.service.impl;

import com.atguigu.param.ProductSearchParam;
import com.atguigu.pojo.Product;
import com.atguigu.search.service.SearchService;
import com.atguigu.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 * @CreateDate 2022/11/16 13:57
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 根据搜索关键字分页查询商品
     *  1.检查传入参数中的关键字属性
     *   1.1如果关键字为null,则进行商品全部查询
     *   1.2关键字不为null,则进行关键字查询
     *  2.对关键字和分页设置
     *  3.对es库中返回的数据一层一层获取
     *   3.1数据从es获取出来需要进行模型转换,productDoc-->product
     *   3.2商品加入到商品集合中
     *  4.封装结果,将商品对象集合返回
     * @param productSearchParam 商品搜索参数(搜索关键字+分页)
     * @return 商品对象集合
     */
    @Override
    public R searchProduct(ProductSearchParam productSearchParam) {

        log.info("productSearchParam={}",productSearchParam);

        SearchRequest searchRequest = new SearchRequest("product");

        //获取关键字
        String search = productSearchParam.getSearch();

        if (StringUtils.isEmpty(search)) {
            //关键字为null,则查询条件为全部匹配查询
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        }else {
            //添加查询all字段
            searchRequest.source().query(QueryBuilders.matchQuery("all",search));
        }
        //设置添加分页查询
        searchRequest.source().from((productSearchParam.getCurrentPage() - 1) * productSearchParam.getPageSize());
        searchRequest.source().size(productSearchParam.getPageSize());


        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("es库中查询出来的总数据={}",searchResponse);
        } catch (IOException e) {
            throw new RuntimeException("es查询失败");
        }

        SearchHits searchHits = searchResponse.getHits();
        log.info("searchHits={}",searchHits);
        long total = searchHits.getTotalHits().value;
        log.info("符合搜索的总条数={}",total);
        SearchHit[] hits = searchHits.getHits();
        log.info("需要的数据集合hits={} 运行类型={}",hits,hits.getClass());

        ArrayList<Product> productList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hit : hits) {
            //查询的内容数据 productDoc数据模型的json格式数据
            String sourceAsString = hit.getSourceAsString();
            //jackson读取该productDoc型的json格式数据,转换成product类型,但是由于product没有all属性,就会封装不了all属性导致报错
            //TODO 所以应该在product实体类上加上@JsonIgnoreProperties(ignoreUnknown = true)注解 忽略没有属性强行封装导致封装失败的问题
            Product product = null;
            try {
                product = objectMapper.readValue(sourceAsString, Product.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //加入商品集合中
            productList.add(product);
        }
        log.info("搜索服务完返回的商品集合={}",productList);
        //封装结果
        R r = R.ok(null, productList, total);

        return r;
    }
}
