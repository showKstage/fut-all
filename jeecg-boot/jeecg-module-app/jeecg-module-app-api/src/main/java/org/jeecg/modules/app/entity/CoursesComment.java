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
 * @Description: 课程评论表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@ApiModel(value="courses_comment对象", description="课程评论表")
@Data
@TableName("courses_comment")
public class CoursesComment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**课程id*/
    @ApiModelProperty(value = "课程id")
    private String coursesDetailId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private String userId;
	/**评论内容*/
	@Excel(name = "评论内容", width = 15)
    @ApiModelProperty(value = "评论内容")
    private String commentContent;
	/**评论时间*/
	@Excel(name = "评论时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "评论时间")
    private Date commentTime;
	/**评价星级*/
	@Excel(name = "评价星级", width = 15)
    @ApiModelProperty(value = "评价星级")
    private Double commentLevel;
}
