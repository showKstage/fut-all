package org.jeecg.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.system.entity.FateCooperation;
import org.jeecg.modules.system.entity.SysAnnouncement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 系统通告表
 * @Author: jeecg-boot
 * @Date:  2019-01-02
 * @Version: V1.0
 */
public interface SysAnnouncementMapper extends BaseMapper<SysAnnouncement> {

	List<SysAnnouncement> querySysCementListByUserId(Page<SysAnnouncement> page, @Param("userId")String userId,@Param("msgCategory")String msgCategory);

	/** 根据id查询企业合作计划信息 */
	FateCooperation selectCooperationById(@Param("id") String id);

	/** 根据id查询企业合作通告数据 */
	SysAnnouncement selectSysAnnouncementById(@Param("id") String id);

	/** 根据id查询企业合作sender的id */
	String selectSenderIdById(@Param("id") String id);

	/** 查询fate_cooperation表的agree_count值 */
	String selectAgreeCountById(@Param("fateCooperation") FateCooperation fateCooperation);

	/** 插入fate_project信息 */
	void insertFateProject(@Param("fateCooperation") FateCooperation fateCooperation);

	/** 插入fate_run_project信息 */
	void insertRunProject(@Param("fateCooperation") FateCooperation fateCooperation,
						  @Param("id") String id);

	/** 修改status状态为执行中 */
	void updateRunningStatus(@Param("fateProjectId") String fateProjectId);

	/** 减少fate_cooperation表的agree_count值 */
	void updateAgreeCount(@Param("fateCooperation") FateCooperation fateCooperation);
}
