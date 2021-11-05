package org.jeecg.modules.app.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 优惠券表
 * @Author: jeecg-boot
 * @Date:   2021-08-17
 * @Version: V1.0
 */
@Data
@TableName("courses_coupon")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="courses_coupon对象", description="优惠券表")
public class CoursesCoupon implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券*/
	@Excel(name = "优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券", width = 15)
    @ApiModelProperty(value = "优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券")
    private Integer type;
	/**优惠券名称*/
	@Excel(name = "优惠券名称", width = 15)
    @ApiModelProperty(value = "优惠券名称")
    private String name;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private Integer count;
	/**金额*/
	@Excel(name = "金额", width = 15)
    @ApiModelProperty(value = "金额")
    private Double amount;
	/**每人限领张数*/
	@Excel(name = "每人限领张数", width = 15)
    @ApiModelProperty(value = "每人限领张数")
    private Integer perLimit;
	/**使用门槛；0表示无门槛*/
	@Excel(name = "使用门槛；0表示无门槛", width = 15)
    @ApiModelProperty(value = "使用门槛；0表示无门槛")
    private Double minPoint;
	/**开始使用时间*/
	@Excel(name = "开始使用时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始使用时间")
    private Date startTime;
	/**结束使用时间*/
	@Excel(name = "结束使用时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束使用时间")
    private Date endTime;
	/**使用类型：0->全场通用；1->指定分类；2->指定商品*/
	@Excel(name = "使用类型：0->全场通用；1->指定分类；2->指定商品", width = 15)
    @ApiModelProperty(value = "使用类型：0->全场通用；1->指定分类；2->指定商品")
    private Integer useType;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String note;
	/**发行数量*/
	@Excel(name = "发行数量", width = 15)
    @ApiModelProperty(value = "发行数量")
    private Integer publishCount;
	/**已使用数量*/
	@Excel(name = "已使用数量", width = 15)
    @ApiModelProperty(value = "已使用数量")
    private Integer useCount;
	/**领取数量*/
	@Excel(name = "领取数量", width = 15)
    @ApiModelProperty(value = "领取数量")
    private Integer receiveCount;
	/**可以领取的日期*/
	@Excel(name = "可以领取的日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "可以领取的日期")
    private Date enableTime;
}
