package org.jeecg.modules.app.controller;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.app.entity.*;
import org.jeecg.modules.app.service.*;
import org.jeecg.modules.app.vo.*;
import org.jeecg.modules.app.vo.CoursesDetailVo;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
* @Description: 课程详情表
* @Author: jeecg-boot
* @Date:   2021-06-13
* @Version: V1.0
*/
@Api(tags="课程详情表")
@RestController
@RequestMapping("/app/courses/coursesDetail")
@Slf4j
public class CoursesDetailController {
   @Autowired
   private ICoursesDetailService coursesDetailService;
   @Autowired
   private ICoursesCatalogueService coursesCatalogueService;
   @Autowired
   private ICoursesFlashSaleService coursesFlashSaleService;
   @Autowired
   private ICoursesUserService coursesUserService;
   @Autowired
   private ICoursesCommentService coursesCommentService;

   @Autowired
   private RedisUtil redisUtil;

   /**
    *  编辑
    *
    * @param coursesDetailPage
    * @return
    */
   @AutoLog(value = "课程详情表-编辑")
   @ApiOperation(value="课程详情表-编辑", notes="课程详情表-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody CoursesDetailPage coursesDetailPage) {
       CoursesDetail coursesDetail = new CoursesDetail();
       BeanUtils.copyProperties(coursesDetailPage, coursesDetail);
       CoursesDetail coursesDetailEntity = coursesDetailService.getById(coursesDetail.getId());
       if(coursesDetailEntity==null) {
           return Result.error("未找到对应数据");
       }
       coursesDetailService.updateMain(coursesDetail, coursesDetailPage.getCoursesCatalogueList(),coursesDetailPage.getCoursesFlashSaleList(),coursesDetailPage.getCoursesUserList(),coursesDetailPage.getCoursesCommentList());
       return Result.OK("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "课程详情表-通过id删除")
   @ApiOperation(value="课程详情表-通过id删除", notes="课程详情表-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       coursesDetailService.delMain(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "课程详情表-批量删除")
   @ApiOperation(value="课程详情表-批量删除", notes="课程详情表-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.coursesDetailService.delBatchMain(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功！");
   }


    @AutoLog(value = "课程详情-课程介绍")
    @ApiOperation(value="课程详情-课程介绍", notes="课程详情-课程介绍")
    @GetMapping(value = "/queryCoursesDetail")
    public Result<?> queryCoursesDetail(@RequestParam(name="coursesDetailId",required=true) String coursesDetailId) {
        CoursesDetailIntroductionVo coursesDetailVo = coursesDetailService.queryCoursesDetail(coursesDetailId);
    if(coursesDetailVo != null) {
        CoursesFlashSale coursesFlashSale = coursesFlashSaleService.queryCourseSaleByCourseId(coursesDetailId);
        coursesDetailVo.setCoursesFlashSale(coursesFlashSale);
        return Result.OK(coursesDetailVo);
    }
    return Result.error("输入课程id错误");
    }

    @AutoLog(value = "课程详情-评价")
    @ApiOperation(value="课程详情-评价", notes="课程详情-评价")
    @GetMapping(value = "/queryCoursesComment")
        public Result<?> queryCoursesComment(@RequestParam(name="coursesDetailId") String coursesDetailId,
                                             @RequestParam(name="limit",required = false) Integer limit) {
        CoursesCommentVo coursesCommentVo = new CoursesCommentVo();
        List<CoursesCommentDetailVo> coursesCommentDetailVoList = null;
        if ("".equals(limit + "")) {
            coursesCommentDetailVoList = coursesCommentService.queryCoursesCommentList(coursesDetailId,0);
        }else {
            coursesCommentDetailVoList = coursesCommentService.queryCoursesCommentList(coursesDetailId, limit);
        }
        Double average = 0.0;
        Map<String,Integer> starMap = new HashMap<>();
        int one=0,two=0,three=0,four=0,five=0;
        List<CoursesCommentDetailVo> coursesSumCommentDetailVoList = coursesCommentService.queryCoursesCommentList(coursesDetailId,0);
        int commentNum = coursesSumCommentDetailVoList.size();
        for (CoursesCommentDetailVo coursesCommentDetailVo : coursesSumCommentDetailVoList) {
            if (coursesCommentDetailVo.getCommentLevel()==1)one++;
            if (coursesCommentDetailVo.getCommentLevel()==2)two++;
            if (coursesCommentDetailVo.getCommentLevel()==3)three++;
            if (coursesCommentDetailVo.getCommentLevel()==4)four++;
            if (coursesCommentDetailVo.getCommentLevel()==5)five++;
            average=average+coursesCommentDetailVo.getCommentLevel();
        }
        starMap.put("1",one);starMap.put("2",two);starMap.put("3",three);starMap.put("4",four);starMap.put("5",five);
        average=average/commentNum;
        coursesCommentVo.setCoursesCommentDetailVoList(coursesCommentDetailVoList);
        coursesCommentVo.setCommentNum(commentNum);
        coursesCommentVo.setCommentAverage(average);
        coursesCommentVo.setStarMap(starMap);
        return Result.OK(coursesCommentVo);
    }

    @AutoLog(value = "课程详情-推荐")
    @ApiOperation(value = "课程详情-推荐",notes = "课程详情-推荐")
    @GetMapping(value = "/queryRecommendCourses")
    public Result<?> queryRecommendCourses(@RequestParam("coursesDetailId") String coursesDetailId){
        List<CoursesDetailVo> coursesDetailVoList = coursesDetailService.queryRecommendCourses(coursesDetailId);
        for(CoursesDetailVo coursesDetailVo : coursesDetailVoList){
            CoursesFlashSale coursesFlashSale = coursesFlashSaleService.queryCourseSaleByCourseId(coursesDetailVo.getCourseId());
            coursesDetailVo.setCoursesFlashSale(coursesFlashSale);
        }
        return Result.OK(coursesDetailVoList);
    }

    @AutoLog(value = "课程详情-目录")
    @ApiOperation(value = "课程详情-目录",notes = "课程详情-目录")
    @GetMapping(value = "/queryCoursesCatalogue")
    public Result<?> queryCoursesCatalogue(@RequestParam("coursesDetailId") String coursesDetailId){
       List<CoursesCatalogueVo> coursesCatalogueVoList = coursesCatalogueService.queryCoursesCatalogue(coursesDetailId);
        return Result.OK(coursesCatalogueVoList);
    }

    @AutoLog(value = "课程详情-收藏")
    @ApiOperation(value = "课程详情-收藏",notes = "课程详情-收藏")
    @GetMapping(value = "/addCoursesCollect")
    public Result<?> addCoursesCollect(@RequestParam("coursesDetailId") String coursesDetailId,@RequestParam("userId") String userId){
       String id = UUID.randomUUID().toString();
       if (coursesDetailService.addCoursesCollect(id,coursesDetailId,userId)>0)return Result.OK("收藏成功！");
       else return Result.OK("收藏失败！");
    }

    @AutoLog(value = "我的学习-我的收藏")
    @ApiOperation(value = "我的学习-我的收藏",notes = "我的学习-我的收藏")
    @GetMapping(value = "/getUserCoursesCollect")
    public Result<?> getUserCoursesCollect(@RequestParam("userId") String userId){
        List<CoursesDetailVo> coursesDetailVoList = coursesDetailService.getUserCoursesCollect(userId);
        for(CoursesDetailVo coursesDetailVo : coursesDetailVoList){
            CoursesFlashSale coursesFlashSale = coursesFlashSaleService.queryCourseSaleByCourseId(coursesDetailVo.getCourseId());
            coursesDetailVo.setCoursesFlashSale(coursesFlashSale);
        }
        return Result.OK(coursesDetailVoList);
    }

    @AutoLog(value = "课程详情-取消收藏")
    @ApiOperation(value = "课程详情-取消收藏",notes = "课程详情-取消收藏")
    @GetMapping(value = "/deleteCoursesCollect")
    public Result<?> deleteCoursesCollect(@RequestParam("coursesDetailId") String coursesDetailId,@RequestParam("userId") String userId){
        if (coursesDetailService.deleteCoursesCollect(coursesDetailId,userId)>0)return Result.OK("取消收藏成功！");
        else return Result.OK("取消收藏失败！");
    }

    @AutoLog(value = "课程详情-获取收藏信息")
    @ApiOperation(value = "课程详情-获取收藏信息",notes = "课程详情-获取收藏信息")
    @GetMapping(value = "/queryCoursesCollect")
    public Result<?> queryCoursesCollect(@RequestParam("coursesDetailId") String coursesDetailId,@RequestParam("userId") String userId){
        if (coursesDetailService.queryCoursesCollect(coursesDetailId,userId)!=null)return Result.OK("已收藏！");
        else return Result.OK("未收藏！");
    }

    @AutoLog(value = "添加课程")
    @ApiOperation(value = "添加课程",notes = "添加课程")
    @PostMapping(value = "/addCourse")
    public Result<?> addCourse(@RequestBody CoursesDetailAddVo coursesDetailAddVo){
       //雪花算法生成随机id
        Snowflake snowflake = new Snowflake(6, 6);
        coursesDetailAddVo.setCourseId(snowflake.nextIdStr());
       int flag = coursesDetailService.addCourse(coursesDetailAddVo);
       if (flag==-1)return Result.OK("课程名已存在！");
       else if (flag==0)return Result.OK("课程添加失败！");
       else return Result.OK("课程添加成功！");
    }

    @AutoLog(value = "评价课程")
    @ApiOperation(value = "评价课程",notes = "评价课程")
    @PostMapping(value = "/commentCourse")
    public Result<?> commentCourse(@RequestBody CoursesComment coursesComment){
        coursesComment.setId(UUID.randomUUID().toString());
        if (coursesDetailService.commentCourse(coursesComment)<0)return Result.OK("评价课程失败！");
        return Result.OK("评价课程成功！");

    }

    @AutoLog(value = "我的学习-付费课程")
    @ApiOperation(value = "我的学习-付费课程",notes = "我的学习-付费课程")
    @GetMapping(value = "/getUserCoursesPaid")
    public Result<?> getUserCoursesPaid(@RequestParam("userId") String userId){
        return Result.OK(coursesDetailService.getUserCoursesPaid(userId));
    }

    @AutoLog(value = "我的学习-免费课程")
    @ApiOperation(value = "我的学习-免费课程",notes = "我的学习-免费课程")
    @GetMapping(value = "/getUserCoursesFree")
    public Result<?> getUserCoursesFree(@RequestParam("userId") String userId){
        return Result.OK(coursesDetailService.getUserCoursesFree(userId));
    }

    @AutoLog(value = "我的学习-最近学习")
    @ApiOperation(value = "我的学习-最近学习",notes = "我的学习-最近学习")
    @GetMapping(value = "/getUserCoursesRecently")
    public Result<?> getUserCoursesRecently(@RequestParam("userId") String userId){
        return Result.OK(coursesDetailService.getUserCoursesRecently(userId));
    }

    @AutoLog(value = "我的学习-最近学习—批量删除")
    @ApiOperation(value = "我的学习-最近学习—批量删除",notes = "我的学习-最近学习—批量删除")
    @DeleteMapping(value = "/deleteUserCoursesRecently")
    public Result<?> deleteUserCoursesRecently(@RequestParam("userId") String userId,@RequestParam("coursesIds") String[] coursesIds ){
       if (coursesDetailService.deleteUserCoursesRecently(userId,coursesIds)>0)return Result.OK("删除成功！");
       return Result.OK("删除失败！");
    }

    @AutoLog(value = "获取首页课程轮播图")
    @ApiOperation(value = "获取首页课程轮播图",notes = "获取首页课程轮播图")
    @GetMapping(value = "/getSlideShow")
    public Result<?> getSlideShow(){
        return Result.OK(coursesDetailService.getSlideShow());
    }

    @AutoLog(value = "添加首页课程轮播图")
    @ApiOperation(value = "添加首页课程轮播图",notes = "添加首页课程轮播图")
    @PostMapping(value = "/addSlideShow")
    public Result<?> addSlideShow(@RequestBody()CourseSlidesShow courseSlidesShow){
       if (coursesDetailService.addSlideShow(courseSlidesShow)>0)return Result.OK("添加成功！");
        return Result.OK("添加失败！");
    }

    @AutoLog(value = "修改首页课程轮播图")
    @ApiOperation(value = "修改首页课程轮播图",notes = "修改首页课程轮播图")
    @PostMapping(value = "/setSlideShow")
    public Result<?> setSlideShow(@RequestBody()CourseSlidesShow courseSlidesShow){
        if (coursesDetailService.setSlideShow(courseSlidesShow)>0)return Result.OK("修改成功！");
        return Result.OK("修改失败！");
    }

    @AutoLog(value = "删除首页课程轮播图")
    @ApiOperation(value = "删除首页课程轮播图",notes = "删除首页课程轮播图")
    @DeleteMapping(value = "/deleteSlideShow")
    public Result<?> deleteSlideShow(@RequestParam("coursesId")String coursesId ){
        if (coursesDetailService.deleteSlideShow(coursesId)>0)return Result.OK("删除成功！");
        return Result.OK("删除失败！");
    }
}
