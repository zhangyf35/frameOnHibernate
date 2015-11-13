package org.webframe.tools.systemUtil;

import java.util.UUID;

/**
 * 字符串处理
 * @author 张永葑
 *
 */
public class StringUtil {
	
	/**
	 * 得到UUID
	 * @return UUID
	 */
	public static String UUID() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}
	
	/**
	 * @param length
	 * @return 指定位数的字符串
	 */
	public static String randomString128() {
		String result128 = "";
		for (int i = 0; i < 4; i++) {
			result128 += UUID();
		}
		return result128;
	}
	
	/**
	 * 得到容易记住的数字
	 * @param numCount
	 * @return 容易记住的数字字符串
	 */
	public static String getEasyNumber(int numCount){
		StringBuffer buf = new StringBuffer();
		int preCount = -1;
		int preNum = -1;
		while (true) {
			int count = (int) (Math.random() * 3+1);
			int num = (int) (Math.random() * 10);
			if(num == preNum || count == preCount) { continue; }
			preNum = num;
			preCount = count;
			for (int i = 0; i < count; i++) {
				buf.append(num);
			}
			if (buf.length() >= numCount) {
				return buf.toString().substring(0, numCount);
			}
		}
	}
	
	/**
	 * 将命名转换成大写字母下划线命名
	 * @param column
	 * @return 转换后的字符串
	 */
	public static String toUpperUnderline(String column) {
		return toUnderLine(column).toUpperCase();
	}
	
	/**
	 * 将命名转换成小写字母下划线命名
	 * @param column
	 * @return 转换后的字符串
	 */
	public static String toLowerUnderline(String column) {
		return toUnderLine(column).toLowerCase(); 
	}
	
	/**
	 * 将命名转换成驼峰命名
	 * @param column
	 * @return 转换后的字符串
	 */
	public static String toCamel(String column) {
		StringBuilder result = new StringBuilder();
		if (column == null || column.isEmpty()) {
			return "";
		} else if(!column.contains("_")){
			return column.toLowerCase();
		} else {
			String[] columns = column.split("_");
			for (String columnSplit : columns) {
				if (columnSplit.isEmpty()) {
					continue;
				} 
				if (result.length() == 0) {
					result.append(columnSplit.toLowerCase());
				} else {
					result.append(firstToUpperCase(columnSplit));
				}
			}
			return result.toString();
		}
	}
	
	/**
	 * 将首字母变成大写
	 * @param str
	 * @return 转换后的字符串
	 */
	public static String firstToUpperCase(String str){
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	/**
	 * 判断一个字符串在另一个字符串出现的次数
	 * @param str
	 * @param target
	 * @return 次数
	 */
	public static int checkCharCount(String str, String target) {
		return str.split(target,-1).length -1;
	}
	
	/**
	 * 将命名变为下划线格式
	 * @param column
	 * @return 转换后的字符串
	 */
	private static String toUnderLine(String column) {
		if (column==null || "".equals(column.trim())){
            return "";
        } else if(column.contains("_")) { //包含下划线
        	return column;
        } else { //驼峰命名
        	int len=column.length();
            StringBuilder sb=new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c=column.charAt(i);
                if (Character.isUpperCase(c)){
                    sb.append("_");
                }
                sb.append(c);
            }
            return sb.toString();
        }
	}
	
}