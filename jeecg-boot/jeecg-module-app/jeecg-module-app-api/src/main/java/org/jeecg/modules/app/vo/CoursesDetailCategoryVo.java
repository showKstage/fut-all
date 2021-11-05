package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/7/15 17:22
 */
@Data
@ApiModel(description = "课程类别")
public class CoursesDetailCategoryVo {

    /**课程类别id*/
    private String id;

    /**课程类别名称*/
    private String categoryName;
}
