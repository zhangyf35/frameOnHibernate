package org.webframe.tools.json.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;



/**
 * 按类过滤属性<br>
 * 当两个类有相同的字段名时,如User中有name,Order中有name,但是我仅仅是不转换User中的name<br>
 * 这种情况适合用@JsonFiltersToClass<br>
 * 如果过滤几个对象中没有要过滤的相同的字段名,那么使用@JsonFilterToFields
 * @author 张永葑
 *
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface JsonFilterProperties {
	Filter[] filters();
}
