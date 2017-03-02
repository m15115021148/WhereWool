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
 * ��Ϣ����ҳ��
 * 
 * @author chenmeng
 * 
 */
public class MessageWebViewActivity extends Activity implements OnClickListener {
	/** ���� */
	private MessageWebViewActivity mContext;
	/** ������һ�� */
	private LinearLayout mBack;
	/** webview */
	private WebView mWb;
	/** ������ */
	private MyProgressDialog progressDialog;
	/** ��Ϣurl */
	private String msgUrl;
	/**��ȡ��Ϣtag*/ 
	private final int GETMSG = 0x2001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
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
	 * ��ʼ��view
	 */
	private void initView() {
		mBack = (LinearLayout) findViewById(R.id.base_back_lay);
		mBack.setOnClickListener(this);
		mWb = (WebView) findViewById(R.id.msg_webview);
		msgUrl= getIntent().getStringExtra("msgUrl");
	}

	/**
	 * ��ʼ�� webview
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {
		// �ɼ���js
		WebSettings setting = mWb.getSettings();

		// ����webview����Ӧ��Ļ
		setting.setLoadWithOverviewMode(true);// ����webview���ص�ҳ���ģʽ��Ҳ����Ϊtrue���ⷽ�����������ҳ����Ӧ�ֻ���Ļ�ķֱ��ʣ���������ʾ����Ļ��
		setting.setDomStorageEnabled(true);
		setting.setJavaScriptEnabled(true);
		setting.setSupportZoom(true);
		// setting.setTextSize(WebSettings.TextSize.NORMAL);
		setting.setDefaultFontSize(20);
		// ͬһ����
		setting.setDefaultTextEncodingName("UTF-8");
		mWb.loadUrl(msgUrl);
		// ��webview�򿪸���ҳ
		mWb.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
				// /����ֵ��true��ʱ�����ȥWebView�򿪣�Ϊfalse����ϵͳ�����������������
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if (progressDialog != null && !progressDialog.isShowing()) {
					progressDialog.setMessage("������...");
					progressDialog.show();
				}

			}

			@Override
			public void onPageFinished(WebView view, String url) {
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();// �رս�����
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
	 * ��д���������������ص��߼�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWb.canGoBack()) {				
				mWb.goBack();// ������һҳ��
				return true;
			} else {
				onBackPressed();
				this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}