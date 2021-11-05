package org.jeecg.modules.app.entity;

import java.io.Serializable;

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
 * @Description: 订单操作记录表
 * @Author: jeecg-boot
 * @Date:   2021-08-21
 * @Version: V1.0
 */
@Data
@TableName("courses_order_operate")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="courses_order_operate对象", description="订单操作记录表")
public class CoursesOrderOperate implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**订单id*/
	@Excel(name = "订单id", width = 15)
    @ApiModelProperty(value = "订单id")
    private String orderId;
	/**操作人id：用户；系统；后台管理员*/
	@Excel(name = "操作人id：用户；系统；后台管理员", width = 15)
    @ApiModelProperty(value = "操作人id：用户；系统；后台管理员")
    private String operateMan;
	/**操作时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "操作时间")
    private java.util.Date createTime;
	/**订单状态：0->待付款； 1->已完成；2->已关闭*/
	@Excel(name = "订单状态：0->待付款； 1->已完成；2->已关闭", width = 15)
    @ApiModelProperty(value = "订单状态：0->待付款； 1->已完成；2->已关闭")
    private Integer orderStatus;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String note;
}
