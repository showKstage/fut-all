package org.jeecg.modules.app.service;

import org.jeecg.modules.app.vo.CoursesCouponVo;
import org.jeecg.modules.app.vo.CoursesDetailIntroductionVo;
import org.jeecg.modules.app.entity.CoursesOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.vo.CoursesOrderInfo;
import org.jeecg.modules.app.vo.CoursesOrderVO;

import java.util.List;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2021-08-21
 * @Version: V1.0
 */
public interface ICoursesOrderService extends IService<CoursesOrder> {

    /** 生成订单 */
    String createOrder( CoursesOrderVO coursesOrderVO,String userId,String coursesId);

    /** 根据课程id和用户id查询当前课程用户可用和不可用优惠券 */
    List<List<CoursesCouponVo>> getCouponsByCoursesIdAndUserId(String userId,
                                                               String coursesId,
                                                               List<String> coursesCategoryId,
                                                               CoursesDetailIntroductionVo coursesDetailIntroductionVo);

    /** 查询用户订单 */
    List<CoursesOrderInfo> queryOrderByUserId(String userId, String orderType);

    /** 删除订单 */
    void deleteOrder(String userId,String orderSn);

    /** 支付成功回调修改订单状态 */
    void paySuccessedOrder(String orderSn);

    /** 未支付超时修改订单状态 */
    void payTimeoutOrder(String userId,String orderSn);

}
