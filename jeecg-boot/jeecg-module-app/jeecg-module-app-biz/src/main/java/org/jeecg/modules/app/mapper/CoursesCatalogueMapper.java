package org.jeecg.modules.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesCatalogue;

import java.util.List;

/**
 * @Description: 课程目录表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface CoursesCatalogueMapper extends BaseMapper<CoursesCatalogue> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	void updateVodIdById(@Param("id") String id,@Param("vodId") String vodId);

	void updateVodIdToNullById(@Param("id") String id);

	public List<CoursesCatalogue> selectByMainId(@Param("mainId") String mainId);
}
