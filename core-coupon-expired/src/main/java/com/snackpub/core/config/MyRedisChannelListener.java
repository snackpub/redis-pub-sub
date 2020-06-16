package com.snackpub.core.config;

import com.snackpub.core.api.entity.Coupon;
import com.snackpub.core.api.mapper.CouponMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * 配置redis消息的监听器：
 * 获取redis中的消息并进行处理
 *
 * @author api
 */
public class MyRedisChannelListener implements MessageListener {

    @Resource
    private CouponMapper couponMapper;

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

        String key = new String(message.getBody());
        if (key.startsWith("coupon")) {
            String id = key.split(":")[1];
            Coupon coupon = couponMapper.selectCouponById(Long.parseLong(id));
            coupon.setState(1);
            couponMapper.updateCoupon(coupon);
            System.out.println("数据库已经更新券失效");
        }

    }

}
