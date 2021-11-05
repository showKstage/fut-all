package org.jeecg.modules.app.vo;

import lombok.Data;

import java.util.List;

/**
* @Description: 系列课程详细信息类
*/
@Data
public class CoursesSeriesDetailVo {


    /**系列课程基本信息类*/
    private CoursesSeriesVo coursesSeriesVo;

    /**系列课程学习人数*/
    private int learningNum;

    /**课程基本信息列表*/
    private List<CoursesDetailVo> coursesDetailVoList;

}
