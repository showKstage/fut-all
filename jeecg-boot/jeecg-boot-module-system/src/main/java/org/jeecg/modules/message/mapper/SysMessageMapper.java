package org.jeecg.modules.message.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.message.entity.SysMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.system.entity.SysAnnouncement;
import org.springframework.stereotype.Component;

/**
 * @Description: 消息
 * @Author: jeecg-boot
 * @Date:  2019-04-09
 * @Version: V1.0
 */
@Component
public interface SysMessageMapper extends BaseMapper<SysMessage> {

    /** 根据企业名查询对用企业用户id */
    String selectFirmIdByFirmName(@Param("cooperation") String cooperation);

    /** 根据企业名查询官网 */
    String selectWebsiteByCreateBy(@Param("createBy") String createBy);

    /** 根据企业id查询企业名称 */
    String selectFirmNameById(@Param("id") String id);

    /** 根据实体查询消息模版id */
    String selectSmsIdByAnnouncement(@Param("announcement") SysAnnouncement announcement);
}
