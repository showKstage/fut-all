package org.jeecg.modules.app.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 课程评价详情内容
 * @Author: shiHao.qiu
 * @Date: 2021/7/9 11:10
 */
@Data
@ApiModel(value="课程评价详情内容", description="课程评价详情内容")
public class CoursesCommentDetailVo {
    /**评价id*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**用户id*/
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**用户头像*/
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    /**用户名*/
    @ApiModelProperty(value = "用户名")
    private String realName;
    /**评论内容*/
    @ApiModelProperty(value = "评论内容")
    private String commentContent;
    /**评论时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "评论时间")
    private Date commentTime;
    /**评价星级*/
    @ApiModelProperty(value = "评价星级")
    private Double commentLevel;
}
