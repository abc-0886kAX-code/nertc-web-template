package com.ytxd.service.SystemManage;

import com.ytxd.common.DataUtils;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import com.ytxd.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("SM_BuiltItem_ServiceImpl")
public class SM_BuiltItem_ServiceImpl extends BaseServiceImpl implements SM_BuiltItem_Service {
	@Resource
	private CommonService service;
	@Autowired
	private SM_CodeItem_Service serviceCodeItem;

	@Override
	public Integer InitSearchById(String searchpagename, String description, String tablename, String idlist) throws Exception {
		searchpagename = searchpagename.replace("'", "").replace(" ", "");
		description = description.replace("'", "").replace(" ", "");
		tablename = tablename.replace("'", "").replace(" ", "");
		idlist = idlist.replace("'", "").replace(" ", "");
        service.delete("SM_SearchCollect", searchpagename, "SearchPageName");
        
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("SM_SearchCollect");
        mySqlData.setFieldValue("SearchPageName", searchpagename);
        mySqlData.setFieldValue("Description", description);
        service.insert(mySqlData);
        
        service.delete("SM_SearchItem", searchpagename, "SearchPageName");
        
        mySqlData = new MySqlData();
        mySqlData.setSql("insert into SM_SearchItem(SearchPageName, FieldName, Description, FieldType, CodeId, OrderId, SkinID, Width, SuffixHtml, SearchSql, Visible, ViewName, SearchField, Operate) ");
        mySqlData.setSql("select ");
        mySqlData.setSqlValue(searchpagename);
        mySqlData.setSql(" SearchPageName,FieldName FieldName,CONCAT(Description,'：') Description,");
        mySqlData.setSql("case when FieldType in ('checkboxlist','radiobuttonlist') then 'select' ");
        mySqlData.setSql("when FieldType in ('datetime','dateandtime') then 'datetime' ");
        mySqlData.setSql("when FieldType in ('int','money','float','datetime6','select','code','SingleUser','MultipleUser') then FieldType ");
        mySqlData.setSql("else 'varchar' end FieldType,");
        mySqlData.setSql("CodeId CodeId,row_number() over(order by OrderId)*5 OrderId,");
        mySqlData.setSql("case when FieldType in ('checkboxlist','radiobuttonlist') then 'select' ");
        mySqlData.setSql("when FieldType in ('datetime','dateandtime') then 'datetime' ");
        mySqlData.setSql("when FieldType in ('int','money','float','datetime6','select','code','SingleUser','MultipleUser') then FieldType ");
        mySqlData.setSql("else 'varchar' end SkinID,");
        mySqlData.setSql("case when FieldType in ('datetime','datetime6','dateandtime','int','money','float') then '100px' else '150px' end Width,'&nbsp;' SuffixHtml,");
        mySqlData.setSql("case when FieldType in ('select','code','checkboxlist','radiobuttonlist','SingleUser','MultipleUser') then CONCAT(' and ',TableName,'.',FieldName,'=''+',FieldName,'+'' ') ");
        mySqlData.setSql("when FieldType in ('datetime','datetime6','dateandtime','int','money','float') then CONCAT(' and ',TableName,'.',FieldName,'>=''+',FieldName,'+'' ') ");
        mySqlData.setSql("else CONCAT(' and ',TableName,'.',FieldName,' like ''%+',FieldName,'+%'' ') end SearchSql,'01' Visible,'add' ViewName, ");
        mySqlData.setSql("CONCAT(TableName,'.',FieldName) SearchField,case when FieldType in ('select','code','checkboxlist','radiobuttonlist','SingleUser','MultipleUser') then 'in' ");
        mySqlData.setSql("when FieldType in ('datetime','datetime6','dateandtime','int','money','float') then '>=' else 'like' end Operate ");
        mySqlData.setSql("from SM_BuiltItem ");
        mySqlData.setFieldWhere("TableName", tablename, "=");
        mySqlData.setFieldWhere("Id", idlist, "in");
        mySqlData.setSort("OrderId");
        service.update(mySqlData);
        
        //后期处理
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_SearchItem set FieldType='varchar',SkinID='varchar',SearchSql=replace(replace(replace(SearchSql,CONCAT(");
        mySqlData.setSqlValue(tablename);
        mySqlData.setSql(",'.',FieldName),CONCAT(FieldName,'.TrueNameJoin')),'=''',' like ''%'),'+''','+%'''),SearchField=CONCAT(FieldName,'.TrueNameJoin'),Operate='like' ");
        //mySqlData.setSql("update SM_SearchItem set FieldType='varchar',SkinID='varchar',SearchSql=replace(replace(replace(SearchSql,CONCAT('" + tablename + ".',FieldName),CONCAT(FieldName,'.TrueNameJoin')),'=''',' like ''%'),'+''','+%'''),SearchField=CONCAT(FieldName,'.TrueNameJoin'),Operate='like' ");
        //mySqlData.setSql("where SearchPageName='" + searchpagename + "' and FieldType in ('select','code') and CodeId='UP' ");
        mySqlData.setFieldWhere("SearchPageName", searchpagename, "=");
        mySqlData.setFieldWhere("FieldType", "select,code", "in");
        mySqlData.setFieldWhere("CodeId", "UP", "=");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_SearchItem set FieldType='code',SkinID='code',SearchSql=replace(replace(SearchSql,'=',' like '),'+''','+%'''),Operate='likeright' ");
        //mySqlData.setSql("where SearchPageName='" + searchpagename + "' and FieldType in ('select','code') and CodeId='UM'"); 
        mySqlData.setFieldWhere("SearchPageName", searchpagename, "=");
        mySqlData.setFieldWhere("FieldType", "select,code", "in");
        mySqlData.setFieldWhere("CodeId", "UM", "=");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("insert into SM_SearchItem(SearchPageName, FieldName, Description, FieldType, CodeId, OrderId, SkinID, Width, SuffixHtml, SearchSql, Visible, SearchField, Operate) ");
        mySqlData.setSql("select SearchPageName, CONCAT('End',FieldName) FieldName, '-' Description, FieldType, CodeId, OrderId+1 OrderId, SkinID, Width, SuffixHtml, ");
        mySqlData.setSql("replace(replace(SearchSql,CONCAT('+',FieldName,'+'),CONCAT('+End',FieldName,'+')),'>','<'), Visible,SearchField, '<=' Operate ");
        mySqlData.setSql("from SM_SearchItem ");
        mySqlData.setFieldWhere("SearchPageName", searchpagename, "=");
        mySqlData.setFieldWhere("FieldType", "datetime,datetime6,dateandtime,int,money,float", "in");
        mySqlData.setSort("OrderId");
        //mySqlData.setSql("where SearchPageName='" + searchpagename + "' and FieldType in ('datetime','datetime6','dateandtime','int','money','float') ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_SearchItem set SuffixHtml=null,SearchSql=replace(SearchSql,CONCAT('+',FieldName,'+'),CONCAT('+Start',FieldName,'+')),FieldName=CONCAT('Start',FieldName), Operate='>=' ");
        //mySqlData.setSql("where SearchPageName='" + searchpagename + "' and FieldType in ('datetime','datetime6','dateandtime','int','money','float') and Description!='-' ");
        mySqlData.setFieldWhere("SearchPageName", searchpagename, "=");
        mySqlData.setFieldWhere("FieldType", "datetime,datetime6,dateandtime,int,money,float", "in");
        mySqlData.setFieldWhere("Description", "-", "!=");
        service.update(mySqlData);
        
        return 1;
	}
	
	@Override
	public Integer InitListById(String listname, String description, String tablename, String idlist, String widthtype) throws Exception {
		listname = listname.replace("'", "").replace(" ", "");
		description = description.replace("'", "").replace(" ", "");
		tablename = tablename.replace("'", "").replace(" ", "");
		idlist = idlist.replace("'", "").replace(" ", "");
		
		String W15 = "15%";
		String W12 = "12%";
		String W10 = "10%";
		String W8 = "8%";
		String W7 = "7%";
		String W6 = "6%";
		String W5 = "5%";
		String W4 = "4%";
		String W3 = "3%";
        if ("px".equals(widthtype.toLowerCase()))
        {
        	W10 = "330px";
        	W12 = "260%";
            W10 = "220px";
            W8 = "180px";
            W7 = "150px";
            W6 = "130px";
            W5 = "110px";
            W4 = "90px";
            W3 = "65px";
        }
        
        service.delete("SM_ListCollect", listname, "ListName");
        
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("SM_ListCollect");
        mySqlData.setFieldValue("ListName", listname);
        mySqlData.setFieldValue("Description", description);
    	service.insert(mySqlData);
        
    	service.delete("SM_ListItem", listname, "ListName");
        
        mySqlData = new MySqlData();
        mySqlData.setSql("insert into SM_ListItem(ListName, FieldType, HeaderText, DataField, DataFormatString, SortExpression, Visible, InsertIndex, Width, HorizontalAlign, IfTotal) ");
        //mySqlData.setSql("select '" + listname + "' ListName,'01' FieldType,Description HeaderText,");
        mySqlData.setSql("select ");
        mySqlData.setSqlValue(listname);
        mySqlData.setSql(" ListName,'01' FieldType,Description HeaderText,");
        mySqlData.setSql("case when FieldType in ('select','code','checkboxlist','radiobuttonlist','SingleUser','MultipleUser') then CONCAT(FieldName,'Name') else FieldName end DataField,");
        mySqlData.setSql("case when FieldType='datetime' then '{0:yyyy-MM-dd}' when FieldType='datetime6' then '{0:yyyy-MM}' when FieldType='dateandtime' then '{0:yyyy-MM-dd HH:mm:ss}' when FieldType in ('float','money') then '{0:N2}' else NULL end DataFormatString,");
        mySqlData.setSql("case when FieldType in ('select','code','radiobuttonlist') and CodeId='UM' then CONCAT(FieldName,'.OrderIdJoin') else CONCAT(TableName,'.',FieldName) end SortExpression, ");
        mySqlData.setSql("'01' Visible, row_number() over(order by OrderId)*5 InsertIndex, ");
        mySqlData.setSql("case when (FieldLen>=500 or (TextSpan>=24 and FieldName not in ('SystemType'))) and fieldname='name' then '" + W15 + "' when FieldLen>=500 or (TextSpan>=24 and FieldName not in ('SystemType')) then '" + W12 + "' when FieldType='text' then '" + W15 + "' when FieldType in ('int','float','money','datetime6') and LENGTH(Description)<=12 then '" + W4 + "' when FieldType in ('code','select') and CodeId='UM' then '" + W8 + "' when FieldName like '%AllUser%' then '" + W10 + "' when FieldType='dateandtime' then '" + W8 + "' when LENGTH(Description)>12 then '" + W5 + "' else '" + W4 + "' end Width,");
        mySqlData.setSql("case when FieldLen>=500 or (TextSpan>=24 and FieldName not in ('SystemType')) then '1' when FieldType='text' then '1' when FieldType in ('code','select') and CodeId='UM' then '1' when FieldName like '%AllUser%' then '1' else '2' end HorizontalAlign,");
//        mySqlData.setSql("case when FieldType in ('datetime','datetime6','dateandtime','int','select','RadioButtonList') then '2' when FieldType in ('float','money') then '3' when SkinId in ('code','SingleUser') and CodeId='UP' then '2' else '1' end HorizontalAlign,");
        mySqlData.setSql("case when FieldType in ('int','float','money') then '01' else '00' end IfTotal ");
        mySqlData.setSql("from SM_BuiltItem ");
        //mySqlData.setSql("where TableName='" + tablename + "' and Id in ('" + idlist.replace(",", "','") + "') ");
        mySqlData.setFieldWhere("TableName", tablename, "=");
        mySqlData.setFieldWhere("Id", idlist, "in");
        mySqlData.setSort("OrderId");
        service.update(mySqlData);
        
        mySqlData = new MySqlData();
        mySqlData.setSql("select ViewURL,ViewURLField from SM_BuiltCollect ");
        mySqlData.setFieldWhere("TableName", tablename, "=");
        Map<String, Object> mapBuiltItem = service.getMap(mySqlData);
        String ViewURL = DataUtils.getMapKeyValue(mapBuiltItem, "ViewURL");
        if (StringUtils.isBlank(ViewURL)) {
            ViewURL = tablename + "-write-view?id={id}";
        }
        String ViewURLField = DataUtils.getMapKeyValue(mapBuiltItem, "ViewURLField");
        if (StringUtils.isBlank(ViewURL)) {
        	ViewURLField = "id";
        }
        
        //后期处理
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_ListItem set FieldType='02',DataNavigateUrlFields='" + ViewURLField + "', FormatLink='" + ViewURL + "', Target='_blank' ");
        //mySqlData.setSql("where ListName='" + listname + "' and DataField='Name' ");
        mySqlData.setFieldWhere("ListName", listname, "=");
        mySqlData.setFieldWhere("DataField", "name", "=");
		if(service.update(mySqlData).getTotal() < 1) {
        	mySqlData = new MySqlData();
            mySqlData.setSql("update SM_ListItem set FieldType='02',DataNavigateUrlFields='" + ViewURLField + "', FormatLink='" + ViewURL + "', Target='_blank' ");
            mySqlData.setFieldWhere("ListName", listname, "=");
            mySqlData.setFieldWhere("InsertIndex", "5", "=");
            service.update(mySqlData);
        }
        try
        {
        	mySqlData = new MySqlData();
        	mySqlData.setTableName("SM_BuiltItem");
            mySqlData.setFieldWhere("TableName", tablename, "=");
            mySqlData.setFieldWhere("Id", idlist, "in");
            mySqlData.setFieldWhere("*", "FieldType in ('select','code','RadioButtonList','CheckBoxList','MultiCode') and CodeId is not null and CodeId not in ('UP','UM','FP')", "sql");
            List<HashMap<String, Object>> listCode = service.getDataList(mySqlData);
            for(HashMap<String, Object> map : listCode) {
            	String codeid = DataUtils.getMapKeyValue(map, "codeid");
            	String wherestring = DataUtils.getMapKeyValue(map, "wherestring");
            	String fieldname = DataUtils.getMapKeyValue(map, "fieldname");
            	Integer iLen = 0;
            	if(StringUtils.isNotBlank(wherestring)) {
            		mySqlData = new MySqlData();
                	mySqlData.setTableName("SM_CodeItem");
                	mySqlData.setSelectField("*", "max(length(Description)) maxlength");
                    mySqlData.setFieldWhere("codeid", codeid, "=");
                    mySqlData.setFieldWhere("flag", "1", "=");
                    HashMap<String, Object> mapLength = service.getMap(mySqlData);
                    iLen = Integer.parseInt(DataUtils.getMapKeyValue(mapLength, "maxlength")) / 3;
            	} else {
            		List<HashMap<String, Object>> listTemp = serviceCodeItem.GetCodeList(codeid, wherestring);
            		for(HashMap<String, Object> mapTemp : listTemp) {
            			Integer iLenTemp = getLength(DataUtils.getMapKeyValue(mapTemp, "description"));
            			if(iLen < iLenTemp / 2) {
                    		iLen = iLenTemp / 2;
                    	}
            		}
            	}
            	if(iLen < getLength(DataUtils.getMapKeyValue(map, "description")) / 2) {
            		iLen = getLength(DataUtils.getMapKeyValue(map, "description")) / 2;
            	}
                if(iLen > 0) {
                	mySqlData = new MySqlData();
                	mySqlData.setTableName("SM_ListItem");
                	mySqlData.setFieldWhere("ListName", listname, "=");
                    mySqlData.setFieldWhere("DataField", fieldname+"name", "=");
                    if(iLen <= 3) {
                    	mySqlData.setFieldValue("HorizontalAlign", "1");
                    	mySqlData.setFieldValue("Width", W3);
                    } else if(iLen >= 6 && iLen < 8 ) {
                    	mySqlData.setFieldValue("HorizontalAlign", "1");
                    	mySqlData.setFieldValue("Width", W5);
                    } else if(iLen >= 8 && iLen < 10 ) {
                    	mySqlData.setFieldValue("HorizontalAlign", "1");
                    	mySqlData.setFieldValue("Width", W6);
                    } else if(iLen >= 10 && iLen < 13 ) {
                    	mySqlData.setFieldValue("HorizontalAlign", "1");
                    	mySqlData.setFieldValue("Width", W7);
                    } else if(iLen >= 13) {
                    	mySqlData.setFieldValue("HorizontalAlign", "2");
                    	mySqlData.setFieldValue("Width", W8);
                    }
                    service.update(mySqlData);
                }            	
            }
        } catch (Exception e) {
			// TODO: handle exception
		}
        /*mySqlData = new MySqlData();
        mySqlData.setSql("update SM_ListItem set FieldType='02', DataFormatString='下载', SortExpression=NULL, Width='2%', HorizontalAlign='2', DataNavigateUrlFields=DataField, DataNavigateUrlFormatString='~/codetree/DownFile.aspx?FileUrl={0}', Target='_blank' ");
        mySqlData.setSql("where ListName=");
        mySqlData.setSqlValue(listname);
        mySqlData.setSql(" and DataField in (select FieldName from SM_BuiltItem where TableName=");
        mySqlData.setSqlValue(tablename);
        mySqlData.setSql(" and FieldType='file') ");
        service.update(mySqlData);
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_ListItem set FieldType='02', DataFormatString='下载', SortExpression=NULL, Width='2%', HorizontalAlign='2', DataNavigateUrlFields='Id', DataNavigateUrlFormatString=concat('~/codetree/DownFileList.aspx?TableName=" + tablename + "&FileFieldName=',DataField,'&IdFieldName=Id&Id={0}'), Target='_blank' ");
        mySqlData.setSql("where ListName=");
        mySqlData.setSqlValue(listname);
        mySqlData.setSql(" and DataField in (select FieldName from SM_BuiltItem where TableName=");
        mySqlData.setSqlValue(tablename);
        mySqlData.setSql(" and FieldType='multifile') ");
        service.update(mySqlData);*/
        
        return 1;
	}
	private int getLength(String s) {
	    int length = 0;
	    for (int i = 0; i < s.length(); i++) {
	        int ascii = Character.codePointAt(s, i);
	        if (ascii >= 0 && ascii <= 255) {
	            length++;
	        } else {
	            length += 2;
	        }
	    }
	    return length;
	}
	
	@Override
	public Integer InitExcelById(String exportname, String description, String tablename, String idlist) throws Exception {
		exportname = exportname.replace("'", "").replace(" ", "");
		description = description.replace("'", "").replace(" ", "");
		tablename = tablename.replace("'", "").replace(" ", "");
		idlist = idlist.replace("'", "").replace(" ", "");
		
		service.delete("SM_ExcelCollect", exportname, "ExportName");
        
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("SM_ExcelCollect");
        mySqlData.setFieldValue("ExportName", exportname);
        mySqlData.setFieldValue("Description", description);
        mySqlData.setFieldValue("SheetName", description);
        mySqlData.setFieldValue("RowNumber", "2");
        mySqlData.setFieldValue("ColumnCount", "50");
        mySqlData.setFieldValue("Auto", "01");
        service.insert(mySqlData);
        
    	service.delete("SM_ExcelItem", exportname, "ExportName");
        
        mySqlData = new MySqlData();
        mySqlData.setSql("insert into SM_ExcelItem(ExportName, SourceColumnName, ExcelColumnName, RowNumber, ColumnNumber, Visible, ColumnWidth, HorizontalAlignment, NumberFormatLocal, IfTotal) ");
        mySqlData.setSql("select ");
        mySqlData.setSqlValue(exportname);
        mySqlData.setSql(" ExportName,case when FieldType in ('select','code','checkboxlist','radiobuttonlist','SingleUser','MultipleUser') then CONCAT(FieldName,'Name') else FieldName end SourceColumnName,");
        mySqlData.setSql("Description SourceColumnName,NULL RowNumber,row_number() over(order by OrderId)*5 ColumnNumber,'01' Visible,");
        mySqlData.setSql("case when FieldLen>=500 or (TextSpan>=24 and FieldName not in ('SystemType')) then '40' when FieldType='text' then '40' when FieldType in ('int','float','money','datetime6') and LENGTH(Description)<=12 then '12' when FieldType in ('dateandtime') then '16' when FieldType in ('code','select') and CodeId='UM' then '25' when FieldName like '%AllUser%' then '30' when LENGTH(Description)>12 then '20' else '15' end ColumnWidth,");
        mySqlData.setSql("case when FieldLen>=500 or (TextSpan>=24 and FieldName not in ('SystemType')) then '-4131' when FieldType='text' then '-4131' when FieldType in ('code','select') and CodeId='UM' then '-4131' when FieldName like '%AllUser%' then '-4131' else '-4108' end HorizontalAlignment,");
        
//        mySqlData.setSql("case when FieldLen>=500 then '40' when FieldType='text' then '40' when FieldType in ('code','select') and CodeId='UM' then '25' when FieldType in ('datetime') then '12' when FieldType in ('dateandtime') then '15' else '20' end ColumnWidth,");
//        mySqlData.setSql("case when FieldLen>=500 then '-4131' when FieldType='text' then '-4131' else '-4108' end HorizontalAlignment,");
//        mySqlData.setSql("case when FieldType in ('datetime','datetime6','dateandtime','int') then '-4108' when FieldType in ('float','money') then '-4152' when FieldType in ('select','code','SingleUser') and CodeId='UP' then '-4108' else '-4131' end HorizontalAlignment,");
        mySqlData.setSql("case when FieldType in ('datetime') then 'yyyy-m-d' when FieldType in ('datetime6') then 'yyyy-m' when FieldType in ('dateandtime') then 'yyyy-m-d h:mm' when FieldType in ('float') then '0.00_ ' when FieldType in ('money') then '#,##0.00' when FieldType in ('int') then '0_ ' else NULL end NumberFormatLocal ");
        mySqlData.setSql(",case when FieldType in ('int','float','money') then '01' else '00' end IfTotal ");
        mySqlData.setSql("from SM_BuiltItem ");
        /*mySqlData.setSql("where TableName='" + tablename + "' and Id in ('" + idlist.replace(",", "','") + "') ");
        mySqlData.setSql("order by OrderId ");*/
        mySqlData.setFieldWhere("TableName", tablename, "=");
        mySqlData.setFieldWhere("Id", idlist, "in");
        mySqlData.setSort("OrderId");
        service.update(mySqlData);
        
        //后期处理
        mySqlData = new MySqlData();
        mySqlData.setSql("update SM_ExcelItem set ColumnWidth='40' ");
        mySqlData.setFieldWhere("ExportName", exportname, "=");
        mySqlData.setFieldWhere("SourceColumnName", "name", "=");
        service.update(mySqlData);
        
        mySqlData = new MySqlData();
        mySqlData.setSql("select FieldName,CodeId from SM_BuiltItem ");
        mySqlData.setFieldWhere("TableName", tablename, "=");
        mySqlData.setFieldWhere("Id", idlist, "in");
        mySqlData.setFieldWhere("FieldType", "select,code,CheckBoxList,RadioButtonList,SingleUser,MultipleUser", "in");
        mySqlData.setFieldWhere("CodeIdNotNull", "CodeId is not null", "sql");
        mySqlData.setFieldWhere("CodeId", "UP,UM", "not in");
        List<HashMap<String, Object>> listBuiltItem = service.getDataList(mySqlData);
        for (Map<String, Object> mapBuiltItem : listBuiltItem) {
        	mySqlData = new MySqlData();
            mySqlData.setSql("select MAX(LENGTH(Description)) deslen from SM_CodeItem ");
            mySqlData.setFieldWhere("CodeId", DataUtils.getMapKeyValue(mapBuiltItem, "codeid"), "=");
            Integer iLen = Integer.parseInt(DataUtils.getMapKeyValue(service.getMap(mySqlData), "deslen"));
            String strLen = "10";
            String HorizontalAlignment = ",HorizontalAlignment='-4108'";
            if (iLen <= 18) {
                strLen = "10";
            }
            else if (iLen > 18 && iLen <= 27) {
                strLen = "15";
            }
            else if (iLen > 27 && iLen <= 36) {
                strLen = "20";
            } 
            else {
                strLen = "25";
                HorizontalAlignment = "";
            }
            mySqlData = new MySqlData();
            mySqlData.setSql("update SM_ExcelItem set ColumnWidth='" + strLen + "'" + HorizontalAlignment + " ");
            mySqlData.setFieldWhere("ExportName", exportname, "=");
            mySqlData.setFieldWhere("SourceColumnName", DataUtils.getMapKeyValue(mapBuiltItem, "fieldname") + "name", "=");
            service.update(mySqlData);
        }
        
        return 1;
	}
	
	@Override
	public Integer InitExcelImportById(String importname, String description, String tablename, String idlist) throws Exception {
		importname = importname.replace("'", "").replace(" ", "");
		description = description.replace("'", "").replace(" ", "");
		tablename = tablename.replace("'", "").replace(" ", "");
		idlist = idlist.replace("'", "").replace(" ", "");
		
		service.delete("SM_ExcelImportCollect", importname, "ImportName");
        
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("SM_ExcelImportCollect");
        mySqlData.setFieldValue("ImportName", importname);
        mySqlData.setFieldValue("Description", description);
        mySqlData.setFieldValue("TableName", tablename);
        service.insert(mySqlData);
        
    	service.delete("SM_ExcelImportItem", importname, "ImportName");
        
        mySqlData = new MySqlData();
        mySqlData.setSql("insert into SM_ExcelImportItem(ImportName, FieldName, ExcelColumnName, Visible, ColumnNumber, NotNull, FieldType, FieldShortestLen, FieldLen, CodeId, CodeName, ParentFieldName) ");
        mySqlData.setSql("select ");
        mySqlData.setSqlValue(importname);
        mySqlData.setSql(" ImportName,FieldName,Description,case when Flag=1 then '01' else '00' end Visible,");
        mySqlData.setSql("row_number() over(order by OrderId)*5 ColumnNumber,case when NotNull=1 then '01' else '00' end NotNull,FieldType,FieldShortestLen, FieldLen, CodeId, CodeName, ParentFieldName ");
        mySqlData.setSql("from SM_BuiltItem ");
        mySqlData.setFieldWhere("TableName", tablename, "=");
        mySqlData.setFieldWhere("Id", idlist, "in");
        mySqlData.setSort("OrderId");
        service.update(mySqlData);
        
        return 1;
	}
}