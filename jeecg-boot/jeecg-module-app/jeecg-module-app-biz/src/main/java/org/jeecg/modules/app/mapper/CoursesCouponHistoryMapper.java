package org.jeecg.modules.app.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.app.entity.CoursesCouponHistory;


import java.util.Date;
import java.util.List;

/**
 * @Description: 优惠券历史记录表
 * @Author: jeecg-boot
 * @Date:   2021-08-17
 * @Version: V1.0
 */
public interface CoursesCouponHistoryMapper extends BaseMapper<CoursesCouponHistory> {

    /** 使用优惠券 */
    int useCoursesCoupon(@Param("couponId")String couponId,
                         @Param("useTime")Date useTime,
                         @Param("userId")String userId);

    /** 设置过期优惠卷状态 */
    int setCoursesCoupon(@Param("couponId")String couponId);

    /** 退回已使用优惠券 */
    void recallCoursesCoupon(@Param("couponId")String couponId,
                             @Param("userId")String userId);
}
