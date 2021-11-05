package org.jeecg.modules.app.service.impl;

import cn.hutool.core.lang.Snowflake;
import org.jeecg.modules.app.service.ICoursesCouponHistoryService;
import org.jeecg.modules.app.service.ICoursesCouponService;
import org.jeecg.modules.app.vo.CoursesCouponVo;
import org.jeecg.modules.app.mapper.CoursesDetailMapper;
import org.jeecg.modules.app.mapper.CoursesSeriesMapper;
import org.jeecg.modules.app.vo.CoursesDetailIntroductionVo;
import org.jeecg.modules.app.entity.CoursesOrder;
import org.jeecg.modules.app.mapper.CoursesOrderMapper;
import org.jeecg.modules.app.service.ICoursesOrderService;
import org.jeecg.modules.app.vo.CoursesOrderInfo;
import org.jeecg.modules.app.vo.CoursesOrderVO;
import org.jeecg.modules.app.vo.CoursesSeriesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2021-08-21
 * @Version: V1.0
 */
@Service
public class CoursesOrderServiceImpl extends ServiceImpl<CoursesOrderMapper, CoursesOrder> implements ICoursesOrderService {

    @Autowired
    CoursesOrderMapper coursesOrderMapper;

    @Autowired
    ICoursesCouponService coursesCouponService;

    @Autowired
    ICoursesCouponHistoryService coursesCouponHistoryService;

    @Autowired
    CoursesDetailMapper coursesDetailMapper;

    @Autowired
    CoursesSeriesMapper coursesSeriesMapper;


    /** 生成订单 返回订单号 */
    @Override
    public String createOrder(CoursesOrderVO coursesOrderVO,String userId,String coursesId) {

        //避免重复插入订单
        CoursesOrder order = coursesOrderMapper.selectOrderIsExist(coursesId, userId);
        if (order != null) {
            return order.getOrderSn();
        }

        //雪花算法生成随机订单编号
        Snowflake snowflake = new Snowflake(5, 5);

        CoursesOrder coursesOrder = new CoursesOrder()
                .setUserId(userId)
                .setOrderSn(snowflake.nextIdStr())
                .setCoursesId(coursesId)
                .setCreateTime(new Date())
                .setTotalAmount(coursesOrderVO.getOriginalPrice())
                .setPayAmount(coursesOrderVO.getRealPrice())
                .setPromotionAmount(coursesOrderVO.getPromotionAmount())
                .setCouponAmount(coursesOrderVO.getCouponPrice())
                .setPayType(coursesOrderVO.getPayType())
                //订单状态默认为0->待付款
                .setStatus(0)
                .setNote(null)
                .setOrderType(coursesOrderVO.getOrderType())
                //订单删除状态默认为0->未删除
                .setDeleteStatus(0);
        if (coursesOrderVO.getCouponPrice() != null){
            coursesOrder.setCouponId(coursesOrderVO.getCurrentCoupon().getId());
            //使用优惠券
            coursesCouponHistoryService.UseCoursesCoupon(coursesOrderVO.getCurrentCoupon().getId(),new Date(),userId);
        }
        //插入订单
        coursesOrderMapper.insertOrder(coursesOrder);

        return coursesOrder.getOrderSn();
    }

    /** 根据课程id和用户id查询当前课程用户可用和不可用优惠券 */
    @Override
    public List<List<CoursesCouponVo>> getCouponsByCoursesIdAndUserId(String userId,
                                                                      String coursesId,
                                                                      List<String> coursesCategoryId,
                                                                      CoursesDetailIntroductionVo coursesDetailIntroductionVo) {
        List<CoursesCouponVo> couponVos = coursesCouponService.queryCoursesCouponByUserId(userId);
        List<List<CoursesCouponVo>> couponsList = new ArrayList<>();
        List<CoursesCouponVo> usableCoupons = new ArrayList<>();
        List<CoursesCouponVo> unusableCoupons = new ArrayList<>();

        Date date = new Date();
        long now = date.getTime();
        for (CoursesCouponVo coursesCouponVo : couponVos) {
            if ((now >= coursesCouponVo.getStartTime().getTime()) && (now <= coursesCouponVo.getEndTime().getTime())) {
                if (coursesDetailIntroductionVo.getCoursesDetailPrice() >= coursesCouponVo.getMinPoint()) {
                    if ((coursesCouponVo.getType() == 0) || (coursesCouponVo.getType() == 2 && coursesCouponVo.getCourseId() == coursesId)) {
                        usableCoupons.add(coursesCouponVo);
                    } else if (coursesCouponVo.getType() == 1) {
                        for (String categoryId : coursesCategoryId) {
                            if (categoryId.equals(coursesCouponVo.getCategoryId())) {usableCoupons.add(coursesCouponVo); }}
                    } else { unusableCoupons.add(coursesCouponVo); }
                } else { unusableCoupons.add(coursesCouponVo); }
            }
        }
        //对可用优惠券的金额进行排序 默认使用金额最高的优惠券
        List<CoursesCouponVo> coursesCouponVoList = usableCoupons.stream()
                .sorted(Comparator.comparing(CoursesCouponVo::getAmount).reversed())
                .collect(Collectors.toList());

        couponsList.add(unusableCoupons);
        couponsList.add(coursesCouponVoList);
        return couponsList;
    }

    @Override
    public List<CoursesOrderInfo> queryOrderByUserId(String userId, String status) {
        List<CoursesOrderInfo> coursesOrderInfos = coursesOrderMapper.selectOrderByUserIdAndOrderType(userId, status);
        for (CoursesOrderInfo coursesOrderInfo : coursesOrderInfos) {
            CoursesDetailIntroductionVo coursesDetailIntroductionVo = coursesDetailMapper.queryCoursesDetail(coursesOrderInfo.getCoursesId());
            if (coursesDetailIntroductionVo!=null){
                coursesOrderInfo.setCourseName(coursesDetailIntroductionVo.getCoursesDetailName());
                coursesOrderInfo.setCoursePicture(coursesDetailIntroductionVo.getCoursesDetailPicture());
                coursesOrderInfo.setCoursePrice(coursesDetailIntroductionVo.getCoursesDetailPrice());
            }
            else {
                CoursesSeriesVo coursesSeriesVo = coursesSeriesMapper.querySeriesCoursesById(coursesOrderInfo.getCoursesId());
                coursesOrderInfo.setCourseName(coursesSeriesVo.getCoursesSeriesName());
                coursesOrderInfo.setCoursePicture(coursesSeriesVo.getCoursesSeriesPicture());
                coursesOrderInfo.setCoursePrice(coursesSeriesVo.getPrice());
            }
        }
        return coursesOrderInfos;
    }

    @Override
    public void deleteOrder(String userId, String orderSn) {
        coursesOrderMapper.deleteOrderByuserId(userId, orderSn);
    }

    @Override
    public void paySuccessedOrder(String orderSn) {
        coursesOrderMapper.paySuccessedUpdateOrder(orderSn);
    }

    @Override
    public void payTimeoutOrder(String userId,String orderSn) {
        coursesOrderMapper.payTimeoutUpdateOrder(userId,orderSn);
    }


}
