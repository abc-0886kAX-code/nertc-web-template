package com.ytxd.handel;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.protocol.PacketType;
import com.ytxd.config.Netty.SocketConstant;
import com.ytxd.config.Netty.SocketIOVo;
import com.ytxd.service.WebSocket.SocketIOServiceImpl;
import com.ytxd.util.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * @Description:
 * @ClassName SocketIo拦截器
 * @date: 2022-07-13 15:40
 * @Author: TY
 */
@Component
@Slf4j
@Order(9999999)
public class SocketIOHandler extends Observable {

    @Autowired
    private SocketIOServer socketIoServer;

    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     *
     * @throws Exception
     */
    @PostConstruct
    private void autoStartup() throws Exception {
        try {
            socketIoServer.start();
            SocketIOVo.init();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("SocketIOServer启动失败");
        }
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     *
     * @throws Exception
     */
    @PreDestroy
    private void autoStop() throws Exception {
        socketIoServer.stop();
    }

    /**
     * 客户端连接的时候触发
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("token");
        // 提醒观察者
        log.info("客户端:" + client.getRemoteAddress() + "  sessionId:" + client.getSessionId() + " username: " + token + "已连接");
        SocketIOServiceImpl.connectMap.put(client.getSessionId().toString(), client);
        System.out.println("链接时个数：" + socketIoServer.getAllClients().size());
        Boolean flag = SocketIOVo.addClient(client);
        if (!flag) {
//        socketIoServer.d
        } else {
//            if (SocketIOVo.hasInitiateClient(client)) {
            // 所有客户端信息并且发送
            EMIT_UPDATE_USER_LIST();
//            }
        }

    }

    /**
     * 客户端关闭连接时触发
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        log.info("客户端:" + client.getSessionId() + "断开连接");
        String token = client.getHandshakeData().getSingleUrlParam("token");
        SocketIOVo.deleteClient(client);
        // 提醒观察者
        this.setChanged();
        System.out.println("退出时个数" + socketIoServer.getAllClients().size());
        EMIT_UPDATE_USER_LIST();
    }

    @OnEvent("message")
    public void EventListener(SocketIOClient client, String data, AckRequest ackSender) {
        System.out.println("Received chat message: " + data);
    }

    /**
     * 通话邀请
     *
     * @param client
     * @param ackSender
     */
    @OnEvent(value = SocketConstant.EMIT_INVITE)
    public void EMIT_invite(SocketIOClient client, JSONObject obj, AckRequest ackSender) {
        String clientid = obj.getString("wait");
//        String clientid = "5277755A544233434F2B56647665647435645571762F695248354A517A46684D6334436C306564703248493D";
        if (StringUtil.isNotEmpty(clientid)) {
            String roomId = SocketIOVo.createRoom(client);
            SocketIOClient socketIOClient = SocketIOVo.getSocketIOClient(clientid);
            if(socketIOClient != null){
                obj.put("roomId", roomId);
                obj.put("msg", "邀请你加入通话");
                socketIOClient.sendEvent(SocketConstant.EMIT_INVITE, JSONObject.toJSON(obj));
            }
        }
    }

    /**
     * 接受邀请
     *
     * @param client
     * @param obj
     * @param ackSender
     */
    @OnEvent(value = SocketConstant.EMIT_INVITE_ACCEPT)
    public void EMIT_INVITE_ACCEPT(SocketIOClient client, JSONObject obj, AckRequest ackSender) {
        String roomId = obj.getString("roomId");
        SocketIOVo.addRoom(roomId, client);
        List<Map<Object, Object>> list = SocketIOVo.getRoomSocketIOClient(roomId);
        list.stream().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            SocketIOClient client1 = (SocketIOClient) item.get("client");
            map.put("id", SocketIOVo.getEncryRedisKey(client));
            map.put("msg", "有新用户加入");
            map.put("roomId",roomId);
            client1.sendEvent(SocketConstant.EMIT_INVITE_ACCEPT, map);
        });
        EMIT_UPDATE_USER_LIST();
    }

    /**
     * 拒绝邀请
     *
     * @param client
     * @param obj
     * @param ackSender
     */
    @OnEvent(value = SocketConstant.EMIT_INVITE_REJECT)
    public void EMIT_INVITE_REJECT(SocketIOClient client, JSONObject obj, AckRequest ackSender) {
        String roomId = obj.getString("roomId");
        SocketIOVo.UpdateRoomUser(roomId);
        List<Map<Object, Object>> list = SocketIOVo.getRoomSocketIOClient(roomId);
        SocketIOVo.removeRoom(roomId);
        list.stream().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            SocketIOClient client1 = (SocketIOClient) item.get("client");
            map.put("id", SocketIOVo.getEncryRedisKey(client));
            map.put("msg", "用户拒绝加入");
            map.put("roomId", roomId);
            client1.sendEvent(SocketConstant.EMIT_INVITE_REJECT, map);
        });
        EMIT_UPDATE_USER_LIST();
    }

    /**
     * 通信开始
     *
     * @param client
     * @param obj
     * @param ackSender
     */
    @OnEvent(value = SocketConstant.EMIT_SIGNAL_START)
    public void EMIT_SIGNAL_START(SocketIOClient client,  JSONObject obj, AckRequest ackSender) {
        String roomId = obj.getString("roomId");
        List<Map<Object, Object>> list = SocketIOVo.getRoomSocketIOClient(roomId);
        list.stream().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            SocketIOClient client1 = (SocketIOClient) item.get("client");
            map.put("msg", "通信开始");
            client1.sendEvent(SocketConstant.EMIT_SIGNAL_START, map);
        });
//        EMIT_UPDATE_USER_LIST();
    }

    /**
     * 通话结束
     *
     * @param client
     * @param obj
     * @param ackSender
     */
    @OnEvent(value = SocketConstant.EMIT_SIGNAL_END)
    public void EMIT_SIGNAL_END(SocketIOClient client, JSONObject obj, AckRequest ackSender) {
        String roomId = obj.getString("roomId");
        SocketIOVo.UpdateRoomUser(roomId);
        List<Map<Object, Object>> list = SocketIOVo.getRoomSocketIOClient(roomId);
        SocketIOVo.removeRoom(roomId);
        list.stream().forEach(item -> {
            String rediskey = SocketIOVo.getEncryRedisKey(client);
            String clientid = (String) item.get("id");
            if (!Objects.equals(rediskey, clientid)) {
                Map<String, Object> map = new HashMap<>();
                SocketIOClient client1 = (SocketIOClient) item.get("client");
                map.put("msg", "通信结束");
                map.put("roomId", roomId);
                client1.sendEvent(SocketConstant.EMIT_SIGNAL_END, map);
            }
        });
        EMIT_UPDATE_USER_LIST();
    }

    /**
     * 发送用户信息列表
     */
    public void EMIT_UPDATE_USER_LIST() {
        // 向所有的发起者发送消息
        List<Map<Object, Object>> list = SocketIOVo.getInitiateClientList();
        List<Map<Object, Object>> receiveList = SocketIOVo.getReceiveClientList();
        List<Map<Object, Object>> dataList = new ArrayList<>();
        receiveList.stream().forEach(item -> {
            Map<Object, Object> map = new HashMap<>();
            for(Object key:item.keySet()){
                if(key != null && !Objects.equals(key.toString(),"client")){
                    map.put(key,item.get(key));
                }
            }
            dataList.add(map);
        });
        list.stream().forEach(item -> {
            Object obj = item.get("client");
            if (obj != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("user", dataList);
                map.put("userSize", dataList.size());
                map.put("count", SocketIOVo.getClientCount());
                SocketIOClient client = (SocketIOClient) item.get("client");
                client.sendEvent(SocketConstant.EMIT_UPDATE_USER_LIST, JSONObject.toJSON(map));
            }
        });
    }


    public static void main(String[] args) {
//        SpringContextUtils.getBean()
        System.out.println("1");
    }
}


