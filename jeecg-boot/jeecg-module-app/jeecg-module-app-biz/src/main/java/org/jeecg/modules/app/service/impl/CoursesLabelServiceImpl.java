package org.jeecg.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.app.entity.CoursesLabelUser;
import org.jeecg.modules.app.mapper.CoursesLabelMapper;
import org.jeecg.modules.app.service.ICoursesLabelService;
import org.jeecg.modules.app.vo.CourseLabelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 标签表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Service
public class CoursesLabelServiceImpl extends ServiceImpl<CoursesLabelMapper, CoursesLabelUser> implements ICoursesLabelService {

	@Autowired
	private CoursesLabelMapper coursesLabelMapper;

	@Override
	public List<String> getCoursesCategory(String categoryName) {
		List<String> coursesCategory = coursesLabelMapper.queryCoursesCategory(categoryName);
		return coursesCategory;
	}

	@Override
	public int addUserLabel(String categoryName,String userId) {
		String categoryId = coursesLabelMapper.queryLabel(categoryName);
		String flag = coursesLabelMapper.queryUserLabel(categoryId,userId);
		if (flag==null)	return coursesLabelMapper.addLabel(categoryId,userId);
		return 0;
	}

	@Override
	public List<CourseLabelVo> getAllCourseLabel() {
		return coursesLabelMapper.getAllCourseLabel();
	}

	@Override
	public int addCourseLabel(String id, String courseLabelName) {
		if (coursesLabelMapper.queryCourseLabel(courseLabelName)!=null)return -1;
		return coursesLabelMapper.addCourseLabel(id, courseLabelName);
	}

	@Override
	public int deleteUerCourseLabel(String userId) {
		return coursesLabelMapper.deleteUerCourseLabel(userId);
	}

}
