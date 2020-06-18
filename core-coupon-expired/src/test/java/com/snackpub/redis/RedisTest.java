package com.snackpub.redis;

import com.snackpub.CoreCouponExpiredApplicationTests;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 测试redis连接是否正常
 *
 * @author snack
 */
public class RedisTest extends CoreCouponExpiredApplicationTests {

    private static Logger log = Logger.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testSend() {
        redisTemplate.opsForValue().set("itcast", "very good！");
        String value = redisTemplate.opsForValue().get("itcast");
        log.info("从redis中获取的数据：" + value);
    }
}
