package com.ytxd.controller.API.DigitalHall;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 水文化大厅接口
 */
@RestController("WaterCultureHall_Controller")
@RequestMapping("/WaterCultureHall")
public class WaterCultureHall_Controller {

    @Resource
    private CommonService commonService;

    /**
     * 文化科普宣传树型数据
     */
    @GetMapping("/PropagandaTree")
    public R getPropagandaList(HttpServletRequest request) throws Exception {
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select * from zsk_wh");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        // 过滤第一级类型
        final List<String> type01List = data.stream().map(item -> DataUtils.getMapKeyValue(item, "type01")).distinct().collect(Collectors.toList());
        List<HashMap<String,Object>> result = new ArrayList<>(type01List.size());
        for (String type01 : type01List) {
            HashMap<String, Object> map01 = new HashMap<>();
            map01.put("label", type01);
            // 过滤第二级类型
            final List<String> type02List = data.stream().filter(item -> DataUtils.getMapKeyValue(item, "type01").equals(type01))
                    .map(item -> DataUtils.getMapKeyValue(item, "type02")).distinct().collect(Collectors.toList());
            List<HashMap<String,Object>> children = new ArrayList<>(type02List.size());
            for (String type02 : type02List) {
                HashMap<String, Object> map02 = new HashMap<>();
                map02.put("label", type02);
                data.stream().filter(item -> DataUtils.getMapKeyValue(item, "type02").equals(type02) && StringUtils.isNotBlank(DataUtils.getMapKeyValue(item, "image"))).findFirst().ifPresent(item -> {
                    map02.put("id", DataUtils.getMapKeyValue(item, "id"));
                    map02.put("image", DataUtils.getMapKeyValue(item, "image"));
                });
                // 如果没找到有图片的数据
                if(!map02.containsKey("image")){
                    data.stream().filter(item -> DataUtils.getMapKeyValue(item, "type02").equals(type02)).findFirst().ifPresent(item -> {
                        map02.put("id", DataUtils.getMapKeyValue(item, "id"));
                        map02.put("image", DataUtils.getMapKeyValue(item, "image"));
                    });
                }
                children.add(map02);
            }
            map01.put("children", children);
            result.add(map01);
        }
        return R.ok().put("data", result);
    }

    /**
     * 文化科普宣传树型数据
     */
    @GetMapping("/PropagandaDetail")
    public R PropagandaDetail(HttpServletRequest request) throws Exception {
        final String id = request.getParameter("id");
        if(StringUtils.isBlank(id)){
            throw new RRException("id不能为空");
        }
        MySqlData mySqlData = new MySqlData();
        final HashMap<String, Object> map = commonService.getMapByKey("zsk_wh", id);
        final HashMap<String, Object> hashMap = new HashMap<>();
        String name = StringUtils.isNotBlank(DataUtils.getMapKeyValue(map, "type05")) ? DataUtils.getMapKeyValue(map, "type05") : DataUtils.getMapKeyValue(map, "type04");
        if(StringUtils.isBlank(name)){
            name = StringUtils.isNotBlank(DataUtils.getMapKeyValue(map, "type03")) ? DataUtils.getMapKeyValue(map, "type03") : DataUtils.getMapKeyValue(map, "type02");
            if(StringUtils.isBlank(name)){
                name = DataUtils.getMapKeyValue(map, "type01");
            }
        }
        hashMap.put("name", name);
        hashMap.put("remark", DataUtils.getMapKeyValue(map, "remark"));
        hashMap.put("image", DataUtils.getMapKeyValue(map, "image"));
        return R.ok().put("data", hashMap);
    }

    /**
     * 视频列表
     */
    @GetMapping("/VideoList")
    public R getVideoList(HttpServletRequest request) throws Exception {
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select * from zsk_video LIMIT 0,4");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }
}
