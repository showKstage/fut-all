package org.jeecg.modules.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.app.service.ICoursesLabelService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.app.vo.CourseLabelVo;
import org.jeecg.modules.app.vo.CoursesParentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
* @Description: 标签表
* @Author: jeecg-boot
* @Date:   2021-06-13
* @Version: V1.0
*/
@Api(tags="标签表")
@RestController
@RequestMapping("app/courses/coursesLabel")
@Slf4j
public class CoursesLabelController {
   @Autowired
   private ICoursesLabelService coursesLabelService;
    @Autowired
    private RedisUtil redisUtil;

   /**
    * @return 选择分类属性
    */
   @AutoLog(value = "标签表-选择分类-查询分类")
   @ApiOperation(value="标签表-选择分类-查询分类", notes="标签表-选择分类-查询分类")
   @GetMapping(value = "/queryCoursesCategory")
   public Result<?> queryCoursesCategory() {

       List<String> jobCategory = coursesLabelService.getCoursesCategory("职场提升");
       List<String> businessCategory = coursesLabelService.getCoursesCategory("电商运营");
       List<String> programCategory = coursesLabelService.getCoursesCategory("编程开发");

       CoursesParentCategory jobParentCategory = new CoursesParentCategory("职场提升",jobCategory);
       CoursesParentCategory businessParentCategory = new CoursesParentCategory("电商运营",businessCategory);
       CoursesParentCategory programParentCategory = new CoursesParentCategory("编程开发",programCategory);

       List<CoursesParentCategory> allCategory = new ArrayList<>();
       allCategory.add(jobParentCategory);
       allCategory.add(businessParentCategory);
       allCategory.add(programParentCategory);

       return Result.OK(allCategory);

   }

    @AutoLog(value = "标签表-选择分类-添加用户课程标签")
    @ApiOperation(value="标签表-选择分类-添加用户课程标签", notes="标签表-选择分类-添加用户课程标签")
    @PostMapping(value = "/addUserLabelToCourses")
    public Result<?> addUserLabelToCourses(@RequestParam(name="labels") List<String> labels,
                                           @RequestParam(name="userId") String userId) {
        for (String label : labels) { coursesLabelService.addUserLabel(label, userId); }
        return Result.OK();
    }


    /**
     * @return 获取所有标签
     */
    @AutoLog(value = "获取所有标签")
    @ApiOperation(value="获取所有标签", notes="获取所有标签")
    @GetMapping(value = "/getAllCourseLabel")
    public Result<?> getAllCourseLabel() {
        List<CourseLabelVo> courseLabels = new ArrayList<>();
        if(redisUtil.hasKey("courseLabels")){
            courseLabels = (List<CourseLabelVo>) redisUtil.get("courseLabels");
            return Result.OK(courseLabels);
        }
        courseLabels = coursesLabelService.getAllCourseLabel();
        redisUtil.set("courseLabels",courseLabels);
        redisUtil.expire("courseLabels",24*3600);
        return Result.OK(courseLabels);

    }

    /**
     * @return 添加课程标签
     */
    @AutoLog(value = "添加课程标签")
    @ApiOperation(value="添加课程标签", notes="添加课程标签")
    @GetMapping(value = "/addCourseLabel")
    public Result<?> addCourseLabel(@RequestParam(name="labelName")String labelName) {
        int flag = coursesLabelService.addCourseLabel(UUID.randomUUID().toString(),labelName);
        if (flag>0)return Result.OK("添加成功！");
        else if (flag==-1)return Result.OK("标签已存在！");
        else return Result.OK("添加失败！");
    }


    /**
     * @return 调整用户分类选择
     */
    @AutoLog(value = "调整用户分类选择")
    @ApiOperation(value="调整用户分类选择", notes="调整用户分类选择")
    @GetMapping(value = "/updateUserCourseLabel")
    public Result<?> updateUserCourseLabel(@RequestParam(name="labels") List<String> labels,
                                           @RequestParam(name="userId") String userId){

        coursesLabelService.deleteUerCourseLabel(userId);
        int num = 0;
        for (int i = 0; i < labels.size(); i++){
            String label = labels.get(i);
            int n = coursesLabelService.addUserLabel(label, userId);
            num += n;
        }
        if (num != labels.size()){
            return Result.error("插入标签失败！");
        }
        return Result.OK();

    }

}
