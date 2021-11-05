package org.jeecg.modules.app.service.impl;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.SystemUser;
import org.jeecg.modules.app.mapper.SystemUserMapper;
import org.jeecg.modules.app.service.ISystemUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 系统登录注册用户表
 * @Author: jeecg-boot
 * @Date:   2021-06-11
 * @Version: V1.0
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {

	@Autowired
	private SystemUserMapper systemUserMapper;

	@Override
	@Transactional
	public SystemUser queryByPhone(String phone){
		return systemUserMapper.queryByPhone(phone);
	}

	@Override
	@Transactional
	public void saveMain(SystemUser systemUser ) {
		systemUserMapper.userRegister(systemUser);
	}

	@Override
	public boolean modifyPassword(String phone,String password,String newPassword) {
		return systemUserMapper.modifyPassword(phone, password,newPassword);
	}

	@Override
	public boolean setPassword(String phone, String newPassword) {
		return systemUserMapper.setPassword(phone, newPassword);
	}

	@Override
	public int alterAvatar(String userId, String avatar) {
		return systemUserMapper.alterAvatar(userId, avatar);
	}

	@Override
	public int alterName(String userId, String name) {
		if (systemUserMapper.queryByName(name)!=null)return -1;
		return systemUserMapper.alterName(userId, name);
	}

	@Override
	public int alterPhoneNumber(String userId, String phone) {
		return systemUserMapper.alterPhoneNumber(userId, phone);
	}


}
