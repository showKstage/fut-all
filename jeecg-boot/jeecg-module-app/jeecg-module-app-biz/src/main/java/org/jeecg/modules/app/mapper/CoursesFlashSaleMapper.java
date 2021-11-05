package org.jeecg.modules.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesFlashSale;

import java.util.List;

/**
 * @Description: 限时优惠课程
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface CoursesFlashSaleMapper extends BaseMapper<CoursesFlashSale> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<CoursesFlashSale> selectByMainId(@Param("mainId") String mainId);

	/**
	 * 根据课程id查询
	 */
	public CoursesFlashSale queryCourseSaleByCourseId(@Param("coursesId") String coursesId);

}
