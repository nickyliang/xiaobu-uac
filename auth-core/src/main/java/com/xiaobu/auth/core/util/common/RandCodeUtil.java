package com.xiaobu.auth.core.util.common;

import java.util.Random;

/**
 * 随机数工具
 *
 * @author qichao
 * @create 2018-06-24
 **/
public class RandCodeUtil {

	/**
	 * 生成指定位数的随机字母
	 * @param charCount 位数
	 * @return
	 */
	public static String getRandCharacter(int charCount) {
		String charValue = "";
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 26) + 'a');
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	/**
	 * 生成指定位数的随机数字
	 * @param charCount 位数
	 * @return
	 */
	public static String getRandNumber(int charCount) {
		String charValue = "";
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 10) + '0');
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	private static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}

}