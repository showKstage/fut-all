package org.jeecg.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.entity.CoursesComment;
import org.jeecg.modules.app.vo.CoursesCommentDetailVo;
import org.jeecg.modules.app.vo.CoursesCommentVo;

import java.util.List;

/**
 * @Description: 课程评论表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface ICoursesCommentService extends IService<CoursesComment> {

	public List<CoursesComment> selectByMainId(String mainId);

	/**查询课程评价信息 */
	public List<CoursesCommentDetailVo> queryCoursesCommentList(String coursesDetailId,int limit);
}
