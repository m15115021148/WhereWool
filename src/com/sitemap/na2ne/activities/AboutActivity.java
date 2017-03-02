package com.sitemap.na2ne.activities;

import org.json.JSONObject;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ����ҳ��
 * com.sitemap.na2ne.activities.AboutActivity
 * @author zhang
 * create at 2016��1��19�� ����3:07:45
 */
public class AboutActivity extends Activity implements OnClickListener {
	private static AboutActivity context;//����
	private LinearLayout base_back_lay;// ���ؼ�
	private TextView back;// ����
	private TextView version;//�汾��
	private static MyProgressDialog progressDialog;// ������
	private final int TWO=2;//2
	private String url="";//���ص�ַ
	private WebView web;//webview
	private HttpUtil http = null;//����������������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
		progressDialog = MyProgressDialog.createDialog(this);
		context=this;
		if(http == null){
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(AboutActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		initview();
		getAbout();
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

	
	private final Handler handler = new Handler() {

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// �رս�����
			}
			switch (msg.what) {
				case HttpUtil.SUCCESS:
					// ��ù���ҳ��
					if (msg.arg1 == TWO) {
						try {
							url=new JSONObject( msg.obj.toString()).getString("url");
							if (url!=null&&!url.equals("")) {
								web.setVisibility(View.VISIBLE);
								initView();
							}
						} catch (Exception e) {
						}
					}
					break;
				case HttpUtil.EMPTY:
					ToastUtil.showCenterShort(context, RequestCode.NULL);
					break;
				case HttpUtil.FAILURE:
					ToastUtil.showCenterShort(context, RequestCode.ERRORINFO);
					break;
				case HttpUtil.LOADING:
					
					break;
				default:
					break;
			}
		}

	};
	
	
	/**
	 * ��ʼ���ؼ�
	 */
	private void initview() {
		back = (TextView) findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		web=(WebView)findViewById(R.id.gongneng_webView);
		version=(TextView)findViewById(R.id.version);
		version.setText("��ǰ�汾"+MyApplication.versionName);
	}

	@Override
	public void onClick(View v) {
		if (v == base_back_lay) {
			finish();
		}
		if (v == back) {
			finish();
		}
	}
	
	/**
	 * ��ѯ�Ƿ���ϵͳ����
	 */
	private void getAbout(){
		if (progressDialog != null && !progressDialog.isShowing()) {
			progressDialog.setMessage("���ڼ���...");
			progressDialog.show();
		}
		if (MyApplication.getNetObject().isNetConnected()) {
			http.sendGet(TWO,WebUrlConfig.getAppInfo(MyApplication.versionName));
		} else {
			ToastUtil.showCenterShort(context, "�����޷����ӣ�");
		}
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

}