package com.ytxd.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;



public class ReadFile {
	public static List<String> readFile(String filePath) throws IOException{
		String path = ReadFile.class.getClass().getResource("/").getPath().replaceFirst("/", "");
		System.out.print(path);
		StringBuffer sb=new StringBuffer();
		FileInputStream fin = new FileInputStream(filePath);
        BufferedInputStream bis=new BufferedInputStream(fin);
        int p=(bis.read()<<8)+bis.read();
        String code=null;
        switch (p) { //判断文件编码格式，防止中午乱码
        case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		default:
			code = "gbk";
		}
        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fis,code);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";
        String reField="";
        int i=0;
        while((strTmp = buffReader.readLine())!=null){
        	if(i==0){
        		reField=strTmp;
        		i++;
        	}else{
        		sb.append(strTmp);
        	}
        	
        }
        buffReader.close();
        reader.close();
        fis.close();
        fin.close();
        List<String> list =new ArrayList<String>();
        list.add(reField);
        list.add(sb.toString());
		return list;
	}
	public static String getValue(Object dto, String name) {
    	Method[] m = dto.getClass().getMethods();
		for (int i = 0; i < m.length; i++) {
			if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
				Object result = null;
				try {
					result = m[i].invoke(dto);
				} catch (Exception ex) {
				}
				if (result == null)
					return "";
				else
					return result.toString();
			}
		}
		return "";
	}
}
