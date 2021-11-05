package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.app.entity.CoursesFlashSale;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

import java.util.List;

/**
 * @Description: 课程介绍信息
 * @Author: shiHao.qiu
 * @Date: 2021/7/8 23:02
 */
@Data
@ApiModel(value="课程介绍信息", description="课程介绍信息")
public class CoursesDetailIntroductionVo {
    /**课程名*/
    @ApiModelProperty(value = "课程名")
    private String coursesDetailName;
    /**课程图片*/
    @ApiModelProperty(value = "课程图片")
    private String coursesDetailPicture;
    /**课程介绍*/
    @ApiModelProperty(value = "课程介绍")
    private String coursesDetailIntroduce;
    /**课程价格*/
    @ApiModelProperty(value = "课程价格")
    private Double coursesDetailPrice;
    /**课程星级*/
    @ApiModelProperty(value = "课程星级")
    private Double coursesDetailLevel;
    /**课程评分*/
    @ApiModelProperty(value = "课程评分")
    private Double coursesDetailScore;
    /**学习人数*/
    @ApiModelProperty(value = "学习人数")
    private Integer learningNum;
    /**教师id*/
    @ApiModelProperty(value = "教师id")
    private String teacherId;
    /**教师粉丝量*/
    @ApiModelProperty(value = "教师粉丝量")
    private Integer fans;
    /**教师名字*/
    @ApiModelProperty(value = "教师名字")
    private String teacherName;
    /**限时优惠课程*/
    @ApiModelProperty(value = "限时优惠课程")
    private CoursesFlashSale coursesFlashSale;
}
