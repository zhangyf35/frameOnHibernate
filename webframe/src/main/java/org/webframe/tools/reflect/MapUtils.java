package org.webframe.tools.reflect;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.webframe.tools.collects.BeansUtil;
import org.webframe.tools.reflect.formater.DateTimeConverter;

/**
 * Map工具类（目前没有使用）<br>
 * 提供了将map装换为对象，或将对象转换为map（字段名为key，字段值为value）
 * @author 张永葑
 * 
 */
public class MapUtils extends org.apache.commons.collections.MapUtils {
	
	/**
	 * 获取BeanUtilsBean 已经注册了Date转换
	 * @return BeanUtilsBean实例
	 */
	private static BeanUtilsBean getBeanUtilsBean() {
		BeanUtilsBean beanUtilsBean = null;
		try {
			DateTimeConverter dtConverter = new DateTimeConverter(); 
			ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean(); 
			convertUtilsBean.deregister(Date.class); 
			convertUtilsBean.register(dtConverter, Date.class); 
			beanUtilsBean = new BeanUtilsBean(convertUtilsBean,new PropertyUtilsBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanUtilsBean;
	} 
	
	/**
	 * 将Map转换为Object
	 * @param clazz 目标对象的类
	 * @param map 待转换Map
	 * @return 转换后的对象
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T, V> T toObject(Class<T> clazz, Map<String, V> map) {
		T object = null;
		try {
			object = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return toObject(object, map);
	}

	/**
	 * 将Map转换为Object
	 * @param object 目标对象
	 * @param map 待转换Map
	 * @return 转换后的对象
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T, V> T toObject(T object, Map<String, V> map) {
		try {
			getBeanUtilsBean().populate(object, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 对象转Map
	 * @param object 目标对象
	 * @return 转换后的Map
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, Serializable> toMap(T object) {
		try {
			return getBeanUtilsBean().describe(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换为List<Map<K, V>>
	 * @param list 待转换对象集合
	 * @return 转换后的List<Map<K, V>>
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <T> List<Map<String, Serializable>> toMapList(List<T> list){
		List<Map<String, Serializable>> retList = BeansUtil.newArrayList();
		if (list != null && !list.isEmpty()) {
			for (Object object : list) {
				Map<String, Serializable> map = toMap(object);
				retList.add(map);
			}
		}
		return retList;
	}
	
	/**
	 * 将list<Map>转换为List<T>
	 * @param cla 目标对象的类
	 * @param list 数据list
	 * @return 转换后的列表对象
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 */
	public static <T,V> List<T> toListObject(Class<T> cla, List<Map<String, V>> list) {
		List<T> listObject = BeansUtil.newArrayList();
		for (Map<String, V> map : list) {
			listObject.add(toObject(cla, map));
		}
		return listObject;
	}
}
