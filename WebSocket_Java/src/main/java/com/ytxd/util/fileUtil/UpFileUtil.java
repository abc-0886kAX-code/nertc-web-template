package com.ytxd.util.fileUtil;

import com.ytxd.common.DataUtils;
import com.ytxd.util.StringUtil;
import com.ytxd.util.newDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UpFileUtil {
	
	/**
	 * 保存上传的文件
	 * @param listFile List<MultipartFile>文件列表
	 * @param filePath 文件上传的相对路径
	 * @return 保存文件的相对路径，多一个文件中间用|分隔
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String saveFile(List<MultipartFile> listFile, String servletRealPath, String relativePath) throws Exception {
		/*
		 * 1.必须进行文件黑名单验证，文件保存类可以不进行白名单验证，黑名单和白名单的验证交给保存前的数据有效性验证
		 * 2.文件名不能用getOriginalFilename()获取的文件名，存储文件的文件名必须是请求的文件名加密后的结果，下载文件名再解密。
		 */
		String returnValue = "";
		if (listFile.size() > 0) {
			// 上传文件
			for (int i = 0; i < listFile.size(); i++) {
				MultipartFile requestFile = listFile.get(i);
				if (requestFile != null) {
					// 得到上传文件名
					String fileName = requestFile.getOriginalFilename();
					// 得到保存文件名
					fileName = newDate.getDate("yyyyMMdd") + (new Random().nextInt(900000) + 100000) + fileName;
					// 如果文件夹不存在就创建
					File file = new File(servletRealPath + relativePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					file = new File(servletRealPath + relativePath, fileName);
					requestFile.transferTo(file);
					if ("".equals(returnValue)) {
						returnValue = relativePath + fileName;
					} else {
						returnValue = returnValue + "|" + relativePath + fileName;
					}
				}
			}
		}
		return returnValue;
	}

	public static String delFile(String saveFileName, String delFileName, String servletRealPath, String relativePath) {
		if (StringUtil.isNotEmpty(delFileName)) {
			String[] delArr = delFileName.replace("*", "|").split("\\|");
			for (int i = 0; i < delArr.length; i++) {
				// 删除服务器上的文件
				String fileName = delArr[i];
				File file = new File(servletRealPath + relativePath, fileName);
				if (file.exists()) {
					file.delete();
				}
				// 根据删除的文件和之前的文件，获取还未删除的文件名称字符串
				fileName = "|" + fileName;
				if (saveFileName.indexOf("|" + fileName) != -1) {
					saveFileName = saveFileName.replace("|" + fileName, "");
				} else if (saveFileName.indexOf(fileName + "|") != -1) {
					saveFileName = saveFileName.replace(fileName + "|", "");
				} else {
					saveFileName = saveFileName.replace(fileName, "");
				}
			}
		}
		return saveFileName;
	}
	public static String saveMultiFile(List<MultipartFile> listFile, String saveFileName, String servletRealPath, String relativePath) throws Exception {
		/*
		 * 1.必须进行文件黑名单验证，文件保存类可以不进行白名单验证，黑名单和白名单的验证交给保存前的数据有效性验证
		 * 2.文件名不能用getOriginalFilename()获取的文件名，存储文件的文件名必须是请求的文件名加密后的结果，下载文件名再解密。
		 */
		/*if (StringUtil.isNotEmpty(saveFileName)) {
			String[] saveArr = saveFileName.split("\\|");
			for (int i = 0; i < saveArr.length; i++) {
				String fileName = saveArr[i];
				File file = new File(servletRealPath + relativePath, fileName); // 判断文件是否存在，若文件不存在，删除文件名
				if (!file.exists()) {
					fileName = "|" + fileName;
					if (saveFileName.indexOf(fileName) != -1) {
						saveFileName = saveFileName.replace(fileName, "");
					} else {
						saveFileName = saveFileName.replace(fileName.substring(1), "");
						if (StringUtil.isNotEmpty(saveFileName)) {
							saveFileName = saveFileName.substring(1);
						}
					}
				}
			}
		} else {
			saveFileName = "";
		}*/
		
		if (listFile.size() > 0) {
			// 上传文件
			for (int i = 0; i < listFile.size(); i++) {
				MultipartFile requestFile = listFile.get(i);
				if (requestFile != null) {
					// 得到上传文件名
					String fileName = requestFile.getOriginalFilename();
					// 文件上传时，Chrome和IE/Edge对于originalFilename处理不同
					// Chrome 会获取到该文件的直接文件名称，IE/Edge会获取到文件上传时完整路径/文件名					
					// Check for Unix-style path
					int unixSep = fileName.lastIndexOf('/');
					// Check for Windows-style path
					int winSep = fileName.lastIndexOf('\\');
					// Cut off at latest possible point
					int pos = (winSep > unixSep ? winSep : unixSep);
					if (pos != -1)  {
						// Any sort of path separator found...
						fileName = fileName.substring(pos + 1);
					}
					
					// 得到保存文件名
					fileName = newDate.getDate("yyyyMMdd") + (new Random().nextInt(900000) + 100000) + fileName;
					// 如果文件夹不存在就创建
					File file = new File(servletRealPath + relativePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					file = new File(servletRealPath + relativePath, fileName);
					requestFile.transferTo(file);
					if (StringUtils.isBlank(saveFileName)) {
						saveFileName = relativePath + fileName;
					} else {
						saveFileName = saveFileName + "|" + relativePath + fileName;
					}
				}
			}
		}
		return saveFileName;
	}
	public static Boolean checkFileFormat(List<MultipartFile> listFile, String whiteList, String blackList) throws Exception {
		/*
		 * 1.必须进行文件黑名单验证，文件保存类可以不进行白名单验证，黑名单和白名单的验证交给保存前的数据有效性验证
		 * 2.文件名不能用getOriginalFilename()获取的文件名，存储文件的文件名必须是请求的文件名加密后的结果，下载文件名再解密。
		 */
		blackList = blackList + "asp,aspx,asa,asax,ascx,ashx,asmx,cer,aSp,aSpx,aSa,aSax,aScx,aShx,aSmx,cEr,php,php5,php4,php3,php2,pHp,pHp5,pHp4,pHp3,pHp2,html,htm,phtml,pht,Html,Htm,pHtml,jsp,jspa,jspx,jsw,jsv,jspf,jtml,jSp,jSpx,jSpa,jSw,jSv,jSpf,jHtml";
		if (listFile.size() > 0) {
			// 上传文件
			for (int i = 0; i < listFile.size(); i++) {
				MultipartFile requestFile = listFile.get(i);
				if (requestFile != null) {
					// 得到上传文件名
					String fileName = requestFile.getOriginalFilename();
					// 文件上传时，Chrome和IE/Edge对于originalFilename处理不同
					// Chrome 会获取到该文件的直接文件名称，IE/Edge会获取到文件上传时完整路径/文件名					
					// Check for Unix-style path
					int unixSep = fileName.lastIndexOf('/');
					// Check for Windows-style path
					int winSep = fileName.lastIndexOf('\\');
					// Cut off at latest possible point
					int pos = (winSep > unixSep ? winSep : unixSep);
					if (pos != -1)  {
						// Any sort of path separator found...
						fileName = fileName.substring(pos + 1);
					}
					if(fileName.indexOf(".") == -1) {
						return false;
					}
//					fileName = "123852185.html::$data";
//					fileName = "123852185.html";
					String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
					if(StringUtils.isBlank(extension)) {
						return false;
					} else {
						extension = extension.replace(" ", "").toLowerCase();
					}

					if(blackList.toLowerCase().indexOf(extension) > -1) {
						return false;
					}
					if(!checkSuffix(blackList,extension)){
						return false;
					}
					if(StringUtils.isNotBlank(whiteList) && whiteList.toLowerCase().indexOf(extension) == -1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static Boolean checkSuffix(String blackList,String filename){
		for(String str:blackList.replace(".","").toLowerCase(Locale.ROOT).split(",")){
			if(filename.indexOf(str) > -1){
				return false;
			}
		}
		return true;
	}

	public static String delMultiFile(String saveFileName, String delFileName, String servletRealPath, String relativePath) {
		if (StringUtils.isNotBlank(delFileName)) {
			String[] delArr = delFileName.replace("*", "|").split("\\|");
			for (int i = 0; i < delArr.length; i++) {
				// 删除服务器上的文件
				String fileName = delArr[i];
				File file = new File(servletRealPath + relativePath, fileName);
				if (file.exists()) {
					file.delete();
				}
				// 根据删除的文件和之前的文件，获取还未删除的文件名称字符串
				if (saveFileName.indexOf("|" + fileName) != -1) {
					saveFileName = saveFileName.replace("|" + fileName, "");
				} else if (saveFileName.indexOf(fileName + "|") != -1) {
					saveFileName = saveFileName.replace(fileName + "|", "");
				} else {
					saveFileName = saveFileName.replace(fileName, "");
				}
			}
		}
		return saveFileName;
	}
	public static String editFile(String servletRealPath, String saveFile, String onlinePreviewPath) {
		if (StringUtils.isBlank(saveFile)) {
			return "";
		}
		String[] fileList = saveFile.replace("*", "|").split("\\|");
		StringBuilder returnValue = new StringBuilder();
		for (int i = 0; i < fileList.length; i++) {
			String filePath = fileList[i];
			File file = new File(servletRealPath + filePath);
			String fileName = file.getName();
			if (fileName != null && fileName.length() > 14) {
				fileName = file.getName().substring(14, file.getName().length());
			}
			if (file.exists()) {
				if(StringUtils.isNotBlank(onlinePreviewPath)) {
					returnValue.append("<a href=\"file/"+filePath+"\" target=\"blank\" download=\""+fileName+"\">"+fileName+"</a>");
					returnValue.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:;\" onclick='CommonFileView(\"" + filePath + "\");'>预览</a>");
					returnValue.append("&nbsp;&nbsp;&nbsp;&nbsp;<img src='resources/images/delimg.png' width='14' height='14' onclick='delFile(\""+filePath+"\",this)'/>");
					returnValue.append("<br/>");
				} else {
					returnValue.append("<a href=\"file/"+filePath+"\" target=\"blank\" download=\""+fileName+"\">"+fileName+"</a>");
					returnValue.append("&nbsp;&nbsp;&nbsp;&nbsp;<img src='resources/images/delimg.png' width='14' height='14' onclick='delFile(\""+filePath+"\",this)'/>");
					returnValue.append("<br/>");
				}
			} else {
				returnValue.append("<a href=\"file/"+filePath+"\" target=\"blank\" download=\""+fileName+"\">"+fileName+"</a>");
				returnValue.append("&nbsp;&nbsp;&nbsp;&nbsp;文件不存在");
				returnValue.append("&nbsp;&nbsp;&nbsp;&nbsp;<img src='resources/images/delimg.png' width='14' height='14' onclick='delFile(\""+filePath+"\",this)'/>");
				returnValue.append("<br/>");
			}
		}
		return returnValue.toString();
	}
	
	public static String viewFile(String servletRealPath, String saveFile, String onlinePreviewPath, String appPath) {
		return viewFile(servletRealPath, saveFile, onlinePreviewPath, false, "", appPath);
	}
	public static String viewMultiFile(String servletRealPath, String saveFile, String onlinePreviewPath, String fieldName, String appPath) {
		return viewFile(servletRealPath, saveFile, onlinePreviewPath, true, fieldName, appPath);
	}
	public static String viewFile(String servletRealPath, String saveFile, String onlinePreviewPath, Boolean multiFile, String fieldName, String appPath) {
		StringBuilder returnValue = new StringBuilder();
		//多文件增加保存和删除文件隐藏控件
		returnValue.append("<div id=\"div" + fieldName + "\">");
		if(StringUtils.isNotBlank(saveFile)) {
			returnValue.append("<input type=\"hidden\" id=\"save" + fieldName + "\" name=\"save" + fieldName + "\" value=\"" + saveFile + "\"/>");
		} else {
			returnValue.append("<input type=\"hidden\" id=\"save" + fieldName + "\" name=\"save" + fieldName + "\" value=\"\"/>");
		}		
		returnValue.append("<input type=\"hidden\" id=\"del" + fieldName + "\" name=\"del" + fieldName + "\" value=\"\"/>");
		if (StringUtils.isBlank(saveFile)) {
			returnValue.append("</div>");
			return returnValue.toString();
		}
		String[] fileList = saveFile.replace("*", "|").split("\\|");
		for (int i = 0; i < fileList.length; i++) {
			String filePath = fileList[i];
			File file = new File(servletRealPath + filePath);
			String fileName = file.getName();
			if (fileName != null && fileName.length() > 14) {
				fileName = file.getName().substring(14, file.getName().length());
			}
			if (file.exists()) {
				if(StringUtils.isNotBlank(onlinePreviewPath)) {
					returnValue.append("<div><a href=\""+appPath+"/Common/download.html?path=" + DataUtils.getEncode(filePath) + "\" target=\"blank\" download=\""+fileName+"\">"+fileName+"</a>");
					returnValue.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:;\" onclick='CommonFileView(\"" + filePath + "\");'><span style=\"font-weight:bold;color:red\");>预览</span></a>");
					if(multiFile) {
						returnValue.append("&nbsp;&nbsp;&nbsp;&nbsp;<img src='resources/images/delimg.png' width='14' height='14' onclick='delFile(\"" + fieldName + "\", \"" + filePath + "\", this);'/>");
					}
					returnValue.append("</div>");
				} else {
					returnValue.append("<div><a href=\""+appPath+"/Common/download.html?path=" + DataUtils.getEncode(filePath) + "\" target=\"blank\" download=\"" + fileName + "\">" + fileName + "</a>");
					if(multiFile) {
						returnValue.append("&nbsp;&nbsp;&nbsp;&nbsp;<img src='resources/images/delimg.png' width='14' height='14' onclick='delFile(\"" + fieldName + "\", \"" + filePath + "\", this);'/>");
					}
					returnValue.append("</div>");
				}
			} else {
				returnValue.append("<div><a href=\""+appPath+"/Common/download.html?path=" + DataUtils.getEncode(filePath) + "\" target=\"blank\" download=\"" + fileName + "\">" + fileName + "</a>");
				returnValue.append("&nbsp;&nbsp;&nbsp;&nbsp;文件不存在");
				if(multiFile) {
					returnValue.append("&nbsp;&nbsp;&nbsp;&nbsp;<img src='resources/images/delimg.png' width='14' height='14' onclick='delFile(\"" + fieldName + "\", \"" + filePath + "\", this);'/>");
				}
				returnValue.append("</div>");
			}
		}
		returnValue.append("</div>");
		return returnValue.toString();
	}
	
	public static List<Map<String,Object>> StringToList(String fileStr){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> map=new HashMap<String, Object>();
		if(StringUtils.isNotBlank(fileStr)){
			String[] fileList = fileStr.replace("*", "|").replace(",", "|").split("\\|");
			for (int i = 0; i < fileList.length; i++) {
				Long startTs = System.currentTimeMillis()+(new Random().nextInt(900000) + 100000);
				map=new HashMap<String, Object>();
				String filepath = fileList[i];
				String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
				if (filename.length() > 14) {
					filename = filename.substring(14, filepath.substring(filepath.lastIndexOf("/") + 1).length());
				}
				map.put("url", filepath);
			    map.put("name", filename);
			    map.put("id", startTs);
			    list.add(map);
			}
		}
		return list;
	}
	
}
