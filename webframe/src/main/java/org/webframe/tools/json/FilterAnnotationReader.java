package org.webframe.tools.json;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.webframe.tools.collects.BeansUtil;
import org.webframe.tools.json.annotation.Filter;
import org.webframe.tools.json.annotation.JsonFilterToFields;
import org.webframe.tools.json.annotation.JsonFiltersToClass;

class FilterAnnotationReader {
	
	public static Map<String, Object> getJsonFilters(){
		Map<String,Object> filterMap = BeansUtil.newHashMap();
		
		Map<Class<?>,Set<String>> map = BeansUtil.newHashMap();
		Set<String> fieldSet = BeansUtil.newHashSet();
		
		StackTraceElement[] stack = new Throwable().getStackTrace();
		String className = stack[2].getClassName();
		String methodName = stack[2].getMethodName();
		
		try {
			Class<?> cla = Class.forName(className);
			Method[] methods = cla.getDeclaredMethods();
			for (Method method : methods) {
				method.setAccessible(true);
				if(method.getName().equals(methodName)) {
					if(method.isAnnotationPresent(JsonFiltersToClass.class)){
						JsonFiltersToClass jsonFilters = method.getAnnotation(JsonFiltersToClass.class);
						Filter[] filters = jsonFilters.filters();
						for (Filter filter : filters) {
							Set<String> set = BeansUtil.newHashSet();
							for (String field : filter.fields()) {
								set.add(field);
							}
							map.put(filter.clazz(), set);
						}
					} else if(method.isAnnotationPresent(JsonFilterToFields.class)) {
						JsonFilterToFields fields = method.getAnnotation(JsonFilterToFields.class);
						for (String field : fields.fields()) {
							fieldSet.add(field);
						}
					}
				}
				
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		filterMap.put("map", map);
		filterMap.put("set", fieldSet);
		return filterMap;
	}
}
