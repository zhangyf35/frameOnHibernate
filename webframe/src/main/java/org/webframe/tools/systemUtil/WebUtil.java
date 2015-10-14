package org.webframe.tools.systemUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * WEB工具类
 * @description 提供WEB请求的常用方法
 * @author 张永凤
 */
@SuppressWarnings("deprecation")
public class WebUtil {
	
	/**
	 * 获取httpClient
	 * @return
	 */
	public static HttpClient getHttpClient(){
		return new DefaultHttpClient();
	}
	
	/**
	 * 发送post请求
	 * @param uri 请求地址
	 * @param parameters 参数
	 * @param defaultCharset 默认编码
	 * @return 服务器响应的字符串
	 */
	@SuppressWarnings("unchecked")
	public static String sendRequestByPost(String uri, Map<String,String> parameters, String defaultCharset){
		HttpClient httpClient = getHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for(Iterator<?> it = parameters.entrySet().iterator(); it.hasNext();){
			Entry<String, String> entry = (Entry<String, String>) it.next();
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, defaultCharset);
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity reponseEntity = response.getEntity();
				return EntityUtils.toString(reponseEntity, defaultCharset);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 发送post请求(默认编码为UTF-8)
	 * @param uri 请求地址
	 * @param parameters 参数
	 * @return 服务器响应的字符串
	 */
	public static String sendRequestByPost(String uri, Map<String,String> parameters){
		return sendRequestByPost(uri, parameters, "UTF-8");
	}
	
	/**
	 * 发送get请求
	 * @param uri 请求地址
	 * @param parameters 参数
	 * @param defaultCharset 默认编码
	 * @return 服务器响应的字符串
	 */
	public static String sendRequestByGet(String uri,String defaultCharset){
		HttpClient httpClient = getHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity reponseEntity = response.getEntity();
				return EntityUtils.toString(reponseEntity, defaultCharset);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 发送get请求(默认编码为UTF-8)
	 * @param uri 请求地址
	 * @param parameters 参数
	 * @return 服务器响应的字符串
	 */
	public static  String sendRequestByGet(String uri){
		return sendRequestByGet(uri,"UTF-8");
	}

}
