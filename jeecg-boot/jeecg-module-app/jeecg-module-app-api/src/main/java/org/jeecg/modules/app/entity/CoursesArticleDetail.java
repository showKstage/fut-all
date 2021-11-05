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
 * @Description: 文章详情表
 * @Author: jeecg-boot
 * @Date:   2021-06-20
 * @Version: V1.0
 */
@Data
@TableName("courses_article_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="courses_article_detail对象", description="文章详情表")
public class CoursesArticleDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**文章类别*/
	@Excel(name = "文章类别", width = 15)
    @ApiModelProperty(value = "文章类别")
    private String articleTypeId;
	/**文章标题*/
	@Excel(name = "文章标题", width = 15)
    @ApiModelProperty(value = "文章标题")
    private String articleTitle;
	/**文章图片*/
	@Excel(name = "文章图片", width = 15)
    @ApiModelProperty(value = "文章图片")
    private String articlePicture;
	/**文章内容*/
	@Excel(name = "文章内容", width = 15)
    @ApiModelProperty(value = "文章内容")
    private String articleContent;
	/**创建人id（教师）*/
	@Excel(name = "创建人id（教师）", width = 15)
    @ApiModelProperty(value = "创建人id（教师）")
    private String teacherId;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
}
