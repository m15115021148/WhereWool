package com.sitemap.na2ne.activities;

import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSON;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.BillListviewAdapter;
import com.sitemap.na2ne.adapters.LocationDetailedListviewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.CodeModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 找回密码确认页面
 * 
 * @author zhang create at 2015年1月11日 下午4:02:07
 */
public class FindPwdSureActivity extends Activity implements OnClickListener {
	private static FindPwdSureActivity context;// 本类
	private LinearLayout base_back_lay;// 返回键
	private TextView back;// 回退
	private TextView register_email_tophone_tv;// 手机注册
	private EditText register_email_pwd_ed;// 密码
	private EditText register_email_repwd_ed;// 确认密码
	private TextView register_sub;// 注册按钮
	private static MyProgressDialog progressDialog;// 进度条
	private HttpUtil http = null;// 网络请求帮助类对象
	private final int zero = 0, one = 1, two = 2, three = 3, four = 4;// 0,1,2,3,4

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// // 隐藏android系统的状态栏
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_findpwd_sure);
		progressDialog = MyProgressDialog.createDialog(this);
		context = this;
		if (http == null) {
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(FindPwdSureActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		initview();
	}

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

	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// 关闭进度条
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// 注册
				if (msg.arg1 == three) {
					int status = -1;
					CodeModel cm = JSON.parseObject(msg.obj.toString(),
							CodeModel.class);
					if (cm != null) {
						status = cm.getStatus();
					}
					switch (status) {
					case zero:
						ToastUtil.showCenterShort(context, "修改密码成功！");
						FindPwdActivity.context.finish();
						context.finish();
						break;
					case one:
						ToastUtil.showCenterShort(context,
								cm.getErrorMessage());
						break;
					case two:
						ToastUtil.showCenterShort(context, "服务器异常！");
						break;
					default:
						break;
					}

				}

				break;
			case HttpUtil.EMPTY:
				// 返回数据为null
				if (msg.arg1 == one) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}
				if (msg.arg1 == two) {
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

	/**
	 * 初始化控件
	 */
	private void initview() {
		back = (TextView) findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		register_sub = (TextView) findViewById(R.id.register_sub);
		register_sub.setOnClickListener(this);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		register_email_pwd_ed = (EditText) findViewById(R.id.register_email_pwd_ed);
		register_email_pwd_ed.setOnClickListener(this);
		register_email_repwd_ed = (EditText) findViewById(R.id.register_email_repwd_ed);
		register_email_repwd_ed.setOnClickListener(this);
	}

	/**
	 * 返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			showNormalDia();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		if (v == base_back_lay) {
			showNormalDia();
		}
		if (v == back) {
			showNormalDia();
		}

		if (v == register_sub) {
			if (register_email_pwd_ed.getText().toString().trim().equals("")
					|| register_email_pwd_ed.getText().toString().trim() == null) {
				ToastUtil.showCenterShort(context, "密码不能为空！");
				return;
			}
			if (register_email_repwd_ed.getText().toString().trim().equals("")
					|| register_email_repwd_ed.getText().toString().trim() == null) {
				ToastUtil.showCenterShort(context, "重复密码不能为空！");
				return;
			}
			if (!MyApplication.isPWD(register_email_pwd_ed.getText().toString()
					.trim())) {
				ToastUtil.showCenterShort(context,
						"密码长度应6-16位数字、字母、下划线！");
				return;
			}
			if (!MyApplication.isPWD(register_email_repwd_ed.getText()
					.toString().trim())) {
				ToastUtil.showCenterShort(context,
						"密码长度应6-16位数字、字母、下划线！");
				return;
			}

			if (!register_email_pwd_ed
					.getText()
					.toString()
					.trim()
					.equals(register_email_repwd_ed.getText().toString().trim())) {
				ToastUtil.showCenterShort(context, "两次输入密码不一致！");
				return;
			}
			if (MyApplication.getNetObject().isNetConnected()) {
				if (progressDialog != null && !progressDialog.isShowing()) {
					progressDialog.setMessage("修改密码中...");
					progressDialog.show();
				}

				String url = WebUrlConfig
						.fixPassword(context.getIntent()
								.getStringExtra("phone"), RegisterPhoneActivity
								.MD5(register_email_pwd_ed.getText().toString()
										.trim()));
				http.sendGet(3, url);
			} else {
				ToastUtil.showCenterShort(this, RequestCode.NONETWORK);
			}
		}
	}

	/**
	 * 对话框
	 */
	private void showNormalDia() {
		AlertDialog.Builder normalDia = new AlertDialog.Builder(
				FindPwdSureActivity.this);
		normalDia.setTitle("提示");
		normalDia.setMessage("是否要退出找回密码？");

		normalDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						context.finish();

					}
				});
		normalDia.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		normalDia.create().show();
	}
}
