package org.webframe.tools.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;






import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

/**
 * json处理类
 * @author 张永凤
 *
 */
public class JsonParser {
		
	/**
	 * 得到过滤的json字符串
	 * @param object
	 * @param map
	 * @return json字符串
	 */
	private static String getFilterJsonString(Object object, Map<Class<?>, Set<String>> map) {
		JsonConfig config = currentConfig();
		config.setJsonPropertyFilter(new JsonFilter(map));
		return JSONSerializer.toJSON(object, config).toString();
	}
	
	/**
	 * 得到自动过滤的json字符串
	 * @param object
	 * @return json字符串
	 */
	private static String getAutoFilterJsonString(Object object) {
		JsonConfig config = currentConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object cla, String field, Object fieldType) {
				if(TypeJudger.isFetchLazy(cla.getClass(), field))
					return true;
				return false;
			}
		});
		return JSONSerializer.toJSON(object, config).toString();
	}
	
	/**
	 * 过滤指定的字段
	 * @param object 要转换的对象
	 * @param map 要过滤的字段树
	 * @param request 请求
	 * @param response 响应
	 */
	public static void outFilterJsonString(Object object, Map<Class<?>, Set<String>> map, HttpServletRequest request, HttpServletResponse response) {
		String resultJsonString = getFilterJsonString(object, map);
		setJsonResponseParams(response);
		outString(resultJsonString, response);
	}
	
	/**
	 * 自动过滤关联的字段
	 * @param object 要转换的对象
	 * @param request 请求
	 * @param response 响应
	 */
	public static void outAutoFilterJsonString(Object object, HttpServletRequest request, HttpServletResponse response) {
		String resultJsonString = getAutoFilterJsonString(object);
		setJsonResponseParams(response);
		outString(resultJsonString, response);
	}
	
	/**
	 * jsonp,前端callBack函数名为jsonpCallback
	 * @param object 要转换的对象
	 * @param map 要过滤的字段树
	 * @param request 请求
	 * @param response 响应
	 * @param jsonpCallback 回调函数名
	 */
	public static void outFilterJsonP(Object object, Map<Class<?>, Set<String>> map, HttpServletRequest request, HttpServletResponse response, String jsonpCallback) {
		String resultJsonString = getFilterJsonString(object, map);
		setJsonpResponseParams(response);
		outString(jsonpCallback+"("+resultJsonString+")", response);
	}
	
	/**
	 * jsonp,前端callBack函数名为jsonpCallback
	 * @param object 要转换的对象
	 * @param request 请求
	 * @param response 响应
	 * @param jsonpCallback 回调函数名
	 */
	public static void outAutoFilterJsonP(Object object,HttpServletRequest request, HttpServletResponse response, String jsonpCallback) {
		String resultJsonString = getAutoFilterJsonString(object);
		setJsonpResponseParams(response);
		outString(jsonpCallback+"("+resultJsonString+")", response);
	}
	
	/**
	 * 设置jsonp  responseMIME
	 * @param response 响应
	 */
	private static void setJsonpResponseParams(HttpServletResponse response) {
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
	}
	
	/**
	 * responseMIME
	 * @param response 响应
	 */
	private static void setJsonResponseParams(HttpServletResponse response) {
		response.setContentType("text/plain;charset=UTF-8");
	}
	
	/**
	 * 输出字符串
	 * @param resultJsonString json字符串
	 * @param response 响应
	 */
	public static void outString(String resultJsonString, HttpServletResponse response) {
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
	private static JsonConfig currentConfig(){
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
