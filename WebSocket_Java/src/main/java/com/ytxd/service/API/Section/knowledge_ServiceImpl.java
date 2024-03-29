package com.ytxd.service.API.Section;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.API.Section.knowledge_Mapper;
import com.ytxd.dao.SystemManage.SM_CodeItem_Mapper;
import com.ytxd.model.SystemManage.SM_CodeItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 科普知识Service
 */
@Service
public class knowledge_ServiceImpl implements knowledge_Service{
    @Resource
    private SM_CodeItem_Mapper itemMapper;
    @Resource
    private knowledge_Mapper mapper;
    /**
     * 获取科普知识的类型
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetKnowledgeTypeList() throws Exception {
        /**
         * 设置默认条件
         */
        SM_CodeItem obj = new SM_CodeItem();
        obj.setcodeid("ZK");
        obj.setflag("01");
        obj.setSort("orderid");
        obj.setOrder("asc");
        List<SM_CodeItem> list = itemMapper.Select_SM_CodeItem(obj);
        List<Map<String,Object>> data = new LinkedList<>();
        if(list != null && list.size()>0){
            /**
             * 对数据进行处理
             */
            list.stream().forEach(sm ->{
                Map<String,Object> map = new HashMap<>();
                map.put("title",sm.getdescription());
                map.put("imgurl",sm.getshortname());
                map.put("code",sm.getcode());
                data.add(map);
            });
        }else {
            throw new RRException("抱歉未查询到分类信息！");
        }
        return data;
    }

    /**
     * 获取科普知识列表
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetKnowledgeList(Map<String, Object> obj) {
        /**
         * 分页判断
         */
        DataUtils.Padding(obj);
        /**
         * 默认排序
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"sort"))){
            obj.put("sort","orderid");
        }
        List<Map<String,Object>> data = mapper.GetKnowledgeList(obj);
        /**
         * 数据处理
         */
        data.stream().forEach(Map -> {
            String images = DataUtils.getMapKeyValue(Map,"images");
            if(StringUtils.isNotBlank(images)){
                Map.put("images",images.split("\\|"));
            }
        });

        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 生物概览
     *
     * @param obj
     * @return
     */
    @Override
    public List<Map<String, Object>> GetBiologicalList(Map<String, Object> obj) {
        return mapper.GetBiologicalList();
    }
}
