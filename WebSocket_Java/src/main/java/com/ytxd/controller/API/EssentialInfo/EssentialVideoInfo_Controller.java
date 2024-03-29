package com.ytxd.controller.API.EssentialInfo;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.API.EssentialInfo.EssentialVideoInfo_Service;
import com.ytxd.service.CommonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 视频相关
 */
@RestController("EssentialVideoInfo_Controller")
@RequestMapping("/EssentialInfo/Video")
public class EssentialVideoInfo_Controller {
    @Resource
    private CommonService commonService;
    @Resource
    private EssentialVideoInfo_Service service;

    /**
     * 获取视频站点列表
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetVideoInfoList")
    public R GetVideoInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        PageInfo pageInfo = service.Select_VideoInfo_List(obj);
        return R.ok().put("data",pageInfo.getList()).put("total",pageInfo.getTotal());
    }

    /**
     * 通过stcd获取视频站点列表
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("GetVideoInfoByStcd")
    public R GetVideoInfoByStcd(HttpServletRequest request,String stcd) throws Exception{
        return R.ok().put("data",service.Select_VideoInfoByStcd(stcd));
    }
    /**
     * 通过stcd获取视频站点记录信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("GetVideoItemInfoByStcd")
    public R GetVideoItemInfoByStcd(HttpServletRequest request,String stcd) throws Exception{
        return R.ok().put("data",service.Select_VideoInfoItem_List(stcd));
    }
}
