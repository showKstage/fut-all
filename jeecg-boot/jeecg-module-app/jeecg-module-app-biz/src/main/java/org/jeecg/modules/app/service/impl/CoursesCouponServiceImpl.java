package org.jeecg.modules.app.service.impl;

import org.jeecg.modules.app.entity.CoursesCoupon;
import org.jeecg.modules.app.mapper.CoursesCouponMapper;
import org.jeecg.modules.app.service.ICoursesCouponService;
import org.jeecg.modules.app.vo.CoursesCouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 优惠券表
 * @Author: jeecg-boot
 * @Date:   2021-08-17
 * @Version: V1.0
 */
@Service
public class CoursesCouponServiceImpl extends ServiceImpl<CoursesCouponMapper, CoursesCoupon> implements ICoursesCouponService {
    @Autowired
    CoursesCouponMapper coursesCouponMapper;
    @Override
    public List<CoursesCouponVo> queryCoursesCouponByUserId(String userId) {
        return coursesCouponMapper.queryCoursesCouponByUserId(userId);
    }
}
