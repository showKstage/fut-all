package org.jeecg.modules.app.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.app.entity.CoursesCatalogue;
import org.jeecg.modules.app.service.ICoursesCatalogueService;
import org.jeecg.modules.app.service.VodService;
import org.jeecg.modules.app.util.ConstantPropertiesUtil;
import org.jeecg.modules.app.vo.VodResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.jeecg.modules.app.util.AliyunVodSDKUtils.initVodClient;

/** 阿里云视频点播
 * @author hongwei.lin
 * @date 2021-07-20
 */
@Api(tags="视频点播")
@RestController
@RequestMapping("/app/courses/vod")
@Slf4j
public class AliyunVodController {

    @Autowired
    VodService vodService;

    @Autowired
    ICoursesCatalogueService catalogueService;

    /**
    /**
     * 上传视频
     * @param file 视频文件
     * @param catalogueId 课程目录id
     * @return
     * @throws Exception

    @AutoLog(value = "视频点播-上传视频")
    @ApiOperation(value="视频点播-上传视频", notes="视频点播-上传视频")
    @PostMapping("/aliyunVod")
    public Result<?> aliyunVodUpload(MultipartFile file,String catalogueId) throws Exception {
        String videoId = vodService.uploadVod(file);
        catalogueService.setCoursesVodId(catalogueId, videoId);
        return Result.OK(videoId);
    }
    */

    /**
     * 删除视频
     * @param id 课程目录id
     * @return
     * @throws Exception
     */
    @AutoLog(value = "视频点播-删除视频")
    @ApiOperation(value="视频点播-删除视频", notes="视频点播-删除视频")
    @DeleteMapping("delVod/{id}")
    public Result<?>  deleteVod(@PathVariable String id) throws Exception {
        catalogueService.setCoursesVodIdToNull(id);
        CoursesCatalogue coursesCatalogue = catalogueService.selectById(id);
        vodService.deleteVod(coursesCatalogue.getVodId());
        return Result.OK();
    }

    /**
     * 批量删除视频
     * @param ids 多个课程目录id
     * @return
     * @throws Exception
     */
    @AutoLog(value = "视频点播-批量删除视频")
    @ApiOperation(value="视频点播-批量删除视频", notes="视频点播-批量删除视频")
    @DeleteMapping("delAllVod/{ids}")
    public Result<?>  deleteVodAll(@RequestParam List<String> ids) throws Exception {
        List<String> vods = new ArrayList<>();
        for (String id : ids) {
            catalogueService.setCoursesVodIdToNull(id);
            CoursesCatalogue coursesCatalogue = catalogueService.selectById(id);
            vods.add(coursesCatalogue.getVodId());
        }
        vodService.deleteALLVod(vods);
        return Result.OK();
    }

    /**
     * 获取视频播放地址
     * @param id 课程目录id
     * @return
     */
    @AutoLog(value = "视频点播-获取视频播放地址")
    @ApiOperation(value="视频点播-获取视频播放地址", notes="视频点播-获取视频播放地址")
    @GetMapping("getPlayInfo")
    public Result<?>  getPlayInfo(@RequestParam String id) {
        DefaultAcsClient client = initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        List<String> vodPlayURL = new ArrayList<>();
        String title;
        //查询vodId
        CoursesCatalogue coursesCatalogue = catalogueService.selectById(id);
        String vodId = coursesCatalogue.getVodId();

        try {
            GetPlayInfoResponse response = vodService.getPlayInfo(client, vodId);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                vodPlayURL.add(playInfo.getPlayURL());
            }
            title = response.getVideoBase().getTitle();
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
        VodResponseVO vodResponseVO = new VodResponseVO();
        vodResponseVO.setTitle(title);
        vodResponseVO.setVodPlayURL(vodPlayURL);
        return Result.OK(vodResponseVO);
    }

}
