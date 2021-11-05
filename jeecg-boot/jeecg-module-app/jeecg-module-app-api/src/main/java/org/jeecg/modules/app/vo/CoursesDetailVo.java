package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.jeecg.modules.app.entity.CoursesFlashSale;


/**
 * @Description: 课程基本信息类
 * @Author: jiale.zhang
 * @Date:   2021-06-24
 * @Version: V1.0
 */
@Data
@ApiModel(description = "课程基本信息类")
public class CoursesDetailVo {

    /**课程id*/
    private String courseId;

    /**课程名*/
    private String courseName;

    /**课程图片*/
    private String coursePicture;

    /**学习人数*/
    private int learningNum;

    /**课程价格*/
    private double price;

    /**限时优惠信息*/
    private CoursesFlashSale coursesFlashSale;

}
