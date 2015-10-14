package org.webframe.tools.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanReader {
	
	/**
	 * 获得类
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Class<T> getClass(T object){
		return (Class<T>) object.getClass();
	}
	
	public static <T> Method[] getMethod(Class<T> cla){
		return cla.getMethods();
	}
	
	public static <T> Field[] getField(Class<T> cla){
		return cla.getDeclaredFields();
	}
	
}
