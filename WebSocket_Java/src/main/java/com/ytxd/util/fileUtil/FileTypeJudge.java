package com.ytxd.util.fileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 项目名称：科研管理系统   
 * 类名称：    FileTypeJudge   
 * 类描述：    对上传文件的类型进行验证，此类验证的是上传文件的十六进制文件头，所以txt类型验证不了
 * 创建人：    李成功   
 * 创建时间：2019年10月16日 上午9:40:24   
 * @version V 1.0   
 *
 */
public final class FileTypeJudge {  
	  
    /** 
     * Constructor 
     */  
    private FileTypeJudge() {  
    }  
  
    /** 
     * 将文件头转换成16进制字符串 
     *  
     * @param 原生byte 
     * @return 16进制字符串 
     */  
    private static String bytesToHexString(byte[] src) {  
  
        StringBuilder stringBuilder = new StringBuilder();  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        for (int i = 0; i < src.length; i++) {  
            int v = src[i] & 0xFF;  
            String hv = Integer.toHexString(v);  
            if (hv.length() < 2) {  
                stringBuilder.append(0);  
            }  
            stringBuilder.append(hv);  
        }  
        return stringBuilder.toString();  
    }  
  
    /** 
     * 得到文件头 
     *  
     * @param filePath 
     *            文件路径 
     * @return 文件头 
     * @throws IOException 
     */  
    private static String getFileContent(InputStream is) throws IOException {  
  
        byte[] b = new byte[28];  
  
        InputStream inputStream = null;  
  
        try {  
            is.read(b, 0, 28);  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        } finally {  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw e;  
                }  
            }  
        }  
        return bytesToHexString(b);  
    }  
  
    /** 
     * 判断文件类型 
     *  
     * @param filePath 
     *            文件路径 
     * @return 文件类型 
     */  
    public static FileType getType(InputStream is) throws IOException {  
  
        String fileHead = getFileContent(is);  
        if (fileHead == null || fileHead.length() == 0) {  
            return null;  
        }  
        fileHead = fileHead.toUpperCase();  
        FileType[] fileTypes = FileType.values();  
  
        for (FileType type : fileTypes) {  
            if (fileHead.startsWith(type.getValue())) {  
                return type;  
            }  
        }  
  
        return null;  
    }  
    
    /**
     * @Title: isInFileType
     * @Description: TODO 上传文件类型是否在固定的安全类型中
     * @author 李成功
     * @date 2019年10月15日 下午4:18:16
     * @modifier：      
     * @modifierTime：2019年10月15日 下午4:18:16   
     * @modifierNotes：   
     * @param value
     * @return
     */
    public static boolean isInFileType(FileType value) {
    	boolean isSecurity=false;
    	FileType[] securityFileType = { FileType.JPEG, FileType.PNG, FileType.GIF, FileType.TIFF, FileType.BMP, FileType.DWG, FileType.PSD, 
    			 FileType.RTF, FileType.XLS_DOC, FileType.XLSX_DOCX, FileType.VSD, 
                 FileType.MDB, FileType.WPS, FileType.PDF, FileType.ZIP, FileType.RAR,  
                 FileType.AVI, FileType.RAM, FileType.RM, FileType.MPG, FileType.MOV, FileType.ASF, FileType.MP4, FileType.FLV, FileType.MID,
                 FileType.WAV, FileType.MP3 };  
    	for (FileType fileType : securityFileType) {
    		if (fileType.equals(value)) {  
    			isSecurity = true;  
    			break;
            }  
		}
    	return isSecurity;
    }
    
    /**
     * @Title: isInFileType
     * @Description: TODO 上传文件类型是否在指定的类型中
     * @author 李成功
     * @date 2019年10月15日 下午4:18:16
     * @modifier：      
     * @modifierTime：2019年10月15日 下午4:18:16   
     * @modifierNotes：   
     * @param value
     * @return
     * @throws IOException 
     */
    public static boolean isInFileType(InputStream is,FileType[] fileTypeList) throws IOException {
    	boolean isSecurity=false;
    	FileType value = FileTypeJudge.getType(is);
    	for (FileType fileType : fileTypeList) {
    		if (fileType.equals(value)) {  
    			isSecurity = true;  
    			break;
    		}  
    	}
    	return isSecurity;
    }
    	
    /**
     * @Title: fileTypeSecurity
     * @Description: TODO 上传的文件类型是否安全
     * @author 李成功
     * @date 2019年10月15日 下午4:18:09
     * @modifier：      
     * @modifierTime：2019年10月15日 下午4:18:09   
     * @modifierNotes：   
     * @param is
     * @return
     * @throws IOException 
     */
    public static boolean fileTypeSecurity(InputStream is) throws IOException {
    	FileType fileType = FileTypeJudge.getType(is);
    	return FileTypeJudge.isInFileType(fileType);
    }
    
    /**
     * 返回文件大类
     * @param value 表示文件类型
     * @return 1 表示图片,2 表示文档,3 表示视频,4 表示种子,5 表示音乐,6 表示其它
     * @return
     */
    public static Integer isFileType(FileType value) {  
        Integer type = 6;// 其他  
        // 图片  
        FileType[] pics = { FileType.JPEG, FileType.PNG, FileType.GIF, FileType.TIFF, FileType.BMP, FileType.DWG, FileType.PSD };  
        //文档
        FileType[] docs = { FileType.RTF, FileType.XML, FileType.HTML, FileType.CSS, FileType.JS, FileType.EML, FileType.DBX, FileType.PST, FileType.XLS_DOC, FileType.XLSX_DOCX, FileType.VSD,  
                FileType.MDB, FileType.WPS, FileType.WPD, FileType.EPS, FileType.PDF, FileType.QDF, FileType.PWL, FileType.ZIP, FileType.RAR, FileType.JSP, FileType.JAVA, FileType.CLASS,  
                FileType.JAR, FileType.MF, FileType.EXE, FileType.CHM };  
        //视频
        FileType[] videos = { FileType.AVI, FileType.RAM, FileType.RM, FileType.MPG, FileType.MOV, FileType.ASF, FileType.MP4, FileType.FLV, FileType.MID };  
        //种子
        FileType[] tottents = { FileType.TORRENT };  
        //音乐
        FileType[] audios = { FileType.WAV, FileType.MP3 };  
        //其他
        FileType[] others = {};  
  
        // 图片  
        for (FileType fileType : pics) {  
            if (fileType.equals(value)) {  
                type = 1; 
                break;
            }  
        }  
        // 文档  
        for (FileType fileType : docs) {  
            if (fileType.equals(value)) {  
                type = 2;  
                break;
            }  
        }  
        // 视频  
        for (FileType fileType : videos) {  
            if (fileType.equals(value)) {  
                type = 3;  
                break;
            }  
        }  
        // 种子  
        for (FileType fileType : tottents) {  
            if (fileType.equals(value)) {  
                type = 4;  
                break;
            }  
        }  
        // 音乐  
        for (FileType fileType : audios) {  
            if (fileType.equals(value)) {  
                type = 5;  
                break;
            }  
        }  
        return type;  
    }  
  
    public static void main(String args[]) throws Exception {  
         System.out.println(
                 FileTypeJudge.isFileType(
                         FileTypeJudge.getType(
                                 new FileInputStream(//file:///C:/Users/Administrator/Desktop/sql50题_每天练2道_很重要很重要(1).pdf
                                         new File("C:\\Users\\Administrator\\Desktop\\sql50题_每天练2道_很重要很重要(1).pdf")))));
         System.out.println(
        		 FileTypeJudge.isInFileType(
        				 FileTypeJudge.getType(
        						 new FileInputStream(//file:///C:/Users/Administrator/Desktop/sql50题_每天练2道_很重要很重要(1).pdf
        								 new File("C:\\Users\\Administrator\\Desktop\\sql50题_每天练2道_很重要很重要(1).pdf")))));
       /* for (FileType type : FileType.values()) {  
            System.out.print(type + "\t");  
        }  */
    }  
}