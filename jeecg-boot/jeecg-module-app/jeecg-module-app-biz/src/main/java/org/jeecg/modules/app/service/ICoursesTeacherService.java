package org.jeecg.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.entity.CoursesTeacher;

import java.util.Set;

/**
 * @Description: 教师信息表
 * @Author: jeecg-boot
 * @Date:   2021-06-27
 * @Version: V1.0
 */
public interface ICoursesTeacherService extends IService<CoursesTeacher> {

    CoursesTeacher getTeacherById(String teacherId);

    int addOrRelease(String userId, String followingId,int isFollow);

    long getFollowNum(String userId);

    long getFansNum(String followingId);

    Set<?> getFansId(String followingId);

    Set<?> getFollowId(String userId);

    int queryIsFollow(String userId, String teacherId);
}
