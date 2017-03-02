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
import com.sitemap.na2ne.models.MoneyModel;
import com.sitemap.na2ne.models.PackageModel;
import com.sitemap.na2ne.models.TipLableModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.utils.WheelView;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * com.sitemap.na2ne.activities.AddFriendPhoneActivity
 * 
 * @author zhang create at 2016��3��24�� ����4:52:43
 */
public class AddFriendPackageActivity extends Activity implements OnClickListener {
	public static AddFriendPackageActivity context;// ����
	private LinearLayout base_back_lay;// ���ؼ�
	private TextView back;// ����
	private TextView package_name;// �ײ�����
	private TextView package_next;// ��һ��
	private TextView package_phone;// ����绰
	private String numberType = "1";// �ֻ�������
	private String phonenum = "";// �ֻ���
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;// ����������������
	private final int ZERO = 0, ONE = 1, TWO = 2, SEVEN = 7;// 0/1/2/3/4/5/7/8/11����
	public List<PackageModel> buyPackage = new ArrayList<PackageModel>();// �����ײ�
	private String selectType = "";// ѡ�������
	private String jine;// ѡ��Ľ��
	private String packageType = "";// �ײ�����
	// ѡ��λ��
	private int packageSelect = 0;
	// �ײ�����
	private List<String> buyType = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addfriend_package);
		context = this;
		progressDialog = MyProgressDialog.createDialog(this);
		if (http == null) {
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(AddFriendPackageActivity.this,HomePageActivity.class);
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
				// ������
				if (msg.arg1 == TWO) {
					MyApplication.moneyModel = JSON.parseObject(
							msg.obj.toString(), MoneyModel.class);
					if (MyApplication.moneyModel != null) {
					} else {
						// ToastUtil.showCenterShort(context,
						// "��������æ�����Ժ����ԣ�");
					}

				}
				// ����ײ��б�
				if (msg.arg1 == SEVEN) {
					buyType.clear();
					buyPackage = JSONObject.parseArray(msg.obj.toString(),
							PackageModel.class);
					if (buyPackage.size() > 0) {
						for (int i = 0; i < buyPackage.size(); i++) {
							buyType.add(buyPackage.get(i).getPackageName());
						}
						package_name.setText(buyType.get(packageSelect));
						package_name.callOnClick();
					} else {
						ToastUtil.showCenterShort(context, "������ѡ���ײ���Ϣ");
					}
				}
				break;
			case HttpUtil.EMPTY:
				// ��������Ϊnull
				if (msg.arg1 == ONE) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
					finish();
				}
				if (msg.arg1 == TWO) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
					finish();
				}
				if(msg.arg1 == SEVEN){
					ToastUtil.showCenterShort(context, "������ѡ���ײ���Ϣ");
				}
				break;
			case HttpUtil.FAILURE:
				ToastUtil.showCenterShort(context,
						RequestCode.ERRORINFO);
				finish();
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
		package_next = (TextView) findViewById(R.id.package_next);
		package_phone = (TextView) findViewById(R.id.package_phone);
		package_name = (TextView) findViewById(R.id.package_name);
		package_name.setOnClickListener(this);
		package_next.setOnClickListener(this);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		phonenum = getIntent().getStringExtra("phonenum");
		package_phone.setText(phonenum);
		numberType = getIntent().getStringExtra("numberType");

		if (MyApplication.getNetObject().isNetConnected()) {
			if (progressDialog != null && !progressDialog.isShowing()) {
				progressDialog.setMessage("���ڻ���ײ��б�...");
				progressDialog.show();
			}
			http.sendGet(SEVEN,
					WebUrlConfig.getPackageInfo(phonenum, numberType));
		} else {
			ToastUtil.showCenterShort(context, RequestCode.NONETWORK);
		}
		if (MyApplication.getNetObject().isNetConnected()) {
			http.sendGet(TWO, WebUrlConfig.requestMoney(MyApplication.userModel
					.getUserID()));
		} else {
			ToastUtil.showCenterShort(this, RequestCode.NONETWORK);
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
		if (v == package_next) {
			if (package_name.getText().toString().equals("��ѡ��")) {
				ToastUtil.showCenterShort(context, "��ѡ���ײͣ�");
				return;
			}
			jine = buyPackage.get(packageSelect).getPackagePrice();
			selectType = buyPackage.get(packageSelect).getPackageID();
			packageType = buyPackage.get(packageSelect).getPackageType();

			if (Integer.parseInt(jine) > Double
					.parseDouble(MyApplication.moneyModel.getBalance())) {
				ToastUtil.showCenterShort(this, "�������ֵ��");
				Intent intent = new Intent(AddFriendPackageActivity.this,
						RechargeActivity.class);
				intent.putExtra("packageMoney", jine);
				startActivity(intent);
				return;
			}
			AlertDialog.Builder customDia = new AlertDialog.Builder(context);
			customDia.setCancelable(false);
			final View viewDia = LayoutInflater.from(context).inflate(
					R.layout.add_frien_dialog_item, null);
			TextView tv1 = (TextView) viewDia.findViewById(R.id.tv_1);
			// ���
			if (packageType.equals("4")) {
				String result = "1������ײ���ʾ��λ�ò���ʵʱλ�ã�\n2��ֻ�е��Է���������װ��APPʱ�����ܻ�ȡ�Է�λ�ã����򽫻ᶨλʧ�ܣ�\n3�������ײͻ�ȡ��λ����ϢΪ�Է���ʵʱλ�ã��Ҳ���Ҫ��װAPP.";
				tv1.setText(result);
			} else {
				List<TipLableModel> msg3 = JSON.parseArray(getIntent()
						.getStringExtra("msg3"), TipLableModel.class);
				String lable = "";// ��ɫ������
				String txt = "";// ���е���
				for (int i = 0; i < msg3.size(); i++) {
					if (i == msg3.size() - 1) {
						lable = lable + msg3.get(i).getLabel();
						txt = txt + msg3.get(i).getTxt();
						break;
					}
					if (!msg3.get(i).getLabel().equals("")) {
						lable = lable + msg3.get(i).getLabel() + ";";
					}
					txt = txt + msg3.get(i).getTxt() + ";";
				}
				MyApplication.setTextColor(tv1, txt.replace(";", "\n"), lable);
			}
			customDia.setView(viewDia);
			customDia.setPositiveButton("ͬ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(
									AddFriendPackageActivity.this,
									AddFriendNameActivity.class);
							intent.putExtra("numberType", numberType);
							intent.putExtra("phonenum", phonenum);
							intent.putExtra("selectType", selectType);
							intent.putExtra("packageName", package_name
									.getText().toString());
							intent.putExtra("msg4",
									getIntent().getStringExtra("msg4"));
							startActivity(intent);
							dialog.dismiss();
						}
					});
			customDia.setNegativeButton("��ͬ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			customDia.create().show();

		}
		if (package_name == v) {
			Builder builder = new AlertDialog.Builder(context);
			if(buyPackage.size()<=0&&buyPackage==null){
//				http.sendGet(SEVEN,
//						WebUrlConfig.getPackageInfo(phonenum, numberType));
				return;
			}
			View outerView = LayoutInflater.from(context).inflate(
					R.layout.wheel_view, null);
			WheelView wv = (WheelView) outerView
					.findViewById(R.id.wheel_view_wv);
			wv.setOffset(2);
			wv.setItems(buyType);
			wv.setSeletion(packageSelect);

			wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
				@Override
				public void onSelected(int selectedIndex, String item) {
					packageSelect = selectedIndex - 2;
					package_name.setText(buyType.get(packageSelect));

				}
			});

			builder.setTitle("�ײ�����")
					.setView(outerView)
					.setPositiveButton("ȷ��", null)
					.show();
		}
	}
}