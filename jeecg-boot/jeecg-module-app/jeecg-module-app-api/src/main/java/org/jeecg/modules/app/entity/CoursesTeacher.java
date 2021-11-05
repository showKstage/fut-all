package org.jeecg.modules.app.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 教师信息表
 * @Author: jeecg-boot
 * @Date:   2021-06-27
 * @Version: V1.0
 */
@Data
@TableName("courses_teacher")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="courses_teacher对象", description="教师信息表")
public class CoursesTeacher implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**教师名字*/
	@Excel(name = "教师名字", width = 15)
    @ApiModelProperty(value = "教师名字")
    private String teacherName;
	/**介绍*/
	@Excel(name = "介绍", width = 15)
    @ApiModelProperty(value = "介绍")
    private String introduce;
	/**头像*/
	@Excel(name = "头像", width = 15)
    @ApiModelProperty(value = "头像")
    private String avatar;
	/**粉丝量*/
	@Excel(name = "粉丝量", width = 15)
    @ApiModelProperty(value = "粉丝量")
    private Integer fans;
	/**关注量*/
	@Excel(name = "关注量", width = 15)
    @ApiModelProperty(value = "关注量")
    private Integer follow;
}
