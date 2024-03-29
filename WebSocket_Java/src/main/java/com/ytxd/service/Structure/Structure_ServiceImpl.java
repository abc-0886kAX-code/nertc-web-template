package com.ytxd.service.Structure;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.UIDUtils;
import com.ytxd.dao.Structure.Structure_Mapper;
import com.ytxd.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Classname Structure_ServiceImpl
 * @Author TY
 * @Date 2023/8/17 10:59
 * @Description TODO 组织架构人员维护
 */
@Service
public class Structure_ServiceImpl implements Structure_Service{
    @Resource
    private Structure_Mapper mapper;
    /**
     *
     * @Desription TODO 查询组织架构人员信息
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/8/17 11:02
     * @Auther TY
     */
    @Override
    public PageInfo getRLStructureInfoList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_RL_Structure_Info(obj);
        return new PageInfo(data);
    }
    /**
     *
     * @Desription TODO 保存信息
     * @param obj
     * @return java.lang.String
     * @date 2023/8/17 11:16
     * @Auther TY
     */
    @Override
    public String SaveStructure(Map<String, Object> obj) throws Exception {
        String structuretype = DataUtils.getMapKeyValue(obj,"structuretype");
        if(StringUtil.isEmpty(structuretype)){
            throw new RRException("必须选择一个类型");
        }
        String full_name = DataUtils.getMapKeyValue(obj,"full_name");
        if(StringUtil.isEmpty(full_name)){
            throw new RRException("姓名不能为空");
        }
        String telephone = DataUtils.getMapKeyValue(obj,"telephone");
        if(StringUtil.isEmpty(telephone)){
            throw new RRException("联系方式不能为空");
        }
        String structureid = DataUtils.getMapKeyValue(obj,"structureid");
        String userid = DataUtils.getMapKeyValue(obj,"userid");
        obj.put("submitman",userid);
        obj.put("submittime",DataUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        if(StringUtil.isEmpty(structureid)){
            structureid = UIDUtils.getUUID();
            obj.put("structureid",structureid);
            mapper.Insert_Structure(obj);
        }else {
           mapper.Update_Structure(obj);
        }
        return structureid;
    }
    /**
     *
     * @Desription TODO 删除信息
     * @param obj
     * @return java.lang.Integer
     * @date 2023/8/17 11:19
     * @Auther TY
     */
    @Override
    public Integer DelStructure(Map<String, Object> obj) throws Exception {
        String structureid = DataUtils.getMapKeyValue(obj,"structureid");
        if(StringUtil.isEmpty(structureid)){
            throw new RRException("请选择需要删除的记录");
        }
        return mapper.Delete_Structure(structureid);
    }
}
