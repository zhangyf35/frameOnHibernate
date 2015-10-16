package org.webframe.tools.json.aop;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webframe.tools.json.FilterAnnotationReader;
import org.webframe.tools.json.JsonParser;
import org.webframe.tools.json.HttpServiceContent;
import org.webframe.tools.json.annotation.JsonFilterAuto;
import org.webframe.tools.json.annotation.JsonFilterProperties;
import org.webframe.tools.json.annotation.Jsonp;

/**
 * aop-Controller<br>
 * 切入所有controller，当controller需要json转换，自动提供转换<br>
 * 请在spring-config.xml中配置该核心aop，通知类型为around<br>
 * 如果@JsonFilterProperties和@JsonFilterAuto都注解了，默认会按照@JsonFilterProperties过滤
 * @author 张永葑
 *
 */
public class JsonFilterInterceptor {
	
	/**
	 * 环绕通知<br>
	 * 如果需要json转换则该类直接切断controller方法，将json结果写到前台<br>
	 * 如果不需要json转换，则会继续交给spring处理
	 * @param pjp
	 * @return Object
	 * @throws Throwable
	 */
    public Object jsonFilterAround(ProceedingJoinPoint pjp) throws Throwable{
    	//获取方法
    	MethodSignature signature = (MethodSignature) pjp.getSignature();
    	Method method = signature.getMethod();
    	//得到返回对象
    	Object object = pjp.proceed();//执行该方法  
    	//判断是否返回为json
    	if(object != null) {
	    	if(method.isAnnotationPresent(ResponseBody.class)) {
	    		HttpServletRequest request = HttpServiceContent.getRequest();
				HttpServletResponse response = HttpServiceContent.getResponse();
				//如果返回的不是字符串
	    		if (! object.getClass().getSimpleName().equals("String")) {
	    			//如果是发送jsonp
	    			if(method.isAnnotationPresent(Jsonp.class)) {
	    				String callbackParam = request.getParameter(method.getAnnotation(Jsonp.class).callbackParamName());
	    				//自定义过滤
	    				if (method.isAnnotationPresent(JsonFilterProperties.class)) {
	        				Map<Class<?>, Set<String>> map = FilterAnnotationReader.getJsonFilters(method);
	        				JsonParser.outFilterJsonP(object, map, request, response, callbackParam);
	        			}
	    				//自动过滤
	    				else if(method.isAnnotationPresent(JsonFilterAuto.class)) {
	        				JsonParser.outAutoFilterJsonP(object, request, response, callbackParam);
	        			}
	    			}
	    			//如果是发送json
	    			else {
	    				// 定义过滤
	    				if (method.isAnnotationPresent(JsonFilterProperties.class)) { 
	        				Map<Class<?>, Set<String>> map = FilterAnnotationReader.getJsonFilters(method);
	        				JsonParser.outFilterJsonString(object, map, request, response);;
	        			}
	    				//自动过滤
	    				else if(method.isAnnotationPresent(JsonFilterAuto.class)) { 
	        				JsonParser.outAutoFilterJsonString(object, request, response);
	        			}
	    			}
	    		} 
	    		//如果返回的是字符串
	    		else {
	    			JsonParser.outString(String.valueOf(object), response);
	    		}
	    		return null;
	    	}
    	}
    	return object;
    }
    
}
