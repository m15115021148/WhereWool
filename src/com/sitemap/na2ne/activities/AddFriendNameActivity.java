package com.sitemap.na2ne.activities;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.CodeModel;
import com.sitemap.na2ne.models.MoneyModel;
import com.sitemap.na2ne.models.PackageModel;
import com.sitemap.na2ne.models.TipLableModel;
import com.sitemap.na2ne.models.UserFriendModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.utils.Log;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * com.sitemap.na2ne.activities.AddFriendPhoneActivity
 * 
 * @author zhang create at 2016��3��24�� ����4:52:43
 */
public class AddFriendNameActivity extends Activity implements OnClickListener {
	private static AddFriendNameActivity context;// ����
	private LinearLayout base_back_lay;// ���ؼ�
	private TextView back;// ����
	private EditText name_name;// ��������
	private TextView name_next;// ��һ��
	private TextView name_phone;// ����绰
	private String selectType = "";// ѡ�������
	private String numberType = "1";// �ֻ�������
	private String phonenum = "";// �ֻ���
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;// ����������������
	private final int ZERO = 0, ONE = 1, TWO = 2, THREE = 3, FOUR = 4;// 0/1/2/3/4/5/7/8/11����
	public List<PackageModel> buyPackage = new ArrayList<PackageModel>();// �����ײ�
	private String packageName;// �ײ�����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addfriend_name);
		context = this;
		progressDialog = MyProgressDialog.createDialog(this);
		if (http == null) {
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(AddFriendNameActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		initview();
	}

	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// �رս�����
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// ������
				if (msg.arg1 == TWO) {
					MyApplication.moneyModel = JSON.parseObject(
							msg.obj.toString(), MoneyModel.class);
				}
				// ��Ӻ���
				if (msg.arg1 == THREE) {
					int status = -1;
					CodeModel cm = JSON.parseObject(msg.obj.toString(),
							CodeModel.class);
					if (cm != null) {
						status = cm.getStatus();
						switch (status) {
						case ZERO:
							ToastUtil.showCenterShort(context,
									"ȷ�϶����ѷ����������ĵȴ���");
							if (MyApplication.getNetObject().isNetConnected()) {
								http.sendGet(TWO, WebUrlConfig
										.requestMoney(MyApplication.userModel
												.getUserID()));
							} 
							if (MyApplication.getNetObject().isNetConnected()) {
								http.sendGet(FOUR, WebUrlConfig
										.getFriendList(MyApplication.userModel
												.getUserID()));
							} 
							break;
						case ONE:
							ToastUtil.showCenterShort(context,cm.getErrorMessage());
							taskFinish();
							break;
						case TWO:
							ToastUtil.showCenterShort(context,
									"�����ȷ�ϣ����Ժ��ڡ��������ҳ���в鿴��˽����");
							taskFinish();
							break;
						default:
							break;
						}
					}
				}
				if (msg.arg1 == FOUR) {
					if (MyApplication.luserFriend.size() > 0) {
						MyApplication.luserFriend.clear();
					}
					try {
						MyApplication.luserFriend = JSONObject.parseArray(
								msg.obj.toString(), UserFriendModel.class);
						
					} catch (Exception e) {
					}
					taskFinish();
				}
				break;
			case HttpUtil.EMPTY:
		
			case HttpUtil.FAILURE:
				if (msg.arg1 == THREE) {
					ToastUtil.showCenterShort(context,
							"�����ȷ�ϣ����Ժ��ڡ��������ҳ���в鿴��˽����");
					AddFriendPhoneActivity.context.finish();
					AddFriendPackageActivity.context.finish();
					finish();
				} 
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
		name_next = (TextView) findViewById(R.id.name_next);
		name_phone = (TextView) findViewById(R.id.name_phone);
		name_name = (EditText) findViewById(R.id.name_name);
		name_name.setOnClickListener(this);
		name_next.setOnClickListener(this);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		phonenum = getIntent().getStringExtra("phonenum");
		name_phone.setText(phonenum);
		numberType = getIntent().getStringExtra("numberType");
		selectType = getIntent().getStringExtra("selectType");
		packageName = getIntent().getStringExtra("packageName");
	}

	@Override
	public void onClick(View v) {
		if (v == base_back_lay) {
			finish();
		}
		if (v == back) {
			finish();
		}
		if (v == name_next) {
			if (name_name.getText().toString().trim().equals("")
					|| name_name.getText().toString().trim() == null) {
				ToastUtil.showCenterShort(context, "��������ʾ���ƣ�");
				return;
			}

			AlertDialog.Builder customDia = new AlertDialog.Builder(context);
			customDia.setCancelable(false);
			final View viewDia = LayoutInflater.from(context).inflate(
					R.layout.add_frien_dialog_item, null);
			TextView tv1 = (TextView) viewDia.findViewById(R.id.tv_1);
			String str1 = "��ȷ��������Ϣ�Ƿ���ȷ��\n" + "�١�������λ�ĵ绰����Ϊ��\n" + "\t\t\t"
					+ phonenum + "\n" + "�ڡ�������ʾ����Ϊ��"
					+ name_name.getText().toString() + "\n" + "�ۡ������ײ�Ϊ��"
					+ packageName + "\n";
			List<TipLableModel> msg4 = JSON.parseArray(getIntent()
					.getStringExtra("msg4"), TipLableModel.class);
			String lable = "";// ��ɫ������
			String txt = "";// ���е���
			Log.e("result", JSON.toJSONString(msg4));
			for (int i = 0; i < msg4.size(); i++) {
				if (i == msg4.size() - 1) {
					lable = lable + msg4.get(i).getLabel();
					txt = txt + msg4.get(i).getTxt();
					break;
				}
				if (!msg4.get(i).getLabel().equals("")) {
					lable = lable + msg4.get(i).getLabel() + ";";
				}
				txt = txt + msg4.get(i).getTxt() + ";";
			}
			String result = str1 + txt;
			MyApplication.setTextColor(tv1, result.replace(";", "\n"), lable);

			customDia.setView(viewDia);
			customDia.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (MyApplication.getNetObject().isNetConnected()) {
								progressDialog.setMessage("�ύ������...");
								progressDialog.show();
								String url = WebUrlConfig.addFriend(
										MyApplication.userModel.getUserID(),
										phonenum, name_name.getText()
												.toString().trim(), selectType,
										"", numberType);
//								http.setTIMEOUT(500);
//								String url1="http://192.168.3.122/YangZi/link";
								http.sendGet(THREE, url);
								
							} else {
								ToastUtil.showCenterShort(
										AddFriendNameActivity.this,
										RequestCode.NONETWORK);
							}
							dialog.dismiss();
						}
					});
			customDia.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			customDia.create().show();

		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// ����ͳ��
		MobclickAgent.onResume(this);
	}
	
	/**
	 * ��Ӻ����������
	 */
	private void taskFinish(){
		AddFriendPhoneActivity.context.finish();
		AddFriendPackageActivity.context.finish();
		finish();
	}
	
	

	@Override
	public void onBackPressed() {
		if (progressDialog != null && progressDialog.isShowing()) {
//		���رս�����
		}else{
			super.onBackPressed();	
		}
		
	}

	@Override
	public void onPause() {
		super.onPause();
		// ����ͳ��
		MobclickAgent.onPause(this);
	}

}
