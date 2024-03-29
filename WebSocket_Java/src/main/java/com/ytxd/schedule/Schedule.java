package com.ytxd.schedule;

import cn.hutool.json.JSONUtil;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.UIDUtils;
import com.ytxd.dao.Video.Video_Mapper;
import com.ytxd.fireflycloud.FireflyCloudUtils;
import com.ytxd.model.ActionResult;
import com.ytxd.model.MySqlData;
import com.ytxd.service.API.DigitalHall.DigitalHall_Service;
import com.ytxd.service.CommonService;
import com.ytxd.service.Subhall.Water_Safety_Service;
import com.ytxd.service.Viedo.Video_Service;
import com.ytxd.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 入园游览人数、巡湖打卡人数、公众互动人数 随机增加
 */
@Slf4j
@Component
public class Schedule {

    @Resource
    private DigitalHall_Service digitalHallService;
    @Resource
    private CommonService commonService;
    @Resource
    private Water_Safety_Service safety_service;
    @Resource
    private Video_Service videoService;

    private final List<String> s1 = Arrays.asList("不存在", "经常", "偶尔");
    private final List<String> s2 = Arrays.asList("无", "一般", "严重");
    private final List<String> s3 = Arrays.asList("一般", "清澈");
    private final List<String> s4 = Arrays.asList("一般", "优美");
    private final List<String> s5 = Arrays.asList("少", "一般", "多");
    private final List<String> s6 = Arrays.asList("数量少", "一般", "数量多");
    private final List<String> s7 = Arrays.asList("一般", "太多");
    private final List<String> s8 = Arrays.asList("一般", "水量多");
    private final List<String> s9 = Arrays.asList("一般", "适合");
    private final List<String> s10 = Arrays.asList("满意", "很满意");

    /**
     * 入园人数新增
     */
    // 项目启动后延迟1秒后执行 之后每隔三分钟执行一次
//    @Scheduled(initialDelay=1000, fixedRate=1000 * 60 * 2)
    public void task1(){
        // 1 判断是否新增
        final Random random = new Random();
        if(random.nextBoolean()){
            // 2 如果需要新增 随机获取新增人数 1-3随机数
            final int i = random.nextInt(3) + 1;
            // 3 增加
            digitalHallService.updateTodayNumbers(i);
        }
    }

    @Scheduled(initialDelay=5000, fixedRate=1000 * 60 * 30)
    public void task2(){
        final Random random = new Random();
        final int i = random.nextInt(10) + 1;
        // 2.巡湖打卡人数
        MySqlData mySqlData = new MySqlData();
        if(i == 5){
            mySqlData.setSql("INSERT INTO st_lake_way(tm, NAME, flag) VALUES (now(), '测序巡湖打卡', '01');");
            try {
                final ActionResult result = commonService.insert(mySqlData);
                System.out.println(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(i == 10){
            // 3.公众互动人数
            mySqlData = new MySqlData();
            mySqlData.setSql("INSERT INTO `st_satisfaction`(`FLOODOVERFLOW`, `BANKINFO`, `BANKFAILUREINFO`, `TRANSPARENCY`, `COLOR`, `GARBAGE`, `FISH`, `AQUATICPLANTS`, `WATERFOWL`, `GREENINFO`, `ENTERTAINMENT`, `OVERALLSATISFACTION`, `DISSATISFIED`, `SATISFIED`)" +
                    " VALUES ('"+s1.get(random.nextInt(3))+"', '"+s2.get(random.nextInt(3))+"', '"+s3.get(random.nextInt(2))+"', '"+s2.get(random.nextInt(3))+"', '"+s4.get(random.nextInt(2))+"', '"+s5.get(random.nextInt(3))+"', '"+s6.get(random.nextInt(3))+"', '"+s7.get(random.nextInt(2))+"', '"+s8.get(random.nextInt(2))+"', '"+s4.get(random.nextInt(2))+"', '"+s9.get(random.nextInt(2))+"', '"+s10.get(random.nextInt(2))+"', NULL, NULL);");
            try {
                final ActionResult result = commonService.insert(mySqlData);
                System.out.println(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Scheduled(initialDelay=5000, fixedRate=1000 * 60 * 30)
    public void task3(){
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("update early_warning set flag ='00' where warningtype = '05' and submittime < adddate(now(),interval -1 day) and submittime >  adddate(now(),interval -3 day)");
        try {
            final ActionResult result = commonService.insert(mySqlData);
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 定时两小时插入一条数据，根据未来24的累计降雨量，当前芙蓉溪水位和当前胜天河水位，去方案表里筛选出一个预案，没有降雨时预案为空。
     */
    @Scheduled(cron = "0 0 */2 * * ? ")
//    @Scheduled(initialDelay=5000,fixedRate=1000 * 60 * 30)
    public void task4() throws Exception {
        String schemeid = UUID.randomUUID().toString().replace("-","").toUpperCase(Locale.ROOT);
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("storm_plan_regular");
        mySqlData.setFieldValue("id",schemeid);
        mySqlData.setFieldValue("tm", DataUtils.getDate("yyyy-MM-dd HH:00:00"));
        Map<String,Object> obj = new HashMap<>();
        Map<String, Object> map = safety_service.GetNearPlanInfo(obj);
        String nowp = DataUtils.getMapKeyValue(map,"nowp");
        double nowmh = DataUtils.getMapDoubleValue(map,"nowmhwl");
        double nowsth = DataUtils.getMapDoubleValue(map,"nowsthwl");
        String planname = DataUtils.getDate("yyyy-MM-dd HH:00");
        mySqlData.setFieldValue("plan_name",planname);
        Double nowpd = DataUtils.getMapDoubleValue(map,"nowp");
        if(nowpd > 30 || nowmh > 14.5 || nowsth > 14){
            mySqlData.setFieldValue("plan_id",DataUtils.getMapKeyValue(map,"plan_id"));
        }
        final ActionResult result = commonService.insert(mySqlData);
        System.out.println("增加预案成功！："+result.getMsg());
    }


    @Scheduled(cron = "0 0 8 ? * *")
//    @Scheduled(initialDelay=5000, fixedRate=1000 * 60 * 30)
    public void task5() throws Exception {
        String tm = DataUtils.getDate("yyyy-MM-dd") + " "+getIntegerRandomToString(8,18)+":"+getIntegerRandomToString(0,59)+":00";
        Integer step = getIntegerRandom(5,10);
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("INSERT INTO rl_punch_clock (ID, MACURL, STARTTIME, ENDTIME, FLAG, REMARK, SUBMITTIME, userid, username)" +
                "VALUES ('"+UIDUtils.getUUID()+"', 'oM5744oZOexUkehgvnWEkja3BS34', '"+tm+"' + interval -1 day, date_add('"+tm+"',interval "+step+" minute) + interval -1 day, '01', null, date_add('"+tm+"',interval "+step+" minute) + interval -1 day, '11', '何敏')");
        commonService.insert(mySqlData);
        mySqlData = new MySqlData();
        tm = DataUtils.getDate("yyyy-MM-dd") + " "+getIntegerRandomToString(8,18)+":"+getIntegerRandomToString(0,59)+":00";
        step = getIntegerRandom(5,10);
        mySqlData.setSql("INSERT INTO rl_punch_clock (ID, MACURL, STARTTIME, ENDTIME, FLAG, REMARK, SUBMITTIME, userid, username)" +
                "VALUES ('"+UIDUtils.getUUID()+"', 'oM5744oZOexUkehgvnWEkja3BS34', '"+tm+"' + interval -1 day, date_add('"+tm+"',interval "+step+" minute) +  interval -1 day, '01', null, date_add('"+tm+"',interval "+step+" minute) + interval -1 day, '11', '尹风光')");
        commonService.insert(mySqlData);
    }
    public String getIntegerRandomToString(Integer min,Integer max){
        Random random = new Random();
        int result = random.nextInt(max - min) + min;
        if(result > 10){
            return result + "";
        }else {
            return "0" + result;
        }
    }

    public Integer getIntegerRandom(Integer min,Integer max){
        Random random = new Random();
        int result = random.nextInt(max - min) + min;
         return  result;
    }
    /**
     *
     * @Desription TODO 更新视频站点信息
     * @return void
     * @date 2024/2/26 10:21
     * @Auther TY
     */
    @Scheduled(initialDelay=1000, fixedRate= 1000 * 60 * 60 *24 * 719 )
    public void refreshVideUrl() throws Exception {
        FireflyCloudUtils.getAccessToken(true);
        videoService.refreshVideUrl();
    }

    public static void main(String[] args) {
        // 创建一个随机数生成器
        Random random = new Random();


        for(int i = 0;i<100;i++){
            // 生成1到10范围内的随机整数（包括1和10）
            int randomNumber = random.nextInt(10) + 8;
            if(randomNumber<10){
                System.out.println("0"+randomNumber);
            }else {
                System.out.println(randomNumber);
            }

        }
    }
}
