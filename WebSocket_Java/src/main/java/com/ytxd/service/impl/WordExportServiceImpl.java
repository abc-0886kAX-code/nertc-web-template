package com.ytxd.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.aspose.words.*;
import org.springframework.stereotype.Service;

import com.aspose.words.net.System.Data.DataTable;
import com.ytxd.service.WordExportService;


@Service("WordExportServiceImpl")
public class WordExportServiceImpl implements WordExportService{



	public static Document adocument;
	private static DocumentBuilder builder;
	//public Document adocument;
	//private DocumentBuilder builder;


	/**
	 * 验证License，因为正版需要收费，这样验证后可避免水印
	 */
	private static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = WordExportServiceImpl.class.getClassLoader().getResourceAsStream("License.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean createNewWordDocument(String sTemplateFile) {
		return createNewWordDocument(sTemplateFile, false);
	}

	@Override
	public boolean createNewWordDocument(String sTemplateFile, boolean bVisible) {
		if (!getLicense()) { // 验证License 若不验证则转化出的文件会有水印产生

			return false;
		}
		try {
			return createNewWordDocument(sTemplateFile, adocument, bVisible);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean CreateNewWordDocument(String sTemplateFile, Document adocument) {
		return createNewWordDocument(sTemplateFile, adocument, false);
	}

	@Override
	public boolean createNewWordDocument(String sTemplateFile, Document adocument, boolean bVisible) {
		try {
			WordExportServiceImpl.adocument = new Document(sTemplateFile);
			builder = new DocumentBuilder(WordExportServiceImpl.adocument);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean saveAs(String fileName) {
		return saveAs(fileName, adocument);
	}

	@Override
	public boolean saveAs(String fileName, Document Adocument) {
		if (!getLicense()) { // 验证License 若不验证则转化出的文件会有水印产生

			return false;
		}
		try {
			Adocument.save(fileName, SaveFormat.DOCX);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 保存为pdf格式
	 */
	@Override
	public void saveAsPDF(String wordPath, String pdfPath, String fontsPath) {
		if (!getLicense()) { // 验证License 若不验证则转化出的文件会有水印产生

			return;
		}
		try {
			adocument.save(pdfPath, SaveFormat.PDF);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存为mhtml格式
	 */
	@Override
	public void saveToMht(String wordPath, String pdfPath, String fontsPath) {
		if (!getLicense()) { // 验证License 若不验证则转化出的文件会有水印产生

			return;
		}
		try {
			if(fontsPath!=null && !fontsPath.equals("")){
				FontSettings.setFontsFolder(fontsPath, false);
			}
			File file = new File(pdfPath); // 新建一个文件

			FileOutputStream os = new FileOutputStream(file);
			Document doc = new Document(wordPath); // Address是将要被转化的word文档
			doc.save(os, SaveFormat.MHTML);// 全面支持DOC, DOCX,
											// OOXML, RTF HTML,
											// OpenDocument,
											// PDF, EPUB, XPS,
											// SWF 相互转换
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存为dot格式
	 */
	@Override
	public void saveToDot(String wordPath, String pdfPath, String fontsPath) {
		if (!getLicense()) { // 验证License 若不验证则转化出的文件会有水印产生

			return;
		}
		try {
			if(fontsPath!=null && !fontsPath.equals("")){
				FontSettings.setFontsFolder(fontsPath, false);
			}
			File file = new File(pdfPath); // 新建一个文件

			FileOutputStream os = new FileOutputStream(file);
			Document doc = new Document(wordPath); // Address是将要被转化的word文档
			doc.save(os, SaveFormat.DOT);// 全面支持DOC, DOCX,
											// OOXML, RTF HTML,
											// OpenDocument,
											// PDF, EPUB, XPS,
											// SWF 相互转换
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存为doc格式
	 */
	@Override
	public void saveToDoc(String wordPath, String pdfPath, String fontsPath) {
		if (!getLicense()) { // 验证License 若不验证则转化出的文件会有水印产生

			return;
		}
		try {
			if(fontsPath!=null && !fontsPath.equals("")){
				FontSettings.setFontsFolder(fontsPath, false);
			}
			File file = new File(pdfPath); // 新建一个文件

			FileOutputStream os = new FileOutputStream(file);
			Document doc = new Document(wordPath); // Address是将要被转化的word文档
			doc.save(os, SaveFormat.DOC);// 全面支持DOC, DOCX,
											// OOXML, RTF HTML,
											// OpenDocument,
											// PDF, EPUB, XPS,
											// SWF 相互转换
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存为docx格式
	 */
	@Override
	public void saveToDocx(String wordPath, String pdfPath, String fontsPath) {
		if (!getLicense()) { // 验证License 若不验证则转化出的文件会有水印产生

			return;
		}
		try {
			if(fontsPath!=null && !fontsPath.equals("")){
				FontSettings.setFontsFolder(fontsPath, false);
			}
			File file = new File(pdfPath); // 新建一个文件

			FileOutputStream os = new FileOutputStream(file);
			Document doc = new Document(wordPath); // Address是将要被转化的word文档
			doc.save(os, SaveFormat.DOCX);// 全面支持DOC, DOCX,
											// OOXML, RTF HTML,
											// OpenDocument,
											// PDF, EPUB, XPS,
											// SWF 相互转换
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		adocument = null;
	}

	@Override
	public void close(Document adocument) {
		adocument = null;
	}

	/**
	 * 填充书签,在书签处填充文字
	 */
	@Override
	public String fill(String sBookmark, String sValue) {
		if (!getLicense()) { // 验证License 若不验证则转化出的文件会有水印产生
			return "getLicense失败; ";
		}
		try {
			if (adocument.getRange().getBookmarks().get(sBookmark) != null) {
				builder.moveToBookmark(sBookmark);
				builder.write(sValue == null ? "" : sValue);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "在标签["+sBookmark+"]处设置值["+sValue+"]时失败; ";
		}
		return "";
	}
	
	/**
	 * 填充书签,在书签处填充word
	 */
	@Override
	public String fillWord(String sBookmark, String path) {
		if (!getLicense()) { // 验证License 若不验证则转化出的文件会有水印产生
			return "getLicense失败; ";
		}
		try {
			if (adocument.getRange().getBookmarks().get(sBookmark) == null) {
				return "";
			}
			Document srcDoc = new Document(path);
			String fileName = path.substring(0, path.lastIndexOf("."));
			String fileType = path.substring(path.lastIndexOf(".")).replace(".", "");
			System.out.println("fileName="+fileName+",fileType="+fileType);
			if(fileType.equals("doc") || fileType.equals("dot")){
				this.saveToDocx(path, fileName+".docx", null);
				srcDoc = new Document(fileName+".docx");
			}else if(!fileType.equals("docx")){
				return "插入word失败，附件请用doc、dot或docx格式的文件; ";
			}
			builder.moveToBookmark(sBookmark); 
			builder.insertDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING );  
		} catch (Exception ex) {
			ex.printStackTrace();
			return "标签处插入word失败; ";
		}
		return "";
	}

	/**
	 * 在文档末尾插入word文档
	 */
	@Override
	public void insertWord(String FileName) {
		if (!getLicense()) { // 验证License 若不验证则转化出的文件会有水印产生

			return;
		}
		try {
			Document append = new Document(FileName);
			adocument.appendDocument(append, ImportFormatMode.KEEP_DIFFERENT_STYLES);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	/**
	 * 查找是否有该书签
	 */
	@Override
	public boolean FindSelection(String sBookmark) {
		try {
			if (adocument.getRange().getBookmarks().get(sBookmark) != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 填充表格
	 */
	@Override
	public void executeWithRegions(DataTable dt) throws Exception {
		if (!getLicense()) { // 验证License 若不验证则转化出的文件会有水印产生
			return;
		}
		if(adocument == null) {
			System.out.println("adocument为空");
		}
		if(adocument.getMailMerge() == null) {
			System.out.println("adocument.getMailMerge()为空");
		}
		adocument.getMailMerge().executeWithRegions(dt);
	}

	/**
	 * 标签位置插入图片
	 */
	@Override
	public void insertImg(String sBookmark, String imgPath) throws Exception{
		if (adocument.getRange().getBookmarks().get(sBookmark) == null) {
			return;
		}
		Shape shape = new Shape(adocument,ShapeType.IMAGE);
		BufferedImage bufferedImage = ImageIO.read(new FileInputStream(imgPath));
		shape.getImageData().setImage(bufferedImage);
		shape.setWrapType(WrapType.NONE);
		shape.setBehindText(true);
		builder.moveToBookmark(sBookmark);
		builder.insertNode(shape);
	}
	@Override
	public void insertPage() throws Exception{
		builder.insertBreak(BreakType.PAGE_BREAK);
	}

	@Override
	public void mergeCell(int TableIndex,int RowIndex,int ColumnIndex,int merges,String type)
	{
		try {
			switch (type){
				//纵向合并
				case "Vertical":
					//移动到制定的单元格
					for(int i =1;i<=merges;i++) {
						builder.moveToCell(TableIndex, RowIndex, ColumnIndex, 0);
						builder.getCellFormat().setVerticalMerge(CellMerge.FIRST);
						builder.moveToCell(TableIndex, RowIndex + i, ColumnIndex, 0);
						builder.getCellFormat().setVerticalMerge(CellMerge.PREVIOUS);
					}
					break;
				//横向合并
				case "Horizontal":
					for(int i =1;i<=merges;i++) {
						builder.moveToCell(TableIndex, RowIndex, ColumnIndex, 0);
						builder.getCellFormat().setHorizontalMerge(CellMerge.FIRST);
						builder.moveToCell(TableIndex, RowIndex, ColumnIndex+i, 0);
						builder.getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);
					}
					break;
			}

			//builder.getCellFormat().setHorizontalMerge(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
