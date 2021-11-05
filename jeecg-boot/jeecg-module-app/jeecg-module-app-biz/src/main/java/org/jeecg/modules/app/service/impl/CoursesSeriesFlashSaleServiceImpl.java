package org.jeecg.modules.app.service.impl;

import org.jeecg.modules.app.entity.CoursesSeriesFlashSale;
import org.jeecg.modules.app.mapper.CoursesSeriesFlashSaleMapper;
import org.jeecg.modules.app.service.ICoursesSeriesFlashSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/7/19 10:30
 */
@Service
public class CoursesSeriesFlashSaleServiceImpl implements ICoursesSeriesFlashSaleService {
    @Autowired
    private CoursesSeriesFlashSaleMapper coursesSeriesFlashSaleMapper;

    @Override
    public CoursesSeriesFlashSale queryCourseSaleByCourseSeriesId(String courseSeriesId) {
        return coursesSeriesFlashSaleMapper.queryCourseSaleByCourseSeriesId(courseSeriesId);
    }
}
