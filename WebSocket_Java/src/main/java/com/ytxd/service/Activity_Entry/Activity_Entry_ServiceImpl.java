package com.ytxd.service.Activity_Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.UIDUtils;
import com.ytxd.dao.Activity_Entry.Activity_Entry_Mapper;
import com.ytxd.util.StringUtil;

/**
 * @Classname Activity_Entry_ServiceImpl
 * @Author TY
 * @Date 2023/9/8 10:41
 * @Description TODO 报名信息
 */
@Service
public class Activity_Entry_ServiceImpl implements Activity_Entry_Service{
    @Resource
    private Activity_Entry_Mapper mapper;
    /**
     *
     * @Desription TODO 查询报名信息
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/9/8 10:43
     * @Auther TY
     */
    @Override
    public List<Map<String, Object>> getActivityEntryList(Map<String, Object> obj) throws Exception{
        String infoid = DataUtils.getMapKeyValue(obj,"infoid");
        if(StringUtil.isEmpty(infoid)){
//            throw new RRException("请选择一个活动");
        }
        return mapper.Select_Activity_Entry_List(obj);
    }
    /**
     *
     * @Desription TODO 保存报名信息
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/9/8 10:43
     * @Auther TY
     */
    @Override
    public String SaveActivityEntry(Map<String, Object> obj) throws Exception{
        String infoid = DataUtils.getMapKeyValue(obj,"infoid");
        if(StringUtil.isEmpty(infoid)){
            throw new RRException("请选择一个活动进行报名");
        }
        String telphone = DataUtils.getMapKeyValue(obj,"telphone");
        if(StringUtil.isEmpty(telphone)){
            throw new RRException("联系方式不能为空！！");
        }
        String enroll_name = DataUtils.getMapKeyValue(obj,"enroll_name");
        if(StringUtil.isEmpty(enroll_name)){
            throw new RRException("姓名不能为空！！");
        }
        String age = DataUtils.getMapKeyValue(obj,"age");
        if(StringUtil.isEmpty(age)){
            throw new RRException("年龄不能为空！！");
        }
        if(!isInteger(age)){
            throw new RRException("年龄只能为整数！！");
        }
        obj.put("age", Integer.parseInt(age));
        String entryid = DataUtils.getMapKeyValue(obj,"entryid");
        Map<String,Object> checkQuery = new HashMap<>();
        checkQuery.put("telphone",telphone);
        checkQuery.put("entryid",entryid);
        Integer checkFlag = mapper.Check_TelPhone_Exists(checkQuery);
        if(checkFlag > 0){
            throw new RRException("该联系方式已经报过名了");
        }

        if(StringUtil.isEmpty(entryid)){
            entryid = UIDUtils.getUUID();
            obj.put("entryid",entryid);
            mapper.Insert_Activity_Entry(obj);
        }else {
            mapper.Update_Activity_Entry(obj);
        }
        return entryid;
    }
    /**
     *
     * @Desription TODO 删除报名信息
     * @param obj
     * @return java.lang.Integer
     * @date 2023/9/8 10:47
     * @Auther TY
     */
    @Override
    public Integer DeleteActivityEntry(Map<String, Object> obj) throws Exception{
        String entryid = DataUtils.getMapKeyValue(obj,"entryid");
        if(StringUtil.isEmpty(entryid)){
            throw new RRException("请选择一条记录！");
        }
        return mapper.Delete_Activity_Entry(obj);
    }
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
