package com.ytxd.model.SystemManage;

import com.ytxd.model.BaseBO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SM_Router extends BaseBO {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 父节点id
	 */
	private Integer pid;
	/**
	 * name
	 */
	private String name;
	/**
	 * 路由地址
	 */
	private String address;
	/**
	 * 路由标题
	 */
	@NotBlank
	private String title;
	/**
	 * 路由图标
	 */
	private String icon;
	/**
	 * 重定向地址
	 */
	private String redirect;
	/**
	 * 路由工作模式
	 */
	private String mode;
	private String modename;
	/**
	 * 是否在菜单上渲染路由
	 */
	private Boolean menu;
	private String menuname;
	private Boolean menubl;
	/**
	 * 是否隐藏
	 */
	private Boolean hidden;
	private String hiddenname;
	private Boolean hiddenbl;
	/**
	 * 是否固定面包屑
	 */
	private Boolean affix;
	private String affixname;
	private Boolean affixbl;
	/**
	 * 是否启用
	 */
	private Boolean flag;
	private String flagname;
	private Boolean flagbl;
	/**
	 * 配置id
	 */
	private String configid;
	/**
	 * 菜单类型
	 */
	@NotBlank
	private String systype;
	/**
	 * 菜单类型
	 */
	private String systypename;
	/**
	 * 权限标识
	 */
	private String permission;
	/**
	 * 录入人
	 */
	private String submitman;
	/**
	 * 录入人
	 */
	private String submitmanname;
	/**
	 * 录入时间
	 */
	private String submittime;
	/**
	 * 排序
	 */
	private Integer orderid;
	/**
	 * 用户id
	 */
	private String  userid;
	/**
	 * 模板标签
	 */
	private String  template;
	/**
	 * 模板标签名称
	 */
	private String  templatename;

	private boolean module;

	private String modulename;

}
