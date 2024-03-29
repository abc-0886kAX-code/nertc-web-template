package com.ytxd.service.WebSocket;

import com.corundumstudio.socketio.SocketIOClient;
import com.ytxd.handel.SocketIOHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description:
 * @ClassName SocketIOServiceImpl
 * @date: 022-07-13 17:20
 * @Author: TY
 */
@Service
@Slf4j
public class SocketIOServiceImpl implements SocketIOService {

    // 使用ConcurrentMap 存储客户端
    public static ConcurrentMap<String, SocketIOClient> connectMap = new ConcurrentHashMap<>();
    // 发送消息的通道
    public final static String SEND_CHANNEL = "message";

    @Autowired
    private SocketIOHandler socketIOHandler;

    /**
     * description: 给容器内所有的客户端发送通知
     * date: 2022-07-13 17:20
     * author: TY
     *
     * @param msg
     * @return void
     */
    @Override
    public void sendMessageToAllUser(Map<String, Object> msg) {
        if (connectMap.isEmpty()){
            return;
        }
        connectMap.entrySet().forEach(entry -> {
            entry.getValue().sendEvent(SEND_CHANNEL,msg);
        });
    }

    /**
     * description: 给指定用户发送通知
     * date: 2022-07-13 17:20
     * author: TY
     *
     * @param username
     * @param msg
     * @return void
     */
    @Override
    public void sendMessage(String username, Map<String, Object> msg) {
        SocketIOClient socketClient = getSocketClientByUsername(username);
        if ( null != socketClient){
            socketClient.sendEvent(SEND_CHANNEL,msg);
        }
    }

    /**
     * description: 根据用户找到对应的客户端
     * date: 2022-07-13 17:20
     * author: TY
     *
     * @param
     * @return com.corundumstudio.socketio.SocketIOClient
     */
    public SocketIOClient getSocketClientByUsername(String username){
        SocketIOClient client = null;
        if (null == username){
            return client;
        }
        if (connectMap.isEmpty()){
            return client;
        }
        for (String key : connectMap.keySet()) {
            if (username.equals(key)){
                client = connectMap.get(key);
            }
        }
        return client;
    }

    /**
     * description: 观察者模式中的通知
     * date: 2022-07-13 17:20
     * author: TY
     *
     * @param o
     * @param arg
     * @return void
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof SocketIOHandler)) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        if (arg instanceof  Map){
            map = (Map<String, Object>) arg;
        }
        log.info("客户端接收到通知："+map.toString());
        Object type = map.get("type");
        if (null == type) {
            return;
        }
        if (type.equals("disconnect")) { // 断开连接
            this.disconnect(map);
        }
    }

    /**
     * description: 断开连接
     * date: 2022-07-13 17:20
     * author: TY
     *
     * @param map
     * @return void
     */
    private void disconnect(Map<String, Object> map) {
        Object token = map.get("sessionId");
        if(null == token ){
            return;
        }
        connectMap.remove(token);
    }

    /**
     * description: 注册进观察者模式
     * date: 2022-07-13 17:20
     * author: TY
     *
     * @param
     * @return void
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // spring 为bean提供了两种初始化Bean的方法，1，在配置文件中指定init-metho方法。2，实现InitializingBean接口，实现afterPropertiesSet()方法
        // 只要实现了 InitializingBean 接口，Spring 就会在类初始化时自动调用该afterPropertiesSet()方法

        // 将当前对象注册进观察者模式中
        socketIOHandler.addObserver(this);
    }
}

