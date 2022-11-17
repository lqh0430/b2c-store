package com.atguigu.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author LQH02
 * @Description 搜索配置类
 * @CreateDate 2022/11/16 10:08
 */
@Configuration
public class SearchConfig {

    /**
     * mq序列化方式,转成json格式
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * 将es客户端注入到IOC容器中
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://120.26.68.16:9200")));
        return restHighLevelClient;
    }

}
