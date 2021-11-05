package org.jeecg.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.entity.CoursesCatalogue;
import org.jeecg.modules.app.vo.CoursesCatalogueVo;

import java.util.List;

/**
 * @Description: 课程目录表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface ICoursesCatalogueService extends IService<CoursesCatalogue> {

	public List<CoursesCatalogue> selectByMainId(String mainId);

	public List<CoursesCatalogueVo> queryCoursesCatalogue(String coursesDetailId);

	void setCoursesVodId(String id, String vodId);

	void setCoursesVodIdToNull(String id);

	CoursesCatalogue selectById(String id);
}
