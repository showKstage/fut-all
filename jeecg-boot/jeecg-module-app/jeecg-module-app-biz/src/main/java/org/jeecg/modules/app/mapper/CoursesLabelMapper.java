package org.jeecg.modules.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesLabelUser;
import org.jeecg.modules.app.vo.CourseLabelVo;

import java.util.List;

/**
 * @Description: 标签表
 * @Author: jeecg-boot
 * @Date:   2021-06-13
 * @Version: V1.0
 */
public interface CoursesLabelMapper extends BaseMapper<CoursesLabelUser> {

    List<String> queryCoursesCategory(@Param("categoryName") String categoryName);

    String queryLabel(@Param("categoryName") String categoryName);

    int addLabel(@Param("categoryId") String categoryId, @Param("userId") String userId);

    /**删除用户已有标签*/
    int deleteUerCourseLabel(@Param("userId")String userId);

    /**绑定课程标签*/
    int bindCourseLabel(@Param("id")String id,@Param("labelId")String labelId,@Param("courseId")String courseId);

    /**绑定课程分类*/
    int bindCourseCategory(@Param("id")String id,@Param("categoryId")String categoryId,@Param("courseId")String courseId);

    /**获取课程标签*/
    List<CourseLabelVo> getAllCourseLabel();

    /**添加课程标签*/
    int addCourseLabel(@Param("id")String id,@Param("courseLabelName")String courseLabelName);

    /**添加课程标签*/
    String queryCourseLabel(@Param("courseLabelName")String courseLabelName);

    /**查询用户已有标签*/
    String queryUserLabel(@Param("categoryId") String categoryId, @Param("userId") String userId);


}
