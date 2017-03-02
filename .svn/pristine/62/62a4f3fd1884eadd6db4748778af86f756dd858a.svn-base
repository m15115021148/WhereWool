package com.sitemap.na2ne.activities;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 设置页面
 * 
 * @author zhang create at 2015年12月24日
 */
public class SetActivity extends Activity implements OnClickListener {
	public static SetActivity context;// 本类
	private LinearLayout base_back_lay;// 返回键
	private TextView back;// 回退
	private TextView userOut;// 退出按钮
	private RelativeLayout user_guanyu_lay;//关于
	private RelativeLayout user_gongneng_lay;//功能介绍
	private RelativeLayout user_safe_lay;//账户与安全
	private RelativeLayout yijian_lay;//意见与反馈
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_set);
		context = this;
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(SetActivity.this,HomePageActivity.class);
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

	/**
	 * 初始化控件
	 */
	private void initview() {
		back = (TextView) findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		userOut = (TextView) findViewById(R.id.user_out_tv);
		userOut.setOnClickListener(this);
		user_guanyu_lay=(RelativeLayout)findViewById(R.id.user_guanyu_lay);
		user_guanyu_lay.setOnClickListener(this);
		user_gongneng_lay=(RelativeLayout)findViewById(R.id.user_gongneng_lay);
		user_gongneng_lay.setOnClickListener(this);
		user_safe_lay=(RelativeLayout)findViewById(R.id.user_safe_lay);
		user_safe_lay.setOnClickListener(this);
		yijian_lay=(RelativeLayout)findViewById(R.id.yijian_lay);
		yijian_lay.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == base_back_lay) {
			finish();
		}
		if (v == back) {
			finish();
		}
		if (v == userOut) {
			showNormalDia();
		}
		if (v==user_guanyu_lay) {
			Intent intent=new Intent(context,AboutActivity.class);
			startActivity(intent);
		}
		if (v==user_gongneng_lay) {
			Intent intent=new Intent(context,GongNengActivity.class);
			startActivity(intent);
		}
		if (v==user_safe_lay) {
			Intent intent=new Intent(context,ChangePwdActivity.class);
			startActivity(intent);
		}
		if (v==yijian_lay) {
			Intent intent=new Intent(context,YiJianActivity.class);
			startActivity(intent);
		}
	}

	/**
	 * 对话框
	 */
	private void showNormalDia() {
		// AlertDialog.Builder normalDialog=new
		// AlertDialog.Builder(getApplicationContext());
		AlertDialog.Builder normalDia = new AlertDialog.Builder(
				SetActivity.this);
		normalDia.setTitle("提示");
		normalDia.setMessage("是否要注销账户？");

		normalDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
						Editor editor=preferences.edit();
						editor.putString("pwd","");
						editor.putString("notiID","0");
						editor.commit();
						HomePageActivity.context.finish();
						Intent intent = new Intent(context, LoginActivity.class);
						context.startActivity(intent);
						MyApplication.luserFriend.clear();
						MyApplication.userModel=null;
						MyApplication.isLogin=-1;
						MyApplication.notiID="0";
						if (UserActivity.context!=null) {
							UserActivity.context.finish();
						}
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

	/**
	 * 退出应用程序按钮功能
	 */
	@SuppressWarnings("deprecation")
	public void exit() {
		// 设置程序完全退出
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
			System.exit(0);
			
		} else {// android2.1
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			am.restartPackage(getPackageName());
		}

	}
}
