package org.jeecg.modules.app.vo;

import java.util.List;

import org.jeecg.modules.app.entity.CoursesDetail;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 系列课程表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="courses_seriesPage对象", description="系列课程表")
public class CoursesSeriesPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private String id;
	/**系列课程名*/
	@Excel(name = "系列课程名", width = 15)
	@ApiModelProperty(value = "系列课程名")
    private String coursesSeriesName;
	/**系列课程图片*/
	@Excel(name = "系列课程图片", width = 15)
	@ApiModelProperty(value = "系列课程图片")
    private String coursesSeriesPicture;
	/**系列课程简介*/
	@Excel(name = "系列课程简介", width = 15)
	@ApiModelProperty(value = "系列课程简介")
    private String coursesSeriesIntroduce;
	/**系列课程价格*/
	@Excel(name = "系列课程价格", width = 15)
	@ApiModelProperty(value = "系列课程价格")
    private Double coursesSeriesPrice;
	/**系列课程创建时间*/
	@Excel(name = "系列课程创建时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "系列课程创建时间")
    private java.util.Date coursesCreatetime;

	@ExcelCollection(name="课程详情表")
	@ApiModelProperty(value = "课程详情表")
	private List<CoursesDetail> coursesDetailList;

}
