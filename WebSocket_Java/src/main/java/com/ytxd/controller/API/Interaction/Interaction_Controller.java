package com.ytxd.controller.API.Interaction;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("Interaction_Controller")
@RequestMapping("/Interaction")
public class Interaction_Controller {

    @Resource
    private CommonService commonService;

    /**
     * 交互屏-获取所有的DNA样品监测数据报告
     */
    @PostMapping("/GetDNAdataReport")
    public R GetDNAdataReport(HttpServletRequest request) throws Exception {
        int total = 0;
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("st_dna_r");
        mySqlData.setSelectField("date","tm date");
        mySqlData.setSelectField("imgUrl","ifnull(imagesurl,'') imgUrl");
        mySqlData.setSelectField("pdfurl","ifnull(pdfurl,'') pdfurl");
        mySqlData.setSort("tm");
        mySqlData.setOrder("desc");
        if (StringUtils.isNotBlank(request.getParameter("currentPage"))) {
            try {
                mySqlData.setPage(Integer.parseInt(request.getParameter("currentPage")));
            } catch (Exception ex) {
                return R.error("currentPage参数不是整数，请修改。");
            }
        }
        if (StringUtils.isNotBlank(request.getParameter("pageSize"))) {
            try {
                mySqlData.setRows(Integer.parseInt(request.getParameter("pageSize")));
            } catch (Exception ex) {
                return R.error("pageSize参数不是整数，请修改。");
            }
        }
        List<HashMap<String, Object>> data = commonService.getDataList(mySqlData);
        if (data != null) {
            if (StringUtils.isNotBlank(request.getParameter("pageSize")) && StringUtils.isNotBlank(request.getParameter("currentPage"))) {// 分页查询，还要查询总记录条数
                total = commonService.getListCount(mySqlData);
            }
        }
        return R.ok().put("data",data).put("total",total);
    }

    /**
     * 交互屏-随机取10条趣味答题
     */
    @GetMapping("/GetFunquiz")
    public R GetFunquiz(HttpServletRequest request) throws Exception {
        String[] optiontitlelist = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        List<Map<String,Object>> resultlist = new ArrayList<>();
        MySqlData titlesql = new MySqlData();
        titlesql.setSql("select title.*,alloption.optionname from ");;
        titlesql.setSql("(select * from qa_title where id >= (SELECT floor(RAND() * (SELECT MAX(id) FROM qa_title))) LIMIT 10) title ");;
        titlesql.setSql("LEFT JOIN (select titleid,group_concat(CONCAT(`option`,'&answer=',answer) ORDER BY orderid SEPARATOR '|>--<|') optionname from qa_option group by titleid) alloption on title.id = alloption.titleid");
        List<HashMap<String, Object>> data = commonService.getDataList(titlesql);
        while (data.size() != 10){ //确保每次能取到10条数据
            data = commonService.getDataList(titlesql);
        }
        for(int i = 0;i<data.size();i++){
            Map<String,Object> titleinfo = data.get(i);
            String title = DataUtils.getMapKeyValue(titleinfo,"title");
            String optioninfos = DataUtils.getMapKeyValue(titleinfo,"optionname");
            titleinfo = new HashMap<>();
            String correctAnswer = "";
            titleinfo.put("problem",title);

            List<Map<String,Object>> optionlist = new ArrayList<>();
            if(StringUtils.isNotBlank(optioninfos)){
                String[] optioninfo = optioninfos.split("\\|>--<\\|");
                for (int j = 0;j<optioninfo.length;j++) {
                    String option = optioninfo[j];
                    String optionname = option.split("&answer=")[0];
                    String optiontitle = option.split("&answer=")[1];
                    if(StringUtils.isNotBlank(optiontitle) && optiontitle.equals("01")){
                        correctAnswer = optiontitlelist[j];
                    }
                    Map<String,Object> optionmap = new HashMap<>();
                    optionmap.put("answer",optiontitlelist[j]);
                    optionmap.put("content",optionname);
                    optionlist.add(optionmap);
                }
            }
            titleinfo.put("option",optionlist);
            titleinfo.put("correctAnswer",correctAnswer);
            titleinfo.put("selectAnswer","");
            resultlist.add(titleinfo);
        }
        return R.ok().put("data",resultlist);
    }

    /**
     * 交互屏-获取所有的科普视屏
     */
    @GetMapping("/GetAllPopularScienceVideo")
    public R GetAllPopularScienceVideo(HttpServletRequest request) throws Exception {
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("zsk_video");
        mySqlData.setSelectField("id","id");
        mySqlData.setSelectField("title","name title");
        mySqlData.setSelectField("videourl","video videourl");
        mySqlData.setSelectField("img","IFNULL(cover,'') img");
        mySqlData.setSort("id");
        List<HashMap<String, Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data",data);
    }

    /**
     * 交互屏-满意度调查
     */
    @PostMapping("/SaveSatisfaction")
    public R SaveSatisfaction(MultipartHttpServletRequest request) throws Exception {
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("st_satisfaction");
        mySqlData.setViewName("add");
        commonService.insert(request,mySqlData);
        return R.ok();
    }

}
