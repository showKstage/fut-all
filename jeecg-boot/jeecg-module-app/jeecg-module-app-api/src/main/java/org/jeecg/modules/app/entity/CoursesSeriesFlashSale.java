package org.jeecg.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 限时优惠系列课程
 * @Author: shiHao.qiu
 * @Date: 2021/7/19 10:23
 */
@ApiModel(value="限时优惠系列课程", description="限时优惠系列课程")
@Data
public class CoursesSeriesFlashSale implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**现价*/
	@Excel(name = "现价", width = 15)
    @ApiModelProperty(value = "现价")
    private Double currentPrice;
	/**原价*/
	@Excel(name = "原价", width = 15)
    @ApiModelProperty(value = "原价")
    private Double originalPrice;
	/**系列课程id*/
    @ApiModelProperty(value = "系列课程id")
    private String courseSeriesId;
	/**优惠起时间*/
	@Excel(name = "优惠起时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "优惠起时间")
    private Date offerStartTime;
	/**优惠止时间*/
	@Excel(name = "优惠止时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "优惠止时间")
    private Date offerEndTime;
}
