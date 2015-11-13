package org.webframe.tools.json.config;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.webframe.tools.collects.BeansUtil;
import org.webframe.tools.json.annotation.FilterFields;
import org.webframe.tools.json.annotation.JsonFilterLazy;
import org.webframe.tools.json.annotation.Jsonp;
import org.webframe.tools.json.annotation.ShowFields;

/**
 * 过滤字段读取类
 * @author 张永葑
 *
 */
public class FilterAnnotationReader {
	
	/** 需要过滤的字段 */
	private Map<Class<?>, Set<String>> filterFields = null;
	
	/** 需要显示的字段 */
	private Map<Class<?>, Set<String>> showFields = null;
	
	/** 需要显示的类 */
	private Class<?>[] showClass = null;
	
	/** 要解析的方法 */
	private Method method;
	
	/** jsonp */
	private String[] jsonp = new String[]{"false",null};
	
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
		if(hasJsonFilterLazy()) {
			JsonFilterLazy filterLazy = method.getAnnotation(JsonFilterLazy.class);
			
			Class<?>[] classes = filterLazy.showLazyClass();
			if(classes == null || classes.length == 0) {
				classes = filterLazy.value();
			}
			
			FilterFields[] filterFields = filterLazy.filterFields();
			ShowFields[] showFields = filterLazy.showFields();
			
			if(filterFields != null && filterFields.length != 0) {
				this.filterFields = BeansUtil.newHashMap();
				for (FilterFields filter : filterFields) {
					Set<String> set = BeansUtil.newHashSet();
					for (String field : filter.fields()) {
						set.add(field);
					}
					this.filterFields.put(filter.clazz(), set);
				}
			} 
			if(showFields != null && showFields.length != 0) {
				this.showFields = BeansUtil.newHashMap();
				for (ShowFields filter : showFields) {
					Set<String> set = BeansUtil.newHashSet();
					for (String field : filter.fields()) {
						set.add(field);
					}
					this.showFields.put(filter.clazz(), set);
				}
			}
			if(classes != null && classes.length != 0) {
				showClass = classes;
			}
		}
	}

	/**
	 * 获取过滤字段
	 * @return
	 */
	public Map<Class<?>, Set<String>> getFilterFields() {
		return this.filterFields;
	}
	
	/**
	 * 获取显示字段
	 * @return
	 */
	public Map<Class<?>, Set<String>> getShowFields() {
		return showFields;
	}

	/**
	 * 获取显示类
	 * @return
	 */
	public Class<?>[] getShowClass() {
		return this.showClass;
	}
	
	/**
	 * 判断是否jsonp发送
	 * @return
	 */
	public Boolean isJsonp() {
		return jsonp[0].equals("true");
	}
	
	/**
	 * 获取jsonp回调函数
	 * @return
	 */
	public String getJsonpCallback() {
		return jsonp[1];
	}
	
	/**
	 * 判断是否含有@JsonFilterLazy注解
	 * @return
	 */
	public Boolean hasJsonFilterLazy() {
		if(method.isAnnotationPresent(JsonFilterLazy.class)) {
			return true;
		}
		return false;
	}
	
}
