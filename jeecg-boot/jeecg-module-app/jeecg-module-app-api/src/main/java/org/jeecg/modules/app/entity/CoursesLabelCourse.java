package org.jeecg.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 课程标签表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@ApiModel(value="courses_label_course对象", description="课程标签表")
@Data
@TableName("courses_label_course")
public class CoursesLabelCourse implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**标签id*/
	@Excel(name = "标签id", width = 15)
    @ApiModelProperty(value = "标签id")
    private String labelId;
	/**课程id*/
    @ApiModelProperty(value = "课程id")
    private String courseId;
}
