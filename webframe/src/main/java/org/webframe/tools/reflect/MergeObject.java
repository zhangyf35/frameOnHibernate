package org.webframe.tools.reflect;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Id;

public class MergeObject {

	/**
	 * 将第二个参数对象的属性值合并到第一个上, 两个对象必须为同一类型
	 * @param obj1
	 * @param obj2
	 */
	public static void merge(Object target, Object current) {
		try {
			Class<?> cla = current.getClass();
			Field[] fields = cla.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Object value;
				value = field.get(current);
				if (value != null) {
					field.set(target, value);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取出对象的id
	 * @param current
	 * @return
	 */
	public static Serializable getId(Object current) {
		try {
			Class<?> cla = current.getClass();
			Field[] fields = cla.getDeclaredFields();
			for (Field field : fields) {
				if(field.isAnnotationPresent(Id.class)) {
					field.setAccessible(true);
					return (Serializable) field.get(current);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}