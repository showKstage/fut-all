package org.jeecg.modules.system.service;

import org.jeecg.modules.system.entity.FateCooperation;
import org.jeecg.modules.system.entity.SysAnnouncement;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 系统通告表
 * @Author: jeecg-boot
 * @Date:  2019-01-02
 * @Version: V1.0
 */
public interface ISysAnnouncementService extends IService<SysAnnouncement> {

	public void saveAnnouncement(SysAnnouncement sysAnnouncement);

	public boolean upDateAnnouncement(SysAnnouncement sysAnnouncement);

	public void saveSysAnnouncement(String title, String msgContent);

	public Page<SysAnnouncement> querySysCementPageByUserId(Page<SysAnnouncement> page,String userId,String msgCategory);

	FateCooperation querySysCementById(String id);

	void addFateProject(FateCooperation fateCooperation);

	/** 合作企业申请接收方同意合作 */
	void agreeFateCooperation(FateCooperation fateCooperation,String announcementId);

	/** 企业同意合作时减少fate_cooperation表中的agree_count字段 为0插入执行计划表 */
	void reduceAgreeCount(FateCooperation fateCooperation);

	/** 修改status状态为执行中 */
	void setRunningStatus(String fateProjectId);

	/** 根据cooperationId查询并插入fate_run_project记录 */
	void addFateRunProject(String id);
}
