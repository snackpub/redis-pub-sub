package com.snackpub.core.config;

import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;

/**
 * 配置redis消息的监听器：
 * 获取redis中的消息并进行处理
 *
 * @author api
 */
public class MyRedisChannelListener implements MessageListener {

    /**
     * 通过Redis处理接收对象的回调
     *
     * @param message 封装Redis消息体及其属性的类
     * @param pattern 模式匹配的通道(如果指定)-可以是null
     */
    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 返回与消息关联的通道
        byte[] channel = message.getChannel();
        // 返回消息主题或有效的负载
        byte[] body = message.getBody();
        String content = new String(body, StandardCharsets.UTF_8);
        String address = new String(channel, StandardCharsets.UTF_8);

        System.out.println("从channel为：" + address + "获取了一条新的消息，消息内容为：" +
                content);
    }

}
