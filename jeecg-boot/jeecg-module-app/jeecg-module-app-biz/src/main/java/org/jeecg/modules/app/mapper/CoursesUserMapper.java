package org.jeecg.modules.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesUser;

import java.util.List;

/**
 * @Description: 用户课程关联表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface CoursesUserMapper extends BaseMapper<CoursesUser> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<CoursesUser> selectByMainId(@Param("mainId") String mainId);
}
