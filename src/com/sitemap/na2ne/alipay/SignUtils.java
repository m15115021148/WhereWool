package com.sitemap.na2ne.alipay;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 
 * Description: �ңӣ����ܹ��ߣ����ﵼ��
 * @author chenhao
 * @date   2015-12-31
 */
public class SignUtils {

	private static final String ALGORITHM = "RSA";
	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String PROVIDER="BC";
	
	/**
	* RSAǩ��
	* @param content ��ǩ������
	* @param privateKey �̻�˽Կ
	* @return ǩ��ֵ
	*/
	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM,PROVIDER);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	* RSA��ǩ�����
	* @param content ��ǩ������
	* @param sign ǩ��ֵ
	* @param ali_public_key ֧������Կ
	* @param input_charset �����ʽ
	* @return ����ֵ
	*/
	public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM,PROVIDER);
	        byte[] encodedKey = Base64.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	

}
