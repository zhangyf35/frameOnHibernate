package org.webframe.tools.systemUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 数字工具类
 * @decription 提供基于数字的各种方法，例如加减乘除四则运算（精确），生成随机数等
 * @author Zebe
 * @date 2014/11/27
 * @version 1.0.1
 */
public class NumberUtil {

	private static final int DEF_DIV_SCALE = 10; // 默认除法运算精度

	/**
	 * 得到当前时刻[年月日时分秒]组合的14位数字
	 * @param length
	 * @return String
	 */
	public static String getDateTimeNumber14() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * 得到当前时刻[年月日时分秒毫秒]组合的17位数字
	 * @param length
	 * @return String
	 */
	public static String getDateTimeNumber17() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	/**
	 * 得到指定长度的随机数字
	 * @param length
	 * @return String
	 */
	public static String getRndNumberByLength(int length) {
		StringBuffer number = new StringBuffer();
		for (int i=0; i<length; i++) {
			number.append(new Random().nextInt(9));
		}
		return number.toString();
	}
	
	/**
	 * 得到6位容易记住的数字，类似于222666、112233、556660之类的数字
	 * @return String
	 */
	public static String getEasyNumber6(){
		StringBuffer buf = new StringBuffer();
		while (true) {
			for (int i=0; i<3; i++) {
				int num = (int) (Math.random() * 10);
				for (int j=0; j<(Math.random() * 3 + 1); j++){
					buf.append(num);
				}
			}
			if (buf.length() >= 6) {
				return buf.toString().substring(0, 6);
			}
		}
	}

	/**
	 * 提供精确的加法运算
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 提供精确的减法运算
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	/**
	 * 提供精确的乘法运算
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到小数点以后10位，以后的数字四舍五入
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1,double v2){
		return div(v1,v2,DEF_DIV_SCALE);
	}
	
	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1,double v2,int scale){
		double result = 0d;
		if (scale < 0) {
			throw new IllegalArgumentException("刻度值有误，保留的小数位数只能是正整数！");
		}
		try {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			result = b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			result = 0d;
		}
		return result;
	}
	
	/**
	 * 提供精确的小数位四舍五入处理
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v,int scale){
		if (scale < 0) {
			throw new IllegalArgumentException("刻度值有误，保留的小数位数只能是正整数！");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 判断一个字符串是否为数字
	 * @param numberStr 字符串
	 * @return boolean
	 */
	public static boolean isNumeric(String numberStr){
		Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(numberStr).matches();  
	}

	/**
	 * 方法测试
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("得到当前时刻[年月日时分秒]组合的14位数字：" + getDateTimeNumber14());
		System.out.println("得到当前时刻[年月日时分秒毫秒]组合的17位数字：" + getDateTimeNumber17());
		System.out.println("得到指定长度的随机数字：" + getRndNumberByLength(6) + " <这是6位测试>");
		System.out.println("得到6位容易记住的数字：" + getEasyNumber6());
		System.out.println("提供精确的加法运算：" + add(56.2, 0.5));
		System.out.println("提供精确的减法运算：" + sub(100.1, 0.22));
		System.out.println("提供精确的乘法运算：" + mul(24.2, 2.556));
		System.out.println("提供（相对）精确的除法运算(最多保留小数点10位)：" + div(3, 7));
		System.out.println("提供（相对）精确的除法运算(保留指定长度小数点)：" + div(3, 7, 2));
		System.out.println("提供精确的小数位四舍五入处理：" + round(div(3, 7, 2), 1));
	}

}