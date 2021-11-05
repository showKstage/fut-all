package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.app.entity.CoursesLabelCourse;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

import java.util.List;

/**
 * @Description: 标签表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="courses_labelPage对象", description="标签表")
public class CoursesLabelPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private String id;
	/**标签名*/
	@Excel(name = "标签名", width = 15)
	@ApiModelProperty(value = "标签名")
    private String labelName;

	@ExcelCollection(name="课程标签表")
	@ApiModelProperty(value = "课程标签表")
	private List<CoursesLabelCourse> coursesLabelCourseList;

}
