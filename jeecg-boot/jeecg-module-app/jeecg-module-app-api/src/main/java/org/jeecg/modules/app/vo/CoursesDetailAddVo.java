package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.jeecg.modules.app.entity.CoursesFlashSale;

import java.util.Date;


/**
 * @Description: 课程添加
 */
@Data
@ApiModel(description = "课程基本信息类")
public class CoursesDetailAddVo {

    /**课程id*/
    private String courseId;

    /**教师id*/
    private String teacherId;

    /**课程名*/
    private String courseName;

    /**课程图片*/
    private String coursePicture;

    /**课程标签*/
    private String courseLabelId;

    /**课程分类*/
    private String courseCategoryId;

    /**课程价格*/
    private double price;

    /**课程介绍*/
    private String coursesDetailIntroduce;

    /**课程创建时间*/
    private Date coursesDetailCreateTime;

}
