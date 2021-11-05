package org.jeecg.modules.app.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/8/7 21:38
 */
@ServerEndpoint("/app/webSocketServer/{userId}")
@Component
public class WebSocketServerService {

    static Log log= LogFactory.getLog(WebSocketServerService.class);
    /**用来存放每个客户端对应的WebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServerService> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";

    /**连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId=userId;
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
            //加入set中
        }else{
            webSocketMap.put(userId,this);
        }
        try {
            sendText("连接成功");
        } catch (IOException e) {
            log.error("用户:"+userId+",网络异常!!!!!!");
        }
    }

    /**连接关闭调用的方法*/
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
        }
        log.info("用户退出:"+userId);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }

    public void sendText(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendBinary(ByteBuffer byteBuffer) throws IOException {
        this.session.getBasicRemote().sendBinary(byteBuffer);
    }

    /**发送文本消息*/
    public static void sendTextInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:"+userId+"，内容:"+message);
        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendText(message);
        }else{
            log.error("用户"+userId+",不在线！");
        }
    }

    /**发送二进制文件*/
    public static void sendBinaryInfo(ByteBuffer byteBuffer,@PathParam("userId") String userId) throws IOException {
        log.info("发送二进制文件到:"+userId);
        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendBinary(byteBuffer);
        }else{
            log.error("用户"+userId+",不在线！");
        }
    }

}
