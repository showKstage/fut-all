package org.jeecg.modules.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesTeacher;

import java.util.List;

/**
 * @Description: 教师信息表
 * @Author: jeecg-boot
 * @Date:   2021-06-27
 * @Version: V1.0
 */
public interface CoursesTeacherMapper extends BaseMapper<CoursesTeacher> {

    CoursesTeacher queryTeacherById(@Param("teacherId") String teacherId);

    void addTeacherFans(@Param("teacherId") String teacherId,@Param("userId") String userId);

    void deleteTeacherFans(@Param("teacherId") String teacherId,@Param("userId") String userId);

    List<CoursesTeacher> queryUserFollowTeacherInfo(@Param("userId") String userId);

    int queryTeacherFansNum(@Param("teacherId") String teacherId);

    int queryUserFollowNum(@Param("userId") String userId);

    int queryIsFollowByUserIdAndTeacherId(@Param("userId") String userId,
                                          @Param("teacherId") String teacherId);

}
