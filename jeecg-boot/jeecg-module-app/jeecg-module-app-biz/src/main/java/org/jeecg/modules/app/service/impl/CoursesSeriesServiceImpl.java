package org.jeecg.modules.app.service.impl;

import org.jeecg.modules.app.entity.CoursesSeries;
import org.jeecg.modules.app.entity.CoursesDetail;
import org.jeecg.modules.app.mapper.CoursesDetailMapper;
import org.jeecg.modules.app.mapper.CoursesSeriesMapper;
import org.jeecg.modules.app.service.ICoursesSeriesService;
import org.jeecg.modules.app.vo.CoursesSeriesVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 系列课程表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Service
public class CoursesSeriesServiceImpl extends ServiceImpl<CoursesSeriesMapper, CoursesSeries> implements ICoursesSeriesService {

	@Autowired
	private CoursesSeriesMapper coursesSeriesMapper;
	@Autowired
	private CoursesDetailMapper coursesDetailMapper;

	/**系列课程查询*/
	@Override
	public List<CoursesSeriesVo> querySeriesCourses(){
		return coursesSeriesMapper.querySeriesCourses();
	}

	/**系列课程学习人数统计*/
	@Override
	public int querySeriesCoursesLearningNum(String coursesSeriesId) {
		return coursesSeriesMapper.querySeriesCoursesLearningNum(coursesSeriesId);
	}

	/**通过id查询系列课程基本信息*/
	@Override
	public CoursesSeriesVo querySeriesCoursesById(String coursesSeriesId) {
		return coursesSeriesMapper.querySeriesCoursesById(coursesSeriesId);
	}


	@Override
	@Transactional
	public void saveMain(CoursesSeries coursesSeries, List<CoursesDetail> coursesDetailList) {
		coursesSeriesMapper.insert(coursesSeries);
		if(coursesDetailList!=null && coursesDetailList.size()>0) {
			for(CoursesDetail entity:coursesDetailList) {
				//外键设置
				entity.setCoursesSeriesId(coursesSeries.getId());
				coursesDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(CoursesSeries coursesSeries,List<CoursesDetail> coursesDetailList) {
		coursesSeriesMapper.updateById(coursesSeries);

		//1.先删除子表数据
		coursesDetailMapper.deleteByMainId(coursesSeries.getId());

		//2.子表数据重新插入
		if(coursesDetailList!=null && coursesDetailList.size()>0) {
			for(CoursesDetail entity:coursesDetailList) {
				//外键设置
				entity.setCoursesSeriesId(coursesSeries.getId());
				coursesDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		coursesDetailMapper.deleteByMainId(id);
		coursesSeriesMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			coursesDetailMapper.deleteByMainId(id.toString());
			coursesSeriesMapper.deleteById(id);
		}
	}
	
}
