package com.atguigu.search.listener;

import com.atguigu.clients.ProductClient;
import com.atguigu.doc.ProductDoc;
import com.atguigu.pojo.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 启动监控类
 * @CreateDate 2022/11/16 10:14
 */
@Component
@Slf4j
public class ApplicationRunListener implements ApplicationRunner {

    /**
     * 注入es客户端
     */
    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 注入商品client
     */
    @Resource
    private ProductClient productClient;

    /**
     * es中先写好的dml语句,测试成功,再粘贴过来
     */
    private String indexStr = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"productId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productName\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"categoryId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productTitle\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productIntro\":{\n" +
            "        \"type\":\"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productPicture\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"productPrice\":{\n" +
            "        \"type\": \"double\",\n" +
            "        \"index\": true\n" +
            "      },\n" +
            "      \"productSellingPrice\":{\n" +
            "        \"type\": \"double\"\n" +
            "      },\n" +
            "      \"productNum\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productSales\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"all\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";


    /**
     * 此方法用于进行数据同步
     * 1.检查es库中是否存在索引对象
     *  1.1不存在,则创建一个
     *  1.2存在,继续执行,删除原来的数据
     * 2.远程调用商品服务的 查询全部商品数据的接口
     * 3.批量插入到es库中
     *  3.1需要进行数据模型转换product->productDoc --->json
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        GetIndexRequest getIndexRequest = new GetIndexRequest("product");
        //1.exists判断索引是否存在 第1个参数:索引对象 第2个参数:固定
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);

        if (!exists) {
            //1.1不存在,新建一个索引对象
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("product");
            //给空的索引对象 添加 索引结构(内容)
            createIndexRequest.source(indexStr, XContentType.JSON);
            //创建
            restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        }
        //1.2删除原来的全部数据
        //指定删除索引对象
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("product");
        //指定删除条件(匹配全部)
        deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
        //开始删除
        restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);

        //2.远程调用  查询商品数据
        List<Product> productList = productClient.allList();
        log.info("productList={}",productList);

        //3.批量插入更新数据到es库中
        BulkRequest bulkRequest = new BulkRequest();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Product product : productList) {
            //模型转换
            ProductDoc productDoc = new ProductDoc(product);
            //用于插入数据的作用
            IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());
            //再序列化成json格式
            String json = objectMapper.writeValueAsString(productDoc);
            log.info("json={}",json);
            indexRequest.source(json,XContentType.JSON);
            //添加
            bulkRequest.add(indexRequest);
        }

        restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);


    }
}
