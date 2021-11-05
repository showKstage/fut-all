package org.jeecg.modules.app.service;

import org.jeecg.modules.app.entity.CoursesSeriesFlashSale;

/**
 * @Description: 系列课程限时优惠
 * @Author: shiHao.qiu
 * @Date: 2021/7/19 10:23
 */

public interface ICoursesSeriesFlashSaleService {
    /**
     * 根据系列课程id查询
     */
    public CoursesSeriesFlashSale queryCourseSaleByCourseSeriesId(String courseSeriesId);
}
