package com.saitu.na2ne.wxapi;

import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.MyProgressDialog;

import android.app.Activity;
import android.os.Handler;

/**
 * 
 * Description: 微信支付帮助工具栏类
 * 
 * @author chenhao
 * @date 2016-3-7
 */
public class WechatPayHelper  {
	private Handler mHandler;// 调用此类的Activity中的Handler
	private HttpUtil httpUtil = null;// 网络请求帮助类对象
	private final int ORDER=6;
	public WechatPayHelper() {

	}

	public WechatPayHelper(Handler mHandler) {
		this.mHandler = mHandler;
		
	}
	public void pay(Activity context,String money,MyProgressDialog progressDialog) {
		if (progressDialog != null && !progressDialog.isShowing()) {
			progressDialog.show();// 开启进度条
		}
		if (MyApplication.getNetObject().isNetConnected()) {
		httpUtil = new HttpUtil(mHandler);
		httpUtil.sendGet(ORDER, WechatPayConfig.getRequestPayUrl(money,MyApplication.userModel.getUserID()));
		} else {
			ToastUtil.showCenterShort(context, "网络无法连接！");
		}
	}

}
