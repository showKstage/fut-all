package org.jeecg.modules.app.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.app.entity.CoursesArticleComment;
import org.jeecg.modules.app.entity.CoursesArticleCommentLike;
import org.jeecg.modules.app.entity.CoursesArticleLike;
import org.jeecg.modules.app.service.ICoursesArticleCommentService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 文章评论表
 * @Author: jeecg-boot
 * @Date:   2021-06-24
 * @Version: V1.0
 */
@Api(tags="文章评论表")
@RestController
@RequestMapping("/app/courses/article/coursesArticleComment")
@Slf4j
public class CoursesArticleCommentController extends JeecgController<CoursesArticleComment, ICoursesArticleCommentService> {
	@Autowired
	private ICoursesArticleCommentService coursesArticleCommentService;

	@AutoLog(value = "文章评论表-添加")
	@ApiOperation(value="文章评论表-添加", notes="文章评论表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CoursesArticleComment coursesArticleComment) {
		coursesArticleCommentService.save(coursesArticleComment);
		return Result.OK("添加成功！");
	}

	@AutoLog(value = "文章评论表-编辑")
	@ApiOperation(value="文章评论表-编辑", notes="文章评论表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CoursesArticleComment coursesArticleComment) {
		coursesArticleCommentService.updateById(coursesArticleComment);
		return Result.OK("编辑成功!");
	}

	 @AutoLog(value = "删除文章评论及子评论")
	 @ApiOperation(value="删除文章评论及子评论", notes="删除文章评论及子评论")
	 @DeleteMapping(value = "/deletesArticleComment")
	 public Result<?> deletesArticleComment(@RequestParam(name="id",required=true) String id) {
		if (coursesArticleCommentService.deletesArticleComment(id)>0) 		 return Result.OK("删除成功!");
		else return Result.OK("删除失败!");
	 }

	 @AutoLog(value = "评论点赞/取消点赞")
	 @ApiOperation(value="评论点赞/取消点赞", notes="评论点赞/取消点赞")
	 @GetMapping(value = "/likeArticleComment")
	 public Result<?> likeArticleComment(@RequestParam(name="commentId",required=true) String commentId,@RequestParam(name="userId",required=true)String userId) {
		 if (coursesArticleCommentService.queryArticleCommentLike(commentId, userId)!=null){
			 if (coursesArticleCommentService.deleteArticleCommentLike(commentId,userId)>0)return Result.OK("删除成功!");
			 else return Result.OK("删除失败!");
		 }
		 if (coursesArticleCommentService.addArticleCommentLike(commentId, userId)>0)return Result.OK("添加成功！");
		 else return Result.OK("添加失败!");
	 }

	 @AutoLog(value = "判断评论点赞")
	 @ApiOperation(value="判断评论点赞", notes="判断评论点赞")
	 @GetMapping(value = "/judgeArticleCommentLike")
	 public Result<?> judgeArticleCommentLike(@RequestParam(name="commentId",required=true) String commentId,@RequestParam(name="userId",required=true)String userId) {
		 if (coursesArticleCommentService.queryArticleCommentLike(commentId, userId)!=null)return Result.OK("已点赞!");
			 else return Result.OK("未点赞!");
	 }

	 @AutoLog(value = "获取子评论数")
	 @ApiOperation(value="获取子评论数", notes="获取子评论数")
	 @GetMapping(value = "/getArticleCommentNum")
	 public Result<?> getArticleCommentNum(@RequestParam(name="commentId",required=true) String commentId) {
		 return Result.OK(coursesArticleCommentService.getArticleCommentNum(commentId));
	 }

	 @AutoLog(value = "获取评论点赞数")
	 @ApiOperation(value="获取评论点赞数", notes="获取评论点赞数")
	 @GetMapping(value = "/getArticleCommentLikeNum")
	 public Result<?> getArticleCommentLikeNum(@RequestParam(name="commentId",required=true) String commentId) {
		 return Result.OK(coursesArticleCommentService.getArticleCommentLikeNum(commentId));
	 }

	 @AutoLog(value = "获取文章评论数")
	 @ApiOperation(value="获取文章评论数", notes="获取文章评论数")
	 @GetMapping(value = "/queryArticleCommentNum")
	 public Result<?> queryArticleCommentNum(@RequestParam(name="articleId",required=true) String articleId) {
		 return Result.OK(coursesArticleCommentService.queryArticleCommentNum(articleId));
	 }
}
