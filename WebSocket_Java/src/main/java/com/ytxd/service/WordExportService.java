package com.ytxd.service;

import com.aspose.words.Document;
import com.aspose.words.net.System.Data.DataTable;

public interface WordExportService {

	public boolean createNewWordDocument(String sTemplateFile);
	
	public boolean createNewWordDocument(String sTemplateFile, boolean bVisible);
	
	public boolean CreateNewWordDocument(String sTemplateFile, Document adocument);
	
	public boolean createNewWordDocument(String sTemplateFile, Document adocument, boolean bVisible);
	
	public boolean saveAs(String fileName);
	
	public boolean saveAs(String fileName, Document Adocument);
	
	
	/**
	 * 保存为pdf格式
	 */
	public void saveAsPDF(String wordPath, String pdfPath, String fontsPath);
	
	/**
	 * 保存为mhtml格式
	 */
	public void saveToMht(String wordPath, String pdfPath, String fontsPath);
	
	/**
	 * 保存为dot格式
	 */
	public void saveToDot(String wordPath, String pdfPath, String fontsPath);
	
	/**
	 * 保存为doc格式
	 */
	public void saveToDoc(String wordPath, String pdfPath, String fontsPath);
	
	/**
	 * 保存为docx格式
	 */
	public void saveToDocx(String wordPath, String pdfPath, String fontsPath);
	
	public void close();
	
	public void close(Document adocument);
	
	/**
	 * 填充书签,在书签处填充文字
	 * @return 
	 */
	public String fill(String sBookmark, String sValue);
	
	/**
	 * 在文档末尾插入word文档
	 */
	public void insertWord(String FileName);
	
	/**
	 * 查找是否有该书签
	 */
	public boolean FindSelection(String sBookmark);
	
	/**
	 * 填充表格
	 */
	public void executeWithRegions(DataTable dt) throws Exception;
	
	/**
	 * 标签位置插入图片
	 */
	public void insertImg(String sBookmark, String imgPath) throws Exception;

	
	/**
	 * 标签位置插入word
	 */
	public String fillWord(String sBookmark, String path);
	

	public void insertPage() throws Exception;

	/**
	 * 合并单元格
	 * @param TableIndex 表格索引
	 * @param RowIndex 行索引
	 * @param ColumnIndex 列索引
	 * @param merges 要合并的行、列数量
	 * @param type 合并方式（"Vertical"纵向合并,"Horizontal"横向合并）
	 */
	public void mergeCell(int TableIndex,int RowIndex,int ColumnIndex,int merges,String type);

}
