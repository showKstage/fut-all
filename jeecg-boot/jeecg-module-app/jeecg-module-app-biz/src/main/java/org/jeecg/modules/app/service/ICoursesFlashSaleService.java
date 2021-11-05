package org.jeecg.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.entity.CoursesFlashSale;

import java.util.List;

/**
 * @Description: 限时优惠课程
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface ICoursesFlashSaleService extends IService<CoursesFlashSale> {

	public List<CoursesFlashSale> selectByMainId(String mainId);

	/**
	 * 根据课程id查询
	 */
	public CoursesFlashSale queryCourseSaleByCourseId(String coursesId);

}
