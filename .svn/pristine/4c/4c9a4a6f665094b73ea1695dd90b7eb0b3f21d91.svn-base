package com.sitemap.na2ne.activities;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

/**
 * 功能页面 com.sitemap.na2ne.activities.GongNengActivity
 * 
 * @author zhang create at 2016年1月19日 下午5:40:45
 */
public class GongNengActivity extends Activity implements OnClickListener {
	private static GongNengActivity context;// 本类
	private LinearLayout base_back_lay;// 返回键
	private TextView back;// 回退
	private static MyProgressDialog progressDialog;// 进度条
	private HttpUtil http = null;// 网络请求帮助类对象
	private final int ZERO = 0, ONE = 1, TWO = 2;// 0.1.2
	private String url = "";// 加载地址
	private WebView web;// webview
	private RelativeLayout wt01,wt02,wt03,wt04,wt05,wt06,wt07,wt08;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gongneng);
		progressDialog = MyProgressDialog.createDialog(this);
		context = this;
		if (http == null) {
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(GongNengActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		initview();
		getGongNeng();
	}

	@SuppressLint("HandlerLeak")
	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// 关闭进度条
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// 获得功能页面
				if (msg.arg1 == TWO) {
					try {
						url = new JSONObject(msg.obj.toString())
								.getString("url");
//						if (url != null && !url.equals("")) {
//							web.setVisibility(View.VISIBLE);
//							initView();
//						}
					} catch (Exception e) {
					}
				}
				break;
			case HttpUtil.EMPTY:
				// 返回数据为null
				if (msg.arg1 == ONE) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}
				if (msg.arg1 == TWO) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}
				break;
			case HttpUtil.FAILURE:
				ToastUtil.showCenterShort(context,
						RequestCode.ERRORINFO);
				break;
			case HttpUtil.LOADING:

				break;
			default:
				break;
			}

		}

	};

	@Override
	public void onResume() {
		super.onResume();
		// 友盟统计
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		// 友盟统计
		MobclickAgent.onPause(this);
	}

	/**
	 * 初始化控件
	 */
	private void initview() {
		back = (TextView) findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		web = (WebView) findViewById(R.id.gongneng_webView);
		wt01 = (RelativeLayout) findViewById(R.id.wt01);
		wt01.setOnClickListener(this);
		wt02 = (RelativeLayout) findViewById(R.id.wt02);
		wt02.setOnClickListener(this);
		wt03 = (RelativeLayout) findViewById(R.id.wt03);
		wt03.setOnClickListener(this);
		wt04 = (RelativeLayout) findViewById(R.id.wt04);
		wt04.setOnClickListener(this);
		wt05 = (RelativeLayout) findViewById(R.id.wt05);
		wt05.setOnClickListener(this);
		wt06 = (RelativeLayout) findViewById(R.id.wt06);
		wt06.setOnClickListener(this);
		wt07 = (RelativeLayout) findViewById(R.id.wt07);
		wt07.setOnClickListener(this);
		wt08 = (RelativeLayout) findViewById(R.id.wt08);
		wt08.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == base_back_lay) {
			finish();
		}
		if (v == back) {
			finish();
		}
		if (v==wt01) {
			Intent intent=new Intent(GongNengActivity.this,GongNengDetailedActivity.class);
			intent.putExtra("wt", 1);
			startActivity(intent);
		}
		if (v==wt02) {
			Intent intent=new Intent(GongNengActivity.this,GongNengDetailedActivity.class);
			intent.putExtra("wt", 2);
			startActivity(intent);
		}
		if (v==wt03) {
			Intent intent=new Intent(GongNengActivity.this,GongNengDetailedActivity.class);
			intent.putExtra("wt", 3);
			startActivity(intent);
		}
		if (v==wt04) {
			Intent intent=new Intent(GongNengActivity.this,GongNengDetailedActivity.class);
			intent.putExtra("wt", 4);
			startActivity(intent);
		}
		if (v==wt05) {
			Intent intent=new Intent(GongNengActivity.this,GongNengDetailedActivity.class);
			intent.putExtra("wt", 5);
			startActivity(intent);
		}
		if (v==wt06) {
			Intent intent=new Intent(GongNengActivity.this,GongNengDetailedActivity.class);
			intent.putExtra("wt", 6);
			startActivity(intent);
		}
		if (v==wt07) {
			Intent intent=new Intent(GongNengActivity.this,GongNengDetailedActivity.class);
			intent.putExtra("wt", 7);
			startActivity(intent);
		}
		if (v==wt08) {
			Intent intent=new Intent(GongNengActivity.this,GongNengDetailedActivity.class);
			intent.putExtra("wt", 8);
			startActivity(intent);
		}
	}

	/**
	 * 查询是否有系统公告
	 */
	private void getGongNeng() {
		if (progressDialog != null && !progressDialog.isShowing()) {
			progressDialog.setMessage("正在加载...");
			progressDialog.show();
		}
		if (MyApplication.getNetObject().isNetConnected()) {
			http.sendGet(TWO,
					WebUrlConfig.getFunctionInfo(MyApplication.versionName));
		} else {
			ToastUtil.showCenterShort(context, RequestCode.NONETWORK);
		}
	}

	/**
	 * 初始化webview
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {

		// 可加载js
		WebSettings setting = web.getSettings();

		// 设置webview自适应屏幕
		setting.setUseWideViewPort(true);// 设置webview推荐窗口
		setting.setLoadWithOverviewMode(true);// 设置webview加载的页面的模式，也设置为true。这方法可以让你的页面适应手机屏幕的分辨率，完整的显示在屏幕上
		setting.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);

		setting.setDomStorageEnabled(true);
		setting.setJavaScriptEnabled(true);
		// 同一编码
		setting.setDefaultTextEncodingName("UTF-8");
		web.loadUrl(url);
		// 用webview打开该网页
		web.setWebViewClient(new WebViewClient() {

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
}
