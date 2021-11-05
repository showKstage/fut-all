package org.jeecg.modules.app.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CoursesOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 课程信息 */
    CoursesDetailIntroductionVo coursesDetailIntroductionVo;
    /** 可用优惠券信息 */
    List<CoursesCouponVo> usableCouponsList;
    /** 不可用优惠券信息 */
    List<CoursesCouponVo> unusableCouponsList;
    /** 当前选择优惠券信息 */
    CoursesCouponVo currentCoupon;
    /** 课程原价 */
    Double originalPrice;
    /**促销优化金额（促销价、满减、阶梯价）*/
    Double promotionAmount;
    /** 优惠券优惠价格 */
    Double couponPrice;
    /** 课程实付价格 */
    Double realPrice;
    /** 支付方式：1->支付宝；2->微信，3->余额支付 */
    int payType;
    /**订单类型：0->正常订单；1->秒杀订单*/
    int orderType;

}
