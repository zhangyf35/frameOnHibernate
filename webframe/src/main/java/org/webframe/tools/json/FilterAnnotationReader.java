package org.webframe.tools.json;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.webframe.tools.collects.BeansUtil;
import org.webframe.tools.json.annotation.Filter;
import org.webframe.tools.json.annotation.JsonFilterProperties;

/**
 * 过滤字段读取类
 * @author 张永葑
 *
 */
public class FilterAnnotationReader {
	
	/**
	 * 读取过滤字段
	 * @param method 方法对象
	 * @return class对应的过滤字段树
	 */
	public static Map<Class<?>,Set<String>> getJsonFilters(Method method){
		Map<Class<?>,Set<String>> map = BeansUtil.newHashMap();
		JsonFilterProperties jsonFilters = method.getAnnotation(JsonFilterProperties.class);
		Filter[] filters = jsonFilters.filters();
		for (Filter filter : filters) {
			Set<String> set = BeansUtil.newHashSet();
			for (String field : filter.fields()) {
				set.add(field);
			}
			map.put(filter.clazz(), set);
		}
		return map;
	}
}
