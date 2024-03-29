package com.ytxd.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlParseCurrency {

	String path;
	String elementName = "";
	String attributeName = "name";
	String attributeValue;

	/**
	 * @param root
	 *            xml的二级节点(不必须设置)
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	/**
	 * @param attributeName
	 *            xml二级节点的属性名称(不必须设置)
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @param attributeValue
	 *            xml二级节点的属性值(必须设置)
	 */
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	/**
	 * @param path
	 *            xml的绝对路径,一般为this.getClass().getClassLoader().getResource("").
	 *            getPath()+*.xml
	 */
	public void setPath(String path) {
		this.path = path;
	}

	@SuppressWarnings("unchecked")
	public String getValue(String name) {

		String result = "";
		try {
			// 创建SAXReader
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(path));
			// 获取根节点

			Element root = document.getRootElement();
			List<Element> elements = new ArrayList<Element>();
			if(elementName.equals("")){
				elements = root.elements();
			}else{
				elements = root.elements(elementName);
			}
			for (Element element : elements) {
				if (element.attribute(attributeName)!=null && element.attribute(attributeName).getStringValue().equals(attributeValue)) {
					result = element.element(name)==null?"":element.element(name).getTextTrim();
					break;
				}
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	public boolean setValue(String name,String value) {
		System.out.println("set value");
		try {
			// 创建SAXReader
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(path));
			// 获取根节点

			Element root = document.getRootElement();
			List<Element> elements = null;
			if(elementName.equals("")){
				elements = root.elements();
			}else{
				elements = root.elements(elementName);
			}
			for (Element element : elements) {
				/*if (element.attribute(attributeName).getStringValue().equals(attributeValue)) {*/
				if (element.attribute(attributeName)!=null && element.attribute(attributeName).getStringValue().equals(attributeValue)) {
					//System.out.println("set"+element.element(name).getTextTrim()+"="+value);					
					if(element.element(name)!=null){
						element.element(name).setText(value);
						System.out.println("set ok");
						break;
					}
				}
			}
			//指定文件输出的位置

	        FileOutputStream out =new FileOutputStream(path);
	        // 指定文本的写出的格式：

	        OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
	        format.setEncoding("UTF-8");
	        //1.创建写出对象
	        XMLWriter writer=new XMLWriter(out,format);
	        //2.写出Document对象
	        writer.write(document);
	        //3.关闭流

	        writer.close();

		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}

}