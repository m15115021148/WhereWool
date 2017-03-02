package com.saitu.na2ne.wxapi;

import com.sitemap.na2ne.config.WebHostConfig;

/**
 * 
 * Description: ΢��֧�������ļ�
 * 
 * @author chenhao
 * @date 2016-3-7
 */
public class WechatPayConfig {
	private static final String APP_ID = "wx03ddbff889621f60";//appid
	// ������������URL
	private static final String REQUEST_PAY_URL = WebHostConfig.getHostName()
			+ "userAction_sendWeChatPay?";

	public static String getAppId() {
		return APP_ID;
	}

	/**
	 * @param money
	 *            ֧�����
	 * @param platform
	 *            ����ƽ̨ ��1��android��2��ios��
	 * @param  userID  �û�ID   
	 * @return url
	 */
	public static String getRequestPayUrl(String money,String userID ) {
		return REQUEST_PAY_URL+"money="+money+"&platform=1&userID="+userID;
	}
}
