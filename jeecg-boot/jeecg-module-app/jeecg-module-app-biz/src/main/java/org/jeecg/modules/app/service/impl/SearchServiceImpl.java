package org.jeecg.modules.app.service.impl;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.app.entity.CoursesCatalogue;
import org.jeecg.modules.app.vo.CoursesCatalogueVo;
import org.jeecg.modules.app.mapper.SearchMapper;
import org.jeecg.modules.app.service.ISearchService;
import org.jeecg.modules.app.vo.CoursesArticleDetailVo;
import org.jeecg.modules.app.vo.CoursesCategoryVo;
import org.jeecg.modules.app.vo.CoursesDetailCategoryVo;
import org.jeecg.modules.app.vo.CoursesDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description: 搜索服务实现类
 * @Author: jiale.zhang
 * @Date:   2021-06-24
 * @Version: V1.0
 */
@Transactional
@Service("ISearchService")
public class SearchServiceImpl implements ISearchService {

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private SearchMapper searchMapper;

    @Override
    public List<CoursesDetailVo> queryCoursesVoByName(String coursesDetailName){
        return searchMapper.queryCoursesVoByName(coursesDetailName);
    }


    @Override
    public List<String> queryLabelNameByCourseId(String courseId){
        return searchMapper.queryLabelNameByCourseId(courseId);
    }

    @Override
    public List<String> queryCategoryNameByCourseId(String courseId){
        return searchMapper.queryCategoryNameByCourseId(courseId);
    }

    @Override
    public List<String> queryLabelNameByArticleId(String articleId){
        return searchMapper.queryLabelNameByArticleId(articleId);
    }

    @Override
    public String queryTypeNameByArticleId(String articleId){
        return searchMapper.queryTypeNameByArticleId(articleId);
    }


    @Override
    public List<CoursesArticleDetailVo> queryArticleVoByTitle(String articleTitle){
        return searchMapper.queryArticleVoByTitle(articleTitle);
    }

    @Override
    public List<CoursesDetailVo> getHotCourses(){
        return searchMapper.getHotCourses();
    }

    @Override
    public List<CoursesDetailVo> queryFreeCourses(String orderBy){
        return searchMapper.queryFreeCourses(orderBy);
    }

    @Override
    public List<CoursesDetailVo> queryFlashSaleCourses(String orderBy,int limit){
        return searchMapper.queryFlashSaleCourses(orderBy,limit);
    }

    @Override
    public List<CoursesDetailVo> queryExcellentCourses(){
        return searchMapper.queryExcellentCourses();
    }

    @Override
    public List<CoursesDetailVo> querySeriesCoursesDetail(String coursesSeriesId) {
        return searchMapper.querySeriesCoursesDetail(coursesSeriesId);
    }


    @Override
    public List<CoursesArticleDetailVo> queryArticleByType(String type,int limit){
        return searchMapper.queryArticleByType(type,limit);
    }

    @Override
    public int addSearchHistoryByUserId(String userId, String searchkey) {
        String shistory = (String) redisUtil.get(userId);  //获取用户的历史记录
        boolean b = redisUtil.hasKey(shistory);
        if (b) {
            Object hk = redisUtil.hget(shistory, searchkey);
            if (hk != null) {
                return 1;
            }else{
                redisUtil.hset(shistory, searchkey, "1");
            }
        }else{
            redisUtil.hset(shistory, searchkey, "1");
        }
        return 1;
    }


    @Override
    public Long delSearchHistoryByUserId(String userId) {
        String shistory =  (String) redisUtil.get(userId);//获取用户的历史记录
        List<String> stringList = new ArrayList<String>();
        boolean b = redisUtil.hasKey(shistory);
//        TODO: 需要用rest controller来调用基础模块的功能，而不是把基础模块对应的代码移植过来处理
//        if (b){
//            Cursor<Map.Entry<Object, Object>> cursor = redisUtil.hScan(shistory, ScanOptions.NONE);
//            while (cursor.hasNext()) {  //遍历
//                Map.Entry<Object, Object> map = cursor.next();
//                String key = map.getKey().toString();
//                stringList.add(key);
//            }
//            return redisUtil.hdel(shistory,stringList);
//        }
        return null;
    }


    @Override
    public List<String> getSearchHistoryByUserId(String userId) {
        List<String> stringList = new ArrayList<String>();
        String shistory = (String) redisUtil.get(userId);  //获取用户的历史记录
        boolean b = redisUtil.hasKey(shistory);
        //        TODO: 需要用rest controller来调用基础模块的功能，而不是把基础模块对应的代码移植过来处理
//        if(b){
//            Cursor<Map.Entry<Object, Object>> cursor = redisUtil.hScan(shistory, ScanOptions.NONE);
//            while (cursor.hasNext()) {  //遍历
//                Map.Entry<Object, Object> map = cursor.next();
//                String key = map.getKey().toString();
//                stringList.add(key);
//            }
//            return stringList;
//        }
        return null;
    }

    /**
     * 热搜榜
     * @param key
     * @param value
     */
    @Override
    public void addToHot(String key, String value){
        //        TODO: 需要用rest controller来调用基础模块的功能，而不是把基础模块对应的代码移植过来处理
//        if(!redisUtil.hasKey(key)){
//            redisUtil.zAdd(key,value,0);
//            //热搜榜更新周期为每三天
//            redisUtil.expire(key,3600*24*3);
//        }
//        redisUtil.zIncrementScore(key,value,1);
    }

    @Override
    public List<CoursesDetailCategoryVo> queryCoursesDetailRecommendCategory(String userId) {
        return searchMapper.queryCoursesDetailRecommendCategory(userId);
    }

    @Override
    public List<CoursesDetailVo> queryCoursesDetailByCategory(String categoryId,int limit) {
        return searchMapper.queryCoursesDetailByCategory(categoryId,limit);
    }

    @Override
    public List<CoursesCategoryVo> queryCoursesCategory() {
        List<CoursesCategoryVo> coursesDetailCategoryVoList = searchMapper.queryCoursesCategory();

        //将子父分类分开
        List<CoursesCategoryVo> parentCategory = new ArrayList<>();
        List<CoursesCategoryVo> childrenCategory = new ArrayList<>();
        for (CoursesCategoryVo coursesCategoryVo : coursesDetailCategoryVoList) {
            if (coursesCategoryVo.getParentId()==null||coursesCategoryVo.getParentId()==""){
                parentCategory.add(coursesCategoryVo);
            }
            else{
                childrenCategory.add(coursesCategoryVo);
            }
        }

        //为父分类添加子分类
        for (CoursesCategoryVo parentCategoryVo : parentCategory) {
            List<CoursesCategoryVo> children = getChildrenCategory(parentCategoryVo.getId(),childrenCategory);
            parentCategoryVo.setChildren(children);
        }

        return parentCategory;
    }

    //获取子分类数组
    public List<CoursesCategoryVo> getChildrenCategory(String parentId,List<CoursesCategoryVo> childrenCategory) {
        List<CoursesCategoryVo> children = new ArrayList<>();
        for (CoursesCategoryVo childrenCategoryVo : childrenCategory) {
            if (parentId.equals(childrenCategoryVo.getParentId())){
                children.add(childrenCategoryVo);
            }
        }
        //递归添加子分类
        for (CoursesCategoryVo child : children) {
            List<CoursesCategoryVo> childrenChild = getChildrenCategory(child.getId(),children);
            child.setChildren(childrenChild);
        }
        return children;
    }
 }

/**
 *
 * TODO:
 *
    @Override
    public List<String> getHotList(String key, String searchkey) {

        Long now = System.currentTimeMillis();
        List<String> result = new ArrayList<>();
        Set<Object> value = redisUtil.zReverseRangeByScore(key, 0, Double.MAX_VALUE);
        //key不为空的时候 推荐相关的最热前十名
        if(StringUtils.isNotEmpty(searchkey)){
            for (Object val : value) {
                if (StringUtils.containsIgnoreCase(String.valueOf(val), searchkey)) {
                    if (result.size() > 9) {//只返回最热的前十名
                        break;
                    }
                    Long time = Long.valueOf((Long) redisUtil.get(String.valueOf(val)));
                    if ((now - time) < 2592000000L) {//返回最近一个月的数据
                        result.add(String.valueOf(val));
                    } else {//时间超过一个月没搜索就把这个词热度归0
                        redisUtil.zAdd(key, String.valueOf(val), 0);
                    }
                }
            }
        }else{
            for (Object val : value) {
                if (result.size() > 9) {//只返回最热的前十名
                    break;
                }
                Long time = Long.valueOf((Long) redisUtil.get(String.valueOf(val)));
                if ((now - time) < 2592000000L) {//返回最近一个月的数据
                    result.add(String.valueOf(val));
                } else {//时间超过一个月没搜索就把这个词热度归0
                    redisUtil.zAdd(key, String.valueOf(val), 0);
                }
            }
        }
        return result;
    }

    @Override
    public void incrementScore(String key, String searchkey) {
        Long now = System.currentTimeMillis();
        redisUtil.zIncrementScore(key, searchkey, 1);
        redisUtil.set(searchkey, String.valueOf(now));
    }
*/





 
 