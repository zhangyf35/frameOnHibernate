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
 * Map工具类（目前没有使用）
 * @author 张永葑
 * 
 */
public class MapUtils extends org.apache.commons.collections.MapUtils {

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
	 * 
	 * @param clazz
	 *            目标对象的类
	 * @param map
	 *            待转换Map
	 * @return
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
	 * 
	 * @param object
	 *            目标对象
	 * @param map
	 *            待转换Map
	 * @return
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
	 * 
	 * @param object
	 *            目标对象
	 * @return
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
	 * 转换为Collection<Map<K, V>>
	 * 
	 * @param collection
	 *            待转换对象集合
	 * @return 转换后的Collection<Map<K, V>>
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
	 * @param cla
	 * @param list
	 * @return
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
