package com.snackpub.config;

import com.snackpub.entity.Coupon;
import com.snackpub.mapper.CouponMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;

/**
 * 配置redis消息的监听器：
 * 获取redis中的消息并进行处理
 *
 * @author snackpub
 */
public class MyRedisChannelListener implements MessageListener {

    private static Logger log = LoggerFactory.getLogger(MyRedisChannelListener.class);

    private CouponMapper couponMapper;

    public MyRedisChannelListener(CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
    }


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
        log.info("从channel为：{}获取了一条新的消息，消息内容为：{}", address, content);

        if (content.startsWith("coupon")) {
            String id = content.split(":")[1];
            Coupon coupon = couponMapper.selectCouponById(Long.parseLong(id));
            coupon.setState(1);
            couponMapper.updateCoupon(coupon);
            log.info("数据库已经更新券失效");
        }

    }

}
