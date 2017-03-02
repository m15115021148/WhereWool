package com.sitemap.na2ne.activities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.CodeModel;
import com.sitemap.na2ne.models.MoneyModel;
import com.sitemap.na2ne.models.UserFriendModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ��������ҳ�� com.sitemap.na2ne.activities.FriendInfoActivity
 * 
 * @author zhang create at 2016��3��23�� ����2:09:18
 */
public class FriendInfoActivity extends Activity implements OnClickListener {
	private static FriendInfoActivity context;// ����
	private LinearLayout base_back_lay;// ���ؼ�
	private TextView back;// ����
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;// ����������������
	private TextView user_phone_tv;// �û��绰
	private TextView now_taocan;// ��ǰ�ײ�
	private TextView end_time;// ����ʱ��
	private TextView user_name_tv;// �û�����
	private TextView delete;// ɾ����ť
	private TextView user_name_change_tv;// �޸ı�ע
	private int position = -1;// �����±�
	private final int ZERO = 0, ONE = 1, TWO = 2, THREE = 3, FOUR = 4,
			FIVE = 5, SIX = 6;// 0.1.2.3.4.5.6
	private boolean isdel = false;// �Ƿ�ɾ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_friend_info);
		progressDialog = MyProgressDialog.createDialog(this);
		context = this;
		if (http == null) {
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(FriendInfoActivity.this,HomePageActivity.class);
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

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// �رս�����
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// �޸��ǳ�
				if (msg.arg1 == THREE) {
					try {
						org.json.JSONObject jo = new org.json.JSONObject(
								msg.obj.toString());
						final int status = jo.getInt("status");
						if (status == 1) {
							ToastUtil.showCenterShort(context, "�޸ĳɹ���");

							String url = WebUrlConfig
									.getFriendList(MyApplication.userModel
											.getUserID());
							http.sendGet(FOUR, url);
						} else if (status == 2) {
							ToastUtil.showCenterShort(context,
									"�û��������ڣ�");
						} else if (status == 3) {
							ToastUtil.showCenterShort(context, RequestCode.NULL);
						}
					} catch (org.json.JSONException e) {
						e.printStackTrace();
					}
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// �رս�����
					}

				}
				// ��ȡ�����б�
				else if (msg.arg1 == FOUR) {
					MyApplication.luserFriend.clear();
					try {
						MyApplication.luserFriend = JSONObject.parseArray(
								msg.obj.toString(), UserFriendModel.class);
					} catch (Exception e) {
					}
					if (isdel) {
						finish();
					}
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// �رս�����
					}
				}
				// ɾ������
				else if (msg.arg1 == FIVE) {
					int status = -1;
					CodeModel cm = JSON.parseObject(msg.obj.toString(),
							CodeModel.class);
					if (cm != null) {
						status = cm.getStatus();
						switch (status) {
						case ZERO:
							isdel = true;
							String url = WebUrlConfig
									.requestMoney(MyApplication.userModel
											.getUserID());
							http.sendGet(SIX, url);
							ToastUtil.showCenterShort(context,
									"ɾ����λ����ɹ���");

							http.sendGet(FOUR, WebUrlConfig
									.getFriendList(MyApplication.userModel
											.getUserID()));
							break;
						case ONE:
							ToastUtil.showCenterShort(context,
									"ɾ����λ����ʧ�ܣ�ʧ��ԭ��" + cm.getErrorMessage());
							break;
						case TWO:
							ToastUtil.showCenterShort(context,
									RequestCode.NULL);
							break;
						default:
							break;
						}
					}
				}
				// ����
				else if (msg.arg1 == SIX) {
					MyApplication.moneyModel = JSON.parseObject(
							msg.obj.toString(), MoneyModel.class);

				}

				break;
			case HttpUtil.EMPTY:
				// ��������Ϊnull
				if (msg.arg1 == ONE) {
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
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		user_phone_tv = (TextView) findViewById(R.id.user_phone_tv);

		user_name_tv = (TextView) findViewById(R.id.user_name_tv);

		now_taocan = (TextView) findViewById(R.id.now_taocan);
		end_time = (TextView) findViewById(R.id.end_time);
		delete = (TextView) findViewById(R.id.delete);
		delete.setOnClickListener(this);
		user_name_change_tv = (TextView) findViewById(R.id.user_name_change_tv);
		user_name_change_tv.setOnClickListener(this);
		position = getIntent().getIntExtra("position", -1);
		if (position != -1) {
			user_name_tv.setText(MyApplication.luserFriend.get(position)
					.getFriendName());
			user_phone_tv.setText(MyApplication.luserFriend.get(position)
					.getFriendPhone());
			now_taocan.setText(MyApplication.luserFriend.get(position)
					.getPackageName());
			String isAgree = MyApplication.luserFriend.get(position)
					.getIsAgree();
			if ("1".equals(isAgree)) {
				String count = MyApplication.luserFriend.get(position)
						.getSurplusTimes();
				end_time.setText(count);
			}
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
		if (v == user_name_change_tv) {
			AlertDialog.Builder customDia = new AlertDialog.Builder(
					FriendInfoActivity.this);
			final View viewDia = LayoutInflater.from(FriendInfoActivity.this)
					.inflate(R.layout.alertdialog_edittext, null);
			TextView oldName = (TextView) viewDia.findViewById(R.id.old_name);
			oldName.setText(MyApplication.luserFriend.get(position)
					.getFriendName());
			customDia.setView(viewDia);
			customDia.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							EditText diaInput = (EditText) viewDia
									.findViewById(R.id.change_name_ed);

							if (diaInput.getText().toString().trim().equals("")
									|| diaInput.getText().toString().trim() == null) {
								ToastUtil.showCenterShort(context,
										"�޸ĵ����ֲ���Ϊ�գ�");
							} else {
								user_name_tv.setText(diaInput.getText()
										.toString().trim());

								if (MyApplication.getNetObject()
										.isNetConnected()) {
									if (progressDialog != null
											&& !progressDialog.isShowing()) {
										progressDialog = MyProgressDialog
												.createDialog(context);
										progressDialog
												.setMessage("�����޸Ķ�λ���뱸ע...");
										progressDialog.show();
									}

									String url = WebUrlConfig.updateName(
											MyApplication.luserFriend.get(
													position).getFriendID(),
											diaInput.getText().toString()
													.trim());
									http.sendGet(THREE, url);
								} else {
									ToastUtil.showCenterShort(context,
											RequestCode.NONETWORK);
								}
							}
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
		if (v == delete) {
			showNormalDia();
		}
	}

	/**
	 * �Ի���
	 */
	private void showNormalDia() {
		AlertDialog.Builder normalDia = new AlertDialog.Builder(
				FriendInfoActivity.this);
		normalDia.setTitle("��ܰ��ʾ");
		normalDia.setMessage("�Ƿ�Ҫɾ����λ���룿��ɾ����λ����󽫲����ٶ�����ж�λ�����趨λ��Ҫ�������Ӷ�λ���룩");

		normalDia.setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (MyApplication.getNetObject().isNetConnected()) {
							if (progressDialog != null
									&& !progressDialog.isShowing()) {
								progressDialog = MyProgressDialog
										.createDialog(context);
								progressDialog.setMessage("����ɾ����λ����...");
								progressDialog.show();
							}

							String url = WebUrlConfig.deleteFriend(
									MyApplication.userModel.getUserID(),
									MyApplication.luserFriend.get(position)
											.getFriendID());
							http.sendGet(FIVE, url);
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