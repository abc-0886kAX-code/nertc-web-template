package com.ytxd.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.ytxd.dao.SystemManage.SM_FunctionTree_Mapper;
import com.ytxd.model.SystemManage.SM_FunctionTree;


@Service("MyFilterInvocationSecurityMetadataSource")
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private SM_FunctionTree_Mapper SM_FunctionTree_Mapper;

	//private HashMap<String, Object> map = null;
	private List<String> list=null;
	/**
	 * 加载权限表中所有权限
	 */
	public void loadResourceDefine() {
		//map = new HashMap<String, Object>();
	    list=new ArrayList<String>();
		List<SM_FunctionTree> permissions = SM_FunctionTree_Mapper.Select_SM_FunctionTreeUrlNotNull();
		String navigateurl="";
		for (SM_FunctionTree SM_FunctionTree : permissions) {
//			ConfigAttribute cfg = new SecurityConfig(SM_FunctionTree.getid());
//			List<ConfigAttribute> list = new ArrayList<>();
//			list.add(cfg);
			navigateurl=SM_FunctionTree.getnavigateurl();
			if(navigateurl.indexOf(".")!=-1){
				navigateurl=navigateurl.substring(0, navigateurl.indexOf("."));
			}
			//map.put(SM_FunctionTree.getid(), navigateurl);
			list.add(navigateurl);
		}

	}

	/**
	 * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法， 用来判定用户
	 * 是否有此权限。如果不在权限表中则放行。
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		if (list==null||list.size()==0) {
			loadResourceDefine();
		}
		// object 中包含用户请求的requestuo的信息
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		String urlReq=request.getServletPath();
		if(urlReq.indexOf(".")!=-1){
			urlReq=urlReq.substring(0, urlReq.indexOf("."));
		}
//		for (Entry<String, Object> entry : map.entrySet()) {
//			String url = entry.getKey();
//			//if (new AntPathRequestMatcher(url).matches(request)) {
//			if(url.equals(urlReq)){
//				return map.get(url);
//			}
//		}
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(urlReq)){
				ConfigAttribute cfg = new SecurityConfig(list.get(i));
				List<ConfigAttribute> list = new ArrayList<>();
				list.add(cfg);
				return list;
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
