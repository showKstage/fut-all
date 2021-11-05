package org.jeecg.modules.app.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.jeecg.modules.app.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/** 阿里云oss对象存储工具类
 * @author hongwei.lin
 * @date 2021.07.31
 */
@Component
public class OssUtil {

    protected static final Logger log = LoggerFactory.getLogger(OssUtil.class);

    @Value("${jeecg.oss.endpoint}")
    private String endpoint;

    @Value("${jeecg.oss.accessKey}")
    private String accessKeyId;

    @Value("${jeecg.oss.secretKey}")
    private String accessKeySecret;

    @Value("${jeecg.oss.bucketName}")
    private String bucketName;

    //文件存储目录
    private String filedir = "image/";

    @Autowired
    IFileService fileService;

    /**
     * 单个文件上传
     * @param file
     * @return 返回完整URL地址
     */
    public String uploadFile(MultipartFile file) {
        String str = "";
        try {
            String fileUrl = uploadImg2Oss(file);
            str = getFileUrl(fileUrl);
            fileService.saveImageLink(fileUrl, str);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return str.trim();
    }


    /**
     * 通过文件名获取文完整件路径
     * @param fileUrl
     * @return 完整URL路径
     */
    public String getFileUrl(String fileUrl) {
        if (fileUrl !=null && fileUrl.length()>0) {
            String[] split = fileUrl.split("/");
            String url = this.getUrl(this.filedir + split[split.length - 1]);
            return url;
        }
        return null;
    }

    //获取去掉参数的完整路径
    private String getShortUrl(String url) {
        String[] imgUrls = url.split("\\?");
        return imgUrls[0].trim();
    }

    // 获得url链接
    private String getUrl(String key) {
        // 设置URL过期时间为20年 3600l* 1000*24*365*20
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 20);
        // 生成URL
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return getShortUrl(url.toString());
        }
        return null;
    }

    // 上传文件
    private String uploadImg2Oss(MultipartFile file) {
        //1、限制最大文件为20M
        if (file.getSize() > 1024 * 1024 *20) {
            return "图片太大";
        }

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase(); //文件后缀
        String uuid = UUID.randomUUID().toString();
        String name = uuid + suffix;

        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        }
        catch (Exception e) {
            return "上传失败";
        }
    }


    // 上传文件（指定文件名）
    private String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件

            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    private static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        //PDF
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        return "image/jpeg";
    }

    /**
     * 删除图片链接
     * @param imageName
     */
    public void deleteImage(String imageName){
        fileService.deleteImageLink(imageName);
    }
}
