package com.tempus.gss.product.tra.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

public class DataConvertUtils{
	
	private static Logger logger = LoggerFactory.getLogger(DataConvertUtils.class);
	
	private static SingletonStrategy singleton = null;
	
	private static XStream xStream = null;
	
	private static ObjectMapper objectMapper = null;
	
	private DataConvertUtils(){}
	
	/**
	 * createSingleton
	 * @return SingletonStrategy
	 */
	private static SingletonStrategy createSingleton() {
        SingletonStrategy result = null;
        String documentFactoryClassName = "com.tempus.gss.product.tra.util.DataConvertUtils";

        try {
            result = (SingletonStrategy) SimpleSingleton.class.newInstance();
        } catch (Exception e) {
            result = new SimpleSingleton();
        }

        result.setSingletonClassName(documentFactoryClassName);
        
        return result;
    }
	
	/**
	 * get DataConvertUtils instance
	 * @return
	 */
	public static synchronized DataConvertUtils getInstance() {
		if (null == singleton) {
			singleton = createSingleton();
		}
		return (DataConvertUtils) singleton.instance();
	}
	
	/**
	 * get xstream instance
	 * @return XStream
	 */
	private XStream getXStream() {
		XStreamMarshaller objMarshaller = new XStreamMarshaller();
		addAlias(objMarshaller);
		objMarshaller.setAutodetectAnnotations(true);
		XStream objXStream = objMarshaller.getXStream();
		objXStream.ignoreUnknownElements();
		xStream =  objXStream;
		return xStream;
	}
	
	/**
	 * 节点重命名
	 * @param objMarshaller
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	private void addAlias(XStreamMarshaller objMarshaller){
		try{
			String f = DataConvertUtils.class.getResource("/xmlalias/xstream-alias.xml").getFile();
			String text = FileUtils.readFileToString(new File(URLDecoder.decode(f,
					"utf-8")), "UTF-8");
			Document doc = DocumentHelper.parseText(text);
			List<Element> elements = doc.selectNodes("//root/alias");
			Map<String, Class<?>> alias = new HashMap<String, Class<?>>();
			for (Element e : elements) {
				String name = e.attribute("name").getStringValue();
				String className = e.attribute("class").getStringValue();
				try{
					alias.put(name,  Class.forName(className));
					objMarshaller.setAliases(alias);
				}catch(ClassNotFoundException ex){
					ex.printStackTrace();
				}
			}
		}catch (Exception e) {
			logger.info("xstream-alias.xml解析异常!" + e.getMessage());
		}
	}
	
	/**
	 * get mapper
	 * @return ObjectMapper
	 */
	private ObjectMapper getObjectMapper(){
		if (null == objectMapper) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper = mapper;
		}
		
		return objectMapper;
	}
	
	/**
	 * object to xml
	 * @param obj
	 * @return String
	 */
	public String toXML(Object obj){
		return getXStream().toXML(obj);
	}
	
	/**
	 * object to xml
	 * @param obj
	 * @return String
	 */
	public String toNotFormatXML(Object obj) {
		XStream xstream = getXStream();
		Writer writer = new StringWriter();
		xstream.marshal(obj, new CompactWriter(writer));
		return writer.toString();
	}
	
	
	/**
	 * xml to object
	 * @param xml
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromXML(String xml){
		return (T)getXStream().fromXML(xml);
	}
	
	
	/**
	 * object to json
	 * @param obj
	 * @return String
	 * @throws JsonProcessingException
	 */
	public String toJson(Object obj) throws JsonProcessingException{
		ObjectMapper mapper = getObjectMapper();
		return mapper.writeValueAsString(obj);
	}
	
	/**
	 * 从json字符串中获取某个节点的值
	 * @param json
	 * @param fieldName
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public String getFieldValueFromJson(String json, String fieldName)
			throws JsonProcessingException, IOException {
		ObjectMapper mapper = getObjectMapper();
		JsonNode jsonNode = mapper.readTree(json);
		String fieldValue = "";
		if (jsonNode.has(fieldName)) {
			fieldValue = jsonNode.get(fieldName).asText();
		}
		return fieldValue;
	}
	
	/**
	 * json to object
	 * @param json
	 * @param cls
	 * @return Object
	 * @throws Exception
	 */
	public <T> T fromJson(String json,Class<T> cls) throws Exception{
		ObjectMapper mapper = getObjectMapper();
		return (T)mapper.readValue(json, cls);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T fromJson(String json,TypeReference<T> typeReference) throws Exception{
		ObjectMapper mapper = getObjectMapper();
		return (T)mapper.readValue(json, typeReference);
	}
	
	/**
	 * 从xml字符串中获取某个节点的值
	 * @param xml
	 * @param fieldName
	 * @return
	 * @throws DocumentException
	 */
	public String getFieldValueFromXml(String xml, String fieldName)
			throws DocumentException {
		Document doc = DocumentHelper.parseText(xml);
		Node node = doc.selectSingleNode(fieldName);
		return null != node ? node.getText() : "";
	}
	
	@SuppressWarnings("unchecked")
	public <T> T fromXml(String xml,Class<T> cls){
		XStreamMarshaller objMarshaller = new XStreamMarshaller();
		XStream objXStream = objMarshaller.getXStream();
		objXStream.autodetectAnnotations(true);
		objXStream.ignoreUnknownElements();
		objXStream.processAnnotations(cls);
		return (T)objXStream.fromXML(xml);
	}
}
