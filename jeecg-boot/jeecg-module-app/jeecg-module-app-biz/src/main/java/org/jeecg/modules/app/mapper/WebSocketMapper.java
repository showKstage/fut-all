package org.jeecg.modules.app.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.vo.Message;
import java.util.List;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/8/9 10:57
 */
public interface WebSocketMapper {
    int insertRecord(Message message);
    List<Message> getChatRecord(@Param("userId")String userId,@Param("teacherId")String teacherId);
}
