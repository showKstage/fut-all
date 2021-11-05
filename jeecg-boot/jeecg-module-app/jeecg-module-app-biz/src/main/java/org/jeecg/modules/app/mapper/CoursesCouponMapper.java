package org.jeecg.modules.app.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesCoupon;
import org.jeecg.modules.app.vo.CoursesCouponVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description: 优惠券表
 * @Author: jeecg-boot
 * @Date:   2021-08-17
 * @Version: V1.0
 */
public interface CoursesCouponMapper extends BaseMapper<CoursesCoupon> {
    /**查询用户所有优惠卷*/
    List<CoursesCouponVo> queryCoursesCouponByUserId(@Param("userId")String userId);
}
