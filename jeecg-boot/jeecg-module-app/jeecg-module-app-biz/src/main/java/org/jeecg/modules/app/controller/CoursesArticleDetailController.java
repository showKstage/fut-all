package org.jeecg.modules.app.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.app.service.ICoursesArticleCommentService;
import org.jeecg.modules.app.service.ICoursesArticleDetailService;
import org.jeecg.modules.app.entity.CoursesArticleDetail;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.app.vo.CoursesArticleCommentVo;
import org.jeecg.modules.app.vo.CoursesArticleDetailVo;
import org.jeecg.modules.app.entity.CoursesTeacher;
import org.jeecg.modules.app.service.ICoursesTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 文章详情表
 * @Author: jeecg-boot
 * @Date:   2021-06-20
 * @Version: V1.0
 */
@Api(tags="文章详情表")
@RestController
@RequestMapping("/app/courses/article/coursesArticleDetail")
@Slf4j
public class CoursesArticleDetailController extends JeecgController<CoursesArticleDetail, ICoursesArticleDetailService> {
	@Autowired
	private ICoursesArticleDetailService coursesArticleDetailService;
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ICoursesTeacherService coursesTeacherService;

	@Autowired
	private ICoursesArticleCommentService coursesArticleCommentService;

	/**
	 *   添加
	 *
	 * @param coursesArticleDetail
	 * @return
	 */
	@AutoLog(value = "文章详情表-添加")
	@ApiOperation(value="文章详情表-添加", notes="文章详情表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CoursesArticleDetail coursesArticleDetail) {
		coursesArticleDetailService.save(coursesArticleDetail);
		return Result.OK("添加成功！");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文章详情表-通过id删除")
	@ApiOperation(value="文章详情表-通过id删除", notes="文章详情表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		coursesArticleDetailService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "文章详情表-批量删除")
	@ApiOperation(value="文章详情表-批量删除", notes="文章详情表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coursesArticleDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文章详情表-通过文章id查询")
	@ApiOperation(value="文章详情表-通过文章id查询", notes="文章详情表-通过文章id查询")
	@GetMapping(value = "/queryArticleById")
	public Result<?> queryArticleById(@RequestParam(name="id",required=true) String id) {
		CoursesArticleDetail coursesArticleDetail = coursesArticleDetailService.queryArticleByid(id);
		if(coursesArticleDetail==null) {
			return Result.error("未找到对应数据");
		}

		//点击量
		if(!redisUtil.hasKey(coursesArticleDetail.getArticleTitle())){
			redisUtil.set(coursesArticleDetail.getArticleTitle(),0);
		}
		redisUtil.incr(coursesArticleDetail.getArticleTitle(),1);

		CoursesArticleDetailVo articleDetailVo = new CoursesArticleDetailVo();
		CoursesTeacher teacher = coursesTeacherService.getTeacherById(coursesArticleDetail.getTeacherId());
		List<CoursesArticleCommentVo> coursesArticleCommentVos = coursesArticleCommentService.queryArticleComment(id);

		articleDetailVo.setCoursesArticleDetail(coursesArticleDetail);
		articleDetailVo.setCoursesTeacher(teacher);
		articleDetailVo.setCoursesArticleCommentVo(coursesArticleCommentVos);

		/** TODO 推荐文章需求暂未实现
		articleDetailVo.setRelatedArticle(null);
		*/
		return Result.OK(articleDetailVo);

	}

	 /**
	  * 我的学习-我的收藏
	  * @param userId
	  * @return
	  */
	@AutoLog(value = "我的学习-我的收藏")
	@ApiOperation(value = "我的学习-我的收藏",notes = "我的学习-我的收藏")
	@GetMapping(value = "/getUserArticleCollect")
	public Result<?> getUserArticleCollect(@RequestParam("userId") String userId){
		return Result.OK(coursesArticleDetailService.queryUserArticle(userId));
	}

}
