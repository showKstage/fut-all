package org.jeecg.modules.enhance;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.system.service.impl.SysAnnouncementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 添加执行计划加强类
 * @author hongwei.lin
 * @date 2021/11/06
 */
@Slf4j
@Component("AddRunProjectEnhance")
public class AddRunProjectEnhance implements CgformEnhanceJavaInter {

    @Autowired
    private SysAnnouncementServiceImpl sysAnnouncementService;

    // 暂被弃用
    @Override
    public int execute(String s, Map<String, Object> map) throws BusinessException {
        return 0;
    }

    @Override
    public int execute(String s, JSONObject jsonObject) throws BusinessException {
        String id = jsonObject.getString("id");
        sysAnnouncementService.addFateRunProject(id);
        return 0;
    }
}
