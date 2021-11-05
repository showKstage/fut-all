package org.jeecg.modules.app.service;

import org.jeecg.modules.app.entity.CoursesDetail;
import org.jeecg.modules.app.entity.CoursesSeries;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.vo.CoursesSeriesVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 系列课程表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface ICoursesSeriesService extends IService<CoursesSeries> {

	/**系列课程查询*/
	public List<CoursesSeriesVo> querySeriesCourses();

	/**系列课程学习人数统计*/
	public int querySeriesCoursesLearningNum(String coursesSeriesId);

	/**通过id查询系列课程基本信息*/
	public CoursesSeriesVo querySeriesCoursesById(String coursesSeriesId);
	/**
	 * 添加一对多
	 *
	 */
	public void saveMain(CoursesSeries coursesSeries, List<CoursesDetail> coursesDetailList) ;

	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(CoursesSeries coursesSeries, List<CoursesDetail> coursesDetailList);

	/**
	 * 删除一对多
	 */
	public void delMain(String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
