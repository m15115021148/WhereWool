package com.sitemap.na2ne.activities;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

/**
 * 
 * Description: ������ҳActivity
 * @author chenhao
 * @date   2016-1-12
 */
public class WebViewActivity extends Activity {
	private WebView web;// ��Ƕ�����
	private String url;// ��ҳ���ӵ�ַ
 private  MyProgressDialog progressDialog;// ������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
				this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ����androidϵͳ��״̬��
		
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		setContentView(R.layout.activity_webview);
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(WebViewActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		progressDialog = MyProgressDialog.createDialog(this);
		web = (WebView) findViewById(R.id.webview);
		url = getIntent().getStringExtra("url");
		initView();

	}
	
	@Override
	public void onResume() {
		super.onResume();
		// ����ͳ��
		MobclickAgent.onResume(this);
	}
	@Override
	public void onPause() {
		super.onPause();
		// ����ͳ��
		MobclickAgent.onPause(this);
	}

	/**
	 * ��ʼ��webview
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {

		// �ɼ���js
		WebSettings setting = web.getSettings();

		// ����webview����Ӧ��Ļ
		setting.setUseWideViewPort(true);// ����webview�Ƽ�����
		setting.setLoadWithOverviewMode(true);// ����webview���ص�ҳ���ģʽ��Ҳ����Ϊtrue���ⷽ�����������ҳ����Ӧ�ֻ���Ļ�ķֱ��ʣ���������ʾ����Ļ��
		setting.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);

		setting.setDomStorageEnabled(true);
		setting.setJavaScriptEnabled(true);
		// ͬһ����
		setting.setDefaultTextEncodingName("UTF-8");
		web.loadUrl(url);
		// ��webview�򿪸���ҳ
		web.setWebViewClient(new WebViewClient() {

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

	/**
	 * �ر�ҳ��
	 * 
	 * @param view
	 */
	public void close(View view) {
		this.finish();
		Intent intent = new Intent(this, HomePageActivity.class);
		startActivity(intent);
	}

	/**
	 * ��д���������������ص��߼�
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (web.canGoBack()) {
				web.goBack();// ������һҳ��
				return true;
			} else {
				// ��������ҳ
				Intent intent = new Intent(this, HomePageActivity.class);
				startActivity(intent);
				this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}