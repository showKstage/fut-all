package org.jeecg.modules.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FateCooperation {

    private String id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String sysOrgCode;

    private String planName;

    private String cooperation;

    private String marketingSystem;

    private String algorithmSelection;

    private String tradingStraregy;

}
