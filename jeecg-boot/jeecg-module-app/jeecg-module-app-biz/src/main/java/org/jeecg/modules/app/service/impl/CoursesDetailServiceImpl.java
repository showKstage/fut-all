package org.jeecg.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.app.entity.*;
import org.jeecg.modules.app.mapper.*;
import org.jeecg.modules.app.service.ICoursesDetailService;
import org.jeecg.modules.app.vo.CourseSlidesShow;
import org.jeecg.modules.app.vo.CourseUserVo;
import org.jeecg.modules.app.vo.CoursesDetailAddVo;
import org.jeecg.modules.app.vo.CoursesDetailIntroductionVo;
import org.jeecg.modules.app.vo.CoursesDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @Description: 课程详情表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Service
public class CoursesDetailServiceImpl extends ServiceImpl<CoursesDetailMapper, CoursesDetail> implements ICoursesDetailService {

	@Autowired
	private CoursesDetailMapper coursesDetailMapper;
	@Autowired
	private CoursesCatalogueMapper coursesCatalogueMapper;
	@Autowired
	private CoursesFlashSaleMapper coursesFlashSaleMapper;
	@Autowired
	private CoursesUserMapper coursesUserMapper;
	@Autowired
	private CoursesCommentMapper coursesCommentMapper;
	@Autowired
	private CoursesLabelMapper coursesLabelMapper;



	@Override
	public List<CoursesDetail> selectByMainId(String mainId) {
		return coursesDetailMapper.selectByMainId(mainId);
	}

	@Override
	@Transactional
	public void saveMain(CoursesDetail coursesDetail, List<CoursesCatalogue> coursesCatalogueList, List<CoursesFlashSale> coursesFlashSaleList, List<CoursesUser> coursesUserList, List<CoursesComment> coursesCommentList) {
		coursesDetailMapper.insert(coursesDetail);
		if(coursesCatalogueList!=null && coursesCatalogueList.size()>0) {
			for(CoursesCatalogue entity:coursesCatalogueList) {
				//外键设置
				entity.setCoursesDetailId(coursesDetail.getId());
				coursesCatalogueMapper.insert(entity);
			}
		}
		if(coursesFlashSaleList!=null && coursesFlashSaleList.size()>0) {
			for(CoursesFlashSale entity:coursesFlashSaleList) {
				//外键设置
				entity.setCourseId(coursesDetail.getId());
				coursesFlashSaleMapper.insert(entity);
			}
		}
		if(coursesUserList!=null && coursesUserList.size()>0) {
			for(CoursesUser entity:coursesUserList) {
				//外键设置
				entity.setCoursesDetailId(coursesDetail.getId());
				coursesUserMapper.insert(entity);
			}
		}
		if(coursesCommentList!=null && coursesCommentList.size()>0) {
			for(CoursesComment entity:coursesCommentList) {
				//外键设置
				entity.setCoursesDetailId(coursesDetail.getId());
				coursesCommentMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(CoursesDetail coursesDetail,List<CoursesCatalogue> coursesCatalogueList,List<CoursesFlashSale> coursesFlashSaleList,List<CoursesUser> coursesUserList,List<CoursesComment> coursesCommentList) {
		coursesDetailMapper.updateById(coursesDetail);
		
		//1.先删除子表数据
		coursesCatalogueMapper.deleteByMainId(coursesDetail.getId());
		coursesFlashSaleMapper.deleteByMainId(coursesDetail.getId());
		coursesUserMapper.deleteByMainId(coursesDetail.getId());
		coursesCommentMapper.deleteByMainId(coursesDetail.getId());
		
		//2.子表数据重新插入
		if(coursesCatalogueList!=null && coursesCatalogueList.size()>0) {
			for(CoursesCatalogue entity:coursesCatalogueList) {
				//外键设置
				entity.setCoursesDetailId(coursesDetail.getId());
				coursesCatalogueMapper.insert(entity);
			}
		}
		if(coursesFlashSaleList!=null && coursesFlashSaleList.size()>0) {
			for(CoursesFlashSale entity:coursesFlashSaleList) {
				//外键设置
				entity.setCourseId(coursesDetail.getId());
				coursesFlashSaleMapper.insert(entity);
			}
		}
		if(coursesUserList!=null && coursesUserList.size()>0) {
			for(CoursesUser entity:coursesUserList) {
				//外键设置
				entity.setCoursesDetailId(coursesDetail.getId());
				coursesUserMapper.insert(entity);
			}
		}
		if(coursesCommentList!=null && coursesCommentList.size()>0) {
			for(CoursesComment entity:coursesCommentList) {
				//外键设置
				entity.setCoursesDetailId(coursesDetail.getId());
				coursesCommentMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		coursesCatalogueMapper.deleteByMainId(id);
		coursesFlashSaleMapper.deleteByMainId(id);
		coursesUserMapper.deleteByMainId(id);
		coursesCommentMapper.deleteByMainId(id);
		coursesDetailMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			coursesCatalogueMapper.deleteByMainId(id.toString());
			coursesFlashSaleMapper.deleteByMainId(id.toString());
			coursesUserMapper.deleteByMainId(id.toString());
			coursesCommentMapper.deleteByMainId(id.toString());
			coursesDetailMapper.deleteById(id);
		}
	}

	@Override
	public List<CoursesDetailVo> queryPayCourses(String teacherId) {
		return coursesDetailMapper.queryPayCoursesByTeacherId(teacherId);
	}

	@Override
	public List<CoursesDetailVo> queryFreeCourses(String teacherId) {
		return coursesDetailMapper.queryFreeCoursesByTeacherId(teacherId);
	}

	@Override
	public CoursesDetailIntroductionVo queryCoursesDetail(String coursesDetailId) {
		return coursesDetailMapper.queryCoursesDetail(coursesDetailId);
	}

	@Override
	public List<CoursesDetailVo> queryRecommendCourses(String coursesDetailId) {
		return coursesDetailMapper.queryRecommendCourses(coursesDetailId);
	}

	@Override
	public List<CoursesDetailVo> getUserCoursesCollect(String userId) {
		return coursesDetailMapper.getUserCoursesCollect(userId);
	}

	@Override
	public String queryCoursesCollect(String coursesDetailId, String userId) {
		return coursesDetailMapper.queryCoursesCollect(coursesDetailId, userId);
	}

	@Override
	public int addCoursesCollect(String id,String coursesDetailId, String userId) {
		return coursesDetailMapper.addCoursesCollect(id,coursesDetailId, userId);
	}

	@Override
	public int deleteCoursesCollect(String coursesDetailId, String userId) {
		return coursesDetailMapper.deleteCoursesCollect(coursesDetailId, userId);
	}

	@Override
	public int addCourse(CoursesDetailAddVo coursesDetailAddVo) {
		if (coursesDetailMapper.queryCourseByCourseName(coursesDetailAddVo.getCourseName())!=null)return -1;
		else {
			coursesLabelMapper.bindCourseCategory(UUID.randomUUID().toString(),coursesDetailAddVo.getCourseCategoryId(),coursesDetailAddVo.getCourseId());
			coursesLabelMapper.bindCourseLabel(UUID.randomUUID().toString(),coursesDetailAddVo.getCourseLabelId(),coursesDetailAddVo.getCourseId());
			return coursesDetailMapper.addCourse(coursesDetailAddVo);
		}
	}

	@Override
	public int commentCourse(CoursesComment coursesComment) {
		return coursesDetailMapper.commentCourse(coursesComment);
	}

	@Override
	public double getCourseLearningProgress(String coursesDetailId, String userId) {
		int watchedNum = coursesDetailMapper.queryWatchNum(coursesDetailId, userId);
		int videosNum = coursesDetailMapper.queryVideosNum(coursesDetailId);
		System.out.println(watchedNum);
		System.out.println(videosNum);
		return (double)watchedNum/(double)videosNum;
	}

	@Override
	public List<CourseUserVo> getUserCoursesFree(String userId) {
		List<CourseUserVo> courseUserVoList = coursesDetailMapper.getUserCoursesFree(userId);
		for (CourseUserVo courseUserVo : courseUserVoList) {
			double learningProgress = getCourseLearningProgress(courseUserVo.getCourseId(),userId);
			courseUserVo.setLearningProgress(learningProgress);
		}
		return courseUserVoList;
	}

	@Override
	public List<CourseUserVo> getUserCoursesPaid(String userId) {
		List<CourseUserVo> courseUserVoList = coursesDetailMapper.getUserCoursesPaid(userId);
		for (CourseUserVo courseUserVo : courseUserVoList) {
			double learningProgress = getCourseLearningProgress(courseUserVo.getCourseId(),userId);
			courseUserVo.setLearningProgress(learningProgress);
		}
		return courseUserVoList;
	}

	@Override
	public List<CourseUserVo> getUserCoursesRecently(String userId) {
		List<CourseUserVo> courseUserVoList = coursesDetailMapper.getUserCoursesRecently(userId);
		for (CourseUserVo courseUserVo : courseUserVoList) {
			double learningProgress = getCourseLearningProgress(courseUserVo.getCourseId(),userId);
			courseUserVo.setLearningProgress(learningProgress);
		}
		return courseUserVoList;
	}

	@Override
	public int deleteUserCoursesRecently(String userId,String[] coursesIds) {
		return coursesDetailMapper.deleteUserCoursesRecently(userId,coursesIds);
	}

	@Override
	public List<String> getCoursesCategoryId(String coursesId) {
		List<String> coursesCategoryIdByCoursesId = coursesDetailMapper.getCoursesCategoryIdByCoursesId(coursesId);
		return coursesCategoryIdByCoursesId;
	}

	@Override
	public List<CourseSlidesShow> getSlideShow() {
		return coursesDetailMapper.getSlideShow();
	}

	@Override
	public int setSlideShow(CourseSlidesShow courseSlidesShow) {
		return coursesDetailMapper.setSlideShow(courseSlidesShow);
	}

	@Override
	public int addSlideShow(CourseSlidesShow courseSlidesShow) {
		return coursesDetailMapper.addSlideShow(courseSlidesShow);
	}

	@Override
	public int deleteSlideShow(String coursesId) {
		return coursesDetailMapper.deleteSlideShow(coursesId);
	}

}
