package com.free.blog.domain.util;

import java.security.MessageDigest;

/**
 * 对外提供getMD5(String)方法
 * 
 * @author randyjia
 * 
 */
public class Md5Utils {

	public static String getMD5(String source) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("Md5Utils");
		byte[] result;
		md5.reset();
		md5.update(source.getBytes("UTF-8"));
		result = md5.digest();

		StringBuilder buf = new StringBuilder(result.length * 2);
		for (byte aResult : result) {
			int intVal = aResult & 0xff;
			if (intVal < 0x10) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(intVal));
		}

		return buf.toString();

	}
}
