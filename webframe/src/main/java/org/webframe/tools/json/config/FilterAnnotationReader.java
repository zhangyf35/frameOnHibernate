package org.webframe.tools.json.config;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.webframe.tools.collects.BeansUtil;
import org.webframe.tools.json.annotation.Filter;
import org.webframe.tools.json.annotation.JsonAutoFilterLazy;
import org.webframe.tools.json.annotation.Jsonp;

/**
 * 过滤字段读取类
 * @author 张永葑
 *
 */
public class FilterAnnotationReader {
	
	/** 额外的过滤字段 */
	Map<Class<?>, Set<String>> extraFilterFields = null;
	
	/** 要显示的类 */
	Class<?>[] displayClass = null;
	
	/** 要解析的方法 */
	Method method;
	
	/** jsonp */
	String[] jsonp = new String[]{"false",null};
	
	public FilterAnnotationReader(Method method) {
		this.method = method;
		this.initFilter();
	}
	
	/**
	 * 设置方法对象初始化filter
	 * @param method 方法对象
	 */
	private void initFilter() {
		//如果注解了jsonp
		if(method.isAnnotationPresent(Jsonp.class)) {
			Jsonp jsonp = method.getAnnotation(Jsonp.class);
			this.jsonp[0] = "true";
			this.jsonp[1] = jsonp.callbackParamName();
		}
		//如果注解了JsonAutoFilterLazy
		if(method.isAnnotationPresent(JsonAutoFilterLazy.class)) {
			JsonAutoFilterLazy autoFilterLazy = method.getAnnotation(JsonAutoFilterLazy.class);
			Filter[] filters = autoFilterLazy.extraFilterFields();
			Class<?>[] classes = autoFilterLazy.showLazyClass();
			if(filters != null && filters.length != 0) {
				extraFilterFields = BeansUtil.newHashMap();
				for (Filter filter : filters) {
					Set<String> set = BeansUtil.newHashSet();
					for (String field : filter.fields()) {
						set.add(field);
					}
					extraFilterFields.put(filter.clazz(), set);
				}
			}
			if(classes != null && classes.length != 0) {
				displayClass = classes;
			}
		}
	}

	public Map<Class<?>, Set<String>> getExtraFilterFields() {
		return this.extraFilterFields;
	}

	public Class<?>[] getShowClass() {
		return this.displayClass;
	}
	
	public Boolean isJsonp() {
		return jsonp[0].equals("true");
	}
	
	public String getJsonpCallback() {
		return jsonp[1];
	}
	
	public Boolean hasJsonAutoFilterLazy() {
		if(method.isAnnotationPresent(JsonAutoFilterLazy.class)) {
			return true;
		}
		return false;
	}
	
}
