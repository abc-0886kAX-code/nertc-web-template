package com.ytxd.service.ExcelOperation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ytxd.common.DataUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class ExportTable
{
    // 私有属性


    /// <summary>
    /// 导出名称
    /// </summary>
    private String ExportName;
    /// <summary>
    /// 数据源 要导出的数据
    /// </summary>
    private List<?> dtSource;
    /// <summary>
    /// 导出格式 ExportName, SourceColumnName, ExcelColumnName, RowNumber, ColumnNumber
    /// </summary>
    private List<SM_ExcelItem> dtItem;
    /// <summary>
    /// 导出格式 ExportName, SourceColumnName, ExcelColumnName, RowNumber, ColumnNumber
    /// </summary>
    private List<SM_ExcelItem> dtExportItem;
    /// <summary>
    /// Page
    /// </summary>
    private String ProjectPath;
    /// <summary>
    /// 导出名称
    /// </summary>
    private String outFileName;
    /// <summary>
    /// 导出Sheet页名称

    /// </summary>
    private String sheetName;
    //通过SM_ExcelCollect表赋值的私有变量
    private String Description;
    private String RowNumber;
    private String ColumnCount;
    private String TemplateFile;
    private String Auto;
    private String CopyFormat;

    /// <summary>
    /// 导出名称
    /// </summary>
    public void setName(String ExportName)
    {
    	this.ExportName = ExportName;
    }
    public void setExportName(String ExportName)
    {
        this.ExportName = ExportName;
    }

    /// <summary>
    /// 数据源 要导出的数据
    /// </summary>
    public void setSource(List<?> ListSource)
    {
        this.dtSource = ListSource;
    }

    /// <summary>
    /// 导出配制
    /// </summary>
    public String setExcelCollect(List<?> ListExcelCollect) {
        if(ListExcelCollect.size()==0){return "导出配制总表记录不存在。";}

        if(StringUtils.isEmpty(ExportName)){
            this.ExportName=getValue(ListExcelCollect.get(0), "exportname").toString();
        }
        if(StringUtils.isEmpty(sheetName)){
            this.sheetName=getValue(ListExcelCollect.get(0), "sheetname").toString();
        }
        this.Description=getValue(ListExcelCollect.get(0), "description").toString();
        this.RowNumber=getValue(ListExcelCollect.get(0), "rownumber").toString();
        this.ColumnCount=getValue(ListExcelCollect.get(0), "columncount").toString();
        this.TemplateFile=getValue(ListExcelCollect.get(0), "templatefile").toString();
        this.Auto=getValue(ListExcelCollect.get(0), "auto").toString();
        this.CopyFormat=getValue(ListExcelCollect.get(0), "copyformat").toString();

        return"";
    }
    public String setExcelCollectByJsonObj(JSONObject json) {
        if(json == null || json.size() == 0) {
            return "导出配制总表记录不存在。";
        }

        if(StringUtils.isEmpty(ExportName)){
            this.ExportName = json.getString("exportname");
        }
        if(StringUtils.isEmpty(sheetName)){
            this.sheetName = json.getString("sheetname");
        }
        this.Description = json.getString("description");
        this.RowNumber = json.getString("rownumber");
        this.ColumnCount = json.getString("columncount");
        this.TemplateFile = json.getString("templatefile");
        this.Auto = json.getString("auto");
        this.CopyFormat = json.getString("copyformat");

        return"";
    }
    /// <summary>
    /// 导出配制
    /// </summary>
    public String setExcelCollect(Map<String, Object> objMap) {
        if(objMap.size()==0){return "导出配制总表记录不存在。";}

        if(StringUtils.isEmpty(ExportName)){
            this.ExportName=objMap.get("exportname").toString();
        }
        if(StringUtils.isEmpty(sheetName)){
            this.sheetName=objMap.get("sheetname").toString();
        }
        this.Description=objMap.get("description").toString();
        this.RowNumber=objMap.get("rownumber").toString();
        this.ColumnCount=objMap.get("columncount").toString();
        this.TemplateFile=objMap.get("templatefile").toString();
        this.Auto=objMap.get("auto").toString();
        this.CopyFormat=objMap.get("copyformat").toString();

        return"";
    }
    public String setExcelItemByJsonArray(JSONArray array) {
        if(array == null || array.size() == 0){
            return "导出配制从表记录不存在。";
        }

        this.dtItem = new ArrayList();
        for(int i=0;i<array.size();i++){
            SM_ExcelItem obj = new SM_ExcelItem();
            JSONObject jsonObject = (JSONObject) array.get(i);
            obj.setexportname(jsonObject.getString("exportname"));
            obj.setsourcecolumnname(jsonObject.getString("sourcecolumnname"));
            obj.setexcelcolumnname(jsonObject.getString("excelcolumnname"));
            obj.setrownumber(jsonObject.getString("rownumber"));
            obj.setcolumnnumber(jsonObject.getString("columnnumber"));
            obj.setcolumnwidth(jsonObject.getString("columnwidth"));
            obj.sethorizontalalignment(jsonObject.getString("horizontalalignment"));
            obj.sethorizontalalignmentname(jsonObject.getString("horizontalalignmentname"));
            obj.setnumberformatlocal(jsonObject.getString("numberformatlocal"));
            obj.setiftotal(jsonObject.getString("iftotal"));
            this.dtItem.add(obj);
        }

        return "";
    }
    /// <summary>
    /// 导出配制
    /// </summary>
    public String setExcelItem(List<?> ListExcelItem) {
        if(ListExcelItem.size() == 0){return "导出配制从表记录不存在。";}

        this.dtItem = new ArrayList();
        for(int i=0;i<ListExcelItem.size();i++){
            SM_ExcelItem obj = new SM_ExcelItem();
            obj.setexportname(getValue(ListExcelItem.get(i), "exportname").toString());
            obj.setsourcecolumnname(getValue(ListExcelItem.get(i), "sourcecolumnname").toString());
            obj.setexcelcolumnname(getValue(ListExcelItem.get(i), "excelcolumnname").toString());
            obj.setrownumber(getValue(ListExcelItem.get(i), "rownumber").toString());
            obj.setcolumnnumber(getValue(ListExcelItem.get(i), "columnnumber").toString());
            obj.setcolumnwidth(getValue(ListExcelItem.get(i), "columnwidth").toString());
            obj.sethorizontalalignment(getValue(ListExcelItem.get(i), "horizontalalignment").toString());
            obj.sethorizontalalignmentname(getValue(ListExcelItem.get(i), "horizontalalignmentname").toString());
            obj.setnumberformatlocal(getValue(ListExcelItem.get(i), "numberformatlocal").toString());
            obj.setiftotal(getValue(ListExcelItem.get(i), "iftotal").toString());
            this.dtItem.add(obj);
        }

        return "";
    }
    /// <summary>
    /// 导出配制
    /// </summary>
    public void setExportItem(List<SM_ExcelItem> ListExportItem)
    {
        this.dtExportItem = ListExportItem;
    }

    public void setProjectPath(String ProjectPath)
    {
        if (StringUtils.isNotEmpty(ProjectPath) && ProjectPath.length() > 0
                && !ProjectPath.replace("\\", "/").substring(ProjectPath.length() - 1, ProjectPath.length()).equals("/")) {
            ProjectPath = ProjectPath + "/";
        }
        this.ProjectPath = ProjectPath;
    }
    /// <summary>
    /// 导出Sheet页名称

    /// </summary>
    public void setSheetName(String SheetName)
    {
        this.sheetName = SheetName;
    }

    // 构造函数

    public ExportTable()
    {
    }

    // 导出数据到Excel中

    // 导出数据到Excel中 模板文件路径和导出文件路径在SM_ExcelCollect表中设置
    /// <summary>
    /// 导出数据到Excel中 模板文件路径和导出文件路径在SM_ExcelCollect表中设置
    /// </summary>
    /// <returns>错误提示</returns>
    public String Export()
    {
        String strError = InitExport();
        if (StringUtils.isNotEmpty(strError)){
            return strError;
        }

        //模板
        String inputFilePath = "";
        if (StringUtils.isNotEmpty(TemplateFile))
        {
            inputFilePath = ProjectPath + TemplateFile;
        }
        //导出文件
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        outFileName = df.format(new Date()) + (new Random().nextInt(900000)+100000) + Description + ".xls";
        String outFilePath = ProjectPath+"ExcelOperation/makeExcel/" + outFileName;

        //删除存在的导出文件

        File outFile = new File(outFilePath);
        File fileParent = outFile.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        if (outFile.exists()) {
            outFile.delete();
        }

        if(StringUtils.isNotEmpty(inputFilePath) && Auto.equals("00"))
        {
            //检查模板文件是否存在

            File inputFile = new File(inputFilePath);
            // 检查模板文件是否存在

            if (!inputFile.exists()) {
                return "模板文件不存在。";
            } else {
                // 复制模板文件成指定文件

                try {
                    FileUtils.copyFile(inputFile, outFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        try
        {
            if (this.Auto.equals("01")) {
                return this.ExportFileAuto(outFilePath);
            }
            else {
                return this.ExportFile(outFilePath);
            }
        }
        catch (Exception ex)
        {
            return ex.getMessage().toString().replace("\r\n", "").replace("'", "‘");
        }
    }


    // 导出数据到Excel中 模板文件路径在SM_ExcelCollect表中设置
    /// <summary>
    /// 导出数据到Excel中 模板文件路径在SM_ExcelCollect表中设置
    /// </summary>
    /// <param name="outFilePath">导出文件路径</param>
    /// <returns>错误提示</returns>
    public String Export(String outFilePath)
    {
        String strError = InitExport();
        if (StringUtils.isNotEmpty(strError))
        {
            return strError;
        }

        String inputFilePath = "";
        if (StringUtils.isNotEmpty(this.TemplateFile))
        {
            inputFilePath = ProjectPath + this.TemplateFile;
        }

        //删除存在的导出文件

        File outFile = new File(outFilePath);
        File fileParent = outFile.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        if (outFile.exists()) {
            outFile.delete();
        }

        if(StringUtils.isNotEmpty(inputFilePath) && this.Auto.equals("00"))
        {
            //检查模板文件是否存在

            File inputFile = new File(inputFilePath);
            // 检查模板文件是否存在

            if (inputFile.exists()) {
                // 复制模板文件成指定文件

                try {
                    FileUtils.copyFile(inputFile, outFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
                return "模板文件不存在。";
            }
        }

        try
        {
            if (this.Auto.equals("01"))
            {
                return this.ExportFileAuto(outFilePath);
            }
            else
            {
                return this.ExportFile(outFilePath);
            }
        }
        catch (Exception ex)
        {
            return ex.getMessage().toString().replace("\r\n", "").replace("'", "‘");
        }
    }

    // 导出数据到Excel中

    /// <summary>
    /// 导出数据到Excel中

    /// </summary>
    /// <param name="inputFilePath">模板文件路径</param>
    /// <param name="outFilePath">导出文件路径</param>
    /// <returns>错误提示</returns>
    public String Export(String inputFilePath, String outFilePath)
    {
        String strError = InitExport();
        if (StringUtils.isNotEmpty(strError)) {
            return strError;
        }

        //删除存在的导出文件

        File outFile = new File(outFilePath);
        File fileParent = outFile.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        if (outFile.exists()) {
            outFile.delete();
        }

        if(StringUtils.isEmpty(inputFilePath)){
            return "模板文件不存在。";
        }
        else{
            //检查模板文件是否存在

            File inputFile = new File(inputFilePath);
            // 检查模板文件是否存在

            if (inputFile.exists()) {
                // 复制模板文件成指定文件

                try {
                    FileUtils.copyFile(inputFile, outFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                return "模板文件不存在。";
            }
        }

        try
        {
            return this.ExportFile(outFilePath);
        }
        catch (Exception ex)
        {
            return ex.getMessage().toString().replace("\r\n", "").replace("'", "‘");
        }
    }


    // 导出数据到Excel中 模板文件路径和导出文件路径在SM_ExcelCollect表中设置
    /// <summary>
    /// 导出数据到Excel中 模板文件路径和导出文件路径在SM_ExcelCollect表中设置
    /// </summary>
    /// <param name="dt">数据源</param>
    /// <param name="ExportName">ExportName</param>
    /// <returns>错误信息</returns>
    public String Export(String ProjectPath, List list, String ExportName)
    {
        this.ProjectPath = ProjectPath;
        this.dtSource = list;
        this.ExportName = ExportName;

        return Export();
    }


    // 导出数据到Excel中 模板文件路径在SM_ExcelCollect表中设置
    /// <summary>
    /// 导出数据到Excel中 模板文件路径在SM_ExcelCollect表中设置
    /// </summary>
    /// <param name="dt">数据源</param>
    /// <param name="ExportName">ExportName</param>
    /// <param name="outFilePath">导出文件路径</param>
    /// <returns>错误信息</returns>
    public String Export(String ProjectPath, List list, String ExportName, String outFilePath)
    {
        this.ProjectPath = ProjectPath;
        this.dtSource = list;
        this.ExportName = ExportName;

        return Export(outFilePath);
    }


    // 导出数据到Excel中

    /// <summary>
    /// 导出数据到Excel中

    /// </summary>
    /// <param name="dt">数据源</param>
    /// <param name="ExportName">ExportName</param>
    /// <param name="inputFilePath">模板文件路径</param>
    /// <param name="outFilePath">导出文件路径</param>
    /// <returns>错误提示</returns>
    public String Export(List list, String ExportName, String inputFilePath, String outFilePath)
    {
        this.dtSource = list;
        this.ExportName = ExportName;

        return Export(inputFilePath, outFilePath);
    }




    // 私有方法

    // 导出初始化


    /// <summary>
    /// 导出初始化

    /// </summary>
    private String InitExport()
    {
        if (ExportName==null || ExportName.isEmpty()) {
            return "没有设置导出名称。";
        }

        if(Description==null || Description.isEmpty()){
            return "导出配制总表记录不存在。";
        }

        if (dtItem==null || dtItem.size() == 0) {
            return "导出配制从表记录为空。";
        }

        return "";
    }


    // 复制行

    /// <summary>
    /// HSSFRow Copy Command
    ///
    /// Description:  Inserts a existing row into a new row, will automatically push down
    ///               any existing rows.  Copy is done cell by cell and supports, and the
    ///               command tries to copy all properties available (style, merged cells, values, etc...)
    /// </summary>
    /// <param name="workbook">Workbook containing the worksheet that will be changed</param>
    /// <param name="worksheet">WorkSheet containing rows to be copied</param>
    /// <param name="sourceRowNum">Source Row Number</param>
    /// <param name="destinationRowNum">Destination Row Number</param>
    private void CopyRow(HSSFWorkbook workbook, HSSFSheet worksheet, int sourceRowNum, int destinationRowNum) {
        // Get the source / new row

        HSSFRow newRow = (HSSFRow) worksheet.getRow(destinationRowNum);
        HSSFRow sourceRow = (HSSFRow) worksheet.getRow(sourceRowNum);

        // If the row exist in destination, push down all rows by 1 else create
        // a new row
        if (newRow != null) {
            worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
        } else {
            newRow = (HSSFRow) worksheet.createRow(destinationRowNum);
        }

        // Loop through source columns to add to new row
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            // Grab a copy of the old/new cell
            HSSFCell oldCell = sourceRow.getCell(i);
            HSSFCell newCell = newRow.createCell(i);
            // If the old cell is null jump to next cell
            if (oldCell == null) {
                newCell = null;
                continue;
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
//			newCell.setCellType(oldCell.getCellType());
            newCell.setCellType(oldCell.getCellTypeEnum());

            // Set the cell data value
            switch (oldCell.getCellType()) {
                case BLANK:
                    // newCell.setCellValue(oldCell.StringCellValue);
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
                case FORMULA:
                    // newCell.setCellFormula(oldCell.get);
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

        // If there are are any merged regions in the source row, copy to new row
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum() + (cellRangeAddress.getFirstRow() - cellRangeAddress.getLastRow())),
                        cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
                worksheet.addMergedRegion(newCellRangeAddress);
            }
        }
    }


    // 导出ExportFile数据
    /// <summary>
    /// 导出ExportFile数据
    /// </summary>
    /// <param name="mySheet">mySheet</param>
    /// <param name="myBook">myBook</param>
    private String ExportFile(String outFilePath)
    {
        //判断模板是否存在
        File outFile = new File(outFilePath);
        if (!outFile.exists()) {
            return ExportName + "的模板文件不存在，请上传模板文件。";
        }
        //读取文件流

        FileInputStream readfile = null;
        try {
            readfile = new FileInputStream(outFilePath);
        } catch (Exception e) {
            return ExportName + "的模板文件正在打开，请关闭后再导出。";
        }
        //创建Workbook
        HSSFWorkbook workbook = null;
        try
        {
            workbook = new HSSFWorkbook(readfile);
        } catch (Exception e) {
            try {
                readfile.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }//关闭读取的文件流
            return ExportName + "的模板文件格式错误，请修改。";
        }
        try {
            readfile.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }//关闭读取的文件流
        if (workbook == null)//工作薄中没有工作表

        {
            return ExportName + "的模板文件格式错误，请修改。";
        }
        //创建Sheet
        HSSFSheet sheet = null;
        String SheetName = sheetName;
        if (StringUtils.isEmpty(SheetName)) {
            sheet = workbook.getSheetAt(0);
        }
        else {
            sheet = workbook.getSheet(SheetName);
        }
        if (sheet == null)//工作薄中没有工作表

        {
            return ExportName + "的ExcelSheetName名称错误，请修改。";
        }
        sheet.setForceFormulaRecalculation(true);

        //开始导出行号

        int RowNumber = 2;
        try
        {
            RowNumber = Integer.parseInt(this.RowNumber);//开始导出行号

        } catch (Exception e) {
            return ExportName + "的开始行号错误，请修改。";
        }
        //是否复制格式
        String CopyFormat = "01";
        if (StringUtils.isNotEmpty(this.CopyFormat))
        {
            CopyFormat = this.CopyFormat;
        }

        // 右击文件 属性信息

//        {
        //文档的摘要信息

        //DocumentSummaryInformation dsi = PropertySetFactory.CreateDocumentSummaryInformation();
        //dsi.Company = "北京银通先达信息技术有限公司";//公司名称
        //dsi.Category = "";//类别
        //dsi.Manager = "Manager";
        //workbook.DocumentSummaryInformation = dsi;

        //SummaryInformation si = PropertySetFactory.CreateSummaryInformation();
        //si.Author = "YTXD"; //填加xls文件作者信息

        //si.ApplicationName = ""; //填加xls文件创建程序信息
        //si.LastAuthor = ""; //填加xls文件最后保存者信息

        //si.Comments = ""; //填加xls文件作者信息

        //si.Title = ""; //填加xls文件标题信息
        //si.Subject = ""; //填加文件主题信息
        //si.Keywords = "";//关键词

        //si.CreateDateTime = DateTime.Now;
        //workbook.SummaryInformation = si;
//        }


        int rowIndex = 0;
        for(int iRow=0; iRow<dtSource.size();iRow++)
        {
            // 新建表，填充表头，填充列头，样式
            if (rowIndex == 65535 || rowIndex == 0)
            {
                //-----还需要根据导出开始行判断复制表头和格式---------------------------------
                if (rowIndex != 0)
                {
                    sheet = workbook.createSheet();
                    rowIndex = rowIndex - 65535;
                }

                //判断数据源是否存在要导出的列
                for (int i = 0; i < dtItem.size(); i++)
                {
                    SM_ExcelItem objItem=dtItem.get(i);
                    String ExcelColumnName = objItem.getexcelcolumnname();
                    String SourceColumnName = objItem.getsourcecolumnname();//数据源列名

                    if (getValue(dtSource.get(0), SourceColumnName)==null && !SourceColumnName.toLowerCase().equals("autonumber") && StringUtils.isNotEmpty(SourceColumnName))
                    {
                        return ExportName + "的" + ExcelColumnName + "的数据源列名错误，请修改。";
                    }
                }
                rowIndex = RowNumber - 1;
            }

            // 填充内容
            try
            {
                if (CopyFormat.equals("01") && (rowIndex != RowNumber - 1))
                {
                    CopyRow(workbook, sheet, rowIndex - 1, rowIndex);
                }
            } catch (Exception ex) {
                return ExportName + "的开始行号错误，请修改。";
            }
            HSSFRow dataRow = sheet.getRow(rowIndex);
            if(dataRow==null){
                dataRow=sheet.createRow(rowIndex);
            }
            for (int i = 0; i < dtItem.size(); i++)
            {
                SM_ExcelItem objItem=dtItem.get(i);
                int ColumnNumber = i;
                if (!CopyFormat.equals("01"))
                {
                    try
                    {
                        ColumnNumber = Integer.parseInt(objItem.getcolumnnumber());//EXCEL列编号

                    } catch (Exception ex) {
                        return ExportName + "的" + objItem.getexcelcolumnname() + "的列号错误，请修改。";
                    }
                    ColumnNumber = ColumnNumber - 1;
                }

                HSSFCell newCell = dataRow.getCell(i);
                if(newCell==null){
                    newCell=dataRow.createCell(i);
                }
                String SourceColumnName = objItem.getsourcecolumnname();//数据源列名

                String drValue = "";
                if (SourceColumnName.toLowerCase().equals("autonumber") || StringUtils.isEmpty(SourceColumnName))
                {
                    drValue = (rowIndex - RowNumber + 2) + "";
                }
                else
                {
                    drValue = getValue(dtSource.get(iRow), SourceColumnName).toString();
                }
                //newCell.SetCellValue(drValue);
                SetCellValue(newCell, drValue, getType(dtSource.get(iRow), SourceColumnName));
            }
            rowIndex++;
        }
        try
        {
            FileOutputStream writefile = new FileOutputStream(outFile);
            workbook.write(writefile);
            writefile.close();
        }
        catch (Exception ex) {
            return ex.getMessage().toString();
        }

        return "";
    }

    /// <summary>
    /// 导出ExportFile数据 自动
    /// </summary>
    /// <param name="mySheet">mySheet</param>
    /// <param name="myBook">myBook</param>
    private String ExportFileAuto(String outFilePath)
    {
//        if (dtExportItem == null)
//        {
//            String strURL = Page.Request.AppRelativeCurrentExecutionFilePath + Page.Request.Url.Query;
//            Page.Session["dtExpert@Data"] = dtSource;
//            Page.Response.Redirect("~/ExcelExport.aspx?ExportName=" + ExportName + "&strURL=" + strURL.Replace("&", "*"));
//        }
//        else
//        {
//            dtItem = dtExportItem;
//        }
        //增加合计行

//        try
//        {
//        	int TotalStartColumn=0;
//        	ArrayList TotalList = new ArrayList();
//        	for(int i=0;i<dtItem.size();i++){
//        		SM_ExcelItem obj = dtItem.get(i);
//        		if(obj.getiftotal().equals("01")){
//        			TotalList.add(obj.getsourcecolumnname());
//        			if(i==0){
//        				TotalStartColumn=Integer.parseInt(obj.getcolumnnumber());
//        			}
//        		}
//        	}
//        	float TotalValue[]=new float[TotalList.size()];
//        	for (int i = 0; i < TotalList.size(); i++)
//            {
//        		TotalValue[i]=0;
//            }
//        	if (TotalList.size()>0)
//            {
////                DataView dView = dtSource.DefaultView;
////                DataRow TotalRow = dtSource.NewRow();
////                int TotalCount = 0;
//                for (int i = 0; i < TotalList.size(); i++)
//                {
//                	for(int j=0;j<dtSource.size();j++)
//                	{
//                		TotalValue[i]=TotalValue[i]+ Float.parseFloat(getValue(dtSource.get(j), TotalList.get(i)).toString());
//                	}
//                }
//                if (TotalCount == 1 && i != 0)
//                {
//                    try
//                    {
//                        TotalRow[dtItem.Rows[i - 1]["SourceColumnName"].ToString()] = "合计";
//                    }
//                    catch { }
//                }
//                dtSource.Rows.Add(TotalRow);
//            }
//        }
//        catch { }

        //创建工作薄

        HSSFWorkbook workbook = new HSSFWorkbook();
        //得到SheetName名称
        String SheetName = this.sheetName;
        if (StringUtils.isEmpty(SheetName))
        {
            SheetName = "Sheet1";
        }
        //创建工作页

        HSSFSheet sheet = workbook.createSheet(SheetName);

        // 右击文件 属性信息

        {
            //文档的摘要信息

            //DocumentSummaryInformation dsi = PropertySetFactory.CreateDocumentSummaryInformation();
            //dsi.Company = "北京银通先达信息技术有限公司";//公司名称
            //dsi.Category = "";//类别
            //dsi.Manager = "Manager";
            //workbook.DocumentSummaryInformation = dsi;

            //SummaryInformation si = PropertySetFactory.CreateSummaryInformation();
            //si.Author = "YTXD"; //填加xls文件作者信息

            //si.ApplicationName = ""; //填加xls文件创建程序信息
            //si.LastAuthor = ""; //填加xls文件最后保存者信息

            //si.Comments = ""; //填加xls文件作者信息

            //si.Title = ""; //填加xls文件标题信息
            //si.Subject = ""; //填加文件主题信息
            //si.Keywords = "";//关键词

            //si.CreateDateTime = DateTime.Now;
            //workbook.SummaryInformation = si;
        }

        sheet.createFreezePane(0, 1, 0, 1);//冻结首行

        HSSFCellStyle dateStyle = workbook.createCellStyle();
        HSSFDataFormat format = workbook.createDataFormat();
        dateStyle.setDataFormat(format.getFormat("yyyy-mm-dd"));
        ArrayList CellStyleList = new ArrayList();

        int rowIndex = 0;
        //如果数据源为空，只导出列头
        if(dtSource.size()==0){
            createhead(sheet,workbook);
        }else{
            for (int iRow=0;iRow<dtSource.size();iRow++)
            {
                // 新建表，填充表头，填充列头，样式
                if (rowIndex == 65535 || rowIndex == 0)
                {
                    if (rowIndex != 0)
                    {
                        sheet = workbook.createSheet();
                        rowIndex = rowIndex - 65535;
                    }

                    // 表头及样式

                    //{
                    //    HSSFRow headerRow = sheet.CreateRow(0) as HSSFRow;
                    //    headerRow.HeightInPoints = 25;
                    //    headerRow.CreateCell(0).SetCellValue(strHeaderText);

                    //    HSSFCellStyle headStyle = workbook.CreateCellStyle() as HSSFCellStyle;
                    //    headStyle.Alignment = NPOI.SS.UserModel.HorizontalAlignment.Center;
                    //    HSSFFont font = workbook.CreateFont() as HSSFFont;
                    //    font.FontHeightInPoints = 20;
                    //    font.Boldweight = 700;
                    //    headStyle.SetFont(font);

                    //    headerRow.GetCell(0).CellStyle = headStyle;

                    //    sheet.AddMergedRegion(new Region(0, 0, 0, dtSource.Columns.Count - 1));//合并单元格

                    //    //headerRow.Dispose();
                    //}



                    // 列头及样式

                    {
                        HSSFRow headerRow = sheet.createRow(0);
                        //表头样式
                        HSSFCellStyle headStyle = workbook.createCellStyle();
                        headStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
                        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
                        headStyle.setWrapText(true);// 指定单元格自动换行

                        headStyle.setBorderBottom(BorderStyle.THIN);//边框线

                        headStyle.setBorderLeft(BorderStyle.THIN);//边框线

                        headStyle.setBorderRight(BorderStyle.THIN);//边框线

                        headStyle.setBorderTop(BorderStyle.THIN);//边框线

                        //字体样式
                        HSSFFont font = workbook.createFont();//字体样式
                        //font.FontHeightInPoints = 10;//字号
                        font.setBold(true);// 字体加粗
                        headStyle.setFont(font);

                        //增加表头
                        for (int i = 0; i < dtItem.size(); i++)
                        {
                            SM_ExcelItem obj=dtItem.get(i);
                            //设置值

                            headerRow.createCell(i).setCellValue(obj.getexcelcolumnname());
                            //设置样式
                            headerRow.getCell(i).setCellStyle(headStyle);
                        }
                    }


                    // 数据列样式

                    {
                        //增加表头
                        CellStyleList = new ArrayList();
                        for (int i = 0; i < dtItem.size(); i++)
                        {
                            SM_ExcelItem obj=dtItem.get(i);
                            String ExcelColumnName = obj.getexcelcolumnname();
                            //判断数据源是否存在要导出的列
                            String SourceColumnName = obj.getsourcecolumnname();//数据源列名

                            if (getValue(dtSource.get(0), SourceColumnName)==null && !SourceColumnName.toLowerCase().equals("autonumber") && StringUtils.isNotEmpty(SourceColumnName))
                            {
                                return ExportName + "的" + ExcelColumnName + "的数据源列名错误，请修改。";
                            }
                            //单元格样式

                            HSSFCellStyle ColumnStyle = workbook.createCellStyle();
                            ColumnStyle.setAlignment(HorizontalAlignment.CENTER);//垂直居中
                            ColumnStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                            ColumnStyle.setWrapText(true);// 指定单元格自动换行

                            ColumnStyle.setBorderBottom(BorderStyle.THIN);//边框线

                            ColumnStyle.setBorderLeft(BorderStyle.THIN);//边框线

                            ColumnStyle.setBorderRight(BorderStyle.THIN);//边框线

                            ColumnStyle.setBorderTop(BorderStyle.THIN);//边框线

                            //字体
                            HSSFFont font = workbook.createFont();
                            font.setFontHeightInPoints((short) 10);//字号
                            ColumnStyle.setFont(font);
                            //水平对齐
                            String Alignment = obj.gethorizontalalignment();
                            if (Alignment.equals("-4108"))
                            {
                                ColumnStyle.setAlignment(HorizontalAlignment.CENTER);
                            }
                            else if (Alignment.equals("-4131"))
                            {
                                ColumnStyle.setAlignment(HorizontalAlignment.LEFT);
                            }
                            else if (Alignment.equals("-4152"))
                            {
                                ColumnStyle.setAlignment(HorizontalAlignment.RIGHT);
                            }
                            else if (Alignment.equals(""))
                            {
                            }
                            else if (Alignment.equals("-4130"))
                            {
                                ColumnStyle.setAlignment(HorizontalAlignment.JUSTIFY);
                            }
                            else if (Alignment.equals("-4117"))
                            {
                                ColumnStyle.setAlignment(HorizontalAlignment.DISTRIBUTED);
                            }
                            else if (Alignment.equals("1"))
                            {
                                ColumnStyle.setAlignment(HorizontalAlignment.GENERAL);
                            }
                            else if (Alignment.equals("5"))
                            {
                                ColumnStyle.setAlignment(HorizontalAlignment.FILL);
                            }
                            else if (Alignment.equals("7"))
                            {
                                ColumnStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                            }
                            else
                            {
                                ColumnStyle.setAlignment(HorizontalAlignment.CENTER);
                            }

                            //数据格式
                            String NumberFormatLocal = obj.getnumberformatlocal();
                            if (StringUtils.isNotEmpty(NumberFormatLocal))
                            {
                                try
                                {
                                    HSSFDataFormat ColumnFormat = workbook.createDataFormat();
                                    ColumnStyle.setDataFormat(ColumnFormat.getFormat(NumberFormatLocal));
                                } catch (Exception ex){
                                    return ExportName + "的" + ExcelColumnName + "的格式错误，请修改。";
                                }
                            }
                            //else
                            //{
                            //    ColumnStyle.DataFormat = HSSFDataFormat.GetBuiltinFormat("@");
                            //}
                            //设置样式
                            //sheet.SetDefaultColumnStyle(i, ColumnStyle);
                            CellStyleList.add(ColumnStyle);
                            //设置列宽
                            try
                            {
                                String ColumnWidth = obj.getcolumnwidth();
                                if (StringUtils.isNotEmpty(ColumnWidth))
                                {
                                    sheet.setColumnWidth(i, Integer.parseInt(ColumnWidth) * 256);
                                }
                                else
                                {
                                    sheet.setColumnWidth(i, 10 * 256);
                                }
                            }
                            catch (Exception ex)
                            {
                                return ExportName + "的" + ExcelColumnName + "的列宽错误，请修改。";
                            }
                        }
                    }
                    rowIndex = 1;
                }

                // 填充内容
                HSSFRow dataRow = sheet.createRow(rowIndex);
                for (int i = 0; i < dtItem.size(); i++)
                {
                    SM_ExcelItem obj=dtItem.get(i);
                    HSSFCell newCell = dataRow.createCell(i);
                    newCell.setCellStyle((HSSFCellStyle)CellStyleList.get(i));
                    String SourceColumnName = obj.getsourcecolumnname();//数据源列名

                    String drValue = "";
                    if (SourceColumnName.toLowerCase().equals("autonumber") || StringUtils.isEmpty(SourceColumnName))
                    {
                        drValue = rowIndex + "";
                    }
                    else
                    {
                        //数据格式
                        String NumberFormatLocal = obj.getnumberformatlocal();
                        if (StringUtils.isNotEmpty(NumberFormatLocal) && NumberFormatLocal.toLowerCase().indexOf("yy") >= 0)
                        {
                            final String value = getValue(dtSource.get(iRow), SourceColumnName).toString();
                            try
                            {
                                SimpleDateFormat df = new SimpleDateFormat(NumberFormatLocal.replaceAll("h", "H"));
                                Date date = df.parse(value);
                                drValue = df.format(date);
                            }
                            catch(Exception ex)
                            {
                                drValue = value;
                            }
                        } else if(StringUtils.isNotEmpty(NumberFormatLocal) && NumberFormatLocal.indexOf("0.00") >= 0){
                            try
                            {
                                DecimalFormat df = new DecimalFormat("#0.00");
                                drValue = df.format(Double.valueOf(getValue(dtSource.get(iRow), SourceColumnName).toString()));
                            }
                            catch(Exception ex)
                            {
                                drValue = getValue(dtSource.get(iRow), SourceColumnName).toString();
                            }
                        }
                        else
                        {
                            drValue = getValue(dtSource.get(iRow), SourceColumnName).toString();
                        }
                    }
                    //newCell.SetCellValue(drValue);
                    SetCellValue(newCell, drValue, getType(dtSource.get(iRow), SourceColumnName));
                }

                rowIndex++;
            }
        }
        FileOutputStream output;
        try {
            output = new FileOutputStream(outFilePath, true);
            try {
                workbook.write(output);
                output.flush();
                workbook.close();
                output.close();
            } catch (IOException e) {
                System.out.println("文件写入错误");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("要导出的文件路径错误");
            e.printStackTrace();
        }
//        using (MemoryStream ms = new MemoryStream())
//        {
//            workbook.Write(ms);
//            ms.Flush();
//            ms.Position = 0;
//
//            //sheet.Dispose();
//            //workbook.Dispose();
//
//            string[] temp = outFilePath.Split('.');
//
//            if (temp[temp.Length - 1] == "xls" && dtItem.Rows.Count < 256 && dtSource.Rows.Count < 65536)
//            {
//                using (FileStream fs = new FileStream(outFilePath, FileMode.Create, FileAccess.Write))
//                {
//                    byte[] data = ms.ToArray();
//                    fs.Write(data, 0, data.Length);
//                    fs.Flush();
//                }
//            }
//            else
//            {
//                if (temp[temp.Length - 1] == "xls")
//                {
//                    outFilePath = outFilePath + "x";
//                }
//
//                using (FileStream fs = new FileStream(outFilePath, FileMode.Create, FileAccess.Write))
//                {
//                    byte[] data = ms.ToArray();
//                    fs.Write(data, 0, data.Length);
//                    fs.Flush();
//                }
//            }
//            //return ms;
//        }

        return "";
    }


    // 文件下载
    /// <summary>
    /// 得到下载路径
    /// </summary>
    /// <returns>下载路径FileUrl</returns>
    public String GetDownFilePath()
    {
        if (StringUtils.isNotEmpty(outFileName))
        {
            return "ExcelOperation/makeExcel/" + outFileName;
        }
        else
        {
            return "ExcelOperation/makeExcel/" + this.Description + ".xls";
        }
    }

    //给单元格赋值

    public static boolean IsNumeric(String str) {
        Pattern pattern = Pattern.compile("^[-+]?[1-9][0-9]{0,16}(\\.\\d+)?$|^[-+]?0(\\.\\d+)?$|^0$");
        return pattern.matcher(str).matches();
    }
    private void SetCellValue(HSSFCell newCell, String CellValue, String DataType)
    {
        if (StringUtils.isEmpty(CellValue))
        {
            newCell.setCellValue(CellValue); return;
        }
        switch (DataType) {
            case "String":
                if (IsNumeric(CellValue)) {
                    newCell.setCellValue(Double.parseDouble(CellValue));
                } else {
                    newCell.setCellValue(CellValue);
                }
                break;
            default:
                newCell.setCellValue(CellValue);
                break;
        }
//        switch (DataType)
//        {
//            case "String": //字符串类型
//                double result;
//                if (IsNumeric(CellValue, out result))
//                {
//                    double.TryParse(CellValue, out result);
//                    newCell.SetCellValue(result);
//                    break;
//                }
//                else
//                {
//                    newCell.SetCellValue(CellValue);
//                    break;
//                }
//
//            case "System.DateTime": //日期类型
//                DateTime dateV;
//                DateTime.TryParse(CellValue, out dateV);
//                newCell.SetCellValue(dateV);
//                break;
//            case "System.Boolean": //布尔型
//                bool boolV = false;
//                bool.TryParse(CellValue, out boolV);
//                newCell.SetCellValue(boolV);
//                break;
//            case "System.Int16": //整型
//            case "System.Int32":
//            case "System.Int64":
//            case "System.Byte":
//                int intV = 0;
//                int.TryParse(CellValue, out intV);
//                newCell.SetCellValue(intV);
//                break;
//            case "System.Decimal": //浮点型
//            case "System.Double":
//                double doubV = 0;
//                double.TryParse(CellValue, out doubV);
//                newCell.SetCellValue(doubV);
//                break;
//            case "System.DBNull": //空值处理
//                newCell.SetCellValue("");
//                break;
//            default:
//                newCell.SetCellValue(CellValue);
//                break;
//        }
    }
    private String getMapKeyValue(Map<String, Object> map, String key) {
        if(map == null) {
            return "";
        }
        Object obj = map.get(key);
        if(obj == null) {
            obj = map.get(key.toLowerCase());
        }
        if(obj == null) {
            obj = map.get(key.toUpperCase());
        }
        if(obj != null) {
            return obj.toString();
        } else {
            return "";
        }
    }
    private String getValue(Object dto, String name) {
        //return ((HashMap)dto).get(name).toString();
        Object result = null;
        if(dto instanceof Map){
            Map<String, Object> map = (Map<String, Object>) dto;
            try {
                result = getMapKeyValue(map, name);
            } catch (Exception ex) {
            }
            if (result == null)
                return "";
            else
                return StringEscapeUtils.unescapeHtml4(result.toString());
        }else{
            Method[] m = dto.getClass().getMethods();
            for (int i = 0; i < m.length; i++) {
                if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                    try {
                        result = m[i].invoke(dto);
                    } catch (Exception ex) {
                    }
                    if (result == null)
                        return "";
                    else
                        return StringEscapeUtils.unescapeHtml4(result.toString());
                }
            }
        }
        return "";
    }

    private String getType(Object dto, String name) {
        if(dto instanceof Map){
            return "String";
        }else{
            Method[] m = dto.getClass().getMethods();
            for (int i = 0; i < m.length; i++) {
                if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                    return m[i].getGenericReturnType().toString();
                }
            }
        }
        return null;
    }
    private String createhead(HSSFSheet sheet,HSSFWorkbook workbook){
        {
            HSSFRow headerRow = sheet.createRow(0);
            //表头样式
            HSSFCellStyle headStyle = workbook.createCellStyle();
            headStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
            headStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
            headStyle.setWrapText(true);// 指定单元格自动换行

            headStyle.setBorderBottom(BorderStyle.THIN);//边框线

            headStyle.setBorderLeft(BorderStyle.THIN);//边框线

            headStyle.setBorderRight(BorderStyle.THIN);//边框线

            headStyle.setBorderTop(BorderStyle.THIN);//边框线

            //字体样式
            HSSFFont font = workbook.createFont();//字体样式
            //font.FontHeightInPoints = 10;//字号
            font.setBold(true);// 字体加粗
            headStyle.setFont(font);

            //增加表头
            for (int i = 0; i < dtItem.size(); i++)
            {
                SM_ExcelItem obj=dtItem.get(i);
                //设置值

                headerRow.createCell(i).setCellValue(obj.getexcelcolumnname());
                //设置样式
                headerRow.getCell(i).setCellStyle(headStyle);
            }
        }


        // 数据列样式

        {
            //增加表头
            ArrayList CellStyleList = new ArrayList();
            for (int i = 0; i < dtItem.size(); i++)
            {
                SM_ExcelItem obj=dtItem.get(i);
                String ExcelColumnName = obj.getexcelcolumnname();
                //判断数据源是否存在要导出的列
                String SourceColumnName = obj.getsourcecolumnname();//数据源列名

//                 if (getValue(dtSource.get(0), SourceColumnName)==null && !SourceColumnName.toLowerCase().equals("autonumber") && StringUtils.isNotEmpty(SourceColumnName))
//                 {
//                     return ExportName + "的" + ExcelColumnName + "的数据源列名错误，请修改。";
//                 }
                //单元格样式

                HSSFCellStyle ColumnStyle = workbook.createCellStyle();
                ColumnStyle.setAlignment(HorizontalAlignment.CENTER);//垂直居中
                ColumnStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                ColumnStyle.setWrapText(true);// 指定单元格自动换行

                ColumnStyle.setBorderBottom(BorderStyle.THIN);//边框线

                ColumnStyle.setBorderLeft(BorderStyle.THIN);//边框线

                ColumnStyle.setBorderRight(BorderStyle.THIN);//边框线

                ColumnStyle.setBorderTop(BorderStyle.THIN);//边框线

                //字体
                HSSFFont font = workbook.createFont();
                font.setFontHeightInPoints((short) 10);//字号
                ColumnStyle.setFont(font);
                //水平对齐
                String Alignment = obj.gethorizontalalignment();
                if (Alignment.equals("-4108"))
                {
                    ColumnStyle.setAlignment(HorizontalAlignment.CENTER);
                }
                else if (Alignment.equals("-4131"))
                {
                    ColumnStyle.setAlignment(HorizontalAlignment.LEFT);
                }
                else if (Alignment.equals("-4152"))
                {
                    ColumnStyle.setAlignment(HorizontalAlignment.RIGHT);
                }
                else if (Alignment.equals(""))
                {
                }
                else if (Alignment.equals("-4130"))
                {
                    ColumnStyle.setAlignment(HorizontalAlignment.JUSTIFY);
                }
                else if (Alignment.equals("-4117"))
                {
                    ColumnStyle.setAlignment(HorizontalAlignment.DISTRIBUTED);
                }
                else if (Alignment.equals("1"))
                {
                    ColumnStyle.setAlignment(HorizontalAlignment.GENERAL);
                }
                else if (Alignment.equals("5"))
                {
                    ColumnStyle.setAlignment(HorizontalAlignment.FILL);
                }
                else if (Alignment.equals("7"))
                {
                    ColumnStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                }
                else
                {
                    ColumnStyle.setAlignment(HorizontalAlignment.CENTER);
                }

                //数据格式
                String NumberFormatLocal = obj.getnumberformatlocal();
                if (StringUtils.isNotEmpty(NumberFormatLocal))
                {
                    try
                    {
                        HSSFDataFormat ColumnFormat = workbook.createDataFormat();
                        ColumnStyle.setDataFormat(ColumnFormat.getFormat(NumberFormatLocal));
                    } catch (Exception ex){
                        return ExportName + "的" + ExcelColumnName + "的格式错误，请修改。";
                    }
                }
                //else
                //{
                //    ColumnStyle.DataFormat = HSSFDataFormat.GetBuiltinFormat("@");
                //}
                //设置样式
                //sheet.SetDefaultColumnStyle(i, ColumnStyle);
                CellStyleList.add(ColumnStyle);
                //设置列宽
                try
                {
                    String ColumnWidth = obj.getcolumnwidth();
                    if (StringUtils.isNotEmpty(ColumnWidth))
                    {
                        sheet.setColumnWidth(i, Integer.parseInt(ColumnWidth) * 256);
                    }
                    else
                    {
                        sheet.setColumnWidth(i, 10 * 256);
                    }
                }
                catch (Exception ex)
                {
                    return ExportName + "的" + ExcelColumnName + "的列宽错误，请修改。";
                }
            }
        }
        return "";
    }
}

