package com.ytxd.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * [类名]<br>
 * Page<br>
 * [功能概要]<br>
 * <br>
 * <br>
 * [変更履歴]<br>
 * 2009-3-16 ver1.00 新建 zhaoxinsheng<br>
 * 
 * @author FOUNDER CORPORATION
 * @version 1.00
 */
public class StringUtil {
    
	private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;
	private static Object initLock = new Object();
	
	static{
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}
	
	/**
     * 判断是否为空，空返回true
     */
    public static boolean isEmpty(String str) {
        if (null == str)
            str = "";
        str = str.trim();
        return "".equals(str);
    }

    /**
     * 判断是否不为空，不空返回true
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 函数名称：arrayToStr 函数功能:把数组转换为字符串列表
     * 
     * @param array1
     *            数组
     * @return 用","隔开的字符串列表
     */
    public static String arrayToStr(String[] array1) {
        String sResult = "";
        if (array1 == null) {
            return sResult;
        }
        for (int i = 0; i < array1.length; i++) {
            if ("".equals(sResult)) {
                sResult = array1[i];
            } else {
                sResult += "," + array1[i];
            }
        }
        return sResult;
    }

    /**
     * 函数名称：arrayToStr 函数功能:把数组转换为字符串列表
     * 
     * @param array1
     *            数组
     * @param splitChar
     *            分隔符
     * @return 用splitChar隔开的字符串列表
     */
    public static String arrayToStr(String[] array1, String splitChar) {
        String sResult = "";
        if (array1 == null) {
            return sResult;
        }
        for (int i = 0; i < array1.length; i++) {
            if ("".equals(sResult)) {
                sResult = array1[i];
            } else {
                sResult += splitChar + array1[i];
            }
        }
        return sResult;
    }

    /**
     * if aInStr is null,then return defaultStr
     * 
     * @param aInStr
     * @param defaultStr
     * @return
     */
    public static String getTrimString(Object aInObj, String defaultStr) {
        if (aInObj == null) {
            return defaultStr.trim();
        } else {
            return aInObj.toString().trim();
        }
    }

    /**
     * default string is blank.
     */
    public static String getTrimString(Object aInobj) {
        return getTrimString(aInobj, "");
    }

    public static String LPadString(String origin, int total, String pad) {
        String temp;
        StringBuffer tempBF = new StringBuffer();
        temp = (origin == null) ? "" : getTrimString(origin);

        for (int i = 0; i < total - getTrimString(origin).length(); i++) {
            tempBF.append(pad);
        }
        tempBF.append(temp);
        return tempBF.toString();

    }

    /**
     * 去除所有空格,包括中间的
     */
    public static String removeAllSpace(String str) {
        return str.replaceAll("\\s+", "");
    }

    /**
     * 折扣显示转换
     */
    public static String discountToString(String discount) {
        if (discount != null && !"".equals(discount)) {
            String value = new Float(discount) * 100 + "";
            return value.substring(0, value.indexOf("."));
        } else {
            return "";
        }
    }

    /**
     * 将页面上折扣转换成存储格式（缩小100倍）
     */
    public static Float toDiscount(Float value) {
        return Float.parseFloat(value * 0.01 + "");
    }

    /**
     * String 转换 数组
     * 
     * @param str
     *            字符串
     * @param sep
     *            分割字符
     * @return
     */
    public static String[] str2Array(String str, String sep) {
        StringTokenizer token = null;
        String[] array = null;

        // check
        if (str == null || sep == null) {
            return null;
        }

        // get string array
        token = new StringTokenizer(str, sep);
        array = new String[token.countTokens()];
        for (int i = 0; token.hasMoreTokens(); i++) {
            array[i] = token.nextToken();
        }

        return array;
    }

    public static String trim(String str) { // 去掉字符串2端的空白字符
        try {
            if (str == null) {
                return null;
            }
            str = str.trim();
            if (str == null) {
                return null;
            }
            return str;
        } catch (Exception e) {
            System.out.println(e);
        }
        return str;
    }

    /**
     * list<String> 转换SQLString<br>
     * 
     * @param inputList
     *            输入的List型
     * @return String
     */
    public static String listToSQLString(List<String> inputList) {
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < inputList.size(); i++) {
            // output.append("'");
        	if(StringUtil.isNotEmpty(inputList.get(i))){
	            output.append(inputList.get(i));
	            // output.append("'");
	            if (i != inputList.size() - 1) {
	                output.append(",");
	            }
        	}
        }
        return output.toString();
    }

    /***
     * 判断字符串是否数字
     * @param str
     * @return flag
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // System.out.println(StringUtil.removeAllSpace(" sss  sss  sss "));
        String value = new Float(1.9) * 100 + "";

        System.out.println(isNumeric(null));

        String prefix = "abc00000001".substring(0, "abc00000001".length() - 8);
        System.out.println(prefix);
        System.out.println("abc00000001".substring("abc00000001".length() - 8));
        System.out.println(Integer.parseInt("abc00000001".substring("abc00000001".length() - 8)));

        System.out.println("  dddd  ".trim());

    }
    
    /**
     * 得到单前系统的本地编码
     * @param encodedStr
     * @return
     */
    public static String getLocalStr(String encodedStr){
    	try{
			if(!(new String(encodedStr.getBytes("UTF-8"))).equals(encodedStr)){
				return new String(encodedStr.getBytes("UTF-8"));
			}else if(!(new String(encodedStr.getBytes("ISO-8859-1"))).equals(encodedStr)){
				return new String(encodedStr.getBytes("ISO-8859-1"));
			}
			return encodedStr;
		} catch (UnsupportedEncodingException e) {
			return encodedStr;
		}
    }

    /**
     * 返回字符串各个字符的首个大写常用汉语拼音
     * @param chars
     * @return
     */
    public static String toShortPinyin(String chars){
    	if(null==chars || "".equals(chars)){
    		return "";
    	}
    	StringBuffer res = new StringBuffer();
    	for(int i=0;i<chars.length();i++){
    		try {
    			if((chars.charAt(i) >= 0x4e00)&&(chars.charAt(i) <= 0x9fbb)){
	    			String[] ss = PinyinHelper.toHanyuPinyinStringArray(chars.charAt(i), format);
	    			if(ss.length>0){
	    				res.append(ss[0].substring(0,1));
	    			}
    			}else{
    				res.append(chars.charAt(i));
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    			res = new StringBuffer();
    			break;
    		}
    	}
		return res.toString();
    }
    
    /**
     * 返回字符串的大写汉语常用拼音
     * @param chars
     * @return
     */
    public static String toFullPinyin(String chars){
    	if(null==chars || "".equals(chars)){
    		return "";
    	}
    	StringBuffer res = new StringBuffer();
    	for(int i=0;i<chars.length();i++){
    		try {
    			if((chars.charAt(i) >= 0x4e00)&&(chars.charAt(i) <= 0x9fbb)){
	    			String[] ss = PinyinHelper.toHanyuPinyinStringArray(chars.charAt(i), format);
	    			if(ss.length>0){
	    				res.append(ss[0]);
	    			}
    			}else{
    				res.append(chars.charAt(i));
    			}	
    		} catch (Exception e) {
    			e.printStackTrace();
    			res = new StringBuffer();
    			break;
    		}
    	}
		return res.toString();
    }
    
    
    /**
     *
     * 输入 0 返回 Y（已删除）
     * 输入 1 返回 N（未删除）
     * @param chars 1显示，0不显示
     * @return
     */
	public static String getYorN(String chars) {
		if (null == chars || "".equals(chars)) {
			return "";
		} else {
			String res = "";
			if (chars.equals("1"))
				res = "N";
			if (chars.equals("0"))
				res = "Y";

			return res;
		}
	}
	
	/**
	 * 返回所输长度的0字符串
	 * @param length
	 * @return
	 */
	public static String getZeroString(Integer length){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<length;i++){
			sb.append("0");
		}
		return sb.toString();
	}
	
	/**
	 * 将
	 * @param filePath
	 * @param info
	 */
	public static void writeFile(String filePath,String info){
		File file = new File(filePath);
		try {
			file.createNewFile();
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8"); 
			PrintWriter pw=new PrintWriter(out);
			pw.println(info);
			pw.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 备份文件
	 * @param filePath
	 */
	public static void bakFile(String filePath){
		File f = new File(filePath);
		String fileName = f.getName();
		filePath = filePath.replace(fileName, DateUtils.getNowDate()+fileName);
		f.renameTo(new File(filePath));
		f.delete();
	}
	
	/**
	 * 清空文件夹下的文件
	 * @param path
	 */
	public static void cleanDir(String path){
		File file = new File(path);
		File [] files = file.listFiles();
		for (File f : files) {
			f.delete();
		}
	}
	
	/**
	 * 获取随即字符串
	 * @param length 长度
	 * @return
	 */
	public static final String randomString(int length) {

        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            synchronized (initLock) {
                if (randGen == null) {
                    randGen = new Random();
                    numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz_" +
                    "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                    //numbersAndLetters = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                }
            }
        }
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
         //randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
        }
        return new String(randBuffer);
}
	
	public static String nullToEmpty(Object obj){
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	} 

}
