package com.ytxd.controller.WebSocket;

import com.ytxd.common.DataUtils;
import com.ytxd.service.WebSocket.SocketIOService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController("SocketIOController")
@RequestMapping("/SocketIOController")
public class SocketIOController {
    @Resource
    private SocketIOService service;
    @PostMapping("/sendMessage")
    public void sendMessage(HttpServletRequest request){
        Map<String,Object> map = DataUtils.getParameterMap(request);
        service.sendMessageToAllUser(map);
    }
}
