package org.webframe.tools.collects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Vector;

/**
 * 对象工厂（当要new一个集合的时候最好使用该类来创建），支持泛型
 * @author 张永葑
 *
 */
public class BeansUtil {
	
	/**
	 * new一个ArrayList
	 * @return ArrayList
	 */
	public static <T> ArrayList<T> newArrayList() {
		return new ArrayList<T>();
	}
	
	/**
	 * new一个LinkedList
	 * @return LinkedList
	 */
	public static <T> LinkedList<T> newLinkedList() {
		return new LinkedList<T>();
	}
	
	/**
	 * new一个Vector
	 * @return Vector
	 */
	public static <T> Vector<T> newVector() {
		return new Vector<T>();
	}
	
	/**
	 * new一个HashMap
	 * @return HashMap
	 */
	public static <T, V> HashMap<T, V> newHashMap() {
		return new HashMap<T, V>();
	}
	
	/**
	 * new一个LinkedHashMap
	 * @return LinkedHashMap
	 */
	public static <T, V> LinkedHashMap<T, V> newLinkedHashMap() {
		return new LinkedHashMap<T, V>();
	}
	
	/**
	 * new一个StringBuffer
	 * @return StringBuffer
	 */
	public static <T, V> StringBuffer newStringBuffer() {
		return new StringBuffer();
	}
	
	/**
	 * new一个StringBuilder
	 * @return StringBuilder
	 */
	public static <T, V> StringBuilder newStringBuilder() {
		return new StringBuilder();
	}
	
	/**
	 * new一个HashSet
	 * @return HashSet
	 */
	public static <T> HashSet<T> newHashSet() {
		return new HashSet<T>();
	}
	
	/**
	 * new一个LinkedHashSet
	 * @return LinkedHashSet
	 */
	public static <T> LinkedHashSet<T> newLinkedHashSet() {
		return new LinkedHashSet<T>();
	}

}
