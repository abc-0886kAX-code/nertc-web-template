package com.ytxd.util.fileUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

import com.ytxd.util.newDate;

/**
 * 说明：文件处理
 * 作者：FH Admin Q31-3596790
 * 
 */
public class FileUtil {

	/**
	 * @Title: saveMultipartFile
	 * @Description: TODO 此方法只是将中煤项目的业务进一步抽取，并不能通用
	 * 采用spring 的 MultipartFile 类进行接受form表单提交上来的单文件进行存储
	 * @author 李成功
	 * @date 2019年10月15日 下午5:02:54
	 * @modifier：      
	 * @modifierTime：2019年10月15日 下午5:02:54   
	 * @modifierNotes：   
	 * @param multipartFilepath 从request中获取到的 MultipartFile 对象
	 * @param destinationAddress 需要将文件存储到的目标地址
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String saveFile(MultipartFile multipartFilepath, String destinationAddress) throws IllegalStateException, IOException{
		if(multipartFilepath!=null && FileTypeJudge.fileTypeSecurity(multipartFilepath.getInputStream())){
			String fileName = saveMultipartFile(multipartFilepath, destinationAddress);
			return !"".equals(fileName) && null!=fileName?fileName:"";
		} 
		return "";
	}
	/**
	 * @Title: saveMultipartFile
	 * @Description: TODO 采用spring 的 MultipartFile 类进行接受form表单提交上来的单文件进行存储
	 * @author 李成功
	 * @date 2019年10月15日 下午5:02:54
	 * @modifier：      
	 * @modifierTime：2019年10月15日 下午5:02:54   
	 * @modifierNotes：   
	 * @param multipartFilepath 从request中获取到的 MultipartFile 对象
	 * @param destinationAddress 需要将文件存储到的目标地址
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String saveMultipartFile(MultipartFile multipartFilepath, String destinationAddress) throws IllegalStateException, IOException{
		String fileName = "";
		if(multipartFilepath!=null){
			fileName =  multipartFilepath.getOriginalFilename();
			//为防止文件名重复，给文件名前面添加日期前缀
		    fileName = newDate.getDate("yyyyMMdd")+(new Random().nextInt(900000)+100000)+fileName;
		    File file=new File(destinationAddress);
		    if(!file.exists()){
		    	file.mkdirs();
		    }
		    file=new File(destinationAddress,fileName);
		    multipartFilepath.transferTo(file);
		}
		return fileName;
	}
	
	/**获取文件大小 返回 KB 保留3位小数  没有文件时返回0
	 * @param filepath 文件完整路径，包括文件名
	 * @return
	 */
	public static Double getFilesize(String filepath){
		File backupath = new File(filepath);
		return Double.valueOf(backupath.length())/1000.000;
	}
	
	/**
	 * 创建目录
	 * @param destDirName目标目录名
	 * @return 
	 */
	public static Boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if(!dir.getParentFile().exists()){				//判断有没有父路径，就是判断文件整个路径是否存在
			return dir.getParentFile().mkdirs();		//不存在就全部创建
		}
		return false;
	}

	/**
	 * 删除文件
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();
		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 读取到字节数组0
	 * @param filePath //路径
	 * @throws IOException
	 */
	public static byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length
				&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		fi.close();
		return buffer;
	}

	/**
	 * 读取到字节数组1
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(String filePath) throws IOException {

		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}

	/**
	 * 读取到字节数组2
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray2(String filePath) throws IOException {
		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			/*while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}*/
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray3(String filePath) throws IOException {

		FileChannel fc = null;
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(filePath, "r");
			fc = rf.getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
					fc.size()).load();
			//System.out.println(byteBuffer.isLoaded());
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				// System.out.println("remain");
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				rf.close();
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @Title: downloadFile
	 * @Description: TODO 对已上传文件的预览方法的统一处理
	 * @author 李成功
	 * @date 2019年9月16日 上午10:30:49
	 * @modifier：      
	 * @modifierTime：2019年9月16日 上午10:30:49   
	 * @modifierNotes：   
	 * @param filepath Web容器中，已上传文件的相对于项目文件夹的路径
	 * @param projRealPath Web容器中，项目文件夹的路径
	 * @return 返回前台需要填充的HTML代码段
	 */
	public static String downloadFile (String filepath, String projRealPath) {
		File file = new File(projRealPath+filepath);
		String result = "";
		if(file.exists()){
			String suffix =file.getPath().substring(file.getPath().lastIndexOf(".")).replace(".", "").toLowerCase();
			switch (suffix) {
				case "jpg":
				case "png":
					result= "<a href=\""+filepath+"\" download=\""+file.getName().substring(14, file.getName().length())+"\">"+file.getName().substring(14, file.getName().length())+"</a><input type=\"hidden\" id=\"attachmentflag\" name=\"attachmentflag\" value=\"1\"/>"
								+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id=\"picBtn\" style='font-family:华文中宋;color:blue;' onclick=\"$('.gallery-pic').dblclick();\">预览</a><div class='gallerys'><img style='display:none;' class='gallery-pic' src=\""+filepath+"\" ondblclick='$.openPhotoGallery(this)' /></div><br>";
					break;
				case "pdf":
					result= "<a href=\""+filepath+"\" download=\""+file.getName().substring(14, file.getName().length())+"\">"+file.getName().substring(14, file.getName().length())+"</a>"
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a class=\"media\" target=\"_blank\" href=\"PDFView/web/viewer.html?file=/kjmis/"+filepath+"\" style='font-family:华文中宋;color:blue;'>预览</a><br>";
					break;
				case "doc":
				case "docx":
				/*case "xls":
				case "xlsx":
				case "ppt":
				case "pptx":
				case "txt":*/
					if(new File(projRealPath+filepath.substring(0,filepath.lastIndexOf("."))+".pdf").exists()){
						result= "<a href=\""+filepath+"\" download=\""+file.getName().substring(14, file.getName().length())+"\">"+file.getName().substring(14, file.getName().length())+"</a>"
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a class=\"media\" target=\"_blank\" href=\"PDFView/web/viewer.html?file=/kjmis/"+filepath.substring(0,filepath.lastIndexOf("."))+".pdf\" style='font-family:华文中宋;color:blue;'>预览</a><br>";
						break;
					}
					/*if(new File(projRealPath+filepath.substring(0,filepath.lastIndexOf("."))+".pdf").exists()){
						result= "<a href=\""+filepath+"\" download=\""+file.getName().substring(14, file.getName().length())+"\">"+file.getName().substring(14, file.getName().length())+"</a>"
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a class=\"media\" target=\"_blank\" href=\"PDFView/web/viewer.html?file=/kjmis/"+filepath.substring(0,filepath.lastIndexOf("."))+".pdf\" style='font-family:华文中宋;color:blue;'>预览</a><br>";
						break;
					} else {
						//如果不存在  将文件再转为PDF保存一遍
						if(ToPdfUtil.toPdf(projRealPath+filepath)){
							if(new File(projRealPath+filepath.substring(0,filepath.lastIndexOf("."))+".pdf").exists()){
								result= "<a href=\""+filepath+"\" download=\""+file.getName().substring(14, file.getName().length())+"\">"+file.getName().substring(14, file.getName().length())+"</a>"
									+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a class=\"media\" target=\"_blank\" href=\"PDFView/web/viewer.html?file=/kjmis/"+filepath.substring(0,filepath.lastIndexOf("."))+".pdf\" style='font-family:华文中宋;color:blue;'>预览</a><br>";
								break;
							}
						}
					}*/
		
				default:
					result= "<a href=\""+filepath+"\" download=\""+file.getName().substring(14, file.getName().length())+"\">"+file.getName().substring(14, file.getName().length())+"</a><br>";
					break;
			}
		}
		 return result;
	}
}