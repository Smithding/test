package com.tempus.gss.product.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * HttpClient工具类
 */
public class HttpClientUtil {
    private static final transient Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

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
                log.info(result);
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
}
