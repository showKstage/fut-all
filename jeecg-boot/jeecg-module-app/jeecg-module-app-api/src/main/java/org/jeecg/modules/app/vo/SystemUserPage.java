package org.jeecg.modules.app.vo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 系统登录注册用户表
 * @Author: jeecg-boot
 * @Date:   2021-06-11
 * @Version: V1.0
 */
@Data
@ApiModel(value="courses_systemuserPage对象", description="系统登录注册用户表")
public class SystemUserPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private int id;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
	@ApiModelProperty(value = "用户id")
    private String userId;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
	@ApiModelProperty(value = "用户名")
    private String name;
	/**电话号码（用户账号）*/
	@Excel(name = "电话号码（用户账号）", width = 15)
	@ApiModelProperty(value = "电话号码（用户账号）")
    private String phone;
	/**登录密码*/
	@Excel(name = "登录密码")
	@ApiModelProperty(value = "登录密码")
	private String password;
	/**头像*/
	@Excel(name = "头像", width = 15)
	@ApiModelProperty(value = "头像")
    private String avatar;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "创建时间")
    private Date createTime;


}
