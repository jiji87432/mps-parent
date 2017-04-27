package com.chanpay.ppd.mps.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SeqUtils {
	private static long COUNT = 0;

	private static final int ROTATION = 99999;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");

	/**
	 * 获取26位唯一字符串
	 * 
	 * @return
	 */
	public static synchronized String getUniqueID() {
		String dateInfo = dateFormat.format(new Date());
		if (COUNT > 999999999) {
			COUNT = 0;
		}
		return dateInfo + num2Str(++COUNT, 9);
	}

	/**
	 * 获取指定位数的ID，最少位数为17位<br>
	 * 
	 * @param width
	 * @return
	 */
	public static synchronized String getUniqueID(int width) {
		String dateInfo = dateFormat.format(new Date());
		if (COUNT > 999999999) {
			COUNT = 0;
		}
		return dateInfo + num2Str(++COUNT, width - 17);
	}

	// 生成的id加前缀
	public static synchronized String getUniqueID(String preStr) {
		if (COUNT > ROTATION) {
			COUNT = 0;
		}
		StringBuilder buf = new StringBuilder();
		buf.delete(0, buf.length());
		Date date = new Date();
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d",
				date, COUNT++);
		return preStr + str;
	}

	/**
	 * 数字按照指定宽度
	 * 
	 * @param number
	 * @param width
	 * @return
	 */
	public static String num2Str(long number, int width) {
		String numStr = String.valueOf(number);
		width -= numStr.length();
		if (width >= 0) {
			StringBuffer zeroBuff = new StringBuffer();
			while (zeroBuff.length() < width) {
				zeroBuff.append("0");
			}
			return zeroBuff.toString() + numStr;
		} else {
			return numStr.substring(Math.abs(width));
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(getUniqueID(20));
		}
	}
}
