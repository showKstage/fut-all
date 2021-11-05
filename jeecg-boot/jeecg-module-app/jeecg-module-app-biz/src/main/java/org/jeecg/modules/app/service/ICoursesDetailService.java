package org.jeecg.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.entity.*;
import org.jeecg.modules.app.vo.CourseSlidesShow;
import org.jeecg.modules.app.vo.CourseUserVo;
import org.jeecg.modules.app.vo.CoursesDetailAddVo;
import org.jeecg.modules.app.vo.CoursesDetailIntroductionVo;
import org.jeecg.modules.app.vo.CoursesDetailVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 课程详情表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface ICoursesDetailService extends IService<CoursesDetail> {

	public List<CoursesDetail> selectByMainId(String mainId);

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(CoursesDetail coursesDetail, List<CoursesCatalogue> coursesCatalogueList, List<CoursesFlashSale> coursesFlashSaleList, List<CoursesUser> coursesUserList, List<CoursesComment> coursesCommentList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(CoursesDetail coursesDetail, List<CoursesCatalogue> coursesCatalogueList, List<CoursesFlashSale> coursesFlashSaleList, List<CoursesUser> coursesUserList, List<CoursesComment> coursesCommentList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);

	/** 根据教师id查询付费课程 */
	List<CoursesDetailVo> queryPayCourses(String teacherId);

	/** 根据教师id查询免费课程 */
	List<CoursesDetailVo> queryFreeCourses(String teacherId);

	/** 查询课程介绍信息*/
	CoursesDetailIntroductionVo queryCoursesDetail(String coursesDetailId);

	/**查询推荐课程信息*/
	List<CoursesDetailVo> queryRecommendCourses(String coursesDetailId);

	/**获取我的收藏课程*/
	List<CoursesDetailVo> getUserCoursesCollect(String userId);

	/**查询课程收藏信息*/
	String queryCoursesCollect(String coursesDetailId,String userId);

	/**添加课程收藏信息*/
	int addCoursesCollect(String id,String coursesDetailId,String userId);

	/**删除课程收藏信息*/
	int deleteCoursesCollect(String coursesDetailId,String userId);

	/**添加课程*/
	int addCourse(CoursesDetailAddVo coursesDetailAddVo);

	/**评论课程*/
	int commentCourse(CoursesComment coursesComment);

	/**课程学习进度*/
	double getCourseLearningProgress(String coursesDetailId,String userId);

	/**获取我的免费课程*/
	List<CourseUserVo> getUserCoursesFree(String userId);

	/**获取我的收费课程*/
	List<CourseUserVo> getUserCoursesPaid(String userId);

	/**获取我的最近学习课程*/
	List<CourseUserVo> getUserCoursesRecently(String userId);

	/**删除最近学习课程*/
	int deleteUserCoursesRecently(String userId,String[] coursesIds);

	/** 根据课程id查询课程分类id */
	List<String> getCoursesCategoryId(String coursesId);

	/** 获取首页课程轮播图 */
	List<CourseSlidesShow> getSlideShow();

	/** 修改首页课程轮播图 */
	int setSlideShow(CourseSlidesShow courseSlidesShow);

	/** 添加首页课程轮播图 */
	int addSlideShow(CourseSlidesShow courseSlidesShow);

	/** 删除首页课程轮播图 */
	int deleteSlideShow(String coursesId);

}
