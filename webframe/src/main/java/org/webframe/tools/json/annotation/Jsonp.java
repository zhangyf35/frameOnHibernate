package org.webframe.tools.json.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 处理跨域请求默认回调函数参数名为callback
 * @author 张永葑
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Jsonp {
	
	/** 回调函数的参数名 */
	String callbackParamName() default "callback";
}
