package com.sitemap.na2ne.config;

/**
 * 
 * @ClassName:     WebHostConfig.java
 * @Description:   ����ip��port�����ļ�
 * @author         chenhao
 * @Date           2015-11-14
 */

 
public class WebHostConfig {
//	 private static final String HOST_ADDRESS = "http://192.168.1.56:1599/";
//	 private static final String HOST_ADDRESS = "http://218.202.235.66:1599/";
	private static final String HOST_ADDRESS = "http://www.na2ne.com:20015/";
	private static final String HOST_NAME = HOST_ADDRESS + "NaErNe/";
	

	public static String getHostAddress() {
		return HOST_ADDRESS; 
	}

	public static String getHostName() {
		return HOST_NAME;
	}
}
