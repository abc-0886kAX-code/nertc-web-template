package com.ytxd.service.WebSocket;

import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.Observer;

/**
 * @Description:
 * @ClassName TestSocketIOService
 * @date: 2021.09.09 14:07
 * @Author: TY
 */
public interface SocketIOService extends Observer, InitializingBean {

    /**
     * description: 给容器内所有的客户端发送通知
     * date: 2022-07-13 17:20
     * author: TY
     *
     * @param msg
     * @return void
     */
    void sendMessageToAllUser(Map<String,Object> msg);

    /**
     * description: 给指定用户发送通知
     * date: 2022-07-13 17:20
     * author: TY
     *
     * @param username
     * @param msg
     * @return void
     */
    void sendMessage(String username, Map<String,Object> msg);

    // 链接 断开 心跳 重连 消息通道

}


