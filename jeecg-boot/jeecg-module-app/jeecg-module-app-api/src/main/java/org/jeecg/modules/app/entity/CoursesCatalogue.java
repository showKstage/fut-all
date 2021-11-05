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
 * @Description: 课程目录表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@ApiModel(value="courses_catalogue对象", description="课程目录表")
@Data
@TableName("courses_catalogue")
public class CoursesCatalogue implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**父目录id*/
	@Excel(name = "父目录id", width = 15)
    @ApiModelProperty(value = "父目录id")
    private String parentId;
	/**课程id*/
    @ApiModelProperty(value = "课程id")
    private String coursesDetailId;
	/**目录名*/
	@Excel(name = "目录名", width = 15)
    @ApiModelProperty(value = "目录名")
    private String catalogueName;
	/**视频点播id*/
	@Excel(name = "视频点播id", width = 15)
    @ApiModelProperty(value = "视频点播id")
    private String vodId;
}
