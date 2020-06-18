package com.snackpub.config;

import com.snackpub.mapper.CouponMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;


/**
 * redis pub/sub 消息监听配置
 *
 * @author snackpub
 * @date 2020/5/6
 */
@Configuration
@Slf4j
public class MessageListenerConfig {

    @Resource
    private CouponMapper couponMapper;

    @Bean
    @ConditionalOnMissingBean(name = "messageListenerAdapter")
    public MessageListenerAdapter messageListenerAdapter() {
        log.info("MessageListenerAdapter init...");
        return new MessageListenerAdapter(new MyRedisChannelListener(couponMapper));
    }

    @Bean
    @ConditionalOnMissingBean
    RedisMessageListenerContainer container(RedisConnectionFactory connFactory, MessageListenerAdapter msgAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connFactory);
        //向(可能正在运行的)容器添加消息侦听器。如果容器正在运行，则侦听器将尽快开始接收(匹配)消息
        //@param1 消息侦听适配器
        //@param2 消息主题
        // container.addMessageListener(msgAdapter, new PatternTopic("news"));
        /*List<Topic> topics = new ArrayList<>(new ArrayList<PatternTopic>() {
            {
                // 根据正则匹配订阅一个或多个频道
                add(new PatternTopic("news*"));
            }
        });*/
        //  此处有坑 需要在Redis服务器命令行执行 config set notify-keyspace-events KEA
        // __keyevent@0__:expired 配置订阅的主题名称此名称时redis提供的名称，标识过期key消息通知 0表示db0 根据自己的dbindex选择合适的数字
        container.addMessageListener(msgAdapter, new PatternTopic("__keyevent@0__:expired"));
        return container;
    }

}
