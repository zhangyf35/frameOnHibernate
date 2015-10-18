package org.webframe.tools.json.propertyFilter;

import java.util.Map;
import java.util.Set;

import org.webframe.tools.json.config.FilterAnnotationReader;
import org.webframe.tools.json.util.TypeJudger;

import net.sf.json.util.PropertyFilter;

public class JsonPropertyFilter implements PropertyFilter{
	
	Set<String> showClass = null;
	
	Map<Class<?>,Set<String>> extraFilterFields = null;
	
	public JsonPropertyFilter(FilterAnnotationReader annotationReader) {
		this.showClass = annotationReader.getShowClass();
		this.extraFilterFields = annotationReader.getExtraFilterFields();
	}
	
	public boolean apply(Object object, String fieldName, Object fieldValue) {
		Boolean isFilter = false;
		Class<?> objectClass = object.getClass();
		try {
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
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return isFilter;
	}
	
	private Boolean showClassJudge(Boolean isFilter, Class<?> objectClass, String fieldName)
			throws NoSuchFieldException, SecurityException {
		if(showClass != null) {
			for (String clazz : showClass) {
				String typeString = objectClass.getDeclaredField(fieldName).getGenericType().toString();
				if(typeString.contains(clazz)){
					return false;
				}
			}
		}
		return isFilter;
	}
	
	private Boolean extraFilterFieldsJudge(Boolean isFilter, Class<?> objectClass, String fieldName) {
		if(extraFilterFields != null && extraFilterFields.keySet().contains(objectClass)) {
			if(extraFilterFields.get(objectClass).contains(fieldName)) {
				return true;
			}
		}
		return isFilter;
	}
	
}
