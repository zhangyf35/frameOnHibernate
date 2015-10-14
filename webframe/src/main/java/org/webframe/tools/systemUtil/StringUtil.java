package org.webframe.tools.systemUtil;

/**
 * 命名字段驼峰,下划线转换
 * @author 张永风
 *
 */
public class StringUtil {

	public static void main(String[] args) {
		System.out.println(toCamel("ccc2_dddddd_aa"));
	}
	/**
	 * 将命名转换成大写字母下划线命名
	 * @param column
	 * @return
	 */
	public static String toUpperUnderline(String column) {
		return toUnderLine(column).toUpperCase();
	}
	
	/**
	 * 将命名转换成小写字母下划线命名
	 * @param column
	 * @return
	 */
	public static String toLowerUnderline(String column) {
		return toUnderLine(column).toLowerCase(); 
	}
	
	
	/**
	 * 将命名转换成驼峰命名
	 * @param column
	 * @return
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
	 * @return
	 */
	public static String firstToUpperCase(String str){
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	/**
	 * 判断一个字符串在另一个字符串出现的次数
	 * @param str
	 * @param target
	 * @return
	 */
	public static int checkCharCount(String str, String target) {
		return str.split(target,-1).length -1;
	}
	
	/**
	 * 将命名变为下划线格式
	 * @param column
	 * @return
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