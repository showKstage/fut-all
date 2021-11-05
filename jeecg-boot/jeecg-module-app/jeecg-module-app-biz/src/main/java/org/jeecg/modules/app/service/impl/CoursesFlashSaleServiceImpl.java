package org.jeecg.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.app.entity.CoursesFlashSale;
import org.jeecg.modules.app.mapper.CoursesFlashSaleMapper;
import org.jeecg.modules.app.service.ICoursesFlashSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 限时优惠课程
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Service
public class CoursesFlashSaleServiceImpl extends ServiceImpl<CoursesFlashSaleMapper, CoursesFlashSale> implements ICoursesFlashSaleService {
	
	@Autowired
	private CoursesFlashSaleMapper coursesFlashSaleMapper;
	
	@Override
	public List<CoursesFlashSale> selectByMainId(String mainId) {
		return coursesFlashSaleMapper.selectByMainId(mainId);
	}

	@Override
	public CoursesFlashSale queryCourseSaleByCourseId(String coursesId){
		return coursesFlashSaleMapper.queryCourseSaleByCourseId(coursesId);
	}

}
