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
 * @Description: 用户标签表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@ApiModel(value="courses_label对象", description="标签表")
@Data
@TableName("courses_label_user")
public class CoursesLabelUser implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**标签名*/
	@Excel(name = "标签名", width = 15)
    @ApiModelProperty(value = "标签名")
    private String labelName;
	/**父标签id*/
	@Excel(name = "父标签id", width = 36)
    @ApiModelProperty(value = "父标签id")
    private String parentLabelId;
	/**用户id*/
	@Excel(name = "用户id", width = 36)
    @ApiModelProperty(value = "用户id")
    private String userId;

}
