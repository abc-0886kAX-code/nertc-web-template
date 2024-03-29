package com.ytxd.model;

public class MySqlField {
	//字段名
	private String fieldName;
	//字段值，新增、更新时需要赋值
	private Object fieldValue;
	//操作符，条件查询时用到的 > < = % 等，%表示模糊查询
	private String operate;
	//字段值的类型，默认为#
	private String valueType;
	//数组类型的值，一般用于in查询。
	private String[] arrayValue;
	//值的分隔符
	private String valueSeparator;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate.toLowerCase();
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String[] getArrayValue() {
		return arrayValue;
	}
	public void setArrayValue(String[] arrayValue) {
		this.arrayValue = arrayValue;
	}
	public String getValueSeparator() {
		return valueSeparator;
	}
	public void setValueSeparator(String valueSeparator) {
		this.valueSeparator = valueSeparator;
	}
}