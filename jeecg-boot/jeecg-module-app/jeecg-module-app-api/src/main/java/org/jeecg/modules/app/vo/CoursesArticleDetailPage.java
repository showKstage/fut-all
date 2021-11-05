package org.jeecg.modules.app.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 文章详情表
 * @Author: jiale.zhang
 * @Date:   2021-06-22
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="courses_article_detail对象", description="文章详情表")
public class CoursesArticleDetailPage {
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
    /**文章内容*/
    @Excel(name = "文章内容", width = 15)
    @ApiModelProperty(value = "文章内容")
    private String articleContent;
    /**创建人*/
    @Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private String userId;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;

    /**阅读量*/
    @Excel(name = "文章阅读量")
    @ApiModelProperty(value = "文章阅读量")
    private int visit;
}
