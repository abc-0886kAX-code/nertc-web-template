package com.ytxd.controller.Statistics;

import com.ytxd.common.util.R;
import com.ytxd.service.Statistics.StbprpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController("Stbprp_Controller")
@RequestMapping("/Stbprp")
public class Stbprp_Controller {
    @Resource
    StbprpService stbprpService;
    //站点统计表接口
    @GetMapping("/GetData")
    public R GetData(HttpServletRequest request)throws Exception{
        LinkedList<Map<String, Object>> obj = stbprpService.GetData(request);
        return R.ok().put("data",obj);
    }
    //站点统计饼状图接口
    @GetMapping("/GetData4")
    public  R GetData4()throws  Exception{
        List<HashMap<String, Object>> obj = stbprpService.GetData4();
        return R.ok().put("data",obj);
    }

    @GetMapping("/GetTopData")
    public  R GetTopData() throws Exception{
        List<HashMap<String, Object>> obj = stbprpService.GetTopData();
        return R.ok().put("data",obj);
    }
}
