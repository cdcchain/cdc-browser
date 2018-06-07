package com.browser.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlockchainTool {
	
	/**
	 * 获取当前时间作为id
	 */
	public static long getId() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String now = df.format(new Date());
		long id = Long.parseLong(now);
		return id;
	}
	
}
