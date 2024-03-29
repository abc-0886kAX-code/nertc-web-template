package com.ytxd.config.Netty;

import com.corundumstudio.socketio.SocketIOClient;
import com.ytxd.util.AESUtil;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体类
 */
public class SocketIOVo {
    // 用户前缀
    private static String userKey = "SocketUser:";
    // 房间前缀
    private static String roomKey = "room:";
    // 以下为map所需要
    // 客户端key
    private static String clientKey = "client";
    // 是否忙碌key
    private static String rushKey = "busy";
    // 是否为发起者
    private static String initiateKey = "initiate";
    // 所属房间
    private static String room = "roomId";
    // 判断哪个平台可以创建房间
    public static HashMap<String, Boolean> User_AgentMap = new HashMap<>();
    // 所有的客户端
    public static ConcurrentHashMap<String, SocketIOClient> ClientMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Map<Object,Object>> ClientInfoMap = new ConcurrentHashMap<>();

    // 房间Map
    public static HashMap<String, Set<String>> roomMap = new HashMap<>();
    public static void init(){
        User_AgentMap.put("Macintosh", true);
        User_AgentMap.put("Windows", false);
        User_AgentMap.put("iPhone", true);
        User_AgentMap.put("Android", true);
        User_AgentMap.put("wechatdevtools", true);
    }

    /**
     * 添加客户端
     *
     * @param client
     */
    public static Boolean addClient(SocketIOClient client) {
//        String User_Agent = client.getHandshakeData().getHttpHeaders().get("User-Agent");
        String User_Agent = client.getHandshakeData().getSingleUrlParam("type");
        String address = client.getRemoteAddress().toString();
        // 判断是否可以创建房间
//        for (String key : User_AgentMap.keySet()) {
//            Boolean flag = User_AgentMap.get(key);
//            if (User_Agent.matches("(.*)" + key + "(.*)")) {
//            if (Objects.equals("Servlet",User_Agent)) {
                Map<Object, Object> map = new HashMap<>();
                map.put(rushKey, false);
                map.put(clientKey, client);
                // 清除客户端
                deleteClient(client);
//                if (flag) {
        if (Objects.equals("Servlet",User_Agent)) {
                    map.put(initiateKey, true);
                } else {
                    map.put(initiateKey, false);
                }
                ClientInfoMap.put(userKey + address,map);
//                RedisUtils.set(userKey + address, JSONObject.toJSONString(map));
                ClientMap.put(userKey + address,client);
               return true;
//            }
//        }
//        return false;
    }

    /**
     * 判断是否是发起者
     *
     * @param client
     * @return
     */
    public static Boolean hasInitiateClient(SocketIOClient client) {
        String address = client.getRemoteAddress().toString();
        Map<Object, Object> dataMap = getRedisMap(userKey+address);
        return dataMap.get(initiateKey) == null ? false : (Boolean) dataMap.get(initiateKey);
    }

    /**
     * 删除客户端
     *
     * @param client
     */
    public static void deleteClient(SocketIOClient client) {
        String address = client.getRemoteAddress().toString();
//        RedisUtils.delete(userKey + address);
        ClientInfoMap.remove(userKey + address);
        ClientMap.remove(userKey + address);
    }

    /**
     * 获取所有被动接受者的信息
     *
     * @return
     */
    public static List<Map<Object, Object>> getReceiveClientList() {
//        Set<String> set = RedisUtils.keys(userKey);
        Set<String> set = getMaoKeys(userKey);
        if (set != null) {
            List<Map<Object, Object>> data = new ArrayList<>();
            for (String key : set) {
                Map<Object, Object> dataMap = getRedisMap(key);
                dataMap.put("id", AESUtil.encrypt(key));
//                dataMap.remove(clientKey);
                if (dataMap.get(initiateKey) != null && !(Boolean) dataMap.get(initiateKey)) {
                    data.add(dataMap);
                }
            }
            return data;
        }
        return null;
    }

    /**
     * 获取所有的发起者
     * @return
     */
    public static List<Map<Object, Object>> getInitiateClientList() {
//        Set<String> set = RedisUtils.keys(userKey);
        Set<String> set = getMaoKeys(userKey);
        if (set != null) {
            List<Map<Object, Object>> data = new ArrayList<>();
            for (String key : set) {
                Map<Object, Object> dataMap = getRedisMap(key);
                dataMap.put("id", AESUtil.encrypt(key));
                if (dataMap.get(initiateKey) != null && (Boolean) dataMap.get(initiateKey)) {
                    data.add(dataMap);
                }
            }
            return data;
        }
        return null;
    }

    /**
     * 获取一个客户端
     *
     * @param clientid
     * @return
     */
    public static SocketIOClient getSocketIOClient(String clientid) {
        Map<Object, Object> dataMap = getRedisMap(AESUtil.decode(clientid));
        if (dataMap != null && dataMap.size() > 0) {
            return (SocketIOClient) dataMap.get(clientKey);
        }
        return null;
    }

    /**
     * 创建一个房间
     */
    public static String createRoom(SocketIOClient client) {
        String roomID = roomKey + UUID.randomUUID().toString().replaceAll("-", "");
        String redisKey = getRedisKey(client);
        Map<Object, Object> dataMap = getRedisMap(redisKey);
        dataMap.put(room, roomID);
        dataMap.put(rushKey, true);
//        RedisUtils.setMap(redisKey, dataMap);
        ClientInfoMap.put(redisKey,dataMap);
        Set<String> roomSet = roomMap.get(roomID);
        if (roomSet == null) {
            roomSet = new HashSet<>();
        }
        roomSet.add(redisKey);
        roomMap.put(roomID, roomSet);
        return roomID;
    }

    /**
     * 加入一个房间
     */
    public static String addRoom(String roomID, SocketIOClient client) {
        String redisKey = getRedisKey(client);
        Map<Object, Object> dataMap = getRedisMap(redisKey);
        dataMap.put(room, roomID);
        dataMap.put(rushKey, true);
//        RedisUtils.set(redisKey, JSONObject.toJSONString(dataMap));
        ClientInfoMap.put(redisKey,dataMap);
        Set<String> roomSet = roomMap.get(roomID);
        if (roomSet == null) {
            roomSet = new HashSet<>();
        }
        roomSet.add(redisKey);
        roomMap.put(roomID, roomSet);
        return roomID;
    }

    /**
     * 获取房间内的用户
     */
    public static List<Map<Object, Object>> getRoomSocketIOClient(String roomID) {
        Set<String> roomSet = roomMap.get(roomID);
        if (roomSet != null) {
            List<Map<Object, Object>> data = new ArrayList<>();
            for (String key : roomSet) {
                Map<Object, Object> dataMap = getRedisMap(key);
                dataMap.put("id", AESUtil.encrypt(key));
                data.add(dataMap);
            }
            return data;
        }
        return null;
    }

    /**
     * 获取rediskey 不加密的
     *
     * @param client
     * @return
     */
    public static String getRedisKey(SocketIOClient client) {
        return userKey + client.getRemoteAddress().toString();
    }

    /**
     * 获取rediskey 加密的
     *
     * @param client
     * @return
     */
    public static String getEncryRedisKey(SocketIOClient client) {
        return AESUtil.encrypt(getRedisKey(client));
    }

    /**
     * 修改房间内用户状态
     * @param roomID
     */
    public static void UpdateRoomUser(String roomID) {
        Set<String> roomSet = roomMap.get(roomID);
        if (roomSet != null) {
            for (String key : roomSet) {
                Map<Object, Object> dataMap = getRedisMap(key);
                dataMap.put(room, "");
                dataMap.put(rushKey, false);
//                RedisUtils.set(key, JSONObject.toJSONString(dataMap));
                ClientInfoMap.put(key,dataMap);
            }
        }
    }
    /**
     * 移除房间，并且修改用户状态
     * @param roomID
     */
    public static void removeRoom(String roomID) {
        roomMap.remove(roomID);
    }

    /**
     * 获取Map
     * @param key
     * @return
     */
    public static Map<Object,Object> getRedisMap(String key){
//        ClientInfoMap.get(key).put(clientKey,ClientMap.get(key));
        Map<Object,Object> cleMAp = ClientInfoMap.get(key);
        HashMap<Object,Object> map = new HashMap<>();
        if(cleMAp != null){
            map.putAll(cleMAp);
        }
//        Map<Object,Object> map = JSONObject.parseObject(RedisUtils.get(key),HashMap.class);
//        map.put(clientKey,ClientMap.get(key));
        return map;
    }

    public static Set<String> getMaoKeys(String prix){
        Set<String> set = new TreeSet<>();
        for(String key:ClientInfoMap.keySet()){
            if(key.matches(prix+"(.*)")){
                set.add(key);
            }
        }
        return set;
    }
    public static Integer getClientCount(){
        return ClientInfoMap.size();
    }
}
