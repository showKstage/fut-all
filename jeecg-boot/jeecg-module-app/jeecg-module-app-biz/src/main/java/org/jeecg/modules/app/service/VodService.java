package org.jeecg.modules.app.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

public interface VodService {

    /** 上传视频 */
//    String uploadVod(MultipartFile file) throws Exception;

    /** 删除单个视频 */
    void deleteVod(String id) throws Exception;

    /** 删除多个视频 */
    void deleteALLVod(List<String> ids) throws Exception;

    /** 获取播放地址 */
    GetPlayInfoResponse getPlayInfo(DefaultAcsClient client,String vodId) throws ClientException;
}
