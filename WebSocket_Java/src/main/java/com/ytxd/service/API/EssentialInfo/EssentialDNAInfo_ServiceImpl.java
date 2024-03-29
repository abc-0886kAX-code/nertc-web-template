package com.ytxd.service.API.EssentialInfo;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.API.EssentialInfo.EssentialDNAInfo_Mapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DNA站点相关
 */
@Service("EssentialDNAInfo_Service")
public class EssentialDNAInfo_ServiceImpl implements EssentialDNAInfo_Service{
    @Resource
    private EssentialDNAInfo_Mapper mapper;
    /**
     * 获取DNA站点信息(带图片信息)
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo Select_DNAInfo_List(Map<String, Object> obj) {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_DNAInfo_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 通过stcd获取DNA站点信息(带图片信息)
     *
     * @param stcd
     * @return
     */
    @Override
    public Map<String,Object> Select_DNAInfoByStcd(String stcd) {
        Map<String,Object> obj = new HashMap<>();
        obj.put("stcd",stcd);
        List<Map<String,Object>> data = mapper.Select_DNAInfo_List(obj);
        if(data != null && data.size() > 0){
            return data.get(0);
        }else {
            throw new RRException("暂无数据！");
        }

    }

    /**
     * 通过stcd获取信息
     *
     * @param stcd
     * @return
     */
    @Override
    public List<Map<String, Object>> Select_DNAInfoItem_List(String stcd) {
        if(StringUtils.isBlank(stcd)){
            throw new RRException("stcd不能为空！");
        }
        return mapper.Select_DNAInfoItem_List(stcd);
    }
}
