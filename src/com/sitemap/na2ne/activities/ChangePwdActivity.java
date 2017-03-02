package com.sitemap.na2ne.activities;

import com.alibaba.fastjson.JSON;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.BillListviewAdapter;
import com.sitemap.na2ne.adapters.LocationDetailedListviewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.CodeModel;
import com.sitemap.na2ne.models.MoneyModel;
import com.sitemap.na2ne.models.RefundSubModel;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * ��������ҳ�� com.sitemap.na2ne.activities.ChangePwdActivity
 * 
 * @author zhang create at 2016��1��21�� ����4:55:48
 */
public class ChangePwdActivity extends Activity implements OnClickListener {
	private static ChangePwdActivity context;// ����
	private LinearLayout base_back_lay;// ���ؼ�
	private TextView back;// ����
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;// ����������������
	private EditText changepwd_old;// ԭʼ����
	private EditText changepwd_new;// ��������
	private EditText changepwd_re;// ȷ������
	private TextView changepwd_sub;// �޸�����
	private final int zero = 0, one = 1, two = 2, three = 3, four = 4,
			eight = 8;// 0/1/2/3/4/8����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// // ����androidϵͳ��״̬��
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changepwd);
		progressDialog = MyProgressDialog.createDialog(this);
		context = this;
		if (http == null) {
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(ChangePwdActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		initview();

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

		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// �رս�����
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				if (msg.arg1 == three) {
					int status = -1;
					CodeModel cm = JSON.parseObject(msg.obj.toString(),
							CodeModel.class);
					if (cm != null) {
						status = cm.getStatus();
						switch (status) {
						case zero:
							ToastUtil.showCenterShort(context,
									"�޸�����ɹ�,�����µ�¼��");
							SharedPreferences preferences = context
									.getSharedPreferences("user",
											Context.MODE_PRIVATE);
							Editor editor = preferences.edit();
							editor.putString("pwd", "");
							editor.commit();
							HomePageActivity.context.finish();
							Intent intent = new Intent(context,
									LoginActivity.class);
							context.startActivity(intent);
							MyApplication.luserFriend.clear();
							MyApplication.userModel = null;
							MyApplication.isLogin = -1;

							if (UserActivity.context != null) {
								UserActivity.context.finish();
							}
							if (SetActivity.context != null) {
								SetActivity.context.finish();
							}
							context.finish();
							break;
						case one:
							ToastUtil.showCenterShort(context,
									"�޸�����ʧ�ܣ�ʧ��ԭ��" + cm.getErrorMessage());
							break;
						case two:
							ToastUtil.showCenterShort(context, RequestCode.NULL);
							break;
						default:
							break;
						}
					}
				}

				break;
			case HttpUtil.EMPTY:
				// ��������Ϊnull
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
	 * ��ʼ���ؼ�
	 */
	private void initview() {
		back = (TextView) findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		changepwd_sub = (TextView) findViewById(R.id.changepwd_sub);
		changepwd_sub.setOnClickListener(this);
		changepwd_old = (EditText) findViewById(R.id.changepwd_old);
		changepwd_new = (EditText) findViewById(R.id.changepwd_new);
		changepwd_re = (EditText) findViewById(R.id.changepwd_re);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == base_back_lay) {
			finish();
		}
		if (v == back) {
			finish();
		}
		if (v == changepwd_sub) {
			if (changepwd_old.getText().toString().trim() == null
					|| changepwd_old.getText().toString().trim().equals("")) {
				ToastUtil.showCenterShort(context, "ԭʼ���벻��Ϊ�գ�");
				return;
			}
			if (changepwd_new.getText().toString().trim() == null
					|| changepwd_new.getText().toString().trim().equals("")) {
				ToastUtil.showCenterShort(context, "�������벻��Ϊ�գ�");
				return;
			}
			if (changepwd_re.getText().toString().trim() == null
					|| changepwd_re.getText().toString().trim().equals("")) {
				ToastUtil.showCenterShort(context, "ȷ�����벻��Ϊ�գ�");
				return;
			}
			// if
			// (MyApplication.isPWD(changepwd_old.getText().toString().trim()))
			// {
			// ToastUtil.showCenterShort(context,
			// "���볤��Ӧ6-16λ���֡���ĸ���»��ߣ�");
			// return;
			// }
			if (!MyApplication.isPWD(changepwd_new.getText().toString().trim())) {
				ToastUtil.showCenterShort(context,
						"���볤��Ӧ6-16λ���֡���ĸ���»��ߣ�");
				return;
			}
			if (!MyApplication.isPWD(changepwd_re.getText().toString().trim())) {
				ToastUtil.showCenterShort(context,
						"���볤��Ӧ6-16λ���֡���ĸ���»��ߣ�");
				return;
			}
			if (changepwd_old.getText().toString().trim()
					.equals(changepwd_new.getText().toString().trim())) {
				ToastUtil.showCenterShort(context, "��������������ԭʼ����һ�£�");
				return;
			}
			if (!changepwd_re.getText().toString().trim()
					.equals(changepwd_new.getText().toString().trim())) {
				ToastUtil.showCenterShort(context, "���������ȷ�����벻һ�£�");
				return;
			}
			AlertDialog.Builder normalDia = new AlertDialog.Builder(
					ChangePwdActivity.this);
			normalDia.setTitle("��ʾ");
			normalDia.setMessage("�Ƿ�ȷ���޸����룿");

			normalDia.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (MyApplication.getNetObject().isNetConnected()) {
								if (progressDialog != null
										&& !progressDialog.isShowing()) {
									progressDialog.setMessage("�޸�������...");
									progressDialog.show();
								}

								String url = WebUrlConfig.updatePassword(
										MyApplication.userModel.getUserID(),
										LoginActivity.MD5(changepwd_old
												.getText().toString().trim()),
										LoginActivity.MD5(changepwd_new
												.getText().toString().trim()));
								http.sendGet(3, url);
								dialog.dismiss();
							} else {
								ToastUtil.showCenterShort(context,
										RequestCode.NONETWORK);
							}
						}
					});
			normalDia.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			normalDia.create().show();

		}
	}

}