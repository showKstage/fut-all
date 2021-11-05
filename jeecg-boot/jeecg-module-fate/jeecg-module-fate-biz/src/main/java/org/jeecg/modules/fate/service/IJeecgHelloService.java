package org.jeecg.modules.fate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.fate.entity.JeecgHelloEntity;

/**
 * 测试接口
 */
public interface IJeecgHelloService extends IService<JeecgHelloEntity> {

    String hello();

}
