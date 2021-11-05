package org.jeecg.modules.app.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.app.entity.SystemUser;

/**
 * @Description: 系统登录注册用户表
 * @Author: jeecg-boot
 * @Date:   2021-06-11
 * @Version: V1.0
 */
public interface SystemUserMapper extends BaseMapper<SystemUser> {
    SystemUser queryByPhone(@Param("phone") String phone);
    int userRegister(SystemUser systemUser);
    boolean modifyPassword(@Param("phone") String phone,@Param("password") String password,@Param("newPassword") String newPassword);
    boolean setPassword(@Param("phone") String phone,@Param("newPassword") String newPassword);
    int alterAvatar(@Param("userId") String userId, @Param("avatar") String avatar);
    int alterName(@Param("userId") String userId, @Param("name") String name);
    String queryByName(@Param("name") String name);
    int alterPhoneNumber(@Param("userId") String userId, @Param("phone") String phone);

}
