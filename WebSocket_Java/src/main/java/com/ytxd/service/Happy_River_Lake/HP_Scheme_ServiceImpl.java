package com.ytxd.service.Happy_River_Lake;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Happy_River_Lake.HP_Scheme_Mapper;
import com.ytxd.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 幸福河湖-评价方案
 */
@Service
public class HP_Scheme_ServiceImpl implements HP_Scheme_Service {
    @Resource
    private HP_Scheme_Mapper mapper;
    /**
     * 查询评价方案
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetHPSchemeList(Map<String, Object> obj) throws Exception{
        /**
         * 分页
         */
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_HP_Scheme_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 通过ID查询
     *
     * @param obj
     * @return
     */
    @Override
    public Map<String, Object> GetHPSchemeListByID(Map<String, Object> obj) throws Exception{
        /**
         * 判断schemeid是否存在
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"schemeid"))){
            throw new RRException("主键不能为空！");
        }
        List<Map<String,Object>> data = mapper.Select_HP_Scheme_List(obj);
        if(data != null && data.size() > 0){
            return data.get(0);
        }
        return null;
    }

    /**
     * 保存
     *
     * @param obj
     * @return
     */
    @Override
    public String HPSchemeSave(Map<String, Object> obj) throws Exception{
        /**
         * 判断schemeid是否存在
         */

        String schemeid = DataUtils.getMapKeyValue(obj,"schemeid");
        String schemetips = DataUtils.getMapKeyValue(obj,"schemetips");
        if(StringUtils.isBlank(schemetips)){
            obj.put("schemetips",1);
        }
        if(StringUtils.isBlank(schemeid)){
            schemeid = UUID.randomUUID().toString().replace("-","").toUpperCase(Locale.ROOT);
            obj.put("schemeid",schemeid);
            obj.put("submittime", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
            mapper.Insert_HP_Scheme(obj);
            /**
             * 初始化指标
             */
            mapper.Insert_Initialization_Scheme_Ind(schemeid);
            mapper.Insert_HP_Scheme_Bulk(schemeid);
            // 初始化分数
            mapper.Update_Default_Scheme_Score(schemeid);
            mapper.Update_Default_ind_Score(schemeid);
            mapper.Insert_Default_Bulk_Score(schemeid);
        }else {
            mapper.Update_HP_Scheme(obj);
        }
        return schemeid;
    }

    /**
     * 删除
     *
     * @param schemeid
     * @return
     */
    @Override
    public Integer Delete_HP_Scheme(String schemeid) throws Exception{
        if(StringUtils.isBlank(schemeid)){
            throw new RRException("主键不能为空！");
        }
        return mapper.Delete_HP_Scheme(schemeid);
    }
}
