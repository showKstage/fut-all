package org.jeecg.modules.app.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 订单课程信息表
 * @Author: jeecg-boot
 * @Date:   2021-08-21
 * @Version: V1.0
 */
@Data
@TableName("courses_order_item")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="courses_order_item对象", description="订单课程信息表")
public class CoursesOrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**订单id*/
	@Excel(name = "订单id", width = 15)
    @ApiModelProperty(value = "订单id")
    private String orderId;
	/**订单编号*/
	@Excel(name = "订单编号", width = 15)
    @ApiModelProperty(value = "订单编号")
    private String orderSn;
	/**课程id*/
	@Excel(name = "课程id", width = 15)
    @ApiModelProperty(value = "课程id")
    private String coursesId;
	/**系列课程id*/
	@Excel(name = "系列课程id", width = 15)
    @ApiModelProperty(value = "系列课程id")
    private String coursesSeriesId;
	/**销售价格*/
	@Excel(name = "销售价格", width = 15)
    @ApiModelProperty(value = "销售价格")
    private Double productPrice;
	/**商品促销分解金额*/
	@Excel(name = "商品促销分解金额", width = 15)
    @ApiModelProperty(value = "商品促销分解金额")
    private Double promotionAmount;
	/**优惠券优惠分解金额*/
	@Excel(name = "优惠券优惠分解金额", width = 15)
    @ApiModelProperty(value = "优惠券优惠分解金额")
    private Double couponAmount;
	/**最终优惠后价格*/
	@Excel(name = "最终优惠后价格", width = 15)
    @ApiModelProperty(value = "最终优惠后价格")
    private Double realAmount;
}
