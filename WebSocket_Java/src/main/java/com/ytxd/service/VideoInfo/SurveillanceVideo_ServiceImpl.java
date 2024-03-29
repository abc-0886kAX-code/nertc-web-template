package com.ytxd.service.VideoInfo;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.VideoInfo.SurveillanceVideo_Mapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SurveillanceVideo_ServiceImpl
 * @Author TY
 * @Date 2024/1/5 16:59
 * @Description TODO 视频监控站点
 */
@Service
public class SurveillanceVideo_ServiceImpl implements SurveillanceVideo_Service{
    @Resource
    private SurveillanceVideo_Mapper mapper;
    /**
     *
     * @Desription TODO 查询视频监控站点
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/1/5 17:00
     * @Auther TY
     */
    @Override
    public PageInfo getSurveillanceVideoList(Map<String, Object> obj) {
        DataUtils.Padding(obj);
        List<HashMap<String,Object>> data = mapper.Select_SurveillanceVideo_List(obj);
        return new PageInfo(data);
    }
}
