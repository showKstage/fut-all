package org.jeecg.modules.app.service;

import org.jeecg.modules.app.entity.CoursesCoupon;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.vo.CoursesCouponVo;
import java.util.List;

/**
 * @Description: 优惠券表
 * @Author: jeecg-boot
 * @Date:   2021-08-17
 * @Version: V1.0
 */
public interface ICoursesCouponService extends IService<CoursesCoupon> {
    /**查询用户所有优惠卷*/
    List<CoursesCouponVo> queryCoursesCouponByUserId(String userId);
}
