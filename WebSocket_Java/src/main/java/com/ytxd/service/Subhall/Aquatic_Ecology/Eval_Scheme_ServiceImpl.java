package com.ytxd.service.Subhall.Aquatic_Ecology;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Subhall.Aquatic_Ecology.Eval_Scheme_Mapper;
import com.ytxd.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 水生态-评价方案
 */
@Service
public class Eval_Scheme_ServiceImpl implements Eval_Scheme_Service{
    @Resource
    private Eval_Scheme_Mapper mapper;
    /**
     * 查询评价方案
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetEvalSchemeList(Map<String, Object> obj) throws Exception{
        /**
         * 分页
         */
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Eval_Scheme_List(obj);
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
    public Map<String, Object> GetEvalSchemeListByID(Map<String, Object> obj) throws Exception{
        /**
         * 判断schemeid是否存在
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"schemeid"))){
            throw new RRException("主键不能为空！");
        }
        List<Map<String,Object>> data = mapper.Select_Eval_Scheme_List(obj);
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
    public String EvalSchemeSave(Map<String, Object> obj) throws Exception{
        /**
         * 判断schemeid是否存在
         */
        String schemeid = DataUtils.getMapKeyValue(obj,"schemeid");
        if(StringUtils.isBlank(schemeid)){
            schemeid = UUID.randomUUID().toString().replace("-","").toUpperCase(Locale.ROOT);
            obj.put("schemeid",schemeid);
            obj.put("submittime", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
            mapper.Insert_Eval_Scheme(obj);
            /**
             * 初始化指标
             */
            mapper.Insert_Initialization_Scheme_Ind(schemeid);
            /**
             * 初始化分数
             */
            mapper.Insert_default_Stcd(schemeid);
            mapper.Update_Default_Scheme_Score(schemeid);
            mapper.Update_Default_ind_Score(schemeid);
            mapper.Insert_Default_stcd_Score(schemeid);
        }else {
            mapper.Update_Eval_Scheme(obj);
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
    public Integer Delete_Eval_Scheme(String schemeid) throws Exception{
        if(StringUtils.isBlank(schemeid)){
            throw new RRException("主键不能为空！");
        }
        return mapper.Delete_Eval_Scheme(schemeid);
    }
}
