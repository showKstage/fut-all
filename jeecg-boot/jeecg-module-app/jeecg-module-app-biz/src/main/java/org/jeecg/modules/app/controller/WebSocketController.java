package org.jeecg.modules.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.app.mapper.WebSocketMapper;
import org.jeecg.modules.app.service.WebSocketServerService;
import org.jeecg.modules.app.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/8/8 10:28
 */
@Api(tags="WebSocket")
@RestController
@RequestMapping("/app/webSocket/")
@Slf4j
public class WebSocketController {

    @Autowired
    private WebSocketMapper webSocketMapper;

    @AutoLog(value = "发送消息")
    @ApiOperation(value = "发送消息", notes = "发送消息")
    @PostMapping(value = "/send")
    public Result<?> send(@RequestBody() Message message) throws IOException {
        if (message.getMsgType().equals("0"))WebSocketServerService.sendTextInfo(message.getSendText(),message.getReceiverId());
        else WebSocketServerService.sendBinaryInfo(ByteBuffer.wrap(message.getSendByte()),message.getReceiverId());
        webSocketMapper.insertRecord(message);
        return Result.OK("MSG SEND SUCCESS");
    }

    @AutoLog(value = "获取消息记录")
    @ApiOperation(value = "获取消息记录", notes = "获取消息记录")
    @PostMapping(value = "/getChatRecord")
    public Result<?> getChatRecord(@RequestParam(name = "userId") String userId,@RequestParam(name = "teacherId") String teacherId) throws IOException {
        return Result.OK(webSocketMapper.getChatRecord(userId, teacherId));
    }
}
