package com.ytxd.service.SystemManage;

import com.alibaba.fastjson.JSON;
import com.ytxd.common.DataUtils;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import com.ytxd.service.impl.BaseServiceImpl;
import com.ytxd.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.Map.Entry;

@Service("SM_ListCollect_ServiceImpl")
public class SM_ListCollect_ServiceImpl extends BaseServiceImpl implements SM_ListCollect_Service {
	@Resource
	private CommonService service;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${informationSchemaName:''}")
	private String informationSchemaName;
	
	@Override
	public String ExportSql(HttpServletRequest request, String idlist) throws Exception {
		// 1. 使用File类打开一个文件；
		String fileName = DataUtils.getDate("yyyyMMdd") + (new Random().nextInt(900000)+100000) + "List.txt";
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
		mySqlData.setTableName("SM_ListCollect");
		mySqlData.setFieldWhere("id", idlist, "in");
		mySqlData.setSort("ListName");
		List<HashMap<String, Object>> listCollect = service.getDataList(mySqlData);
        
		//处理mysql里int类型在Insert不插入时赋默认值的问题
		strSql = new StringBuilder();
        strSql.append("SELECT COLUMN_NAME columnname ");
        if(StringUtils.isNotBlank(informationSchemaName)) {
            strSql.append("FROM (select * from INFORMATION_SCHEMA.COLUMNS where table_schema='" + informationSchemaName + "') columns ");
        } else {
        	strSql.append("FROM INFORMATION_SCHEMA.COLUMNS ");
        }
        strSql.append("WHERE DATA_TYPE in ('INT','INTEGER','BIGINT','SMALLINT','DECIMAL','DOUBLE','FLOAT','NUMERIC') AND TABLE_NAME='SM_ListCollect' AND COLUMN_NAME!='id' ");
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
        strSql.append("WHERE DATA_TYPE in ('INT','INTEGER','BIGINT','SMALLINT','DECIMAL','DOUBLE','FLOAT','NUMERIC') AND TABLE_NAME='SM_ListItem' AND COLUMN_NAME!='id' ");
        mySqlData = new MySqlData();
        mySqlData.setSql(strSql.toString());
        List<HashMap<String, Object>> listIntItem = service.getDataList(mySqlData);
        StringBuilder intItem = new StringBuilder();
        for (Map<String, Object> mapIntItem : listIntItem) {
			intItem.append(DataUtils.getMapKeyValue(mapIntItem, "columnname").toLowerCase() + ",");
        }

		for (Map<String, Object> mapCollect : listCollect) {
            String listname = DataUtils.getMapKeyValue(mapCollect, "listname");

            //============================
            //生成指标集
            bw.write("DELETE FROM SM_ListCollect WHERE ListName='" + listname + "'; ");
            bw.write("\r\n");
            StringBuilder strField = new StringBuilder();
            StringBuilder strValue = new StringBuilder();
            for(Entry<String, Object> entry : mapCollect.entrySet()) {
        	   	String key = entry.getKey();
        	   	Object value = entry.getValue();
        	   	
                if (value != null && StringUtils.isNotBlank(value.toString())) {
                	if(key.toLowerCase().equals("id") || key.toLowerCase().equals("listhtml")) {
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
            bw.write("INSERT INTO SM_ListCollect(" + strField.toString() + ") ");
            bw.write("VALUES (" + strValue.toString() + "); ");
            bw.write("\r\n");

            mySqlData = new MySqlData();
            mySqlData.setTableName("SM_ListItem");
            mySqlData.setFieldWhere("ListName", listname, "=");
            mySqlData.setSort("InsertIndex");
            List<HashMap<String, Object>> listItem = service.getDataList(mySqlData);
            
            bw.write("DELETE FROM SM_ListItem WHERE ListName='" + listname + "'; ");
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
                bw.write("INSERT INTO SM_ListItem(" + strField.toString() + ") ");
                bw.write("VALUES (" + strValue.toString() + "); ");
                bw.write("\r\n");
            }
        }
		//刷新流
	    bw.flush();
		// 4. 关闭输出
		bw.close();
		
		return "UpFile/" + fileName;
	}
	@Override
	public Integer InitExcelByList(HttpServletRequest request, String listname) throws Exception {
		listname = listname.replace("'", "").replace(" ", "");
		service.delete("SM_ExcelCollect", listname, "ExportName");
        
		MySqlData mySqlData = new MySqlData();
        mySqlData = new MySqlData();
        mySqlData.setSql("insert into SM_ExcelCollect(ExportName, Description, SheetName, RowNumber, ColumnCount, TemplateFile, Auto, CopyFormat) ");
        mySqlData.setSql("select ListName, Description, Description, 2, 50, NULL, '01', NULL ");
        mySqlData.setSql("from SM_ListCollect ");
        mySqlData.setFieldWhere("ListName", listname, "=");
        mySqlData.setSort("ListName");
    	service.insert(mySqlData);
        
    	service.delete("SM_ExcelItem", listname, "ExportName");
        
        mySqlData = new MySqlData();
        StringBuilder strSql = new StringBuilder();
        strSql.append("insert into SM_ExcelItem(ExportName, SourceColumnName, ExcelColumnName, RowNumber, ColumnNumber, Visible, ColumnWidth, HorizontalAlignment, NumberFormatLocal, IfTotal) ");
        strSql.append("select ListName, DataField, HeaderText, NULL, InsertIndex*5, '01', case when Width like '%px%' then replace(Width,'px','')/6 else replace(Width,'%','')*3 end, ");
        strSql.append("case when HorizontalAlign='1' then '-4131' when HorizontalAlign='2' then '-4108' when HorizontalAlign='3' then '-4152' when HorizontalAlign='4' then '-4130' else NULL end,");
        strSql.append("case when DataFormatString is NULL then NULL ");
        strSql.append("when DataFormatString like '%yy%' then REPLACE(REPLACE(DataFormatString,'{0:',''),'}','') ");
        strSql.append("when DataFormatString='{0:n0}' or DataFormatString='{0:n}' then '#,##0' ");
        strSql.append("when DataFormatString='{0:n1}' then '#,##0.0' ");
        strSql.append("when DataFormatString='{0:n2}' then '#,##0.00' ");
        strSql.append("when DataFormatString='{0:n3}' then '#,##0.000' ");
        strSql.append("when DataFormatString='{0:n4}' then '#,##0.0000' ");
        strSql.append("else NULL end,IfTotal ");
        strSql.append("from SM_ListItem ");
        mySqlData.setSql(strSql.toString());
        mySqlData.setFieldWhere("ListName", listname, "=");
        mySqlData.setFieldWhere("Visible", "01", "=");
        mySqlData.setSort("InsertIndex");        
        service.insert(mySqlData);
        
        return 1;
	}
	
	@Override
	public void UpdateListHtml(HttpServletRequest request, String listname) throws Exception {
		// String ListHtml = service.getListHtml(listname, "ListTableAlias", request);
		// 先删除redis里的数据，然后才会重新生成。
		RedisUtils.delete("list:" + listname);
		// 得到最新的配置数据
		List<HashMap<String, Object>> listList = service.getListJson(listname);
		String jsonList = JSON.toJSONString(listList);
		// 更新数据库里的配置数据
		/*MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_ListCollect");
		mySqlData.setFieldValue("ListHtml", jsonList);
		mySqlData.setFieldWhere("ListName", listname, "=");
		service.update(mySqlData);*/
		// 更新redis的值
		RedisUtils.set("list:" + listname, jsonList);
	}
}
