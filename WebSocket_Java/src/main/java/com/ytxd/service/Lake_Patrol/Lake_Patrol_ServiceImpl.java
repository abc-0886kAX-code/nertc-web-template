package com.ytxd.service.Lake_Patrol;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.IPUtils;
import com.ytxd.dao.Lake_Patrol.Lake_Patrol_Mapper;
import com.ytxd.util.DateUtils;
import com.ytxd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 巡湖打卡
 */
@Service
public class Lake_Patrol_ServiceImpl implements Lake_Patrol_Service{
    @Resource
    private Lake_Patrol_Mapper mapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    /**
     * 获取巡湖列表
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo getRLPunchClockList(Map<String, Object> obj) throws Exception{
        obj.put("page",1);
        obj.put("rows",20);
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_RL_Punch_Clock_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 查询当前设备是否正在巡湖中
     *
     * @param obj
     * @return
     */
    @Override
    public Map<String, Object> getRLPunchClock(Map<String, Object> obj) throws Exception {
        String macurl = DataUtils.getMapKeyValue(obj,"macurl");
        if(StringUtils.isBlank(macurl)){
            throw new RRException("mac地址不能为空！！");
        }
        obj.put("isregister","01");
        List<Map<String,Object>> data = mapper.Select_RL_Punch_Clock_List(obj);
        if(data != null && !data.isEmpty()){
            return data.get(0);
        }
        return null;
    }

    /**
     * 巡护打卡保存
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public String RLPunchClockSave(Map<String, Object> obj) throws Exception {
        /**
         * 操作类型
         */
        String op_type = DataUtils.getMapKeyValue(obj,"op_type");
        String id = DataUtils.getMapKeyValue(obj,"id");
        String macurl = DataUtils.getMapKeyValue(obj,"macurl");
//        String macurl = "E0-D0-45-E6-A3-9D";
//        obj.put("macurl",macurl);
        if(Objects.equals("01",op_type)){
            CheckMACPatrol(macurl);
            obj.put("starttime", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }else {
            if(StringUtils.isBlank(id)){
                throw new RRException("当前设备还未开启签到，无法结束签到！！");
            }
            obj.put("flag","01");
            obj.put("endtime", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        if(StringUtil.isEmpty(id)){
            if(StringUtils.isBlank(macurl)){
                throw new RRException("mac地址不能为空！！");
            }
            id = UUID.randomUUID().toString().toUpperCase(Locale.ROOT).replaceAll("-","");
            obj.put("id",id);
            obj.put("flag","00");
            obj.put("submittime",DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
            mapper.Insert_RL_Punch_Clock(obj);
            mapper.Insert_st_lake_way(obj);
        }else {
            mapper.Update_RL_Punch_Clock(obj);
        }
        // 保存路线信息
        RLPunchAddressSave(id,DataUtils.getMapKeyValue(obj,"position"));
        return id;
    }

    /**
     * 巡护打卡删除
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Integer RLPunchClockDel(Map<String, Object> obj) throws Exception {
        String id = DataUtils.getMapKeyValue(obj,"id");
        if(StringUtils.isBlank(id)){
            throw new RRException("请选择需要删除巡护打卡！！");
        }
        obj.put("clockid",id);
        mapper.Delete_RL_Punch_Address(obj);
        return mapper.Delete_RL_Punch_Clock(obj);
    }

    /**
     * 保存路线
     * @param clockid
     * @param position
     * @return
     */
    private  Integer RLPunchAddressSave(String clockid,String position){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        // 批量的保存的mapper
        Lake_Patrol_Mapper batchMapper = sqlSession.getMapper(Lake_Patrol_Mapper.class);
        if(StringUtils.isBlank(position)){
            return null;
        }
        // 获取路线的经纬度数组
        Double[][] doubles = new Double[][]{};
        doubles = JSONObject.parseObject(position,doubles.getClass());
        for(int i = 0;i < doubles.length; i++){
            Double[] nums = doubles[i];
            Map<String,Object> map = new HashMap<>();
            map.put("lgtd",nums[0]);
            map.put("lttd",nums[1]);
            Double height = nums[2];
            if(height == null){
                height = 0.0;
            }
            map.put("height",height);
            map.put("orderid",(i+1));
            map.put("clockid",clockid);
            batchMapper.Insert_RL_Punch_Address(map);
        }
        // 提交批量保存的数据
        sqlSession.commit();
        sqlSession.clearCache();
        return 1;
    }

    /**
     * 检查该设备是否存在打卡中的事件
     * @param macUrl
     * @return
     */
    private Boolean CheckMACPatrol(String macUrl){
        Map<String,Object> map = new HashMap<>();
        map.put("macurl",macUrl);
        map.put("isregister","01");
        if(StringUtils.isBlank(macUrl)){
            return true;
        }
        List<Map<String,Object>> data = mapper.Select_RL_Punch_Clock_List(map);
        if(data != null && !data.isEmpty()){
            throw new RRException("该设备已经存在一个正在打卡中的事件！！");
        }
        return true;
    }
    /**
     * 获取训湖打卡路线
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Double[][] getRLPunchAddress(Map<String, Object> obj) throws Exception {
        String clockid = DataUtils.getMapKeyValue(obj,"clockid");
        if(StringUtils.isBlank(clockid)){
            throw new RRException("巡湖事件不能为空！！");
        }
        List<Map<String,Object>> data = mapper.Select_RL_Punch_Address(obj);
        if(data != null && !data.isEmpty()){
            Double[][] doubles = new Double[data.size()][3];
           for(Integer i = 0;i < data.size();i++){
               Map<String,Object> item = data.get(i);
               Double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
               Double lttd = DataUtils.getMapDoubleValue(item,"lttd");
               Double height = DataUtils.getMapDoubleValue(item,"height");
               Double[] doubles1 = new Double[]{lgtd,lttd,height};
               doubles[i] = doubles1;
           }
            return doubles;
        }
        return null;
    }

    /**
     * 查询巡湖事件
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo getRLPatrolList(Map<String, Object> obj) {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_RL_Patrol(obj);
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
            String filepath = DataUtils.getMapKeyValue(item,"filepath");
            if(StringUtils.isNotBlank(filepath)){
                item.put("filepath",filepath.split("\\|"));
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 巡湖事件保存
     *
     * @param obj
     * @return
     */
    @Override
    public String RLPatrolSave(Map<String, Object> obj) {
        String id = DataUtils.getMapKeyValue(obj,"id");
        if(StringUtils.isBlank(id)){
            id = UUID.randomUUID().toString().toUpperCase(Locale.ROOT).replaceAll("-","");
            obj.put("id",id);
            obj.put("submittime",DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
            mapper.Insert_RL_Patrol(obj);
        }else {
            mapper.Update_Rl_Patrol(obj);
        }
        return id;
    }

    /**
     * 巡湖事件删除
     *
     * @param obj
     * @return
     */
    @Override
    public Integer RLPatrolDel(Map<String, Object> obj) {
        String id = DataUtils.getMapKeyValue(obj,"id");
        if(StringUtils.isBlank(id)){
            throw new RRException("请选择需要删除的巡湖事件！！");
        }
        return mapper.Delete_Rl_Patrol(obj);
    }
}
