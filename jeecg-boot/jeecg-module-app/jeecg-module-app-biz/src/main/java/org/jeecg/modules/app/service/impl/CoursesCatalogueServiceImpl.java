package org.jeecg.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.app.entity.CoursesCatalogue;
import org.jeecg.modules.app.mapper.CoursesCatalogueMapper;
import org.jeecg.modules.app.service.ICoursesCatalogueService;
import org.jeecg.modules.app.vo.CoursesCatalogueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 课程目录表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
@Service
public class CoursesCatalogueServiceImpl extends ServiceImpl<CoursesCatalogueMapper, CoursesCatalogue> implements ICoursesCatalogueService {
	
	@Autowired
	private CoursesCatalogueMapper coursesCatalogueMapper;
	
	@Override
	public List<CoursesCatalogue> selectByMainId(String mainId) {
		return coursesCatalogueMapper.selectByMainId(mainId);
	}

	/**
	 * 获取课程目录结构
	 * @param coursesDetailId
	 * @return
	 */
	@Override
	public List<CoursesCatalogueVo> queryCoursesCatalogue(String coursesDetailId) {
		List<CoursesCatalogue> coursesCatalogueList = coursesCatalogueMapper.selectByMainId(coursesDetailId) ;
		//将子父目录分开
		List<CoursesCatalogueVo> parentCatalogues = new ArrayList<>();
		List<CoursesCatalogueVo> childrenCatalogues = new ArrayList<>();
		for (CoursesCatalogue coursesCatalogue : coursesCatalogueList) {
			if (coursesCatalogue.getParentId()==null||coursesCatalogue.getParentId()==""){
				parentCatalogues.add(new CoursesCatalogueVo().createNode(coursesCatalogue));
			}
			else{
				childrenCatalogues.add(new CoursesCatalogueVo().createNode(coursesCatalogue));
			}
		}

		//为父目录添加子目录
		for (CoursesCatalogueVo parentCatalogue : parentCatalogues) {
			List<CoursesCatalogueVo> children = getChildrenCatalogues(parentCatalogue.getId(),childrenCatalogues);
			parentCatalogue.setChildren(children);
		}

		return parentCatalogues;
	}

	@Override
	public void setCoursesVodId(String id, String vodId) {
		coursesCatalogueMapper.updateVodIdById(id, vodId);
	}

	@Override
	public void setCoursesVodIdToNull(String id) {
		coursesCatalogueMapper.updateVodIdToNullById(id);
	}

	@Override
	public CoursesCatalogue selectById(String id) {
		return coursesCatalogueMapper.selectById(id);
	}

	//获取子目录数组
	public List<CoursesCatalogueVo> getChildrenCatalogues(String parentId,List<CoursesCatalogueVo> childrenCatalogues) {
		List<CoursesCatalogueVo> children = new ArrayList<>();
		for (CoursesCatalogueVo childrenCatalogue : childrenCatalogues) {
			if (parentId.equals(childrenCatalogue.getParentId())){
				children.add(childrenCatalogue);
			}
		}
		//递归添加子目录
		for (CoursesCatalogueVo child : children) {
			List<CoursesCatalogueVo> childrenChild = getChildrenCatalogues(child.getId(),children);
			child.setChildren(childrenChild);
		}
		return children;
	}
}
