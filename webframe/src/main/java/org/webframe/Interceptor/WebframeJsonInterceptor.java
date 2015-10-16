package org.webframe.Interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * json处理拦截器
 * @author 张永葑
 *
 */
public class WebframeJsonInterceptor implements HandlerInterceptor{
	
	private Method method = null;
	
	// preHandle()方法在业务处理器处理请求之前被调用
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
		
	}
	
	// postHandle()方法在业务处理器处理请求之后被调用   
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			System.out.println(handlerMethod.getMethod().getReturnType().getSimpleName());
		}
	}
	
	// afterCompletion()方法在DispatcherServlet完全处理完请求后被调用   
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
		
	}

}
