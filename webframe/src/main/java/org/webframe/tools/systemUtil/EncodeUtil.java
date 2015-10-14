package org.webframe.tools.systemUtil;

import java.security.MessageDigest;

/**
 * 加密解密工具类
 * @decription 提供常用加密、解密算法
 * @author Zebe
 * @date 2015/7/30
 * @version 1.0.1
 */
public class EncodeUtil {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	private Object salt; // 盐值
	private String algorithm; // 加密算法，如MD5、DES

	/**
	 * 构造器
	 * @param salt
	 * @param algorithm
	 */
	public EncodeUtil(Object salt, String algorithm) {
		this.salt = salt;
		this.algorithm = algorithm;
	}

	/**
	 * 加密算法
	 * @param rawPass
	 * @return
	 */
	public String encode(String rawPass) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			//加密后的字符串  
			result = byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes("utf-8")));
		} catch (Exception ex) {
		}
		return result;
	}

	/**
	 * 密码比对验证
	 * @param encPass
	 * @param rawPass
	 * @return
	 */
	public boolean isPasswordValid(String encPass, String rawPass) {
		String pass1 = "" + encPass;
		String pass2 = encode(rawPass);
		return pass1.equals(pass2);
	}

	/**
	 * 合并密码和盐值
	 * @param password
	 * @return
	 */
	private String mergePasswordAndSalt(String password) {
		if (password == null) {
			password = "";
		}
		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}

	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 转换单个字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 加密测试方法
	 * @param args
	 */
	public static void main(String[] args) {
		// 盐值声明（128位长度的随机数）
		String salt = NumberUtil.getRndNumberByLength(128);
		EncodeUtil encoderMd5 = new EncodeUtil(salt, "MD5");
		// 密码声明
		String password = "test";
		String encode = encoderMd5.encode(password);
		// 得到密文
		System.out.println("使用盐值" + salt + "加密" + password + "，得到密文：" + encode);
		// 测试验证，将用户传来的密码加密后的密文对比
		String userPassword = "test";
		boolean passwordValid = encoderMd5.isPasswordValid(encode, userPassword);
		System.out.println("是否符合：" + passwordValid);
	}
	
}