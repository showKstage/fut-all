package org.jeecg.modules.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.app.entity.CoursesCoupon;
import org.jeecg.modules.app.service.ICoursesCouponHistoryService;
import org.jeecg.modules.app.service.ICoursesCouponService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.app.vo.CoursesCouponListVo;
import org.jeecg.modules.app.vo.CoursesCouponVo;
import org.jeecg.modules.app.entity.SystemUser;
import org.jeecg.modules.app.service.ISystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 优惠券表
 * @Author: jeecg-boot
 * @Date:   2021-08-17
 * @Version: V1.0
 */
@Api(tags="优惠券表")
@RestController
@RequestMapping("/app/coupon/coursesCoupon")
@Slf4j
public class CoursesCouponController extends JeecgController<CoursesCoupon, ICoursesCouponService> {

	@Autowired
	private ICoursesCouponService coursesCouponService;

	@Autowired
	private ICoursesCouponHistoryService coursesCouponHistoryService;

	@Autowired
	private ISystemUserService systemUserService;

	/**
	 * 分页列表查询
	 *
	 * @param coursesCoupon
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "优惠券表-分页列表查询")
	@ApiOperation(value="优惠券表-分页列表查询", notes="优惠券表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CoursesCoupon coursesCoupon,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CoursesCoupon> queryWrapper = QueryGenerator.initQueryWrapper(coursesCoupon, req.getParameterMap());
		Page<CoursesCoupon> page = new Page<CoursesCoupon>(pageNo, pageSize);
		IPage<CoursesCoupon> pageList = coursesCouponService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coursesCoupon
	 * @return
	 */
	@AutoLog(value = "优惠券表-添加")
	@ApiOperation(value="优惠券表-添加", notes="优惠券表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CoursesCoupon coursesCoupon) {
		coursesCouponService.save(coursesCoupon);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coursesCoupon
	 * @return
	 */
	@AutoLog(value = "优惠券表-编辑")
	@ApiOperation(value="优惠券表-编辑", notes="优惠券表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CoursesCoupon coursesCoupon) {
		coursesCouponService.updateById(coursesCoupon);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "优惠券表-通过id删除")
	@ApiOperation(value="优惠券表-通过id删除", notes="优惠券表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		coursesCouponService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "优惠券表-批量删除")
	@ApiOperation(value="优惠券表-批量删除", notes="优惠券表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coursesCouponService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "优惠券表-通过id查询")
	@ApiOperation(value="优惠券表-通过id查询", notes="优惠券表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CoursesCoupon coursesCoupon = coursesCouponService.getById(id);
		if(coursesCoupon==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coursesCoupon);
	}


	 /**
	  * 查询用户所有优惠卷——支付订单
	  * @param userId
	  * @param amount
	  * @param courseId
	  * @param categoryId
	  * @return
	  */
	 @AutoLog(value = "查询用户所有优惠卷——支付订单")
	 @ApiOperation(value="查询用户所有优惠卷——支付订单", notes="查询用户所有优惠卷——支付订单")
	 @GetMapping(value = "/getCoursesCouponPay")
	 public Result<?> getCoursesCouponPay(@RequestParam(name="userId")String userId,
												 @RequestParam(name="amount")Double amount,
												 @RequestParam(name="courseId")String courseId,
												 @RequestParam(name="categoryId")String categoryId){

    	 List<CoursesCouponVo> coursesCoupons = coursesCouponService.queryCoursesCouponByUserId(userId);
		 CoursesCouponListVo coursesCouponList = new CoursesCouponListVo();
		 List<CoursesCouponVo> usableCoupons = new ArrayList<>();
		 List<CoursesCouponVo> unavailableCoupons = new ArrayList<>();
		 Date date = new Date();
		 for (CoursesCouponVo coursesCoupon : coursesCoupons) {
		 	//通过金额判断是否可用
		 	if (coursesCoupon.getMinPoint()<amount){
				//通过类型判断是否可用
				if (coursesCoupon.getUseType()==0){
					//通过时间判断是否可用
					if (date.compareTo(coursesCoupon.getStartTime()) > 0 && date.compareTo(coursesCoupon.getEndTime()) < 0)usableCoupons.add(coursesCoupon);
					else unavailableCoupons.add(coursesCoupon);
				}
				if (coursesCoupon.getUseType()==1){
					if (date.compareTo(coursesCoupon.getStartTime()) > 0 && date.compareTo(coursesCoupon.getEndTime()) < 0){
						if (categoryId.equals(coursesCoupon.getCategoryId()))usableCoupons.add(coursesCoupon);
						else unavailableCoupons.add(coursesCoupon);
					}
					else unavailableCoupons.add(coursesCoupon);
				}
				if (coursesCoupon.getUseType()==2){
					if (date.compareTo(coursesCoupon.getStartTime()) > 0 && date.compareTo(coursesCoupon.getEndTime()) < 0){
						if (courseId.equals(coursesCoupon.getCourseId()))usableCoupons.add(coursesCoupon);
						else unavailableCoupons.add(coursesCoupon);
					}
					else unavailableCoupons.add(coursesCoupon);
				}

			}
		 }
		 coursesCouponList.setUsableCoupons(usableCoupons);
		 coursesCouponList.setUnavailableCoupons(unavailableCoupons);
		 return Result.OK(coursesCouponList);
	 }


	 /**
	  * 查询用户所有优惠卷——我的优惠卷
	  * @param userId
	  * @return
	  */
	 @AutoLog(value = "查询用户所有优惠卷——我的优惠卷")
	 @ApiOperation(value="查询用户所有优惠卷——我的优惠卷", notes="查询用户所有优惠卷——我的优惠卷")
	 @GetMapping(value = "/getUserCoursesCoupon")
	 public Result<?> getUserCoursesCoupon(@RequestParam(name="userId")String userId){
		 List<CoursesCouponVo> coursesCoupons = coursesCouponService.queryCoursesCouponByUserId(userId);
		 CoursesCouponListVo coursesCouponList = new CoursesCouponListVo();
		 List<CoursesCouponVo> usableCoupons = new ArrayList<>();
		 List<CoursesCouponVo> unavailableCoupons = new ArrayList<>();
		 Date date = new Date();
		 for (CoursesCouponVo coursesCoupon : coursesCoupons) {
			 //通过时间判断是否可用
			 if (date.compareTo(coursesCoupon.getEndTime()) > 0) {
			 	coursesCouponHistoryService.setCoursesCoupon(coursesCoupon.getId());
			 	unavailableCoupons.add(coursesCoupon);
			 }
			 else usableCoupons.add(coursesCoupon);
		 }
		 coursesCouponList.setUsableCoupons(usableCoupons);
		 coursesCouponList.setUnavailableCoupons(unavailableCoupons);
		 return Result.OK(coursesCouponList);
	 }


	 /**
	  * 使用优惠卷
	  * @param couponId
	  * @return
	  */
	 @AutoLog(value = "使用优惠卷")
	 @ApiOperation(value="使用优惠卷", notes="使用优惠卷")
	 @GetMapping(value = "/UseCoursesCoupon")
	 public Result<?> UseCoursesCoupon(@RequestParam(name="couponId")String couponId,
									   HttpServletRequest request) throws Exception {
		 String token = request.getHeader("token");
		 if (StringUtils.isEmpty(token)){
			 throw new Exception("token不能为空");
		 }
		 String userId = JwtUtil.getUsername(token);
		 if (userId.length() == 11){
			 SystemUser systemUser = systemUserService.queryByPhone(userId);
			 userId = systemUser.getUserId();
		 }

	 	if (coursesCouponHistoryService.UseCoursesCoupon(couponId,new Date(),userId)>0)return Result.OK("使用成功！");
	 	else return Result.OK("使用失败！");
	 }



}
