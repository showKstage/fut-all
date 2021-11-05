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
 * @Description: 课程详情表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@ApiModel(value="courses_detail对象", description="课程详情表")
@Data
@TableName("courses_detail")
public class CoursesDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**教师id*/
	@Excel(name = "教师id", width = 15)
    @ApiModelProperty(value = "教师id")
    private String teacherId;
	/**系列课程id*/
	@Excel(name = "系列课程id", width = 15)
    @ApiModelProperty(value = "系列课程id")
    private String coursesSeriesId;
	/**课程名*/
	@Excel(name = "课程名", width = 15)
    @ApiModelProperty(value = "课程名")
    private String coursesDetailName;
	/**课程图片*/
	@Excel(name = "课程图片", width = 15)
    @ApiModelProperty(value = "课程图片")
    private String coursesDetailPicture;
	/**课程介绍*/
	@Excel(name = "课程介绍", width = 15)
    @ApiModelProperty(value = "课程介绍")
    private String coursesDetailIntroduce;
	/**课程价格*/
	@Excel(name = "课程价格", width = 15)
    @ApiModelProperty(value = "课程价格")
    private Double coursesDetailPrice;
	/**课程星级*/
	@Excel(name = "课程星级", width = 15)
    @ApiModelProperty(value = "课程星级")
    private Double coursesDetailLevel;
	/**课程评分*/
	@Excel(name = "课程评分", width = 15)
    @ApiModelProperty(value = "课程评分")
    private Double coursesDetailScore;
	/**课程创建时间*/
	@Excel(name = "课程创建时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "课程创建时间")
    private Date coursesDetailCreatetime;
}
