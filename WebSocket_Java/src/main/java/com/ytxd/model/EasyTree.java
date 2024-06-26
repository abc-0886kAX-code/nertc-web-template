package com.ytxd.model;

import java.util.List;

public class EasyTree {
	private String id;
	private String text;
	private String state = "open";// open,closed
	private boolean checked = false;
	private Object attributes;
	private List<EasyTree> children;
	private boolean open = false;
	
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Object getAttributes() {
		return attributes;
	}
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	public List<EasyTree> getChildren() {
		return children;
	}
	public void setChildren(List<EasyTree> children) {
		this.children = children;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	private String iconCls;
	private String pid;
}
