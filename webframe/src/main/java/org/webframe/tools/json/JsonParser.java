package org.webframe.tools.json;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;


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
	 * 如果使用字段过滤注解就必须使用此方法才能有效过滤字段
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object, Map<Class<?>, Set<String>> map) {
		JsonConfig config = currentConfig();
		config.setJsonPropertyFilter(new JsonFilter(map));
		return JSONSerializer.toJSON(object, config).toString();
	}
	
	/**
	 * 自动过滤关联的字段
	 * @param object
	 * @return
	 */
	
	public static String autoFilterToJsonString(Object object) {
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
	 * 前端callBack函数名为jsonpCallback
	 * @param object
	 * @param response
	 * @return
	 */
	/*public static void outFilterJsonP(Object object,HttpServletRequest request, HttpServletResponse response) {
		JsonConfig config = currentConfig();
		Map<String,Object> map = FilterAnnotationReader.getJsonFilters();
		config.setJsonPropertyFilter(new JsonFilter(map));
		String resultJSON = JSONSerializer.toJSON(object, config).toString();
		setResponseParams(response);
		outString(resultJSON, request, response);
	}
	
	*//**
	 * 前端callBack函数名为jsonpCallback
	 * @param object
	 * @param response
	 * @return
	 *//*
	public static void outAutoFilterJsonP(Object object,HttpServletRequest request, HttpServletResponse response) {
		String resultJSON = autoFilterToJsonString(object);
		setResponseParams(response);
		outString(resultJSON, request, response);
	}
	
	
	
	
	
	private static void setResponseParams( HttpServletResponse response) {
		response.setContentType("text/plain");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
	}
	
	private static void outString(String resultJSON, HttpServletRequest request, HttpServletResponse response) {
		try {
			String jsonpCallback = request.getParameter("jsonpCallback");//客户端请求参数
			PrintWriter out = response.getWriter();
	        out.write(jsonpCallback+"("+resultJSON+")");//返回jsonp格式数据  
	        out.flush();
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
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
