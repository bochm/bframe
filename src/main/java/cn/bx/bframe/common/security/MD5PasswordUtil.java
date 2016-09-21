package cn.bx.bframe.common.security;

import cn.bx.bframe.common.util.Encodes;

/**
 * 密码生成和校验类
 * @author bcm
 * @version 2016-05-19
 */
public class MD5PasswordUtil {
	
	/**
	 * 生成 md5 密码
	 * @param plainPassword 明文密码
	 * @return 密文密码
	 */
	public static String entryptPassword(String plainPassword) {
		return Encodes.encodeHex(Digests.md5(plainPassword.getBytes()));
	}
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		return password.equals(entryptPassword(plainPassword));
	}
	
	
}
