package com.tempus.gss.product.tra.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrainHttpClient {
	private static Logger logger = LoggerFactory.getLogger("log.sp");

	public static String trainSearchUrl = PropertiesUtils.get("train.searchUrl");
	public static String trainOrderUrl = PropertiesUtils.get("train.orderUrl");
	public static String trainChannel = PropertiesUtils.get("train.channel");//渠道号
	public static String key =  PropertiesUtils.get("train.key");//md5key
	
	
	private static TrainHttpClient client;

	public static TrainHttpClient getInstance() {
		if (client == null) {
			client = new TrainHttpClient();
		}
		return client;
	}

	public String getResFortbe(Map<String,String> map,String url) throws Exception{
		StringBuilder respStr = new StringBuilder();
		//初始化参数
		if(map!=null){
			map.put("channel",Util.isNull(TrainHttpClient.trainChannel)?"F3F56DC1830C245D":TrainHttpClient.trainChannel);
		}
		String finalUrl = sort(map,url);
		logger.info("创旅加密后入参为:" + finalUrl);
		// 构造HttpClient 的实例
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpGet post = new HttpGet(finalUrl);
			CloseableHttpResponse response = client.execute(post);
			HttpEntity res = response.getEntity();
			return EntityUtils.toString(res, "UTF-8");
		} catch (Exception e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			logger.info(e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != client) {
				client.close();
			}
		}
		return respStr.toString();
	}
	
	public String sort(Map<String,String> map,String url)throws Exception{
		String str = "";
		Object[] arr = map.keySet().toArray();
		Arrays.sort(arr);
		for (Object object : arr) {
			url+=(object+"="+map.get(object)+"&");
			str+=(object.toString().toLowerCase()+"="+map.get(object)+"&");
		}
		String tk = Util.isNull(TrainHttpClient.key)?"0f92ed29cb0c4c08a225546302fcbb43":TrainHttpClient.key;
		str+="md5key="+tk;
		logger.info("创旅加密前入参为："+str);
		url+="sign="+Md5Encrypt.md(str).toUpperCase();
		return url;
	}
	
	
	public List<NameValuePair> getNameValuePair(Map<String,String> map)throws Exception{
		List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
		String channel = Util.isNull(TrainHttpClient.trainChannel)?"F3F56DC1830C245D":TrainHttpClient.trainChannel;
		map.put("channel", channel);
		Object[] objArr = map.keySet().toArray();
		Arrays.sort(objArr);
		String str = "";
		for (Object object : objArr) {
			nvps.add(new BasicNameValuePair(object.toString(),map.get(object)));
			str+=(object.toString().toLowerCase()+"="+map.get(object)+"&");
		}
		String tk = Util.isNull(TrainHttpClient.key)?"0f92ed29cb0c4c08a225546302fcbb43":TrainHttpClient.key;
		str+="md5key="+tk;
		logger.info("创旅Pos请求加密前入参为:" + str);
		nvps.add(new BasicNameValuePair("sign",Md5Encrypt.md(str).toUpperCase()));
		return nvps;
	}

	public String getResForPost(Map<String,String> map,String url) throws Exception{
		StringBuilder respStr = new StringBuilder();
		logger.info("创旅Pos请求URL为:" + url);
		// 构造HttpClient 的实例
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(url);
	        post.setEntity(new UrlEncodedFormEntity(getNameValuePair(map),"UTF-8"));  
			CloseableHttpResponse response = client.execute(post);
			HttpEntity res = response.getEntity();
			return EntityUtils.toString(res, "UTF-8");
		} catch (Exception e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			logger.info(e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != client) {
				client.close();
			}
		}
		return respStr.toString();
	}
}
