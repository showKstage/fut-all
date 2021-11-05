package org.jeecg.modules.app.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.app.entity.CoursesCouponRelation;
import org.jeecg.modules.app.service.ICoursesCouponRelationService;

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
 * @Description: 优惠券课程关系表
 * @Author: jeecg-boot
 * @Date:   2021-08-17
 * @Version: V1.0
 */
@Api(tags="优惠券课程关系表")
@RestController
@RequestMapping("/app/coupon/coursesCouponRelation")
@Slf4j
public class CoursesCouponRelationController extends JeecgController<CoursesCouponRelation, ICoursesCouponRelationService> {
	@Autowired
	private ICoursesCouponRelationService coursesCouponRelationService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coursesCouponRelation
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "优惠券课程关系表-分页列表查询")
	@ApiOperation(value="优惠券课程关系表-分页列表查询", notes="优惠券课程关系表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CoursesCouponRelation coursesCouponRelation,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CoursesCouponRelation> queryWrapper = QueryGenerator.initQueryWrapper(coursesCouponRelation, req.getParameterMap());
		Page<CoursesCouponRelation> page = new Page<CoursesCouponRelation>(pageNo, pageSize);
		IPage<CoursesCouponRelation> pageList = coursesCouponRelationService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coursesCouponRelation
	 * @return
	 */
	@AutoLog(value = "优惠券课程关系表-添加")
	@ApiOperation(value="优惠券课程关系表-添加", notes="优惠券课程关系表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CoursesCouponRelation coursesCouponRelation) {
		coursesCouponRelationService.save(coursesCouponRelation);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coursesCouponRelation
	 * @return
	 */
	@AutoLog(value = "优惠券课程关系表-编辑")
	@ApiOperation(value="优惠券课程关系表-编辑", notes="优惠券课程关系表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CoursesCouponRelation coursesCouponRelation) {
		coursesCouponRelationService.updateById(coursesCouponRelation);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "优惠券课程关系表-通过id删除")
	@ApiOperation(value="优惠券课程关系表-通过id删除", notes="优惠券课程关系表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		coursesCouponRelationService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "优惠券课程关系表-批量删除")
	@ApiOperation(value="优惠券课程关系表-批量删除", notes="优惠券课程关系表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coursesCouponRelationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "优惠券课程关系表-通过id查询")
	@ApiOperation(value="优惠券课程关系表-通过id查询", notes="优惠券课程关系表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CoursesCouponRelation coursesCouponRelation = coursesCouponRelationService.getById(id);
		if(coursesCouponRelation==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coursesCouponRelation);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coursesCouponRelation
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoursesCouponRelation coursesCouponRelation) {
        return super.exportXls(request, coursesCouponRelation, CoursesCouponRelation.class, "优惠券课程关系表");
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
        return super.importExcel(request, response, CoursesCouponRelation.class);
    }

}
