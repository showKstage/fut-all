package org.jeecg.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.entity.JeecgHelloEntity;

/**
 * 测试接口
 */
public interface IJeecgHelloService extends IService<JeecgHelloEntity> {

    String hello();

}
