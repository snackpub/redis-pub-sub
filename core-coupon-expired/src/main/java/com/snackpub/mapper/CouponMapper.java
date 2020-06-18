package com.snackpub.mapper;


import com.snackpub.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author api
 */
@Mapper
public interface CouponMapper {


    /**
     * 保存优惠券信息
     *
     * @param coupon 优惠券实体
     */
    void saveCoupon(Coupon coupon);

    /**
     * 根据id更新优惠券信息
     *
     * @param coupon 优惠券实体
     */
    void updateCoupon(Coupon coupon);

    /**
     * 根据id查询优惠券信息
     *
     * @param id 主键ID
     * @return coupon
     */
    Coupon selectCouponById(long id);

}
