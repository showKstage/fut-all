package org.jeecg.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.entity.CoursesUser;

import java.util.List;

/**
 * @Description: 用户课程关联表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface ICoursesUserService extends IService<CoursesUser> {

	public List<CoursesUser> selectByMainId(String mainId);

}
