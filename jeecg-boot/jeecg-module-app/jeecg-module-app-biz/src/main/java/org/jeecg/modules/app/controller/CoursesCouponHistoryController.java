package org.jeecg.modules.app.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.app.entity.CoursesCouponHistory;
import org.jeecg.modules.app.service.ICoursesCouponHistoryService;

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
 * @Description: 优惠券历史记录表
 * @Author: jeecg-boot
 * @Date:   2021-08-17
 * @Version: V1.0
 */
@Api(tags="优惠券历史记录表")
@RestController
@RequestMapping("/app/coupon/coursesCouponHistory")
@Slf4j
public class CoursesCouponHistoryController extends JeecgController<CoursesCouponHistory, ICoursesCouponHistoryService> {
	@Autowired
	private ICoursesCouponHistoryService coursesCouponHistoryService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coursesCouponHistory
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "优惠券历史记录表-分页列表查询")
	@ApiOperation(value="优惠券历史记录表-分页列表查询", notes="优惠券历史记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CoursesCouponHistory coursesCouponHistory,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CoursesCouponHistory> queryWrapper = QueryGenerator.initQueryWrapper(coursesCouponHistory, req.getParameterMap());
		Page<CoursesCouponHistory> page = new Page<CoursesCouponHistory>(pageNo, pageSize);
		IPage<CoursesCouponHistory> pageList = coursesCouponHistoryService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coursesCouponHistory
	 * @return
	 */
	@AutoLog(value = "优惠券历史记录表-添加")
	@ApiOperation(value="优惠券历史记录表-添加", notes="优惠券历史记录表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CoursesCouponHistory coursesCouponHistory) {
		coursesCouponHistoryService.save(coursesCouponHistory);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coursesCouponHistory
	 * @return
	 */
	@AutoLog(value = "优惠券历史记录表-编辑")
	@ApiOperation(value="优惠券历史记录表-编辑", notes="优惠券历史记录表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CoursesCouponHistory coursesCouponHistory) {
		coursesCouponHistoryService.updateById(coursesCouponHistory);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "优惠券历史记录表-通过id删除")
	@ApiOperation(value="优惠券历史记录表-通过id删除", notes="优惠券历史记录表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		coursesCouponHistoryService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "优惠券历史记录表-批量删除")
	@ApiOperation(value="优惠券历史记录表-批量删除", notes="优惠券历史记录表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coursesCouponHistoryService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "优惠券历史记录表-通过id查询")
	@ApiOperation(value="优惠券历史记录表-通过id查询", notes="优惠券历史记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CoursesCouponHistory coursesCouponHistory = coursesCouponHistoryService.getById(id);
		if(coursesCouponHistory==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coursesCouponHistory);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coursesCouponHistory
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoursesCouponHistory coursesCouponHistory) {
        return super.exportXls(request, coursesCouponHistory, CoursesCouponHistory.class, "优惠券历史记录表");
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
        return super.importExcel(request, response, CoursesCouponHistory.class);
    }

}
