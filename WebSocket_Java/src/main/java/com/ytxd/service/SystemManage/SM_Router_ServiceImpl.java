package com.ytxd.service.SystemManage;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytxd.dao.SystemManage.SM_Router_Mapper;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_Router;
import com.ytxd.service.impl.BaseServiceImpl;

@Service("SM_Router_ServiceImpl")
public class SM_Router_ServiceImpl extends BaseServiceImpl implements SM_Router_Service {
	@Autowired
	private SM_Router_Mapper mapper;

	/**
	 * 获取列表
	 *
	 * @param route
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageInfo GetSMRouterList(SM_Router route, SysUser user) throws Exception {
		DataUtils.Padding(route);
		List<SM_Router> list = mapper.Select_SM_Route(route);
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}

	/**
	 * 根据用户权限获取路由
	 *
	 * @param route
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageInfo GetSMRouterListByUser(SM_Router route, SysUser user) throws Exception {
		DataUtils.Padding(route);
		List<SM_Router> list = mapper.Select_SM_Route(route);
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}

	/**
	 * 根据id获取信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public SM_Router GetSMRouterListById(String id) throws Exception {
		if(StringUtils.isBlank(id)){
			throw new RRException("id不能为空！");
		}
		SM_Router obj = new SM_Router();
		obj.setId(Integer.parseInt(id));
		List<SM_Router> list = mapper.Select_SM_Route(obj);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else {
			throw new RRException("未查询到信息！");
		}
	}

	/**
	 * 保存信息
	 *
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer SM_Router_Save(SM_Router obj) throws Exception {
		Integer total = 0;
		if(obj.getId() == null ){
			total = mapper.Insert_SM_Route(obj);
		}else {
			 total = mapper.Update_SM_Route(obj);
		}
		if(total > 0 ){
			return total;
		}else {
			throw new RRException("操作失败！");
		}
	}

	/**
	 * 删除信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer SM_Router_Delete(String id) throws Exception {
		if(StringUtils.isBlank(id)){
			throw new RRException("id不能为空！");
		}
		Integer total = mapper.Delete_SM_Route(id.split(","));
		if(total > 0){
			return  total;
		}else {
			throw new RRException("删除失败！");
		}
	}
}
