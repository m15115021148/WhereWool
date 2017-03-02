package com.sitemap.na2ne.activities;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 功能页面 com.sitemap.na2ne.activities.GongNengActivity
 * 
 * @author zhang create at 2016年1月19日 下午5:40:45
 */
public class GongNengDetailedActivity extends Activity implements OnClickListener {
	private LinearLayout base_back_lay;// 返回键
	private TextView back;// 回退
	private static MyProgressDialog progressDialog;// 进度条
	private HttpUtil http = null;// 网络请求帮助类对象
	private int wt=0;//第几个问题
	private TextView wt_title,wt_answer,wt_img;//问题，答案，图片

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gongneng_detailed);
		progressDialog = MyProgressDialog.createDialog(this);
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(GongNengDetailedActivity.this,HomePageActivity.class);
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
		wt_title= (TextView) findViewById(R.id.wt_title);
		wt_answer= (TextView) findViewById(R.id.wt_answer);
		wt_img= (TextView) findViewById(R.id.wt_img);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		wt=getIntent().getIntExtra("wt", 1);
		switch (wt) {
		case 1:
			wt_title.setText("如何选择/切换号码，以及快捷添加号码");
			wt_answer.setText("答：在首页想要定位一个号码，先得选择，点击地图右上角的常用联系人图标，在右侧滑出的联系人列表中选择，或者切换；如果还未添加联系人，点击列表框上方的添加联系人进行添加。");
			wt_img.setBackgroundResource(R.drawable.wt1_03);
			break;
		case 2:
			wt_title.setText("对号码的定位/追踪记录如何清除");
			wt_answer.setText("答：对所选号码定位或追踪定位后，地图上会显示位置信息，点击常用号码下方的垃圾桶图标，可以清楚这些信息记录，或者点击所选号码浮框右侧的叉直接关闭对该号码的定位。");
			wt_img.setBackgroundResource(R.drawable.wt2_03);
			break;
		case 3:
			wt_title.setText("如何定位自己的位置");
			wt_answer.setText("答：点击地图左下角的定位图标即定位到自己当前位置。");
			wt_img.setBackgroundResource(R.drawable.wt3_03);
			break;
		case 4:
			wt_title.setText("如何对号码自动进行持续追踪定位");
			wt_answer.setText("答：想要对所选号码在一段时间内进行持续追踪定位，就点击地图右下角的追踪定位图标，选择追踪总时间和定位间隔时间，即自动持续定位该号码。并可以查看追踪定位的历史记录。");
			wt_img.setBackgroundResource(R.drawable.wt4_03);
			break;
		case 5:
			wt_title.setText("添加号码长时间未收到短信问题");
			wt_answer.setText("答：添加号码长时间未收到短信，可以再次添加；若提示“用户已存在”请到号码管理菜单中找到添加的号码列表，向左滑动该号码后点击 重新发送短信按钮再次发送。");
			wt_img.setBackgroundResource(R.drawable.wt5_03);
			break;
		case 6:
			wt_title.setText("如何变更套餐");
			wt_answer.setText("答：在号码管理页面左划该号码条目，点击购买套餐即可购买。");
			wt_img.setBackgroundResource(R.drawable.wt6_03);
			break;
		case 7:
			wt_title.setText("定位失败，找不到网络问题");
			wt_answer.setText("答：首先移动联通号码由于运营商限制4G网络下会定位失败，请确保对方已关闭4G网络。其次还有可能是网络原因，请稍后再试（定位不成功不扣费）。");
//			wt_img.setBackgroundResource(R.drawable.wt1_03);
			wt_img.setVisibility(View.GONE);
			break;
		case 8:
			wt_title.setText("不用了在哪里退款");
			wt_answer.setText("答：账户余额可以全部退款，进入退款记录页面后，点击右上角的申请退款按钮即可提交申请，申请后10个工作日内将金额原路退回。");
			wt_img.setBackgroundResource(R.drawable.wt8_03);
			break;
		default:
			break;
		}
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



}
