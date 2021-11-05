package org.jeecg.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.app.entity.CoursesComment;
import org.jeecg.modules.app.mapper.CoursesCommentMapper;
import org.jeecg.modules.app.service.ICoursesCommentService;
import org.jeecg.modules.app.vo.CoursesCommentDetailVo;
import org.jeecg.modules.app.vo.CoursesCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 课程评论表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Service
public class CoursesCommentServiceImpl extends ServiceImpl<CoursesCommentMapper, CoursesComment> implements ICoursesCommentService {
	
	@Autowired
	private CoursesCommentMapper coursesCommentMapper;
	
	@Override
	public List<CoursesComment> selectByMainId(String mainId) {
		return coursesCommentMapper.selectByMainId(mainId);
	}

	/**查询课程评价信息 */
	@Override
	public List<CoursesCommentDetailVo> queryCoursesCommentList(String coursesDetailId,int limit) {
		if (limit <= 0) {
			return coursesCommentMapper.queryCoursesCommentList(coursesDetailId,0);
		}else{
			return coursesCommentMapper.queryCoursesCommentList(coursesDetailId,limit);
		}
	}
}
