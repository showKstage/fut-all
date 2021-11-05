package org.jeecg.modules.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesComment;
import org.jeecg.modules.app.vo.CoursesCommentDetailVo;


import java.util.List;

/**
 * @Description: 课程评论表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface CoursesCommentMapper extends BaseMapper<CoursesComment> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<CoursesComment> selectByMainId(@Param("mainId") String mainId);
	/**查询课程评价信息 */
	public List<CoursesCommentDetailVo> queryCoursesCommentList(@Param("coursesDetailId") String coursesDetailId,
																@Param("limit") int limit);
}
