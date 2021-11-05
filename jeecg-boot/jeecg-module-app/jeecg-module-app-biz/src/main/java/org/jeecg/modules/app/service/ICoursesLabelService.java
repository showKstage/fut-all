package org.jeecg.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.entity.CoursesLabelUser;
import org.jeecg.modules.app.vo.CourseLabelVo;

import java.util.List;

/**
 * @Description: 标签表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface ICoursesLabelService extends IService<CoursesLabelUser> {

	/** 查询子标签分类 */
	List<String> getCoursesCategory(String id);

	/** 查询标签分类并插入用户标签表 */
	int addUserLabel(String categoryName, String userId);

	/**获取课程标签*/
	List<CourseLabelVo> getAllCourseLabel();

	/**添加课程标签*/
	int addCourseLabel(String id, String courseLabelName);

	/**删除用户已有标签*/
	int deleteUerCourseLabel(String userId);

}
