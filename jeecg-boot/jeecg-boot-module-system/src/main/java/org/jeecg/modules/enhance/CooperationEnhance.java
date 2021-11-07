package org.jeecg.modules.enhance;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.CommonSendStatus;
import org.jeecg.common.constant.WebsocketConst;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.message.mapper.SysMessageMapper;
import org.jeecg.modules.message.websocket.WebSocket;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.system.entity.SysAnnouncement;
import org.jeecg.modules.system.service.ISysAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 申请企业合作加强类
 * @author hongwei.lin
 * @date 2021/11/03
 */
@Component("CooperationEnhance")
@Slf4j
public class CooperationEnhance implements CgformEnhanceJavaInter {

    @Autowired
    private ISysAnnouncementService sysAnnouncementService;

    @Autowired
    private SysMessageMapper sysMessageMapper;

    @Resource
    private WebSocket webSocket;

    // 暂被弃用
    @Override
    public int execute(String s, Map<String, Object> map) throws BusinessException {
        return 0;
    }

    @Override
    public int execute(String s, JSONObject jsonObject) throws BusinessException {
        String create_by = jsonObject.getString("create_by");
        String cooperationId = jsonObject.getString("id");
        String[] cooperationFirm = jsonObject.getString("cooperation").split(",");
        for (String firmIds : cooperationFirm) {
            if (!",".equals(firmIds)) {
                // 查询申请企业对应userId
//                String firmId = sysMessageMapper.selectFirmIdByFirmName(firmIds);
                String originFirmId = sysMessageMapper.selectFirmIdByFirmName(firmIds);
                // 编写请求消息模版
                SysAnnouncement sysAnnouncement = new SysAnnouncement();
                sysAnnouncement.setTitile("您有一个企业合作申请，请查阅！");
                sysAnnouncement.setMsgCategory("1");
                sysAnnouncement.setCreateBy(create_by);
                sysAnnouncement.setDelFlag("0");
                sysAnnouncement.setStartTime(new Date());
                sysAnnouncement.setMsgType("USER");
                //雪花算法生成随机订单编号
                Snowflake snowflake = new Snowflake(5, 5);
                String snowId = snowflake.nextIdStr();
                sysAnnouncement.setId(snowId);
                String website = sysMessageMapper.selectWebsiteByCreateBy(create_by);
                sysAnnouncement.setMsgContent("<p>现有一个企业申请与您合作</p>\n<p><a href=" + website + ">" +
                        "查阅该申请企业" + "<" + create_by + ">" + "详细信息</a></p>\n\n\n" +
                        "<p><a href=\"http://localhost:7001/sys/annountCement/cooperation/agree/" + cooperationId + "/"+ snowId + "\" onclick=\"javascript:alert('操作成功')>同意合作</a></p><p>" +
                        "<a href=\\\"http://www.baidu.com\\\">暂时拒绝</a></p>");
                sysAnnouncement.setUserIds(originFirmId + ",");
                sysAnnouncement.setEndTime(new Date());
                sysAnnouncementService.saveAnnouncement(sysAnnouncement);

                // 触达消息至对应企业用户
//                String id = sysMessageMapper.selectSmsIdByAnnouncement(sysAnnouncement);
//                sysAnnouncement = sysAnnouncementService.getById(id);
                if (sysAnnouncement == null) {
                    log.error("未找到对应实体");
                } else {
                    sysAnnouncement.setSendStatus(CommonSendStatus.PUBLISHED_STATUS_1);//发布中
                    sysAnnouncement.setSendTime(new Date());
                    sysAnnouncement.setSender(create_by);
                    boolean ok = sysAnnouncementService.updateById(sysAnnouncement);
                    if (ok) {
                        log.info("该系统通知发布成功");
                        if (sysAnnouncement.getMsgType().equals(CommonConstant.MSG_TYPE_ALL)) {
                            JSONObject obj = new JSONObject();
                            obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_TOPIC);
                            obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
                            obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
                            webSocket.sendMessage(obj.toJSONString());
                        } else {
                            // 2.插入用户通告阅读标记表记录
                            String userId = sysAnnouncement.getUserIds();
                            String[] userIds = userId.substring(0, (userId.length() - 1)).split(",");
//                            String anntId = sysAnnouncement.getId();
//                            Date refDate = new Date();
                            JSONObject obj = new JSONObject();
                            obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_USER);
                            obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
                            obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
                            webSocket.sendMessage(userIds, obj.toJSONString());
                        }
                    }
                }
            }
        }
        return 0;
    }
}
