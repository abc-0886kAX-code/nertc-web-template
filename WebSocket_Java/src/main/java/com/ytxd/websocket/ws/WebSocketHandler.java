package com.ytxd.websocket.ws;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytxd.util.RedisUtils;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import com.google.gson.Gson;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
@Slf4j
@ServerEndpoint("/websocket")
public class WebSocketHandler extends AbstractWebSocketHandler {
    private static CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();

    /**
     * ws上线
     * 把自身id注册到redis
     * 把自身sesion
     * 返回在线列表
     * */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        //记录session
        sessions.add(session);
        log.info("WebSocket连接已建立，当前连接数：{}", sessions.size());
        //记录客户端id
        String clientId = session.getId();
        log.info("客户端连接标识符：" + clientId);
        //预注册
        HashMap cc = new HashMap<Object,Object>();
        cc.put("clientId",clientId);


        RedisUtils.setMap("ws:"+clientId,cc);

        Gson gson = new Gson();
        //组装返回列表
        Map<String, Object> data1 = new HashMap<>();
        data1.put("clientId",clientId);
        //返回当前登陆用户id和在线用户列表
        WsMessage wm = new WsMessage();
        wm.setTP("onOpen");
        wm.setData(data1);
        wm.setDataType("data");
        String json = gson.toJson(wm);
        log.info("返回消息："+json);
        session.getBasicRemote().sendText(json);
    }

    /**
     * ws离线
     * 从redis移除ws
     * 从session中移除ws
     * 发挥在线列表
     * */
    @OnClose
    public void onClose(Session session) {
        String clientId = session.getId();
        //处理session缓存
        sessions.remove(session);
        //处理redis缓存
        RedisUtils.delete("ws:"+clientId);
        log.info("WebSocket连接已关闭，当前连接数：{}", sessions.size());
        //刷新其他用户的在线列表
        //获取在线用户列表
        List<Map<Object, Object>> userlist = new ArrayList<>();
        for (Session userSession : sessions) {
            userlist.add(RedisUtils.getMap("ws:"+userSession.getId()));
        }

        Gson gson = new Gson();
        WsMessage wm = new WsMessage();
        wm.setTP("userlist");
        wm.setList(userlist);
        wm.setDataType("list");
        String json = gson.toJson(wm);
        for (Session userSession : sessions) {
            try {
                log.info("返回消息："+json);
                userSession.getBasicRemote().sendText(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        log.info("收到客户端消息：{}", message);
        // 处理消息逻辑
        String clientId = session.getId(); //记录客户端id
        Gson gson = new Gson();
            WsMessage wsMessage = gson.fromJson(message, WsMessage.class);
        String msg = "";
        switch (wsMessage.getTP())
        {
            case "register":
                register(clientId,wsMessage.getdData());
                break;
            case "call":
                Map<String,Object> mp = call(clientId,wsMessage.getdData());
                //通知被叫用户生成的房间号
                for (Session userSession : sessions) {
                    if(mp.get("clientId").equals(userSession.getId())) {
                        try {
                            //String roomJson = gson.toJson(mp);
                            WsMessage wm = new WsMessage();
                            wm.setTP("callto");
                            wm.setData(mp);
                            wm.setDataType("data");
                            String json = gson.toJson(wm);
                            log.info("返回消息："+json);
                            userSession.getBasicRemote().sendText(json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //通知主叫用户生成的房间号
                Map<String,Object> mp2 = new HashMap<>();
                mp2.put("roomId",mp.get("roomId"));
                mp2.put("type","0");
                mp2.put("clientId",clientId);
                WsMessage wm2 = new WsMessage();
                wm2.setTP("call");
                wm2.setData(mp2);
                wm2.setDataType("data");
                String json = gson.toJson(wm2);
                log.info("返回消息："+json);
                session.getBasicRemote().sendText(String.valueOf(json));
                break;
            case "Hangup":
                Hangup(clientId,wsMessage.getdData());
                break;
            case "userlist":
                userlist(clientId,wsMessage.getdData());
                break;
            case "online":
                online(clientId,wsMessage.getdData());
                break;
            case "Join":
                Join(clientId,wsMessage.getdData());
                break;
            case "roomList":
                roomList(clientId,wsMessage.getdData());
                break;
        }

    }

    /**
     * 查询用户所在的房间情况
     * */
    private void roomList(String clientId, String data) {
        // 创建 Gson 对象
        Gson gson = new Gson();
        // 将 JSON 数据解析成对象
        Room rooms = gson.fromJson(data, Room.class);
        String json = RedisUtils.get(rooms.getdRoomId());
        for (Session userSession : sessions) {
            try {
                if(clientId.equals(userSession.getId())) {
                    log.info("返回消息："+json);
                    userSession.getBasicRemote().sendText(json);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 建立连接之后把自身信息注册到服务器
     * */
    public void register(String clientId,String data) throws Exception {
        // 创建 Gson 对象
        Gson gson = new Gson();
        // 将 JSON 数据解析成对象
        Users user = gson.fromJson(data, Users.class);
        Map<Object,Object> mp = RedisUtils.getMap("ws:"+clientId);
        mp.put("userId",user.getUserId());
        mp.put("userName",user.getUserName());
        mp.put("clientId",user.getClientId());
        mp.put("token",getToken((long)Double.parseDouble(user.getUserId())));
        Map<String, Object> newMap = new HashMap<>();
        // 遍历原始的 Map<Object, Object>
        for (Map.Entry<Object, Object> entry : mp.entrySet()) {
            // 将键转换为 String 类型
            String key = entry.getKey().toString();
            // 将值保持不变
            Object value = entry.getValue();
            // 将键值对放入新的 Map<String, Object> 中
            newMap.put(key, value);
        }
        RedisUtils.setMap("ws:"+clientId,newMap);

        //获取在线用户列表
        List<Map<Object, Object>> userlist = new ArrayList<>();
        for (Session userSession : sessions) {
            userlist.add(RedisUtils.getMap("ws:"+userSession.getId()));
        }

        WsMessage wm = new WsMessage();
        wm.setTP("userlist");
        wm.setList(userlist);
        wm.setDataType("list");
        String json = gson.toJson(wm);
        for (Session userSession : sessions) {
            try {
                log.info("返回消息："+json);
                userSession.getBasicRemote().sendText(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 维持在线心跳包
     * */
    public void online(String clientId,String data){

    }

    /**
     * 呼叫用户
     * 返回呼叫用户的id和房间号
     * */
    public Map<String,Object> call(String clientId,String data){
        //json格式化
        Gson gson = new Gson();
        Users user = gson.fromJson(data, Users.class);

        //获取到目标用户的ws客户端id
        String callclientId = user.getClientId();

        //生成房间号
        UUID roomId = UUID.randomUUID();

        //被叫用户
        Map<String,Object> rooms = new HashMap<>();
        rooms.put("clientId",callclientId);
        rooms.put("roomId",roomId);
        rooms.put("type","1");

        //主叫用户
        Map<String,Object> roomscall = new HashMap<>();
        roomscall.put("clientId",clientId);
        roomscall.put("roomId",roomId);
        roomscall.put("type","0");

        //把房间信息存入redis
        List<Map<String,Object>> roomList = new ArrayList<>();
        roomList.add(rooms);
        roomList.add(roomscall);
        RedisUtils.set(roomId.toString(),gson.toJson(roomList));

        return rooms;
    }

    /**
     * 挂断通话
     * 接收挂断用户和需要挂断的房间，判断房间是否存在其他人员，如果只有一人则通知该用户挂断
     * */
    public void Hangup(String clientId,String data) throws IOException {
        //json格式化
        Gson gson = new Gson();
        Room rm = gson.fromJson(data, Room.class);
        String roomId = rm.getdRoomId();
        try {
            //找到房间中的用户
            String json = RedisUtils.get(roomId);
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Map.class);
            List<Map<Object, Object>> list = objectMapper.readValue(objectMapper.readTree(json).toString(), javaType);

            //Type listType = new com.google.gson.reflect.TypeToken<List<Room>>(){}.getType();
            //List<Room> roomList = gson.fromJson(json,listType);
            if (list.size() == 2) {
                //通话模式
                for (Map<Object, Object> room : list) {
                    //遍历房间中的人，通知离线。
                    String clientIds = room.get("clientId").toString();
                    for (Session userSession : sessions) {
                        if (clientIds.equals(userSession.getId())) {
                            try {
                                WsMessage wm = new WsMessage();
                                wm.setTP("Hangup");
                                String Hangupjson = gson.toJson(wm);
                                log.info("返回消息："+Hangupjson);
                                userSession.getBasicRemote().sendText(Hangupjson);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                //移除redis
                RedisUtils.delete(roomId);
            } else {
                //会议模式
                List<Map<Object, Object>> listnew = new ArrayList<>(list);
                for (Map<Object, Object> room : list) {
                    //遍历房间中的人，移除离线用户。
                    if (clientId.equals(room.get("clientId").toString())) {
                        listnew.remove(room);
                        RedisUtils.set(roomId, gson.toJson(listnew));
                    }
                }
                if (listnew.size() > 1) {
                    //如果还有2人以上，则维持通话，重新返回room的在线列表
                    //String roomJson =gson.toJson(roomList);
                    WsMessage wm = new WsMessage();
                    wm.setTP("RoomList");
                    wm.setList(list);
                    wm.setDataType("list");
                    String Hangupjson = gson.toJson(wm);
                    for (Map<Object, Object> room : list) {
                        for (Session userSession : sessions) {
                            if (room.get("clientId").toString().equals(userSession.getId())) {
                                try {
                                    log.info("返回消息："+Hangupjson);
                                    userSession.getBasicRemote().sendText(Hangupjson);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            /*else
            {
                //只有1人清理房间
                for (Map<Object,Object> room : list) {
                    String clientIds = room.get("clientId").toString();
                    for (Session userSession : sessions) {
                        if (clientIds.equals(userSession.getId())) {
                            try {
                                WsMessage wm = new WsMessage();
                                wm.setTP("Hangup");
                                String Hangupjson = gson.toJson(wm);
                                userSession.getBasicRemote().sendText(Hangupjson);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }*/
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * 多方通讯
     * */
    public void Join(String clientId,String data) throws IOException {
        //json格式化
        Gson gson = new Gson();
        Room rm = gson.fromJson(data, Room.class);
        String roomId = rm.getdRoomId();
        String clientIdcall = rm.getClientId();
        //获取房间信息。
        String json = RedisUtils.get(roomId);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Map.class);
        List<Map<Object, Object>> list = objectMapper.readValue(objectMapper.readTree(json).toString(), javaType);
        //将被叫用户加入房间。
        Map<Object, Object> toUser = new HashMap<>();
        toUser.put("clientId",clientIdcall);
        toUser.put("type","1");
        toUser.put("roomId",roomId);
        list.add(toUser);
        RedisUtils.set(roomId,gson.toJson(list));
        //返回信息。
        for (Session userSession : sessions) {
            //返回被叫用户信息
            if (clientIdcall.equals(userSession.getId())) {
                try {
                    WsMessage wm = new WsMessage();
                    wm.setTP("callto");
                    Map<String , Object> mp = new HashMap<>();
                    mp.put("clientId",clientIdcall);
                    mp.put("type","1");
                    mp.put("roomId",roomId);
                    wm.setData(mp);
                    wm.setDataType("data");
                    json = gson.toJson(wm);
                    log.info("返回消息："+json);
                    userSession.getBasicRemote().sendText(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //被动刷新房间信息？

        }
    }

    /**
     * 用户列表
     * */
    public void userlist(String clientId,String data){
        //刷新其他用户的在线列表
        //获取在线用户列表
        List<Map<Object, Object>> userlist = new ArrayList<>();
        for (Session userSession : sessions) {
            userlist.add(RedisUtils.getMap("ws:"+userSession.getId()));
        }
        Gson gson = new Gson();
        String json = gson.toJson(userlist);
        //遍历在线用户，根据登陆用户的id，对指定用户返回用户列表
        for (Session userSession : sessions) {
            if(userSession.getId().equals(clientId)) {
                try {
                    log.info("返回消息："+json);
                    userSession.getBasicRemote().sendText(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //根据连接信息获取token
    private String getToken(long uid) throws Exception {
        long curTimeMs = System.currentTimeMillis();
        return getTokenWithCurrentTime(null, uid, 86400, curTimeMs);
    }

    private String getTokenWithCurrentTime(String channelName, long uid, int ttlSec, long curTimeMs) throws Exception {
        String appKey = "9d808098269de56dccb0b3b70cb6a5d3";
        String appSecret = "40812ba7a0a1";
        DynamicToken tokenModel = new DynamicToken();
        //生成 signature，将 appkey、uid、curTime、ttl、appsecret 五个字段拼成一个字符串，进行 sha1 编码
        tokenModel.signature = sha1(String.format("%s%d%d%d%s%s", appKey, uid, curTimeMs, ttlSec, channelName, appSecret));
        tokenModel.curTime = curTimeMs;     //获取当前时间戳，单位为毫秒
        tokenModel.ttl = ttlSec;      //设置Token的过期时间，单位为秒
        ObjectMapper objectMapper = new ObjectMapper();
        String signature = objectMapper.writeValueAsString(tokenModel);
        return Base64.getEncoder().encodeToString(signature.getBytes(StandardCharsets.UTF_8));   // 对JSON字符串进行Base64编码，返回生成的Token字符串
    }

    private String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
        byte[] result = mDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static class DynamicToken {
        public String signature;
        public long curTime;
        public int ttl;
    }



    static class WsMessage {
        String tp;
        Map<String,Object> data;
        String dataType;
        List<Map<Object,Object>> list;

        public String getTP(){
            return tp;
        }

        public String getdData(){
            Gson gson = new Gson();
            return gson.toJson(data);
        }

        public void setTP(String tp){
            this.tp = tp;
        }

        public void setData(Map<String,Object> data){
            this.data = data;
        }

        public void setDataType(String dataType){
            this.dataType = dataType;
        }

        public String getDataType(){
            return this.dataType;
        }

        public List<Map<Object,Object>> getList(){
            return this.list;
        }

        public void setList(List<Map<Object,Object>> list){
            this.list = list;
        }
    }

    static class Users{
        String userName;
        String userId;
        String clientId;
        public String getUserName(){
            return userName;
        }

        public String getUserId(){
            return userId;
        }
        public String getClientId(){
            return clientId;
        }
    }

    static class Room {
        String clientId;
        String roomId;
        String type;

        public String getClientId(){
            return clientId;
        }

        public String getdRoomId(){
            return roomId;
        }

        public String getType(){
            return type;
        }
    }
}
