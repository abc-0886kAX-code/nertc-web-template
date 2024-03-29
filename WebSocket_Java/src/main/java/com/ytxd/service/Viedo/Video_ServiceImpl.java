package com.ytxd.service.Viedo;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Video.Video_Mapper;
import com.ytxd.fireflycloud.FireflyCloudUtils;
import com.ytxd.request.TowerVideoRequest;
import com.ytxd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class Video_ServiceImpl implements Video_Service{

    @Resource
    private Video_Mapper mapper;
    @Value("${Video_targetUrl}")
    private String Video_targetUrl;
    @Resource
    private TowerVideoRequest towerVideoRequest;
    /**
     * 获取视频站点信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetVideoInfoList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        /**
         * 只查询视频站点的数据
         */
        obj.put("sttp","06");
        List<Map<String,Object>> data = mapper.Select_Video_Info_List(obj);
        /**
         * 处理经纬度
         */
        data.stream().forEach(item ->{
            double  lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double  lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        data.stream().filter(item -> "01".equals(DataUtils.getMapKeyValue(item, "sign"))).forEach(item -> {
            // 高塔视频 调用接口获取视频流地址
            final String url = towerVideoRequest.getRealUrl(DataUtils.getMapKeyValue(item, "stcd"), 0);
            if(StringUtils.isNotBlank(url)){
                item.put("videourl", url);
                item.put("videoformat", "flv");
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 开启视频转化
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    @Async
    public void OpenVideConversion(Map<String, Object> obj) throws Exception {
        if(StringUtil.isEmpty(DataUtils.getMapKeyValue(obj,"stcd"))){
            throw new RRException("站码不能为空！");
        }
        List<Map<String,Object>> data = mapper.Select_Video_Info_List(obj);
        if(!(data != null && data.size() > 0)){
            throw new RRException("未查询到数据！");
        }
        String videourl = DataUtils.getMapKeyValue(data.get(0),"videourl");
        if(StringUtil.isEmpty(videourl)){
            throw new RRException("该视频站点无对应视频流！");
        }
        OpenConversion openConversion = OpenConversion.getInstance(videourl,Video_targetUrl);
        openConversion.OpenConversionTOFlv();
    }

    /**
     *
     * @Desription TODO 刷新视频站点的播放url
     * @param deviceSerial
     * @return void
     * @date 2024/2/26 10:24
     * @Auther TY
     */
    @Override
    public void refreshVideUrl(String... deviceSerial) throws Exception {
        if(Objects.isNull(deviceSerial) || deviceSerial.length == 0){
            Map<String,Object> obj = new HashMap<>();
            obj.put("sttp","06");
            obj.put("flag","01");
            List<Map<String,Object>> data = mapper.Select_Video_Info_List(obj);
            Iterator<Map<String,Object>> iterator = data.iterator();
            while (iterator.hasNext()){
                Map<String,Object> info = iterator.next();
                String deviceid = DataUtils.getMapKeyValue(info,"deviceid");
                if(StringUtils.isNotBlank(deviceid)&&deviceid.length()==9){
                    UpdateVideUrl(deviceid);
                }
            }
        }else {
            for(String deviceid:deviceSerial){
                if(StringUtils.isNotBlank(deviceid)&&deviceid.length()==9){
                    UpdateVideUrl(deviceid);
                }
            }
        }
    }
    /**
     *
     * @Desription TODO 更新视频站点信息
     * @param deviceid
     * @return void
     * @date 2024/2/26 10:40
     * @Auther TY
     */
    private void UpdateVideUrl(String deviceid){
        try {
            String videoUrl = FireflyCloudUtils.getVideUrl(deviceid);
            Map<String, Object> params = new HashMap<>();
            params.put("deviceid", deviceid);
            params.put("videourl", videoUrl);
            mapper.Update_Vide_Url(params);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
