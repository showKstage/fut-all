package org.jeecg.modules.app.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.app.entity.JeecgHelloEntity;
import org.jeecg.modules.app.mapper.JeecgHelloMapper;
import org.jeecg.modules.app.service.IJeecgHelloService;
import org.springframework.stereotype.Service;

/**
 * 测试Service
 */
@Service
public class JeecgHelloServiceImpl extends ServiceImpl<JeecgHelloMapper, JeecgHelloEntity> implements IJeecgHelloService {

    @Override
    public String hello() {
        return "hello jeecg!";
    }
}
