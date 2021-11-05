package org.jeecg.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.app.entity.CoursesUser;
import org.jeecg.modules.app.mapper.CoursesUserMapper;
import org.jeecg.modules.app.service.ICoursesUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 用户课程关联表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Service
public class CoursesUserServiceImpl extends ServiceImpl<CoursesUserMapper, CoursesUser> implements ICoursesUserService {
	
	@Autowired
	private CoursesUserMapper coursesUserMapper;
	
	@Override
	public List<CoursesUser> selectByMainId(String mainId) {
		return coursesUserMapper.selectByMainId(mainId);
	}
}
