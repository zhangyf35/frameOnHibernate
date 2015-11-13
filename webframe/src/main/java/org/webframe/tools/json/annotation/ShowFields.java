package org.webframe.tools.json.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * 此注释在@JsonAutoFilterLazy中的extraFilterFields属性中注解
 * @author 张永葑
 *
 */
@Target({})
@Retention(RUNTIME)
public @interface ShowFields {
	
	/** 要加载的类 */
	Class<?> clazz() default void.class;
	
	/** 要过滤的类中的字段 */
	String[] fields() default {};
	
}
