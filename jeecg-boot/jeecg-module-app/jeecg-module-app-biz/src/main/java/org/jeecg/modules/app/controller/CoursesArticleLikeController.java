package org.jeecg.modules.app.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.app.entity.CoursesArticleLike;
import org.jeecg.modules.app.service.ICoursesArticleLikeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 文章点赞表
 * @Author: jeecg-boot
 * @Date:   2021-06-24
 * @Version: V1.0
 */
@Api(tags="文章点赞表")
@RestController
@RequestMapping("/app/courses/article/coursesArticleLike")
@Slf4j
public class CoursesArticleLikeController extends JeecgController<CoursesArticleLike, ICoursesArticleLikeService> {
	@Autowired
	private ICoursesArticleLikeService coursesArticleLikeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coursesArticleLike
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "文章点赞表-分页列表查询")
	@ApiOperation(value="文章点赞表-分页列表查询", notes="文章点赞表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CoursesArticleLike coursesArticleLike,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CoursesArticleLike> queryWrapper = QueryGenerator.initQueryWrapper(coursesArticleLike, req.getParameterMap());
		Page<CoursesArticleLike> page = new Page<CoursesArticleLike>(pageNo, pageSize);
		IPage<CoursesArticleLike> pageList = coursesArticleLikeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coursesArticleLike
	 * @return
	 */
	@AutoLog(value = "文章点赞表-添加")
	@ApiOperation(value="文章点赞表-添加", notes="文章点赞表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CoursesArticleLike coursesArticleLike) {
		coursesArticleLikeService.save(coursesArticleLike);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coursesArticleLike
	 * @return
	 */
	@AutoLog(value = "文章点赞表-编辑")
	@ApiOperation(value="文章点赞表-编辑", notes="文章点赞表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CoursesArticleLike coursesArticleLike) {
		coursesArticleLikeService.updateById(coursesArticleLike);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文章点赞表-通过id删除")
	@ApiOperation(value="文章点赞表-通过id删除", notes="文章点赞表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		coursesArticleLikeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "文章点赞表-批量删除")
	@ApiOperation(value="文章点赞表-批量删除", notes="文章点赞表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coursesArticleLikeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文章点赞表-通过id查询")
	@ApiOperation(value="文章点赞表-通过id查询", notes="文章点赞表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CoursesArticleLike coursesArticleLike = coursesArticleLikeService.getById(id);
		if(coursesArticleLike==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coursesArticleLike);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coursesArticleLike
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoursesArticleLike coursesArticleLike) {
        return super.exportXls(request, coursesArticleLike, CoursesArticleLike.class, "文章点赞表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoursesArticleLike.class);
    }

	 /**
	  * 获取文章点赞数
	  * @param articleId
	  * @return
	  */
	 @AutoLog(value = "获取文章点赞数")
	 @ApiOperation(value="获取文章点赞数", notes="获取文章点赞数")
	 @GetMapping(value = "/queryArticleLikeNum")
	 public Result<?> queryArticleLikeNum(@RequestParam(name="articleId",required=true) String articleId) {
		 return Result.OK(coursesArticleLikeService.queryArticleLikeNum(articleId));
	 }

	 /**
	  * 获取文章收藏数
	  * @param articleId
	  * @return
	  */
	 @AutoLog(value = "获取文章收藏数")
	 @ApiOperation(value="获取文章收藏数", notes="获取文章收藏数")
	 @GetMapping(value = "/queryArticleCollectNum")
	 public Result<?> queryArticleCollectNum(@RequestParam(name="articleId",required=true) String articleId) {
		 return Result.OK(coursesArticleLikeService.queryArticleCollectNum(articleId));
	 }

	 /**
	  * 收藏文章
	  * @param userId
	  * @param articleId
	  * @return
	  */
	 @AutoLog(value = "文章收藏/取消收藏")
	 @ApiOperation(value="文章收藏/取消收藏", notes="文章收藏/取消收藏")
	 @GetMapping(value = "/collectArticle")
	 public Result<?> collectArticle(@RequestParam(name="userId",required=true) String userId,@RequestParam(name="articleId",required=true) String articleId) {
		 if (coursesArticleLikeService.judgeCollectArticle(userId, articleId)!=null){
		 	if (coursesArticleLikeService.deleteCollectArticle(userId, articleId)>0)return Result.OK("取消收藏成功！");
		 	else return Result.OK("取消收藏失败！");
		 }
		 if (coursesArticleLikeService.addCollectArticle(userId, articleId)>0)return Result.OK("收藏成功！");
		 else return Result.OK("收藏失败！");
	 }

	 @AutoLog(value = "判断文章收藏")
	 @ApiOperation(value="判断文章收藏", notes="判断文章收藏")
	 @GetMapping(value = "/judgeCollectArticle")
	 public Result<?> judgeCollectArticle(@RequestParam(name="userId",required=true) String userId,@RequestParam(name="articleId",required=true) String articleId) {
		 if (coursesArticleLikeService.judgeCollectArticle(userId, articleId)!=null)return Result.OK("已收藏！");
		 return Result.OK("未收藏！");
	 }

}
