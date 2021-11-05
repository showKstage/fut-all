package org.jeecg.modules.app.controller;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.app.entity.CoursesArticleDetail;
import org.jeecg.modules.app.service.ICoursesArticleDetailService;
import org.jeecg.modules.app.entity.CoursesDetail;
import org.jeecg.modules.app.service.ICoursesDetailService;
import org.jeecg.modules.app.vo.CoursesDetailVo;
import org.jeecg.modules.app.entity.CoursesTeacher;
import org.jeecg.modules.app.service.ICoursesTeacherService;
import org.jeecg.modules.app.vo.CoursesTeacherHomePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

import java.util.List;

/**
 * @Description: 教师信息表
 * @Author: jeecg-boot
 * @Date:   2021-06-27
 * @Version: V1.0
 */
@Api(tags="教师信息表")
@RestController
@RequestMapping("/app/courses/teacher/coursesTeacher")
@Slf4j
public class CoursesTeacherController extends JeecgController<CoursesTeacher, ICoursesTeacherService> {
	@Autowired
	private ICoursesTeacherService coursesTeacherService;

	@Autowired
	private ICoursesArticleDetailService coursesArticleDetailService;

	@Autowired
	private ICoursesDetailService coursesDetailService;

	/**
	 * 通过id查询
	 *
	 * @param teacherId 教师id
	 * @return
	 */
	@AutoLog(value = "教师信息表-通过id查询")
	@ApiOperation(value="教师信息表-通过id查询", notes="教师信息表-通过id查询")
	@GetMapping(value = "/queryTeacherById")
	public Result<?> queryById(@RequestParam(name="teacherId") String teacherId) {
		CoursesTeacher coursesTeacher = coursesTeacherService.getTeacherById(teacherId);
		long fansNum = coursesTeacherService.getFansNum(teacherId);
		coursesTeacher.setFans((int)fansNum);
		if(coursesTeacher==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coursesTeacher);
	}

	 /**
	  * 关注/取消关注教师
	  * @param teacherId 教师id
	  * @param userId 用户id
	  * @return 返回值 isFollow = 1 表示关注     isFollow = 0 表示取消关注
	  */
	 @AutoLog(value = "教师信息表-关注/取消关注教师")
	 @ApiOperation(value="教师信息表-关注/取消关注教师", notes="教师信息表-关注/取消关注教师")
	 @GetMapping(value = "/addOrReleaseTeacherById")
	 public Result<?> addOrReleaseTeacherById(@RequestParam(name="teacherId") String teacherId,
											  @RequestParam(name="userId") String userId,
											  @RequestParam(name="isFollow") int isFollowBefore) {
		/** isFollow = 1 表示已关注     isFollow = 0 表示未关注 */
		 int isFollow = coursesTeacherService.addOrRelease(userId, teacherId,isFollowBefore);
		 return Result.OK(isFollow);
	 }


	 /** TODO 按后续需求判断是否需要此功能
	 @AutoLog(value = "教师信息表-查看教师粉丝列表")
	 @ApiOperation(value="教师信息表-查看教师粉丝列表", notes="教师信息表-查看教师粉丝列表")
	 @GetMapping(value = "/checkFansListByTeacherID")
	 public Result<?> checkFansListByTeacherID(@RequestParam(name="teacherId") String teacherId) {
		 Set<?> fansId = FollowUtils.getFansId(teacherId);
	 }
	 **/

	 /**
	  * 查询教师主页
	  * @param teacherId 教师id
	  * @param userId  用户id
	  * @return 返回教师个人信息、粉丝数、用户是否关注、付费课程、免费课程、文章
	  */
	 @AutoLog(value = "教师信息表-查询教师主页")
	 @ApiOperation(value="教师信息表-查询教师主页", notes="教师信息表-查询教师主页")
	 @GetMapping(value = "/queryTeacherHomePageById")
	 public Result<?> queryTeacherHomePageById(@RequestParam(name="teacherId") String teacherId,
								@RequestParam(name="userId") String userId) {
		 CoursesTeacher teacherInfo = coursesTeacherService.getTeacherById(teacherId);
		 teacherInfo.setFans((int) coursesTeacherService.getFansNum(teacherId));

		 List<CoursesArticleDetail> coursesArticleDetails = coursesArticleDetailService.queryArticleByTeacherId(teacherId);
		 List<CoursesDetailVo> freeCoursesDetails = coursesDetailService.queryFreeCourses(teacherId);
		 List<CoursesDetailVo> payCoursesDetails = coursesDetailService.queryPayCourses(teacherId);
		 int isFollow = coursesTeacherService.queryIsFollow(userId, teacherId);

		 CoursesTeacherHomePage coursesTeacherHomePage = new CoursesTeacherHomePage(teacherInfo,isFollow,payCoursesDetails,freeCoursesDetails,coursesArticleDetails);
		 return Result.OK(coursesTeacherHomePage);
	 }



}
