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
	 * @return 加密后的字符串
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
	public static boolean passwordContrast(String oldPassword, String newPassword, String randomString) {
		if(oldPassword != null && oldPassword.equals(encode(newPassword,randomString))){
			return true;
		}
		return false;
	}
	
}