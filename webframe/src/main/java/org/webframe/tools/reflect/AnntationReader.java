package org.webframe.tools.reflect;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

public class AnntationReader {
	
	/**
	 * 获取对象属性上注解的table值
	 * @param object
	 * @return
	 */
	public static <T> String getTable(Class<T> cla){
		if(cla.isAnnotationPresent(Table.class)){
			return cla.getAnnotation(Table.class).name();
    	}
	    return "";
	}
	
	/**
	 * 获取对象属性上注解的Id值
	 * @param object
	 * @return
	 */
	public static <T> Serializable[] getId(T object){
		Field[] fields = BeanReader.getField(object.getClass());
	    for (Field field : fields) {
	    	if(field.isAnnotationPresent(Id.class)){
	    		field.setAccessible(true);
	    		try {
	    			Serializable[] serializables = new Serializable[2];
	    			serializables[0] = field.getName();
	    			serializables[1] = (Serializable) field.get(object);
					return serializables;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
	    	}
		}
	    return null;
	}
	
	/**
	 * 获取对象属性上注解的Cloumn值
	 * @param object
	 * @return
	 */
	public static <T> Map<String, Serializable> getColumn(T object){
		Map<String, Serializable> columns = new HashMap<String, Serializable>();
		Field[] fields = BeanReader.getField(object.getClass());
	    for (Field field : fields) {
	    	if(field.isAnnotationPresent(Column.class)){
	    		field.setAccessible(true);
	    		try {
	    			Column annotationCloumn = field.getAnnotation(Column.class);
	    			columns.put(annotationCloumn.name(), (Serializable) field.get(object));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
	    	}
		}
	    return columns;
	}
	
	/**
	 * 返回类上的某个注解
	 * @param cla
	 * @param annotationClass
	 * @return
	 */
	public static <T> Annotation getClassAnnotation(Class<T> cla, Class<? extends Annotation> annotationClass){
		return cla.getAnnotation(annotationClass);
	}
	
	public static <T> Annotation[] getFieldAnnotation(Class<T> cla, Class<? extends Annotation> annotationClass){
		Field[] fields = BeanReader.getField(cla);
		for (Field field : fields) {
			field.getAnnotation(annotationClass);
		}
		return null;
		
	}
	
}
