package com.sitemap.na2ne.activities;

import java.security.MessageDigest;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.BillListviewAdapter;
import com.sitemap.na2ne.adapters.LocationDetailedListviewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.CodeModel;
import com.sitemap.na2ne.models.UserModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import android.widget.Toast;

/**
 * 手机注册页面
 * 
 * @author zhang create at 2015年12月22日 下午2:02:07
 */
public class RegisterPhoneActivity extends Activity implements
		OnClickListener {
	public static RegisterPhoneActivity context;//本类
	private LinearLayout base_back_lay;//返回键
	private TextView back;//回退
	private TextView register_phone_toemail_tv;//邮箱注册
	private EditText register_phone_num_ed;//电话号码
	private EditText register_phone_name_ed;//姓名
	private EditText register_phone_code_ed;//验证码
	private EditText register_phone_pwd_ed;//密码
	private EditText register_phone_repwd_ed;//确认密码
	private TextView send_code;//发送验证码
	private TextView register_sub;//提交注册
	private Timer	timer5;//计时器
	private static MyProgressDialog progressDialog;//进度条
	private HttpUtil http = null;//网络请求帮助类对象
	private final int zero=0, one=1,two=2,three=3,four=4,nine=9;//0,1,2,3,4,9
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_phone);
		progressDialog = MyProgressDialog.createDialog(this);
		context = this;
		if(http == null){
			http = new HttpUtil(handler);
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
				// 请求验证码
				if (msg.arg1 == 2) {
//					System.out.println(msg.obj.toString());
					int status = -1;
					CodeModel cm=JSON.parseObject(msg.obj.toString(), CodeModel.class);
					if (cm!=null) {
						status=cm.getStatus();
					}
					switch (status) {
					case 0:
						
						break;
					case 1:
						send_code.setBackgroundResource(R.drawable.register_code);
						MyApplication.fayzm=60;
						send_code.setText("发送验证码");
						send_code.setTextColor(Color.rgb(255,255,255));
						send_code.setClickable(true);
						if(timer5!=null){
							timer5.cancel();
						}
						ToastUtil.showCenterShort(context, cm.getErrorMessage());
						break;
					case 2:
						send_code.setBackgroundResource(R.drawable.register_code);
						MyApplication.fayzm=60;
						send_code.setText("发送验证码");
						send_code.setTextColor(Color.rgb(255,255,255));
						send_code.setClickable(true);
						if(timer5!=null){
							timer5.cancel();
						}
						ToastUtil.showCenterShort(context, RequestCode.NULL);
						break;
					default:
						send_code.setBackgroundResource(R.drawable.register_code);
						MyApplication.fayzm=60;
						send_code.setText("发送验证码");
						send_code.setTextColor(Color.rgb(255,255,255));
						send_code.setClickable(true);
						if(timer5!=null){
							timer5.cancel();
						}
						ToastUtil.showCenterShort(context, RequestCode.NULL);
						break;
					}

				}
				//注册
				if (msg.arg1 ==3) {
					int status = -1;
					CodeModel cm=JSON.parseObject(msg.obj.toString(), CodeModel.class);
					if (cm!=null) {
						status=cm.getStatus();
					}
					switch (status) {
					case 0:
						ToastUtil.showCenterShort(context, "注册成功！");
						context.finish();
						break;
					case 1:
						ToastUtil.showCenterShort(context, cm.getErrorMessage());
						send_code.setBackgroundResource(R.drawable.register_code);
						MyApplication.fayzm=60;
						send_code.setText("发送验证码");
						send_code.setTextColor(Color.rgb(255,255,255));
						send_code.setClickable(true);
						if(timer5!=null){
							timer5.cancel();
						}
						break;
					case 2:
						ToastUtil.showCenterShort(context, RequestCode.NULL);
						send_code.setBackgroundResource(R.drawable.register_code);
						MyApplication.fayzm=60;
						send_code.setText("发送验证码");
						send_code.setTextColor(Color.rgb(255,255,255));
						send_code.setClickable(true);
						if(timer5!=null){
							timer5.cancel();
						}
						break;
					default:
						send_code.setBackgroundResource(R.drawable.register_code);
						MyApplication.fayzm=60;
						send_code.setText("发送验证码");
						send_code.setTextColor(Color.rgb(255,255,255));
						send_code.setClickable(true);
						if(timer5!=null){
							timer5.cancel();
						}
						ToastUtil.showCenterShort(context, RequestCode.NULL);
						break;
					}

				}
				//对比验证码
				if (msg.arg1 ==four) {
					int status = -1;
					CodeModel cm=JSON.parseObject(msg.obj.toString(), CodeModel.class);
					if (cm!=null) {
						status=cm.getStatus();
					}
					switch (status) {
					case zero:
						Intent intent =new Intent(RegisterPhoneActivity.this,RegisterSureActivity.class);
						intent.putExtra("phone", register_phone_num_ed.getText().toString().trim());
						intent.putExtra("code", register_phone_code_ed.getText().toString().trim());
						startActivity(intent);
						if(timer5!=null){
							timer5.cancel();
						}
						MyApplication.fayzm=60;
						send_code.setText("发送验证码");
						send_code.setTextColor(Color.rgb(255,255,255));
						send_code.setClickable(true);
						
						context.finish();
					
						
						break;
					case one:
						ToastUtil.showCenterShort(context, cm.getErrorMessage());
						break;
					case two:
						ToastUtil.showCenterShort(context, RequestCode.NULL);
						send_code.setBackgroundResource(R.drawable.register_code);
						MyApplication.fayzm=60;
						send_code.setText("发送验证码");
						send_code.setTextColor(Color.rgb(255,255,255));
						send_code.setClickable(true);
						if(timer5!=null){
							timer5.cancel();
						}
						break;
					default:
						send_code.setBackgroundResource(R.drawable.register_code);
						MyApplication.fayzm=60;
						send_code.setText("发送验证码");
						send_code.setTextColor(Color.rgb(255,255,255));
						send_code.setClickable(true);
						if(timer5!=null){
							timer5.cancel();
						}
						ToastUtil.showCenterShort(context, RequestCode.NULL);
						break;
					}

				}

				break;
			case HttpUtil.EMPTY:
				// 返回数据为null
				if (msg.arg1 == 1) {
					ToastUtil.showCenterShort(context,RequestCode.NULL);
					send_code.setBackgroundResource(R.drawable.register_code);
					MyApplication.fayzm=60;
					send_code.setText("发送验证码");
					send_code.setTextColor(Color.rgb(255,255,255));
					send_code.setClickable(true);
					if(timer5!=null){
						timer5.cancel();
					}
				}
				if (msg.arg1 == 2) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
					send_code.setBackgroundResource(R.drawable.register_code);
					MyApplication.fayzm=60;
					send_code.setText("发送验证码");
					send_code.setTextColor(Color.rgb(255,255,255));
					send_code.setClickable(true);
					if(timer5!=null){
						timer5.cancel();
					}
				}		
				break;
			case HttpUtil.FAILURE:
				ToastUtil.showCenterShort(context, RequestCode.ERRORINFO);
				send_code.setBackgroundResource(R.drawable.register_code);
				MyApplication.fayzm=60;
				send_code.setText("发送验证码");
				send_code.setTextColor(Color.rgb(255,255,255));
				send_code.setClickable(true);
				if(timer5!=null){
					timer5.cancel();
				}
				break;
			case HttpUtil.LOADING:
				
				break;
			case 9:
				MyApplication.fayzm--;
				send_code.setText("已发送（"+MyApplication.fayzm+"）");
				if (MyApplication.fayzm==0) {
					send_code.setBackgroundResource(R.drawable.register_code);
					MyApplication.fayzm=60;
					send_code.setText("发送验证码");
					send_code.setTextColor(Color.rgb(255,255,255));
					send_code.setClickable(true);
					if(timer5!=null){
						timer5.cancel();
					}
				}
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
		back=(TextView)findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		send_code=(TextView)findViewById(R.id.send_code);
		send_code.setOnClickListener(this);
		register_sub=(TextView)findViewById(R.id.register_sub);
		register_sub.setOnClickListener(this);
		register_phone_num_ed=(EditText)findViewById(R.id.register_phone_num_ed);
		register_phone_num_ed.setOnClickListener(this);
//		register_phone_name_ed=(EditText)findViewById(R.id.register_phone_name_ed);
//		register_phone_name_ed.setOnClickListener(this);
		register_phone_code_ed=(EditText)findViewById(R.id.register_phone_code_ed);
		register_phone_code_ed.setOnClickListener(this);
//		register_phone_pwd_ed=(EditText)findViewById(R.id.register_phone_pwd_ed);
//		register_phone_pwd_ed.setOnClickListener(this);
//		register_phone_repwd_ed=(EditText)findViewById(R.id.register_phone_repwd_ed);
//		register_phone_repwd_ed.setOnClickListener(this);
		base_back_lay=(LinearLayout)findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
//		register_phone_toemail_tv=(TextView)findViewById(R.id.register_phone_toemail_tv);
//		register_phone_toemail_tv.setOnClickListener(this);
	}
	
	/**
	 * 对话框
	 */
	private void showNormalDia() {
		AlertDialog.Builder normalDia = new AlertDialog.Builder(
				RegisterPhoneActivity.this);
		normalDia.setTitle("提示");
		normalDia.setMessage("是否要退出注册？");

		normalDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						MyApplication.fayzm=60;
						send_code.setText("发送验证码");
						send_code.setBackgroundResource(R.drawable.register_code);
						send_code.setTextColor(Color.rgb(255,255,255));
						send_code.setClickable(true);
						if(timer5!=null){
							timer5.cancel();
						}
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
		if (v==base_back_lay) {
			showNormalDia();
		}
		if (v==back) {
			showNormalDia();
		}
//		if (v==register_phone_toemail_tv) {
//			Intent intent = new Intent(context, RegisterEmailActivity.class);
//			context.startActivity(intent);
//			context.finish();
//		}
		if (v==send_code) {
			if (isMobileNO(register_phone_num_ed.getText().toString().trim())) {
			if (register_phone_num_ed.getText().toString().trim().equals("")||register_phone_num_ed.getText().toString().trim()==null) {
				ToastUtil.showCenterShort(context, "手机号不能为空！");
				return;
			}else {
				MyApplication.shouji=register_phone_num_ed.getText().toString().trim();
				send_code.setBackgroundResource(R.drawable.register_codehui);
				send_code.setText("已发送（"+MyApplication.fayzm+"）");
//				send_code.setTextColor(Color.rgb(153, 153, 153));
				send_code.setClickable(false);
				 timer5= new Timer();
					timer5.schedule(new TimerTask() {

						@Override
						public void run() {
								handler.sendEmptyMessage(9);
						}
							
						},10, 1000);
				if (MyApplication.getNetObject().isNetConnected()) {
				String url = WebUrlConfig.requestCode(register_phone_num_ed.getText().toString().trim());
				http.sendGet(2,url);
				} else {
					ToastUtil.showCenterShort(this, RequestCode.NONETWORK);
					send_code.setBackgroundResource(R.drawable.register_code);
					MyApplication.fayzm=60;
					send_code.setText("发送验证码");
					send_code.setTextColor(Color.rgb(255,255,255));
					send_code.setClickable(true);
					if(timer5!=null){
						timer5.cancel();
					}
				}
			}
			}else {
				ToastUtil.showCenterShort(context, "请输入正确的手机号！");
				return;
			}
		}
		if (v==register_sub) {
			if (register_phone_num_ed.getText().toString().trim().equals("")||register_phone_num_ed.getText().toString().trim()==null) {
				ToastUtil.showCenterShort(context, "手机号不能为空！");
				return;
			}
//			if (register_phone_name_ed.getText().toString().trim().equals("")||register_phone_name_ed.getText().toString().trim()==null) {
//				ToastUtil.showCenterShort(context, "姓名不能为空！");
//				return;
//			}
			if (register_phone_code_ed.getText().toString().trim().equals("")||register_phone_code_ed.getText().toString().trim()==null) {
				ToastUtil.showCenterShort(context, "验证码不能为空！");
				return;
			}
//			if (register_phone_pwd_ed.getText().toString().trim().length()<6||register_phone_pwd_ed.getText().toString().trim().length()<6) {
//				ToastUtil.showCenterShort(context, "密码长度至少为6位！");
//				return;
//			}
//			if (register_phone_repwd_ed.getText().toString().trim().length()<6||register_phone_repwd_ed.getText().toString().trim().length()<6) {
//				ToastUtil.showCenterShort(context, "重复密码长度至少为6位！");
//				return;
//			}
//			if (register_phone_pwd_ed.getText().toString().trim().equals("")||register_phone_pwd_ed.getText().toString().trim()==null) {
//				ToastUtil.showCenterShort(context, "密码不能为空！");
//				return;
//			}
//			if (register_phone_repwd_ed.getText().toString().trim().equals("")||register_phone_repwd_ed.getText().toString().trim()==null) {
//				ToastUtil.showCenterShort(context, "重复密码不能为空！");
//				return;
//			}
			if (!register_phone_num_ed.getText().toString().trim().equals(MyApplication.shouji)) {
				ToastUtil.showCenterShort(context, "手机号与验证手机不一致！");
				return;
			}
			
//			if (!register_phone_pwd_ed.getText().toString().trim().equals(register_phone_repwd_ed.getText().toString().trim())) {
//				ToastUtil.showCenterShort(context, "两次输入密码不一致！");
//				return;
//			}
//			if (MyApplication.getNetObject().isNetConnected()) {
//				progressDialog.setMessage("注册中...");
//				progressDialog.show();
//				httpUtil = new XUtilsHelper(WebUrlConfig.register(register_phone_num_ed.getText().toString().trim(), register_phone_name_ed.getText().toString().trim(), MD5(register_phone_pwd_ed.getText().toString().trim()), register_phone_code_ed.getText().toString().trim()), handler);
//				httpUtil.sendGet(3);
//			} else {
//				ToastUtil.showCenterShort(this, "网络无法连接！");
//			}
			if (MyApplication.getNetObject().isNetConnected()) {
				if (progressDialog != null && !progressDialog.isShowing()) {
					progressDialog.setMessage("验证中...");
					progressDialog.show();
				}
				
				String url = WebUrlConfig.compareCode(register_phone_num_ed.getText().toString().trim(), register_phone_code_ed.getText().toString().trim());
				http.sendGet(four,url);
			} else {
				ToastUtil.showCenterShort(this, RequestCode.NONETWORK);
			}
			
		}
	}
	
	/**
	 * MD5加密，32位
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toUpperCase();
	}
	
	/**
	 * 验证手机号
	 * @param mobiles
	 * @return
	 */
	public boolean isMobileNO(String mobiles) {
		return mobiles.length()==11?true:false;
		}
}
