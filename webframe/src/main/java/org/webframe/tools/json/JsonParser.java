package org.webframe.tools.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.webframe.tools.json.config.FilterAnnotationReader;
import org.webframe.tools.json.propertyFilter.JsonPropertyFilter;
import org.webframe.tools.json.util.TypeJudger;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * json输出类
 * @author 张永凤
 *
 */
public class JsonParser {
	
	/**
	 * 待转换对象
	 */
	private Object object;
	
	/**
	 * 过滤注解读取对象
	 */
	private FilterAnnotationReader annotationReader;
	
	/**
	 * 请求对象
	 */
	private HttpServletRequest request;
	
	/**
	 * 相应对象
	 */
	private HttpServletResponse response;
	
	/**
	 * 构造器
	 * @param object
	 * @param annotationReader
	 * @param request
	 * @param response
	 */
	public JsonParser(Object object, FilterAnnotationReader annotationReader,
			HttpServletRequest request, HttpServletResponse response) {
		this.object = object;
		this.annotationReader = annotationReader;
		this.request = request;
		this.response = response;
	}
	
	/**
	 * 输出json
	 */
	public void outJson() {
		JsonConfig config = getCurrentConfig();
		if(annotationReader.hasJsonAutoFilterLazy()) {
			config.setJsonPropertyFilter(new JsonPropertyFilter(annotationReader));
		}
		outjsonString(config);
	}
	
	/**
	 * 得到过滤的json字符串
	 * @param propertyFilter
	 * @return
	 */
	public String getJsonString(JsonConfig config) {
		return JSONSerializer.toJSON(object, config).toString();
	}
	
	/**
	 * json
	 * @param propertyFilter
	 */
	private void outjsonString(JsonConfig config) {
		String resultJsonString = getJsonString(config);
		if(annotationReader.isJsonp()) {
			String jsonpCallback = request.getParameter(annotationReader.getJsonpCallback());
			setJsonpResponseParams();
			outString(jsonpCallback+"("+resultJsonString+")");
		} else {
			setJsonResponseParams();
			outString(resultJsonString);
		}
		System.out.println(resultJsonString);
	}
	
	/**
	 * 设置jsonp  responseMIME
	 * @param response 响应
	 */
	private void setJsonpResponseParams() {
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
	}
	
	/**
	 * responseMIME
	 * @param response 响应
	 */
	private void setJsonResponseParams() {
		response.setContentType("text/plain;charset=UTF-8");
	}
	
	/**
	 * 输出字符串
	 * @param resultJsonString json字符串
	 * @param response 响应
	 */
	public void outString(String resultJsonString) {
		try {
			PrintWriter out = response.getWriter();
	        out.write(resultJsonString);//返回jsonp格式数据  
	        out.flush();
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 配置jsonLib
	 * @return Json配置对象
	 */
	private JsonConfig getCurrentConfig(){
		JsonConfig config=new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		//过滤hibernate懒加载
		config.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
		//date格式化
		config.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			public Object processObjectValue(String arg0, Object date, JsonConfig arg2) {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			}
			public Object processArrayValue(Object date, JsonConfig arg1) {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			}
		});
		//设置数字默认值
		TypeJudger.setNumberDefaultValue(config);
		//防止自包含
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		return config;
	}
}
