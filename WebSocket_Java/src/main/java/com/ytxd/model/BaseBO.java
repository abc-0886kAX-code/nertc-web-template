package com.ytxd.model;

import java.io.Serializable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ytxd.util.StringUtil;

public class BaseBO implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 记录日志
	 */
	static Logger logger = LogManager.getLogger(BaseBO.class);

	private Integer addUserId;
	private String addTime;
	private Integer editUserId;
	private String editTime;
	private String isDelete;
	
	
	private Integer startRow;
	private Integer endRow;
	private String orderBy;
	private Integer pageLimit;
	private Integer pageOffset;
	
	//分页使用
	private Integer page;
	private Integer rows;
	//每页行数，默认每页50行
	private Integer pageSize = 50;
	
	//排序使用
	private String sort;
	private String order;
	
	//where条件
	private String whereString;
	private String html;
	
	private Integer selectrowcount;
	
	
	//列表视图
	private String listview;
	
	private String rdmdb;
	
	// 拆分in()查询字段到数组
	private String[] checkresultarray; // 审核结果数组
	private String[] idarray; // id数组

	private String secondaryunitname; //所属二级单位
	
	
	public String getSecondaryunitname() {
		return secondaryunitname;
	}
	public void setSecondaryunitname(String secondaryunitname) {
		this.secondaryunitname = secondaryunitname;
	}
	public void setcheckresultarray(String[] checkresultarray) {
		this.checkresultarray = checkresultarray;
	}
	public String[] getcheckresultarray() {
		return checkresultarray;
	}
	public void setidarray(String[] idarray) {
		this.idarray = idarray;
	}
	public String[] getidarray() {
		return idarray;
	}

	
	public String getrdmdb() {
		return "rdm.";
	}
	private String currentuserid;
	public String getcurrentuserid() {
		return "currentuserid";
	}
	
	public Integer getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Integer addUserId) {
		this.addUserId = addUserId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public Integer getEditUserId() {
		return editUserId;
	}
	public void setEditUserId(Integer editUserId) {
		this.editUserId = editUserId;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getEndRow() {
		return endRow;
	}
	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public Integer getPageLimit() {
		return pageLimit;
	}
	public void setPageLimit(Integer pageLimit) {
		this.pageLimit = pageLimit;
	}
	public Integer getPageOffset() {
		return pageOffset;
	}
	public void setPageOffset(Integer pageOffset) {
		this.pageOffset = pageOffset;
	}
	
	
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((addTime == null) ? 0 : addTime.hashCode());
//		result = prime * result
//				+ ((addUserId == null) ? 0 : addUserId.hashCode());
//		result = prime * result
//				+ ((editTime == null) ? 0 : editTime.hashCode());
//		result = prime * result
//				+ ((editUserId == null) ? 0 : editUserId.hashCode());
//		result = prime * result + ((endRow == null) ? 0 : endRow.hashCode());
//		result = prime * result
//				+ ((isDelete == null) ? 0 : isDelete.hashCode());
//		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
//		result = prime * result
//				+ ((pageLimit == null) ? 0 : pageLimit.hashCode());
//		result = prime * result
//				+ ((pageOffset == null) ? 0 : pageOffset.hashCode());
//		result = prime * result
//				+ ((startRow == null) ? 0 : startRow.hashCode());
//		return result;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		BaseBO other = (BaseBO) obj;
//		if (addTime == null) {
//			if (other.addTime != null)
//				return false;
//		} else if (!addTime.equals(other.addTime))
//			return false;
//		if (addUserId == null) {
//			if (other.addUserId != null)
//				return false;
//		} else if (!addUserId.equals(other.addUserId))
//			return false;
//		if (editTime == null) {
//			if (other.editTime != null)
//				return false;
//		} else if (!editTime.equals(other.editTime))
//			return false;
//		if (editUserId == null) {
//			if (other.editUserId != null)
//				return false;
//		} else if (!editUserId.equals(other.editUserId))
//			return false;
//		if (endRow == null) {
//			if (other.endRow != null)
//				return false;
//		} else if (!endRow.equals(other.endRow))
//			return false;
//		if (isDelete == null) {
//			if (other.isDelete != null)
//				return false;
//		} else if (!isDelete.equals(other.isDelete))
//			return false;
//		if (orderBy == null) {
//			if (other.orderBy != null)
//				return false;
//		} else if (!orderBy.equals(other.orderBy))
//			return false;
//		if (pageLimit == null) {
//			if (other.pageLimit != null)
//				return false;
//		} else if (!pageLimit.equals(other.pageLimit))
//			return false;
//		if (pageOffset == null) {
//			if (other.pageOffset != null)
//				return false;
//		} else if (!pageOffset.equals(other.pageOffset))
//			return false;
//		if (startRow == null) {
//			if (other.startRow != null)
//				return false;
//		} else if (!startRow.equals(other.startRow))
//			return false;
//		return true;
//	}
//	@Override
//	public String toString() {
//		return "BaseBO [addUserId=" + addUserId + ", addTime=" + addTime
//				+ ", editUserId=" + editUserId + ", editTime=" + editTime
//				+ ", isDelete=" + isDelete + ", startRow=" + startRow
//				+ ", endRow=" + endRow + ", orderBy=" + orderBy
//				+ ", pageLimit=" + pageLimit + ", pageOffset=" + pageOffset
//				+ "]";
//	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPage() {
		return page;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
		setPageLimit((this.page-1)*rows);
		setPageOffset(this.page*rows);
		this.pageSize = rows;
		setStartRow((this.page-1)*rows);
		setEndRow(this.page*rows);
	}
	public Integer getRows() {
		return rows;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public void setSort(String sort) {
		// 如果没有空格和逗号，则是正常单个字段--放行
		if (sort.indexOf(" ") == -1 && sort.indexOf(",") == -1) {
			this.sort = sort;
		} else {
			String[] split = null;
			for (String string : sort.replaceAll("\\s{1,}", " ").split(",")) {
				// 空格分割后的排序字段的数组长度
				split = string.trim().split(" ");
				if (split.length == 1) {// 为一时，两个字段排序 ex:id,name
					this.sort = sort;
					// break;
				} else if (split.length == 2) {// 为多组字段，查看第二个是不是排序字段，按不同顺序排序
												// ex:id DESC,name ASC
					if ("asc".equals(split[1].toLowerCase()) 
							|| "desc".equals(split[1].toLowerCase())) {
						this.sort = sort;
						// break;
					} else {
						this.sort = "1";
						break;
					}
				} else {// 大于两个则证明非排序字段，给赋默认值
					this.sort = "1";
					break;
				}
			}
		}
	}
	public String getSort() {
		return sort;
	}
	public void setOrder(String order) {
		switch (order.toUpperCase()) {
		case "ASC":
		case "DESC":
		case "":
			this.order = order;
			break;
		default:
			this.order = "ASC";
			break;
		}
	}
	public String getOrder() {
		return order;
	}
	public void setListview(String listview) {
		this.listview = listview;
	}
	public String getListview() {
		return listview;
	}
	public void setWhereString(String whereString) {
		this.whereString = whereString;
	}
	public String getWhereString() {
		return whereString;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getHtml() {
		return html;
	}
	public Integer getselectrowcount() {
		return selectrowcount;
	}
	public void setselectrowcount(Integer selectrowcount) {
		this.selectrowcount = selectrowcount;
	}

	public BaseBO GetdecodeUtf() {
		try {
			String whereString = this.getWhereString();
			if (StringUtil.isNotEmpty(whereString)) {
				whereString = whereString.replace('%', '@');
				whereString = new String(whereString.getBytes("ISO-8859-1"), "UTF-8");
				this.setWhereString(whereString.replace('@', '%'));
			}
		} catch (Exception e) {
			logger.error("中文转码出现问题", e);
		}
		return this;
	}
	
	public String setOneFieldCodeUtf8(String inStr) throws Exception {
		if (StringUtil.isNotEmpty(inStr)) {
			inStr = java.net.URLDecoder.decode(inStr, "UTF-8");
		}
		return inStr;
	}
}
