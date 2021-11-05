package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/8/8 11:18
 */

@Data
public class Message {
    /**发送者id*/
    private String senderId;
    /**接收者id*/
    private String receiverId;
    /**发送文本内容*/
    private String sendText;
    /**发送二进制内容*/
    private byte[] sendByte;
    /**发送时间*/
    private Date sendTime;
    /**消息类型*/
    private String msgType;


}
