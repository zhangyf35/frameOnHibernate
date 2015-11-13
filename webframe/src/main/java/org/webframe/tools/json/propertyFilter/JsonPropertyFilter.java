package org.webframe.tools.json.propertyFilter;

import java.util.Map;
import java.util.Set;

import org.webframe.Exception.JsonFilterException;
import org.webframe.tools.json.config.FilterAnnotationReader;
import org.webframe.tools.json.util.TypeJudger;

import net.sf.json.util.PropertyFilter;

/**
 * json过滤器
 * @author 张永凤
 *
 */
public class JsonPropertyFilter implements PropertyFilter{
	
	private FilterAnnotationReader annotationReader = null;
	
	public JsonPropertyFilter(FilterAnnotationReader annotationReader) {
		this.annotationReader = annotationReader;
	}
	
	/**
	 * 接口过滤方法
	 */
	public boolean apply(Object object, String fieldName, Object fieldValue) {
		//是否过滤当前字段, true为过滤,false为不过滤
		boolean isFilter = false;
		try {
			//当前过滤类
			Class<?> objectClass = object.getClass();
			//获取当前过滤类
			if(objectClass.getName().contains("_$")) {
				objectClass = getRealClass(objectClass);
			}
			if(TypeJudger.isMap(object)) {
				isFilter = filterFieldsJudgeForMap(fieldName);
			} else {
				if(showFieldsIsContainsClass(objectClass) && filterFieldsIsContainsClass(objectClass)) {
					throw new JsonFilterException("The same class cannot have both filterFields and showFields attributes in @jsonFilterLazy");
				} else if(filterFieldsIsContainsClass(objectClass)) {
					//过滤字段
					isFilter = filterFieldsJudge(objectClass, fieldName);
				} else if(showFieldsIsContainsClass(objectClass)) {
					//是否为显示字段
					isFilter = !showFieldsJudge(objectClass, fieldName);
				}
				
				//是否是懒加载字段
				if(TypeJudger.isFetchLazy(objectClass, fieldName)) {
					isFilter = showClassJudge(objectClass, fieldName);
				}
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
	boolean showClassJudge(Class<?> objectClass, String fieldName)
			throws NoSuchFieldException, SecurityException {
		Class<?>[] showClass = annotationReader.getShowClass();
		if(showClass != null) {
			for (Class<?> clazz : showClass) {
				String typeString = objectClass.getDeclaredField(fieldName).getGenericType().toString();
				if(typeString.contains(clazz.getName())){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * entity中过滤的字段
	 * @param isFilter
	 * @param objectClass
	 * @param fieldName
	 * @return Boolean
	 */
	boolean filterFieldsJudge(Class<?> objectClass, String fieldName) {
		Map<Class<?>, Set<String>> filterFields = annotationReader.getFilterFields();
		if(filterFields.get(objectClass).contains(fieldName)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 显示字段中是否包含当前过滤类
	 * @param objectClass
	 * @return
	 */
	boolean filterFieldsIsContainsClass(Class<?> objectClass) {
		Map<Class<?>, Set<String>> filterFields = annotationReader.getFilterFields();
		return filterFields != null? filterFields.keySet().contains(objectClass) : false;
	}
	
	/**
	 * entity中显示的字段
	 * @param isFilter
	 * @param objectClass
	 * @param fieldName
	 * @return Boolean
	 */
	boolean showFieldsJudge(Class<?> objectClass, String fieldName) {
		Map<Class<?>, Set<String>> showFields = annotationReader.getShowFields();
		if(showFields.get(objectClass).contains(fieldName)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 显示字段中是否包含当前过滤类
	 * @param objectClass
	 * @return
	 */
	boolean showFieldsIsContainsClass(Class<?> objectClass) {
		Map<Class<?>, Set<String>> showFields = annotationReader.getShowFields();
		return showFields != null ? showFields.keySet().contains(objectClass) : false;
	}
	
	/**
	 * Map中过滤的字段
	 * @param isFilter
	 * @param fieldName
	 * @return Boolean
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	boolean filterFieldsJudgeForMap(String fieldName)
			throws InstantiationException, IllegalAccessException {
		Map<Class<?>, Set<String>> filterFields = annotationReader.getFilterFields();
		if(filterFields != null && filterFields.keySet().contains(Map.class)) {
			if(filterFields.get(Map.class).contains(fieldName)) {
				return true;
			}
		}
		return false;
	}
	
	Class<?> getRealClass(Class<?> objectClass) throws ClassNotFoundException {
		String classToString = objectClass.toString();
		String classString = classToString.substring(6,classToString.indexOf("_$"));
		return Class.forName(classString);
	}
}
