package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.jeecg.modules.app.entity.CoursesComment;

import java.util.List;
import java.util.Map;

/**
 * @Description: 课程评价信息
 * @Author: shiHao.qiu
 * @Date: 2021/7/9 10:53
 */
@Data
@ApiModel(value="课程评价信息", description="课程评价信息")
public class CoursesCommentVo {
    /**课程评价列表*/
    private List<CoursesCommentDetailVo> coursesCommentDetailVoList;
    /**课程评价数*/
    private int commentNum;
    /**课程评价平均分*/
    private double commentAverage;
    /**课程评价星级分布*/
    private Map<String,Integer> starMap;
}
