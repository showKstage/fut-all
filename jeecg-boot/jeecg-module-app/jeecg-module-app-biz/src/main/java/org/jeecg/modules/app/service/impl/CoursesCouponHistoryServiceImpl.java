package org.jeecg.modules.app.service.impl;

import org.jeecg.modules.app.entity.CoursesCouponHistory;
import org.jeecg.modules.app.mapper.CoursesCouponHistoryMapper;
import org.jeecg.modules.app.service.ICoursesCouponHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;

/**
 * @Description: 优惠券历史记录表
 * @Author: jeecg-boot
 * @Date:   2021-08-17
 * @Version: V1.0
 */
@Service
public class CoursesCouponHistoryServiceImpl extends ServiceImpl<CoursesCouponHistoryMapper, CoursesCouponHistory> implements ICoursesCouponHistoryService {
    @Autowired
    CoursesCouponHistoryMapper coursesCouponHistoryMapper;

    @Override
    public int UseCoursesCoupon(String couponId, Date useTime,String userId) {
        return coursesCouponHistoryMapper.useCoursesCoupon(couponId, useTime,userId);
    }

    @Override
    public int setCoursesCoupon(String couponId) {
        return coursesCouponHistoryMapper.setCoursesCoupon(couponId);
    }

    @Override
    public void recallCoupon(String couponId, String userId) {
        coursesCouponHistoryMapper.recallCoursesCoupon(couponId, userId);
    }
}
