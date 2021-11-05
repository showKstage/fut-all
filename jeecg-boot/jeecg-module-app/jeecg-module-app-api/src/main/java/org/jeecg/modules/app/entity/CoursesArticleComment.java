package org.jeecg.modules.app.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 文章评论表
 * @Author: jeecg-boot
 * @Date:   2021-06-24
 * @Version: V1.0
 */
@Data
@TableName("courses_article_comment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="courses_article_comment对象", description="文章评论表")
public class CoursesArticleComment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**评论内容*/
	@Excel(name = "评论内容", width = 15)
    @ApiModelProperty(value = "评论内容")
    private String comments;
	/**文章id*/
	@Excel(name = "文章id", width = 15)
    @ApiModelProperty(value = "文章id")
    private String articleId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private String userId;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
    private String userName;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**父评论id*/
	private String parentId;
}
