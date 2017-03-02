package com.saitu.na2ne.wxapi;

import com.sitemap.na2ne.config.WebHostConfig;

/**
 * 
 * Description: 微信支付配置文件
 * 
 * @author chenhao
 * @date 2016-3-7
 */
public class WechatPayConfig {
	private static final String APP_ID = "wx03ddbff889621f60";//appid
	// 订单交易请求URL
	private static final String REQUEST_PAY_URL = WebHostConfig.getHostName()
			+ "userAction_sendWeChatPay?";

	public static String getAppId() {
		return APP_ID;
	}

	/**
	 * @param money
	 *            支付金额
	 * @param platform
	 *            请求平台 （1：android，2：ios）
	 * @param  userID  用户ID   
	 * @return url
	 */
	public static String getRequestPayUrl(String money,String userID ) {
		return REQUEST_PAY_URL+"money="+money+"&platform=1&userID="+userID;
	}
}
