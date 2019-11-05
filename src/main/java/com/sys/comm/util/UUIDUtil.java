package com.sys.comm.util;

import java.util.Random;
import java.util.UUID;

public class UUIDUtil {
	public static String getStringUUid(){
		String result = UUID.randomUUID().toString();
		return result.replaceAll("-", "");
	}
	
	/**
	 * 获取6位数字加前缀序列号
	 * 
	 * @return
	 */
	public static String getSequenceNum(String prefix) {
		StringBuffer dataBuf = new StringBuffer("");
		String randomOne = getRandom(6);
		dataBuf.append(randomOne);
		return prefix+dataBuf.toString();
	}
	public static String getRandom(int num) {
		if (num <= 0 || num >= 10) {
			return "0000";
		}
		StringBuffer NumBuf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			NumBuf.append("9");
		}
		num = Integer.parseInt(NumBuf.toString());
		StringBuffer randomBuf = new StringBuffer("");
		Random random = new Random();
		String randNum = String.valueOf(random.nextInt(num));
		randomBuf.append(randNum);
		int randNumLength = randNum.length();
		int curNumLength = String.valueOf(num).length();
		if (randNumLength < curNumLength) {
			int k = curNumLength - randNumLength;
			for (int i = 0; i < k; i++) {
				randomBuf.append("0");
			}
		}
		return randomBuf.toString();
	}
}
