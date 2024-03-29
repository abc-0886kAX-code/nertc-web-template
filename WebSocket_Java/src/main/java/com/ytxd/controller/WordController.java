package com.ytxd.controller;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspose.words.net.System.Data.DataRow;
import com.aspose.words.net.System.Data.DataTable;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import com.ytxd.service.WordExportService;
import com.ytxd.util.newDate;

import cn.hutool.core.util.RandomUtil;	


@Controller("WordController")
@RequestMapping("/word")
public class WordController {

	@Autowired
	WordExportService wordExportService;
	@Autowired
	CommonService service;

	@RequestMapping("/ExportWord")
	@ResponseBody
	public Map<String, Object> exportWord(HttpServletRequest request) throws Exception {
		String strError = "";
		// String apachePath =
		// request.getSession().getServletContext().getRealPath("/");
		String apachePath = DataUtils.getConfInfo("file.uploadFolder");
		String id = request.getParameter("id");
		String wordname = request.getParameter("wordname");
		if (StringUtils.isBlank(wordname)) {
			return R.error("wordname不能为空，请修改");
		}
		// 得到word配置主表的信息
		MySqlData mySqlData = new MySqlData();
		mySqlData.setSql("select * from SM_WordCollect  where WordName=");
		mySqlData.setSqlValue(wordname);
		List<HashMap<String, Object>> WordCollectList = service.getDataList(mySqlData);
		HashMap<String, Object> wordCollect = WordCollectList.get(0);
		String fileName = DataUtils.getMapKeyValue(wordCollect, "description");
		String tableName = DataUtils.getMapKeyValue(wordCollect, "tablename");
		String tableId = DataUtils.getMapKeyValue(wordCollect, "tableid");
		String templateFile = DataUtils.getMapKeyValue(wordCollect, "templatefile");
		String exportFile = DataUtils.getMapKeyValue(wordCollect, "exportfile");
		String isPDF = DataUtils.getMapKeyValue(wordCollect, "ispdf");
		String outSelect = DataUtils.getMapKeyValue(wordCollect, "outselect");
		String outLeftJoin = DataUtils.getMapKeyValue(wordCollect, "outleftjoin");
		String StrWhere = DataUtils.getMapKeyValue(wordCollect, "strwhere");
		
		HashMap<String, Object> data = null;

		// 验证导出记录唯一标识
		if (StringUtils.isBlank(id)) {
			if (StringUtils.isNotBlank(request.getParameter(tableId.toLowerCase()))) {
				id = request.getParameter(tableId.toLowerCase());
			}
		}
		
		if(StringUtils.isNotBlank(id)) {
			// 得到导出表数据
			mySqlData = new MySqlData();
			mySqlData.setTableName(tableName);
			mySqlData.setFieldWhere(tableId, id, "=");
			if (StringUtils.isNotBlank(outSelect)) {
				mySqlData.setSelectField("", outSelect.replace("\n",""));
			}
			if (StringUtils.isNotBlank(outLeftJoin)) {
				mySqlData.setJoinSql("", outLeftJoin);
			}
			if (StringUtils.isNotBlank(StrWhere)) {
				mySqlData.setFieldWhere("", StrWhere, "sql");
			}
			data = service.getMapByKey(mySqlData);
			if (data == null) {
				return R.error("主表没有查询到数据，请修改过滤条件。");
			}
			
			String exportname = DataUtils.getMapKeyValue(data, exportFile);
			if (StringUtils.isNotBlank(exportname)) {
				fileName += exportname;
			}
		}
		
		
		File file = new File(apachePath + templateFile);
		if (StringUtils.isBlank(templateFile) || !file.exists()) {
			return R.error("找不到模板文件，请上传模板");
		}
		// 模板文件存在删除
		String tempFileName = RandomUtil.randomUUID() + file.getName().substring(file.getName().lastIndexOf("."));
		String tempFilePath = apachePath + "WordOperation/template/" + tempFileName;
		File outFile = new File(tempFilePath);
		if (outFile.exists()) {
			outFile.delete();
		}
		String inputFilePath = apachePath + templateFile;
		File inputFile = new File(inputFilePath);
		FileUtils.copyFile(inputFile, outFile);
		Boolean bWordCreate = wordExportService.createNewWordDocument(apachePath + templateFile);
		if (!bWordCreate) {
			return R.error("word创建失败。");
		}
		if(data != null) {
			// 获取配置子表，循环插入数据
			mySqlData = new MySqlData();
			mySqlData.setSql("select * from SM_WordItem where Visible='01' and WordName=");
			mySqlData.setSqlValue(wordname);
			List<HashMap<String, Object>> WordItemList = service.getDataList(mySqlData);
			for (int i = 0; i < WordItemList.size(); i++) {
				HashMap<String, Object> worditem = WordItemList.get(i);
				// 验证数据源字段不为空并且有数据
				String sourcecolumnname = DataUtils.getMapKeyValue(worditem, "sourcecolumnname");
				String sourcecolumnnamevalue = DataUtils.getMapKeyValue(data, sourcecolumnname);
				String wordcolumnname = DataUtils.getMapKeyValue(worditem, "wordcolumnname");
				if (StringUtils.isNotBlank(sourcecolumnname) && StringUtils.isNotBlank(sourcecolumnnamevalue)) {
					// 判断数据类型
					String numberformatlocal = DataUtils.getMapKeyValue(worditem, "numberformatlocal");
					if (StringUtils.isBlank(numberformatlocal)) {
						strError += wordExportService.fill(wordcolumnname, sourcecolumnnamevalue);
					} else {
						if ("yyyy".equalsIgnoreCase(numberformatlocal)) { // 年度
							String value = GetData(sourcecolumnnamevalue, "Year");
							strError += wordExportService.fill(wordcolumnname, value);
						} else if ("yyyy-MM".equalsIgnoreCase(numberformatlocal)) { // 年月
							String value = GetData(sourcecolumnnamevalue, "Month");
							strError += wordExportService.fill(wordcolumnname, value);
						} else if ("yyyy-MM-dd".equalsIgnoreCase(numberformatlocal)) { // 年月日
							String value = GetData(sourcecolumnnamevalue, "Day");
							strError += wordExportService.fill(wordcolumnname, value);
						} else if ("yyyy-MM-dd HH:mm:ss".equalsIgnoreCase(numberformatlocal)) { // 年月日时分秒
							String value = GetData(sourcecolumnnamevalue, "Time");
							strError += wordExportService.fill(wordcolumnname, value);
						} else if ("select".equalsIgnoreCase(numberformatlocal)) { //
							SetCheckBox(wordcolumnname, sourcecolumnnamevalue, DataUtils.getMapKeyValue(worditem, "codeid"));
						} else if ("file".equalsIgnoreCase(numberformatlocal)) { // 文件类型，插入word文档
							String filePath = sourcecolumnnamevalue;
							if (StringUtils.isNotBlank(filePath)) {
								filePath = request.getSession().getServletContext().getRealPath("/") + filePath;
								File file2 = new File(filePath);
								if (file2.exists()) {
									strError += wordExportService.fillWord(wordcolumnname, filePath);
								} else {
									filePath = apachePath + sourcecolumnnamevalue;
									file2 = new File(filePath);
									if (file2.exists()) {
										strError += wordExportService.fillWord(wordcolumnname, filePath);
									}
								}							
							}
						} else if ("Age".equalsIgnoreCase(numberformatlocal)) { // 根据出生日期算年龄
							String value = getAge(sourcecolumnnamevalue);
							strError += wordExportService.fill(wordcolumnname, value);
						} else {
							strError += wordExportService.fill(wordcolumnname, sourcecolumnnamevalue);
						}
					}
				}
				if (StringUtils.isNotBlank(strError)) {
					return R.error(strError);
				}
			}
		}
		
		// 查询是否存在子表
		mySqlData = new MySqlData();
		mySqlData.setSql("select * from SM_WordCollect where ParentTable=");
		mySqlData.setSqlValue(wordname);
		List<HashMap<String, Object>> dtSubList = service.getDataList(mySqlData);
		if (dtSubList != null && dtSubList.size() > 0) {
			// 子表数据插入
			SubTableImport(dtSubList, id);
		}
		
		
		fileName = newDate.getDate("yyyyMMdd") + (new Random().nextInt(900000) + 100000) + fileName;
		String filePath = apachePath + "UpFile/" + fileName + file.getName().substring(file.getName().lastIndexOf("."));
		wordExportService.saveAs(filePath);
		if ("01".equals(isPDF)) {
			wordExportService.saveAsPDF(filePath, apachePath + "UpFile/" + fileName + ".pdf", null);
		}
		wordExportService.close();
		if (outFile.exists()) {
			// 删除模板文件
			outFile.delete();
		}

		String Path = "UpFile/" + fileName + file.getName().substring(file.getName().lastIndexOf("."));
		if ("01".equals(isPDF)) {
			Path = "UpFile/" + fileName + ".pdf";
		}
		if (StringUtils.isNotBlank(strError)) {
			return R.error(strError);
		} else {
			return R.ok().put("PATH", Path);
		}
	}
	
	
	private void SubTableImport(List<HashMap<String, Object>> dtSubList, String id) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		MySqlData mySqlData = new MySqlData();
		HashMap<String, Object> worditem = new HashMap<String, Object>();
		for (int i = 0; i < dtSubList.size(); i++) {
			// 得到word配置主表信息
			map = dtSubList.get(i);
			String tableName = DataUtils.getMapKeyValue(map, "tablename");
			String outSelect = DataUtils.getMapKeyValue(map, "outselect");
			String outLeftJoin = DataUtils.getMapKeyValue(map, "outleftjoin");
			String parentFile = DataUtils.getMapKeyValue(map, "parentfile");
			String StrWhere = DataUtils.getMapKeyValue(map, "strwhere");
			String wordname = DataUtils.getMapKeyValue(map, "wordname");
			String worddtname = DataUtils.getMapKeyValue(map, "worddtname");
			String sortorder = DataUtils.getMapKeyValue(map, "sortorder");
			// 获取数据源
			mySqlData = new MySqlData();
			mySqlData.setTableName(tableName);
			if (StringUtils.isNotBlank(parentFile)) {
				mySqlData.setFieldWhere(parentFile, id, "=");
			}
			if (StringUtils.isNotBlank(outSelect)) {
				mySqlData.setSelectField("", outSelect);
			}
			if (StringUtils.isNotBlank(outLeftJoin)) {
				mySqlData.setJoinSql("", outLeftJoin);
			}
			if (StringUtils.isNotBlank(StrWhere)) {
				mySqlData.setFieldWhere("where" + i, StrWhere.replace("{0}", id), "sql");
			}
			if (StringUtils.isNotBlank(sortorder)) {
				mySqlData.setSort(sortorder);
			}
			List<HashMap<String, Object>> dataList = service.getJoinDataList(mySqlData);
			// 获取word配置子表信息
			mySqlData = new MySqlData();
			mySqlData.setSql("select * from SM_WordItem  where Visible='01' and WordName=");
			mySqlData.setSqlValue(wordname);
			List<HashMap<String, Object>> WordItemList = service.getDataList(mySqlData);
			DataTable dt = new DataTable(worddtname);
			
			// 创建一个数据表，和word模板里的表对应
			for (int j = 0; j < WordItemList.size(); j++) {
				worditem = WordItemList.get(j);
				dt.getColumns().add(DataUtils.getMapKeyValue(worditem, "wordcolumnname"));
			}
			DataRow row = dt.newRow(); // 新增一行
			// 循环插入
			if (dataList.size() > 0) {
				for (int j = 0; j < dataList.size(); j++) {
					row = dt.newRow(); // 新增一行
					for (int k = 0; k < WordItemList.size(); k++) {
						worditem = WordItemList.get(k);
						String value = "";
						String numberformatlocal = DataUtils.getMapKeyValue(worditem, "numberformatlocal");
						String sourcecolumnname = DataUtils.getMapKeyValue(worditem, "sourcecolumnname");
						String sourcecolumnnamevalue = DataUtils.getMapKeyValue(dataList.get(j), sourcecolumnname);
						if (StringUtils.isNotBlank(sourcecolumnname) && StringUtils.isNotBlank(sourcecolumnnamevalue)) {
							if (StringUtils.isBlank(numberformatlocal)) {
								value = sourcecolumnnamevalue;
							} else {
								// 判断数据类型
								if ("yyyy".equalsIgnoreCase(numberformatlocal)) { // 年度
									value = GetData(sourcecolumnnamevalue, "Year");
								} else if ("yyyy-MM".equalsIgnoreCase(numberformatlocal)) { // 年月
									value = GetData(sourcecolumnnamevalue, "Month");
								} else if ("yyyy-MM-dd".equalsIgnoreCase(numberformatlocal)) { // 年月日
									value = GetData(sourcecolumnnamevalue, "Day");
								} else if ("yyyy-MM-dd HH:mm:ss".equalsIgnoreCase(numberformatlocal)) { // 年月日时分秒
									value = GetData(sourcecolumnnamevalue, "Time");
								} else if ("Age".equalsIgnoreCase(numberformatlocal)) { // 根据出生日期算年龄
									value = getAge(sourcecolumnnamevalue);
								} else {
									value = sourcecolumnnamevalue;
								}
							}
						}
						// 生成序号
						if ("number".equalsIgnoreCase(numberformatlocal)) {
							value = j + 1 + "";
						}
						row.set(k, value);
					}
					dt.getRows().add(row); // 加入此行数据
				}
				/*dt.getRows().add(dt.newRow());
				// 增加空行，防止显示定义的字段等信息。
				row = dt.newRow();
				for (int k = 0; k < WordItemList.size(); k++) {
					row.set(k, "");
				}
				dt.getRows().add(row);*/

			} else {
				dt.getRows().add(dt.newRow());
				// 增加空行，防止显示定义的字段等信息。
				row = dt.newRow();
				for (int k = 0; k < WordItemList.size(); k++) {
					row.set(k, "");
				}
				dt.getRows().add(row);
			}
			wordExportService.executeWithRegions(dt);
		}
		
	}

	// 时间数据格式化
	private String GetData(String dateStr, String type) {
		String ResDateTime = "";
		if (StringUtils.isNotBlank(dateStr)) {
			try {
				String parse = dateStr;
				parse = parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");
				parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");
				parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");
				parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");
				parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");
				parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");
				parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");
				SimpleDateFormat format = new SimpleDateFormat(parse);
				Date date = format.parse(dateStr);
				if ("Year".equalsIgnoreCase(type)) {
					format = new SimpleDateFormat("yyyy");
					ResDateTime = format.format(date);
				} else if ("Month".equalsIgnoreCase(type)) {
					format = new SimpleDateFormat("yyyy年MM月");
					ResDateTime = format.format(date);
				} else if ("Day".equalsIgnoreCase(type)) {
					format = new SimpleDateFormat("yyyy-MM-dd");
					ResDateTime = format.format(date);
				} else if ("Time".equalsIgnoreCase(type)) {
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ResDateTime = format.format(date);
				} else if ("Age".equalsIgnoreCase(type)) {
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ResDateTime = format.format(date);
				}
			} catch (Exception ex) {
			}
		}
		return ResDateTime;
	}

	// 设置导出为单选或多选的数据
	private void SetCheckBox(String wordcolumnname, String Value, String CodeId) {
		try {
			// 查找参数数据
			MySqlData mySqlData = new MySqlData();
			mySqlData.setSql("select * from SM_CodeItem where CodeId = '" + CodeId + "' and flag='1' ");
			List<HashMap<String, Object>> dataList = service.getDataList(mySqlData);
			// 遍历参数 循环给每个参数对应的数据赋值
			for (int i = 0; i <= dataList.size(); i++) {
				String code = DataUtils.getMapKeyValue(dataList.get(i), "code");
				if (("," + Value + ",").indexOf("," + code + ",") != -1) {
					wordExportService.fill(wordcolumnname + code, "☑");
				} else {
					wordExportService.fill(wordcolumnname + code, "□");
				}
			}
		} catch (Exception ex) {
		}
	}

	// 根据出生日期计算年龄
	public static String getAge(String birthDay) throws Exception {
		if (StringUtils.isBlank(birthDay)) {
			return birthDay;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDayDate = sdf.parse(birthDay);
		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDayDate);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		return age + "";
	}
}
