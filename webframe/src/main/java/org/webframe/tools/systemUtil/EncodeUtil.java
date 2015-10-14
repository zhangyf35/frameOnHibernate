package org.webframe.tools.systemUtil;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密解密工具类
 * @author 张永葑
 */
public class EncodeUtil {
	
	/**
	 * 加密算法
	 * @param str 需要加密的字符串
	 * @param randomString 随机字符串
	 * @return 
	 */
	public static String encode(String str, String randomString) {
		return DigestUtils.md5Hex(DigestUtils.md5Hex(str) + randomString + DigestUtils.md5Hex(str)+randomString);
	}

	/**
	 * 密码比对验证
	 * @param oldString
	 * @param newString
	 * @param randomString
	 * @return (如果密码对比正确返回true)
	 */
	public static boolean passwordContrast(String oldString, String newString, String randomString) {
		if(oldString.equals(encode(newString,randomString))){
			return true;
		}
		return false;
	}
	
}