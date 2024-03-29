package com.ytxd.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class MySqlData {
	//数据库表名
	private String tablename;
	//增加修改视图名称
	private String viewname;
	//搜索配置名称
	private String searchpagename;
	//列表配置名称
	private String listname;
	//返回信息
	private String msg;
	//是否校验空值
	private Boolean checknull;
	//是否有过滤条件
	private Boolean haveWhere;
	//是否启用缓存
	private Boolean enableCache;
	//是否启用code关联显示描述列
	private Boolean codeJoin;
	//通用的id，对应每个表的id字段
	private String id;
	//当前登录用户的tokenId
	private String tokenId;
	//当前是哪一页
	private Integer page;
	private Integer rows;
	//每页行数，默认每页50行
	private Integer pageSize = 50;
	//起始行
	private Integer startRow;
	//结束行
	private Integer endRow;
	//新增时返回的id值
	private Long newId;
	//封装增加和修改字段为列表对象
	private List<MySqlField> valueFields = new ArrayList<MySqlField>();
	//封装查询select列字段为列表对象
	private List<MySqlField> selectFields = new ArrayList<MySqlField>();
	//封装查询条件字段为列表对象
	private List<MySqlField> whereFields = new ArrayList<MySqlField>();
	//封装一个OR查询条件字段为列表对象
	private List<MySqlField> orWhereFields = new ArrayList<MySqlField>();
	//封装sql关联为列表对象
	private List<MySqlField> joinSqls = new ArrayList<MySqlField>();
	//封装完整的SQL对象，只有用户输入的值做特殊处理。
	private List<MySqlField> sqls = new ArrayList<MySqlField>();
	//封装存储过程的参数。
	private List<MySqlField> procParams = new ArrayList<MySqlField>();
	
	//保存附件类型保存后回调显示的html文件，这与SQL无关不要放这里。
	private Map<String, Object> fileHtmls = new HashMap<String, Object>();
	//搜索的表格列表，用于count时关联
	private Map<String, Object> whereTable = new HashMap<String, Object>();
	
	//过滤条件SQL
	/*private String whereSql;
	private Integer selectrowcount;*/
	//存储过程 名称
	private String procName;
	
	
	//排序使用
	private String sort;
	private String order;
	
	public String getTableName() {
		if(StringUtils.isNotBlank(tablename)) {
			return tablename.replace(" ", "").replace("'", "").toLowerCase();
		} else {
			return tablename;
		}
	}
	/**
	 * 设置数据库表名
	 * @param tablename 数据库表名
	 */
	public void setTableName(String tablename) {
		if(StringUtils.isNotBlank(tablename)) {
			this.tablename = tablename.replace(" ", "").replace("'", "").toLowerCase();
		} else {
			this.tablename = tablename;
		}
	}
	public String getViewName() {
		return viewname;
	}
	/**
	 * 设置视图名称，表SM_BuiltCollect里的TableName字段值。
	 * @param viewname 视图名称
	 */
	public void setViewName(String viewname) {
		this.viewname = viewname;
	}
	public String getSearchPageName() {
		return searchpagename;
	}
	/**
	 * 设置搜索配置名称，表SM_SearchCollect里的SearchPageName字段值。
	 * @param searchpagename 搜索配置名称
	 */
	public void setSearchPageName(String searchpagename) {
		this.searchpagename = searchpagename;
	}
	public String getListName() {
		return listname;
	}
	/**
	 * 设置列表配置名称，表SM_ListCollect里的ListName字段值。
	 * @param listname 列表配置名称
	 */
	public void setListName(String listname) {
		this.listname = listname;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getCheckNull() {
		if (checknull == null) {
			return false;
		} else {
			return checknull;
		}
	}
	/**
	 * 设置后台验证是否为空。
	 * @param checknull
	 */
	public void setCheckNull(Boolean checknull) {
		this.checknull = checknull;
	}

	public Boolean getHaveWhere() {
		if (haveWhere == null) {
			return false;
		} else {
			return haveWhere;
		}
	}

	/**
	 * 是否有过滤条件
	 * 
	 * @param haveWhere
	 */
	public void setHaveWhere(Boolean haveWhere) {
		this.haveWhere = haveWhere;
	}
	
	public Boolean getEnableCache() {
		if (enableCache == null) {
			return false;
		} else {
			return enableCache;
		}
	}

	/**
	 * 是否启用缓存 
	 * 用于表格视图，搜索和列表，其他先不要用。
	 * 
	 * @param enableCache
	 */
	public void setEnableCache(Boolean enableCache) {
		this.enableCache = enableCache;
	}
	
	public Boolean getCodeJoin() {
		if (codeJoin == null) {
			return true;
		} else {
			return codeJoin;
		}
	}

	/**
	 * 是否启用code关联显示描述列，默认关联。
	 * 用于getList得到列表时
	 * 
	 * @param codeJoin
	 */
	public void setCodeJoin(Boolean codeJoin) {
		this.codeJoin = codeJoin;
	}

	public String getId() {
		if(StringUtils.isNotBlank(id)) {
			return id.toLowerCase();
		} else {
			return id;
		}
	}
	/**
	 * 修改时设置id的值
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
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
	public Long getNewId() {
		return newId;
	}
	public void setNewId(Long newId) {
		this.newId = newId;
	}
	public List<MySqlField> getValueFields() {
		return valueFields;
	}
	public void setValueFields(List<MySqlField> valueFields) {
		this.valueFields = valueFields;
	}
	public Integer getFieldValueSize(){
		return valueFields.size();
	}
	public List<MySqlField> getSelectFields() {
		return selectFields;
	}
	public void setSelectFields(List<MySqlField> selectFields) {
		this.selectFields = selectFields;
	}
	public List<MySqlField> getWhereFields() {
		return whereFields;
	}
	public void setWhereFields(List<MySqlField> whereFields) {
		this.whereFields = whereFields;
	}
	public List<MySqlField> getOrWhereFields() {
		return orWhereFields;
	}
	public void setOrWhereFields(List<MySqlField> orWhereFields) {
		this.orWhereFields = orWhereFields;
	}
	public List<MySqlField> getJoinSqls() {
		return joinSqls;
	}
	public void setJoinSqls(List<MySqlField> joinSqls) {
		this.joinSqls = joinSqls;
	}
	public List<MySqlField> getSqls() {
		return sqls;
	}
	public void setSqls(List<MySqlField> sqls) {
		this.sqls = sqls;
	}
	public List<MySqlField> getProcParams() {
		return procParams;
	}
	public void setProcParams(List<MySqlField> procParams) {
		this.procParams = procParams;
	}
	public Map<String, Object> getFileHtmls() {
		return fileHtmls;
	}
	public void setFileHtmls(Map<String, Object> fileHtmls) {
		this.fileHtmls = fileHtmls;
	}
	public Map<String, Object> getWhereTable() {
		return whereTable;
	}
	public void setWhereTable(String key, Object value) {
		if(StringUtils.isNotBlank(tablename)) {
			if(!key.toLowerCase().equals(tablename.toLowerCase())) {
				this.whereTable.put(key.toLowerCase(), value);
			}
		} else {
			this.whereTable.put(key.toLowerCase(), value);
		}
	}
	/*public String getWhereSql() {
		return whereSql;
	}
	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}*/
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	/*public Integer getselectrowcount() {
		return selectrowcount;
	}
	public void setselectrowcount(Integer selectrowcount) {
		this.selectrowcount = selectrowcount;
	}*/
	/**
	 * 设置排序字段
	 * @param sort 如：TableName.FieldName
	 */
	public void setSort(String sort) {
		if(StringUtils.isBlank(sort)) {
			this.sort = "";
			return ;
		}
		sort = sort.replace("`", "").toLowerCase();
		if(StringUtils.isNotBlank(sort)) {
			sort = sort.replace("'", "").replaceAll("\\s+", " ");
		}
		// 如果没有空格和逗号，则是正常单个字段--放行
		if (sort.indexOf(" ") == -1 && sort.indexOf(",") == -1) {
			this.sort = "`" + sort.replace(".", "`.`") + "`";
		} else {
			StringBuilder strSort = new StringBuilder();
			for (String string : sort.replaceAll("\\s{1,}", " ").split(",")) {
				// 空格分割后的排序字段的数组长度
				String[] split = string.trim().split(" ");
				if (split.length == 1) {// 为一时，两个字段排序 ex:id,name
					strSort.append("`" + split[0] + "`,");
				} else if (split.length == 2) {// 为多组字段，查看第二个是不是排序字段，按不同顺序排序
												// ex:id DESC,name ASC
					if ("asc".equals(split[1].toLowerCase())  || "desc".equals(split[1].toLowerCase())) {
						strSort.append("`" + split[0] + "` " + split[1].toUpperCase() + ",");
					} else {
						this.sort = "1,";
						break;
					}
				} else {// 大于两个则证明非排序字段，给赋默认值
					this.sort = "1,";
					break;
				}
			}
			this.sort = strSort.deleteCharAt(strSort.length() - 1).toString().replace(".", "`.`");
		}
	}
	public String getSort() {
		return sort;
	}
	/**
	 * 设置查询的排序方式，值为ASC或者DESC
	 * @param order ASC或者DESC
	 */
	public void setOrder(String order) {
		if(StringUtils.isBlank(order)) {
			this.order = "";
			return ;
		}
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
	
	/**
	 * 用于增加和修改SQL，设置字段的键值对。SQL默认的#操作。
	 * @param fieldName 数据库字段
	 * @param fieldValue 字段值
	 */
	public void setFieldValue(String fieldName, Object fieldValue){
		setFieldValue(fieldName, fieldValue, "#");
	}
	/**
	 * 用于增加和修改SQL，设置字段的键值对。SQL默认的#操作。
	 * @param fieldName 数据库字段
	 * @param fieldValue 字段值
	 * @param valueType 操作符，#或者%，如果用%号，那字段值里不能含用户输入的值，不然有SQL注入分险。
	 */
	public void setFieldValue(String fieldName, Object fieldValue, String valueType){
		setFieldValue(valueFields, fieldName, fieldValue, valueType);
	}
	private void setFieldValue(List<MySqlField> listField, String fieldName, Object fieldValue){
		setFieldValue(listField, fieldName, fieldValue, null);
	}

	private void setFieldValue(List<MySqlField> listField, String fieldName, Object fieldValue, String valueType) {
		fieldName = fieldName.replace(" ", "").replace("'", "");
		boolean isFind = false;
		for (MySqlField field : listField) {
			if (fieldName.equalsIgnoreCase(field.getFieldName())) {
				field.setFieldValue(fieldValue);
				if (valueType != null) {
					field.setValueType(valueType);
				}
				isFind = true;
				break;
			}
		}

		// 如果没找到则新增一条记录
		if (!isFind) {
			MySqlField field = new MySqlField();
			field.setFieldName(fieldName);
			field.setFieldValue(fieldValue);
			if (valueType != null) {
				field.setValueType(valueType);
			}
			listField.add(field);
		}
	}
	
	/**
	 * 
	 * @Title getFieldValue
	 * @Description (TODO获取valueFields里的字段值)
	 * @param fieldName
	 * @return
	 */
	public Object getFieldValue(String fieldName){
		return getFieldValue(valueFields, fieldName);
	}
	private Object getFieldValue(List<MySqlField> listField, String fieldName){
		Object result = "";
		for(MySqlField field : listField){
			if(fieldName.equalsIgnoreCase(field.getFieldName())){
				result = field.getFieldValue();
				break;
			}
		}
		return result;
	}
	/**
	 * 设置or过滤条件，一个实体里只能有一个or过滤条件。
	 * @param fieldName 数据库字段，可以包括表名。如：TableName.FieldName
	 * @param fieldValue 字段的值
	 * @param operate 操作符 如：in,like,>,>=,<,<=等SQL操作符，in时默认为英文对号,
	 */
	public void setFieldOrWhere(String fieldName, Object fieldValue, String operate){
		setFieldOrWhere(fieldName, fieldValue, operate, "");
	}
	/**
	 * 设置or过滤条件，一个实体里只能有一个or过滤条件。
	 * @param fieldName 数据库字段，可以包括表名。如：TableName.FieldName
	 * @param fieldValue 字段的值
	 * @param operate 操作符 如：in,like,>,>=,<,<=等SQL操作符，in时默认为英文对号,
	 * @param separator 分隔符 在in或者like时有效,in时默认为英文对号,
	 */
	public void setFieldOrWhere(String fieldName, Object fieldValue, String operate, String separator){
		setFieldWhere(orWhereFields, fieldName, fieldValue, operate, separator);
	}
	/**
	 * 设置过滤条件
	 * @param fieldName 数据库字段，可以包括表名。如：TableName.FieldName
	 * @param fieldValue 字段的值
	 * @param operate 操作符 如：in,like,>,>=,<,<=等SQL操作符，in时默认为英文对号,如果是sql那就value是一个完整的SQL
	 */
	public void setFieldWhere(String fieldName, Object fieldValue, String operate){
		setFieldWhere(fieldName, fieldValue, operate, "");
	}
	/**
	 * 设置过滤条件
	 * @param fieldName 数据库字段，可以包括表名。如：TableName.FieldName
	 * @param fieldValue 字段的值
	 * @param operate 操作符 如：in,like,>,>=,<,<=等SQL操作符，in时默认为英文对号,
	 * @param separator 分隔符 在in或者like时有效,in时默认为英文对号,
	 */
	public void setFieldWhere(String fieldName, Object fieldValue, String operate, String separator){
		setFieldWhere(whereFields, fieldName, fieldValue, operate, separator);
	}
	private void setFieldWhere(List<MySqlField> whereFields, String fieldName, Object fieldValue, String operate, String separator){
		if(StringUtils.isBlank(fieldName) || StringUtils.isBlank(operate)) {
			return;
		}
		if (fieldName.indexOf(".") > 0 && fieldName.indexOf("(") == -1) {
			setWhereTable(fieldName.substring(0, fieldName.indexOf(".")).toLowerCase(), "");
		}
		if(operate.toLowerCase().indexOf("in") > -1) {
			if(StringUtils.isBlank(separator)) {
				separator = ",";
			}
			//特殊情况有 * ^ : | . \
		}
		boolean isFind = false;
//		for(MySqlField field : whereFields){
//			if(fieldName.equalsIgnoreCase(field.getFieldName())){
//				field.setOperate(operate.toLowerCase());
//				if(operate.toLowerCase().indexOf("in") > -1){
//					field.setArrayValue(fieldValue.split(separator));
//				} else {
//					field.setFieldValue(fieldValue);
//				}
//				if(StringUtils.isNotBlank(separator) && (operate.equalsIgnoreCase("like") || operate.equalsIgnoreCase("%"))){
//					field.setValueSeparator(separator);
//				} 
//				isFind = true;
//				break;
//			}
//		}
		
		//如果没找到则新增一条记录
		if(!isFind){
			MySqlField field = new MySqlField();
			field.setFieldName(fieldName);
			field.setOperate(operate);
			if(operate.toLowerCase().indexOf("in") > -1){
				field.setArrayValue(fieldValue.toString().split(separator));
			} else {
				field.setFieldValue(fieldValue);
			}
			if(StringUtils.isNotBlank(separator) && (operate.equalsIgnoreCase("like") || operate.equalsIgnoreCase("%"))){
				field.setValueSeparator(separator);
			} 
			whereFields.add(field);
		}
	}
	
	/**
	 * 设置select查询字段，fieldName只是作为map主键，SQL里用的是fieldValue的值。
	 * @param fieldName map主键，如果赋值相同的值，前面的赋值会被覆盖。
	 * @param fieldValue SQL里的select部分SQL，这里不能包含用户输入的值。
	 */
	public void setSelectField(String fieldName, String fieldValue){
		setFieldValue(selectFields, fieldName, fieldValue);
	}
	/**
	 * 设置SQL的JOIN部分SQL，fieldName必须是关联表的表名作为map主键，SQL里用的是fieldValue的值。
	 * 重点：fieldName必须是关联表的表名，一个关联表写一个不要多个写一起，查询关联表数据必须是表名.字段名，否则查询报错。
	 * 重点：fieldValue里不能包含用户输入的值，否则有sql注入风险。
	 * @param fieldName 必须是关联表的表名作为map主键，如果赋值相同，前面的赋值会被覆盖。
	 * @param fieldValue SQL的JOIN部分SQL，这里不能包含用户输入的值。
	 */
	public void setJoinSql(String fieldName, String fieldValue){
		if(fieldValue.trim().replace("  ", " ").toLowerCase().startsWith("inner")) {
			//如果为inner join都需要拼接进行count
			setFieldValue(joinSqls, fieldName, fieldValue);
		} else if(StringUtils.isNotBlank(tablename) && StringUtils.isNotBlank(fieldName)) {
			//如果表名不为空，得执行count前用setCountJoin()来设置join语句。
			setFieldValue(joinSqls, fieldName, fieldValue, "#");
		} else {
			setFieldValue(joinSqls, fieldName, fieldValue);
		}
	}
	/**
	 * 设置SQL的JOIN部分SQL，在count的时候会拼接。
	 * 重点：fieldName必须是关联表的表名，一个关联表写一个不要多个写一起，查询关联表数据必须是表名.字段名，否则查询报错。
	 * 重点：fieldValue里不能包含用户输入的值，否则有sql注入风险。
	 * @param fieldName 必须是关联表的表名作为map主键，如果赋值相同，前面的赋值会被覆盖。
	 * @param fieldValue SQL的JOIN部分SQL，这里不能包含用户输入的值。
	 */
	public void setJoinSqlCountJoin(String fieldName, String fieldValue){
		setFieldValue(joinSqls, fieldName, fieldValue);
	}
	/**
	 * 设置表格视图自动关联的JOIN部分SQL，这fieldName必须是关联表的表名作为map主键，SQL里用的是fieldValue的值。
	 * 重点：fieldName必须是关联表的表名，一个关联表写一个不要多个写一起，查询关联表数据必须是表名.字段名，否则查询报错。
	 * 重点：fieldValue里不能包含用户输入的值，否则有sql注入风险。
	 * @param fieldName 必须是关联表的表名作为map主键，如果赋值相同，前面的赋值会被覆盖。
	 * @param fieldValue SQL的JOIN部分SQL，这里不能包含用户输入的值。
	 */
	public void setCodeJoinSql(String fieldName, String fieldValue){
		setFieldValue(joinSqls, fieldName, fieldValue, "#");
	}
	
	/**
	 * 执行getListCount前执行，用于得到count时需要的join部分。
	 */
	public void setCountJoin(){
		if(whereTable.size() > 0) {
			joinSqls.forEach(field -> {
				if (whereTable.containsKey(field.getFieldName().toLowerCase())) {
					field.setValueType("");
				}
			});
		}
	}
	
	/**
	 * 这个功能不能与其他功能同时使用，需要写完整的SQL语句，过滤条件尽量用setFieldWhere。
	 * SQL语句执行得到结果
	 * 重点：fieldValue里不能包含用户输入的值，否则有sql注入风险。
	 * @param fieldValue
	 */
	public void setSql(String fieldValue){
		/*if (!getHaveWhere() && StringUtils.isNotBlank(fieldValue) && (fieldValue.startsWith("where ") || fieldValue.indexOf(" where ") > -1)) {
			setHaveWhere(true);
		}*/
		setFieldValue(sqls, sqls.size() + "", fieldValue);
	}
	/**
	 * 这个功能不能与其他功能同时使用，需要写完整的SQL语句。
	 * SQL语句里的用户输入值，SQL里用#赋值。
	 * @param fieldValue
	 */
	public void setSqlValue(Object fieldValue){
		setFieldValue(sqls, sqls.size() + "", fieldValue, "#");
	}
	/**
	 * 拼接配置的where条件
	 */
	public void setSqlWhere(){
		setFieldValue(sqls, sqls.size() + "", "", "where");
	}
	
	/**
	 * 设置存储过程参数
	 * @param fieldName 参数名
	 * @param fieldValue 参数值
	 * @param operate IN或者OUT，输入或者输出参数
	 * @param valueType 参数类型VARCHAR
	 */
	public void setProcParam(String fieldName, Object fieldValue, String operate, String valueType){
		MySqlField field = new MySqlField();
		field.setFieldName(fieldName);
		field.setFieldValue(fieldValue);
		field.setOperate(operate.toUpperCase());
		field.setValueType(valueType.toUpperCase());
		procParams.add(field);
	}
	
	/**
	 * 设置附件类型保存后回调显示的html内容
	 * @param fileName 文件控件名称
	 * @param fileHtml 附件类型保存后回调显示的html内容
	 */
	public void setFileHtml(String fileName, String fileHtml){
		fileHtmls.put(fileName, fileHtml);
	}
}