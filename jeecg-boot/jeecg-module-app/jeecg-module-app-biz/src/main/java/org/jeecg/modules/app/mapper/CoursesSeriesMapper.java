package org.jeecg.modules.app.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.app.entity.CoursesSeries;
import org.jeecg.modules.app.vo.CoursesSeriesVo;

import java.util.List;

/**
 * @Description: 系列课程表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface CoursesSeriesMapper extends BaseMapper<CoursesSeries> {
    /**系列课程查询*/
    public List<CoursesSeriesVo> querySeriesCourses();

    /**通过id查询系列课程基本信息*/
    public CoursesSeriesVo querySeriesCoursesById(@Param("coursesSeriesId")String coursesSeriesId);

    /**系列课程学习人数统计*/
    public int querySeriesCoursesLearningNum(@Param("coursesSeriesId")String coursesSeriesId);



}
