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
 * @Description: 文章关注表
 * @Author: jeecg-boot
 * @Date:   2021-06-20
 * @Version: V1.0
 */
@Data
@TableName("cources_article_focus")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cources_article_focus对象", description="文章关注表")
public class CoursesArticleFocus implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**文章id*/
	@Excel(name = "文章id", width = 15)
    @ApiModelProperty(value = "文章id")
    private String articleId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private String userId;
}
