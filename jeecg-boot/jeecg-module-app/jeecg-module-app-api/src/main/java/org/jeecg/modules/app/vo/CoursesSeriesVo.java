package org.jeecg.modules.app.vo;

import lombok.Data;
import org.jeecg.modules.app.entity.CoursesSeriesFlashSale;

/**
* @Description: 系列课程基本信息类
* @Author: jiale.zhang
* @Date:   2021-06-30
* @Version: V1.0
*/
@Data
public class CoursesSeriesVo {

    /**系列课程id*/
    private String id;

    /**系列课程名*/
    private String coursesSeriesName;

    /**系列课程图片*/
    private String coursesSeriesPicture;

    /**系列课程价格*/
    private Double price;

    /**系列课程优惠信息*/
    private CoursesSeriesFlashSale coursesSeriesFlashSale;

    /**系列课程简介*/
    private String coursesSeriesIntroduce;



}
