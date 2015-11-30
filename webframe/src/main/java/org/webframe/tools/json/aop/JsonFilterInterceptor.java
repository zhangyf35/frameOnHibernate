package org.webframe.tools.json.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webframe.tools.json.JsonParser;
import org.webframe.tools.json.config.FilterAnnotationReader;

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
    	Object object = pjp.proceed();
    	//判断是否返回为json
    	if(object != null) {
	    	if(method.isAnnotationPresent(ResponseBody.class)) {
	    		if (object.getClass().getSimpleName().equals("String")) {//如果返回的是字符串
	    			new JsonParser(null,null).outString(String.valueOf(object));
	    		}else {//如果返回的不是字符串
	    			FilterAnnotationReader annotationReader = new FilterAnnotationReader(method);
					JsonParser jsonParser = new JsonParser(object, annotationReader);
	    			jsonParser.outJson();
	    		}
	    		return null;
	    	}
    	}
    	return object;
    }
    
}
