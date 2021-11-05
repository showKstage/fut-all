package org.jeecg.modules.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.jeecg.modules.app.entity.CoursesCatalogue;
import org.jeecg.modules.app.entity.CoursesComment;
import org.jeecg.modules.app.entity.CoursesFlashSale;
import org.jeecg.modules.app.entity.CoursesUser;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 课程详情表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="courses_detailPage对象", description="课程详情表")
public class CoursesDetailPage {

	/**主键*/
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
    private java.util.Date coursesDetailCreatetime;

	@ExcelCollection(name="课程目录表")
	@ApiModelProperty(value = "课程目录表")
	private List<CoursesCatalogue> coursesCatalogueList;
	@ExcelCollection(name="限时优惠课程")
	@ApiModelProperty(value = "限时优惠课程")
	private List<CoursesFlashSale> coursesFlashSaleList;
	@ExcelCollection(name="用户课程关联表")
	@ApiModelProperty(value = "用户课程关联表")
	private List<CoursesUser> coursesUserList;
	@ExcelCollection(name="课程评论表")
	@ApiModelProperty(value = "课程评论表")
	private List<CoursesComment> coursesCommentList;

}
