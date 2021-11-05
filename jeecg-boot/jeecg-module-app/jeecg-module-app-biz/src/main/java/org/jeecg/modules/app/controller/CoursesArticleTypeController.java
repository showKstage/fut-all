package org.jeecg.modules.app.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.app.entity.CoursesArticleType;
import org.jeecg.modules.app.service.ICoursesArticleTypeService;

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
 * @Description: 文章类别表
 * @Author: jeecg-boot
 * @Date:   2021-06-24
 * @Version: V1.0
 */
@Api(tags="文章类别表")
@RestController
@RequestMapping("/app/courses/article/coursesArticleType")
@Slf4j
public class CoursesArticleTypeController extends JeecgController<CoursesArticleType, ICoursesArticleTypeService> {
	@Autowired
	private ICoursesArticleTypeService coursesArticleTypeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coursesArticleType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "文章类别表-分页列表查询")
	@ApiOperation(value="文章类别表-分页列表查询", notes="文章类别表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CoursesArticleType coursesArticleType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CoursesArticleType> queryWrapper = QueryGenerator.initQueryWrapper(coursesArticleType, req.getParameterMap());
		Page<CoursesArticleType> page = new Page<CoursesArticleType>(pageNo, pageSize);
		IPage<CoursesArticleType> pageList = coursesArticleTypeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coursesArticleType
	 * @return
	 */
	@AutoLog(value = "文章类别表-添加")
	@ApiOperation(value="文章类别表-添加", notes="文章类别表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CoursesArticleType coursesArticleType) {
		coursesArticleTypeService.save(coursesArticleType);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coursesArticleType
	 * @return
	 */
	@AutoLog(value = "文章类别表-编辑")
	@ApiOperation(value="文章类别表-编辑", notes="文章类别表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CoursesArticleType coursesArticleType) {
		coursesArticleTypeService.updateById(coursesArticleType);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文章类别表-通过id删除")
	@ApiOperation(value="文章类别表-通过id删除", notes="文章类别表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		coursesArticleTypeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "文章类别表-批量删除")
	@ApiOperation(value="文章类别表-批量删除", notes="文章类别表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coursesArticleTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文章类别表-通过id查询")
	@ApiOperation(value="文章类别表-通过id查询", notes="文章类别表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CoursesArticleType coursesArticleType = coursesArticleTypeService.getById(id);
		if(coursesArticleType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coursesArticleType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coursesArticleType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoursesArticleType coursesArticleType) {
        return super.exportXls(request, coursesArticleType, CoursesArticleType.class, "文章类别表");
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
        return super.importExcel(request, response, CoursesArticleType.class);
    }

}
