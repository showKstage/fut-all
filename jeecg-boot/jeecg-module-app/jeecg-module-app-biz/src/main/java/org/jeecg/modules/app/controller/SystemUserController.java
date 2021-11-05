package org.jeecg.modules.app.controller;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.app.entity.SystemUser;
import org.jeecg.modules.app.service.ISystemUserService;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.DySmsEnum;
import org.jeecg.common.util.DySmsHelper;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.base.service.impl.BaseCommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Description: 用户登录注册模块
 * @Author: jiale.zhang
 * @Date:   2021-06-11
 * @Version: V1.0
 */
@Api(tags="用户登录注册模块")
@RestController
@RequestMapping("/app/courses/")
@Slf4j
public class SystemUserController {
	@Autowired
	private ISystemUserService systemUserService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Resource
	RedisUtil redisUtil;

	@Resource
	BaseCommonServiceImpl baseCommon;

	@Bean  //注入加密api
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 注册账号
	 * @param phone
	 * @param password
	 * @param smsMode 当smsMode为1时表示验证码用于注册
	 * @param captcha
	 * @return
	 */
	@AutoLog(value = "系统登录注册用户表-注册")
	@ApiOperation(value = "系统登录注册用户表-注册", notes = "系统登录注册用户表-注册")
	@PostMapping(value = "/register")
	public Result<?> add(@RequestParam(name = "phone") String phone,
						 @RequestParam(name = "password" ) String password,
						 @RequestParam(name = "smsMode" ) String smsMode,
						 @RequestParam(name = "captcha" ) String captcha) {
		SystemUser user = systemUserService.queryByPhone(phone);
		if (user != null){
			return Result.error("该手机号已经注册，请重新登录！");
		}

		SystemUser systemUser = new SystemUser();
		String uuid36 = UUID.randomUUID().toString();
		String uuid32 = uuid36.replaceAll("-", "");
		systemUser.setUserId(uuid32);
		systemUser.setPhone(phone);
		String encodePassword = passwordEncoder.encode(password); //密码加密
		systemUser.setPassword(encodePassword);

		//当smsMode为1时，用户通过手机号+验证码注册；否则通过手机号+密码注册
		if (CommonConstant.SMS_TPL_TYPE_1.equals(smsMode)){
			Object code = redisUtil.get(phone);
			if (code == null || StringUtils.isEmpty(captcha)){
				return Result.error("验证码错误，请稍后再试！");
			}else if (((String)code).equals(captcha)) {
				systemUserService.saveMain(systemUser);
				redisUtil.del(phone);
				return Result.OK("注册成功！");
			}
		}

		return Result.OK("注册失败，请稍后重试！");
	}

	/**
	 * 登录账号
	 * @param phone
	 * @param password
	 * @param smsMode 当smsMode为0时，用户通过手机号+验证码登录
	 * @param captcha
	 * @return
	 */
	@AutoLog(value = "系统登录验证-登录")
	@ApiOperation(value = "系统登录验证-登录", notes = "系统登录验证-登录")
	@PostMapping(value = "/login")
	public Result<?> queryByPhoneAndPassword(@RequestParam(name = "phone") String phone,
											 @RequestParam(name = "password",required = false) String password,
											 @RequestParam(name = "smsMode",required = false) String smsMode,
											 @RequestParam(name = "captcha",required = false) String captcha) {
		SystemUser systemUser = systemUserService.queryByPhone(phone);
		if (systemUser == null) {
			return Result.error("未找到对应用户，请先注册！");
		}

		/** 生成token */
		String token = JwtUtil.sign(systemUser.getUserId(), password);
		//设置token缓存有效时间
		redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
		redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
		JSONObject obj = new JSONObject();
		obj.put("token", token);
		obj.put("userInfo", systemUser);

		//当smsMode为0时，用户通过手机号+验证码登录；否则通过手机号+密码登录
		if (CommonConstant.SMS_TPL_TYPE_0.equals(smsMode)) {
			Object code = redisUtil.get(phone);
			if (code == null || StringUtils.isEmpty(captcha)) {
				return Result.error("验证码错误，请稍后再试！");
			} else if (((String) code).equals(captcha)) {
				redisUtil.del(phone);
				return Result.OK(systemUser);
			}
		}

		String encoderPassword = systemUser.getPassword();
		if (passwordEncoder.matches(password, encoderPassword)) {
			return Result.OK(obj);
		} else {
			return Result.error("密码错误！");
		}
	}

	/**
	 * 短信获取验证码
	 *
	 * @param phone
	 * @param smsMode 0-通过验证码登录 , 1-注册账号 , 2-忘记密码 , 3-修改手机号码
	 * @return
	 */
	@ApiOperation(value = "短信获取验证码", notes = "短信获取验证码")
	@GetMapping(value = "/getSms")
	public Result<?> getSms(@RequestParam(name = "phone") String phone,
							@RequestParam(name = "smsMode") String smsMode) {
		log.info(phone);
		if (StringUtils.isEmpty(phone)) {
			return Result.error("手机号不能为空！");
		}

		if (redisUtil.hasKey(phone) == true){
			return Result.error("验证码5分钟内，仍然有效！");
		}

		String captcha = RandomUtil.randomNumbers(6);
		JSONObject sms = new JSONObject();
		sms.put("code", captcha);
		try {
			//flag用于判断发送信息是否成功
			boolean flag = false;
			//smsMode为1时，处于注册状态
			if (CommonConstant.SMS_TPL_TYPE_1.equals(smsMode)) {
				SystemUser systemUser = systemUserService.queryByPhone(phone);
				if (systemUser != null) {
					baseCommon.addLog("手机号已经注册，请重新登录！", CommonConstant.LOG_TYPE_1, null);
					return Result.error("手机号已经注册，请重新登录！");
				}
				flag = DySmsHelper.sendSms(phone, sms, DySmsEnum.REGISTER_TEMPLATE_CODE);
			} else {
				/** 按后续是否需要判断账户是否有效添加判断账户有效功能 */
				//smsMode为0时，处于登录状态 ； smsMode为2时，处于忘记密码状态
				if (CommonConstant.SMS_TPL_TYPE_0.equals(smsMode)) {
					flag = DySmsHelper.sendSms(phone, sms, DySmsEnum.LOGIN_TEMPLATE_CODE);
				} else if (CommonConstant.SMS_TPL_TYPE_2.equals(smsMode)) {
					flag = DySmsHelper.sendSms(phone, sms, DySmsEnum.FORGET_PASSWORD_TEMPLATE_CODE);
				}
			}

			if (flag == false) {
				return Result.error("验证码发送失败，请稍后重试！");
			}

			//设置验证码有效期为5分钟
			redisUtil.set(phone, captcha, 300);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Result.error("短信发送服务出现异常，请联系管理员！");
		}

		return Result.OK();

	}


	/**
	 * 修改密码 / 忘记密码
	 *
	 * @param phone
	 * @param beforePassword 原密码
	 * @param afterPassword 修改后密码
	 * @return
	 */
	@ApiOperation(value = "系统登录注册用户表-修改密码/忘记密码", notes = "系统登录注册用户表-修改密码/忘记密码")
	@PostMapping(value = "/modifyPassword")
	public Result<?> modifyPassword(@RequestParam(name = "phone") String phone,
									@RequestParam(name = "beforePassword",required = false) String beforePassword,
									@RequestParam(name = "newPassword") String afterPassword) {
		String newPassword = passwordEncoder.encode(afterPassword);
		boolean flag = false;

		SystemUser systemUser = systemUserService.queryByPhone(phone);
		if (systemUser.getPassword() != null && passwordEncoder.matches(beforePassword,systemUser.getPassword())){
			flag = systemUserService.setPassword(phone, newPassword);
		}else if (systemUser.getPassword() == null){
			flag = systemUserService.setPassword(phone, newPassword);
		}

		if (flag == true) {
			return Result.OK("密码设置成功！");
		} else {
			return Result.error("原密码输入错误，密码修改失败！");
		}
	}

	/**
	 * 忘记密码
	 */
	@ApiOperation(value = "系统登录注册用户表-忘记密码", notes = "系统登录注册用户表-忘记密码")
	@PostMapping(value = "/forgetPassword")
	public Result<?> forgetPassword(@RequestParam(name = "phone") String phone,
							  @RequestParam(name = "captcha") String captcha) {
		Object code = redisUtil.get(phone);
		if (code == null || StringUtils.isEmpty(captcha)){
			return Result.error("验证码错误，请稍后再试！");
		}else if (((String)code).equals(captcha)){
			systemUserService.setPassword(phone, null);
		}
		return Result.OK("验证码输入成功！");
	}


	/**
	 * 修改昵称
	 */
	@ApiOperation(value = "修改昵称", notes = "修改昵称")
	@PostMapping(value = "/alterName")
	public Result<?> alterName(@RequestParam(name = "userId") String userId,
									@RequestParam(name = "newName") String newName){
		int flag = systemUserService.alterName(userId, newName);
		if (flag==-1)return Result.OK("该昵称已存在！");
		else if(flag==0)return Result.OK("修改失败！");
		else return Result.OK("修改成功！");
	}


	/**
	 * 修改头像
	 */
	@ApiOperation(value = "修改头像", notes = "修改头像")
	@PostMapping(value = "/alterAvatar")
	public Result<?> alterAvatar(@RequestParam(name = "userId") String userId,
								 @RequestParam(name = "avatar") String avatar){
		if (systemUserService.alterAvatar(userId, avatar)>0)return Result.OK("修改成功！");
		return Result.OK("修改失败！");
	}

	/**
	 * 修改手机号（验证旧手机号码）
	 */
	@ApiOperation(value = "修改手机号-验证旧手机号码", notes = "修改手机号-验证旧手机号码")
	@PostMapping(value = "/verifyPhoneNumber")
	public Result<?> verifyPhoneNumber(@RequestParam(name = "phone") String phone,
									  @RequestParam(name = "captcha" ) String captcha
									  ) throws ClientException {
		Object code = redisUtil.get(phone);
		if (code == null || StringUtils.isEmpty(captcha)||!((String) code).equals(captcha))return Result.error("验证码错误，请稍后再试！");
		redisUtil.del(phone);
		return Result.OK("验证成功！");
	}


	/**
	 * 修改手机号（更新手机号）
	 */
	@ApiOperation(value = "修改手机号-更新手机号", notes = "修改手机号-更新手机号")
	@PostMapping(value = "/alterPhoneNumber")
	public Result<?> alterPhoneNumber(@RequestParam(name = "phone") String phone,
									  @RequestParam(name = "captcha" ) String captcha,
									  @RequestParam(name = "userId" ) String userId){
		Object code = redisUtil.get(phone);
		if (code == null || StringUtils.isEmpty(captcha)||!((String) code).equals(captcha))return Result.error("验证码错误，请稍后再试！");
		else {
			if (systemUserService.alterPhoneNumber(userId, phone)<0)return Result.OK("更新失败！");
			redisUtil.del(phone);
			return Result.OK("更新成功！");
		}

	}
}
