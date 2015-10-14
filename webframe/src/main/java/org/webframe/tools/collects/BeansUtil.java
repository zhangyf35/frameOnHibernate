package org.webframe.tools.collects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class BeansUtil {
	
	public static <T> List<T> newArrayList() {
		return new ArrayList<T>();
	}
	
	public static <T> List<T> newLinkedList() {
		return new LinkedList<T>();
	}
	
	public static <T> List<T> newVictor() {
		return new Vector<T>();
	}
	
	public static <T, V> Map<T, V> newHashMap() {
		return new HashMap<T, V>();
	}
	
	public static <T, V> Map<T, V> newLinkedHashMap() {
		return new LinkedHashMap<T, V>();
	}
	
	public static <T, V> StringBuffer newStringBuffer() {
		return new StringBuffer();
	}
	
	public static <T, V> StringBuilder newStringBuilder() {
		return new StringBuilder();
	}
	
	public static <T> Set<T> newHashSet() {
		return new HashSet<T>();
	}
}
