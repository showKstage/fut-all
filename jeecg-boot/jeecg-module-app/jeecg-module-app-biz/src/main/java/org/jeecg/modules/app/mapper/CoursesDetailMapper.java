package org.jeecg.modules.app.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesComment;
import org.jeecg.modules.app.entity.CoursesDetail;
import org.jeecg.modules.app.vo.*;


/**
 * @Description: 课程详情表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface CoursesDetailMapper extends BaseMapper<CoursesDetail> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<CoursesDetail> selectByMainId(@Param("mainId") String mainId);

	/**
	 * 根据教师id查询付费课程
	 */
	List<CoursesDetailVo> queryPayCoursesByTeacherId(@Param("teacherId") String teacherId);

	/**
	 * 根据教师id查询免费课程
	 */
	List<CoursesDetailVo> queryFreeCoursesByTeacherId(@Param("teacherId") String teacherId);

	/**
	 * 查询课程介绍信息
	 */
	CoursesDetailIntroductionVo queryCoursesDetail(@Param("coursesDetailId") String coursesDetailId);

	/**
	 * 查询推荐课程信息
	 */
	List<CoursesDetailVo> queryRecommendCourses(@Param("coursesDetailId") String coursesDetailId);

	/**
	 * 获取我的收藏课程
	 */
	List<CoursesDetailVo> getUserCoursesCollect(@Param("userId")String userId);

	/**
	 * 查询课程收藏信息
	 */
	String queryCoursesCollect(@Param("coursesDetailId")String coursesDetailId,@Param("userId")String userId);

	/**
	 * 添加课程收藏信息
	 */
	int addCoursesCollect(@Param("id")String id,@Param("coursesDetailId")String coursesDetailId,@Param("userId")String userId);

	/**
	 * 删除课程收藏信息
	 */
	int deleteCoursesCollect(@Param("coursesDetailId")String coursesDetailId,@Param("userId")String userId);

	/**
	 * 添加课程
	 */
	int addCourse(CoursesDetailAddVo coursesDetailAddVo);
	/**
	 * 根据课程名查询课程
	 */
	String queryCourseByCourseName(@Param("courseName")String courseName);
	/**
	 * 评论课程
	 */
	int commentCourse(CoursesComment coursesComment);
	/**
	 * 获取课程视频个数
	 */
	int queryVideosNum (@Param("coursesDetailId")String coursesDetailId);
	/**
	 * 获取课程视频观看个数
	 */
	int queryWatchNum (@Param("coursesDetailId")String coursesDetailId,@Param("userId")String userId);

	/**
	 * 获取我的免费课程
	 */
	List<CourseUserVo> getUserCoursesFree(@Param("userId")String userId);

	/**
	 * 获取我的收费课程
	 */
	List<CourseUserVo> getUserCoursesPaid(@Param("userId")String userId);

	/**
	 * 获取我的最近学习课程
	 */
	List<CourseUserVo> getUserCoursesRecently(@Param("userId")String userId);

	/**
	 * 删除最近学习课程
	 */
	int deleteUserCoursesRecently(@Param("userId")String userId,@Param("coursesIds")String[] coursesIds);


	/** 根据课程id查询课程分类id */
	List<String> getCoursesCategoryIdByCoursesId(@Param("coursesId") String coursesId);

	/**
	 * 获取首页课程轮播图
	 */
	List<CourseSlidesShow> getSlideShow();

	/**
	 *修改首页课程轮播图
	 */
	int setSlideShow(CourseSlidesShow courseSlidesShow);

	/**
	 *添加首页课程轮播图
	 */
	int addSlideShow(CourseSlidesShow courseSlidesShow);

	/**
	 *删除首页课程轮播图
	 */
	int deleteSlideShow(@Param("coursesId") String coursesId);
}
