package org.jeecg.modules.app.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.app.entity.SystemUser;
import org.jeecg.modules.app.service.ISystemUserService;
import org.jeecg.modules.app.service.ICoursesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Unsafe;

import javax.servlet.http.HttpServletRequest;


@Api(tags="支付回调")
@RestController
@RequestMapping("/app/courses/order/coursesWxPay")
@Slf4j
public class CoursesWxPayController {

    @Autowired
    ICoursesOrderService coursesOrderService;

    @Autowired
    ISystemUserService systemUserService;

    @AutoLog(value = "微信支付结果回调")
    @ApiOperation(value="微信支付结果回调", notes="微信支付结果回调")
    @PostMapping(value = "/wxPayCallBack")
    public Result<?> wxPayCallBack(@RequestBody JSONObject jsonObject) throws Exception {
        String orderSn = jsonObject.getString("outTradeNo");
        int code = (int)jsonObject.get("code");
        if (code == 1){
            return Result.OK("用户取消了付款！");
        }else if (code == 0){
            coursesOrderService.paySuccessedOrder(orderSn);
            return Result.OK("用户下单成功！");
        }
        return Result.error("非法编码！");
    }



}
