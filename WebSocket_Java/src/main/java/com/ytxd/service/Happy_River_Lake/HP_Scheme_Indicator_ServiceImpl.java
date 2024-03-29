package com.ytxd.service.Happy_River_Lake;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Happy_River_Lake.HPCalIndScore_Mapper;
import com.ytxd.dao.Happy_River_Lake.HP_Scheme_Indicator_Mapper;
import com.ytxd.dao.Subhall.Aquatic_Ecology.CalIndScore_Mapper;
import com.ytxd.util.EnforcementUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 幸福河湖-评价方案指标
 */
@Service
public class HP_Scheme_Indicator_ServiceImpl implements HP_Scheme_Indicator_Service {
    @Resource
    private HP_Scheme_Indicator_Mapper mapper;
    @Resource
    private HPCalIndScore_Mapper calIndScoreMapper;

    @Autowired
    private  SqlSessionFactory sqlSessionFactory;
    private static Map<String, Method> methodMap = new HashMap<>();

    {
        try {
            Object obj = HPCalIndScore.class.newInstance();
            Class clz = obj.getClass();
           methodMap = Stream.of(clz.getDeclaredMethods()).collect(Collectors.toMap(item -> {
                return item.getName().toLowerCase(Locale.ROOT);
            }, v -> v, (v1, v2) -> {
                return v1;
            }));
        } catch (Exception e) {
        }
    }

    /**
     * 查询评价方案指标
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetHPSchemeIndicatorList(Map<String, Object> obj) {
        /**
         * 分页
         */
        DataUtils.Padding(obj);
        obj.put("indrank","01,02,03");
        List<Map<String,Object>> data = mapper.Select_HP_Scheme_Indicator_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 查询评价方案指标 通过ID
     *
     * @param obj
     * @return
     */
    @Override
    public Map<String, Object> GetHPSchemeIndicatorListById(Map<String, Object> obj) {
        /**
         * 判断id是否存在
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"id"))){
            throw new RRException("主键不能为空！");
        }
        List<Map<String,Object>> data = mapper.Select_HP_Scheme_Indicator_List(obj);
        if(data != null && data.size() > 0){
            return data.get(0);
        }
        return null;
    }

    /**
     * 修改指标
     *
     * @param obj
     * @return
     */
    @Override
    public String UpdateHPSchemeIndicator(Map<String, Object> obj) {
        String id = DataUtils.getMapKeyValue(obj,"id");
        if(StringUtils.isBlank(id)){
            throw new RRException("主键不能为空！");
        }
        mapper.Update_HP_Scheme_Indicator(obj);
        return id;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer DeleteHPSchemeIndicator(String id) {
        if(StringUtils.isBlank(id)){
            throw new RRException("主键不能为空！");
        }
        return mapper.Delete_HP_Scheme_Indicator(id);
    }

    /**
     * 保存选择的指标
     *
     * @param obj
     * @return
     */
    @Override
    public Integer ChooseHPSchemeIndicatorSave(Map<String, Object> obj) {
        /**
         * 判断是否选择了指标
         */
        String indid = DataUtils.getMapKeyValue(obj,"indid");
        String schemeid = DataUtils.getMapKeyValue(obj,"schemeid");
        if (StringUtils.isBlank(indid)) {
            throw new RRException("请选择指标！");
        }
        if (StringUtils.isBlank(schemeid)) {
            throw new RRException("方案ID不能为空！");
        }
        /**
         * 插入父级指标
         */
        InertParentHPScheme(indid,schemeid);
        /**
         * 插入选中的指标
         */
        mapper.Insert_Choose_HP_Scheme_Indicator(indid,schemeid);
        /**
         * 插入子级指标
         */
        InertChildrenHPScheme(indid,schemeid);
        return indid.split(",").length;
    }

    /**
     * 插入父级指标
     * @param indid
     * @param schemeid
     */
    private void InertParentHPScheme(String indid,String schemeid){
        if (StringUtils.isBlank(indid)) {
            throw new RRException("请选择指标！");
        }
        if (StringUtils.isBlank(schemeid)) {
            throw new RRException("方案ID不能为空！");
        }
        /**
         * 查询父级指标
         */
        List<Map<String,Object>> data = mapper.Select_Parent_HP_Scheme_indList(indid);
        data.stream().forEach(item ->{
            String parentid = DataUtils.getMapKeyValue(item,"parentid");
            if(StringUtils.isNotBlank(parentid)){
                /**
                 * 插入父级指标
                 */
                InertParentHPScheme(parentid,schemeid);
                /**
                 * 插入本级指标
                 */
                mapper.Insert_Choose_HP_Scheme_Indicator(parentid,schemeid);
            }
        });
    }

    /**
     * 插入子级指标
     * @param indid
     * @param schemeid
     */
    private void InertChildrenHPScheme(String indid,String schemeid){
        if (StringUtils.isBlank(indid)) {
            throw new RRException("请选择指标！");
        }
        if (StringUtils.isBlank(schemeid)) {
            throw new RRException("方案ID不能为空！");
        }
        /**
         * 查询子级指标
         */
        List<Map<String,Object>> data = mapper.Select_Children_HP_Scheme_indList(indid);
        data.stream().forEach(item ->{
            String childrenid = DataUtils.getMapKeyValue(item,"indid");
            if(StringUtils.isNotBlank(childrenid)){
                /**
                 * 插入子级级指标
                 */
                InertChildrenHPScheme(childrenid,schemeid);
                /**
                 * 插入本级指标
                 */
                mapper.Insert_Choose_HP_Scheme_Indicator(childrenid,schemeid);
            }
        });
    }


    /**
     * 批量保存
     *
     * @param obj
     * @return
     */
    @Transactional
    @Override
    public Integer HPSchemeIndicatornBatchSave(Map<String, Object> obj) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        // 批量的保存的mapper
        HP_Scheme_Indicator_Mapper batchMapper = sqlSession.getMapper(HP_Scheme_Indicator_Mapper.class);
        String jsonStr = DataUtils.getMapKeyValue(obj,"schemeindicatorn");
        String id = DataUtils.getMapKeyValue(obj,"id");
        String schemeid = DataUtils.getMapKeyValue(obj,"schemeid");
        String indid = DataUtils.getMapKeyValue(obj,"indid");
        if(StringUtils.isBlank(jsonStr)){
            throw new RRException("批量保存数据不能为空！");
        }
        if(StringUtils.isBlank(schemeid)){
            throw new RRException("方案ID不能为空！");
        }
        /**
         * 判断参数是否填写了
         */
        if (StringUtils.isBlank(id)){
            throw new RRException("父级指标ID不能为空为空");
        }
        // 获取批量保存的数据
        List<Map<String,Object>> list = JSONObject.parseObject(jsonStr, ArrayList.class);
        for(Map<String,Object> item:list){
            item.put("schemeid",schemeid);
            item.put("hpsciid",item.get("id"));
            item.put("bulkid",item.get("stcd"));
            // 先删除
            batchMapper.Delete_HP_Scheme_Indicator_Score(item);
            // 后保存
            batchMapper.Insert_HP_Scheme_Indicator_Score(item);
        }
        // 提交批量保存的数据
        sqlSession.commit();
        sqlSession.clearCache();
        // 计算 额外指标分数 和 应得的分数
        Double[] scores = CalIndScore(list,id);
        // 计算额外指标分数
        Double otherInd = scores[0];

        // 计算该级别指标应得的分数
        Double Ind = scores[1];

        // 保存本级指标分数
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("id",id);
        paramsMap.put("score",Ind);
        paramsMap.put("otherscore",otherInd);
        mapper.Update_HP_Scheme_Indicator(paramsMap);
        //递归计算父级指标的得分

        Map<String,Object> parentData = mapper.Seletc_Parent_Ind_Info(schemeid,indid);
        if(parentData != null && StringUtils.isNotBlank(DataUtils.getMapKeyValue(parentData,"parentid"))){
            CalParentIndScore(schemeid,DataUtils.getMapKeyValue(parentData,"parentid"));
        }
        // 更新指标得分
//        mapper.Insert_Lake_Healthy_Info(schemeid);
        mapper.Update_Lake_Bliss_Total_Info(schemeid);
        mapper.Update_HP_scheme_Score(schemeid);
        return list.size();
    }
    private void CalParentIndScore(String schemeid,String parenid){
        mapper.Update_Parent_Ind_Score(schemeid,parenid);
        /**
         * 查询父级指标的信息
         */
        Map<String,Object> parentData = mapper.Seletc_Parent_Ind_Info(schemeid,parenid);
        if(parentData != null && StringUtils.isNotBlank(DataUtils.getMapKeyValue(parentData,"parentid"))){
            CalParentIndScore(schemeid,DataUtils.getMapKeyValue(parentData,"parentid"));
        }
    }
    /**
     * 计算指标的得分
     */
    private Double[] CalIndScore(List<Map<String,Object>> list,String parentid){
        // 通过指标ID分组
        Map<String,List<Map<String,Object>>> dataMap = list.stream().collect(Collectors.groupingBy(item ->{
            String id = DataUtils.getMapKeyValue(item,"id");
            return StringUtils.isBlank(id)?"00":id;
        }));
        // 存放每个指标的值
        Map<Object,Double> itemMap = new HashMap<>();
        for(Object key:dataMap.keySet()){
            List<Map<String,Object>> indList  = dataMap.get(key);
            // 算总和
            OptionalDouble avgDouble = indList.stream().mapToDouble(item -> DataUtils.getMapDoubleValue(item,"score")).reduce(Double::sum);
            // 计算每个指标的平均值
            itemMap.put(key,avgDouble.getAsDouble()/indList.size());
        }

        // 查询计算公式
        Map<String,Object> calMap = calIndScoreMapper.Select_Ind_Formula(parentid);
        //查询参数对应的指标
        List<Map<String,Object>> paramList = calIndScoreMapper.Select_Param_Ind_List(parentid);
        // 处理查询到的结果
        Map<String,List<Map<String,Object>>> paramMap = paramList.stream().collect(Collectors.groupingBy(item -> {
            String keystr = DataUtils.getMapKeyValue(item,"param_type");
            return StringUtils.isBlank(keystr)?"00":keystr;
        })) ;
        // 开始计算,先判断是否具有中间指标值
         double otherscore = CalOtherIndScore(DataUtils.getMapKeyValue(calMap,"formula1"),paramMap.get("01"),itemMap,0.0);
        // 在计算最后的分数
        double score = CalOtherIndScore(DataUtils.getMapKeyValue(calMap,"formula"),paramMap.get("00"),itemMap,otherscore);
        return new Double[]{otherscore,score};
    }
    // 计算指标分数
    private Double CalOtherIndScore(String formula,List<Map<String,Object>> paramList,Map<Object,Double> itemMap,Double scale){
        if(StringUtils.isBlank(formula)){
            double score = 0.0;
            for(Object key:itemMap.keySet()){
                score += itemMap.get(key);
            }
            return score/itemMap.keySet().size();
        }
        for(String key:methodMap.keySet()){
            // 通过反射执行方法
            if(formula.contains(key)){
                Method method = methodMap.get(key);
                Object score = null;
                try{
                    String params = formula.replaceAll(key,"").replaceAll("\\(","")
                            .replaceAll("\\)","").replaceAll("score",Double.toString(scale));
                    score = method.invoke(HPCalIndScore.class.newInstance(),params.split(","));
                }catch (Exception e){e.printStackTrace();}
                if(score != null && StringUtils.isNotBlank(score.toString())){
                    return Double.parseDouble(score.toString());
                }else {
                    double score1 = 0.0;
                    for(Object key1:itemMap.keySet()){
                        score1 += itemMap.get(key1);
                    }
                    return score1/itemMap.keySet().size();
                }
            }
        }
        Map<String,Object> params = new HashMap<>();
        try {
        paramList.stream().forEach(item ->{
            String target_id = DataUtils.getMapKeyValue(item,"targetid");
            String parameter = DataUtils.getMapKeyValue(item,"parameter");
            params.put(parameter,itemMap.get(target_id));
        });
        // 执行计算语句
            Object score = EnforcementUtils.executeString(formula,params,true);
            if(score != null && StringUtils.isNotBlank(score.toString())){
                return Double.parseDouble(score.toString());
            }
        }catch (Exception e){
            double score = 0.0;
            for(Object key:itemMap.keySet()){
                score += itemMap.get(key);
            }
            return score/itemMap.keySet().size();
        }
        return 100.0;
    }

    /**
     * 根据方案id和指标id查询子级指标
     * @param obj
     * @return
     */
    @Override
    public Map<String, Object> GetForIndInfoList(Map<String, Object> obj) {
        //
        String schemeid = DataUtils.getMapKeyValue(obj,"schemeid");
        // 指标id
        String indid = DataUtils.getMapKeyValue(obj,"indid");
        // 主键
        String id = DataUtils.getMapKeyValue(obj,"id");
        /**
         * 判断参数是否填写了
         */
        if (StringUtils.isBlank(schemeid)){
            throw new RRException("方案ID不能为空");
        }
        if (StringUtils.isBlank(indid)){
            throw new RRException("指标id为空");
        }
        if (StringUtils.isBlank(id)){
            throw new RRException("主键为空");
        }
        /**
         * 查询子级指标信息
         */
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("schemeid",schemeid);
        queryMap.put("parentid",indid);
        List<Map<String,Object>> childrenList = mapper.Select_HP_Scheme_Indicator_List(queryMap);
        /**
         * 查询选中的指标
         */
        queryMap = new HashMap<>();
        queryMap.put("id",id);
        List<Map<String,Object>> parentList = mapper.Select_HP_Scheme_Indicator_List(queryMap);
        /**
         * 查询方案的站点评价信息
         */
        queryMap = new HashMap<>();
        queryMap.put("schemeid",schemeid);
        List<Map<String,Object>> stcdList = mapper.Select_hp_scheme_bulk_List(queryMap);
        /**
         * 处理子级指标
         * 给stcdlist分组
         */

        childrenList.stream().forEach(item ->{
            Map<String,Object> dataMap = new HashMap<>();
            /**
             * 获取站点的得分list
             */
            List<Map<String,Object>> stcdscoreList = mapper.Select_hp_scheme_bulk_score_List(schemeid,DataUtils.getMapKeyValue(item,"id"));
            stcdscoreList.stream().forEach(map ->{
                dataMap.put(DataUtils.getMapKeyValue(map,"bulkid"),map.get("score"));
            });
            item.put("data",dataMap);
        });
        /**
         * 最后返回的数据
         */
        Map<String,Object> data = new HashMap<>();
        // 列
        data.put("column",stcdList);
        // 行
        data.put("row",childrenList);
        // 父级指标描述
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object>  otherMap = mapper.Select_HP_indicator_format_list(indid);
        if(parentList != null && parentList.size() > 0){
            data.put("desc",parentList.get(0).get("formuladesc"));
            Map<String,Object> resultlistMap = new HashMap<>();
            resultlistMap.put("indid",indid);
            resultlistMap.put("title",parentList.get(0).get("indname"));
            resultlistMap.put("score",parentList.get(0).get("score"));
            resultList.add(resultlistMap);

        }
        if(otherMap != null){
            if(parentList != null && parentList.size() > 0){
                otherMap.put("score",parentList.get(0).get("otherscore"));
            }
            resultList.add(otherMap);
        }
        data.put("result",resultList);
        return data;
    }

    /**
     * 根据方案id获取二级指标雷达图
     *
     * @param obj
     * @return
     */
    @Override
    public List<Map<String, Object>> GetIndRadarInfo(Map<String, Object> obj) {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"schemeid"))){
            throw new RRException("请选择方案！");
        }
        List<Map<String,Object>> data = mapper.Select_Ind_Radar_Info(obj);
        OptionalDouble maxValue = data.stream().mapToDouble(item -> DataUtils.getMapDoubleValue(item,"score")).reduce(Double::max);
        OptionalDouble minValue = data.stream().mapToDouble(item -> DataUtils.getMapDoubleValue(item,"score")).reduce(Double::min);
        data.stream().forEach(item->{
            item.put("max",maxValue.getAsDouble());
            item.put("min",minValue.getAsDouble());
        });
        return data;
    }

}
