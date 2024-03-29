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

@Service("SM_SearchCollect_ServiceImpl")
public class SM_SearchCollect_ServiceImpl extends BaseServiceImpl implements SM_SearchCollect_Service {
	@Resource
	private CommonService service;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${informationSchemaName:''}")
	private String informationSchemaName;
	
	@Override
	public String ExportSql(HttpServletRequest request, String idlist) throws Exception {
		// 1. 使用File类打开一个文件；
		String fileName = DataUtils.getDate("yyyyMMdd") + (new Random().nextInt(900000)+100000) + "Search.txt";
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
		mySqlData.setTableName("SM_SearchCollect");
		mySqlData.setFieldWhere("id", idlist, "in");
		mySqlData.setSort("SearchPageName");
		List<HashMap<String, Object>> listCollect = service.getDataList(mySqlData);
        
		//处理mysql里int类型在Insert不插入时赋默认值的问题
		strSql = new StringBuilder();
        strSql.append("SELECT CONCAT(',',GROUP_CONCAT(DISTINCT COLUMN_NAME),',') columnname ");
        if(StringUtils.isNotBlank(informationSchemaName)) {
            strSql.append("FROM (select * from INFORMATION_SCHEMA.COLUMNS where table_schema='" + informationSchemaName + "') columns ");
        } else {
        	strSql.append("FROM INFORMATION_SCHEMA.COLUMNS ");
        }
        strSql.append("WHERE DATA_TYPE in ('int','decimal','double') AND TABLE_NAME='SM_SearchCollect' AND COLUMN_NAME!='id' ");
        mySqlData = new MySqlData();
        mySqlData.setSql(strSql.toString());
		String intCollect = DataUtils.getMapKeyValue(service.getMap(mySqlData), "columnname").toLowerCase();
		
		//处理mysql里int类型在Insert不插入时赋默认值的问题
		strSql = new StringBuilder();
        strSql.append("SELECT CONCAT(',',GROUP_CONCAT(DISTINCT COLUMN_NAME),',') columnname ");
        if(StringUtils.isNotBlank(informationSchemaName)) {
            strSql.append("FROM (select * from INFORMATION_SCHEMA.COLUMNS where table_schema='" + informationSchemaName + "') columns ");
        } else {
        	strSql.append("FROM INFORMATION_SCHEMA.COLUMNS ");
        }
        strSql.append("WHERE DATA_TYPE in ('int','decimal','double') AND TABLE_NAME='SM_SearchItem' AND COLUMN_NAME!='id' ");
        mySqlData = new MySqlData();
        mySqlData.setSql(strSql.toString());
		String intItem = DataUtils.getMapKeyValue(service.getMap(mySqlData), "columnname").toLowerCase();

		for (Map<String, Object> mapCollect : listCollect) {
            String searchpagename = DataUtils.getMapKeyValue(mapCollect, "searchpagename");
            
            //============================
            //生成指标集
            bw.write("DELETE FROM SM_SearchCollect WHERE SearchPageName='" + searchpagename + "'; ");
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
            	for(String intField : intCollect.split(",")) {
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
            bw.write("INSERT INTO SM_SearchCollect(" + strField.toString() + ") ");
            bw.write("VALUES (" + strValue.toString() + "); ");
            bw.write("\r\n");

            mySqlData = new MySqlData();
            mySqlData.setTableName("SM_SearchItem");
            mySqlData.setFieldWhere("SearchPageName", searchpagename, "=");
            mySqlData.setSort("OrderId");
            List<HashMap<String, Object>> listItem = service.getDataList(mySqlData);
            
            bw.write("DELETE FROM SM_SearchItem WHERE SearchPageName='" + searchpagename + "'; ");
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
                	for(String intField : intItem.split(",")) {
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
                bw.write("INSERT INTO SM_SearchItem(" + strField.toString() + ") ");
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
	public void UpdateSearchHtml(HttpServletRequest request, String searchPageName) throws Exception {
//		String SearchHtml = service.getSearchHtml(searchPageName, "SearchTableAlias", request);
		// 先删除redis里的数据，然后才会重新生成。
		RedisUtils.delete("search:" + searchPageName);
		// 得到最新的配置数据
		List<HashMap<String, Object>> listSearch = service.getSearchJson(searchPageName);
		String jsonSearch = JSON.toJSONString(listSearch);
		// 更新数据库里的配置数据
		/*MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_SearchCollect");
		mySqlData.setFieldValue("SearchHtml", jsonSearch);
		mySqlData.setFieldWhere("SearchPageName", searchPageName, "=");
		service.update(mySqlData);*/
		// 更新redis的值
		RedisUtils.set("search:" + searchPageName, jsonSearch);
	}
}