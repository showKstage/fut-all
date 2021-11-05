package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/7/23 10:45
 */

@Data
@ApiModel(description = "课程分类展示")
public class CoursesCategoryVo {
    /**课程类别id*/
    private String id;

    /**课程类别父id*/
    private String parentId;

    /**子课程类别列表*/
    private List<CoursesCategoryVo> children;

    /**课程类别名称*/
    private String categoryName;
}
