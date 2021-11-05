package org.jeecg.modules.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesLabelCourse;

import java.util.List;

/**
 * @Description: 课程标签表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface CoursesLabelCourseMapper extends BaseMapper<CoursesLabelCourse> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<CoursesLabelCourse> selectByMainId(@Param("mainId") String mainId);
}
