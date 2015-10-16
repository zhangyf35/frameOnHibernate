package org.webframe.tools.json.interceptor;

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
import org.webframe.tools.json.SysContent;
import org.webframe.tools.json.annotation.JsonFilterAuto;
import org.webframe.tools.json.annotation.JsonFilterProperties;
import org.webframe.tools.json.annotation.Jsonp;

public class JsonFilterInterceptor {
	
    public Object jsonFilterAround(ProceedingJoinPoint pjp) throws Throwable{
    	//获取方法
    	MethodSignature signature = (MethodSignature) pjp.getSignature();
    	Method method = signature.getMethod();
    	
    	//得到返回对象
    	Object object = pjp.proceed();//执行该方法  
    	
    	//判断是否返回为json
    	if(method.isAnnotationPresent(ResponseBody.class)) {
    		HttpServletRequest request = SysContent.getRequest();
			HttpServletResponse response = SysContent.getResponse();
    		if (! object.getClass().getSimpleName().equals("String")) {
    			if(method.isAnnotationPresent(Jsonp.class)) {
    				String callbackParam = request.getParameter(method.getAnnotation(Jsonp.class).callback());
    				if(method.isAnnotationPresent(JsonFilterAuto.class)) { //自动过滤
        				JsonParser.outAutoFilterJsonP(object, request, response, callbackParam);
        			} else if (method.isAnnotationPresent(JsonFilterProperties.class)) { // 定义过滤
        				Map<Class<?>, Set<String>> map = FilterAnnotationReader.getJsonFilters(method);
        				JsonParser.outFilterJsonP(object, map, request, response, callbackParam);
        			}
    			} else {
    				if(method.isAnnotationPresent(JsonFilterAuto.class)) { //自动过滤
        				JsonParser.outAutoFilterJsonString(object, request, response);
        			} else if (method.isAnnotationPresent(JsonFilterProperties.class)) { // 定义过滤
        				Map<Class<?>, Set<String>> map = FilterAnnotationReader.getJsonFilters(method);
        				JsonParser.outFilterJsonString(object, map, request, response);;
        			}
    			}
    		} else {
    			JsonParser.outString(String.valueOf(object), request, response);
    		}
    	}
    	return null;
    }
    
}
