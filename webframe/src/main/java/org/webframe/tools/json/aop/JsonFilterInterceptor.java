package org.webframe.tools.json.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webframe.tools.json.JsonParser;
import org.webframe.tools.json.config.FilterAnnotationReader;
import org.webframe.tools.json.context.HttpServiceContent;

/**
 * aop-Controller<br>
 * 切入所有controller，当controller需要json转换，自动提供转换<br>
 * 请在spring-config.xml中配置该核心aop，通知类型为around<br>
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
    	Method method = ((MethodSignature) pjp.getSignature()).getMethod();
    	//得到返回对象
    	Object object = pjp.proceed();//执行该方法  
    	//判断是否返回为json
    	if(object != null) {
	    	if(method.isAnnotationPresent(ResponseBody.class)) {
	    		HttpServletRequest request = HttpServiceContent.getRequest();
				HttpServletResponse response = HttpServiceContent.getResponse();
				long x = System.currentTimeMillis();
				//如果返回的是字符串
	    		if (object.getClass().getSimpleName().equals("String")) {
	    			JsonParser.outString(String.valueOf(object), response);
	    		}
	    		//如果返回的不是字符串
	    		else {
	    			FilterAnnotationReader annotationReader = new FilterAnnotationReader(method);
					JsonParser jsonParser = new JsonParser(object, annotationReader, request, response);
	    			jsonParser.outJson();
	    		}
	    		long y = System.currentTimeMillis();
	    		System.out.println("json转换时间:"+(y-x)+"毫秒");
	    		return null;
	    	}
    	}
    	
    	return object;
    }
    
}
