package org.jeecg.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.entity.CoursesLabelCourse;

import java.util.List;

/**
 * @Description: 课程标签表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface ICoursesLabelCourseService extends IService<CoursesLabelCourse> {

	public List<CoursesLabelCourse> selectByMainId(String mainId);
}
