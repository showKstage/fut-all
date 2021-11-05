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
 * @Description: 优惠券历史记录表
 * @Author: jeecg-boot
 * @Date:   2021-08-17
 * @Version: V1.0
 */
@Data
@TableName("courses_coupon_history")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="courses_coupon_history对象", description="优惠券历史记录表")
public class CoursesCouponHistory implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**优惠券id*/
	@Excel(name = "优惠券id", width = 15)
    @ApiModelProperty(value = "优惠券id")
    private String couponId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private String userId;
	/**订单id*/
	@Excel(name = "订单id", width = 15)
    @ApiModelProperty(value = "订单id")
    private String orderId;
	/**获取类型：0->后台赠送；1->主动获取*/
	@Excel(name = "获取类型：0->后台赠送；1->主动获取", width = 15)
    @ApiModelProperty(value = "获取类型：0->后台赠送；1->主动获取")
    private Integer getType;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**使用状态：0->未使用；1->已使用；2->已过期*/
	@Excel(name = "使用状态：0->未使用；1->已使用；2->已过期", width = 15)
    @ApiModelProperty(value = "使用状态：0->未使用；1->已使用；2->已过期")
    private Integer useStatus;
	/**使用时间*/
	@Excel(name = "使用时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "使用时间")
    private Date useTime;
}
