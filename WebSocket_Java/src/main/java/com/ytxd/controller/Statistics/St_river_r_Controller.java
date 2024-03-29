package com.ytxd.controller.Statistics;

import com.ytxd.common.util.R;
import com.ytxd.service.Statistics.St_river_rService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/St_river_r")
public class St_river_r_Controller {
       @Resource
       St_river_rService service;

       //河道top5接口
       @GetMapping("/GetData")
       public R GetData(){
           List<HashMap<String, Object>> obj = service.GetData();
           return R.ok().put("data",obj);
       }
       @GetMapping("/GetCount")
       public R  GetCount(){
           List<HashMap<String,Object>> obj =service.GetData();
           return R.ok().put("data",obj);
       }

}
