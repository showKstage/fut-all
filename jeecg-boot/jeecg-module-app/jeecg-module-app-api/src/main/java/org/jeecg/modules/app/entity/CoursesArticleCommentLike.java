package org.jeecg.modules.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/7/13 18:02
 */
@Data
@ApiModel(value="文章评论点赞", description="文章评论点赞")
public class CoursesArticleCommentLike {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**评论id*/
    @ApiModelProperty(value = "评论id")
    private String commentId;
    /**用户id*/
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**点赞时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "点赞时间")
    private Date commentLikeTime;
}
