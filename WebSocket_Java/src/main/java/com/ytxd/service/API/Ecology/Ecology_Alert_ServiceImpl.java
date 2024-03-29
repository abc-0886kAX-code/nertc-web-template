package com.ytxd.service.API.Ecology;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.API.Ecology.Ecology_Alert_Mapper;
import com.ytxd.service.CommonService;
import com.ytxd.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生态报警
 */
@Service
public class Ecology_Alert_ServiceImpl implements Ecology_Alert_Service {
    @Resource
    private Ecology_Alert_Mapper mapper;
    @Resource
    private CommonService commonService;
    /**
     * 获取报警站点信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetEcologyAlertList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        /**
         * 写入默认值
         */
        List<Map<String,Object>> data = mapper.GetEcologyAlertList(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 通过id获取报警站点信息
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> GetEcologyAlertListById(String id) throws Exception {
        if(StringUtil.isEmpty(id)){
            throw new RRException("id 不能为空！");
        }
        /**
         * 写入id
         */
        Map<String,Object> obj = new HashMap<>();
        obj.put("id",id);
        List<Map<String,Object>> data = mapper.GetEcologyAlertList(obj);
        if(data == null || data.size() == 0){
            throw new RRException("暂无数据");
        }
        return data.get(0);
    }

    /**
     * 解除报警
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Integer RelieveEcologyAlert(Map<String, Object> obj) throws Exception {
        /**
         * 验证必填项是否已经填写
         */
        String id = DataUtils.getMapKeyValue(obj,"id");
        if(StringUtil.isEmpty(id)){
            throw new RRException("id不能为空！");
        }
        if(StringUtil.isEmpty(DataUtils.getMapKeyValue(obj,"hand_result"))){
            throw new RRException("预警处理结果不能为空！");
        }
        obj.put("idarray",id.split(","));
        Integer flag = mapper.UpdateEcologyAlert(obj);
        return flag;
    }

    /**
     * 获取管理者名录
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetManagerList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.GetManagerList(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }


}
