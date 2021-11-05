package org.jeecg.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.app.entity.CoursesTeacher;
import org.jeecg.modules.app.mapper.CoursesTeacherMapper;
import org.jeecg.modules.app.service.ICoursesTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Description: 教师信息表
 * @Author: jeecg-boot
 * @Date:   2021-06-27
 * @Version: V1.0
 */
@Service
public class CoursesTeacherServiceImpl extends ServiceImpl<CoursesTeacherMapper, CoursesTeacher> implements ICoursesTeacherService {

    @Autowired
    CoursesTeacherMapper coursesTeacherMapper;

    @Autowired
    RedisUtil redisUtil;

    /** 关注用户 */
    private static final String FOLLOWING = "FOLLOWING_";
    /** 粉丝用户 */
    private static final String FANS = "FANS_";
    /** 共同关注 暂不需实现 */
    private static final String COMMON_KEY = "COMMON_FOLLOWING";

    @Override
    public CoursesTeacher getTeacherById(String teacherId) {
        return coursesTeacherMapper.queryTeacherById(teacherId);
    }

    /**
     *
     * @param userId
     * @param followingId
     * @param isFollow  0 = 为关注  1 = 已关注
     * @return
     */
    @Override
    public int addOrRelease(String userId, String followingId,int isFollow) {
        if (userId == null || followingId == null) {
            return -1;
        }
        String followingKey = FOLLOWING + userId;
        String fansKey = FANS + followingId;
        if (isFollow == 0) {
            coursesTeacherMapper.addTeacherFans(followingId, userId);
            redisUtil.sSet(followingKey, followingId);
            redisUtil.sSet(fansKey, userId);
            //保证并发下最终一致性
            redisUtil.expire(followingKey, 7200);
            redisUtil.expire(fansKey, 7200);
            isFollow = 1;
        }else {
            coursesTeacherMapper.deleteTeacherFans(followingId, userId);
            redisUtil.setRemove(followingKey, followingId);
            redisUtil.setRemove(fansKey, userId);
            isFollow = 0;
        }
        return isFollow;
    }

    /**
     * 获取用户关注数
     * @param userId 用户id
     * @return
     */
    @Override
    public long getFollowNum(String userId) {
        return redisUtil.sGetSetSize(FOLLOWING + userId);
    }

    /**
     * 获取教师粉丝数
     * @param followingId 教师id
     * @return
     */
    @Override
    public long getFansNum(String followingId) {
        long fans = 0;
        fans = redisUtil.sGetSetSize(FANS + followingId);
        return fans;
    }

    /**
     * 获取教师粉丝id
     * @param followingId
     * @return
     */
    @Override
    public Set<?> getFansId(String followingId) {
        Set<?> fans = redisUtil.sGet(FANS + followingId);
        return fans;
    }

    /**
     * 获取用户关注教师id
     * @param userId
     * @return
     */
    @Override
    public Set<?> getFollowId(String userId) {
        Set<?> fans = redisUtil.sGet(FOLLOWING + userId);
        return fans;
    }

    /**
     * 查询用户对教师是否关注
     * @param userId
     * @param teacherId
     * @return 返回值 0-未关注  1-已关注
     */
    @Override
    public int queryIsFollow(String userId, String teacherId) {
        int i = coursesTeacherMapper.queryIsFollowByUserIdAndTeacherId(userId, teacherId);
        return i > 0 ? 1 : 0;
    }

}
