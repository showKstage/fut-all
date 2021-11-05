package org.jeecg.modules.app.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 视频点播配置类
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${jeecg.vod.accessKeyId}")
    private String keyId;

    @Value("${jeecg.vod.accessKeySecret}")
    private String keySecret;


    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID=keyId;
        ACCESS_KEY_SECRET=keySecret;
    }
}
