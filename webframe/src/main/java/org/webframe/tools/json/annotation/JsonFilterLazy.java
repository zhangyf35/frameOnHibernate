package org.webframe.tools.json.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 自动过滤对象中的懒加载字段<br>
 * 
 * 当我们在使用spring和hibernate时，hibernate中的懒加载在进行json转换的时候总是会被转换，<br>
 * 所以只要你用了hibernate在进行json转换时都会将有关联的全部转换,在jackson和jsonlib中都对这个问题进行了优化，但个人觉得稍显麻烦<br>
 * 
 * 现在我们使用@JsonFilterLazy注解，对sping-mvc中任何代码不需要改动,@JsonFilterLazy会帮助我们自动忽略掉懒加载,<br>
 * 有时我们又需要转换懒加载对象,那么我们只要需要转换的类写到showLazyClass中就行了<br>
 * 
 * 当我们需要过滤类中的属性时,就需要用到filterFields属性(仅仅过滤User中的name和password,其余属性除懒加载字段外自动转换)<br>
 * 格式为<br>
 * filterFields = {<br>
 * 		{@code}@FilterFields(clazz = User.class, fields = {"name", "password"})<br>
 * }<br>
 * 
 * 当我们仅仅需要显示某个类中的属性时,就需要用到showFields属性(仅仅转换User中的name和password)
 * showFields = {
 * 		{@code}@ShowFields(clazz = User.class, fields = {"name", "password"})<br>
 * }
 * 
 * 注意: 因为filterFields和showFields是相反的,所以两个属性里面不能出现同一个类
 * 
 * @see org.webframe.tools.json.annotation.FilterFields
 * @see org.webframe.tools.json.aop.JsonFilterInterceptor
 * @author 张永葑
 *
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface JsonFilterLazy {
	
	/**
	 * 需要转换的类
	 * @return Class[]
	 */
	@SuppressWarnings("rawtypes")
	Class[] showLazyClass() default {};
	
	/**
	 * 针对某个类需要过滤的字段, 除给定类的给定字段过滤,其余给定类的没给定的字段自动转换
	 * @return FilterFields[]
	 */
	FilterFields[] filterFields() default {};
	
	/**
	 * 针对某个类需要显示的字段, 除给定类的给定字段显示,其余给定类的没给定的字段自动过滤
	 * @return ShowFields[]
	 */
	ShowFields[] showFields() default {};
	
	/**
	 * 默认显示的类,当我们使用属性名时请使用showLazyClass
	 * @return Class[]
	 */
	@SuppressWarnings("rawtypes")
	Class[] value() default {};
	
}
