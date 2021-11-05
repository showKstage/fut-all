package org.jeecg.modules.fate.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.fate.entity.JeecgHelloEntity;
import org.jeecg.modules.fate.mapper.JeecgHelloMapper;
import org.jeecg.modules.fate.service.IJeecgHelloService;
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
