package org.jeecg.modules.app.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.app.service.ICoursesCouponHistoryService;
import org.jeecg.modules.app.service.ICoursesCouponService;
import org.jeecg.modules.app.vo.CoursesCouponVo;
import org.jeecg.modules.app.service.ICoursesDetailService;
import org.jeecg.modules.app.vo.CoursesDetailIntroductionVo;
import org.jeecg.modules.app.entity.SystemUser;
import org.jeecg.modules.app.service.ISystemUserService;
import org.jeecg.modules.app.entity.CoursesOrder;
import org.jeecg.modules.app.service.ICoursesOrderService;

import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.app.vo.CoursesOrderInfo;
import org.jeecg.modules.app.vo.CoursesOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2021-08-21
 * @Version: V1.0
 */
@Api(tags="订单表")
@RestController
@RequestMapping("/app/courses/order/coursesOrder")
@Slf4j
public class CoursesOrderController extends JeecgController<CoursesOrder, ICoursesOrderService> {

	@Autowired
	private ICoursesOrderService coursesOrderService;

	@Autowired
	private ISystemUserService systemUserService;

	@Autowired
	private ICoursesDetailService coursesDetailService;

	@Autowired
	private ICoursesCouponHistoryService coursesCouponHistoryService;

	 /**
	  * 确认订单
	  * @param coursesId
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 @AutoLog(value = "确认订单")
	 @ApiOperation(value="确认订单", notes="确认订单")
	 @GetMapping(value = "/checkOrder")
	 public Result<?> checkOrder(@RequestParam("coursesId") String coursesId,
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

		 //查询课程详情
		 CoursesDetailIntroductionVo coursesDetailIntroductionVo = coursesDetailService.queryCoursesDetail(coursesId);
		 List<String> coursesCategoryId = coursesDetailService.getCoursesCategoryId(coursesId);

		 //查询当前课程用户可使用的优惠券和不可使用优惠券
		 List<List<CoursesCouponVo>> couponsList = coursesOrderService.getCouponsByCoursesIdAndUserId(userId,coursesId, coursesCategoryId, coursesDetailIntroductionVo);
		 List<CoursesCouponVo> usableCoupons = couponsList.get(1);
		 List<CoursesCouponVo> unusableCoupons = couponsList.get(0);

		 CoursesOrderVO coursesOrderVO = new CoursesOrderVO()
				 .setCoursesDetailIntroductionVo(coursesDetailIntroductionVo)
				 .setUnusableCouponsList(unusableCoupons)
				 .setUsableCouponsList(usableCoupons)
				 .setOriginalPrice(coursesDetailIntroductionVo.getCoursesDetailPrice())
				 //默认支付方式为3 余额支付
				 .setPayType(3)
				 //订单类型 默认为0->正常订单
				 .setOrderType(0);
		 if (coursesDetailIntroductionVo.getCoursesFlashSale() != null) {
			 //促销优惠金额
			 coursesOrderVO.setPromotionAmount(coursesDetailIntroductionVo.getCoursesFlashSale().getOriginalPrice() - coursesDetailIntroductionVo.getCoursesFlashSale().getCurrentPrice());
		 }
		 if (usableCoupons.size() != 0) {
		 //默认当前选择优惠券为可用优惠券中第一张 即优惠金额最大
		 coursesOrderVO.setCurrentCoupon(usableCoupons.get(0));
		 coursesOrderVO.setCouponPrice(usableCoupons.get(0).getAmount());
		 coursesOrderVO.setRealPrice(coursesOrderVO.getOriginalPrice() - coursesOrderVO.getCouponPrice());
		 if (coursesDetailIntroductionVo.getCoursesFlashSale() != null){
			 coursesOrderVO.setRealPrice(coursesOrderVO.getRealPrice() - coursesOrderVO.getPromotionAmount());
		 }
		 }else {
			 if (coursesDetailIntroductionVo.getCoursesFlashSale() != null){
				 coursesOrderVO.setRealPrice(coursesOrderVO.getOriginalPrice() - coursesOrderVO.getPromotionAmount());
			 }else {
				 coursesOrderVO.setRealPrice(coursesOrderVO.getOriginalPrice());
			 }
		 }

		 return Result.OK(coursesOrderVO);
	 }


	 /**
	  * 生成订单
	  * @param coursesOrderVO
	  * @param request
	  * @param coursesId
	  * @return
	  * @throws Exception
	  */
	@AutoLog(value = "生成订单")
	@ApiOperation(value="生成订单", notes="生成订单")
	@PostMapping(value = "/createOrder")
	public Result<?> createOrder(@RequestBody CoursesOrderVO coursesOrderVO,
								 String coursesId,
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
		HashMap<String, String> data = new HashMap<>();
		try {
			String orderSn = coursesOrderService.createOrder(coursesOrderVO, userId, coursesId);
			data.put("orderSn", orderSn);
		}catch (Exception e) {
			return Result.error(e.getMessage());
		}
		return Result.OK(data);

	}




	 /**
	  * 查看订单列表
	  * @param request
	  * @return
	  */
	@AutoLog(value = "查看订单列表")
	@ApiOperation(value="查看订单列表", notes="查看订单列表")
	@GetMapping(value = "/queryOrder")
	public Result<?> queryOrder(HttpServletRequest request,
								String status) throws Exception {
		String token = request.getHeader("token");
		if (StringUtils.isEmpty(token)){
			throw new Exception("token不能为空");
		}
		String userId = JwtUtil.getUsername(token);
		if (userId.length() == 11){
			SystemUser systemUser = systemUserService.queryByPhone(userId);
			userId = systemUser.getUserId();
		}
		List<CoursesOrderInfo> orders = coursesOrderService.queryOrderByUserId(userId, status);
		return Result.OK(orders);
	}


	 /**
	  * 删除订单 (只是修改订单状态 订单数据本身不删除)
	  * @param request
	  * @param orderSn
	  * @return
	  */
	@AutoLog(value = "删除订单")
	@ApiOperation(value="删除订单", notes="删除订单")
	@PutMapping(value = "/deleteOrder")
	public Result<?> deleteOrder(@RequestParam("orderSn") String orderSn,
								 HttpServletRequest request
								 ) throws Exception {
		String token = request.getHeader("token");
		if (StringUtils.isEmpty(token)){
			throw new Exception("token不能为空");
		}
		String userId = JwtUtil.getUsername(token);
		if (userId.length() == 11){
			SystemUser systemUser = systemUserService.queryByPhone(userId);
			userId = systemUser.getUserId();
		}
		try {
			coursesOrderService.deleteOrder(userId, orderSn);
		}catch (Exception e){
			return Result.error("删除失败!");
		}
		return Result.OK("删除成功!");
	}

	 /**
	  * 关闭订单 (超时十五分钟未付款)
	  * @param orderSn
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 @AutoLog(value = "关闭订单")
	 @ApiOperation(value="关闭订单", notes="关闭订单")
	 @PostMapping(value = "/closeOrder")
	 public Result<?> closeOrder(String orderSn,
								  String couponId,
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
		 coursesOrderService.payTimeoutOrder(userId, orderSn);
		 //返还使用的优惠券
		 coursesCouponHistoryService.recallCoupon(couponId,userId);
		 return Result.OK("订单已关闭！");

	 }




}
