package org.jeecg.modules.app.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/8/17 11:16
 */
@Data
public class CoursesCouponListVo {
    /**可使用的优惠券*/
    List<CoursesCouponVo> usableCoupons;
    /**不可使用的优惠券名称*/
    List<CoursesCouponVo> unavailableCoupons;
}
