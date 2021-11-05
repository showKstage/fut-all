package org.jeecg.modules.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.app.util.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Api(tags="文件上传和下载")
@RestController
@RequestMapping("/app/courses/file")
@Slf4j
public class FileController {

    @Autowired
    OssUtil ossUtil;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @AutoLog(value = "文件上传和下载-图片上传")
    @ApiOperation(value = "文件上传和下载-图片上传", notes = "文件上传和下载-图片上传")
    @PostMapping(value = "/uploadImage")
    public Result<?> uploadImage(@RequestPart("image") MultipartFile file) {
        try {
            String url = ossUtil.uploadFile(file); //调用OSS工具类
            Map<String, Object> returnbody = new HashMap<>();
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("url", url);
            returnbody.put("data",returnMap);
            returnbody.put("code","200");
            returnbody.put("message","上传成功");
            return Result.OK(returnbody);
        }
        catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @AutoLog(value = "文件上传和下载-图片访问")
    @ApiOperation(value = "文件上传和下载-图片访问", notes = "文件上传和下载-图片访问")
    @PostMapping(value = "/downloadImage")
    public Result<?> downloadImage(String url) {
        String fileUrl = ossUtil.getFileUrl(url);
        return Result.OK(fileUrl);
    }

    @AutoLog(value = "文件上传和下载-删除图片链接")
    @ApiOperation(value = "文件上传和下载-删除图片链接", notes = "文件上传和下载-删除图片链接")
    @PostMapping(value = "/deleteImage")
    public Result<?> deleteImage(String imageName){
        try {
            ossUtil.deleteImage(imageName);
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
        return Result.OK();
    }
}
