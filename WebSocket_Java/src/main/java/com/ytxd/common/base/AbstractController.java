package com.ytxd.common.base;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytxd.common.util.MPPageConvert;
import com.ytxd.model.SysUser;

/**
 * Controller公共组件
 * 
 * @author ytxd
 * @email 2625100545@qq.com
 * @date 2019年11月9日 下午9:42:26
 */

public abstract class AbstractController {
	@Autowired
	protected MPPageConvert mpPageConvert;

	protected SysUser getUser() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}

	protected String getUserId() {
		return getUser().getUserId();
	}
}
