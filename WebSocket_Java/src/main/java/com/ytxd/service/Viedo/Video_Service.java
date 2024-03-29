package com.ytxd.service.Viedo;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 视频相关
 */
public interface Video_Service {
    /**
     * 获取视频站点信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetVideoInfoList(Map<String,Object> obj) throws Exception;

    /**
     * 开启视频转化
     * @param obj
     * @return
     * @throws Exception
     */
    public void OpenVideConversion(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 刷新视频站点的url
     * @param deviceSerial
     * @return void
     * @date 2024/2/26 10:48
     * @Auther TY
     */
    public void refreshVideUrl(String ...deviceSerial) throws Exception;
}
