package org.webframe.tools.json.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 自动过滤对象中的懒加载字段<br>
 * 当注解了@JsonAutoFilterLazy,系统会自动过滤对象的懒加载字段,如果想显示一些懒加载类<br>
 * 需要添加属性showClass,将需要转换的类填写,<br>
 * 如果还有额外的普通字段需要过滤,需要添加extraFilterFields属性,<br>
 * extraFilterFields是一个@Filter数组,为每个单独的类添加要过滤的字段
 * 
 * 两个注解都在org.webframe.tools.json.annotation包中,不要选错了
 * 
 * @author 张永葑
 *
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface JsonAutoFilterLazy {
	@SuppressWarnings("rawtypes")
	Class[] showLazyClass() default {};
	
	Filter[] extraFilterFields() default {};
	
}
