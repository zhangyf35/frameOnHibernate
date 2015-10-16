package org.webframe.tools.systemUtil;

import java.util.Collection;

/**
 * 判断对象
 * @author 张永葑
 *
 */
public class JudgeUtil {
	
	/**
	 * 判断对象是否未Null
	 * @param obj
	 * @return 为null时为true，反之false
	 */
	public static boolean isNull(Object obj) {
		return obj==null?true:false;
	}
	
	/**
	 * 判断字符串是否为空且为Null
	 * @param str
	 * @return 为null且为""时为true，反之false
	 */
	public static boolean StringIsEmptyAndNull(String str) {
		if(str == null && "".equals(str))
			return true;
		return false;
	}
	
	/**
	 * 判断字符串值是否为空
	 * @return 为""时为true，反之false
	 */
	public static boolean StringIsEmpty(String str) {
		if("".equals(str))
			return true;
		return false;
	}
	
	/**
	 * 判断集合是否未null和空
	 * @return 为null且为空时为true，反之false
	 */
	@SuppressWarnings({ "rawtypes", "null" })
	public static boolean listIsNullAndEmpty(Collection collection) {
		if(collection == null && collection.isEmpty())
			return true;
		return false;
	}
	
	/**
	 * 判断集合是否未null和空
	 * @return 为null时为true，反之false
	 */
	@SuppressWarnings({ "rawtypes"})
	public static boolean listIsEmpty(Collection collection) {
		if(collection.isEmpty())
			return true;
		return false;
	}
}
