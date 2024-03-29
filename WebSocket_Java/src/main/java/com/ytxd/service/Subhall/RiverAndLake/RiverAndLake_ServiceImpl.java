package com.ytxd.service.Subhall.RiverAndLake;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Subhall.RiverAndLake.RiverAndLake_Mapper;
import com.ytxd.request.TowerVideoRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 河湖长效管护
 */
@Service
public class RiverAndLake_ServiceImpl implements RiverAndLake_Service{

    @Resource
    private RiverAndLake_Mapper mapper;
    @Resource
    private TowerVideoRequest towerVideoRequest;

    /**
     * 查询视频分区信息
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetRLVideoPartitionList(Map<String, Object> obj) throws Exception{
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_RL_Video_Partition_List(obj);
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
            String flag = DataUtils.getMapKeyValue(item,"state");
            item.put("bflag",Objects.equals(flag,"01"));
            String state = DataUtils.getMapKeyValue(item,"state");
            item.put("bstate",Objects.equals(state,"01"));
            if("01".equals(DataUtils.getMapKeyValue(item, "sign"))){
                // 高塔视频 调用接口获取视频流地址
                final String url = towerVideoRequest.getRealUrl(DataUtils.getMapKeyValue(item, "stcd"), 0);
                if(StringUtils.isNotBlank(url)){
                    item.put("videourl", url);
                    item.put("videoformat", "flv");
                }
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 视频站点统计信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> GetVideoStatisticsInfo(Map<String, Object> obj) throws Exception {
        Map<String,Object> data = mapper.Select_Video_Statistics_Info(obj);
        return data;
    }

    /**
     * 按时间查询值班人员信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetRLArrangeList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        // 判断时间是否有值
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtil.format(new Date(),"yyyy-MM-dd"));
        }
        List<Map<String,Object>> data = mapper.Select_RL_Arrange_List(obj);
        data.stream().forEach(item ->{
            String imageurl = DataUtils.getMapKeyValue(item,"imageurl");
            if(StringUtils.isNotBlank(imageurl)){
                item.put("imageurl",imageurl.split("\\|"));
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取基础设施信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetRLInfrastructureList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        obj.put("inftype",obj.get("code"));
        List<Map<String,Object>> data = mapper.Select_RL_Infrastructure_List(obj);
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
            String imageurl = DataUtils.getMapKeyValue(item,"imageurl");
            if(StringUtils.isNotBlank(imageurl)){
                item.put("imageurl",imageurl.split("\\|"));
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取基础设施统计信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetRLInfrastructureStatistics(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_RL_Infrastructure_Statistics(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 查询高塔报警的统计预警信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetStAlarmRStatistics(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm",DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_St_Alarm_R_Statistics(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 查询两个小时内的报警信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetStAlarmRList(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm",DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        obj.put("page",1);
        obj.put("rows",50);
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_St_Alarm_R_List(obj);
        data.stream().forEach(item ->{
            String imagepath = DataUtils.getMapKeyValue(item,"imagepath");
            if(StringUtils.isNotBlank(imagepath)){
                item.put("imagepath",imagepath.split("\\|"));
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 根据ID查询报警信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> GetStAlarmRInfoByID(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"warnid"))){
            throw new RRException("请选中报警信息！");
        }
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_St_Alarm_R_List(obj);
        if(data != null && data.size() > 0){
            data.stream().forEach(item ->{
                String imagepath = DataUtils.getMapKeyValue(item,"imagepath");
                if(StringUtils.isNotBlank(imagepath)){
                    item.put("imagepath",imagepath.split("\\|"));
                }
            });
            return data.get(0);
        }
        return null;
    }

    /**
     * 处理报警信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Integer UpdateStAlarmR(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"warnid"))){
            throw new RRException("请选中需要处理的报警信息！");
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"flag"))){
            obj.put("flag","01");
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"warnendtime"))){
            obj.put("warnendtime",DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        return mapper.Update_St_Alarm_R(obj);
    }

    /**
     * 查询组织架构信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetRLStructureInfo(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_RL_Structure_Info(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取河湖信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetRLInformationList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_RL_Information_List(obj);
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取战点的详细信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> GetSiteIntroduceInfo(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"stcd"))){
            throw new RRException("请选择站点");
        }
        List<Map<String,Object>> data = mapper.Select_Site_Introduce_Info(obj);
        data.stream().forEach(item ->{
            String sttp = DataUtils.getMapKeyValue(item,"sttp");
            Map<String,Object> dataMap = null;
            if(Objects.equals("01",sttp) || Objects.equals("08",sttp)){
                dataMap = mapper.Select_Site_Rvfcch_Info(item);
            }else if(Objects.equals("09",sttp)){
                dataMap = mapper.Select_Site_Rsvrfcch_Info(item);
            }
            if(dataMap != null){
                item.putAll(dataMap);
            }
            String filepath = DataUtils.getMapKeyValue(item,"filepath");
            if(StringUtils.isNotBlank(filepath)){
                item.put("filepath",filepath.split("\\|"));
            }
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        if(data != null && data.size() > 0){
            return data.get(0);
        }
        return null;
    }

    /**
     * 芙蓉溪打卡-事件列表
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetRLPatrolList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_RL_Patrol_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    @Override
    public PageInfo selectRlPersonnel(Map<String, Object> obj) {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.selectRlPersonnel(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }
}
