package com.ytxd.controller.API.EssentialInfo;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.API.EssentialInfo.EssentialDNAInfo_Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController("/EssentialDNAInfo_Controller")
@RequestMapping("/EssentialInfo/DNA")
public class EssentialDNAInfo_Controller {
    @Resource
    private EssentialDNAInfo_Service service;

    /**
     * 获取DNA站点信息（带图片）
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetDNAInfoList")
    public R GetDNAInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        PageInfo pageInfo = service.Select_DNAInfo_List(obj);
        return R.ok().put("data",pageInfo.getList()).put("total",pageInfo.getTotal());
    }

    /**
     * 通过stcd获取DNA站点信息（带图片）
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetDNAInfoByStcd")
    public R GetDNAInfoByStcd(HttpServletRequest request,String stcd) throws Exception{
        return R.ok().put("data",service.Select_DNAInfoByStcd(stcd));
    }

    /**
     * 通过stcd获取DNA站点的检测数据
     * @param request
     * @param stcd
     * @return
     * @throws Exception
     */
    @GetMapping("/GetDNAItemInfoByStcd")
    public R GetDNAItemInfoByStcd(HttpServletRequest request,String stcd) throws Exception{
        return R.ok().put("data",service.Select_DNAInfoItem_List(stcd));
    }
}
