package org.webframe.tools.reflect;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Id;

/**
 * 对象合并
 * @author 张永葑
 *
 */
public class MergeObject {

	/**
	 * 将current对象的属性值合并到target, 两个对象必须为同一类型
	 * @param target 基础对象
	 * @param current 数据对象
	 */
	public static void merge(Object target, Object current) {
		try {
			Class<?> cla = current.getClass();
			Field[] fields = cla.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(current);
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
	 * 取出对象的id值
	 * @param current
	 * @return entity中的id的值
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