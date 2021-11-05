package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.List;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/7/15 16:56
 */

@Data
@ApiModel(description = "课程推荐信息类")
public class CoursesDetailRecommendVo {
    /**课程基本信息数组*/
    private List<CoursesDetailVo> coursesDetailVoList;

    /**课程类别*/
    private String category;
}
