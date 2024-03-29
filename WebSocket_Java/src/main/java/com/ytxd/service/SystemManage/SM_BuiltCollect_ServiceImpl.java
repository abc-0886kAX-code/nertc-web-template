package com.ytxd.service.SystemManage;

import com.ytxd.common.DataUtils;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import com.ytxd.service.impl.BaseServiceImpl;
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

@Service("SM_BuiltCollect_ServiceImpl")
public class SM_BuiltCollect_ServiceImpl extends BaseServiceImpl implements SM_BuiltCollect_Service {
	@Resource
	private CommonService service;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${file.onlinePreviewPath}")
    private String onlinePreviewPath;
    @Value("${informationSchemaName:''}")
	private String informationSchemaName;

    @Override
	public Integer InitBuiltItem(String tablename) throws Exception {
		tablename = tablename.replace("'", "").replace(" ", "");
		MySqlData mySqlData = new MySqlData();
		StringBuilder strSql = new StringBuilder();
        strSql.append("insert into SM_BuiltItem(TableName, FieldName, ViewName, Description, FieldType, ");
        strSql.append("FieldLen, FieldDec, CodeId, OnlyLeaf, Flag, ReadOnly, NotNull, DefaultValue, ");
        strSql.append("OrderId, IsRow, SkinID, Width, Height, ToolTip, FieldShortestLen, NameSpan, ");
        strSql.append("TextSpan, ShowHelp, WhereValue) ");
        strSql.append("select '" + tablename + "' TableName,columns.column_name FieldName,'add' ViewName,column_comment Description,");
        strSql.append("case when columns.data_type in ('text','longtext','mediumtext') or (columns.data_type='varchar' and columns.CHARACTER_MAXIMUM_LENGTH>=2000) then 'text' ");
        strSql.append("when columns.data_type in ('bit','tinyint','smallint','MEDIUMINT','int','INTEGER','bigint') then 'int' ");
        strSql.append("when columns.data_type in ('smalldatetime','datetime') then 'datetime' ");
        strSql.append("when columns.data_type in ('money','float','decimal','numeric','double','smallmoney') then 'money' ");
        strSql.append("else 'varchar' end FieldType,");
        strSql.append("case when CHARACTER_MAXIMUM_LENGTH>=16777215 then NULL else ifnull(NUMERIC_PRECISION,CHARACTER_MAXIMUM_LENGTH) end FieldLen,");
        strSql.append("case when NUMERIC_SCALE!=0 then NUMERIC_SCALE else NULL end FieldDec,");
        strSql.append("NULL CodeId,'00' OnlyLeaf,'01' Flag,'00' ReadOnly,'00' NotNull,NULL DefaultValue,");
        strSql.append("columns.ORDINAL_POSITION*5 OrderId,'00' IsRow,NULL SkinID,");
        strSql.append("case when columns.data_type in ('text','longtext','mediumtext') or (columns.data_type='varchar' and columns.CHARACTER_MAXIMUM_LENGTH>=500) then '80%' else NULL end Width,");
        strSql.append("case when columns.data_type in ('text','longtext','mediumtext') or (columns.data_type='varchar' and columns.CHARACTER_MAXIMUM_LENGTH>=2000) then '60px' else NULL end Height,");
        strSql.append("NULL ToolTip,NULL FieldShortestLen,1 NameSpan,");
        strSql.append("case when columns.data_type in ('text','longtext','mediumtext') or (columns.data_type='varchar' and columns.CHARACTER_MAXIMUM_LENGTH>=500) then 3 else 1 end TextSpan");
        strSql.append(",NULL ShowHelp,NULL WhereValue ");
        if(StringUtils.isNotBlank(informationSchemaName)) {
        	strSql.append("FROM (select * from information_schema.tables where table_schema='" + informationSchemaName + "') tables ");
            strSql.append("inner join (select * from information_schema.columns where table_schema='" + informationSchemaName + "') columns on columns.table_name=tables.table_name ");
        } else {
        	strSql.append("FROM information_schema.tables tables ");
            strSql.append("inner join information_schema.columns columns on columns.table_name=tables.table_name ");
        }        
        strSql.append("where tables.table_name='" + tablename + "' and columns.EXTRA!='auto_increment' ");
        strSql.append("and columns.column_name not in (select FieldName from SM_BuiltItem where TableName='" + tablename + "' and FieldName is not null) ");
        strSql.append("order by columns.table_name,columns.ORDINAL_POSITION ");
        mySqlData.setSql(strSql.toString());
        service.insert(mySqlData);
        
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set SkinID=FieldType where TableName='" + tablename + "' and SkinID is null ");
        service.update(mySqlData);
        //后期处理
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set NotNull='01' where TableName='" + tablename + "' and FieldName='Name' ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set Flag='00',ReadOnly='01' where TableName='" + tablename + "' and FieldName='Number' ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set FieldType='select',NotNull='01',SkinID='select' where TableName='" + tablename + "' and FieldName in ('Type','Level') ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set FieldType='code',CodeId='UP',NotNull='01',SkinID='SingleUser' where TableName='" + tablename + "' and FieldName ='UserId' ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set FieldType='code',CodeId='UM',NotNull='01',SkinID='code' where TableName='" + tablename + "' and FieldName ='DepartmentId' ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set FieldType='datetime',NotNull='01',SkinID='datetime' where TableName='" + tablename + "' and FieldName ='KeyTime' ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set FieldType='TextCode',CodeId='UP',SkinID='TextMultipleUser' where TableName='" + tablename + "' and FieldName in ('AllUsers') ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set Flag='00',FieldType='TextCode',CodeId='UP',SkinID='TextMultipleUser' where TableName='" + tablename + "' and FieldName in ('InnerUsers') ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set FieldType='file',SkinID='file',Width='300px',Height=NULL,DefaultValue='UpFile/' where TableName='" + tablename + "' and FieldName in ('Attachment') ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set Flag='00',FieldType='file',SkinID='file',Width='300px',Height=NULL,DefaultValue='UpFile/' where TableName='" + tablename + "' and FieldName in ('Attachment1','Attachment2') ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set FieldType='code',CodeId='UP',ReadOnly='01',SkinID='code' where TableName='" + tablename + "' and FieldName='SubmitMan' ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set FieldType='datetime',ReadOnly='01',SkinID='datetime' where TableName='" + tablename + "' and FieldName='SubmitTime' ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set ViewName='Check',FieldType='select',CodeId='FP',NotNull='01',SkinID='select',WhereValue=' and Code in (@01@,@02@,@03@) ' where TableName='" + tablename + "' and FieldName='CheckResult' ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set ViewName='Check',Flag='00' where TableName='" + tablename + "' and FieldName='Score' ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set ViewName='Check' where TableName='" + tablename + "' and FieldName='CheckMsg' ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set ViewName='Check',FieldType='code',CodeId='UP',ReadOnly='01',SkinID='code' where TableName='" + tablename + "' and FieldName='CheckMan' ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set ViewName='Check',FieldType='datetime',ReadOnly='01',SkinID='datetime' where TableName='" + tablename + "' and FieldName='CheckTime' ");
        service.update(mySqlData);
        //预测
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set FieldType='datetime',SkinID='datetime' where TableName='" + tablename + "' and FieldName like '%Time' ");
        service.update(mySqlData);
        
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set NameSpan='36%' where NameSpan='1' ");
        service.update(mySqlData);
        
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set NameSpan='18%' where textspan='3' ");
        service.update(mySqlData);
        
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set textspan='12' where textspan='1' ");
        service.update(mySqlData);
        
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_BuiltItem set textspan='24' where textspan='3' ");
        service.update(mySqlData);
        
        return 1;
	}
	
	@Override
	public String ExportSql(HttpServletRequest request, String idlist) throws Exception {
		// 1. 使用File类打开一个文件；
		String fileName = DataUtils.getDate("yyyyMMdd") + (new Random().nextInt(900000)+100000) + "Table.txt";
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
		mySqlData.setTableName("SM_BuiltCollect");
		mySqlData.setFieldWhere("id", idlist, "in");
		mySqlData.setSort("TableName");
		List<HashMap<String, Object>> listCollect = service.getDataList(mySqlData);
        
		//处理mysql里int类型在Insert不插入时赋默认值的问题
        strSql = new StringBuilder();
        strSql.append("SELECT CONCAT(',',GROUP_CONCAT(DISTINCT COLUMN_NAME),',') columnname ");
        if(StringUtils.isNotBlank(informationSchemaName)) {
            strSql.append("FROM (select * from INFORMATION_SCHEMA.COLUMNS where table_schema='" + informationSchemaName + "') columns ");
        } else {
        	strSql.append("FROM INFORMATION_SCHEMA.COLUMNS ");
        }
        strSql.append("WHERE DATA_TYPE in ('int','decimal','double') AND TABLE_NAME='SM_BuiltCollect' AND COLUMN_NAME!='id' ");
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
        strSql.append("WHERE DATA_TYPE in ('int','decimal','double') AND TABLE_NAME='SM_BuiltItem' AND COLUMN_NAME!='id' ");
        mySqlData = new MySqlData();
        mySqlData.setSql(strSql.toString());
        String intItem = DataUtils.getMapKeyValue(service.getMap(mySqlData), "columnname").toLowerCase();
        
        for (Map<String, Object> mapCollect : listCollect) {
        	String tablename = DataUtils.getMapKeyValue(mapCollect, "tablename");

            //============================
            //生成指标集
            bw.write("DELETE FROM SM_BuiltCollect WHERE TableName='" + tablename + "'; ");
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
            bw.write("INSERT INTO SM_BuiltCollect(" + strField.toString() + ") ");
            bw.write("VALUES (" + strValue.toString() + "); ");
            bw.write("\r\n");

            //得到子表数据
            mySqlData = new MySqlData();
            mySqlData.setTableName("SM_BuiltItem");
            mySqlData.setFieldWhere("TableName", tablename, "=");
            mySqlData.setSort("flag desc,orderid");
            List<HashMap<String, Object>> listItem = service.getDataList(mySqlData);
            //生成子表SQL文件
            bw.write("DELETE FROM SM_BuiltItem WHERE TableName='" + tablename + "'; ");
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
                bw.write("INSERT INTO SM_BuiltItem(" + strField.toString() + ") ");
                bw.write("VALUES (" + strValue.toString() + "); ");
                bw.write("\r\n");
            }
        }
        bw.flush();
		// 4. 关闭输出
		bw.close();
		
		return "UpFile/" + fileName;
	}
}