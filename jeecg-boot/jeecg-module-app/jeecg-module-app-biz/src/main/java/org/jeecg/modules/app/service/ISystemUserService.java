package org.jeecg.modules.app.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.DySmsEnum;
import org.jeecg.modules.app.entity.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 系统登录注册用户表
 * @Author: jeecg-boot
 * @Date:   2021-06-11
 * @Version: V1.0
 */
public interface ISystemUserService extends IService<SystemUser> {

	public SystemUser queryByPhone(String phone);
	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(SystemUser systemUser) ;
	
	boolean modifyPassword(String phone,String password,String newPassword);

	boolean setPassword(String phone, String newPassword);

	int alterAvatar(String userId, String avatar);

	int alterName(String userId, String name);

	int alterPhoneNumber(String userId, String phone);


}
