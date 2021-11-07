package org.jeecg.modules.system.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.CommonSendStatus;
import org.jeecg.common.constant.WebsocketConst;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.message.mapper.SysMessageMapper;
import org.jeecg.modules.message.websocket.WebSocket;
import org.jeecg.modules.system.entity.FateCooperation;
import org.jeecg.modules.system.entity.SysAnnouncement;
import org.jeecg.modules.system.entity.SysAnnouncementSend;
import org.jeecg.modules.system.mapper.SysAnnouncementMapper;
import org.jeecg.modules.system.mapper.SysAnnouncementSendMapper;
import org.jeecg.modules.system.service.ISysAnnouncementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 系统通告表
 * @Author: jeecg-boot
 * @Date:  2019-01-02
 * @Version: V1.0
 */
@Service
@Slf4j
public class SysAnnouncementServiceImpl extends ServiceImpl<SysAnnouncementMapper, SysAnnouncement> implements ISysAnnouncementService {

	@Resource
	private SysAnnouncementMapper sysAnnouncementMapper;
	
	@Resource
	private SysAnnouncementSendMapper sysAnnouncementSendMapper;

	@Resource
	private SysMessageMapper sysMessageMapper;
	
	@Resource
	private WebSocket webSocket;

	@Resource
	private SysAnnouncementServiceImpl sysAnnouncementService;

	@Transactional
	@Override
	public void saveAnnouncement(SysAnnouncement sysAnnouncement) {
		if(sysAnnouncement.getMsgType().equals(CommonConstant.MSG_TYPE_ALL)) {
			sysAnnouncementMapper.insert(sysAnnouncement);
		}else {
			// 1.插入通告表记录
			sysAnnouncementMapper.insert(sysAnnouncement);
			// 2.插入用户通告阅读标记表记录
			String userId = sysAnnouncement.getUserIds();
			String[] userIds = userId.substring(0, (userId.length()-1)).split(",");
			String anntId = sysAnnouncement.getId();
			Date refDate = new Date();
			for(int i=0;i<userIds.length;i++) {
				SysAnnouncementSend announcementSend = new SysAnnouncementSend();
				announcementSend.setAnntId(anntId);
				announcementSend.setUserId(userIds[i]);
				announcementSend.setReadFlag(CommonConstant.NO_READ_FLAG);
				announcementSend.setReadTime(refDate);
				sysAnnouncementSendMapper.insert(announcementSend);
			}
		}
	}
	
	/**
	 * @功能：编辑消息信息
	 */
	@Transactional
	@Override
	public boolean upDateAnnouncement(SysAnnouncement sysAnnouncement) {
		// 1.更新系统信息表数据
		sysAnnouncementMapper.updateById(sysAnnouncement);
		String userId = sysAnnouncement.getUserIds();
		if(oConvertUtils.isNotEmpty(userId)&&sysAnnouncement.getMsgType().equals(CommonConstant.MSG_TYPE_UESR)) {
			// 2.补充新的通知用户数据
			String[] userIds = userId.substring(0, (userId.length()-1)).split(",");
			String anntId = sysAnnouncement.getId();
			Date refDate = new Date();
			for(int i=0;i<userIds.length;i++) {
				LambdaQueryWrapper<SysAnnouncementSend> queryWrapper = new LambdaQueryWrapper<SysAnnouncementSend>();
				queryWrapper.eq(SysAnnouncementSend::getAnntId, anntId);
				queryWrapper.eq(SysAnnouncementSend::getUserId, userIds[i]);
				List<SysAnnouncementSend> announcementSends=sysAnnouncementSendMapper.selectList(queryWrapper);
				if(announcementSends.size()<=0) {
					SysAnnouncementSend announcementSend = new SysAnnouncementSend();
					announcementSend.setAnntId(anntId);
					announcementSend.setUserId(userIds[i]);
					announcementSend.setReadFlag(CommonConstant.NO_READ_FLAG);
					announcementSend.setReadTime(refDate);
					sysAnnouncementSendMapper.insert(announcementSend);
				}
			}
			// 3. 删除多余通知用户数据
			Collection<String> delUserIds = Arrays.asList(userIds);
			LambdaQueryWrapper<SysAnnouncementSend> queryWrapper = new LambdaQueryWrapper<SysAnnouncementSend>();
			queryWrapper.notIn(SysAnnouncementSend::getUserId, delUserIds);
			queryWrapper.eq(SysAnnouncementSend::getAnntId, anntId);
			sysAnnouncementSendMapper.delete(queryWrapper);
		}
		return true;
	}

	// @功能：流程执行完成保存消息通知
	@Override
	public void saveSysAnnouncement(String title, String msgContent) {
		SysAnnouncement announcement = new SysAnnouncement();
		announcement.setTitile(title);
		announcement.setMsgContent(msgContent);
		announcement.setSender("JEECG BOOT");
		announcement.setPriority(CommonConstant.PRIORITY_L);
		announcement.setMsgType(CommonConstant.MSG_TYPE_ALL);
		announcement.setSendStatus(CommonConstant.HAS_SEND);
		announcement.setSendTime(new Date());
		announcement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
		sysAnnouncementMapper.insert(announcement);
	}

	@Override
	public Page<SysAnnouncement> querySysCementPageByUserId(Page<SysAnnouncement> page, String userId,String msgCategory) {
		 return page.setRecords(sysAnnouncementMapper.querySysCementListByUserId(page, userId, msgCategory));
	}

	@Override
	public FateCooperation querySysCementById(String id){
		return sysAnnouncementMapper.selectCooperationById(id);
	}

	@Override
	public void addFateProject(FateCooperation fateCooperation){
		if (Integer.parseInt(sysAnnouncementMapper.selectAgreeCountById(fateCooperation)) == 0){
			sysAnnouncementMapper.insertFateProject(fateCooperation);
		}
	}

	@Override
	public void agreeFateCooperation(FateCooperation fateCooperation,String announcementId) {
		// 获取合作计划发布人和合作计划名信息
		String createBy = fateCooperation.getCreateBy();
		String planName = fateCooperation.getPlanName();
		// 查询接收的信息
		SysAnnouncement announcement = sysAnnouncementMapper.selectSysAnnouncementById(announcementId);
		String create_by = sysMessageMapper.selectFirmNameById(announcement.getUserIds().substring(0, announcement.getUserIds().length() - 1));
		// 查询接收信息的用户id
		String senderId = sysAnnouncementMapper.selectSenderIdById(announcementId);
		// 编写请求消息模版
		SysAnnouncement sysAnnouncement = new SysAnnouncement();
		sysAnnouncement.setTitile("对方企业同意您的<" + planName + ">申请合作计划，请查阅！");
		sysAnnouncement.setMsgCategory("1");
		sysAnnouncement.setCreateBy(create_by);
		sysAnnouncement.setDelFlag("0");
		sysAnnouncement.setStartTime(new Date());
		sysAnnouncement.setMsgType("USER");
		sysAnnouncement.setMsgContent("<p>对方<" + create_by + ">企业同意与您的<" + planName + ">合作</p>");
		sysAnnouncement.setUserIds(senderId + ",");
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
					JSONObject obj = new JSONObject();
					obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_USER);
					obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
					obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
					webSocket.sendMessage(userIds, obj.toJSONString());
				}
			}
		}
	}

	@Override
	public void reduceAgreeCount(FateCooperation fateCooperation){
		sysAnnouncementMapper.updateAgreeCount(fateCooperation);
	}

	@Override
	public void setRunningStatus(String fateProjectId) {
		sysAnnouncementMapper.updateRunningStatus(fateProjectId);
	}

	@Override
	public void addFateRunProject(String id) {
		// 查询fate_cooperation
		FateCooperation cooperation = sysAnnouncementMapper.selectCooperationById(id);
		//雪花算法生成随机订单编号
		Snowflake snowflake = new Snowflake(5, 5);
		String snowId = snowflake.nextIdStr();
		sysAnnouncementMapper.insertRunProject(cooperation,snowId);
	}

}
