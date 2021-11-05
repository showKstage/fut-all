package org.jeecg.modules.app.service;

import org.jeecg.modules.app.entity.CoursesCouponHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * @Description: 优惠券历史记录表
 * @Author: jeecg-boot
 * @Date:   2021-08-17
 * @Version: V1.0
 */
public interface ICoursesCouponHistoryService extends IService<CoursesCouponHistory> {
    /**使用优惠卷*/
    int UseCoursesCoupon(String couponId,Date useTime,String userId);

    /**设置过期优惠卷状态*/
    int setCoursesCoupon(String couponId);

    /** 退回已使用优惠券 */
    void recallCoupon(String couponId,String userId);

}
