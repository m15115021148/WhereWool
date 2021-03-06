package com.sitemap.na2ne.activities;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.views.MyProgressDialog;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

/**
 * 消息详情页面
 * 
 * @author chenmeng
 * 
 */
public class MessageWebViewActivity extends Activity implements OnClickListener {
	/** 本类 */
	private MessageWebViewActivity mContext;
	/** 返回上一层 */
	private LinearLayout mBack;
	/** webview */
	private WebView mWb;
	/** 进度条 */
	private MyProgressDialog progressDialog;
	/** 消息url */
	private String msgUrl;
	/**获取消息tag*/ 
	private final int GETMSG = 0x2001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message_web_view);
		progressDialog = MyProgressDialog.createDialog(this);
		mContext = this;
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(MessageWebViewActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		initView();
		initWebView();
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		mBack = (LinearLayout) findViewById(R.id.base_back_lay);
		mBack.setOnClickListener(this);
		mWb = (WebView) findViewById(R.id.msg_webview);
		msgUrl= getIntent().getStringExtra("msgUrl");
	}

	/**
	 * 初始化 webview
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {
		// 可加载js
		WebSettings setting = mWb.getSettings();

		// 设置webview自适应屏幕
		setting.setLoadWithOverviewMode(true);// 设置webview加载的页面的模式，也设置为true。这方法可以让你的页面适应手机屏幕的分辨率，完整的显示在屏幕上
		setting.setDomStorageEnabled(true);
		setting.setJavaScriptEnabled(true);
		setting.setSupportZoom(true);
		// setting.setTextSize(WebSettings.TextSize.NORMAL);
		setting.setDefaultFontSize(20);
		// 同一编码
		setting.setDefaultTextEncodingName("UTF-8");
		mWb.loadUrl(msgUrl);
		// 用webview打开该网页
		mWb.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
				// /返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if (progressDialog != null && !progressDialog.isShowing()) {
					progressDialog.setMessage("加载中...");
					progressDialog.show();
				}

			}

			@Override
			public void onPageFinished(WebView view, String url) {
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();// 关闭进度条
				}
			}

		});
	}

	@Override
	public void onClick(View v) {
		if (v == mBack) {
			onBackPressed();
			finish();
		}
	}
	
	@Override
	public void onBackPressed() {
		setResult(GETMSG);
		super.onBackPressed();
	}
	
	/**
	 * 改写物理按键——返回的逻辑
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWb.canGoBack()) {				
				mWb.goBack();// 返回上一页面
				return true;
			} else {
				onBackPressed();
				this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
