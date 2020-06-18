package com.snackpub.test;

import java.math.BigDecimal;
import java.util.Date;

import com.snackpub.CoreCouponExpiredApplicationTests;
import com.snackpub.tools.DataUtils;
import com.snackpub.entity.Coupon;
import com.snackpub.mapper.CouponMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CouponTest extends CoreCouponExpiredApplicationTests {

    @Autowired
    private CouponMapper couponMapper;

    @Test
    public void testSaveCoupon() {
        Date now = new Date();
        Coupon coupon = new Coupon("测试优惠券", BigDecimal.ONE,
                "全品类优惠10元", now, DataUtils.addTime(now, 1), 0);

        couponMapper.saveCoupon(coupon);
    }

    @Test
    public void testUpdateCoupon() {
        Coupon coupon = couponMapper.selectCouponById(3);
        coupon.setState(1);
        couponMapper.updateCoupon(coupon);
    }

    @Test
    public void selectCouponById() {
        Coupon coupon = couponMapper.selectCouponById(3);
        System.out.println(coupon);
    }

}
