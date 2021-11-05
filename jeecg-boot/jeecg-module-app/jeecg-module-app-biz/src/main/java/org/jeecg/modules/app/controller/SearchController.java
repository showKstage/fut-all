package org.jeecg.modules.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.app.entity.CoursesFlashSale;
import org.jeecg.modules.app.entity.CoursesSeriesFlashSale;
import org.jeecg.modules.app.service.ICoursesFlashSaleService;
import org.jeecg.modules.app.service.ICoursesSeriesFlashSaleService;
import org.jeecg.modules.app.service.ICoursesSeriesService;
import org.jeecg.modules.app.service.ISearchService;
import org.jeecg.modules.app.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description: 搜索
 * @Author: jiale.zhang
 * @Date:   2021-06-22
 * @Version: V1.0
 */

@Api(tags="用户搜索")
@RestController
@RequestMapping("/app/courses/search")
@Slf4j
public class SearchController {


    @Autowired
    private ICoursesFlashSaleService coursesFlashSaleService;
    @Autowired
    private ISearchService searchService;
    @Autowired
    private ICoursesSeriesService coursesSeriesService;
    @Autowired
    private ICoursesSeriesFlashSaleService coursesSeriesFlashSaleService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 搜索框
     *
     * @param searchKey
     * @return
     */
    @AutoLog(value = "搜索框-搜索课程、文章基本信息")
    @ApiOperation(value="搜索框-搜索课程、文章基本信息", notes="搜索框-搜索课程、文章基本信息")
    @GetMapping(value = "/query")
    public Result<?> queryCoursesAndArticles(@RequestParam(name="userId")String userId,@RequestParam(name = "searchKey",required = false) String searchKey) {
        SearchResult searchResult = new SearchResult();
        //判断缓存中是否存在该关键词缓存的值
        if(redisUtil.hasKey(searchKey)){
            searchResult = (SearchResult) redisUtil.get(searchKey);
            List<CoursesDetailVo> coursesDetailVos = searchResult.getCoursesDetailVo();
            List<CoursesArticleDetailVo> coursesArticleDetailVos = searchResult.getCoursesArticleDetailVo();
            if(coursesDetailVos.size()>0 && coursesDetailVos!=null){
                for(CoursesDetailVo coursesDetailVo : coursesDetailVos){
                    //热门搜索统计(课程标签）
                    List<String> courseLabels = searchService.queryLabelNameByCourseId(coursesDetailVo.getCourseId());
                    for(String label : courseLabels){
                        searchService.addToHot("HotSearch",label);
                    }
                    //热门搜索统计（课程类别）
                    List<String> categorys = searchService.queryCategoryNameByCourseId(coursesDetailVo.getCourseId());
                    for(String category : categorys){
                        searchService.addToHot("HotSearch",category);
                    }
                }
            }

            if(coursesArticleDetailVos.size()>0 && coursesArticleDetailVos!=null){
                for(CoursesArticleDetailVo coursesArticleDetailVo : coursesArticleDetailVos){
                    //热门搜索统计（文章类别）
                    String category = searchService.queryTypeNameByArticleId(coursesArticleDetailVo.getArticleId());
                    if(category!=null && category!=""){
                        searchService.addToHot("HotSearch",category);
                    }
                    //热门搜索统计(课程标签）
                    List<String> articleLabels = searchService.queryLabelNameByArticleId(coursesArticleDetailVo.getArticleId());
                    for(String label : articleLabels){
                        searchService.addToHot("HotSearch",label);
                    }
                }
            }
            //用户历史搜索记录
            if(!redisUtil.hasKey(userId)){
                String historyKey=userId+"key";
                //缓存用户的历史记录
                redisUtil.set(userId,historyKey);
            }
            //添加到用户历史记录
            if(!redisUtil.hasKey((String) redisUtil.get(userId))){
                searchService.addSearchHistoryByUserId(userId,searchKey);
            }else if(searchService.getSearchHistoryByUserId(userId)!=null){
                //去重
                if(!searchService.getSearchHistoryByUserId(userId).contains(searchKey)){
                    searchService.addSearchHistoryByUserId(userId,searchKey);
                }
            }

            return Result.OK(searchResult);
        }


        //通过课程名模糊查询课程基本信息
        List<CoursesDetailVo> coursesDetails;
        coursesDetails = searchService.queryCoursesVoByName("%"+searchKey+"%");

        //通过文章标题模糊查询文章
        List<CoursesArticleDetailVo> coursesArticleDetails;
        coursesArticleDetails = searchService.queryArticleVoByTitle("%"+searchKey+"%");

        if (coursesDetails==null && coursesArticleDetails==null) {
            return Result.error("暂无相关数据");
        }

        List<CoursesDetailVo> coursesDetailVos = new ArrayList<CoursesDetailVo>();
        if(coursesDetails.size()>0 && coursesDetails!=null){
            for(CoursesDetailVo coursesDetailVo : coursesDetails){
                CoursesFlashSale coursesFlashSale = coursesFlashSaleService.queryCourseSaleByCourseId(coursesDetailVo.getCourseId());
                coursesDetailVo.setCoursesFlashSale(coursesFlashSale);
                coursesDetailVos.add(coursesDetailVo);
                //热门搜索统计(课程标签）
                List<String> courseLabels = searchService.queryLabelNameByCourseId(coursesDetailVo.getCourseId());
                for(String label : courseLabels){
                    searchService.addToHot("HotSearch",label);
                }
                //热门搜索统计（课程类别）
                List<String> categorys = searchService.queryCategoryNameByCourseId(coursesDetailVo.getCourseId());
                for(String category : categorys){
                    searchService.addToHot("HotSearch",category);
                }
            }
        }

        List<CoursesArticleDetailVo> coursesArticleDetailVos = new ArrayList<CoursesArticleDetailVo>();
        if(coursesArticleDetails.size()>0 && coursesArticleDetails!=null){
            for(CoursesArticleDetailVo coursesArticleDetailVo : coursesArticleDetails){
                //点击量
                int n = 0;
                if(redisUtil.hasKey(coursesArticleDetailVo.getArticleTitle())){
                    n = (int) redisUtil.get(coursesArticleDetailVo.getArticleTitle());
                }
                coursesArticleDetailVo.setVisit(n);
                coursesArticleDetailVos.add(coursesArticleDetailVo);
                //热门搜索统计（文章类别）
                String category = searchService.queryTypeNameByArticleId(coursesArticleDetailVo.getArticleId());
                if(category!=null && category!=""){
                    searchService.addToHot("HotSearch",category);
                }
                //热门搜索统计(文章标签）
                List<String> articleLabels = searchService.queryLabelNameByArticleId(coursesArticleDetailVo.getArticleId());
                for(String label : articleLabels){
                    searchService.addToHot("HotSearch",label);
                }
            }
        }
        //用户历史搜索记录
        if(!redisUtil.hasKey(userId)){
            String historyKey=userId+"key";
            //缓存用户的历史记录
            redisUtil.set(userId,historyKey);
        }
        //添加到用户历史记录
        if(!redisUtil.hasKey((String) redisUtil.get(userId))){
            searchService.addSearchHistoryByUserId(userId,searchKey);
        }else if(searchService.getSearchHistoryByUserId(userId)!=null){
            //去重
            if(!searchService.getSearchHistoryByUserId(userId).contains(searchKey)){
                searchService.addSearchHistoryByUserId(userId,searchKey);
            }
        }
        searchResult.setCourseSum(coursesDetailVos.size());
        searchResult.setArticleSum(coursesArticleDetailVos.size());
        searchResult.setCoursesDetailVo(coursesDetailVos);
        searchResult.setCoursesArticleDetailVo(coursesArticleDetailVos);

        //对搜索内容进行缓存
        if(!redisUtil.hasKey(searchKey)){
            redisUtil.set(searchKey,searchResult);
            //过期时间为3小时
            redisUtil.expire(searchKey, 3600*3);
        }
        return Result.OK(searchResult);
    }

    /**
     * 热门搜索
     *
     * @param
     * @return result
     */
    @AutoLog(value = "用户搜索-获取热门搜索")
    @ApiOperation(value="用户搜索-获取热门搜索", notes="用户搜索-获取热门搜索")
    @GetMapping(value = "/getHotList")
    public Result<?> getHotList(){
        List<String> result = new ArrayList<>();
        //获取前十
//        TODO: 需要用rest controller来调用基础模块的功能，而不是把基础模块对应的代码移植过来处理
//        Set<Object> hotSearchs = redisUtil.zReverseRangeByScore("HotSearch",0,10);
//        for (Object hotSearch : hotSearchs){
//            result.add((String)hotSearch);
//        }
        return Result.OK(result);
    }

    /**
     * 获取历史记录
     * 
     * @param userId 用户id
     * @return
     */
    @AutoLog(value = "用户搜索-获取历史记录")
    @ApiOperation(value = "用户搜索-获取历史记录",notes = "用户搜索-获取历史记录")
    @GetMapping(value = "/getHistorySearch")
    public Result<?> getHistorySearch(@RequestParam(name = "userId")String userId){
        List<String> stringList = searchService.getSearchHistoryByUserId(userId);
        return Result.OK(stringList);
    }

    /**
     * 删除历史记录
     *
     * @param userId,searchkey
     * @return
     */
     @AutoLog(value = "用户搜索-删除历史记录")
     @ApiOperation(value = "用户搜索-删除历史记录",notes = "用户搜索-删除历史记录")
     @GetMapping(value = "/deleteHistorySearch")
     public Result<?> deleteHistorySearch(@RequestParam("userId") String userId){
         return Result.OK(searchService.delSearchHistoryByUserId(userId));
     }

    /**
     * 热门好课（Top15)
     * @return
     */
    @AutoLog(value = "热门好课-获取热门好课（Top15)")
    @ApiOperation(value = "热门好课-获取热门好课（Top15)",notes = "热门好课-获取热门好课（Top15)")
    @GetMapping(value = "/getHotCourses")
    public Result<?> getHotCoursesList(){
        List<CoursesDetailVo> coursesDetailVoList = new ArrayList<CoursesDetailVo>();
        if(redisUtil.hasKey("HotCourses")){
            coursesDetailVoList = (List<CoursesDetailVo>) redisUtil.get("HotCourses");
            return Result.OK(coursesDetailVoList);
        }
        List<CoursesDetailVo> coursesDetailVos = searchService.getHotCourses();
        for(CoursesDetailVo coursesDetailVo : coursesDetailVos){
            CoursesFlashSale coursesFlashSale = coursesFlashSaleService.queryCourseSaleByCourseId(coursesDetailVo.getCourseId());
            coursesDetailVo.setCoursesFlashSale(coursesFlashSale);
            coursesDetailVoList.add(coursesDetailVo);
        }
        redisUtil.set("HotCourses",coursesDetailVoList);
        //缓存更新为每24小时
        redisUtil.expire("HotCourses",24*3600);

        return Result.OK(coursesDetailVoList);
    }

    /**
     * 免费专区
     * @param orderBy（0-按热度，1-按时间)
     * @return
     */
    @AutoLog(value = "免费专区-查询免费课程（0-按热度，1-按时间)")
    @ApiOperation(value = "免费专区-查询免费课程（0-按热度，1-按时间)",notes = "免费专区-查询免费课程（0-按热度，1-按时间)")
    @GetMapping(value = "/getFreeCourses")
    public Result<?> getFreeCoursesByOrder(@RequestParam("orderBy") String orderBy){
        if(redisUtil.hasKey("FreeCourses")){
            List<CoursesDetailVo> coursesDetailVoList = (List<CoursesDetailVo>) redisUtil.get("FreeCourses");
            return Result.OK(coursesDetailVoList);
        }
        List<CoursesDetailVo> coursesDetailVoList = searchService.queryFreeCourses(orderBy);
        redisUtil.set("FreeCourses",coursesDetailVoList);
        redisUtil.expire("FreeCourses",24*3600);

        return Result.OK(coursesDetailVoList);
    }

    /**
     * 限时优惠
     * @param orderBy
     * @return
     */
    @AutoLog(value = "限时优惠-查询限时优惠课程（0-按热度，1-按时间)")
    @ApiOperation(value = "限时优惠-查询限时优惠课程（0-按热度，1-按时间)",notes = "限时优惠-查询限时优惠课程（0-按热度，1-按时间)")
    @GetMapping(value = "/getFlashSaleCourses")
    public Result<?> getFlashSaleCoursesByOrder(@RequestParam("orderBy") String orderBy,@RequestParam("limit")int limit){
        if(redisUtil.hasKey("FlashSaleCourses"+limit)){
            List<CoursesDetailVo> coursesDetailVoList = (List<CoursesDetailVo>) redisUtil.get("FlashSaleCourses"+limit);
            return Result.OK(coursesDetailVoList);
        }
        List<CoursesDetailVo> coursesDetailVoList = new ArrayList<>();
        List<CoursesDetailVo> coursesDetailVos = searchService.queryFlashSaleCourses(orderBy,limit);
        for(CoursesDetailVo coursesDetailVo : coursesDetailVos){
            CoursesFlashSale coursesFlashSale = coursesFlashSaleService.queryCourseSaleByCourseId(coursesDetailVo.getCourseId());
            coursesDetailVo.setCoursesFlashSale(coursesFlashSale);
            coursesDetailVoList.add(coursesDetailVo);
        }
        redisUtil.set("FlashSaleCourses"+limit,coursesDetailVoList);
        redisUtil.expire("FlashSaleCourses"+limit,24*3600);
        return Result.OK(coursesDetailVoList);
    }

    /**
     * 精品课程（Top15）
     * @return
     */
    @AutoLog(value = "精品课程-获取精品课程（Top15)")
    @ApiOperation(value = "精品课程-获取精品课程（Top15)",notes = "精品课程-获取精品课程（Top15)")
    @GetMapping(value = "/getExcellentCourses")
    public Result<?> getExcellentCourses(){
        if(redisUtil.hasKey("ExcellentCourses")){
            List<CoursesDetailVo> coursesDetailVoList = (List<CoursesDetailVo>) redisUtil.get("ExcellentCourses");
            return Result.OK(coursesDetailVoList);
        }
        List<CoursesDetailVo> coursesDetailVoList = new ArrayList<>();
        List<CoursesDetailVo> coursesDetailVos = searchService.queryExcellentCourses();
        for(CoursesDetailVo coursesDetailVo : coursesDetailVos){
            CoursesFlashSale coursesFlashSale = coursesFlashSaleService.queryCourseSaleByCourseId(coursesDetailVo.getCourseId());
            coursesDetailVo.setCoursesFlashSale(coursesFlashSale);
            coursesDetailVoList.add(coursesDetailVo);
        }
        redisUtil.set("ExcellentCourses",coursesDetailVoList);
        redisUtil.expire("ExcellentCourses",24*3600);

        return Result.OK(coursesDetailVoList);
    }

    /**
     * 系列课程
     * @return
     */
    @AutoLog(value = "系列课程-获取系列课程基本信息")
    @ApiOperation(value = "系列课程-获取系列课程基本信息",notes = "系列课程-获取系列课程基本信息")
    @GetMapping(value = "/getCoursesSeries")
    public Result<?> getCoursesSeries(){
        if(redisUtil.hasKey("seriesCourses")){
            List<CoursesSeriesVo> coursesSeriesVoList = (List<CoursesSeriesVo>) redisUtil.get("seriesCourses");
            return Result.OK(coursesSeriesVoList);
        }
        List<CoursesSeriesVo> coursesSeriesVoList = coursesSeriesService.querySeriesCourses();
        for (CoursesSeriesVo coursesSeriesVo : coursesSeriesVoList) {
            CoursesSeriesFlashSale coursesSeriesFlashSale = coursesSeriesFlashSaleService.queryCourseSaleByCourseSeriesId(coursesSeriesVo.getId());
            coursesSeriesVo.setCoursesSeriesFlashSale(coursesSeriesFlashSale);
        }
        redisUtil.set("seriesCourses",coursesSeriesVoList);
        return Result.OK(coursesSeriesVoList);

    }

    /**
     * 系列课程详情
     * @return
     */
    @AutoLog(value = "系列课程-获取系列课程详情")
    @ApiOperation(value = "系列课程-获取系列课程详情",notes = "系列课程-获取系列课程详情")
    @GetMapping(value = "/getCoursesSeriesDetail")
    public Result<?> getCoursesSeriesDetail(@RequestParam("coursesSeriesId") String coursesSeriesId){
        if(redisUtil.hasKey("coursesSeriesDetail"+coursesSeriesId)){
            CoursesSeriesDetailVo coursesSeriesDetailVo = (CoursesSeriesDetailVo) redisUtil.get("coursesSeriesDetail"+coursesSeriesId);
            return Result.OK(coursesSeriesDetailVo);
        }
        CoursesSeriesDetailVo coursesSeriesDetailVo = new CoursesSeriesDetailVo();
        List<CoursesDetailVo> coursesDetailVos = searchService.querySeriesCoursesDetail(coursesSeriesId);
        for(CoursesDetailVo coursesDetailVo : coursesDetailVos){
            CoursesFlashSale coursesFlashSale = coursesFlashSaleService.queryCourseSaleByCourseId(coursesDetailVo.getCourseId());
            coursesDetailVo.setCoursesFlashSale(coursesFlashSale);
        }
        int learningNum = coursesSeriesService.querySeriesCoursesLearningNum(coursesSeriesId);
        CoursesSeriesVo coursesSeriesVo = coursesSeriesService.querySeriesCoursesById(coursesSeriesId);
        CoursesSeriesFlashSale coursesSeriesFlashSale = coursesSeriesFlashSaleService.queryCourseSaleByCourseSeriesId(coursesSeriesVo.getId());
        coursesSeriesVo.setCoursesSeriesFlashSale(coursesSeriesFlashSale);
        coursesSeriesDetailVo.setCoursesSeriesVo(coursesSeriesVo);
        coursesSeriesDetailVo.setCoursesDetailVoList(coursesDetailVos);
        coursesSeriesDetailVo.setLearningNum(learningNum);

        redisUtil.set("coursesSeriesDetail"+coursesSeriesId,coursesSeriesDetailVo);
        redisUtil.expire("coursesSeriesDetail"+coursesSeriesId,24*3600);
        return Result.OK(coursesSeriesDetailVo);


    }

    /**
     * 根据文章类别查询
     * @return
     */
    @AutoLog(value = "文章-根据文章类别查询")
    @ApiOperation(value = "文章-根据文章类别查询",notes = "文章-根据文章类别查询")
    @GetMapping(value = "/getArticleByType")
    public Result<?> getArticleByType(@RequestParam("type") String type,@RequestParam("limit")int limit){
        if(redisUtil.hasKey(type+""+limit)){
            List<CoursesArticleDetailVo> coursesArticleDetailVoList = (List<CoursesArticleDetailVo>) redisUtil.get(type+""+limit);
            return Result.OK(coursesArticleDetailVoList);
        }
        List<CoursesArticleDetailVo> coursesArticleDetailVoList = searchService.queryArticleByType(type,limit);
        redisUtil.set(type+""+limit, coursesArticleDetailVoList);
        redisUtil.expire(type+""+limit, 24*3600);
        return Result.OK(coursesArticleDetailVoList);
    }

    /**
     * 根据用户标签推荐课程
     * @return
     */
    @AutoLog(value = "根据用户标签推荐课程")
    @ApiOperation(value = "根据用户标签推荐课程",notes = "根据用户标签推荐课程")
    @GetMapping(value = "/getRecommendCoursesDetail")
    public Result<?> getRecommendCoursesDetail(@RequestParam("userId") String userId,@RequestParam("limit")int limit) {
        if(redisUtil.hasKey(limit+"RecommendCoursesDetail"+userId)){
            List<CoursesDetailRecommendVo> coursesDetailRecommendVoList  = (List<CoursesDetailRecommendVo>) redisUtil.get(limit+"RecommendCoursesDetail"+userId);
            return Result.OK(coursesDetailRecommendVoList);
        }
        List<CoursesDetailRecommendVo> coursesDetailRecommendVoList = new ArrayList<>();
        List<CoursesDetailCategoryVo> coursesDetailCategoryVos=searchService.queryCoursesDetailRecommendCategory(userId);
        for (CoursesDetailCategoryVo coursesDetailCategoryVo : coursesDetailCategoryVos) {
            CoursesDetailRecommendVo coursesDetailRecommendVo = new CoursesDetailRecommendVo();
            coursesDetailRecommendVo.setCategory(coursesDetailCategoryVo.getCategoryName());
            List<CoursesDetailVo> coursesDetailVos = searchService.queryCoursesDetailByCategory(coursesDetailCategoryVo.getId(),limit);
            coursesDetailRecommendVo.setCoursesDetailVoList(coursesDetailVos);
            coursesDetailRecommendVoList.add(coursesDetailRecommendVo);
        }
        redisUtil.set(limit+"RecommendCoursesDetail"+userId, coursesDetailRecommendVoList);
        redisUtil.expire(limit+"RecommendCoursesDetail"+userId, 24*3600);
        return Result.OK(coursesDetailRecommendVoList);
    }

    /**
     * 展示课程分类
     * @return
     */
    @AutoLog(value = "展示课程分类")
    @ApiOperation(value = "展示课程分类",notes = "展示课程分类")
    @GetMapping(value = "/getCoursesCategory")
    public Result<?> getCoursesCategory(){
        if(redisUtil.hasKey("coursesCategoryVoList")){
            List<CoursesCategoryVo> coursesCategoryVoList  = (List<CoursesCategoryVo>) redisUtil.get("coursesCategoryVoList");
            return Result.OK(coursesCategoryVoList);
        }
        List<CoursesCategoryVo> coursesCategoryVoList = searchService.queryCoursesCategory();
        redisUtil.set("coursesCategoryVoList",coursesCategoryVoList);
        redisUtil.expire("coursesCategoryVoList", 24*3600);
        return Result.OK(coursesCategoryVoList);

    }

    /**
     * 根据课程分类获取课程
     * @return
     */
    @AutoLog(value = "根据课程分类获取课程")
    @ApiOperation(value = "根据课程分类获取课程",notes = "根据课程分类获取课程")
    @GetMapping(value = "/getCoursesDetailByCategory")
    public Result<?> getCoursesDetailByCategory(@RequestParam("categoryId") String categoryId,@RequestParam("limit")int limit){
        if(redisUtil.hasKey(limit+"CoursesDetail"+categoryId)){
            List<CoursesDetailVo> coursesDetailVoList  = (List<CoursesDetailVo>) redisUtil.get(limit+"CoursesDetail"+categoryId);
            return Result.OK(coursesDetailVoList);
        }
        List<CoursesDetailVo> coursesDetailVoList = searchService.queryCoursesDetailByCategory(categoryId,limit);
        redisUtil.set(limit+"CoursesDetail"+categoryId, coursesDetailVoList);
        redisUtil.expire(limit+"CoursesDetail"+categoryId, 24*3600);
        return Result.OK(coursesDetailVoList);
    }
}
