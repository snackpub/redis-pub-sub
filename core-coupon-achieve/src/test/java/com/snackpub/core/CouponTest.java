package com.snackpub.core;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.snackpub.boot.tools.DataUtils;
import com.snackpub.core.api.entity.Coupon;
import com.snackpub.core.api.mapper.CouponMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponTest {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 1.创建一个优惠券
     * 2.保存到数据库中
     * 3.保存到redis服务器中，设置超时时间
     */
    @Test
    public void testGetCoupon() {
        Date now = new Date();
        int timeOut = 1;// 优惠券的超时时间：1分钟

        //自定义一个优惠券
        Coupon coupon = new Coupon();

        coupon.setName("测试优惠券");
        coupon.setMoney(BigDecimal.ONE);//设置金额
        coupon.setCouponDesc("全品类优惠券1元");
        coupon.setCreateTime(now);
        coupon.setExpireTime(DataUtils.addTime(now, timeOut));//设置优惠券的失效时间，一份后失效
        coupon.setState(0);

        //将优惠券保存到数据库中
        couponMapper.saveCoupon(coupon);

        //将优惠券设置到redis缓存中，并且设置超时件
        redisTemplate.opsForValue().set("coupon:" + coupon.getId(), coupon.getId() + "", timeOut, TimeUnit.MINUTES);


    }

}
