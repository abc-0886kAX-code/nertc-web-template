package com.ytxd.controller.SystemManage;


import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.ActionResult;
import com.ytxd.service.CommonService;
import com.ytxd.util.FileUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *  控制层(前后台交互)
 */
@Controller("SM_FunctionConfig_Controller")
@RequestMapping(value = "/SystemManage/SM_FunctionConfig")
public class SM_FunctionConfig_Controller  extends BaseController{
	@Resource
	private CommonService service;

	@RequestMapping("/MyList")
	public String MyList(HttpServletRequest request, Model model) throws Exception {
		//获取保存的json文件，用于判断勾选了哪些内容
		String filePath = request.getSession().getServletContext().getRealPath("UpFile/");
    	String fileName="AMConfig.josn";
    	String jsonStr = FileUtil.readJsonToString(filePath+fileName);
	    model.addAttribute("jsonData", jsonStr.replaceAll("\"", "'"));
		return "SystemManage/SM_FunctionConfig/SM_FunctionConfig_MyList";
	}
	@RequestMapping("/Save")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
		ActionResult result = new ActionResult();
		HashMap<String, Object> DataMap=new HashMap<String, Object>();
		HashMap<String, Object> map=new HashMap<String, Object>();
		List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
		String Achievements = request.getParameter("Achievements");
		String [] AchievementsChild = request.getParameterValues("AchievementsChild");
		if(StringUtils.isNotBlank(Achievements)){
			map.put("name", Achievements);
			if(AchievementsChild!=null){
				map.put("child", Arrays.toString(AchievementsChild).replaceAll("\\[", "").replaceAll("\\]", ""));
			}else{
				map.put("child","");
			}
			list.add(map);
			map=new HashMap<String, Object>();
		}
		map.put("name", Achievements);
		String AW_Award = request.getParameter("AW_Award");
		String [] AW_AwardChild = request.getParameterValues("AW_AwardChild");
		if(StringUtils.isNotBlank(AW_Award)){
			map.put("name", AW_Award);
			if(AW_AwardChild!=null){
				map.put("child", Arrays.toString(AW_AwardChild).replaceAll("\\[", "").replaceAll("\\]", ""));
			}else{
				map.put("child","");
			}
			list.add(map);
			map=new HashMap<String, Object>();
		}
		String Other = request.getParameter("Other");
		String [] OtherChild = request.getParameterValues("OtherChild");
		if(StringUtils.isNotBlank(Other)){
			map.put("name", Other);
			if(OtherChild!=null){
				map.put("child", Arrays.toString(OtherChild).replaceAll("\\[", "").replaceAll("\\]", ""));
			}else{
				map.put("child","");
			}
			list.add(map);
			map=new HashMap<String, Object>();
		}
		String Academic = request.getParameter("Academic");
		String [] AcademicChild = request.getParameterValues("AcademicChild");
		if(StringUtils.isNotBlank(Academic)){
			map.put("name", Academic);
			if(AcademicChild!=null){
				map.put("child", Arrays.toString(AcademicChild).replaceAll("\\[", "").replaceAll("\\]", ""));
			}else{
				map.put("child","");
			}
			list.add(map);
		}
		DataMap.put("data", list);
		//将map转为josn并保存到服务器
		JSONObject jsonObject = JSONObject.fromObject(DataMap);
//		String content = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteDateUseDateFormat);
		String content=jsonObject.toString();
        // 标记文件生成是否成功
        boolean flag = true;
        // 生成json格式文件
        try {
            // 保证创建一个新文件
        	String filePath = request.getSession().getServletContext().getRealPath("UpFile/");
        	String fileName="AMConfig.josn";
			File file = new File(filePath+fileName);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(content);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
		if(flag) {
			return R.ok("保存成功");
		} else {
			return R.error("保存失败");
		}
	}
}
