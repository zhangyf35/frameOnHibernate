package org.webframe.tools.json.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import net.sf.json.JsonConfig;

/**
 * json过滤类型配置类
 * @author 张永葑
 *
 */
public class TypeJudger {
	
	private static Class<?>[] numberClass = {Byte.class, Short.class, Integer.class, Long.class,
								Float.class, Double.class, BigInteger.class, BigDecimal.class};
	
	/**
	 * 设置number的类型为NUll时json转换后也为null
	 * @param config
	 */
	public static void setNumberDefaultValue(JsonConfig config) {
		for (Class<?> clazz : numberClass) {
			config.registerDefaultValueProcessor(clazz, new NumberDefaultValueProcessor());
		}
	}
	
	/**
	 * 判断某一个字段是否是懒加载
	 * @param cla 类
	 * @param fieldName 字段名
	 * @return 如果是懒加载true 反之为fasle
	 */
	public static Boolean isFetchLazy(Class<?> cla, String fieldName) {
		try {
			Field field = cla.getDeclaredField(fieldName);
			if(field.isAnnotationPresent(OneToOne.class)) {
				return field.getAnnotation(OneToOne.class).fetch() == FetchType.LAZY;
			} else if(field.isAnnotationPresent(OneToMany.class)) {
				return field.getAnnotation(OneToMany.class).fetch() == FetchType.LAZY;
			} else if(field.isAnnotationPresent(ManyToOne.class)) {
				return field.getAnnotation(ManyToOne.class).fetch() == FetchType.LAZY;
			} else if(field.isAnnotationPresent(ManyToMany.class)) {
				return field.getAnnotation(ManyToMany.class).fetch() == FetchType.LAZY;
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 是否是集合
	 * @param object
	 * @return
	 */
	public static Boolean isCollect(Object object) {
		if(isMap(object) || object instanceof Collection) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是map
	 * @param object
	 * @return
	 */
	public static Boolean isMap(Object object) {
		if(object instanceof Map)
			return true;
		return false;
	}
	
}
