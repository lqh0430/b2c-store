package com.atguigu.search.listener;

import com.atguigu.clients.SearchClient;
import com.atguigu.doc.ProductDoc;
import com.atguigu.pojo.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @version 1.0
 * @Author LQH02
 * @Description RabbitMQ监听器 当后台发出删除和修改商品时，监听器就会监听消息,实现es库的数据更新和删除
 * @CreateDate 2022/11/16 20:01
 */
@Component
public class RabbitMQListener {

    /**
     * 注入es客户端
     */
    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 满足这三个规则,即可调用insert()方法
     * @param product
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "insert.queue"), //插入队列名
            exchange = @Exchange("topic.ex"), //交换机名字
            key = "insert.product"
    ))
    public void insert(Product product) throws IOException {
        //按每条数据id装入到product索引中
        IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());
        //但是需要传的是productDoc类型的json格式数据
        ProductDoc productDoc = new ProductDoc(product);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDoc);

        //json数据准备好
        indexRequest.source(json, XContentType.JSON);
        //开始添加
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

    }

    /**
     * 删除通知
     * @param product
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "remove.queue"), //删除队列名
            exchange = @Exchange("topic.ex"), //交换机名字
            key = "delete.product"
    ))
    public void remove(Product product) throws IOException {
        //对product索引发出每条按productId 删除请求
        DeleteRequest deleteRequest = new DeleteRequest("product").id(product.getProductId().toString());

        restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
    }

}
