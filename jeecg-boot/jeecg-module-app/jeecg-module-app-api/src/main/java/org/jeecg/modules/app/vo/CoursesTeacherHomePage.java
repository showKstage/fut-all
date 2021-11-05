package org.jeecg.modules.app.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jeecg.modules.app.entity.CoursesArticleDetail;
import org.jeecg.modules.app.entity.CoursesTeacher;

import java.io.Serializable;
import java.util.List;

/**
 * @author hongwei.lin
 * @date 2021-07-01
 */
@Data
@AllArgsConstructor
public class CoursesTeacherHomePage implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 教师个人信息 */
    private CoursesTeacher coursesTeacher;
    /** 用户是否关注教师 0-未关注 1-已关注 */
    private int isFollow;
    /** 付费课程列表 */
    private List<CoursesDetailVo> payCoursesDetails;
    /** 免费课程列表 */
    private List<CoursesDetailVo> freeCoursesDetails;
    /** 文章列表 */
    private  List<CoursesArticleDetail>  coursesArticleDetails;
}
