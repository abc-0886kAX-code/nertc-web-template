package com.ytxd.service.API;

import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.model.ActionResult;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import com.ytxd.util.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController("/Common_API_Controller")
@RequestMapping("/Common/API")
public class Common_API_Controller {
    @Resource
    private CommonService service;

    /**
     * 获取列表(公用的接口)
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetListByName")
    public R GetListByName(HttpServletRequest request) throws Exception{
        String tablename = request.getParameter("tablename");
        if(StringUtil.isEmpty(tablename)){
            throw new RRException("tablename不能为空！");
        }
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName(tablename);
        mySqlData.setViewName("add");
        ActionResult result = service.getList(request,mySqlData);
        if(!result.getSuccess()){
            throw new RRException(result.getMsg());
        }
        return  R.ok().put("data",result.getData()).put("total",result.getTotal());
    }
}
