package com.ytxd.service.Subhall.Aquatic_Ecology;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Subhall.Aquatic_Ecology.Eval_Indicator_Library_Mapper;
import com.ytxd.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 水安全-指标库
 */
@Service
public class Eval_Indicator_Library_ServiceImpl implements Eval_Indicator_Library_Service{
    @Resource
    private Eval_Indicator_Library_Mapper mapper;
    /**
     * 查询指标列表
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetEvalIndicatorLibraryList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Eval_Indicator_Library_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 通过ID查询子表列表
     *
     * @param obj
     * @return
     */
    @Override
    public Map<String, Object> GetEvalIndicatorLibraryListByID(Map<String, Object> obj) throws Exception {
        /**
         * 判断id是否存在
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"indid"))){
            throw new RRException("主键不能为空！");
        }
        List<Map<String,Object>> data = mapper.Select_Eval_Indicator_Library_List(obj);
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
    public String EvalIndicatorLibrarySave(Map<String, Object> obj) throws Exception {
        String indid = DataUtils.getMapKeyValue(obj,"indid");
        if(StringUtils.isBlank(indid)){
            indid = UUID.randomUUID().toString().replace("-","").toUpperCase(Locale.ROOT);
            obj.put("indid",indid);
            obj.put("submittime", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
            mapper.Insert_Eval_Indicator_Library(obj);
        }else {
            mapper.Update_Eval_Indicator_Library(obj);
        }
        return indid;
    }

    /**
     * 删除
     *
     * @param obj
     * @return
     */
    @Override
    public Integer DeleteEvalIndicatorLibrary(Map<String, Object> obj) throws Exception {
        String indid = DataUtils.getMapKeyValue(obj,"indid");
        if(StringUtils.isBlank(indid)){
            throw new RRException("主键不能为空！");
        }
        DeleteChildrenEvalIndicator(indid);
        return mapper.Delete_Eval_Indicator_Library(indid);
    }

    /**
     * 删除所有的子级指标
     * @param indid
     */
    private void DeleteChildrenEvalIndicator(String indid){
        List<Map<String,Object>> data = mapper.Select_Children_Eval_Indicator_List(indid);
        data.forEach(item ->{
            String parentid = DataUtils.getMapKeyValue(item,"indid");
            DeleteChildrenEvalIndicator(DataUtils.getMapKeyValue(item,"indid"));
            mapper.Delete_Eval_Indicator_Library(parentid);
        });
    }
}
