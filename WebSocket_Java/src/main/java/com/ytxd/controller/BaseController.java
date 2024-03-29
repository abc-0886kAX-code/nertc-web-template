package com.ytxd.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.ytxd.model.SysUser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ytxd.util.UrlUtil;

public class BaseController {
	
	/**
	 * 记录日志
	 */
	protected final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * 图片服务器地址
	 */
	protected static final String imagePath = UrlUtil.imageUrl();
	
	/**
	 * 图片上传路径
	 */
	protected static final String imageUploadPath = UrlUtil.imageUploadPath();

	private static final List<String> permissionsName = Arrays.asList("_Insert", "_Update", "_Delete", "_Import");
	private static final List<String> permissions = Arrays.asList("add", "edit", "delete", "import");
	
//	public boolean HavePower(HashMap<String, String> powerList, String powerCode) {
//		if(powerList.containsKey(powerCode.toLowerCase())){
//			return true;
//		} else {
//			return false;
//		}
//	}
	
//	public <T extends BaseBO> T SetSubmit(T model,SysUser obj){
//		model.setSubmitman(obj.getUserId().toString());
//		model.setSubmitmanname(obj.getSubmitmanname());
//		model.setSubmittime(DateFormat.getDateInstance().format(new Date()));
//		return model;
//	}
protected List<String> getPower(SysUser user, String tableName){
	List<String> powerList= new ArrayList<>();
	for (int i = 0; i < permissionsName.size(); i++) {
		if(user.getFunctionPermissions(tableName + permissionsName.get(i))){
			powerList.add(permissions.get(i));
		}
	}
	return powerList;
}
}