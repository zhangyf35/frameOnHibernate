package org.webframe.tools.json;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.webframe.tools.collects.BeansUtil;
import org.webframe.tools.json.annotation.Filter;
import org.webframe.tools.json.annotation.JsonFilterProperties;

public class FilterAnnotationReader {
	
	public static Map<Class<?>,Set<String>> getJsonFilters(Method method){
		Map<Class<?>,Set<String>> map = BeansUtil.newHashMap();
		method.setAccessible(true);
		if(method.isAnnotationPresent(JsonFilterProperties.class)){
			JsonFilterProperties jsonFilters = method.getAnnotation(JsonFilterProperties.class);
			Filter[] filters = jsonFilters.filters();
			for (Filter filter : filters) {
				Set<String> set = BeansUtil.newHashSet();
				for (String field : filter.fields()) {
					set.add(field);
				}
				map.put(filter.clazz(), set);
			}
		}
		return map;
	}
}
