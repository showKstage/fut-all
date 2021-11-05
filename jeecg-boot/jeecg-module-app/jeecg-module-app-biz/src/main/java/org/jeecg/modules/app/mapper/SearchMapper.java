package org.jeecg.modules.app.mapper;


import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.vo.CoursesArticleDetailVo;
import org.jeecg.modules.app.vo.CoursesCategoryVo;
import org.jeecg.modules.app.vo.CoursesDetailCategoryVo;
import org.jeecg.modules.app.vo.CoursesDetailVo;

import java.util.List;

/**
 * @Description: 搜索映射接口
 * @Author: jiale.zhang
 * @Date:   2021-06-24
 * @Version: V1.0
 */
public interface SearchMapper {
    /**
     * 搜索课程基本信息（根据课程名）
     * @param coursesDetailName
     * @return
     */
    public List<CoursesDetailVo> queryCoursesVoByName(@Param("coursesDetailName") String coursesDetailName);


    /**
     * 搜索文章基本信息
     * @param articleTitle
     * @return
     */
    public List<CoursesArticleDetailVo> queryArticleVoByTitle(@Param("articleTitle") String articleTitle);



    public List<String> queryLabelNameByCourseId(@Param("courseId") String courseId);
    public List<String> queryCategoryNameByCourseId(@Param("courseId") String courseId);
    public List<String> queryLabelNameByArticleId(@Param("articleId") String articleId);
    public String queryTypeNameByArticleId(@Param("articleId") String articleId);

    /**获取热门好课（Top15）*/
    public List<CoursesDetailVo> getHotCourses();

    /**
     * 查询免费课程
     * @param orderBy (0-按热度排序，1-按时间排序）
     * @return
     */
    public List<CoursesDetailVo> queryFreeCourses(@Param("orderBy") String orderBy);

    /**
     * 查询限时优惠课程
     * @param orderBy (0-按热度排序，1-按时间排序）
     * @return
     */
    public List<CoursesDetailVo> queryFlashSaleCourses(@Param("orderBy") String orderBy,@Param("limit") int limit);

    /**获取精品课程*/
    public List<CoursesDetailVo> queryExcellentCourses();



    /**根据分类查询文章*/
    public List<CoursesArticleDetailVo> queryArticleByType(@Param("type") String type,@Param("limit") int limit);


    /**获取系列课程详情*/
    public List<CoursesDetailVo> querySeriesCoursesDetail(@Param("coursesSeriesId") String coursesSeriesId);

    /**获取推荐课程分类*/
    public List<CoursesDetailCategoryVo> queryCoursesDetailRecommendCategory(@Param("userId") String userId);

    /**根据分类查询课程*/
    public List<CoursesDetailVo> queryCoursesDetailByCategory(@Param("categoryId") String categoryId,@Param("limit") int limit);

    /**获取所有课程分类*/
    public List<CoursesCategoryVo> queryCoursesCategory();

}
