package com.tempus.gss.product.hol.api.util;

import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.dom4j.DocumentHelper;

/**
 * 类 名 称： DocumentUtil 类 描 述： 解析XML辅助类
 * 
 * @author juan.yin 创建时间： 2014年7月31日 下午5:33:11
 */
public class DocumentUtil {

	/**
	 * 根据文件名获取资源下的Doc文件
	 * 
	 * @param fileName 文件名
	 * @return Document对象
	 */
	public static Document getDocumentByFileName(String fileName) {
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(DocumentUtil.class.getResourceAsStream(fileName));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}
	public static Document getDocumentByFileName2(String fileName) {
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			//读取文件
			doc = reader.read(DocumentUtil.class.getClassLoader().getResourceAsStream(fileName));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}
	/**
	 * 方法描述: 将xml格式的字符串转换成document对象 作 者： liguihua 日 期： 2014年7月31日-下午5:20:55
	 * 
	 * @param xml
	 * @return 返回类型： Document
	 */
	public static Document getDocument(String xml) {
		if (null == xml || "".equals(xml.trim())) {
			return null;
		}
		Document doc = null;
		try {
			StringReader reader = new StringReader(xml);
			InputSource input = new InputSource(reader);
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(input);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 方法描述: 从指定路径读取xml文件并封装成document对象 作 者： liguihua 日 期： 2014年7月31日-下午5:21:09
	 * 
	 * @param path
	 * @return 返回类型： Document
	 */
	public static Document getDocumentByPath(String path) {
		Document doc = null;
		try {
			File file = new File(path);
			SAXReader sax = new SAXReader();
			doc = sax.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 方法描述: 从指定路径读取xml文件并封装成document对象 作 者： liguihua 日 期： 2014年7月31日-下午5:20:33
	 * 
	 * @param path
	 * @return 返回类型： String
	 */
	public static String getDocumentXmlByPath(String path) {
		Document doc = getDocumentByPath(path);
		if (doc != null) {
			return doc.asXML();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> fillInMap(Element e) {
		Map<String, String> map = new HashMap<String, String>();
		if (e == null) {
			return map;
		}
		Iterator<Element> elist = e.elementIterator();
		while (elist.hasNext()) {
			Element e1 = elist.next();
			if (e1.getText() == null || "null".equals(e1.getText().trim())) {
				map.put(e1.getName(), "");
			} else {
				map.put(e1.getName(), e1.getText());
			}
		}
		return map;
	}
	
	/**
	 * 解析xml
	 * @param text
	 * @return
	 */
	public static Document parseText(String text){
		Document document = null;
		try {
			document = DocumentHelper.parseText(text);
		} catch (DocumentException e) {
			return null;
		}
		return document;
	}

}
