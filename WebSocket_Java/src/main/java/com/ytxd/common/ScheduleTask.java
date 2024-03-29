package com.ytxd.common;


import com.ytxd.dao.Subhall.RiverAndLake.RiverAndLake_Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {

    private RiverAndLake_Mapper riverAndLake_Mapper;
    @Autowired
    public void setRiverAndLake_Mapper(RiverAndLake_Mapper riverAndLake_Mapper) {
        this.riverAndLake_Mapper = riverAndLake_Mapper;
    }


    // 创建包含指定镇和乡的String数组
    private static final String[] towns = {
            "忠兴镇", "柏林镇", "徐家镇", "石板镇", "玉河镇",
            "东林乡", "云凤乡", "凤凰乡", "朝真乡", "建华乡",
            "观太乡", "游仙镇", "松垭镇", "石马镇", "小枧沟镇",
            "新桥镇", "沉抗镇", "刘家镇", "太平乡", "东宣乡",
            "街子乡", "梓绵乡", "白蝉乡", "魏城镇", "武引云凤所",
            "武引沉抗所", "水务局"
    };
    private static final String[] event = {
            "河道日常巡查" , "水库日常巡查" , "水体采样" , "水位监测" , "流量监测"
    };

    /**
     * @Description: 定时生成假数据-用于河湖管护界面。
     * @param
     * @return: void
     * @Author: ZJW
     * @Date: 2024/3/25 15:51
     */
    @Scheduled(cron = "12,47 3,32,58 6,9,11,13,16,18 * * ? ")
//    @Scheduled(cron = "0/13 0/1 * * * ? ")
//    @Scheduled(cron = "0/8 * * * * ? ")
    public void setRLValue() {
        // 创建一个Date对象，表示当前的时间
        Date now = new Date();
        // 创建一个SimpleDateFormat对象，并设置所需的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 使用format方法将当前时间格式化成字符串
        String formattedDate = sdf.format(now);
        // 创建Random对象实例
        Random random = new Random();

        HashMap<String, Object> map = new HashMap<>();
        map.put("warnID", UUID.randomUUID().toString().replace("-" , ""));
        map.put("deviceID", UUID.randomUUID().toString().replace("-" , "").substring(2,20));
        map.put("warnStartTime", formattedDate);
        formattedDate = sdf.format(new Date());
        map.put("warnEndTime", formattedDate);
        map.put("warnDesc", now.getTime() % 2 == 0 ? "人" : "船");
        map.put("flag", "01");
        map.put("type", "01");

        riverAndLake_Mapper.insertStAlarmR(map);
        map = new HashMap<>();
        map.put("id" , UUID.randomUUID().toString().replace("-" , ""));
        map.put("clockid" , UUID.randomUUID().toString().replace("-" , ""));
        map.put("eventname" , event[random.nextInt(event.length)]);
        formattedDate = sdf.format(new Date());
        map.put("tm" , formattedDate);
        map.put("address" , towns[random.nextInt(towns.length)]);
        map.put("eventdesc" , map.getOrDefault("eventname" , 0));
        map.put("flag", "01");
        formattedDate = sdf.format(new Date());
        map.put("submittime", formattedDate);
        riverAndLake_Mapper.insertRlPatrol(map);
    }

    // 常见的中文姓氏
    private static final String[] surnames = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯",
            "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦",
            "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严",
            "华", "金", "魏", "陶", "姜"};

    // 常用的中文名字字符
    private static final String[] names = {"建", "国", "华", "明", "军", "洋", "志", "宇", "海",
            "晨", "燕", "凯", "盛", "雨", "嘉", "智", "健", "悦",
            "飞", "新", "泽", "宁", "立", "淳", "薇", "勇", "杰",
            "思", "涛", "俊"};

    public String getName() {
        Random random = new Random();

        // 随机选择姓氏
        String surname = surnames[random.nextInt(surnames.length)];

        // 随机组合名字，这里假设名字只有一个或两个汉字
        String name = names[random.nextInt(names.length)];
        if (random.nextBoolean()) {
            // 有一定概率加上第二个汉字
            name += names[random.nextInt(names.length)];
        }
        return surname + name;
    }


}
