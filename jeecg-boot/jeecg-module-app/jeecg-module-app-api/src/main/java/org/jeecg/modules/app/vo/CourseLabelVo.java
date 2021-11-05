package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/7/29 11:42
 */
@Data
@ApiModel(description = "课程标签")
public class CourseLabelVo {
    /**课程标签id*/
    private String courseLabelId;

    /**课程标签名*/
    private String courseLabelName;
}
