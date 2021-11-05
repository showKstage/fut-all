package org.jeecg.modules.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/9/13 9:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CoursesOrderInfo {
    /**订单编号*/
    @ApiModelProperty(value = "订单编号")
    private String orderSn;
    /**课程id*/
    @ApiModelProperty(value = "课程id/系列课程id")
    private String coursesId;
    /**课程图片*/
    @ApiModelProperty(value = "课程图片")
    private String coursePicture;
    /**课程名称*/
    @ApiModelProperty(value = "课程名称")
    private String courseName;
    /**课程价格*/
    @ApiModelProperty(value = "课程价格")
    private double coursePrice;
    /**订单状态*/
    @ApiModelProperty(value = "订单状态")
    private int status;
    /**提交时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "提交时间")
    private Date createTime;
    /**应付金额*/
    @ApiModelProperty(value = "应付金额")
    private Double payAmount;






}
