package org.webframe.tools.json.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 自动过滤对象中的懒加载字段<br>
 * 当我们在使用spring和hibernate时，hibernate中的懒加载在进行json转换的时候总是会被转换，<br>
 * 所以只要你用了hibernate在进行json转换时都会将有关联的全部转换,在jackson和jsonlib中都对这个问题进行了优化，但个人觉得稍显麻烦<br>
 * 现在我们使用@JsonAutoFilterLazy注解，对sping-mvc中任何代码不需要改动<br>
 * ^@RequestMapping("/list")<br>
 * ^@JsonAutoFilterLazy<br>
 * public User list() {<br>
 *    User user = globalService.list("from User where age>=?", new Object[]{18});<br>
 *    return user;<br>
 * }<br>
 * 
 * 如果User中包含一组订单List<Order>为懒加载，那么@JsonAutoFilterLazy会帮助我们自动忽略掉懒加载<br>
 * 当我们在controller中查出了User，又想拥有该用户的订单怎么办，如下： <br>
 * 将@JsonAutoFilterLazy 改为:<br>
 * 
 * ^@JsonAutoFilterLazy(<br>
 *     showLazyClass={Order.class}<br>
 * )<br>
 * showLazyClass代表要显示的懒加载类，那么在最终的json结果中就会包含User的订单Order<br>
 * 如果订单中还有订单中的商品Commodity类，将上面注解改成<br>
 * ^@JsonAutoFilterLazy(<br>
 *     showLazyClass={Order.class,Commodity.class}<br>
 * )<br>
 * 
 * 如果我们还要过滤其他不是懒加载的普通字段，比如我们不想用户的密码和用户的邮箱在json字符串中，即User中的password，email字段，有该怎么做呢？<br>
 * 将上面的注解改为：<br>
 * ^@JsonAutoFilterLazy(<br>
 *     showLazyClass={Order.class,Commodity.class},<br>
 *     extraFilterFields={<br>
 *     		^@Filter(clazz=User.class,fields={"password","email"})<br>
 *     }<br>
 * )<br>
 * 
 * 如果还不想显示Commodity中的价格price，则将上面的注解改为<br>
 * ^@JsonAutoFilterLazy(<br>
 *     showLazyClass={Order.class,Commodity.class},<br>
 *     extraFilterFields={<br>
 *     		^@Filter(clazz=User.class,fields={"password","email"}),<br>
 *     		^@Filter(clazz=Commodity.class,fields={"price"})<br>
 *     }<br>
 * )<br>
 * 
 * @see org.webframe.tools.json.annotation.Filter
 * @see org.webframe.tools.json.aop.JsonFilterInterceptor
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
