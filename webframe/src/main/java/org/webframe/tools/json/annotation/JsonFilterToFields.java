package org.webframe.tools.json.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 按类过滤属性<br>
 * 当需要过滤对象中的字段可以使用这个注解<br>
 * 当要过滤的字段几个对象中都有同名的,如User中有name,Order中有name<br>
 * 只需要过滤User中的name那么请使用@JsonFiltersToClass
 * @author 张永峰
 *
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface JsonFilterToFields {
	String[] fields() default {};
}
