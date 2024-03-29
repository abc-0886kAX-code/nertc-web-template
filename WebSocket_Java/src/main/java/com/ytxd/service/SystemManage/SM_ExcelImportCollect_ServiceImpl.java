package com.ytxd.service.SystemManage;

import com.ytxd.common.DataUtils;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import com.ytxd.service.impl.BaseServiceImpl;
import com.ytxd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

@Service("SM_ExcelImportCollect_ServiceImpl")
public class SM_ExcelImportCollect_ServiceImpl extends BaseServiceImpl implements SM_ExcelImportCollect_Service {
	@Resource
	private CommonService service;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${informationSchemaName:''}")
	private String informationSchemaName;
	
	@Override
	public String ExportSql(HttpServletRequest request, String idlist) throws Exception {
		// 1. 使用File类打开一个文件；
		String fileName = DataUtils.getDate("yyyyMMdd") + (new Random().nextInt(900000)+100000) + "ExcelImport.txt";
//	    String filePath = request.getSession().getServletContext().getRealPath("UpFile/");
        String filePath = uploadFolder + "UpFile/";
        File file = new File(filePath);
	    if(!file.exists()){
	    	file.mkdirs();
	    }
	    file = new File(filePath, fileName);
		// 2. 通过字节流或字符流的子类指定输出的位置
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		// 3. 进行写操作
		StringBuilder strSql = new StringBuilder();
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_ExcelImportCollect");
		mySqlData.setFieldWhere("id", idlist, "in");
		mySqlData.setSort("ImportName");
		List<HashMap<String, Object>> listCollect = service.getDataList(mySqlData);
        
		//处理mysql里int类型在Insert不插入时赋默认值的问题
		strSql = new StringBuilder();
        strSql.append("SELECT COLUMN_NAME columnname ");
        if(StringUtils.isNotBlank(informationSchemaName)) {
            strSql.append("FROM (select * from INFORMATION_SCHEMA.COLUMNS where table_schema='" + informationSchemaName + "') columns ");
        } else {
        	strSql.append("FROM INFORMATION_SCHEMA.COLUMNS ");
        }
        strSql.append("WHERE DATA_TYPE in ('INT','INTEGER','BIGINT','SMALLINT','DECIMAL','DOUBLE','FLOAT','NUMERIC') AND TABLE_NAME='SM_ExcelImportCollect' AND COLUMN_NAME!='id' ");
        mySqlData = new MySqlData();
        mySqlData.setSql(strSql.toString());
        List<HashMap<String, Object>> listIntCollect = service.getDataList(mySqlData);
        StringBuilder intCollect = new StringBuilder();
        for (Map<String, Object> mapIntCollect : listIntCollect) {
			intCollect.append(DataUtils.getMapKeyValue(mapIntCollect, "columnname").toLowerCase() + ",");
        }
		
		//处理mysql里int类型在Insert不插入时赋默认值的问题
		strSql = new StringBuilder();
        strSql.append("SELECT COLUMN_NAME columnname ");
        if(StringUtils.isNotBlank(informationSchemaName)) {
            strSql.append("FROM (select * from INFORMATION_SCHEMA.COLUMNS where table_schema='" + informationSchemaName + "') columns ");
        } else {
        	strSql.append("FROM INFORMATION_SCHEMA.COLUMNS ");
        }
        strSql.append("WHERE DATA_TYPE in ('INT','INTEGER','BIGINT','SMALLINT','DECIMAL','DOUBLE','FLOAT','NUMERIC') AND TABLE_NAME='SM_ExcelImportItem' AND COLUMN_NAME!='id' ");
        mySqlData = new MySqlData();
        mySqlData.setSql(strSql.toString());
        List<HashMap<String, Object>> listIntItem = service.getDataList(mySqlData);
        StringBuilder intItem = new StringBuilder();
        for (Map<String, Object> mapIntItem : listIntItem) {
			intItem.append(DataUtils.getMapKeyValue(mapIntItem, "columnname").toLowerCase() + ",");
        }

		for (Map<String, Object> mapCollect : listCollect) {
            String importname = DataUtils.getMapKeyValue(mapCollect, "importname");
            //============================
            //生成指标集
            bw.write("DELETE FROM SM_ExcelImportCollect WHERE ImportName='" + importname + "'; ");
            bw.write("\r\n");
            StringBuilder strField = new StringBuilder();
            StringBuilder strValue = new StringBuilder();
            for(Entry<String, Object> entry : mapCollect.entrySet()) {
        	   	String key = entry.getKey();
        	   	Object value = entry.getValue();
        	   	
                if (value != null && StringUtils.isNotBlank(value.toString())) {
                	if(key.toLowerCase().equals("id")) {
                		continue;
                	}
                	strField.append(key + ",");
                    strValue.append("'" + value.toString().replace("'", "''") + "',");
                } 
            }
            //处理mysql里int类型在Insert不插入时赋默认值的问题
            if(StringUtils.isNotBlank(intCollect)) {
            	for(String intField : intCollect.toString().split(",")) {
					if (StringUtils.isNotBlank(intField) && ("," + strField.toString().toLowerCase()).indexOf("," + intField.toLowerCase() + ",") == -1) {
						strField.append(intField + ",");
						strValue.append("NULL,");
					}
                }
            }
            
            if (strField.length() > 0) {
                strField.deleteCharAt(strField.length() - 1);
            }
            if (strValue.length() > 0) {
                strValue.deleteCharAt(strValue.length() - 1);
            }
            bw.write("INSERT INTO SM_ExcelImportCollect(" + strField.toString() + ") ");
            bw.write("VALUES (" + strValue.toString() + "); ");
            bw.write("\r\n");

            mySqlData = new MySqlData();
            mySqlData.setTableName("SM_ExcelImportItem");
            mySqlData.setFieldWhere("ImportName", importname, "=");
            mySqlData.setSort("ColumnNumber");
            List<HashMap<String, Object>> listItem = service.getDataList(mySqlData);
            
            bw.write("DELETE FROM SM_ExcelImportItem WHERE ImportName='" + importname + "'; ");
            bw.write("\r\n");
            for(Map<String, Object> mapItem : listItem) {
                strField = new StringBuilder();
                strValue = new StringBuilder();
                Iterator<Entry<String, Object>> iItem = mapItem.entrySet().iterator();
                while(iItem.hasNext()) {
                	Entry<String, Object> entry = iItem.next();
                	String key = entry.getKey();
                	Object value = entry.getValue();
                	
                    if (value != null && StringUtils.isNotBlank(value.toString())) {
                    	if(key.toLowerCase().equals("id")) {
                    		continue;
                    	}
                    	strField.append(key + ",");
                        strValue.append("'" + value.toString().replace("'", "''") + "',");
                    } 
                }
                //处理mysql里int类型在Insert不插入时赋默认值的问题
                if(StringUtils.isNotBlank(intItem)) {
                	for(String intField : intItem.toString().split(",")) {
    					if (StringUtils.isNotBlank(intField) && ("," + strField.toString().toLowerCase()).indexOf("," + intField.toLowerCase() + ",") == -1) {
    						strField.append(intField + ",");
    						strValue.append("NULL,");
    					}
                    }
                }
                
                if (strField.length() > 0) {
                    strField.deleteCharAt(strField.length() - 1);
                }
                if (strValue.length() > 0) {
                    strValue.deleteCharAt(strValue.length() - 1);
                }
                bw.write("INSERT INTO SM_ExcelImportItem(" + strField.toString() + ") ");
                bw.write("VALUES (" + strValue.toString() + "); ");
                bw.write("\r\n");
            }
        }
		bw.flush();
		// 4. 关闭输出
		bw.close();
		
		return "UpFile/" + fileName;
	}
    @Override
    public String ExportFileAuto(HashMap<String, Object> map) throws Exception {
        // 生成导出文件路径
        String outFilePath = "ExcelOperation/template/"+DataUtils.getDate("yyyyMMdd") + (new Random().nextInt(900000) + 100000) + DataUtils.getMapKeyValue(map, "description") + ".xls";
        // 得到导出配置信息
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("SM_ExcelImportItem");
        mySqlData.setFieldWhere("importname", DataUtils.getMapKeyValue(map, "importname"), "=");
        mySqlData.setFieldWhere("Visible", "01", "=");
        mySqlData.setSort("columnnumber");
        mySqlData.setOrder("ASC");
        List<HashMap<String, Object>> listItem = service.getDataList(mySqlData);

        // 创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 得到SheetName名称
        String SheetName = "Sheet1";
        // 创建工作页
        HSSFSheet sheet = workbook.createSheet(SheetName);
        // 冻结首行
        sheet.createFreezePane(0, 1, 0, 1);
        // 创建日期格式
        HSSFCellStyle dateStyle = workbook.createCellStyle();
        HSSFDataFormat format = workbook.createDataFormat();
        dateStyle.setDataFormat(format.getFormat("yyyy-mm-dd"));

        // 增加表头和设置样式
        HSSFRow headerRow = sheet.createRow(0);
        //表头样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        headStyle.setWrapText(true);// 指定单元格自动换行
        //边框线
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setBorderTop(BorderStyle.THIN);

        //字体样式
        HSSFFont headFont = workbook.createFont();//字体样式
        //headFont.FontHeightInPoints = 10;//字号
        headFont.setBold(true);// 字体加粗
        headStyle.setFont(headFont);

        // 增加表头
        for (int i = 0; i < listItem.size(); i++) {
            HashMap<String, Object> mapItem = listItem.get(i);
            // 设置值
            headerRow.createCell(i).setCellValue(DataUtils.getMapKeyValue(mapItem, "excelcolumnname"));
            // 设置样式
            headerRow.getCell(i).setCellStyle(headStyle);
        }

        // 增加数据和设置样式
        HSSFRow dataRow = sheet.createRow(1);
        for (int i = 0; i < listItem.size(); i++) {
            HashMap<String, Object> mapItem = listItem.get(i);
            // 单元格样式
            HSSFCellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER);// 垂直居中
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setWrapText(true);// 指定单元格自动换行
            // 边框线
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            // 字体
            HSSFFont dataFont = workbook.createFont();
            dataFont.setFontHeightInPoints((short) 10);// 字号
            dataStyle.setFont(dataFont);
            HSSFDataFormat ColumnFormat = workbook.createDataFormat();
            switch (DataUtils.getMapKeyValue(mapItem, "fieldtype")) {
                case "int":
                    ColumnFormat = workbook.createDataFormat();
                    dataStyle.setDataFormat(ColumnFormat.getFormat("#,#0"));
                    break;
                case "float":
                case "money":
                    ColumnFormat = workbook.createDataFormat();
                    dataStyle.setDataFormat(ColumnFormat.getFormat("#,##0.00"));
                    break;
                case "datetime":
                    ColumnFormat = workbook.createDataFormat();
                    dataStyle.setDataFormat(ColumnFormat.getFormat("yyyy-mm-dd"));
                    break;
                case "datetime6":
                    ColumnFormat = workbook.createDataFormat();
                    dataStyle.setDataFormat(ColumnFormat.getFormat("yyyy-mm"));
                    break;
                case "dateandtime":
                    ColumnFormat = workbook.createDataFormat();
                    dataStyle.setDataFormat(ColumnFormat.getFormat("yyyy-mm-dd HH:mm:ss"));
                    break;
                case "select":
                case "RadioButtonList":
                case "CheckBoxList":
                    try {
                        MySqlData codeSqlData = new MySqlData();
                        String codeid = DataUtils.getMapKeyValue(mapItem, "codeid");
                        if(!"UM".equals(codeid) && !"UP".equals(codeid)){
                            codeSqlData.setTableName("sm_codeitem");
                            codeSqlData.setFieldWhere("codeid", codeid , "=");
                            codeSqlData.setFieldWhere("flag", "1", "=");
                            if(StringUtils.isNoneBlank(DataUtils.getMapKeyValue(mapItem, "wherevalue"))) {
                                codeSqlData.setFieldWhere("where", DataUtils.getMapKeyValue(mapItem, "wherevalue"), "sql");
                            }
                            codeSqlData.setSort("orderid");
                            codeSqlData.setOrder("ASC");
                            List<HashMap<String, Object>> listCode = service.getDataList(codeSqlData);
                            String[] textlist = new String[listCode.size()];
                            for (int n = 0; n < listCode.size(); n++) {
                                textlist[n] = DataUtils.getMapKeyValue(listCode.get(n), "description");
                            }

                            if(listCode.size() < 100) {
                                setHSSFValidation(sheet, workbook, codeid, textlist, 0, 5000, i, i);
                            } else {
                                ExcelTo255(workbook, DataUtils.getMapKeyValue(mapItem, "codeid"), textlist, 0, 500, i, i);// 第i+1列的前501行都设置为选择列表形式.
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }

            // 设置样式
            dataRow.createCell(i);
            dataRow.getCell(i).setCellStyle(dataStyle);

            // 设置列宽
            try {
                String ColumnWidth = DataUtils.getMapKeyValue(mapItem, "columnwidth");
                if (StringUtil.isNotEmpty(ColumnWidth)) {
                    sheet.setColumnWidth(i, Integer.parseInt(ColumnWidth) * 256);
                } else {
                    sheet.setColumnWidth(i, 12 * 256);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        FileOutputStream output;
        try {
            // 不存在创建路径
            File outFile = new File(uploadFolder + outFilePath);
            File fileParent = outFile.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            // 导出excel文件
            output = new FileOutputStream(uploadFolder + outFilePath, true);
            try {
                File file = new File(uploadFolder + outFilePath);
                if (file.exists()) {
                    file.delete();
                }
                workbook.write(output);
                output.flush();
                workbook.close();
                output.close();

                // 删除主表原来模板文件
                File templatefile = new File(uploadFolder + DataUtils.getMapKeyValue(map, "templatefile"));
                if (templatefile.exists()) {
                    templatefile.delete();
                }

                // 更新主表路径
                MySqlData updteColect = new MySqlData();
                updteColect.setTableName("sm_excelimportcollect");
                updteColect.setFieldValue("templatefile", outFilePath);
                updteColect.setFieldWhere("id", DataUtils.getMapKeyValue(map, "id"), "=");
                service.update(updteColect);
            } catch (IOException e) {
                System.out.println("文件写入错误");
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("要导出的文件路径错误");
            e.printStackTrace();
        }

        return "";
    }

    private static HSSFSheet setHSSFValidation(HSSFSheet sheet, HSSFWorkbook workbook, String codeid, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        codeid = codeid+"Code";
        //如果已经存在隐藏数据sheet就不创建直接引用
        if(workbook.getSheetIndex(codeid)==-1){
            HSSFSheet hidden = workbook.createSheet(codeid);
            HSSFCell cell = null;
            for (int i = 0, length = textlist.length; i < length; i++)
            {
                String name = textlist[i];
                HSSFRow row = hidden.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(name);
            }
            // 创建名称，可被其他单元格引用
            HSSFName namedCell = workbook.createName();
            namedCell.setNameName(codeid);
            // 设置名称引用的公式
            namedCell.setRefersToFormula(codeid+"!$A$1:$A$" + textlist.length);
        }
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(codeid);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        // 将对象数据sheet设置为隐藏
        workbook.setSheetHidden(1, true);
        if (null != data_validation_list)
        {
            // 将数据赋给下拉列表
            sheet.addValidationData(data_validation_list);
        }
        return sheet;
    }

    private void ExcelTo255(HSSFWorkbook workbook, String sheetName, String[] sheetData, int firstRow, int lastRow, int firstCol, int lastCol) {
        if (workbook.getSheetIndex(sheetName) == -1) {
            //将下拉框数据放到新的sheet里，然后excle通过新的sheet数据加载下拉框数据
            HSSFSheet hidden = workbook.createSheet(sheetName);

            //创建单元格对象
            HSSFCell cell =null;
            //遍历我们上面的数组，将数据取出来放到新sheet的单元格中
            for (int i = 0, length = sheetData.length; i < length; i++){
                //取出数组中的每个元素
                String name = sheetData[i];
                //根据i创建相应的行对象（说明我们将会把每个元素单独放一行）
                HSSFRow row = hidden.createRow(i);
                //创建每一行中的第一个单元格
                cell = row.createCell(0);
                //然后将数组中的元素赋值给这个单元格
                cell.setCellValue(name);
            }
            // 创建名称，可被其他单元格引用
            HSSFName namedCell = workbook.createName();
            namedCell.setNameName(sheetName);
            // 设置名称引用的公式
            namedCell.setRefersToFormula(sheetName+"!$A$1:$A$" + sheetData.length);
        }

        //加载数据,将名称为hidden的sheet中的数据转换为List形式
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(sheetName);

        // 设置第一列的3-65534行为下拉列表
        // (3, 65534, 2, 2) ====> (起始行,结束行,起始列,结束列)
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        // 将设置下拉选的位置和数据的对应关系 绑定到一起
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);

        //将第二个sheet设置为隐藏
        //workbook.setSheetHidden(sheetNameIndex, true);
        if (workbook.getSheetIndex(sheetName) >= 0) {
            workbook.setSheetHidden(workbook.getSheetIndex(sheetName), true);
        }
        //将数据赋给下拉列表
        workbook.getSheetAt(0).addValidationData(dataValidation);
    }
}
