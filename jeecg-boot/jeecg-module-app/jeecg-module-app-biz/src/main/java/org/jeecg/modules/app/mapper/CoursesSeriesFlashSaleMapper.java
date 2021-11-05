package org.jeecg.modules.app.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesSeriesFlashSale;

/**
 * @Description:系列课程优惠
 * @Author: shiHao.qiu
 * @Date: 2021/7/19 10:31
 */

public interface CoursesSeriesFlashSaleMapper {

    public CoursesSeriesFlashSale queryCourseSaleByCourseSeriesId(@Param("courseSeriesId") String courseSeriesId);
}
