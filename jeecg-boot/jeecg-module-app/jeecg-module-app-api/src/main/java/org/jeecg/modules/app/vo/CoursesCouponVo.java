package org.jeecg.modules.app.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 优惠券
 * @Author: shiHao.qiu
 * @Date: 2021/8/17 10:37
 */

@Data
public class CoursesCouponVo implements Comparable<CoursesCouponVo>{
    /**优惠卷id*/
    private String id;
    /**优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券*/
    private Integer type;
    /**优惠券名称*/
    private String name;
    /**数量*/
    private Integer count;
    /**金额*/
    private Double amount;
    /**开始使用时间*/
    private Date startTime;
    /**结束使用时间*/
    private Date endTime;
    /**使用门槛；0表示无门槛*/
    private Double minPoint;
    /**使用类型：0->全场通用；1->指定分类；2->指定商品*/
    private Integer useType;
    /**课程id/系列课程id*/
    private String courseId;
    /**课程分类id*/
    private String categoryId;


    @Override
    public int compareTo(@NotNull CoursesCouponVo o) {
        return this.getAmount().compareTo(o.getAmount());
    }
}
