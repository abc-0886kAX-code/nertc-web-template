package com.ytxd.service.ExcelOperation;

import com.ytxd.common.DataUtils;
import com.ytxd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class ImportExcel {
	class SM_ExcelImportItem {
		String ImportName;
		String FieldName;
		String ExcelColumnName;
		String Visible;
		String ColumnNumber;
		String NotNull;
		String FieldType;
		String FieldShortestLen;
		String FieldLen;
		String CodeId;
		String CodeName;
		String OnlyFlag;
		String AMUserImportType;

		public String getImportName() {
			if (ImportName == null) {
				return "";
			} else {
				return ImportName;
			}
		}

		public void setImportName(String importName) {
			ImportName = importName;
		}

		public String getFieldName() {
			if (FieldName == null) {
				return "";
			} else {
				return FieldName;
			}
		}

		public void setFieldName(String fieldName) {
			FieldName = fieldName;
		}

		public String getExcelColumnName() {
			if (ExcelColumnName == null) {
				return "";
			} else {
				return ExcelColumnName;
			}
		}

		public void setExcelColumnName(String excelColumnName) {
			ExcelColumnName = excelColumnName;
		}

		public String getVisible() {
			if (Visible == null) {
				return "";
			} else {
				return Visible;
			}
		}

		public void setVisible(String visible) {
			Visible = visible;
		}

		public String getColumnNumber() {
			if (ColumnNumber == null) {
				return "";
			}
			return ColumnNumber;
		}

		public void setColumnNumber(String columnNumber) {
			ColumnNumber = columnNumber;
		}

		public String getNotNull() {
			if (NotNull == null) {
				return "";
			} else {
				return NotNull;
			}
		}

		public void setNotNull(String notNull) {
			NotNull = notNull;
		}

		public String getFieldType() {
			if (FieldType == null) {
				return "";
			} else {
				return FieldType;
			}
		}

		public void setFieldType(String fieldType) {
			FieldType = fieldType;
		}

		public String getFieldShortestLen() {
			if (FieldShortestLen == null) {
				return "";
			} else {
				return FieldShortestLen;
			}
		}

		public void setFieldShortestLen(String fieldShortestLen) {
			FieldShortestLen = fieldShortestLen;
		}

		public String getFieldLen() {
			if (FieldLen == null) {
				return "";
			} else {
				return FieldLen;
			}
		}

		public void setFieldLen(String fieldLen) {
			FieldLen = fieldLen;
		}

		public String getCodeId() {
			if (CodeId == null) {
				return "";
			} else {
				return CodeId;
			}
		}

		public void setCodeId(String codeId) {
			CodeId = codeId;
		}

		public String getCodeName() {
			if (CodeName == null) {
				return "";
			} else {
				return CodeName;
			}
		}

		public void setCodeName(String codeName) {
			CodeName = codeName;
		}

		public String getOnlyFlag() {
			if (OnlyFlag == null) {
				return "";
			} else {
				return OnlyFlag;
			}
		}

		public void setOnlyFlag(String onlyFlag) {
			OnlyFlag = onlyFlag;
		}

		public String getAMUserImportType() {
			if (AMUserImportType == null) {
				return "";
			} else {
				return AMUserImportType;
			}
		}

		public void setAMUserImportType(String aMUserImportType) {
			AMUserImportType = aMUserImportType;
		}
	}

	// 私有属性
	// / <summary>
	// / 导入名称 必填
	// / </summary>
	private String ImportName = "";
	// / <summary>
	// / 导入格式dtItem
	// / </summary>
	private List<SM_ExcelImportItem> dtItem;
	// / <summary>
	// / INSERT的字段部份。格式：Field1,Field2,
	// / </summary>
	private String AddField = "";
	// / <summary>
	// / INSERT的Value部份。格式：Value1,Value2,
	// / </summary>
	public String AddValue = "";
	// / <summary>
	// / UPDATE的SET部分。格式：Field1=Value1,Field2=Value2,
	// / </summary>
	public String ModifySQL = "";
	// / <summary>
	// / 判断唯一性的过滤条件，除开导入配制里选择过滤字段。
	// / </summary>
	public String OnlyWhere = "";

	// / <summary>
	// / SheetName 默认第一页
	// / </summary>
	private String strSheetName = "";
	// / <summary>
	// / 开始行号 默认第二行开始
	// / </summary>
	private String strRowNumber = "";
	// / <summary>
	// / 表名
	// / </summary>
	private String strTableName = "";
	// / <summary>
	// / 导入前要执行Sql
	// / </summary>
	private String strImportStartSql = "";
	// / <summary>
	// / 导入后要执行Sql
	// / </summary>
	private String strImportEndSql = "";
	// / <summary>
	// / 文件路径
	// / </summary>
	private String strFilePath = "";
	private String strType = "01";
	private String strUpdateType = "01";
	private String strImportType = "02";

	public String database = "kjmis_zm";
	// 通过SM_ExcelImportCollect表赋值的私有变量
	private String Description;
	private String TemplateFile;
	private String ImportCheckResult;
	private String IdFieldName;

	// 先不用这种方式
	private void setExcelCollect(List dtCollect) {
		try {
			ImportName = getStrValue(dtCollect.get(0), "ImportName");
			Description = getStrValue(dtCollect.get(0), "Description");
			strTableName = getStrValue(dtCollect.get(0), "TableName");
			TemplateFile = getStrValue(dtCollect.get(0), "TemplateFile");
			ImportCheckResult = getStrValue(dtCollect.get(0), "ImportCheckResult");
			IdFieldName = getStrValue(dtCollect.get(0), "IdFieldName");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 先不用这种方式，如果用这种方式请保证FieldName的大小写与列名一致。
	private void setExcelItem(List dtItemList) {
		this.dtItem = new ArrayList();
		for (Object obj : dtItemList) {
			SM_ExcelImportItem objItem = new SM_ExcelImportItem();
			objItem.setImportName(getStrValue(obj, "ImportName").toString());
			objItem.setFieldName(getStrValue(obj, "FieldName").toString());
			objItem.setExcelColumnName(getStrValue(obj, "ExcelColumnName")
					.toString());
			objItem.setVisible(getStrValue(obj, "Visible").toString());
			objItem.setColumnNumber(getStrValue(obj, "RowNumber"));
			objItem.setNotNull(getStrValue(obj, "NotNull").toString());
			objItem.setFieldType(getStrValue(obj, "FieldType").toString());
			objItem.setFieldShortestLen(getStrValue(obj, "FieldShortestLen"));
			objItem.setFieldLen(getStrValue(obj, "FieldLen"));
			objItem.setCodeId(getStrValue(obj, "CodeId").toString());
			objItem.setCodeName(getStrValue(obj, "CodeName").toString());
			objItem.setOnlyFlag(getStrValue(obj, "OnlyFlag").toString());
			objItem.setAMUserImportType(getStrValue(obj, "AMUserImportType")
					.toString());

			this.dtItem.add(objItem);
		}
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * 导入名称 必填
	 */
	public void setImportName(String ImportName) {
		this.ImportName = ImportName;
	}

	/**
	 * INSERT的字段部份。格式：Field1,Field2,
	 * 
	 * @param AddField
	 */
	public void setAddField(String AddField) {
		this.AddField = AddField;
	}

	/**
	 * INSERT的Value部份。格式：Value1,Value2,
	 * 
	 * @param AddValue
	 */
	public void setAddValue(String AddValue) {
		this.AddValue = AddValue;
	}

	/**
	 * UPDATE的SET部分。格式：Field1=Value1,Field2=Value2,
	 * 
	 * @param ModifySQL
	 */
	public void setModifySQL(String ModifySQL) {
		this.ModifySQL = ModifySQL;
	}

	/**
	 * 判断唯一性的过滤条件，除开导入配制里选择过滤字段。
	 * 
	 * @param OnlyWhere
	 */
	public void setOnlyWhere(String OnlyWhere) {
		this.OnlyWhere = OnlyWhere;
	}

	/**
	 * SheetName 默认第一页
	 * 
	 * @param SheetName
	 */
	public void setSheetName(String SheetName) {
		this.strSheetName = SheetName;
	}

	/**
	 * 开始行号 默认第二行开始
	 * 
	 * @param RowNumber
	 */
	public void setRowNumber(String RowNumber) {
		this.strRowNumber = RowNumber;
	}

	/**
	 * 导入前要执行SQL
	 * 
	 * @param ImportStartSql
	 */
	public void setImportStartSql(String ImportStartSql) {
		this.strImportStartSql = ImportStartSql;
	}

	/**
	 * 导入后要执行SQL
	 * 
	 * @param ImportEndSql
	 */
	public void setImportEndSql(String ImportEndSql) {
		this.strImportEndSql = ImportEndSql;
	}

	/**
	 * 导入文件路径 必填
	 * 
	 * @param FilePath
	 */
	public void setFilePath(String FilePath) {
		this.strFilePath = FilePath;
	}

	/**
	 * 处理类型 01为只增加有重复提醒，02为无重复增加有重复修改，03为只修改无重复提醒
	 * 
	 * @param Type
	 */
	public void setType(String Type) {
		this.strType = Type;
	}

	/**
	 * 修改类型 01为以导入数据为准，02为如果数据库有值以数据库原来值为准，如果数据库没有值以导入数据为准。
	 * 
	 * @param UpdateType
	 */
	public void setUpdateType(String UpdateType) {
		this.strUpdateType = UpdateType;
	}

	/**
	 * 兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	 * 
	 * @param ImportType
	 */
	public void setImportType(String ImportType) {
		this.strImportType = ImportType;
	}

	/**
	 * 导入excel
	 * 
	 * @return 返回错误信息
	 * @throws SQLException
	 */
	public String Import() throws SQLException {
		// 检查模板文件是否存在
		File file = new File(strFilePath);
		if (!file.exists()) {
			return "导入文件不存在。";
		}
		// 导入excle
		return ImportFile();
	}

	/**
	 * 导入excel
	 * 
	 * @param FilePath
	 *            导入文件绝对路径
	 * @return 返回错误信息
	 * @throws SQLException
	 */
	public String Import(String FilePath) throws SQLException {
		strFilePath = FilePath;
		return Import();
	}

	/**
	 * 导入excel
	 * 
	 * @param ImportName
	 *            导入名称
	 * @param FilePath
	 *            导入文件绝对路径
	 * @return 返回错误信息
	 * @throws SQLException
	 */
	public String Import(String ImportName, String FilePath)
			throws SQLException {
		this.ImportName = ImportName;
		this.strFilePath = FilePath;
		return Import();
	}

	// 导入非列表
	// / <summary>
	// / 导入非列表 在SM_ExcelImportItem里设置行号和列号
	// / </summary>
	// / <param name="dt">数据源</param>
	// / <param name="ExportName">报表名称</param>
	// / <returns></returns>
	// public String ImportNoList()
	// {
	// //初始化
	// String strError = InitImport();
	// if (strError != "")
	// {
	// return strError;
	// }
	//
	// //读取文件流
	// FileStream readfile = null;
	// try
	// {
	// readfile = new FileStream(strFilePath, FileMode.Open, FileAccess.Read);
	// }
	// catch
	// {
	// return "导入文件读取失败。";
	// }
	// //创建Workbook
	// IWorkbook workbook = null;
	// //创建Workbook。文件为03版及以前版本
	// try
	// {
	// //创建Workbook。文件为03版及以前版本
	// workbook = new HSSFWorkbook(readfile);
	// }
	// catch
	// {
	// //创建Workbook。文件为07版及以后版本
	// try
	// {
	// //关闭读取的文件流
	// if (readfile != null)
	// {
	// readfile.Close();
	// }
	// //读取文件流
	// readfile = new FileStream(strFilePath, FileMode.Open,
	// FileAccess.ReadWrite);
	// //创建Workbook。文件为07版及以后版本
	// workbook = new XSSFWorkbook(readfile);
	// }
	// catch
	// {
	// return "您上传的文件格式错误，请修改。";
	// }
	// }
	//
	// //关闭读取的文件流
	// if (readfile != null)
	// {
	// readfile.Close();
	// }
	// //创建Sheet
	// ISheet mySheet = null;
	// String SheetName = strSheetName;
	// if (StringUtil.isEmpty(SheetName))
	// {
	// mySheet = workbook.GetSheetAt(0);
	// }
	// else
	// {
	// if (workbook.GetSheetIndex(SheetName) >= 0)
	// {
	// mySheet = workbook.GetSheet(SheetName);
	// }
	// else
	// {
	// return "SheetName错误，请填写正确的Sheet名称。";
	// }
	// }
	//
	// //得到dt用于判断是否重复
	// DataTable dt = null;
	// if (dtItem.Columns.Contains("OnlyFlag"))
	// {
	// StringBuilder SelectField = new StringBuilder();
	// DataRow[] drItem = dtItem.Select("OnlyFlag='01'");
	// for (int i = 0; i < drItem.Length; i++)
	// {
	// SelectField.append(drItem[i]["FieldName"].toString() + ",");
	// }
	// if (SelectField.Length > 0)
	// {
	// SelectField.Remove(SelectField.Length - 1, 1);
	// dt = SqlHelper.ExecuteDataTable("select " + SelectField.toString() +
	// " from [" + strTableName + "] where 1=1 " + OnlyWhere);
	// }
	// }
	//
	// //全面检查错误，将所有错误标记出来。
	// int iErrorCount = 0;//记录错误数
	// IDrawing patr = (IDrawing)mySheet.CreateDrawingPatriarch();
	// ICreationHelper facktory = workbook.GetCreationHelper();
	//
	// //------开始导入------------------------------------------------
	// StringBuilder strSql = new StringBuilder();
	// using (SqlConnection conn = new SqlConnection(SqlHelper.connString))
	// {
	// conn.Open();
	// using (SqlTransaction trans = conn.BeginTransaction())
	// {
	// try
	// {
	// //导入前处理
	// if (strImportStartSql != "")
	// {
	// SqlHelper.executeNonQuery(trans, CommandType.Text, strImportStartSql);
	// }
	// //导入数据
	// StringBuilder strField = new StringBuilder();
	// StringBuilder strValue = new StringBuilder();
	// StringBuilder strUpdate = new StringBuilder();
	// StringBuilder strWhere = new StringBuilder();
	// if (AddField != "" && AddValue != "")
	// {
	// strField.append(AddField);
	// strValue.append(AddValue);
	// }
	// if (ModifySQL != "")
	// {
	// strUpdate.append(ModifySQL);
	// }
	//
	// int ColumnNumber = 0;
	// int RowNumber = 0;
	// for (int i = 0; i < dtItem.Rows.Count; i++)
	// {
	// try
	// {
	// String strExcelColumnName =
	// dtItem.Rows[i]["ExcelColumnName"].toString();//Excel列名
	// String strFieldName = dtItem.Rows[i]["FieldName"].toString();//字段名称
	// String strNotNull = dtItem.Rows[i]["NotNull"].toString();//不为空
	// 00为允许，01为不允许
	// String strFieldType =
	// dtItem.Rows[i]["FieldType"].toString().toLowerCase();//字段类型
	// String strCodeId =
	// dtItem.Rows[i]["CodeId"].toString().toLowerCase();//CodeId
	// String strFieldShortestLen =
	// dtItem.Rows[i]["FieldShortestLen"].toString().toLowerCase();//字段最短长度
	// String strFieldLen =
	// dtItem.Rows[i]["FieldLen"].toString().toLowerCase();//字段最长长度
	// String strCodeName = "";
	// if (dtItem.Columns.Contains("CodeName"))
	// {
	// strCodeName = dtItem.Rows[i]["CodeName"].toString().toLowerCase();
	// }
	//
	// ColumnNumber = Convert.ToInt16(dtItem.Rows[i]["ColumnNumber"]);//列号
	// RowNumber = Convert.ToInt16(dtItem.Rows[i]["RowNumber"]);//行号
	// //得到单元格的值
	// String ExcelCellText = "";
	// if (mySheet.getRow(RowNumber - 1).getCell(ColumnNumber - 1) != null)
	// {
	// ExcelCellText = GetCellValue(mySheet.getRow(RowNumber -
	// 1).getCell(ColumnNumber - 1)).trim();
	// //ExcelCellText = mySheet.getRow(RowNumber - 1).getCell(ColumnNumber -
	// 1).toString().trim();
	// }
	// //判断单元格值的有效性
	// if (ExcelCellText == "")
	// {
	// if (strNotNull == "01")//不允许为空
	// {
	// iErrorCount++;
	// InsertComment(mySheet.getRow(RowNumber - 1).getCell(ColumnNumber - 1),
	// ColumnNumber, ColumnNumber + 2, RowNumber, RowNumber + 3, "不能为空，请输入值。",
	// patr, facktory);
	// strError = "第" + RowNumber.toString() + "行，第" + ColumnNumber.toString() +
	// "列[" + strExcelColumnName + "]为空，请输入值。";
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// continue;
	// }
	// else
	// {
	// strField.append("[" + strFieldName + "],");
	// strValue.append("null,");
	// }
	// }
	// else
	// {
	// //验证数据有效性
	// if (strFieldShortestLen != "" && (strFieldType == "varchar" ||
	// strFieldType == "text" || strFieldType == "textcode" || strFieldType ==
	// "textmultipleuser"))//有最短长度限制的字符串
	// {
	// if (LenOfByte(ExcelCellText) < Convert.ToInt32(strFieldShortestLen))
	// {
	// iErrorCount++;
	// InsertComment(mySheet.getRow(RowNumber - 1).getCell(ColumnNumber - 1),
	// ColumnNumber, ColumnNumber + 2, RowNumber, RowNumber + 5, "内容太少，不能少于" +
	// strFieldShortestLen + "个字节，约" + (Convert.ToInt32(strFieldShortestLen) /
	// 2).toString() + "个汉字。", patr, facktory);
	// strError = "第" + RowNumber.toString() + "行，第" + ColumnNumber.toString() +
	// "列[" + strExcelColumnName + "]内容太少，不能少于" + strFieldShortestLen + "个字节，约"
	// + (Convert.ToInt32(strFieldShortestLen) / 2).toString() + "个汉字。";
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// continue;
	// }
	// }
	// else if (strFieldLen != "" && (strFieldType == "varchar" || strFieldType
	// == "text" || strFieldType == "textcode" || strFieldType ==
	// "textmultipleuser"))//有最长长度限制的字符串
	// {
	// if (LenOfByte(ExcelCellText) > Convert.ToInt32(strFieldLen))
	// {
	// iErrorCount++;
	// InsertComment(mySheet.getRow(RowNumber - 1).getCell(ColumnNumber - 1),
	// ColumnNumber, ColumnNumber + 2, RowNumber, RowNumber + 5,
	// "内容超过指定长度，最多只能输入" + strFieldLen + "个字节，约" + (Convert.ToInt32(strFieldLen)
	// / 2).toString() + "个汉字。", patr, facktory);
	// strError = "第" + RowNumber.toString() + "行，第" + ColumnNumber.toString() +
	// "列[" + strExcelColumnName + "]内容超过指定长度，最多只能输入" + strFieldLen + "个字节，约" +
	// (Convert.ToInt32(strFieldLen) / 2).toString() + "个汉字。";
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// continue;
	// }
	// }
	// else if (strFieldType == "datetime")//日期
	// {
	// try
	// {
	// ExcelCellText = ExcelCellText.Replace("/", "-").Replace(".", "-");
	// if (ExcelCellText.Length == 8 && ExcelCellText.IndexOf("-") == -1)
	// {
	// ExcelCellText = ExcelCellText.Substring(0, 4) + "-" +
	// ExcelCellText.Substring(4, 2) + "-" + ExcelCellText.Substring(6, 2);
	// }
	// ExcelCellText = Convert.ToDateTime(ExcelCellText).ToString("yyyy-MM-dd");
	// }
	// catch
	// {
	// iErrorCount++;
	// InsertComment(mySheet.getRow(RowNumber - 1).getCell(ColumnNumber - 1),
	// ColumnNumber, ColumnNumber + 2, RowNumber, RowNumber + 3, "日期格式错误，请修改。",
	// patr, facktory);
	// strError = "第" + RowNumber.toString() + "行，第" + ColumnNumber.toString() +
	// "列[" + strExcelColumnName + "]日期格式错误，请修改。";
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// continue;
	// }
	// //ExcelCellText = Convert.ToDateTime(ExcelCellText).toString();
	// }
	// else if (strFieldType == "dateandtime")//日期
	// {
	// DateTime outDateTime;
	// if (DateTime.TryParseExact(ExcelCellText + ":00", "yyyy-MM-dd HH:mm:ss",
	// null, DateTimeStyles.None, out outDateTime))
	// {
	// ExcelCellText = outDateTime.ToString("yyyy-MM-dd HH:mm:ss");
	// }
	// else
	// {
	// try
	// {
	// ExcelCellText =
	// Convert.ToDateTime(ExcelCellText).ToString("yyyy-MM-dd HH:mm:ss");
	// }
	// catch
	// {
	// iErrorCount++;
	// InsertComment(mySheet.getRow(RowNumber - 1).getCell(ColumnNumber - 1),
	// ColumnNumber, ColumnNumber + 2, RowNumber, RowNumber + 3, "时间格式错误，请修改。",
	// patr, facktory);
	// strError = "第" + RowNumber.toString() + "行，第" + ColumnNumber.toString() +
	// "列[" + strExcelColumnName + "]时间格式错误，请修改。";
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// continue;
	// }
	// }
	// //ExcelCellText = Convert.ToDateTime(ExcelCellText + ":00").toString();
	// }
	// else if (strFieldType == "datetime6")//日期
	// {
	// DateTime outDateTime;
	// if (DateTime.TryParseExact(ExcelCellText + "-01", "yyyy-MM-dd", null,
	// DateTimeStyles.None, out outDateTime))
	// {
	// ExcelCellText = outDateTime.ToString("yyyy-MM-dd");
	// }
	// else
	// {
	// try
	// {
	// ExcelCellText = ExcelCellText.Replace("/", "-").Replace(".", "-");
	// if (ExcelCellText.Length == 6 && ExcelCellText.IndexOf("-") == -1)
	// {
	// ExcelCellText = ExcelCellText.Substring(0, 4) + "-" +
	// ExcelCellText.Substring(4, 2);
	// }
	// ExcelCellText = Convert.ToDateTime(ExcelCellText +
	// "-01").ToString("yyyy-MM-dd");
	// }
	// catch
	// {
	// iErrorCount++;
	// InsertComment(mySheet.getRow(RowNumber - 1).getCell(ColumnNumber - 1),
	// ColumnNumber, ColumnNumber + 2, RowNumber, RowNumber + 3, "年月格式错误，请修改。",
	// patr, facktory);
	// strError = "第" + RowNumber.toString() + "行，第" + ColumnNumber.toString() +
	// "列[" + strExcelColumnName + "]年月格式错误，请修改。";
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// continue;
	// }
	// }
	// //ExcelCellText = Convert.ToDateTime(ExcelCellText + "-01").toString();
	// }
	// else if (strFieldType == "int" || strFieldType == "float" || strFieldType
	// == "money")//数字
	// {
	// try
	// {
	// ExcelCellText = Convert.ToDecimal(ExcelCellText.Replace(",",
	// "")).toString();
	// }
	// catch
	// {
	// iErrorCount++;
	// InsertComment(mySheet.getRow(RowNumber - 1).getCell(ColumnNumber - 1),
	// ColumnNumber, ColumnNumber + 2, RowNumber, RowNumber + 3, "数字格式错误，请修改。",
	// patr, facktory);
	// strError = "第" + RowNumber.toString() + "行，第" + ColumnNumber.toString() +
	// "列[" + strExcelColumnName + "]数字格式错误，请修改。";
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// continue;
	// }
	// //ExcelCellText = Convert.ToDecimal(ExcelCellText.Replace(",",
	// "")).toString();
	// }
	// else if (strCodeId != "" && (strFieldType == "code" || strFieldType ==
	// "multicode" || strFieldType == "select" || strFieldType == "singleuser"
	// || strFieldType == "multipleuser" || strFieldType == "checkboxlist" ||
	// strFieldType == "radiobuttonlist"))
	// {
	// String strTemp = ExcelCellText;
	// ExcelCellText = SqlHelper.executeScalar("select dbo.GetCode('" +
	// strCodeId + "','" + ExcelCellText + "')").toString();
	// if (ExcelCellText == "")//不允许为空
	// {
	// iErrorCount++;
	// InsertComment(mySheet.getRow(RowNumber - 1).getCell(ColumnNumber - 1),
	// ColumnNumber, ColumnNumber + 2, RowNumber, RowNumber + 4,
	// "没有对应数据，请检查数据是否正确。", patr, facktory);
	// strError = "第" + RowNumber.toString() + "行，第" + ColumnNumber.toString() +
	// "列[" + strExcelColumnName + "]没有对应数据，请检查数据是否正确。";
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// continue;
	// }
	// //选择类型同时保存描述
	// if (strCodeName == "01")
	// {
	// strField.append("[" + strFieldName + "Name],");
	// strValue.append("'" + strTemp.Replace("'", "''") + "',");
	// strUpdate.append("[" + strFieldName + "Name]='" + strTemp.Replace("'",
	// "''") + "',");
	// }
	// }
	// //导入sql语句构建
	// strField.append("[" + strFieldName + "],");
	// strValue.append("'" + ExcelCellText.Replace("'", "''") + "',");
	// strUpdate.append("[" + strFieldName + "]='" + ExcelCellText.Replace("'",
	// "''") + "',");
	// //用于判断记录重复的过虑条件。
	// if (dtItem.Columns.Contains("OnlyFlag") &&
	// dtItem.Rows[i]["OnlyFlag"].toString() == "01")
	// {
	// strWhere.append(" and [" + strFieldName + "]='" +
	// ExcelCellText.Replace("'", "''") + "' ");
	// }
	// }
	// }
	// catch (Exception ex)
	// {
	// //不应该进入这里，如果进入请修改程序。
	// iErrorCount++;
	// InsertComment(mySheet.getRow(RowNumber - 1).getCell(ColumnNumber - 1),
	// ColumnNumber, ColumnNumber + 2, RowNumber, RowNumber + 3, "数据有错，请修改。",
	// patr, facktory);
	// strError = ex.Message.Replace("\r\n", "").Replace("'", "‘");
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// }
	// }
	// //去掉字符串最后一位
	// if (strField.Length > 0)
	// {
	// strField.Remove(strField.Length - 1, 1);
	// }
	// if (strValue.Length > 0)
	// {
	// strValue.Remove(strValue.Length - 1, 1);
	// }
	// if (strUpdate.Length > 0)
	// {
	// strUpdate.Remove(strUpdate.Length - 1, 1);
	// }
	// //生成执行SQL语句
	// if (dt != null)//判断重复或者修改数据
	// {
	// if (dt.Select(" 1=1 " + strWhere.toString()).Length > 0)//如果记录重复
	// {
	// if (strType == "02" || strType == "03")//修改模式
	// {
	// //修改sql语句
	// if (iErrorCount == 0)//数据校验无问题
	// {
	// strSql = new StringBuilder();
	// strSql.append("UPDATE [" + strTableName + "] SET " + strUpdate.toString()
	// + " ");
	// strSql.append("WHERE 1=1 " + strWhere.toString() + " " + OnlyWhere);
	//
	// SqlHelper.executeNonQuery(trans, CommandType.Text, strSql.toString());
	// }
	// }
	// else
	// {
	// //暂时处理，最好是将这行标记一个背景色。
	// iErrorCount++;
	// InsertComment(mySheet.getRow(0).getCell(0), 0, 2, 0, 0 + 3, "数据重复，请修改。",
	// patr, facktory);
	// strError = "导入数据重复，请修改。";
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// }
	// }
	// else
	// {
	// if (strType == "03")//只能修改
	// {
	// //暂时处理，最好是将这行标记一个背景色。
	// iErrorCount++;
	// InsertComment(mySheet.getRow(0).getCell(0), 0, 2, 0, 0 + 3,
	// "只能修改，记录不存在请修改。", patr, facktory);
	// strError = "只能修改，记录不存在请修改。";
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// }
	// else if (iErrorCount == 0)//数据校验无问题
	// {
	// strSql = new StringBuilder();
	// strSql.append("INSERT INTO [" + strTableName + "](" + strField.toString()
	// + ") VALUES(");
	// strSql.append(strValue.toString() + ")");
	//
	// SqlHelper.executeNonQuery(trans, CommandType.Text, strSql.toString());
	// }
	// }
	// }
	// else
	// {
	// if (strType == "03")//只能修改
	// {
	// //暂时处理，最好是将这行标记一个背景色。
	// iErrorCount++;
	// InsertComment(mySheet.getRow(0).getCell(0), 0, 2, 0, 0 + 3,
	// "只能修改，没有配制唯一标识，请配制。", patr, facktory);
	// strError = "只能修改，没有配制唯一标识，请配制。";
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// }
	// else if (iErrorCount == 0)//数据校验无问题
	// {
	// strSql = new StringBuilder();
	// strSql.append("INSERT INTO [" + strTableName + "](" + strField.toString()
	// + ") VALUES(");
	// strSql.append(strValue.toString() + ")");
	//
	// SqlHelper.executeNonQuery(trans, CommandType.Text, strSql.toString());
	// }
	// }
	//
	// //导入后处理
	// if (iErrorCount == 0)//数据校验无问题
	// {
	// if (strImportEndSql != "")
	// {
	// SqlHelper.executeNonQuery(trans, CommandType.Text, strImportEndSql);
	// }
	// //数据导入没有问题，提交导入数据。
	// trans.Commit();
	// return "";
	// }
	// else
	// {
	// //数据导入有问题，回滚导入数据。
	// trans.Rollback();
	// }
	// }
	// catch (Exception ex)
	// {
	// trans.Rollback();
	// //return ex.Message.Replace("\r\n", "").Replace("'", "‘");
	// try
	// {
	// iErrorCount++;
	// InsertComment(mySheet.getRow(0).getCell(0), 0, 7, 0, 0 + 15,
	// "执行SQL语句错误。\r\nSQL：" + strSql.toString() + "。\r\n错误：" + ex.Message, patr,
	// facktory);
	// strError = "执行SQL语句错误。\r\nSQL：" + strSql.toString() + "。\r\n错误：" +
	// ex.Message.Replace("\r\n", "").Replace("'", "‘");
	// //兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
	// if (strImportType==null || strImportType.isEmpty() ||
	// strImportType.equals("01"))
	// {
	// return strError;
	// }
	// }
	// catch
	// {
	// return "执行SQL语句错误";
	// }
	// }
	// }
	// }
	// if (iErrorCount > 0)
	// {
	// try
	// {
	// //转为字节数组
	// MemoryStream stream = new MemoryStream();
	// workbook.Write(stream);
	// byte[] buf = stream.ToArray();
	//
	// //文件存在就删除，因为Exel2007打开原来文件保存有问题，但是新创建的文件没有问题。
	// if (File.Exists(strFilePath))
	// {
	// File.Delete(strFilePath);
	// }
	// //保存为Excel文件
	// using (FileStream fs = new FileStream(strFilePath, FileMode.Create,
	// FileAccess.Write))
	// {
	// fs.Write(buf, 0, buf.Length);
	// fs.Flush();
	// }
	// }
	// catch (Exception ex)
	// {
	// return "保存错误文件失败。";
	// }
	// return "导入文件有" + iErrorCount.toString() + "处错误，请下载文件修改。";
	// }
	// return "";
	// //------------------------------------------------------
	// }

	// 得到自增长Id
	// public String Ident_Current()
	// {
	// SqlTool SqlHelper = new SqlTool("Oracle");
	// return SqlHelper.executeScalar("select ident_current('" + strTableName +
	// "')").toString();
	// }

	// 得到导入说明
	// / <summary>
	// / 得到导入说明
	// / </summary>
	// / <param name="ImportName">导入名称</param>
	// / <returns>string</returns>
	public String GetRemark(String ImportName) {
		SqlTool SqlHelper = new SqlTool(database);
		String obj = SqlHelper.executeScalar("select Remark from SM_ExcelImportCollect where ImportName='" + ImportName + "' ");
		SqlHelper.close();
		if (obj.isEmpty() || obj == null || obj.equals("")) {
			return "";
		} else {
			return obj.toString().replaceAll("\n", "<br>");
		}
	}

	// 得到导入状态
	// / <summary>
	// / 得到导入状态
	// / </summary>
	// / <param name="ImportName">导入名称</param>
	// / <returns>string</returns>
	public String GetCheckResult(String ImportName, String DefaultCheckResult) {
		if (ImportCheckResult != null && !ImportCheckResult.equals("")) {
			return ImportCheckResult;
		}
		String CheckResult = DefaultCheckResult;
		StringBuilder strSql = new StringBuilder();
		strSql.append("select * ");
		strSql.append("from SM_ExcelImportCollect ");
		strSql.append("where ImportName='" + ImportName + "' ");
		SqlTool SqlHelper = new SqlTool(database);
		ResultSet rs = SqlHelper.executeDataTable(strSql.toString());
		try {
			if (rs.next()) {
				if (!rs.getString("ImportCheckResult").equals("")) {
					CheckResult = rs.getString("ImportCheckResult");
				}
			}
		} catch (Exception ex) { }
		SqlHelper.close();

		return CheckResult;
	}

	// 得到模板文件路径
	// / <summary>
	// / 得到模板文件路径
	// / </summary>
	// / <param name="ImportName">导入名称</param>
	// / <returns>string</returns>
	public String GetTemplateFile(String ImportName) {
		if (TemplateFile != null && !TemplateFile.isEmpty()) {
			return TemplateFile;
		}
		SqlTool SqlHelper = new SqlTool(database);
		String obj = SqlHelper.executeScalar("select TemplateFile from SM_ExcelImportCollect where ImportName='" + ImportName + "' ");
		if (obj == null || obj.isEmpty()) {
			return "";
		} else {
			return obj.toString();
		}
	}

	// 私有方法

	// 字节长度
	private int LenOfByte(String str) throws UnsupportedEncodingException {
		if (str == null || str.isEmpty()) {
			return 0;
		}
		return str.getBytes().length;
		// return str.getBytes("utf-8").length;
	}

	// 导出初始化

	// / <summary>
	// / 导出初始化
	// / </summary>
	public String InitImport() throws SQLException {
		if (ImportName == null || ImportName.isEmpty()) {
			return "没有设置导入名称，请修改程序。";
		}

		if (Description == null || Description.equals("")) {
			StringBuilder strSql = new StringBuilder();
			strSql.append("select * from SM_ExcelImportCollect where ImportName='" + this.ImportName + "'");
			SqlTool SqlHelper = new SqlTool(database);
			ResultSet dtCollect = SqlHelper.executeDataTable(strSql.toString());
			if (dtCollect.next()) {
				Description = dtCollect.getString("Description");
				strTableName = dtCollect.getString("TableName");
				TemplateFile = dtCollect.getString("TemplateFile");
				ImportCheckResult = dtCollect.getString("ImportCheckResult");
				IdFieldName = dtCollect.getString("IdFieldName");
				SqlHelper.close();
			} else {
				SqlHelper.close();
				return "导入配制总表记录不存在。";
			}
		}

		if (dtItem == null || dtItem.size() == 0) {
			StringBuilder strSql = new StringBuilder();
			strSql.append("select ImportName, FieldName, ExcelColumnName, Visible, ColumnNumber, NotNull, FieldType, FieldShortestLen, FieldLen, CodeId, CodeName, OnlyFlag, AMUserImportType ");
			strSql.append("from SM_ExcelImportItem ");
//			strSql.append("LEFT JOIN (SELECT COLUMN_NAME FROM information_schema.COLUMNS  WHERE UPPER(TABLE_NAME)=UPPER('" + this.strTableName + "')) COLUMNS ON UPPER(columns.column_name)=UPPER(SM_ExcelImportItem.FieldName) ");
			strSql.append("where ImportName='" + this.ImportName + "' and Visible='01' ");
			strSql.append("order by ColumnNumber");
			SqlTool SqlHelper = new SqlTool(database);
			ResultSet rs = SqlHelper.executeDataTable(strSql.toString());
			this.dtItem = new ArrayList();
			while (rs.next()) {
				//判断导入配置的字段是否在数据库表中，如果不存在，给出提示
				String FieldName = rs.getString("FieldName");
				if(StringUtil.isEmpty(FieldName)){
					return "“"+rs.getString("ExcelColumnName")+"”列对应的字段不在数据库表中，请修改。";
				}
				SM_ExcelImportItem objItem = new SM_ExcelImportItem();
				objItem.setImportName(rs.getString("ImportName"));
				objItem.setFieldName(rs.getString("FieldName"));
				objItem.setExcelColumnName(rs.getString("ExcelColumnName"));
				objItem.setVisible(rs.getString("Visible"));
				objItem.setColumnNumber(rs.getString("ColumnNumber"));
				objItem.setNotNull(rs.getString("NotNull"));
				objItem.setFieldType(rs.getString("FieldType"));
				objItem.setFieldShortestLen(rs.getString("FieldShortestLen"));
				objItem.setFieldLen(rs.getString("FieldLen"));
				objItem.setCodeId(rs.getString("CodeId"));
				objItem.setCodeName(rs.getString("CodeName"));
				objItem.setOnlyFlag(rs.getString("OnlyFlag"));
				objItem.setAMUserImportType(rs.getString("AMUserImportType"));

				this.dtItem.add(objItem);
			}
			if (dtItem == null || dtItem.size() == 0) {
				return "导入配制从表记录不存在。";
			}
		}

		return "";
	}

	// 打开EXCEL
	// / <summary>
	// / 打开EXCEL
	// / </summary>
	// / <returns>错误信息</returns>
	public String OpenExcel() {
		// 检查模板文件是否存在
		File file = new File(strFilePath);
		if (!file.exists()) {
			return "导入文件不存在。";
		}
		return "";
	}

	// 关闭EXCEL
	// / <summary>
	// / 关闭EXCEL
	// / </summary>
	// / <param name="strFilePath">文件路径</param>
	// / <returns>错误信息</returns>
	public String CloseExcle() {
		return "";
	}

	// 插入批注
	// / <summary>
	// / 插入批注
	// / </summary>
	// / <param name="ICell">ICell</param>
	// / <param name="Col1">int</param>
	// / <param name="Col2">int</param>
	// / <param name="Row1">int</param>
	// / <param name="Row2">int</param>
	// / <param name="content">string批注内容</param>
	// / <param name="patr">IDrawing</param>
	// / <param name="facktory">ICreationHelper</param>
	// / <returns>void</returns>
	public void InsertComment(Cell myCell, int Col1, int Col2, int Row1,
			int Row2, String content, Drawing patr, CreationHelper facktory) {
		if (myCell != null) {
			if (myCell.getCellComment() == null) {
				// 建立批注
				if (content != null && !content.isEmpty()) {
					ClientAnchor anchor = facktory.createClientAnchor();
					anchor.setCol1(Col1);
					anchor.setCol2(Col2);
					anchor.setRow1(Row1);
					anchor.setRow2(Row2);
					Comment comment = patr.createCellComment(anchor);
					comment.setString(facktory.createRichTextString(content));
					myCell.setCellComment(comment);
				}
			} else {
				// 修改原批注文本
				if (content != null && !content.isEmpty()) {
					// String commentstr = myCell.CellComment.String.String;
					String commentstr = myCell.getCellComment().getString()
							.getString();
					if (commentstr != content) {
						myCell.getCellComment().setString(
								facktory.createRichTextString(commentstr
										+ "\r\n" + content));
						// myCell.CellComment.String =
						// facktory.CreateRichTextString(commentstr + "\r\n" +
						// content);
					}
				} else {
					myCell.removeCellComment();
				}
			}
		}
	}

	// 导入Excel
	private CellStyle NORMALCellStyle(Cell myCell) {
		// 去掉单元格底色
		CellStyle CellStyle = (CellStyle) myCell.getCellStyle();
		CellStyle.setFillPattern(FillPatternType.NO_FILL);

		return CellStyle;
	}

	private CellStyle ErrorCellStyle(Workbook workbook, Cell myCell) {
		// 设置单元格底色
		CellStyle NewCellStyle = workbook.createCellStyle();
		CellStyle OldCellStyle = (CellStyle) myCell.getCellStyle();
		NewCellStyle.cloneStyleFrom(OldCellStyle);
		NewCellStyle.setFillForegroundColor((short) 15);
		NewCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return NewCellStyle;
	}

	public String ImportFile() throws SQLException {
		// 导入初始化
		String strError = InitImport();
		if (!strError.isEmpty()) {
			return strError;
		}

		// 验证和设置开始导入行号
		int iBeginRow = 2;
		if (strRowNumber != null && !strRowNumber.isEmpty()) {
			try {
				iBeginRow = Integer.parseInt(strRowNumber);
			} catch (Exception e) {
				return "开始行号错误，必须输入正整数。";
			}
		}

		// 读取文件流
		InputStream readfile = null;
		try {
			readfile = new FileInputStream(strFilePath);
		} catch (Exception e) {
			return "导入文件读取失败。";
		}
		// 创建Workbook
		Workbook workbook = null;
		// 创建Workbook。文件为03版及以前版本
		try {
			// 创建Workbook。文件为03版及以前版本
			workbook = new HSSFWorkbook(readfile);
			Sheet sheetAt = workbook.getSheetAt(0);
			int lastRowNum = sheetAt.getLastRowNum();
			if (lastRowNum <= 0) {
				return "导入文件不能为空，请修改。";
			}
			// 判断除标题行之外的数据行是否为空
			for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
				Row row = sheetAt.getRow(rowIndex);
				if (row != null) {
					Iterator<Cell> cellIterator = row.cellIterator();
					boolean rowIsEmpty = true;
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getCellType() != CellType.BLANK) {
							rowIsEmpty = false;
							break;
						}
					}
					if (rowIsEmpty) {
						// 所有数据行都为空，判断为文件为空
						return "导入文件不能为空，请修改。";
					}
				}
			}



//			workbook = new HSSFWorkbook(new BufferedInputStream(readfile));
//			workbook=WorkbookFactory.create(readfile);
		} catch (Exception e) {
			// 创建Workbook。文件为07版及以后版本
			try {
				// 关闭读取的文件流
				if (readfile != null) {
					readfile.close();
				}
				return "导入文件格式不支持，只支持.xls格式的文件导入。";
				// 读取文件流
//				readfile = new FileInputStream(strFilePath);
				// 创建Workbook。文件为07版及以后版本
//				workbook = new XSSFWorkbook(readfile);
//				workbook=WorkbookFactory.create(readfile);
			} catch (Exception ex) {
				try {
					// 关闭读取的文件流
					if (readfile != null) {
						readfile.close();
					}
				} catch (Exception ex1) {}
				return "您上传的文件格式错误，请修改。";
			}
		}
		// 关闭读取的文件流
		if (readfile != null) {
			try {
				readfile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 创建Sheet
		Sheet mySheet = null;
		String SheetName = strSheetName;
		if (SheetName == null || SheetName.isEmpty()) {
			//获取所有sheet页，默认导入第一个不隐藏的sheet
			int numberOfSheets = workbook.getNumberOfSheets();		
			for (int i = 0; i < numberOfSheets; i++) {
				if(!workbook.isSheetHidden(i)){
					mySheet = workbook.getSheetAt(i);
					break;
				}
			}
		} else {
			if (workbook.getSheetIndex(SheetName) >= 0) {
				mySheet = workbook.getSheet(SheetName);
				
			} else {
				return "SheetName错误，请填写正确的Sheet名称。";
			}
		}
		//处理合并单元格
		removeMergedRegion(mySheet,iBeginRow);
		// 得到dt用于判断是否重复
		Map dtMap = new HashMap();
		String Id = "Id";
		if (IdFieldName != null && !IdFieldName.isEmpty()) {
			Id = IdFieldName;
		}
		ArrayList OnlyFlagList = new ArrayList();//唯一标准，用于记录是否存在的验证
		StringBuilder strAllCodeId=new StringBuilder();//codeid,用于验证code是否存在
		StringBuilder SelectField = new StringBuilder();
		for (int i = 0; i < dtItem.size(); i++) {
			SM_ExcelImportItem obj = dtItem.get(i);
			if (obj.getOnlyFlag().equals("01")) {
				OnlyFlagList.add(obj.getFieldName());
				if(obj.getFieldType().equals("datetime")){
					SelectField.append("DATE_FORMAT("+obj.getFieldName() + ",'%Y-%m-%d'),");
				} else if(obj.getFieldType().equals("dateandtime")){
					SelectField.append("DATE_FORMAT("+obj.getFieldName() + ",'%Y-%m-%d %H:%i:%s'),");
				} else if(obj.getFieldType().equals("datetime6")){
					SelectField.append("DATE_FORMAT("+obj.getFieldName() + ",'%Y-%m'),");
				} else {
					SelectField.append(obj.getFieldName() + ",");
				}
			}
			if (obj.getCodeId()!=null&&!obj.getCodeId().isEmpty()) {
				if(strAllCodeId.length()==0){
					strAllCodeId.append(obj.getCodeId());
				} else {
					strAllCodeId.append(","+obj.getCodeId());
				}
			}
		}
		if (SelectField.length() > 0) {
			SelectField.deleteCharAt(SelectField.length() - 1);
			SqlTool SqlHelper = new SqlTool(database);
			StringBuilder strOnlySql = new StringBuilder();
			strOnlySql.append("select ROW_NUMBER() OVER (PARTITION BY " + SelectField.toString() + " ORDER BY " + Id + ") ROWNUMBER," + SelectField.toString() + "," + Id + " ");
			strOnlySql.append("from " + strTableName + " ");
			strOnlySql.append(" where 1=1 " + OnlyWhere);
			ResultSet rs = SqlHelper.executeDataTable(strOnlySql.toString());
			StringBuilder strKey = new StringBuilder();
			StringBuilder strValue = new StringBuilder();
			while (rs.next()) {
				if (rs.getString("ROWNUMBER").equals("1")) {
					strKey = new StringBuilder();
					strValue = new StringBuilder();
					for (int i = 0; i < OnlyFlagList.size(); i++) {
						if (strKey.length() == 0) {
							strKey.append(rs.getString(i+2));
						} else {
							strKey.append("|*-*|" + rs.getString(i+2));
						}
					}
				}
				if (strValue.length() == 0) {
					strValue.append(rs.getString(Id));
				} else {
					strValue.append("|*-*|" + rs.getString(Id));
				}

				dtMap.put(strKey.toString(), strValue.toString());
			}
			SqlHelper.close();
		}
		Map dtCodeMap = new HashMap();
		if(strAllCodeId.length()>0){
			SqlTool SqlHelper = new SqlTool(database);
			StringBuilder strCodeSql = new StringBuilder();
			strCodeSql.append("select CodeId,Code,Description ");
			strCodeSql.append("from SM_CodeItem ");
			strCodeSql.append("where FLAG=1 and CodeId in ('"+strAllCodeId.toString().replaceAll(",", "','")+"') ");
			strCodeSql.append("order by CodeId,Code");
			ResultSet rs = SqlHelper.executeDataTable(strCodeSql.toString());
			while (rs.next()) {
				dtCodeMap.put(rs.getString("CodeId").toUpperCase()+"|*-*|"+rs.getString("Description"), rs.getString("Code"));
			}
			SqlHelper.close();
		}
		//判断是否需要将人员导入到成果人员子表。
		Map dtAMUserMap = new HashMap();
		for (int i = 0; i < dtItem.size(); i++) {
			SM_ExcelImportItem obj = dtItem.get(i);
			if(obj.getAMUserImportType()!=null && !obj.getAMUserImportType().equals("")){
				dtAMUserMap.put(obj.getAMUserImportType(), obj.getFieldName());
			}
		}
		if(dtAMUserMap.size()>0){
			String informationSchemaName = DataUtils.getConfInfo("informationSchemaName");
			SqlTool SqlHelper = new SqlTool(database);
			StringBuilder strSql = new StringBuilder();
			strSql.append("select COUNT(1) ");
			if(StringUtils.isNotBlank(informationSchemaName)) {
	            strSql.append("FROM (select * from INFORMATION_SCHEMA.COLUMNS where table_schema='" + informationSchemaName + "') columns ");
	        } else {
	        	strSql.append("FROM INFORMATION_SCHEMA.COLUMNS ");
	        }
			strSql.append("WHERE TABLE_NAME='" + strTableName + "' AND COLUMN_NAME='ImportFlag'");
			//strSql.append("WHERE TABLE_SCHEMA='kjmis_jxxdzy' and TABLE_NAME='"+strTableName+"' AND COLUMN_NAME='ImportFlag'");
			if(Integer.parseInt(SqlHelper.executeScalar(strSql.toString())) == 0) {
				//创建列
				SqlHelper.executeNonQuery("alter table " + strTableName + " add ImportFlag varchar(20)");
			}
			SqlHelper.close();
			AddField = AddField + "ImportFlag,";
			AddValue = AddValue + "'1',";
		}

		// 全面检查错误，将所有错误标记出来。
		int iErrorCount = 0;// 记录错误数
		Drawing patr = mySheet.createDrawingPatriarch();
		CreationHelper facktory = workbook.getCreationHelper();

		// ------开始导入------------------------------------------------
		strError = "";// 原来的错误提醒，用于兼容。
		StringBuilder strSql = new StringBuilder();
		// 处理附件类型，将附件放入相应文件夹路径下。
		strSql = new StringBuilder();
		strSql.append("select distinct FieldName,DefaultValue ");
		strSql.append("from SM_BuiltItem ");
		strSql.append("where TableName='" + strTableName + "' and FieldType in ('file','MultiFile') and ifnull(DefaultValue,'')!='' ");
		strSql.append("and FieldName in (select FieldName from SM_ExcelImportItem where ImportName='" + ImportName + "')");
		SqlTool SqlHelper = new SqlTool(database);
		ResultSet dtFile = SqlHelper.executeDataTable(strSql.toString());
		Map fileMap = new HashMap();
		int FileRow = 0;
		while (dtFile.next()) { 
			FileRow++;
			fileMap.put(dtFile.getString("FieldName"), dtFile.getString("DefaultValue"));
		}
		SqlHelper.close();

		int iError = 0;// 记录在哪行报错。
		SqlHelper = new SqlTool(database);
		SqlHelper.conn.setAutoCommit(false);
		SqlHelper.stmt = SqlHelper.conn.createStatement();
		try {
			// 导入前处理
			// if (this.strType == "01")//01为新增，新增时删除原来的记录
			// {
			// strSql = new StringBuilder("delete from " +
			// strTableName);//删除原来的数据
			// SqlHelper.executeNonQuery(trans, CommandType.Text,
			// strSql.toString());
			// }
			if (strImportStartSql != null && !strImportStartSql.isEmpty()) {
				String[] StartSqlList = strImportStartSql.split(";");
				for (int isql=0; isql<StartSqlList.length;isql++)
				{
					if(!StartSqlList[isql].toString().equals("")){
						SqlHelper.stmt.executeUpdate(StartSqlList[isql].toString());
					}
				}
			}
			// 导入数据
			int iNULLRowCount = 0;// 记录连续空行数
			for (int i = iBeginRow; i < 60000; i++)// 默认从第二行开始
			{
				// 记录行数
				iError = i;
				// 判断是否有值，是否导入完成。
				if (mySheet.getRow(i - 1) == null
						|| ((mySheet.getRow(i - 1).getCell(0) == null 
						|| mySheet.getRow(i - 1).getCell(0).toString().trim() == "")
								&& (mySheet.getRow(i - 1).getCell(1) == null || mySheet.getRow(i - 1).getCell(1).toString().trim() == "")
								&& (mySheet.getRow(i - 1).getCell(2) == null || mySheet
										.getRow(i - 1).getCell(2).toString()
										.trim() == "")
								&& (mySheet.getRow(i - 1).getCell(3) == null || mySheet
										.getRow(i - 1).getCell(3).toString()
										.trim() == "") && (mySheet
								.getRow(i - 1).getCell(4) == null || mySheet
								.getRow(i - 1).getCell(4).toString().trim() == "")))// 第一列为空，说明下面没有数据的，停止循环。
				{
					// 本行没有值记录，如果有连续十行没有值，导入结束。
					iNULLRowCount = iNULLRowCount + 1;
					if (iNULLRowCount > 10) {
						break;
					} else {
						continue;
					}
				} else {
					// 本行有值，清空连续空行记录。
					iNULLRowCount = 0;
				}

				// 导入sql语句构建
				StringBuilder strField = new StringBuilder();
				StringBuilder strValue = new StringBuilder();
				StringBuilder strUpdate = new StringBuilder();
				StringBuilder strWhere = new StringBuilder();
				if (AddField != null && AddValue != null && !AddField.isEmpty() && !AddValue.isEmpty()) {
					strField.append(AddField);
					strValue.append(AddValue);
				}
				if (ModifySQL != null && !ModifySQL.isEmpty()) {
					strUpdate.append(ModifySQL);
				}

				for (int j = 1; j <= dtItem.size(); j++) {
					SM_ExcelImportItem objItem = dtItem.get(j - 1);
					String strExcelColumnName = objItem.getExcelColumnName();// Excel列名
					try {
						// 得到配置信息
						String strFieldName = objItem.getFieldName();// 字段名称
						String strNotNull = objItem.getNotNull();// 不为空 00为允许，01为不允许
						String strFieldType = objItem.getFieldType().toLowerCase();// 字段类型
						String strCodeId = objItem.getCodeId().toLowerCase();// CodeId
						String strFieldShortestLen = objItem.getFieldShortestLen().toLowerCase();// 字段最短长度
						String strFieldLen = objItem.getFieldLen().toLowerCase();// 字段最长长度
						String strCodeName = objItem.getCodeName().toLowerCase();
						// 得到单元格的值
						String ExcelCellText = "";
						if (mySheet.getRow(i - 1).getCell(j - 1) != null) {
							ExcelCellText = GetCellValue(mySheet.getRow(i - 1).getCell(j - 1)).trim();
						}

						// 判断单元格值的有效性
						if (ExcelCellText.isEmpty()) {
							if (strNotNull.equals("01"))// 不允许为空
							{
								iErrorCount++;
								if (mySheet.getRow(i - 1).getCell(j - 1) == null) {
									mySheet.getRow(i - 1).createCell(j - 1);// 创建单元格
								}
								mySheet.getRow(i - 1).getCell(j - 1).setCellStyle(ErrorCellStyle(workbook,mySheet.getRow(i - 1).getCell(j - 1)));// 增加单位格背景色，这样容易发现。
								InsertComment(mySheet.getRow(i - 1).getCell(j - 1), j, j + 2, i, i + 3,"不能为空，请输入值。", patr, facktory);
								strError = "第" + iError + "行，第" + j + "列["+ strExcelColumnName + "]为空，请输入值。";
								// 兼容以前导入
								// 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
								if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
									return strError;
								}
								continue;
							} else {
								// 导入sql语句构建
								strField.append("" + strFieldName + ",");
								strValue.append("null,");
								strUpdate.append("" + strFieldName + "=null,");
								// 用于判断记录重复的过虑条件。
								if (objItem.getOnlyFlag().equals("01")) {
									if (strWhere.length() == 0) {
										strWhere.append(ExcelCellText);
									} else {
										strWhere.append("|*-*|" + ExcelCellText);
									}
								}

								if (mySheet.getRow(i - 1).getCell(j - 1) == null) {
									mySheet.getRow(i - 1).createCell(j - 1);// 创建单元格
								}
								mySheet.getRow(i - 1).getCell(j - 1).setCellStyle(NORMALCellStyle(mySheet.getRow(i - 1).getCell(j - 1)));// 去掉背景色
								InsertComment(mySheet.getRow(i - 1).getCell(j - 1), j, j + 2, i, i + 3, "", patr,facktory);// 去掉批注
							}
						} else {
							// 验证数据有效性
							if (!strFieldShortestLen.isEmpty() && (strFieldType.equals("varchar") || strFieldType.equals("text") || strFieldType.equals("textcode") || strFieldType.equals("textmultipleuser")))// 有最短长度限制的字符串
							{
								if (LenOfByte(ExcelCellText) < Integer.parseInt(strFieldShortestLen)) {
									iErrorCount++;
									mySheet.getRow(i - 1).getCell(j - 1).setCellStyle(ErrorCellStyle(workbook,mySheet.getRow(i - 1).getCell(j - 1)));// 增加单位格背景色，这样容易发现。
									InsertComment(mySheet.getRow(i - 1).getCell(j - 1),j,j + 2,i,i + 5,"内容太少，不能少于" + strFieldShortestLen + "个字节，约" + (Integer.parseInt(strFieldShortestLen) / 2) + "个汉字。", patr, facktory);
									strError = "第" + iError + "行，第" + j + "列[" + strExcelColumnName + "]内容太少，不能少于" + strFieldShortestLen + "个字节，约" + (Integer.parseInt(strFieldShortestLen) / 2) + "个汉字。";
									// 兼容以前导入
									// 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
									if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
										return strError;
									}
									continue;
								}
							}
							if (!strFieldLen.isEmpty() && (strFieldType.equals("varchar") || strFieldType.equals("text") || strFieldType.equals("textcode") || strFieldType.equals("textmultipleuser")))// 有最长长度限制的字符串
							{
								if (LenOfByte(ExcelCellText) > Integer.parseInt(strFieldLen)) {
									iErrorCount++;
									mySheet.getRow(i - 1).getCell(j - 1).setCellStyle(ErrorCellStyle(workbook,mySheet.getRow(i - 1).getCell(j - 1)));// 增加单位格背景色，这样容易发现。
									InsertComment(mySheet.getRow(i - 1).getCell(j - 1), j, j + 2, i, i + 5, "内容超过指定长度，最多只能输入" + strFieldLen + "个字节，约" + (Integer.parseInt(strFieldLen) / 2) + "个汉字。", patr, facktory);
									strError = "第" + iError + "行，第" + j + "列[" + strExcelColumnName + "]内容超过指定长度，最多只能输入" + strFieldLen + "个字节，约" + (Integer.parseInt(strFieldLen) / 2) + "个汉字。";
									// 兼容以前导入
									// 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
									if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
										return strError;
									}
									continue;
								}
							} else if (strFieldType.equals("datetime"))// 日期
							{
								try {
									ExcelCellText = ExcelCellText.replace("/", "-").replace(".", "-");
									if (ExcelCellText.length() == 8 && ExcelCellText.indexOf("-") == -1) {
										ExcelCellText = ExcelCellText.substring(0, 4) + "-" + ExcelCellText.substring(4, 6) + "-" + ExcelCellText.substring(6, 8);
									}
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
									// format.setLenient(false);
									ExcelCellText = format.format(format.parse(ExcelCellText)).toString();
								} catch (Exception ex) {
									iErrorCount++;
									mySheet.getRow(i - 1).getCell(j - 1).setCellStyle(ErrorCellStyle(workbook,mySheet.getRow(i - 1).getCell(j - 1)));// 增加单位格背景色，这样容易发现。
									InsertComment(mySheet.getRow(i - 1).getCell(j - 1), j, j + 2, i,i + 3, "日期格式错误，请修改。", patr,facktory);
									strError = "第" + iError + "行，第" + j + "列[" + strExcelColumnName + "]日期格式错误，请修改。";
									// 兼容以前导入
									// 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
									if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
										return strError;
									}
									continue;
								}
							} else if (strFieldType.equals("dateandtime"))// 日期
							{
								try {
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//									format.setLenient(false);
									ExcelCellText = format.format(format.parse(ExcelCellText)).toString();
								} catch (Exception ex) {
									iErrorCount++;
									mySheet.getRow(i - 1).getCell(j - 1).setCellStyle(ErrorCellStyle(workbook,mySheet.getRow(i - 1).getCell(j - 1)));// 增加单位格背景色，这样容易发现。
									InsertComment(mySheet.getRow(i - 1).getCell(j - 1), j, j + 2, i,i + 3, "时间格式错误，请修改。", patr,facktory);
									strError = "第" + iError + "行，第" + j + "列[" + strExcelColumnName + "]时间格式错误，请修改。";
									// 兼容以前导入
									// 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
									if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
										return strError;
									}
									continue;
								}
							} else if (strFieldType.equals("datetime6"))// 日期
							{
								try {
									ExcelCellText = ExcelCellText.replace("/", "-").replace(".", "-");
									if (ExcelCellText.length() == 6 && ExcelCellText.indexOf("-") == -1) {
										ExcelCellText = ExcelCellText.substring(0, 4) + "-" + ExcelCellText.substring(4, 6) + "-01";
									} else if (ExcelCellText.length() <= 7) {
										ExcelCellText = ExcelCellText + "-01";
									}
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//									format.setLenient(false);
									ExcelCellText = format.format(format.parse(ExcelCellText)).toString();
								} catch (Exception ex) {
									iErrorCount++;
									mySheet.getRow(i - 1).getCell(j - 1).setCellStyle(ErrorCellStyle(workbook,mySheet.getRow(i - 1).getCell(j - 1)));// 增加单位格背景色，这样容易发现。
									InsertComment(mySheet.getRow(i - 1).getCell(j - 1), j, j + 2, i,i + 3, "年月格式错误，请修改。", patr,facktory);
									strError = "第" + iError + "行，第" + j + "列[" + strExcelColumnName + "]年月格式错误，请修改。";
									// 兼容以前导入
									// 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
									if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
										return strError;
									}
									continue;
								}
							} else if (strFieldType.equals("int")
									|| strFieldType.equals("float")
									|| strFieldType.equals("money"))// 数字
							{
								try {
									ExcelCellText = Double.parseDouble(ExcelCellText.replace(",", "")) + "";
								} catch (Exception ex) {
									iErrorCount++;
									mySheet.getRow(i - 1).getCell(j - 1).setCellStyle(ErrorCellStyle(workbook,mySheet.getRow(i - 1).getCell(j - 1)));// 增加单位格背景色，这样容易发现。
									InsertComment(mySheet.getRow(i - 1).getCell(j - 1), j, j + 2, i,i + 3, "数字格式错误，请修改。", patr,facktory);
									strError = "第" + iError + "行，第" + j + "列[" + strExcelColumnName + "]数字格式错误，请修改。";
									// 兼容以前导入
									// 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
									if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
										return strError;
									}
									continue;
								}
							} else if (!strCodeId.isEmpty()
									&& (strFieldType.equals("code") || strFieldType.equals("multicode") || strFieldType.equals("select")
											|| strFieldType.equals("singleuser")
											|| strFieldType.equals("multipleuser")
											|| strFieldType.equals("checkboxlist") 
											|| strFieldType.equals("radiobuttonlist"))) {
								String strTemp = ExcelCellText;
//								if(dtCodeMap.containsKey(strCodeId.toUpperCase()+"|*-*|"+ExcelCellText)){
//									ExcelCellText=dtCodeMap.get(strCodeId.toUpperCase()+"|*-*|"+ExcelCellText).toString();
//								}
//								ResultSet rsCode=SqlHelper.stmt.executeQuery("select GetCode('" + strCodeId.toUpperCase() + "','" + ExcelCellText + "') from dual");
//								if(rsCode!=null&&rsCode.next()){
//									ExcelCellText=rsCode.getString(1);
//									rsCode.close();
//								}
								ExcelCellText = SqlHelper.executeScalar("select GetCode('" + strCodeId.toUpperCase() + "','" + ExcelCellText + "') from dual");
								
								if (ExcelCellText.isEmpty())// 不允许为空
								{
									iErrorCount++;
									InsertComment(mySheet.getRow(i - 1).getCell(j - 1), j, j + 2, i, i + 4, "没有对应数据，请检查数据是否正确。", patr, facktory);
									mySheet.getRow(i - 1).getCell(j - 1).setCellStyle(ErrorCellStyle(workbook, mySheet.getRow(i - 1).getCell(j - 1)));// 增加单位格背景色，这样容易发现。
									strError = "第" + iError + "行，第" + j + "列[" + strExcelColumnName + "]没有对应数据，请检查数据是否正确。";
									// 兼容以前导入
									// 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
									if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
										return strError;
									}
									continue;
								}
								// 选择类型同时保存描述
								if (strCodeName.equals("01")) {
									strField.append("" + strFieldName + "Name,");
									strValue.append("'" + strTemp.replace("'", "''") + "',");
									strUpdate.append("" + strFieldName + "Name='" + strTemp.replace("'", "''") + "',");
								}
							} else if ((strFieldType.equals("file") || strFieldType.equals("multifile")) && FileRow > 0) {
								// 处理附件类型，将附件放入相应文件夹路径下。;
								if (fileMap.containsKey(strFieldName)) {
									String FileFieldName = strFieldName;
									String FileDefaultValue = fileMap.get(strFieldName).toString();
									ExcelCellText = FileDefaultValue + ExcelCellText.replace(",", "*" + FileDefaultValue);
								}
							}
							// 导入sql语句构建
							if (strFieldType.equals("datetime") || strFieldType.equals("datetime6")) {
								strField.append("" + strFieldName + ",");
								strValue.append("to_date('" + ExcelCellText + "','%Y-%m-%d'),");
								strUpdate.append("" + strFieldName + "=to_date('" + ExcelCellText + "','%Y-%m-%d'),");
							} else if (strFieldType.equals("dateandtime")) {
								strField.append("" + strFieldName + ",");
								strValue.append("to_date('" + ExcelCellText + "','%Y-%m-%d %H:%i:%s'),");
								strUpdate.append("" + strFieldName + "=to_date('" + ExcelCellText + "','%Y-%m-%d %H:%i:%s'),");
							} else {
								strField.append("" + strFieldName + ",");
								strValue.append("'" + ExcelCellText.replace("'", "''") + "',");
								strUpdate.append("" + strFieldName + "='" + ExcelCellText.replace("'", "''") + "',");
							}

							// 用于判断记录重复的过虑条件。
							if (objItem.getOnlyFlag().equals("01")) {
								if (strWhere.length() == 0) {
									strWhere.append(ExcelCellText);
								} else {
									strWhere.append("|*-*|" + ExcelCellText);
								}
							}

							mySheet.getRow(i - 1).getCell(j - 1).setCellStyle(NORMALCellStyle(mySheet.getRow(i - 1).getCell(j - 1)));// 去掉背景色
							InsertComment(mySheet.getRow(i - 1).getCell(j - 1), j, j + 2, i, i + 3, "", patr, facktory);// 去掉批注
						}
					} catch (Exception ex) {
						// 不应该进入这里，如果进入请修改程序。
						iErrorCount++;
						mySheet.getRow(i - 1).getCell(j - 1).setCellStyle(ErrorCellStyle(workbook, mySheet.getRow(i - 1).getCell(j - 1)));// 增加单位格背景色，这样容易发现。
						InsertComment(mySheet.getRow(i - 1).getCell(j - 1), j, j + 2, i, i + 3, "数据有错，请修改。", patr, facktory);
						strError = "第" + iError + "行，第" + j + "列[" + strExcelColumnName + "]的数据有错";
						// 兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
						if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
							return strError;
						}
						// return "第" + iError.toString() + "行，第" + j.toString()
						// + "列[" + strExcelColumnName + "]的数据有错。";
					}
				}
				// 去掉字符串最后一位
				if (strField.length() > 0) {
					strField.deleteCharAt(strField.length() - 1);
				}
				if (strValue.length() > 0) {
					strValue.deleteCharAt(strValue.length() - 1);
				}
				if (strUpdate.length() > 0) {
					strUpdate.deleteCharAt(strUpdate.length() - 1);
				}
				// 生成执行SQL语句
				if (dtMap.size() > 0)// 判断重复或者修改数据
				{
					if (dtMap.containsKey(strWhere.toString()))// 如果记录重复
					{
						if (strType.equals("02") || strType.equals("03"))// 修改模式
						{
							if (dtMap.get(strWhere.toString()).toString().indexOf("|*-*|") > -1) {
								mySheet.getRow(i - 1).getCell(0).setCellStyle(ErrorCellStyle(workbook, mySheet.getRow(i - 1).getCell(0)));// 增加单位格背景色，这样容易发现。
								InsertComment(mySheet.getRow(i - 1).getCell(0), 0, 2, i, i + 3, "本行数据有多条重复，请修改。", patr, facktory);
							}
							// 修改sql语句
							if (iErrorCount == 0 && !dtMap.get(strWhere.toString()).toString().isEmpty())// 数据校验无问题
							{
								strSql = new StringBuilder();
								strSql.append("UPDATE " + strTableName + " SET " + strUpdate.toString() + " ");
								strSql.append("WHERE 1=1 and " + Id + "='" + dtMap.get(strWhere.toString()).toString() + "' " + OnlyWhere);

								SqlHelper.stmt.executeUpdate(strSql.toString());
							}
						} else {
							iErrorCount++;
							mySheet.getRow(i - 1).getCell(0).setCellStyle(ErrorCellStyle(workbook, mySheet.getRow(i - 1).getCell(0)));// 增加单位格背景色，这样容易发现。
							InsertComment(mySheet.getRow(i - 1).getCell(0), 0, 2, i, i + 3, "本行数据重复，请修改。", patr, facktory);
							strError = "第" + iError + "行数据重复，请修改。";
							// 兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
							if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
								return strError;
							}
							// return "第" + iError.toString() + "行的数据重复，请修改。";
						}
					} else {
						if (strType.equals("03"))// 只能修改
						{
							iErrorCount++;
							mySheet.getRow(i - 1).getCell(0).setCellStyle(ErrorCellStyle(workbook, mySheet.getRow(i - 1).getCell(0)));// 增加单位格背景色，这样容易发现。
							InsertComment(mySheet.getRow(i - 1).getCell(0), 0, 2, i, i + 3, "只能修改，记录不存在请修改。", patr, facktory);
							strError = "第" + iError + "行只能修改，记录不存在请修改。";
							// 兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
							if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
								return strError;
							}
						} else if (iErrorCount == 0)// 数据校验无问题
						{
							strSql = new StringBuilder();
							strSql.append("INSERT INTO " + strTableName + "(" + strField.toString() + ") VALUES(");
							strSql.append(strValue.toString() + ")");
							SqlHelper.stmt.executeUpdate(strSql.toString());

							// 得到dt用于判断是否重复
							if (OnlyFlagList.size() > 0) {
								dtMap.put(strWhere.toString(), "");
							}
						}
					}
				} else {
					if (strType.equals("03"))// 只能修改
					{
						iErrorCount++;
						mySheet.getRow(i - 1).getCell(0).setCellStyle(ErrorCellStyle(workbook, mySheet.getRow(i - 1).getCell(0)));// 增加单位格背景色，这样容易发现。
						InsertComment(mySheet.getRow(i - 1).getCell(0), 0, 2, i, i + 3, "只能修改，没有配制唯一标识，请配制。", patr, facktory);
						strError = "只能修改，没有配制唯一标识，请配制。";
						// 兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
						if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
							return strError;
						}
					} else if (iErrorCount == 0)// 数据校验无问题
					{
						strSql = new StringBuilder();
						strSql.append("INSERT INTO " + strTableName + "(" + strField.toString() + ") VALUES(");
						strSql.append(strValue.toString() + ")");
						SqlHelper.stmt.executeUpdate(strSql.toString());

						// 得到dt用于判断是否重复
						if (OnlyFlagList.size() > 0) {
							dtMap.put(strWhere.toString(), "");
						}
					}
				}
			}
			// 导入后处理
			if (iErrorCount == 0)// 数据校验无问题
			{
				if (!strImportEndSql.equals("")) {
					String[] EndSqlList = strImportEndSql.split(";");
					for (int isql=0; isql<EndSqlList.length;isql++)
					{
						if(!EndSqlList[isql].toString().equals("")){
							SqlHelper.stmt.executeUpdate(EndSqlList[isql].toString());
						}
					}
				}

				// 数据导入没有问题，提交导入数据。
				SqlHelper.conn.commit();
				SqlHelper.conn.setAutoCommit(true);
				SqlHelper.close();
				
				//导入后处理
                //判断是否需要将人员导入到成果人员子表。
				if(dtAMUserMap.size()>0){
					String AchievementType = strTableName;
                    String AllUsersName = "";//01	完成人员姓名
                    String AllDeptName = "";//02	院系名称
                    String AllRank = "";//03	完成人员排名
                    String AllAuthorType = "";//04	完成人员类别
                    String AllUserType = "";//05	完成人员角色
                    String AllWorkload = "";//06	工作量
                    String AllWordnumber = "";//07	字数
                    String SplitString = ",";
                    String strWhere = " and ImportFlag='1' ";

                    if (dtAMUserMap.get("01")!=null && !dtAMUserMap.get("01").toString().equals("")){
                        AllUsersName = dtAMUserMap.get("01").toString();
                    }
                    if (dtAMUserMap.get("02")!=null && !dtAMUserMap.get("02").toString().equals("")){
                    	AllDeptName = dtAMUserMap.get("02").toString();
                    }
                    if (dtAMUserMap.get("03")!=null && !dtAMUserMap.get("03").toString().equals("")){
                    	AllRank = dtAMUserMap.get("03").toString();
                    }
                    if (dtAMUserMap.get("04")!=null && !dtAMUserMap.get("04").toString().equals("")){
                    	AllAuthorType = dtAMUserMap.get("04").toString();
                    }
                    if (dtAMUserMap.get("05")!=null && !dtAMUserMap.get("05").toString().equals("")){
                    	AllUserType = dtAMUserMap.get("05").toString();
                    }
                    if (dtAMUserMap.get("06")!=null && !dtAMUserMap.get("06").toString().equals("")){
                    	AllWorkload = dtAMUserMap.get("06").toString();
                    }
                    if (dtAMUserMap.get("07")!=null && !dtAMUserMap.get("07").toString().equals("")){
                    	AllWordnumber = dtAMUserMap.get("07").toString();
                    }

                    SqlHelper = new SqlTool(database);
                    strSql = new StringBuilder();
                    try
                    {
                        for (int i = 0; i < 1000; i++)
                        {
                            //处理要导入的人员
                            String Workload = "0";
                            String AddFlag = "";
                            String Rank = ("0" + (i + 1)).substring(("0" + (i + 1)).length() - 2);
                            if (i == 0)
                            {
                                Workload = "100";
                                AddFlag = "01";
                            }

                            //将处理好的人员导入人员子表
                            strSql = new StringBuilder();
                            strSql.append("insert into AM_Achievement_User(AchievementType, AchievementId, UserId, TrueName, DeptName, Workload, AddFlag, \"Rank\", AuthorType, UserType, Wordnumber) ");
                            strSql.append("select '" + AchievementType + "', " + Id + ", '0'");
                            strSql.append(", replace(substr(" + AllUsersName + ",1,instr(CONCAT(" + AllUsersName + ",'" + SplitString + "'),'" + SplitString + "')-1),' ','') ");
                            if (!AllDeptName.equals(""))
                            {
                                strSql.append(", replace(substr(" + AllDeptName + ",1,instr(CONCAT(" + AllDeptName + ",'" + SplitString + "'),'" + SplitString + "')-1),' ','') ");
                            }
                            else
                            {
                                strSql.append(", '1**Import**1' DeptName");
                            }
                            if (!AllWorkload.equals(""))
                            {
                                strSql.append(", replace(substr(" + AllWorkload + ",1,instr(CONCAT(" + AllWorkload + ",'" + SplitString + "'),'" + SplitString + "')-1),' ','') , '" + AddFlag + "'");
                            }
                            else
                            {
                                strSql.append(", " + Workload + ", '" + AddFlag + "'");
                            }
                            if (!AllRank.equals(""))
                            {
                                strSql.append(", replace(substr(" + AllRank + ",1,instr(CONCAT(" + AllRank + ",'" + SplitString + "'),'" + SplitString + "')-1),' ','') ");
                            }
                            else
                            {
                                strSql.append(", '" + Rank + "'");
                            }
                            if (!AllAuthorType.equals(""))
                            {
                                strSql.append(", replace(substr(" + AllAuthorType + ",1,instr(CONCAT(" + AllAuthorType + ",'" + SplitString + "'),'" + SplitString + "')-1),' ','') ");
                            }
                            else
                            {
                                strSql.append(", '03' AuthorType");
                            }
                            if (!AllUserType.equals(""))
                            {
                                strSql.append(", replace(substr(" + AllUserType + ",1,instr(CONCAT(" + AllUserType + ",'" + SplitString + "'),'" + SplitString + "')-1),' ','') ");
                            }
                            else
                            {
                                strSql.append(", NULL UserType ");
                            }
                            if (!AllWordnumber.equals(""))
                            {
                                strSql.append(", replace(substr(" + AllWordnumber + ",1,instr(CONCAT(" + AllWordnumber + ",'" + SplitString + "'),'" + SplitString + "')-1),' ','') ");
                            }
                            else
                            {
                                strSql.append(", NULL Wordnumber ");
                            }
                            strSql.append("from " + AchievementType + " ");
                            strSql.append("where substr(" + AllUsersName + ",1,instr(CONCAT(" + AllUsersName + ",'" + SplitString + "'),'" + SplitString + "')-1) !='' " + strWhere);
                            strSql.append("order by " + Id + "");
                            SqlHelper.executeNonQuery(strSql.toString());

                            //更新用户列
                            strSql = new StringBuilder();
                            strSql.append("update " + AchievementType + " ");
                            strSql.append("set " + AllUsersName + "=substr(" + AllUsersName + ",instr(CONCAT(" + AllUsersName + ",'" + SplitString + "'),'" + SplitString + "')+1,length(" + AllUsersName + ")) ");
                            if (!AllDeptName.equals(""))
                            {
                                strSql.append(", " + AllDeptName + "=substr(" + AllDeptName + ",instr(CONCAT(" + AllDeptName + ",'" + SplitString + "'),'" + SplitString + "')+1,length(" + AllDeptName + ")) ");
                            }
                            if (!AllRank.equals(""))
                            {
                                strSql.append(", " + AllRank + "=substr(" + AllRank + ",instr(CONCAT(" + AllRank + ",'" + SplitString + "'),'" + SplitString + "')+1,length(" + AllRank + ")) ");
                            }
                            if (!AllAuthorType.equals(""))
                            {
                                strSql.append(", " + AllAuthorType + "=substr(" + AllAuthorType + ",instr(CONCAT(" + AllAuthorType + ",'" + SplitString + "'),'" + SplitString + "')+1,length(" + AllAuthorType + ")) ");
                            }
                            if (!AllUserType.equals(""))
                            {
                                strSql.append(", " + AllUserType + "=substr(" + AllUserType + ",instr(CONCAT(" + AllUserType + ",'" + SplitString + "'),'" + SplitString + "')+1,length(" + AllUserType + ")) ");
                            }
                            if (!AllWorkload.equals(""))
                            {
                                strSql.append(", " + AllWorkload + "=substr(" + AllWorkload + ",instr(CONCAT(" + AllWorkload + ",'" + SplitString + "'),'" + SplitString + "')+1,length(" + AllWorkload + ")) ");
                            }
                            if (!AllWordnumber.equals(""))
                            {
                                strSql.append(", " + AllWordnumber + "=substr(" + AllWordnumber + ",instr(CONCAT(" + AllWordnumber + ",'" + SplitString + "'),'" + SplitString + "')+1,length(" + AllWordnumber + ")) ");
                            }
                            if (!StringUtil.isEmpty(strWhere))
                            {
                                strSql.append("where 1=1 " + strWhere);
                            }
                            SqlHelper.executeNonQuery(strSql.toString());

                            //用户列都为空就终止导入
                            strSql = new StringBuilder();
                            strSql.append("select count(1) from " + AchievementType + " where ifnull(" + AllUsersName + ",'') !='' " + strWhere);
                            if (Integer.parseInt(SqlHelper.executeScalar(strSql.toString())) == 0)
                            {
                                break;
                            }
                        }
                    }
                    catch (Exception e) {
                        //导入标记
                        strSql = new StringBuilder();
                        strSql.append("update " + AchievementType + " ");
                        strSql.append("set ImportFlag=(select max(ImportFlag)+1 from " + AchievementType + ") ");
                        if (!StringUtil.isEmpty(strWhere))
                        {
                            strSql.append("where 1=1 " + strWhere);
                        }
                        SqlHelper.executeNonQuery(strSql.toString());
                        return "主表导入成功，人员拆分出问题，请记住提醒，速联系开发商。";
                    }

                    try
                    {
                        strSql = new StringBuilder();
                        strSql.append("update AM_Achievement_User set Workload=null where Workload='0' and AchievementType='" + AchievementType + "' ");
                        if (!StringUtil.isEmpty(strWhere))
                        {
                            strSql.append(" and AchievementId in (select " + Id + " from " + AchievementType + " where 1=1 " + strWhere + ")");
                        }
                        SqlHelper.executeNonQuery(strSql.toString());

                        strSql = new StringBuilder();
                        strSql.append("update AM_Achievement_User set Wordnumber=null where Wordnumber='0' and AchievementType='" + AchievementType + "' ");
                        if (!StringUtil.isEmpty(strWhere))
                        {
                            strSql.append(" and AchievementId in (select " + Id + " from " + AchievementType + " where 1=1 " + strWhere + ")");
                        }
                        SqlHelper.executeNonQuery(strSql.toString());

                        strSql = new StringBuilder();
                        strSql.append("update AM_Achievement_User set AddFlag=null where AddFlag='' and AchievementType='" + AchievementType + "' ");
                        if (!StringUtil.isEmpty(strWhere))
                        {
                            strSql.append(" and AchievementId in (select " + Id + " from " + AchievementType + " where 1=1 " + strWhere + ")");
                        }
                        SqlHelper.executeNonQuery(strSql.toString());

                        strSql = new StringBuilder();
                        strSql.append("update AM_Achievement_User set \"Rank\"=null where \"Rank\"='' and AchievementType='" + AchievementType + "' ");
                        if (!StringUtil.isEmpty(strWhere))
                        {
                            strSql.append(" and AchievementId in (select " + Id + " from " + AchievementType + " where 1=1 " + strWhere + ")");
                        }
                        SqlHelper.executeNonQuery(strSql.toString());

                        if (!AllDeptName.equals(""))
                        {
                            strSql = new StringBuilder();
                            strSql.append("update AM_Achievement_User ");
                            strSql.append("set DepartmentId=(select DepartmentId from SM_Department where Status='01' and NodeName=AM_Achievement_User.DeptName) ");
                            strSql.append("where DeptName in (select NodeName from SM_Department where Status='01') ");
                            strSql.append("and AchievementType='" + AchievementType + "' ");
                            if (!StringUtil.isEmpty(strWhere))
                            {
                                strSql.append(" and AchievementId in (select " + Id + " from " + AchievementType + " where 1=1 " + strWhere + ")");
                            }
                            SqlHelper.executeNonQuery(strSql.toString());
                        }

                        if (!AllRank.equals(""))
                        {
                            strSql = new StringBuilder();
                            strSql.append("update AM_Achievement_User ");
                            strSql.append("set \"Rank\"=(select Code from SM_CodeItem where CodeId='AO' and Flag=1 and Description=AM_Achievement_User.\"Rank\") ");
                            strSql.append("where \"Rank\" in (select Description from SM_CodeItem where CodeId='AO' and Flag=1) ");
                            strSql.append("and AchievementType='" + AchievementType + "' ");
                            if (!StringUtil.isEmpty(strWhere))
                            {
                                strSql.append(" and AchievementId in (select " + Id + " from " + AchievementType + " where 1=1 " + strWhere + ")");
                            }
                            SqlHelper.executeNonQuery(strSql.toString());
                        }

                        if (!AllAuthorType.equals(""))
                        {
                            strSql = new StringBuilder();
                            strSql.append("update AM_Achievement_User ");
                            strSql.append("set AuthorType=(select Code from SM_CodeItem where CodeId='DB' and Flag=1 and Description=AM_Achievement_User.AuthorType) ");
                            strSql.append("where AuthorType in (select Description from SM_CodeItem where CodeId='DB' and Flag=1) ");
                            strSql.append("and AchievementType='" + AchievementType + "' ");
                            if (!StringUtil.isEmpty(strWhere))
                            {
                                strSql.append(" and AchievementId in (select " + Id + " from " + AchievementType + " where 1=1 " + strWhere + ")");
                            }
                            SqlHelper.executeNonQuery(strSql.toString());

                            if (AllDeptName.equals(""))
                            {
                                strSql = new StringBuilder();
                                strSql.append("update AM_Achievement_User ");
                                strSql.append("set DeptName=NULL ");
                                strSql.append("where ifnull(AuthorType,'')!='01' ");
                                strSql.append("and AchievementType='" + AchievementType + "' ");
                                if (!StringUtil.isEmpty(strWhere))
                                {
                                    strSql.append(" and AchievementId in (select " + Id + " from " + AchievementType + " where 1=1 " + strWhere + ")");
                                }
                                SqlHelper.executeNonQuery(strSql.toString());
                            }
                        }

                        if (!AllUserType.equals(""))
                        {
                            strSql = new StringBuilder();
                            strSql.append("update AM_Achievement_User ");
                            strSql.append("set UserType=(select Code from SM_CodeItem where CodeId='AN' and Flag=1 and Description=AM_Achievement_User.UserType) ");
                            strSql.append("where UserType in (select Description from SM_CodeItem where CodeId='AN' and Flag=1) ");
                            strSql.append("and AchievementType='" + AchievementType + "' ");
                            if (!StringUtil.isEmpty(strWhere))
                            {
                                strSql.append(" and AchievementId in (select " + Id + " from " + AchievementType + " where 1=1 " + strWhere + ")");
                            }
                            SqlHelper.executeNonQuery(strSql.toString());
                        }

                        //更新内部人员信息
                        strSql = new StringBuilder();
                        strSql.append("update AM_Achievement_User ");
                        strSql.append("set UserId=(select UserId from (select * from SM_Users where usertype='00' and disabled='01' order by userid desc) SM_Users where replace(SM_Users.TrueName,' ','')=replace(AM_Achievement_User.TrueName,' ','') LIMIT 1) ");
                        if (AllDeptName.equals(""))
                        {
                            strSql.append(",DepartmentId=(select DepartmentId from (select * from SM_Users where usertype='00' and disabled='01' order by userid desc) SM_Users where replace(SM_Users.TrueName,' ','')=replace(AM_Achievement_User.TrueName,' ','') LIMIT 1) ");
                            strSql.append(",DeptName=(select getcodename('UM',DepartmentId) from (select * from SM_Users where usertype='00' and disabled='01' order by userid desc) SM_Users where replace(SM_Users.TrueName,' ','')=replace(AM_Achievement_User.TrueName,' ','') LIMIT 1) ");
                        }
                        strSql.append(",Subject=(select Subject from (select * from SM_Users where usertype='00' and disabled='01' order by userid desc) SM_Users where replace(SM_Users.TrueName,' ','')=replace(AM_Achievement_User.TrueName,' ','') LIMIT 1) ");
                        strSql.append(",AuthorType='01' ");
                        strSql.append("where UserId='0' and AchievementType='" + AchievementType + "' ");
                        if (!StringUtil.isEmpty(strWhere))
                        {
                            strSql.append(" and AchievementId in (select " + Id + " from " + AchievementType + " where 1=1 " + strWhere + ") ");
                        }
                        if (!AllAuthorType.equals(""))
                        {
                            strSql.append(" and AuthorType='01' ");
                        }
                        strSql.append("and TrueName in (select TrueName from SM_Users ");
                        strSql.append("where UserType='00' and Disabled='01' ");
                        strSql.append("and TrueName not in (select TrueName from (");
                        strSql.append("select row_number() over(partition by TrueName order by TrueName) rownumber,TrueName ");
                        strSql.append("from SM_Users ");
                        strSql.append("where UserType='00' and Disabled='01'");
                        strSql.append(") aa ");
                        strSql.append("where ifnull(TrueName,'')!='' and rownumber>1)) ");
                        SqlHelper.executeNonQuery(strSql.toString());

                        //主表处理
                        ResultSet dtAM = SqlHelper.executeDataTable("select * from " + strTableName + " where 1=2");
                        strSql = new StringBuilder();
                        strSql.append("update " + AchievementType + " ");
                        strSql.append("set AllUsers=(select GROUP_CONCAT(ifnull(UserId,0)) from (select * from AM_Achievement_User order by AchievementType,\"Rank\",id) AM_Achievement_User where AchievementType='" + AchievementType + "' and AM_Achievement_User.AchievementId=" + AchievementType + "." + Id + ") ");

                        try{
                        	if (dtAM.findColumn("AllUsersName")>0)
                            {
                        		strSql.append(",AllUsersName=(select GROUP_CONCAT(ifnull(TrueName,'')) from (select * from AM_Achievement_User order by AchievementType,\"Rank\",id) AM_Achievement_User where AchievementType='" + AchievementType + "' and AM_Achievement_User.AchievementId=" + AchievementType + "." + Id + ") ");
                            }
                        } catch (Exception ss){}
                        

                        try{
	                        if (dtAM.findColumn("AllUsersSearch")>0)
	                        {
	                        	strSql.append(",AllUsersSearch=(select GROUP_CONCAT(ifnull(TrueName,'')) from (select * from AM_Achievement_User order by AchievementType,\"Rank\",id) AM_Achievement_User where AchievementType='" + AchievementType + "' and AM_Achievement_User.AchievementId=" + AchievementType + "." + Id + ") ");
	                        }
                        } catch (Exception ss){}
                        
                        try{
	                        if (dtAM.findColumn("AllDepartmentId")>0)
	                        {
	                        	strSql.append(",AllDepartmentId=(select GROUP_CONCAT(ifnull(DepartmentId,'')) from (select * from AM_Achievement_User order by AchievementType,\"Rank\",id) AM_Achievement_User where AchievementType='" + AchievementType + "' and AM_Achievement_User.AchievementId=" + AchievementType + "." + Id + ") ");
	                        }
                        } catch (Exception ss){}
                        
                        try{
	                        if (dtAM.findColumn("AllWordnumber")>0)
	                        {
	                        	strSql.append(",AllWordnumber=(select GROUP_CONCAT(ifnull(Wordnumber,0)) from (select * from AM_Achievement_User order by AchievementType,\"Rank\",id) AM_Achievement_User where AchievementType='" + AchievementType + "' and AM_Achievement_User.AchievementId=" + AchievementType + "." + Id + ") ");
	                        }
                        } catch (Exception ss){}
                        
                        try{
	                        if (dtAM.findColumn("AllRankName")>0)
	                        {
	                        	strSql.append(",AllRankName=(select GROUP_CONCAT(ifnull(GetCodeName('AO',\"Rank\"),'')) from (select * from AM_Achievement_User order by AchievementType,\"Rank\",id) AM_Achievement_User where AchievementType='" + AchievementType + "' and AM_Achievement_User.AchievementId=" + AchievementType + "." + Id + ") ");
	                        }
                        } catch (Exception ss){}
                        
                        try{
	                        if (dtAM.findColumn("AllAuthorTypeName")>0)
	                        {
	                        	strSql.append(",AllAuthorTypeName=(select GROUP_CONCAT(ifnull(GetCodeName('DB',AuthorType),'')) from (select * from AM_Achievement_User order by AchievementType,\"Rank\",id) AM_Achievement_User where AchievementType='" + AchievementType + "' and AM_Achievement_User.AchievementId=" + AchievementType + "." + Id + ") ");
	                        }
                        } catch (Exception ss){}
                        
                        try{
	                        if (dtAM.findColumn("AllUserTypeName")>0)
	                        {
	                        	strSql.append(",AllUserTypeName=(select GROUP_CONCAT(ifnull(GetCodeName('AN',UserType),'')) from (select * from AM_Achievement_User order by AchievementType,\"Rank\",id) AM_Achievement_User where AchievementType='" + AchievementType + "' and AM_Achievement_User.AchievementId=" + AchievementType + "." + Id + ") ");
	                        }
                        } catch (Exception ss){}
                        try{
	                        if (!StringUtil.isEmpty(strWhere))
	                        {
	                            strSql.append("where 1=1 " + strWhere);
	                        }
                        } catch (Exception ss){}
                        SqlHelper.executeNonQuery(strSql.toString());

                        //导入标记
                        strSql = new StringBuilder();
                        strSql.append("update " + AchievementType + " ");
                        strSql.append("set ImportFlag=NULL ");
                        if (!StringUtil.isEmpty(strWhere))
                        {
                            strSql.append("where 1=1 " + strWhere);
                        }
                        SqlHelper.executeNonQuery(strSql.toString());

                        //if (AchievementType == "AM_Papers")
                        //{
                        //strSql.Append(",FirstAuthor='" + strFirst.toString() + "',FirstAuthorName='" + strFirstName.toString() + "' ");
                        //strSql.Append(",CommAuthor='" + strSecond.toString() + "',CommAuthorName='" + strSecondName.toString() + "' ");
                        //strSql.Append(",OtherAuthor='" + strThird.toString() + "',OtherAuthorName='" + strThirdName.toString() + "' ");
                        //}
                        //else if (AchievementType == "AM_Writings")
                        //{
                        //strSql.Append(",FirstManager='" + strFirst.toString() + "',FirstManagerName='" + strFirstName.toString() + "' ");
                        //strSql.Append(",ThirdManager='" + strSecond.toString() + "',ThirdManagerName='" + strSecondName.toString() + "' ");
                        //strSql.Append(",FourthManager='" + strThird.toString() + "',FourthManagerName='" + strThirdName.toString() + "' ");
                        //}
                    }
                    catch (Exception e) {
                        //导入标记
                        strSql = new StringBuilder();
                        strSql.append("update " + AchievementType + " ");
                        strSql.append("set ImportFlag=(select max(ImportFlag)+1 from " + AchievementType + ") ");
                        if (!StringUtil.isEmpty(strWhere))
                        {
                            strSql.append("where 1=1 " + strWhere);
                        }
                        SqlHelper.executeNonQuery(strSql.toString());
                        return "主表导入成功，人员拆分成功，人员后期处理出问题，请记住提醒，速联系开发商。";
                    }
                    SqlHelper.close();
				}

				return "";
			} else {
				// 数据导入有问题，回滚导入数据。
				SqlHelper.conn.rollback();
			}
		} catch (Exception ex) {
			// 格式验证通过，执行sql语句执行错误会进入这里。
			if (SqlHelper.conn != null) {
				SqlHelper.conn.rollback();
			}
			// 不应该进入这里，如果进入请修改程序。
			// return ex.Message.Replace("\r\n", "").Replace("'", "‘") + "SQL："
			// + strSql.toString().Replace("'", "‘");
			try {
				iErrorCount++;
				mySheet.getRow(iError - 1).getCell(0).setCellStyle(ErrorCellStyle(workbook, mySheet.getRow(iError - 1).getCell(0)));// 增加单位格背景色，这样容易发现。
				InsertComment(mySheet.getRow(iError - 1).getCell(0), 0, 7, iError, iError + 15, "执行SQL语句错误。\r\nSQL：" + strSql.toString() + "。\r\n错误：" + ex.getMessage().toString(), patr, facktory);
				strError = "第" + iError + "行执行SQL语句错误。";
				// 兼容以前导入 为空或者为01时有一个错误就提示具体错误，02为全面检查错误然后提示下载导入文件。
				if (strImportType == null || strImportType.isEmpty() || strImportType.equals("01")) {
					return strError;
				}
			} catch (Exception ex1) {
				return "第" + iError + "行的数据有错。";
			}
		}
		// if (iErrorCount == 1)
		// {
		// try
		// {
		// FileStream writefile = new FileStream(strFilePath,
		// FileMode.OpenOrCreate, FileAccess.Write);
		// workbook.Write(writefile);
		// writefile.Close();
		// }
		// catch (Exception ex)
		// {
		// return "保存错误文件失败。";
		// }
		// return strError;
		// }
		// else
		if (iErrorCount > 0) {
			try {
				File file = new File(strFilePath);
				if (file.exists()) {
					file.delete();
				}
				OutputStream stream = new FileOutputStream(strFilePath);
				workbook.write(stream);
				stream.flush();
				stream.close();
				workbook.close();
			} catch (Exception ex) {
				return "保存错误文件失败。";
			}
			return "导入文件有" + iErrorCount + "处错误，请下载文件修改。";
		} else {
			try {
				workbook.close();
			} catch (Exception ex) {
				return "文件关闭失败。";
			}
		}
		return "";
		// ------------------------------------------------------
	}

	// 得到单元格的值
	// / <summary>
	// /
	// / </summary>
	// / <param name="cell"></param>
	// / <returns></returns>
	private String GetCellValue(Cell cell) {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case STRING:
			String CellValue = cell.getStringCellValue();
			if (CellValue != null && CellValue.length() > 0) {
				return CellValue.toString();
			} else {
				return "";
			}
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat DateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return DateFormat.format(cell.getDateCellValue()).toString();
			} else {
				if(cell.getCellStyle().getDataFormatString().indexOf("%")!=-1){
					//百分比*100
					return getRealStringValueOfDouble(cell.getNumericCellValue()*100);
				} else {
					return getRealStringValueOfDouble(cell.getNumericCellValue());
				}
			}
		case BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case ERROR:
			return ErrorEval.getText(cell.getErrorCellValue());
		case FORMULA:// 公式类型
			switch (cell.getCachedFormulaResultTypeEnum()) {
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat DateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					return DateFormat.format(cell.getDateCellValue()).toString();
				} else {
					return getRealStringValueOfDouble(cell.getNumericCellValue());
				}
			case STRING:
				String strFORMULA = cell.getStringCellValue();
				if (strFORMULA != null && strFORMULA.length() > 0) {
					return strFORMULA.toString();
				} else {
					return "";
				}
			case BOOLEAN:
				return cell.getBooleanCellValue() + "";
			case ERROR:
				return ErrorEval.getText(cell.getErrorCellValue());
			default:
				return "";
			}
		default:
			return "";
//			return cell.getRichStringCellValue().getString();
		}
	}
	// 处理科学计数法与普通计数法的字符串显示，尽最大努力保持精度
    private String getRealStringValueOfDouble(Double d) {
        String doubleStr = d.toString();
        boolean b = doubleStr.contains("E");
        int indexOfPoint = doubleStr.indexOf('.');
        if (b) {
            int indexOfE = doubleStr.indexOf('E');
            // 小数部分
            BigInteger xs = new BigInteger(doubleStr.substring(indexOfPoint + BigInteger.ONE.intValue(), indexOfE));
            // 指数
            int pow = Integer.valueOf(doubleStr.substring(indexOfE + BigInteger.ONE.intValue()));
            int xsLen = xs.toByteArray().length;
            int scale = xsLen - pow > 0 ? xsLen - pow : 0;
            doubleStr = String.format("%." + scale + "f", d);
        } else {
            Pattern p = Pattern.compile(".0$");
            java.util.regex.Matcher m = p.matcher(doubleStr);
            if (m.find()) {
                doubleStr = doubleStr.replace(".0", "");
            }
        }
        return doubleStr;
    }
    
    private void CopyCell(Cell oldCell, Cell newCell) {
		if (oldCell == null) {
			newCell = null;
		}
		
		// Copy style from old cell and apply to new cell
		if (oldCell.getCellStyle() != null)
			newCell.setCellStyle(oldCell.getCellStyle());

		// If there is a cell comment, copy
		if (oldCell.getCellComment() != null)
			newCell.setCellComment(oldCell.getCellComment());

		// If there is a cell hyperlink, copy
		if (oldCell.getHyperlink() != null)
			newCell.setHyperlink(oldCell.getHyperlink());

        // Set the cell data type
		newCell.setCellType(oldCell.getCellType());

        // Set the cell data value
		switch (oldCell.getCellTypeEnum()) {
		case BLANK:
			newCell.setCellValue(oldCell.getStringCellValue());
			break;
		case BOOLEAN:
			newCell.setCellValue(oldCell.getBooleanCellValue());
			break;
		case ERROR:
			newCell.setCellErrorValue(oldCell.getErrorCellValue());
			break;
		case FORMULA:
			newCell.setCellFormula(oldCell.getCellFormula());
			break;
		case NUMERIC:
			newCell.setCellValue(oldCell.getNumericCellValue());
			break;
		case STRING:
			newCell.setCellValue(oldCell.getRichStringCellValue());
			break;
		default:
			newCell.setCellValue(oldCell.getStringCellValue());
			break;
		}
    }
    
    public void removeMergedRegion(Sheet sheet,int iBeginRow){
        List<CellRangeAddress> caList =sheet.getMergedRegions();
        for(int i = 0 ; i < caList.size() ; i++){
            CellRangeAddress ca = (CellRangeAddress)caList.get(i);
            if(ca==null){
            	continue;
            }
            int firstRow = ca.getFirstRow();
            if(firstRow+1<iBeginRow){
            	continue;
            }
            int lastRow = ca.getLastRow();
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            sheet.removeMergedRegion(i);
                  
            for(int iRow=firstRow;iRow<=lastRow;iRow++){
            	for(int iColumn=firstColumn;iColumn<=lastColumn;iColumn++){
            		if(iRow!=firstRow || iColumn!=firstColumn){
            			if(sheet.getRow(iRow).getCell(iColumn)==null){
            				sheet.getRow(iRow).createCell(iColumn);
            			}
            			CopyCell(sheet.getRow(firstRow).getCell(firstColumn),sheet.getRow(iRow).getCell(iColumn));
            		}
            	}
            }
        }     
    }  
    
    /*public void removeMergedRegion(Sheet sheet,int iBeginRow){
        int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            if(ca==null){
            	continue;
            }
            int firstRow = ca.getFirstRow();
            if(firstRow+1<iBeginRow){
            	continue;
            }
            int lastRow = ca.getLastRow();
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            sheet.removeMergedRegion(i);
                  
            for(int iRow=firstRow;iRow<=lastRow;iRow++){
            	for(int iColumn=firstColumn;iColumn<=lastColumn;iColumn++){
            		if(iRow!=firstRow || iColumn!=firstColumn){
            			if(sheet.getRow(iRow).getCell(iColumn)==null){
            				sheet.getRow(iRow).createCell(iColumn);
            			}
            			CopyCell(sheet.getRow(firstRow).getCell(firstColumn),sheet.getRow(iRow).getCell(iColumn));
            		}
            	}
            }
        }     
    }  */

	private String getStrValue(Object dto, String name) {
		Method[] m = dto.getClass().getMethods();
		for (int i = 0; i < m.length; i++) {
			if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
				Object result = null;
				try {
					result = m[i].invoke(dto);
				} catch (Exception ex) {
				}
				if (result == null)
					return "";
				else
					return result.toString();
			}
		}
		return "";
	}
}
