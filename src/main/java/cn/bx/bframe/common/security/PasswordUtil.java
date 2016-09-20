package cn.bx.bframe.common.security;

import java.io.UnsupportedEncodingException;

import cn.bx.bframe.common.util.Encodes;

/**
 * 密码生成和校验类
 * @author bcm
 * @version 2016-05-19
 */
public class PasswordUtil {
	public static final String HASH_ALGORITHM = "SHA-1";//Hash算法
	public static final int HASH_INTERATIONS = 1024; //迭代次数
	public static final int SALT_SIZE = 8;//salt位数,8*2=16
	
	/**
	 * 生成随机的16位salt并经过1024次 sha-1 hash迭代的密码
	 * @param plainPassword 明文密码
	 * @return 密文密码
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 获取密码中的salt
	 * @param password 包含salt密文密码
	 * @return salt
	 */
	public static byte[] decryptSalt(String password) {
		return Encodes.decodeHex(password.substring(0,SALT_SIZE*2));
	}
	/**
	 * 获取密码中的密文密码
	 * @param password 包含salt密文密码
	 * @return salt
	 */
	public static String decryptPassword(String password) {
		return password.substring(SALT_SIZE*2);
	}
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	public  static void main(String[] args) throws UnsupportedEncodingException{
		System.out.println(entryptPassword("0000cs55555rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr555ffffffffffffffffffssss222"));
	}
}
