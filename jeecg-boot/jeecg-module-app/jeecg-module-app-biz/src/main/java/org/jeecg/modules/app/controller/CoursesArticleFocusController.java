package org.jeecg.modules.app.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.app.entity.CoursesArticleFocus;
import org.jeecg.modules.app.service.ICoursesArticleFocusService;

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
 * @Description: 文章关注表
 * @Author: jeecg-boot
 * @Date:   2021-06-20
 * @Version: V1.0
 */
@Api(tags="文章关注表")
@RestController
@RequestMapping("/app/courses/article/coursesArticleFocus")
@Slf4j
public class CoursesArticleFocusController extends JeecgController<CoursesArticleFocus, ICoursesArticleFocusService> {
	@Autowired
	private ICoursesArticleFocusService coursesArticleFocusService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coursesArticleFocus
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "文章关注表-分页列表查询")
	@ApiOperation(value="文章关注表-分页列表查询", notes="文章关注表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CoursesArticleFocus coursesArticleFocus,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CoursesArticleFocus> queryWrapper = QueryGenerator.initQueryWrapper(coursesArticleFocus, req.getParameterMap());
		Page<CoursesArticleFocus> page = new Page<CoursesArticleFocus>(pageNo, pageSize);
		IPage<CoursesArticleFocus> pageList = coursesArticleFocusService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coursesArticleFocus
	 * @return
	 */
	@AutoLog(value = "文章关注表-添加")
	@ApiOperation(value="文章关注表-添加", notes="文章关注表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CoursesArticleFocus coursesArticleFocus) {
		coursesArticleFocusService.save(coursesArticleFocus);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coursesArticleFocus
	 * @return
	 */
	@AutoLog(value = "文章关注表-编辑")
	@ApiOperation(value="文章关注表-编辑", notes="文章关注表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CoursesArticleFocus coursesArticleFocus) {
		coursesArticleFocusService.updateById(coursesArticleFocus);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文章关注表-通过id删除")
	@ApiOperation(value="文章关注表-通过id删除", notes="文章关注表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		coursesArticleFocusService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "文章关注表-批量删除")
	@ApiOperation(value="文章关注表-批量删除", notes="文章关注表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coursesArticleFocusService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文章关注表-通过id查询")
	@ApiOperation(value="文章关注表-通过id查询", notes="文章关注表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CoursesArticleFocus coursesArticleFocus = coursesArticleFocusService.getById(id);
		if(coursesArticleFocus==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coursesArticleFocus);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coursesArticleFocus
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoursesArticleFocus coursesArticleFocus) {
        return super.exportXls(request, coursesArticleFocus, CoursesArticleFocus.class, "文章关注表");
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
        return super.importExcel(request, response, CoursesArticleFocus.class);
    }

}
