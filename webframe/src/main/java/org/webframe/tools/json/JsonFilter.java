package org.webframe.tools.json;

import java.util.Map;
import java.util.Set;

import net.sf.json.util.PropertyFilter;

/**
 * json过滤配置类
 * @author 张永葑
 *
 */
class JsonFilter implements PropertyFilter{
	
	Map<Class<?>,Set<String>> map = null;
	
	public JsonFilter(Map<Class<?>, Set<String>> jsonFilters) {
		this.map = (Map<Class<?>, Set<String>>) jsonFilters;
	}
	
	public boolean apply(Object cla, String fieldName, Object fieldType) {
		if(map != null && !map.isEmpty()) {
			for (Class<?> key : map.keySet()) {
				if(cla.getClass().getName().equals(key.getName())) {
					for (String field : map.get(key)) {
						if(fieldName.equals(field)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
}
