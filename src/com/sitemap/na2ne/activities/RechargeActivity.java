package com.sitemap.na2ne.activities;

import com.alibaba.fastjson.JSON;
import com.saitu.na2ne.R;
import com.saitu.na2ne.wxapi.WechatPayHandler;
import com.saitu.na2ne.wxapi.WechatPayHelper;
import com.sitemap.na2ne.alipay.AliPayHandler;
import com.sitemap.na2ne.alipay.AliPayHelper;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.MoneyModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.CircleBgView;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 充值页面
 * 
 * @author zhang create at 2015年12月22日 下午2:02:07
 * 
 * @fix 添加金额选择RadioGroup的点击事件 ----陈浩、2015-12-30
 * @fix 添加微信支付相关功能 ----陈浩、2016-12-19
 */
public class RechargeActivity extends Activity implements OnClickListener,
		TextWatcher, OnTouchListener {
	private static RechargeActivity context;// 本类
	private LinearLayout back;// 返回键
	private EditText editText = null;// 其他金额输入框
	private TextView money;// 选择金额后显示T的extView
	private TextView recharge_10;// 默认选中的TextView
	private TextView tempTextView;// 被选中的金额TextView
	private AliPayHelper aliPayHelper;// 付款工具
	private AliPayHandler aliPayHandler;// 付款异步处理类
	private TextView recharge_yue;// 用户余额
	private static MyProgressDialog progressDialog;// 进度条
	private HttpUtil http = null;//网络请求帮助类对象
	private final int  two = 2;// 2数字
	private boolean isclick = true;// 支付按钮是否点击
	private TextView pay;// 充值按钮
	private String packageMoney = "0";// 获得的套餐金额
	// 支付方式
	private final int PAY_ALI=0;//阿里方式
	private final int PAY_WECHAT=1;//微信方式
	private int payType = PAY_ALI; // 0:阿里，1：微信；默认0
	private WechatPayHelper wechatPayHelper;// 付款工具
	private WechatPayHandler wechatPayHandler;// 付款异步处理类
	private TextView aliView;//阿里支付按钮
	private TextView wechatView;//微信支付按钮
	private final int MIN_MONEY=20;//最低充值金额
	private boolean isFirst=true;//是否是第一次启动
	private TextView back_tv;//返回箭头
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_recharge);
		context = this;
		if(http == null){
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(RechargeActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		initview();
		initAliPay();
	}

	/**
	 * 选择支付方式
	 * 
	 * @param PayView
	 *            支付按钮
	 */
	public void setPayType(View payView) {
		((TextView) payView).setTextColor(getResources().getColor(R.color.lan));
		if (payView.getId() == R.id.pay_ali) {
//			如果直接使用R.color.lan，则不能正常显示;
			wechatView.setTextColor(getResources().getColor(R.color.black));
			payType =PAY_ALI;
			initAliPay();
		}
		if (payView.getId() == R.id.pay_wechat) {
			aliView.setTextColor(getResources().getColor(R.color.black));
			payType = PAY_WECHAT;
			initWechatPay();
		}
	}

	@SuppressLint("HandlerLeak")
	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
//			恢复充值按钮的可点击性
			pay.setClickable(true);
			isclick = true;
			if(msg.what==WechatPayHandler.WECHAT_EXCEPTION){
				//微信支付异常
			}
				if (msg.what == HttpUtil.SUCCESS) {
				// 获得余额
				if (msg.arg1 == two) {
					MyApplication.moneyModel = JSON.parseObject(
							msg.obj.toString(), MoneyModel.class);
					if (MyApplication.moneyModel != null) {
						recharge_yue.setText(MyApplication.moneyModel
								.getBalance());
					} else {
						ToastUtil.showCenterShort(context,
								"服务器忙,更新余额失败！");
					}

				}

			} else if (msg.what == HttpUtil.FAILURE) {
				// 获取数据异常
			} else if (msg.what == HttpUtil.EMPTY) {
				// 返回数据为null
				if (msg.arg1 == two) {
				}
			}
		}

	};

	@Override
	public void onResume() {
		super.onResume();
		// 友盟统计
		MobclickAgent.onResume(this);
		if(!isFirst){
			requestMoney();
		}else{
			isFirst=false;
		}
		
	}

	@Override
	public void onPause() {
		super.onPause();
		// 友盟统计
		MobclickAgent.onPause(this);
	}

	/**
	 * 初始化阿里支付环境
	 */
	private void initAliPay() {
		// 注意以下代码顺序不可变
		if (aliPayHandler == null) {
			aliPayHandler = new AliPayHandler(this);
		}
		if (aliPayHelper == null) { 
			aliPayHelper = new AliPayHelper(this, aliPayHandler);
		}

	}

	/**
	 * 初始化微信支付
	 * 
	 */
	private void initWechatPay() {
		// 注意以下代码顺序不可变
		if (wechatPayHandler == null) {
			progressDialog = MyProgressDialog.createDialog(this);
			wechatPayHandler = new WechatPayHandler(this,progressDialog,handler);
		}
		if (wechatPayHelper == null) {
			wechatPayHelper = new WechatPayHelper(wechatPayHandler);
		}
	}

	/**
	 * 初始化控件
	 */
	private void initview() {
		back = (LinearLayout) findViewById(R.id.base_back_lay);
		back.setOnClickListener(this);
		back_tv=(TextView) findViewById(R.id.back_tv);
		back_tv.setOnClickListener(this);
		recharge_10 = (TextView) findViewById(R.id.recharge_10);
		tempTextView = recharge_10;// tempTextView初始化
		// 金额选择
		money = (TextView) findViewById(R.id.money);
		// 金额自定义输入
		editText = (EditText) findViewById(R.id.recharge_other);
		editText.addTextChangedListener(this); // EditText添加输入监听
		editText.setOnTouchListener(this);
		recharge_yue = (TextView) findViewById(R.id.recharge_yue);
		aliView = (TextView) findViewById(R.id.pay_ali);
		wechatView = (TextView) findViewById(R.id.pay_wechat);
		recharge_yue.setText(MyApplication.moneyModel.getBalance() + "元");
		pay = (TextView) findViewById(R.id.pay);

		packageMoney = getIntent().getStringExtra("packageMoney");
		// 从其他页面跳转到此页面时，调用此段代码
		if (packageMoney != null && !packageMoney.equals("0")) {
			editText.setText(String.valueOf(packageMoney));
			money.setText(packageMoney);
			resetSelectView();
		}

	}

	@Override
	public void onClick(View v) {
		// 返回
		if (v == back) {
			finish();
			return;
		}
		if (v==back_tv) {
			finish();
		}
	
		// 充值
		if (v == pay) {
			if (!isclick) {
//				不可重复点击充值按钮
				return;
			}
			if (money.getText().toString().trim().equals("")) {
				ToastUtil.showCenterShort(RechargeActivity.this,
						"充值金额不能为空！");
				return;
			}

			int rechargeMoney=0;
			try {
				rechargeMoney = Integer.parseInt(money.getText().toString()
						.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
//				输入的数字大小超过Int长度限制
				ToastUtil.showCenterShort(RechargeActivity.this,
						"本次充值金额过大，无法充值！");
				return;
			}
			if (rechargeMoney < MIN_MONEY) {
				ToastUtil.showCenterShort(RechargeActivity.this,
						"充值金额最低"+MIN_MONEY+"元！");
				return;
			}

			if (payType == PAY_ALI) {
				// 前两个参数可为空
				aliPayHelper.pay(MyApplication.userModel.getUserID(), "", "",
						money.getText().toString().trim());
			}
			if (payType == PAY_WECHAT) {
				wechatPayHelper.pay(this,money.getText().toString().trim(),progressDialog);
			}
			pay.setClickable(false);
			isclick = false;
			return;
		}

		// 选择金额
		resetSelectView(v.getId());

	}

	/**
	 * 重置金额选择框背景,点击TextView控件时的切换
	 * 
	 * @param id
	 *            被选中控件id
	 */
	private void resetSelectView(int id) {
		tempTextView.setBackground(getResources().getDrawable(
				R.drawable.recharge_jine_bg1));
		TextView newSelected = (TextView) findViewById(id);
		newSelected.setBackground(getResources().getDrawable(
				R.drawable.recharge_jine_bg2));
		tempTextView = newSelected;
		String msg = newSelected.getText().toString().replace("元", "");
		money.setText(msg.trim());
		editText.setBackground(getResources().getDrawable(
				R.drawable.recharge_jine_bg1));

	}

	/**
	 * 重置金额选择框背景,点击EditText控件时的切换
	 * 
	 */
	private void resetSelectView() {
		// 兼容EditText和TextView的背景切换
		tempTextView.setBackground(getResources().getDrawable(
				R.drawable.recharge_jine_bg1));
		editText.setBackground(getResources().getDrawable(
				R.drawable.recharge_jine_bg2));
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void afterTextChanged(Editable s) {
		// 实时记录EditText中输入的数字
		money.setText(editText.getText());
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		resetSelectView();
		return false;
	}
	
	/**
	 * 请求账户余额
	 */
	private void requestMoney(){
		if (MyApplication.getNetObject().isNetConnected()) {
			http.sendGet(two,WebUrlConfig.requestMoney(MyApplication.userModel
					.getUserID()));
		} else {
			ToastUtil.showCenterShort(this, RequestCode.NONETWORK);
		}
	}

	
}
