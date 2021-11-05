package org.jeecg.modules.app.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.app.entity.CoursesOrder;
import org.jeecg.modules.app.vo.CoursesOrderInfo;

import java.util.List;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2021-08-21
 * @Version: V1.0
 */
public interface CoursesOrderMapper extends BaseMapper<CoursesOrder> {

    /** 查询同个用户同个课程订单是否重复存在 */
    CoursesOrder selectOrderIsExist(@Param("coursesId") String coursesId,
                           @Param("userId") String userId);

    /** 插入订单 */
    void insertOrder(@Param("coursesOrder") CoursesOrder coursesOrder);

    /** 根据订单状态查询用户订单 */
    List<CoursesOrderInfo> selectOrderByUserIdAndOrderType(@Param("userId") String userId,
                                                           @Param("status") String status);

    /** 删除订单 */
    void deleteOrderByuserId(@Param("userId") String userId,
                             @Param("orderSn") String orderSn);

    /** 支付成功回调修改订单状态 */
    void paySuccessedUpdateOrder(@Param("orderSn") String orderSn);

    /** 未支付超时修改订单状态 */
    void payTimeoutUpdateOrder(@Param("userId") String userId,
                               @Param("orderSn") String orderSn);

}
