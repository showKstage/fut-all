package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/8/20 16:05
 */
@Data
@ApiModel(description = "收费/免费课程")
public class CourseUserVo {
    /**课程id*/
    private String courseId;

    /**课程名*/
    private String coursesDetailName;

    /**课程图片*/
    private String coursesDetailPicture;

    /**学习进度*/
    private Double LearningProgress;

}
