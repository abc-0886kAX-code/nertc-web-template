package com.ytxd.dao.SystemManage;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ytxd.model.SystemManage.SM_Router;

@Mapper
public interface SM_Router_Mapper {
	/**
	 * 获取路由列表
	 * @param obj
	 * @return
	 */
	public List<SM_Router> Select_SM_Route(SM_Router obj);

	/**
	 * 新增路由
	 * @param obj
	 * @return
	 */
	public Integer Insert_SM_Route(SM_Router obj);

	/**
	 * 修改路由
	 * @param obj
	 * @return
	 */
	public Integer Update_SM_Route(SM_Router obj);

	/**
	 * 删除路由
	 * @param id
	 * @return
	 */
	public Integer Delete_SM_Route(String ...id);

	/**
	 * 删子节点
	 * @param id
	 * @return
	 */
	public Integer Delete_SM_RouteByPid(String ...id);

}
