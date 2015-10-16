package org.webframe.tools.json.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 处理跨域请求默认毁掉函数参数名为callback
 * @author 张永葑
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Jsonp {
	String callbackParamName() default "callback";
}
