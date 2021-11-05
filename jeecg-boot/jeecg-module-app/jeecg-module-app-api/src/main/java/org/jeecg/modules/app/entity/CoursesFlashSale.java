package org.jeecg.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 限时优惠课程
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@ApiModel(value="courses_flash_sale对象", description="限时优惠课程")
@Data
@TableName("courses_flash_sale")
public class CoursesFlashSale implements Serializable {
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
	/**课程id*/
    @ApiModelProperty(value = "课程id")
    private String courseId;
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
