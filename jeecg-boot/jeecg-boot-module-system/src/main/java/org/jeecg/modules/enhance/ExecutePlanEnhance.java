package org.jeecg.modules.enhance;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.system.service.impl.SysAnnouncementServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * 执行计划加强类
 * @author hongwei.lin
 * @date 2021/11/05
 */
@Component("ExecutePlanEnhance")
@Slf4j
public class ExecutePlanEnhance implements CgformEnhanceJavaInter {

    @Resource
    private SysAnnouncementServiceImpl sysAnnouncementService;

    // 暂被弃用
    @Override
    public int execute(String s, Map<String, Object> map) throws BusinessException {
        return 0;
    }

    @Override
    public int execute(String s, JSONObject jsonObject){
        String parameter = jsonObject.getString("execute_json");
        // 算法run执行接口
        String url = "http://fate-p.aift.ftwhale.com:8082/v1/job/submit";
        String result = doPostJson(url, parameter);
        if ("success".equals(result)){
            // 将 准备执行中 状态修改为 执行中
            String fateProjectId = jsonObject.getString("fate_project_id");
            sysAnnouncementService.setRunningStatus(fateProjectId);
        }
        log.info(result);
        return 0;
    }

    private String doPostJson(String url , String json){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String result;
        // 创建httppost对象
        HttpPost httpPost = new HttpPost(url);
        StringEntity httpEntity = new StringEntity(json,"utf-8");
        // 设置请求格式
        httpPost.setHeader("Content-type","application/json");
        httpPost.setEntity(httpEntity);
        // 发送请求，并获取返回值
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpPost);
            //返回json数据
            HttpEntity entity = response.getEntity();
            String responseContent = EntityUtils.toString(entity, "UTF-8");
            log.info(responseContent);
            //返回响应码200
            return "success";
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
            return "error";
    }
}
