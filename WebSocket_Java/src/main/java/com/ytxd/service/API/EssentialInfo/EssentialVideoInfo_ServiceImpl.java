package com.ytxd.service.API.EssentialInfo;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.API.EssentialInfo.EssentialVideoInfo_Mapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视频相关
 */
@Service
public class EssentialVideoInfo_ServiceImpl implements EssentialVideoInfo_Service{
    @Resource
    private EssentialVideoInfo_Mapper mapper;
    /**
     * 获取视频站点信息（带视频信息）
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo Select_VideoInfo_List(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_VideoInfo_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 通过stcd获取视频站点信息（带视频信息）
     *
     * @param stcd
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> Select_VideoInfoByStcd(String stcd) throws Exception {
        Map<String, Object> obj = new HashMap<>();
        if(StringUtils.isBlank(stcd)){
            throw new RRException("stcd不能为空！");
        }
        obj.put("stcd",stcd);
        List<Map<String,Object>> data = mapper.Select_VideoInfo_List(obj);
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
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> Select_VideoInfoItem_List(String stcd) throws Exception {
        if(StringUtils.isBlank(stcd)){
            throw new RRException("sctd不能为空！");
        }
        return mapper.Select_VideoInfoItem_List(stcd);
    }
}
