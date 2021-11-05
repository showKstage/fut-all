package org.jeecg.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.app.entity.CoursesLabelCourse;
import org.jeecg.modules.app.mapper.CoursesLabelCourseMapper;
import org.jeecg.modules.app.service.ICoursesLabelCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 课程标签表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Service
public class CoursesLabelCourseServiceImpl extends ServiceImpl<CoursesLabelCourseMapper, CoursesLabelCourse> implements ICoursesLabelCourseService {
	
	@Autowired
	private CoursesLabelCourseMapper coursesLabelCourseMapper;
	
	@Override
	public List<CoursesLabelCourse> selectByMainId(String mainId) {
		return coursesLabelCourseMapper.selectByMainId(mainId);
	}
}
