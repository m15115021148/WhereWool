package com.sitemap.na2ne.alipay;

import com.sitemap.na2ne.config.WebHostConfig;

/**
 * 
 * Description: ֧���������ļ�
 * 
 * @author chenhao
 * @date 2015-12-31
 */
public class AliPayConfig {
	/*Ϊ׿֧�����˻�*/
//	public static final String PARTNER = "2088021205949442";
//	public static final String SELLER = "forbst@sina.com";
	// �������׳ɹ����첽֪ͨ��ַ��Ϊ׿��
//	public static final String NOTIFY_URL = WebHostConfig.getHostName()+"/userAction_pay";
	
	/*��ͼ֧�����˻�*/
	// �̻�PID 
	public static final String PARTNER = "2088221482341230";
//	 �̻��տ��˺�
	public static final String SELLER = "na2nepay@sina.com";
//	 �̻�˽Կ��pkcs8��ʽ
	public static String RSA_PRIVATE = "";
//	 ֧������Կ
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	// �������׳ɹ����첽֪ͨ��ַ����ͼ��
	public static final String NOTIFY_URL = WebHostConfig.getHostName()+"/userAction_paysitemap";

	public static String getPartner() {
		return PARTNER;
	}

	public static String getSeller() {
		return SELLER;
	}

	public static String getRsaPrivate() {
		RSA_PRIVATE = "MIICXAIBAAKBgQCt4un255QbQRwX4iFW+qk+GjAOX1V20MHY0qrs/I4A4pbDSehG"
				+ "6u83psYZRRZh/dVBWcjoxirL/HtnGNBTqtdtDw8oihOOW8yG6HD6/nGgbY9EI/B6"
				+ "X4Xv3dm8aAlDQOH7nQePzUnq4nxA3TOPCL5xixtKnJ1HuPwWtyjVvs+yRwIDAQAB"
				+ "AoGADHA+7DqEja6Ko+q5F8+2a4rUBTMsmeM0+p2XPHyUa9vJjQ6sXuZPVlvHr+cR"
				+ "QtE1gAc8J+qFBTJ6YVWVEWcXIcfIJq3p/On/RMdFmHgWtlJIEfJzbIr7+ah3PuBc"
				+ "/DReNVYqi87cV/f5X9qcvphBsKf8UMCqcBEXYJhzhviIhgECQQDeRXPMuyTkv6w6"
				+ "FCSFtJ+/S8svvTbRqg8UXNu1Y+3rSdfSPVOzz7WczHquAslCHPg9PLipVioWTxU8"
				+ "xSWetd4XAkEAyEXbudBk4PqSrcvQe2d3A/nZXMDcisXIFJQRNe4KHtri5eG3p3LY"
				+ "bA6VCc5pwx7NhtObud/6DpxQH0whDOgbUQJAAkFgKG6ptCyBueEkcbrgeBdzy1s3"
				+ "KC96kbFThWarLl0EdJysscFkzV+Byyw6EJKsripkkUtPiARrab9yX0bnJwJAYAyY"
				+ "98HJT9j81dtk7npNqxt9sq1QWEHI1o24v1udgPTLUyLw2J8Myq4nrl9Pe+PfACWm"
				+ "jvurSqyPBKOXSkBbMQJBAK0olB10HKfr7ZEJy2sxQEj/Zev1Gg/RoeXnmaALFVRb"
				+ "pcxHbNFkBxAcDB60tTG15mb64Va9KQWvPmMhlgrKT1w=";
		return RSA_PRIVATE;
	}

	public static String getRsaPublic() {
		return RSA_PUBLIC;
	}

	public static String getNotifyUrl() {
		return NOTIFY_URL;
	}
}
