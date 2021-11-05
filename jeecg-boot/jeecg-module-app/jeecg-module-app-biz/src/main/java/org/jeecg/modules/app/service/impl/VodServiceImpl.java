package org.jeecg.modules.app.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
//import com.aliyun.vod.upload.req.UploadStreamRequest;
//import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import org.jeecg.modules.app.service.ICoursesCatalogueService;
import org.jeecg.modules.app.service.ICoursesDetailService;
import org.jeecg.modules.app.service.ICoursesTeacherService;
import org.jeecg.modules.app.service.VodService;
import org.jeecg.modules.app.util.AliyunVodSDKUtils;
import org.jeecg.modules.app.util.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/** @author hongwei.lin
 * @date 2020-07-23
 */
@Service
public class VodServiceImpl implements VodService{

    @Autowired
    private ICoursesDetailService coursesDetailService;

    @Autowired
    private ICoursesTeacherService coursesTeacherService;

    @Autowired
    private ICoursesCatalogueService coursesCatalogueService;

    /**
    /**
     *  阿里云上传视频
//     * @param file
     * @return
     * @throws Exception

    @Override
    public String uploadVod(MultipartFile file) throws Exception {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = "";
            if (response.isSuccess()) {
                videoId=response.getVideoId();
            }else{
                videoId=response.getVideoId();
            }

            return videoId;
        } catch (Exception e) {
            throw new Exception("视频上传失败");
        }
    }
    */
    //阿里云删除视频
    @Override
    public void deleteVod(String id) throws Exception {
        try{
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("视频删除失败");
        }
    }

    //阿里云批量删除视频
    @Override
    public void deleteALLVod(List<String> ids) throws Exception {
        try{
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String join = StringUtils.join(ids, ",");
            request.setVideoIds(join);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("视频删除失败");
        }
    }

    /*获取播放地址函数*/
    @Override
    public GetPlayInfoResponse getPlayInfo(DefaultAcsClient client,String vodId) throws ClientException {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(vodId);
        return client.getAcsResponse(request);
    }



}
