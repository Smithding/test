package com.tempus.gss.product.hol.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tempus.gss.pay.util.MD5Util;
import com.tempus.gss.pay.util.WebClientDevWrapper;
import com.tempus.gss.util.JsonUtil;
import com.thoughtworks.xstream.XStream;

/**
 * HttpClient工具类
 * Created by luofengjie on 2017/3/24.
 */
@Component
public class HttpClientUtil {
	
	/*@Autowired
	static Environment env;*/
	
	@Value("${tc.customHeader.access}")
	private  String access;
	@Value("${tc.accessId}")
	private  String accessId;
	@Value("${tc.customHeader.security}")
	private  String security;
	@Value("${tc.securityId}")
	private  String securityId;

    protected final static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
   
    
    /*public HttpClientUtil(@Value("${tc.customHeader.access}")String accessq, @Value("${tc.accessId}")String accessIdq, @Value("${tc.customHeader.security}")String securityq, @Value("${tc.securityId}")String securityIdq){
    	access = accessq;
    	accessId =accessIdq;
    	security = securityq;
    	securityId = securityIdq;
    }*/
    
    /**
     * get请求
     * @param url
     * @return
     * @throws Exception
     */
    public static String executeGet(String url){
        BufferedReader in = null;

        String content = null;
        try {
            // 定义HttpClient
            HttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            content = sb.toString();
        } finally {
            if (in != null) {
                try {
                    in.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return content;
        }
    }

    /**
     * 发起post请求
     *
     * @param url
     * @param reqJsonStr 入参为json串
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T doJsonPost(String url, String reqJsonStr, Class<T> c) {
        T t = null;
        HttpClient client = null;
        try {
            if (url.startsWith("https")) {
                client = new DefaultHttpClient();
                client = WebClientDevWrapper.wrapClient(client);
            } else {
                client = HttpClients.createDefault();
            }
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(reqJsonStr,
                    "application/json", "utf-8");// 指定请求头
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Accept-Charset", "utf-8");
            HttpResponse res = client.execute(httpPost);// 发送请求
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity(),"UTF-8");
                log.info("接口请求返回结果:"+result);
                t = JSONObject.parseObject(result, c);
            }

        } catch (Exception e) {
            log.info("doJsonPost请求异常");
            e.printStackTrace();
        }finally {
            client.getConnectionManager().shutdown();// 关闭连接
        }
        return t;
    }

    /**
     * 发起post请求
     *
     * @param url
     * @param reqXmlStr 入参为xml串
     * @param xStream   该工具主要用于xml报文与对象之间相互转换
     * @param c
     * @return
     */
    public static <T> T doXMLPost(String url, String reqXmlStr, XStream xStream, String encoding, Class<T> c) {
        T t = null;
        try {
            HttpClient client = null;
            if (url.startsWith("https")) {
                client = new DefaultHttpClient();
                client = WebClientDevWrapper.wrapClient(client);
            } else {
                client = HttpClients.createDefault();
            }
            HttpPost post = new HttpPost(url);

            StringEntity s = new StringEntity(reqXmlStr, encoding);//设置编码格式
            s.setContentType("application/xml");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String entity = EntityUtils.toString(res.getEntity(), encoding);
                //获取xml响应报文信息
                String responseXml = entity.split("\\r\\n")[1];
                log.info("响应报文:" + responseXml);
                if (!StringUtils.isEmpty(responseXml)) {
                    if (xStream == null) {
                        xStream = new XStream();
                    }
                    t = (T) xStream.fromXML(responseXml);
                }
            }
        } catch (Exception e) {
            log.info("doXMLPost请求异常");
            e.printStackTrace();
        }
        return t;
    }
    
    /**
     * TC业务发起post请求
     *
     * @param url
     * @param reqJsonStr 入参为json串
     * @param payment 1：预付； 2：现付 
     * @return
     */
    public String doTCJsonPost(String url, String reqJsonStr) {
    	/*ApplicationContext pp=new GenericApplicationContext();
    	Environment environment = pp.getEnvironment();
    	String property = environment.getProperty("tc.customHeader.access");
    	System.out.println("property: "+property);*/
    	HttpClient client =null;
    	HttpPost httpPost = null;
    	HttpResponse res = null;
    	RequestConfig requestConfig = null;
    	//CloseableHttpResponse response = null;
    	try {
            if (url.startsWith("https")) {
                client = new DefaultHttpClient();
                client = WebClientDevWrapper.wrapClient(client);
            } else {
                client = HttpClients.createDefault();
                requestConfig = RequestConfig.custom().setConnectTimeout(180000).setConnectionRequestTimeout(180000).setSocketTimeout(180000).build();
            }
            
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-type","application/x-www-form-urlencoded");
            
          /*  String accessId = "DZSSZSTBGJSYFWGFYXGSTDI";
            String securityId = "a76ec9bf-ba68-40f5-8cf2-6fd90453001e";
		    httpPost.setHeader("X-ACCESS-ID",accessId);*/
		/*    
		    String access = PropertyUtil.getProperty("tc.customHeader.access");
		    String accessId = PropertyUtil.getProperty("tc.accessId");
           
            
            String security = PropertyUtil.getProperty("tc.customHeader.security");
            String securityId = PropertyUtil.getProperty("tc.securityId");*/
            
		   /* client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 120000);
		    client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 120000);*/
             httpPost.setHeader(access,accessId);
		     TreeMap<String, String> tmp = new TreeMap<String, String>(); 
		     
		     JSONObject jsonObj = JSON.parseObject(reqJsonStr);
		     for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
		    	 if("java.lang.String".equals(entry.getValue().getClass().getName())){
		    		 tmp.put(entry.getKey(), String.valueOf(entry.getValue()));
		    	 }else{
		    		 tmp.put(entry.getKey(), JsonUtil.toJson(entry.getValue()));
		    	 }
		        }
		     List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
		     for (Map.Entry<String, String> entry : tmp.entrySet()) {
		    	 	
	                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	            }
	            UrlEncodedFormEntity formEntity = null;
	            formEntity = new UrlEncodedFormEntity(parameters,"utf-8");
	            httpPost.setEntity(formEntity);
	            
	            StringBuffer queryStr = new StringBuffer("");
			    Iterator<String> each = tmp.keySet().iterator(); 
			    while (each.hasNext()){
			    	 String key= (String)each.next();
			    	 if(queryStr.toString().equals("")){
			    		 queryStr.append(key+"="+tmp.get(key));
			    	 }else{
			    		    queryStr.append(key+"="+tmp.get(key));
			    	   }
			    }
			    String singPwdMing = accessId+securityId+ queryStr.toString();
			    String stringPwd = MD5Util.MD5Encode(singPwdMing, "utf-8");
			    //httpPost.setHeader(security,stringPwd);
			    httpPost.setHeader(security,stringPwd);
			    
	            res = client.execute(httpPost);// 发送请求
	           /*if(res!=null)
	           {
	        	   log.debug("请求同程酒店接口请求返回结果:"+res.getEntity());
	           }*/
	            
	            
	            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	               String result = EntityUtils.toString(res.getEntity(),"UTF-8");
	                //log.info("接口请求返回结果:"+result);
	                return result;
	            	/*HttpEntity entity = res.getEntity();
	            	StringBuffer returnMessage = new StringBuffer();
	            	if (entity != null) {
	            		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
	            		String lineMessage;
	            		while ((lineMessage = bufferedReader.readLine()) != null) {
	            			returnMessage.append(lineMessage);
	            		}
	            		bufferedReader.close();
	            	}
	            	String result = returnMessage.toString();
	            	log.info("接口请求返回结果:"+result);
	            	return result;*/
	            }
	        }catch(ConnectionClosedException e){
	        	 try {
					res = client.execute(httpPost);  // 发送请求
					if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			               String result = EntityUtils.toString(res.getEntity(),"UTF-8");
			               // log.info("接口请求返回结果:"+result);
			                return result;
					}
				} catch (ClientProtocolException e1) {
					log.error("doJsonPost请求异常",e1);
				} catch (IOException e1) {
					log.error("doJsonPost请求异常",e1);
				}
	        }catch (Exception e) {
	            log.error("doJsonPost请求异常",e);
	        }
        	finally {
	            client.getConnectionManager().shutdown();// 关闭连接
	        }
	        return null;
    }

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	 
}
