package org.webframe.tools.json;

import java.util.Map;
import java.util.Set;

import net.sf.json.util.PropertyFilter;

class JsonFilter implements PropertyFilter{
	
	Map<Class<?>,Set<String>> map = null;
	Set<String> fields = null;
	
	@SuppressWarnings("unchecked")
	public JsonFilter(Map<String, Object> jsonFilters) {
		this.map = (Map<Class<?>, Set<String>>) jsonFilters.get("map");
		this.fields = (Set<String>) jsonFilters.get("set");
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
		}else if(fields != null && fields.size() != 0) {
			for (String field : fields) {
				if(fieldName.equals(field)){
					return true;
				}
			}
		}
		return false;
	}
	
}
