package com.ytxd.controller.API.EssentialInfo;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.model.ActionResult;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站点基本信息
 */
@RestController("EssentialInfo_Controller")
@RequestMapping("/EssentialInfo")
public class EssentialInfo_Controller {
    @Resource
    private CommonService commonService;

    /**
     * 获取站点类型
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetSiteInfoList")
    public R GetSiteInfoList(HttpServletRequest request) throws Exception{
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("st_stbprp_b");
        mySqlData.setViewName("add");
        String sttp = request.getParameter("sttp");
        if(StringUtils.isNotBlank(sttp)){
            mySqlData.setFieldWhere("sttp",sttp,"=");
        }
        String stcd = request.getParameter("stcd");
        if(StringUtils.isNotBlank(stcd)){
            mySqlData.setFieldWhere("stcd",stcd,"=");
        }
        String stnm = request.getParameter("stnm");
        if(StringUtils.isNotBlank(stnm)){
            mySqlData.setFieldWhere("stnm",stnm,"like");
        }
        ActionResult result = commonService.getList(request,mySqlData);
        if(result.getSuccess()){
            return R.ok().put("data",result.getData()).put("total",result.getTotal());
        }else {
            throw  new RRException(result.getMsg());
        }
    }

    /**
     * 获取一链五珠信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetScenicSpotList")
    public R GetScenicSpotList(HttpServletRequest request) throws Exception{
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("SCENIC_SPOT");
        mySqlData.setViewName("add");
        mySqlData.setFieldWhere("flag","01","=");
        List<HashMap<String,Object>> data = commonService.getJoinDataList(mySqlData);
        if(data != null && data.size() > 0){
            data.stream().forEach(Map ->{
                String images = DataUtils.getMapKeyValue(Map,"imagesurl");
                if(StringUtils.isNotBlank(images)){
                    Map.put("imagesurl",images.split("\\|"));
                }
                String filepath = DataUtils.getMapKeyValue(Map,"filepath");
                if(StringUtils.isNotBlank(filepath)){
                    Map.put("filepath",filepath.split("\\|"));
                }
            });
        }
        return R.ok().put("data",data);
    }
}
