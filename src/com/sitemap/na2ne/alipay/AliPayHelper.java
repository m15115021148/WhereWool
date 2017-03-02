package com.sitemap.na2ne.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.alipay.sdk.app.PayTask;
import com.sitemap.na2ne.utils.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/**
 * 
 * Description: ֧����֧������(���ֻ���û��֧�����ͻ���Ҳ����֧����ֻҪ���˺ż���)
 * 
 * @author chenhao
 * @date 2016-1-7
 */
public class AliPayHelper {

	private Activity context;// ���ô����Activity
	private Handler mHandler;// ���ô����Activity�е�Handler
	/** ֧����֧������ **/
	// �̻�PID
	private static final String PARTNER = AliPayConfig.getPartner();
	// �̻��տ��˺�
	private static final String SELLER = AliPayConfig.getSeller();
	// �̻�˽Կ��pkcs8��ʽ
	private static String RSA_PRIVATE = AliPayConfig.getRsaPrivate();
	// �������׳ɹ����첽֪ͨ��ַ
	private static String NOTIFY_URL = AliPayConfig.getNotifyUrl();

	// ֧����ʶ��
	public static final int SDK_PAY_FLAG = 1;
	// ���֧�����ͻ��˱�ʶ��
	public static final int SDK_CHECK_FLAG = 2;
	
	public final String GOODS_NAME="��ͼ��λ����";
	//�û�id
	private String userID;

	public AliPayHelper() {

	}

	public AliPayHelper(Activity  context, Handler mHandler) {
		this.context = context;
		this.mHandler = mHandler;
	}

	/**
	 * call alipay sdk pay. ����SDK֧��
	 * 
	 * @param userID
	 *            �û�ID
	 * @param goodsName
	 *            ��Ʒ���ƣ�Ĭ��ֵ����ͼ��λ����
	 * @param goodsInfo
	 *            ��Ʒ������Ĭ��ֵ����ͼ��λ����
	 * @param price
	 *            ��Ʒ�۸񣬲���С��0
	 */
	public void pay(String userID,String goodsName, String goodsInfo, String price) {
		this.userID=userID;
//		price="0.01";//����ʹ��
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(context)
					.setTitle("����")
					.setMessage("APP֧��ϵͳ�쳣���������û�����")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									dialoginterface.dismiss();
								}
							}).show();
			
			return;
		}
		if (TextUtils.isEmpty(goodsName)) {
			goodsName = GOODS_NAME;// Ĭ��ֵ
		}
		if (TextUtils.isEmpty(goodsInfo)) {
			goodsInfo = GOODS_NAME;// Ĭ��ֵ
		}
		if (!TextUtils.isEmpty(price)) {
			float money = 0;
			try {
				money = Float.parseFloat(price);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				ToastUtil.showCenterShort(context, "��ֵ������");
				return;
			}
			if (money > 0) {

			} else {
				ToastUtil.showCenterShort(context, "��ֵ����С��0��");
				return;
			}

		} else {
			ToastUtil.showCenterShort(context, "��ֵ����Ϊ�գ�");
			return;
		}
		// ����
		String orderInfo = getOrderInfo(goodsName, goodsInfo, price);

		// �Զ�����RSA ǩ��
		String sign = sign(orderInfo);
		try {
			// �����sign ��URL����
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ToastUtil.showCenterShort(context, "RSAǩ������");
			return;
		}
		// �����ķ���֧���������淶�Ķ�����Ϣ
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override												
			public void run() {
				// ����PayTask ����
				PayTask alipay = new PayTask(context);
				// ����֧���ӿڣ���ȡ֧�����
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// �����첽����
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * ��ѯ�ն��豸�Ƿ����֧������֤�˻�
	 * 
	 */
	public void checkAccount() {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// ����PayTask ����
				PayTask payTask = new PayTask(context);
				// ���ò�ѯ�ӿڣ���ȡ��ѯ���
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. ��ȡSDK�汾��
	 * 
	 */
	public String getSDKVersion() {
		PayTask payTask = new PayTask(context);
		String version = payTask.getVersion();
		return version;
	}

	/**
	 * create the order info. ����������Ϣ
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {
		//����

		// ǩԼ����������ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// ǩԼ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// ��Ʒ����(����ƴ����userID,�ԡ�$*$����body�ָ)
		orderInfo += "&body=" + "\"" + body +"$*$"+userID+ "\"";
		
		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// �������첽֪ͨҳ��·��
		orderInfo += "&notify_url=" + "\"" + NOTIFY_URL + "\"";

		// ����ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";

		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";

		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
		// orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
		// orderInfo += "&paymethod=\"expressGateway\"";
		

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. �����̻������ţ���ֵ���̻���Ӧ����Ψһ�����Զ����ʽ�淶��
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt(10);
		key = "Ali_Android_" + key.substring(0, 15);
//		Wechat_Android_
		return key;
	}

	/**
	 * sign the order info. �Զ�����Ϣ����ǩ��
	 * 
	 * @param content
	 *            ��ǩ��������Ϣ
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. ��ȡǩ����ʽ
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

}