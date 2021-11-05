package org.jeecg.modules.app.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2021-08-21
 * @Version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("courses_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="courses_order对象", description="订单表")
public class CoursesOrder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private String userId;
	/**优惠券id*/
	@Excel(name = "优惠券id", width = 15)
    @ApiModelProperty(value = "优惠券id")
    private String couponId;
	/**订单编号*/
	@Excel(name = "订单编号", width = 15)
    @ApiModelProperty(value = "订单编号")
    private String orderSn;
	/**课程id*/
    @Excel(name = "课程id/系列课程id", width = 15)
    @ApiModelProperty(value = "课程id/系列课程id")
    private String coursesId;
	/**提交时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "提交时间")
    private java.util.Date createTime;
	/**订单总金额*/
	@Excel(name = "订单总金额", width = 15)
    @ApiModelProperty(value = "订单总金额")
    private Double totalAmount;
	/**应付金额*/
	@Excel(name = "应付金额", width = 15)
    @ApiModelProperty(value = "应付金额")
    private Double payAmount;
	/**促销优化金额（促销价、满减、阶梯价）*/
	@Excel(name = "促销优化金额（促销价、满减、阶梯价）", width = 15)
    @ApiModelProperty(value = "促销优化金额（促销价、满减、阶梯价）")
    private Double promotionAmount;
	/**优惠券抵扣金额*/
	@Excel(name = "优惠券抵扣金额", width = 15)
    @ApiModelProperty(value = "优惠券抵扣金额")
    private Double couponAmount;
	/**支付方式：0->未支付；1->支付宝；2->微信，3->余额支付*/
	@Excel(name = "支付方式：0->未支付；1->支付宝；2->微信，3->余额支付", width = 15)
    @ApiModelProperty(value = "支付方式：0->未支付；1->支付宝；2->微信，3->余额支付")
    private Integer payType;
	/**订单状态：0->待付款；1->已完成；2->已关闭*/
	@Excel(name = "订单状态：0->待付款；1->已完成；2->已关闭", width = 15)
    @ApiModelProperty(value = "订单状态：0->待付款；1->已完成；2->已关闭")
    private Integer status;
	/**订单类型：0->正常订单；1->秒杀订单*/
	@Excel(name = "订单类型：0->正常订单；1->秒杀订单", width = 15)
    @ApiModelProperty(value = "订单类型：0->正常订单；1->秒杀订单")
    private Integer orderType;
	/**订单备注*/
	@Excel(name = "订单备注", width = 15)
    @ApiModelProperty(value = "订单备注")
    private String note;
	/**删除状态：0->未删除；1->已删除*/
	@Excel(name = "删除状态：0->未删除；1->已删除", width = 15)
    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    private Integer deleteStatus;
	/**支付时间*/
	@Excel(name = "支付时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "支付时间")
    private java.util.Date paymentTime;
	/**评价时间*/
	@Excel(name = "评价时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "评价时间")
    private java.util.Date commentTime;
}
