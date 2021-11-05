package org.jeecg.modules.app.service;


import org.jeecg.modules.app.vo.CoursesArticleDetailVo;
import org.jeecg.modules.app.vo.CoursesCategoryVo;
import org.jeecg.modules.app.vo.CoursesDetailCategoryVo;
import org.jeecg.modules.app.vo.CoursesDetailVo;

import java.util.List;

/**
 * @Description: 搜索服务接口
 * @Author: jiale.zhang
 * @Date:   2021-06-24
 * @Version: V1.0
 */
public interface ISearchService {

    /**搜索课程基本信息（根据课程名）
     *
     * @param coursesDetailName
     * @return
     */
    public List<CoursesDetailVo> queryCoursesVoByName(String coursesDetailName);



    /**
     * 搜索文章基本信息
     * @param articleTitle
     * @return
     */
    public List<CoursesArticleDetailVo> queryArticleVoByTitle(String articleTitle);


    public List<String> queryLabelNameByCourseId(String courseId);
    public List<String> queryCategoryNameByCourseId(String courseId);
    public List<String> queryLabelNameByArticleId(String articleId);
    public String queryTypeNameByArticleId(String articleId);



    /**获取热门好课（Top15）*/
    public List<CoursesDetailVo> getHotCourses();
    /**
     * 查询免费课程
     * @param orderBy (0-按热度排序，1-按时间排序）
     * @return
     */
    public List<CoursesDetailVo> queryFreeCourses(String orderBy);
    /**
     * 查询限时优惠课程
     * @param orderBy (0-按热度排序，1-按时间排序）
     * @return
     */
    public List<CoursesDetailVo> queryFlashSaleCourses(String orderBy,int limit);
    /**获取精品课程*/
    public List<CoursesDetailVo> queryExcellentCourses();

    /**获取系列课程详情*/
    public List<CoursesDetailVo> querySeriesCoursesDetail(String coursesSeriesId);

    /**根据分类查询文章*/
    public List<CoursesArticleDetailVo> queryArticleByType(String type,int limit);

    /**新增一条该userid用户在搜索栏的历史记录
     * searchkey 代表输入的关键词
     * @param userid
     * @param searchkey
     * @return
     */
    public int addSearchHistoryByUserId(String userid, String searchkey);

    /**删除个人历史数据
     *
     * @param userId
     * @return
     */
    public Long delSearchHistoryByUserId(String userId);

    /**获取个人历史数据列表
     *
     * @param userId
     * @return
     */
    public List<String> getSearchHistoryByUserId(String userId);


    //TODO:
    /**
     根据searchkey搜索其相关最热的前十名 (如果searchkey为null空，则返回redis存储的前十最热词条)
     */
    //public List<String> getHotList(String key, String searchkey);

    /**每次点击给相关词searchkey热度 +1
     *
     * @param key
     * @param searchkey
     */
    //public void incrementScore(String key, String searchkey);


    /**
     * 热搜榜
     * @param key
     * @param value
     */
    public void addToHot(String key, String value);


    /**
     * 获取推荐课程分类
     *
     */
    public List<CoursesDetailCategoryVo> queryCoursesDetailRecommendCategory(String userId);

    /**
     * 根据分类查询课程
     */
    public List<CoursesDetailVo> queryCoursesDetailByCategory(String categoryId,int limit);

    /**
     * 获取所有课程分类
     *
     */
    public List<CoursesCategoryVo> queryCoursesCategory();
}
