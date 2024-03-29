package com.ytxd.controller.Map;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Map.Map_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController("Map_Contorller")
@RequestMapping("/Map_Contorller")
public class Map_Contorller {

    @Resource
    Map_Service service;

    @PostMapping("/getRiverMsg")
    public R getRiverMsg(HttpServletRequest request) throws Exception{
        if(request.getParameter("fid")!=null && !request.getParameter("fid").equals("")) {
            String fid = request.getParameter("fid");
            return R.ok().put("data",service.getRiverMsg(fid));
        }
        return R.error("fid不能为空");
    }

    @PostMapping("/getRsvrMsg")
    public R getRsvrMsg(HttpServletRequest request) throws Exception{
        if(request.getParameter("fid")!=null && !request.getParameter("fid").equals("")) {
            String fid = request.getParameter("fid");
            return R.ok().put("data",service.getRsvrMsg(fid));
        }
        return R.error("fid不能为空");
    }
}
