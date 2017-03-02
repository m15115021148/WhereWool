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

/**
 * 邮箱注册页面
 * 
 * @author zhang create at 2015年12月22日 下午2:02:07
 */
public class RegisterSureActivity extends Activity implements
		OnClickListener {
	private static RegisterSureActivity context;//本类
	private LinearLayout base_back_lay;//返回键
	private TextView back;//回退
	private TextView register_email_tophone_tv;//手机注册
	private EditText register_email_name_ed;//姓名
	private EditText register_email_pwd_ed;//密码
	private EditText register_email_repwd_ed;//确认密码
	private TextView register_sub;//注册按钮
	private static MyProgressDialog progressDialog;//进度条
	private HttpUtil http = null;//网络请求帮助类对象
	private final int zero=0, one=1,two=2,three=3,four=4;//0,1,2,3,4
	private TextView xieyi;//使用协议
	private String numberType;//手机号类型
	String phoneName ;//手机品牌
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		// 隐藏android系统的状态栏
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_sure);
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
				//注册
				if (msg.arg1 ==three) {
					int status = -1;
					CodeModel cm=JSON.parseObject(msg.obj.toString(), CodeModel.class);
					if (cm!=null) {
						status=cm.getStatus();
					}
					switch (status) {
					case zero:
						ToastUtil.showCenterShort(context, "注册成功！");
						RegisterPhoneActivity.context.finish();
						context.finish();
						break;
					case one:
						ToastUtil.showCenterShort(context, cm.getErrorMessage());
						break;
					case two:
						ToastUtil.showCenterShort(context, RequestCode.NULL);
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
	 * 初始化控件
	 */
	private void initview() {
		back=(TextView)findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		xieyi=(TextView)findViewById(R.id.xieyi);
		xieyi.setOnClickListener(this);
		register_sub=(TextView)findViewById(R.id.register_sub);
		register_sub.setOnClickListener(this);
		base_back_lay=(LinearLayout)findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		register_email_name_ed=(EditText)findViewById(R.id.register_email_name_ed);
		register_email_name_ed.setOnClickListener(this);
		register_email_pwd_ed=(EditText)findViewById(R.id.register_email_pwd_ed);
		register_email_pwd_ed.setOnClickListener(this);
		register_email_repwd_ed=(EditText)findViewById(R.id.register_email_repwd_ed);
		register_email_repwd_ed.setOnClickListener(this);
		if (MyApplication.isCTCCNO(context.getIntent().getStringExtra("phone"))) {
			numberType="1";
		}else if (MyApplication.isCMCCNO(context.getIntent().getStringExtra("phone"))) {
			numberType="2";
		}else if (MyApplication.isCUCCNO(context.getIntent().getStringExtra("phone"))) {
			numberType="3";
		}
		 phoneName = android.os.Build.MODEL; 
//		 Log.i("TAG", phoneName);
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
		if (v==xieyi) {
			AlertDialog.Builder normalDia = new AlertDialog.Builder(
					RegisterSureActivity.this);
			normalDia.setTitle("用户协议");
			normalDia.setMessage("使用规则\n1、在官网(http://www.na2ne.com)或各大应用商店下载“哪儿呢”APP应用→注册账号→对账号进充值→使用定位服务。\n2、公司将建立安全可靠的用户信息安全管理制度、落实技术安全防控措施，对用户在使用定位服务过程中涉及的隐私内容加以保护。\n3、鉴于网络服务的特殊性（包括但不限于服务器稳定性问题、恶意网络攻击及其他公司无法控制的情形），公司有权随时中断或终止定位服务。若发生中断或中止服务的情况，公司将尽可能及时通过网页公告、系统通知、私信、短信提醒或其他合理方式通知受到影响的用户。\n4、公司会定期或不定期的对服务平台进行检修或者维护。如因此造成服务的中断，公司不承担任何责任，但公司会尽可能事先通过网页公告、系统通知、私信、短信提醒或其他合理方式通知用户。\n5、如发生下列任何一种情形，公司都有权随时中断或终止向用户提供的服务，而无需对用户或第三方承担任何责任：\n1) 用户违反法律法规、国家政策或本协议中规定的使用规则；\n2) 用户在使用收费服务时账户余额为0，且没有及时充值；\n3)如用户在申请开通服务后连续90日内未实际使用，则公司有权对该账号进行冻结处理。若用户想重新开通该账号，可以拨打客服电话021-61132501。\n\n免责声明\n1、如用户违反使用规则2。5中所写内容，公司有权随时中断或终止用户对定位服务的使用且不承担违约责任。\n2、由于定位服务是以用户所提供的帐号为依据，所以用户不应将其帐号、密码转让或出借予他人使用。如用户发现帐号或定位服务遭他人非法使用，应立即通知公司。若因没有及时通知公司、黑客行为或用户自身的保管疏忽而产生的一切纠纷、损失和不良后果，我公司不承担任何连带责任。\n3、在使用“哪儿呢”定位服务时，移动终端持有者必须事先知道并同意被定位；若在未得到被定位移动终端用户同意的情况下进行定位，因此所产生的一切损失、纠纷和不良后果(如隐私信息泄露、非法位置获取等)，均由用户自行承担，我公司不承担任何连带责任。\n4、用户不得将通过定位服务获得的位置信息泄露给第三方进行谋利。若因此而产生的一切纠纷、损失和不良后果，我公司均不承担任何连带责任。\n5、定位服务使用者与移动终端持有者在使用过程中所产生的一切纠纷、损失与不良后果，我公司均不承担连带责任。\n6、一切因违反国家相关法律法规、政策而产生的纠纷、损失与不良后果，我公司均不承担连带责任。\n\n上海赛图计算机科技有限公司拥有对所有内容的最终解释权。");

			normalDia.setPositiveButton("我知道了",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							
						}
					});
			normalDia.create().show();
		
		}
		
		if (v == register_sub) {
			if (register_email_name_ed.getText().toString().trim().equals("")||register_email_name_ed.getText().toString().trim()==null) {
				ToastUtil.showCenterShort(context, "姓名不能为空！");
				return;
			}
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

			if (!MyApplication.isPWD(register_email_pwd_ed.getText().toString().trim())) {
				ToastUtil.showCenterShort(context, "密码长度应6-16位数字、字母、下划线！");
			return;
		}
		if (!MyApplication.isPWD(register_email_repwd_ed.getText().toString().trim())) {
			ToastUtil.showCenterShort(context, "密码长度应6-16位数字、字母、下划线！");
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
					progressDialog.setMessage("注册中...");
					progressDialog.show();
				}

				String url = WebUrlConfig.register(context
						.getIntent().getStringExtra("phone"),
						register_email_name_ed.getText().toString().trim(),
						RegisterPhoneActivity.MD5(register_email_pwd_ed
								.getText().toString().trim()), context
								.getIntent().getStringExtra("code"),numberType,phoneName);
				http.sendGet(3,url);
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
				RegisterSureActivity.this);
		normalDia.setTitle("提示");
		normalDia.setMessage("是否要退出注册？");

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
