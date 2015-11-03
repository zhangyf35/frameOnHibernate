package org.webframe.tools.json.propertyFilter;

import java.util.Map;
import java.util.Set;

import org.webframe.tools.json.config.FilterAnnotationReader;
import org.webframe.tools.json.util.TypeJudger;

import net.sf.json.util.PropertyFilter;

/**
 * json过滤器
 * @author 张永凤
 *
 */
public class JsonPropertyFilter implements PropertyFilter{
	
	/** 要显示的类数组 */
	Class<?>[] showClass = null;
	
	/** 要过滤的字段集合 */
	Map<Class<?>,Set<String>> extraFilterFields = null;
	
	public JsonPropertyFilter(FilterAnnotationReader annotationReader) {
		this.showClass = annotationReader.getShowClass();
		this.extraFilterFields = annotationReader.getExtraFilterFields();
	}
	
	/**
	 * 接口过滤方法
	 */
	public boolean apply(Object object, String fieldName, Object fieldValue) {
		Boolean isFilter = false;
		Class<?> objectClass = object.getClass();
		try {
			if(TypeJudger.isMap(object)) {
				isFilter = extraFilterFieldsJudgeForMap(isFilter, fieldName);
			} else {
				if(objectClass.getName().contains("_$")) {
					String classToString = objectClass.toString();
					String classString = classToString.substring(6,classToString.indexOf("_$"));
					objectClass = Class.forName(classString);
				}
				if(TypeJudger.isFetchLazy(objectClass, fieldName)) {
					isFilter = true;
					isFilter = showClassJudge(isFilter, objectClass, fieldName);
				}
				isFilter = extraFilterFieldsJudge(isFilter, objectClass, fieldName);
			}
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return isFilter;
	}
	
	/**
	 * 要显示的类
	 * @param isFilter
	 * @param objectClass
	 * @param fieldName
	 * @return Boolean
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	private Boolean showClassJudge(Boolean isFilter, Class<?> objectClass, String fieldName)
			throws NoSuchFieldException, SecurityException {
		if(showClass != null) {
			for (Class<?> clazz : showClass) {
				String typeString = objectClass.getDeclaredField(fieldName).getGenericType().toString();
				if(typeString.contains(clazz.getName())){
					return false;
				}
			}
		}
		return isFilter;
	}
	
	/**
	 * entity中过滤不是懒加载的字段
	 * @param isFilter
	 * @param objectClass
	 * @param fieldName
	 * @return Boolean
	 */
	private Boolean extraFilterFieldsJudge(Boolean isFilter, Class<?> objectClass, String fieldName) {
		if(extraFilterFields != null && extraFilterFields.keySet().contains(objectClass)) {
			if(extraFilterFields.get(objectClass).contains(fieldName)) {
				return true;
			}
		}
		return isFilter;
	}
	/**
	 * Map中过滤不是懒加载的字段
	 * @param isFilter
	 * @param fieldName
	 * @return Boolean
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Boolean extraFilterFieldsJudgeForMap(Boolean isFilter, String fieldName)
			throws InstantiationException, IllegalAccessException {
		if(extraFilterFields != null && extraFilterFields.keySet().contains(Map.class)) {
			if(extraFilterFields.get(Map.class).contains(fieldName)) {
				return true;
			}
		}
		return isFilter;
	}
}
