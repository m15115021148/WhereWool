package com.sitemap.na2ne.activities;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.TipLableModel;
import com.sitemap.na2ne.models.TipMsgModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
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
public class AddFriendPhoneActivity extends Activity implements OnClickListener {
	public static AddFriendPhoneActivity context;// ����
	private LinearLayout base_back_lay;// ���ؼ�
	private TextView back;// ����
	private EditText addfriend_num_ed;// ����绰
	private TextView phone_next;// ��һ��
	private String numberType = "1";// �ֻ�������
	private TextView tongxunlu;// ͨѶ¼
	private final int PICK_CONTACT = 1001;// ��תcode
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;// ����������������
	/** ���� */
	private static final int SUCCESS = 2, FIAL = 1, GETTIPMSG = 0X1003;

	/** ��ʾʵ���� */
	private TipMsgModel tipModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addfriend_phone);
		context = this;
		if (http == null) {
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(AddFriendPhoneActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		initview();
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initview() {
		back = (TextView) findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		phone_next = (TextView) findViewById(R.id.phone_next);
		phone_next.setOnClickListener(this);
		addfriend_num_ed = (EditText) findViewById(R.id.addfriend_num_ed);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		tongxunlu = (TextView) findViewById(R.id.tongxunlu);
		tongxunlu.setOnClickListener(this);
		if (MyApplication.getNetObject().isNetConnected()) {
			progressDialog = MyProgressDialog.createDialog(this);
			progressDialog.setMessage("������...");
			progressDialog.show();
			http.sendGet(GETTIPMSG, WebUrlConfig.getTipMsg(String.valueOf(1)));
		} else {
			ToastUtil.showCenterShort(context, RequestCode.NONETWORK);
			onBackPressed();
			// finish();
		}

	}

	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// �رս�����
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// ���Ӻ���ʱ��������ʾ
				if (msg.arg1 == GETTIPMSG) {
					tipModel = JSON.parseObject(msg.obj.toString(),
							TipMsgModel.class);
					List<TipLableModel> msg1 = tipModel.getMsg1();

					AlertDialog.Builder customDia = new AlertDialog.Builder(
							context);
					customDia.setCancelable(false);
					final View viewDia = LayoutInflater.from(context).inflate(
							R.layout.add_frien_dialog_item, null);
					TextView tv1 = (TextView) viewDia.findViewById(R.id.tv_1);

					String lable = "";// ��ɫ������
					String txt = "";// ���е���
					for (int i = 0; i < msg1.size(); i++) {
						if (i == msg1.size() - 1) {
							lable = lable + msg1.get(i).getLabel();
							txt = txt + msg1.get(i).getTxt();
							break;
						}
						if (!msg1.get(i).getLabel().equals("")) {
							lable = lable + msg1.get(i).getLabel() + ";";
						}
						txt = txt + msg1.get(i).getTxt() + ";";
					}
					MyApplication.setTextColor(tv1, txt.replace(";", "\n"),
							lable);

					customDia.setView(viewDia);
					customDia.setPositiveButton("ͬ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					customDia.setNegativeButton("��ͬ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									onBackPressed();
									finish();
									dialog.dismiss();
								}
							});
					customDia.create().show();
				}
				break;
			case HttpUtil.EMPTY:
				if(msg.arg1 == GETTIPMSG){
					onBackPressed();
				}
				break;
			case HttpUtil.FAILURE:
				if (msg.arg1 == GETTIPMSG) {
					ToastUtil.showCenterShort(context, "�޷���������");
					onBackPressed();
					// finish();
				} else {
					ToastUtil.showCenterShort(context,
							RequestCode.ERRORINFO);
				}
				break;
			case HttpUtil.LOADING:

				break;
			default:
				break;
			}

		}
	};

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

	@Override
	public void onClick(View v) {
		if (v == base_back_lay) {
			finish();
		}
		if (v == back) {
			finish();
		}
		if (v == tongxunlu) {
			Intent intent = new Intent(Intent.ACTION_PICK,
					ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, PICK_CONTACT);
		}
		if (v == phone_next) {
			if (addfriend_num_ed.getText().toString().trim() == null
					|| addfriend_num_ed.getText().toString().trim().equals("")) {
				ToastUtil.showCenterShort(context, "����λ���벻��Ϊ�գ�");
				return;
			}
			if (addfriend_num_ed.getText().toString().trim().length() != 11) {
				ToastUtil.showCenterShort(context, "������11λ�ֻ��ţ�");
				return;
			}
			if (MyApplication.isCTCCNO(addfriend_num_ed.getText().toString())) {
				numberType = "1";
			} else if (MyApplication.isCMCCNO(addfriend_num_ed.getText()
					.toString())) {
				numberType = "2";
			} else if (MyApplication.isCUCCNO(addfriend_num_ed.getText()
					.toString())) {
				numberType = "3";
			} else {
				ToastUtil.showCenterShort(context, "��������ȷ���ֻ��ţ�");
				return;
			}

			AlertDialog.Builder customDia = new AlertDialog.Builder(context);
			customDia.setCancelable(false);
			final View viewDia = LayoutInflater.from(context).inflate(
					R.layout.add_frien_dialog_item, null);
			TextView tv1 = (TextView) viewDia.findViewById(R.id.tv_1);

			if (tipModel == null) {
				ToastUtil.showCenterShort(context, "ͻȻ�Ҳ��������ˣ��ٸ��λ����>_<");
				return;
			}
			List<TipLableModel> msg2 = tipModel.getMsg2();

			String lable = "";// ��ɫ������
			String txt = "";// ���е���
			for (int i = 0; i < msg2.size(); i++) {
				if (i == msg2.size() - 1) {
					lable = lable + msg2.get(i).getLabel();
					txt = txt + msg2.get(i).getTxt();
					break;
				}
				if (!msg2.get(i).getLabel().equals("")) {
					lable = lable + msg2.get(i).getLabel() + ";";
				}
				txt = txt + msg2.get(i).getTxt() + ";";
			}
			MyApplication.setTextColor(tv1, txt.replace(";", "\n"), lable);

			customDia.setView(viewDia);
			customDia.setPositiveButton("ͬ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(
									AddFriendPhoneActivity.this,
									AddFriendPackageActivity.class);
							intent.putExtra("numberType", numberType);
							intent.putExtra("phonenum", addfriend_num_ed
									.getText().toString());
							intent.putExtra("msg3",
									JSON.toJSONString(tipModel.getMsg3()));
							intent.putExtra("msg4",
									JSON.toJSONString(tipModel.getMsg4()));
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
	}

	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (PICK_CONTACT):
			if (resultCode == Activity.RESULT_OK) {
				try {

					Uri contactData = data.getData();

					Cursor c = managedQuery(contactData, null, null, null, null);

					c.moveToFirst();

					String phoneNum = this.getContactPhone(c);
					if (phoneNum != null) {
						if (phoneNum.startsWith("+86")) {
							phoneNum = phoneNum.substring(3); // ȥ��+86
						}
						if (!phoneNum.equals("")
								&& isNum(phoneNum.replace(" ", ""))) {
							addfriend_num_ed.setText(phoneNum.replace(" ", ""));
						} else {
							addfriend_num_ed.setText("");
							ToastUtil.showCenterShort(context,
									"��ȡ�����ú��룬���ֶ����룡");
						}
					} else {
						addfriend_num_ed.setText("");
						ToastUtil.showCenterShort(context,
								"��ȡ�����ú��룬���ֶ����룡");
					}

					Log.i("TAG", "" + phoneNum);
					Log.i("TAG", "" + isNum(phoneNum));
				} catch (Exception e) {
					addfriend_num_ed.setText("");
					ToastUtil.showCenterShort(context, "��ȡ�����ú��룬���ֶ����룡");
				}
			}
			break;
		}
	}

	private boolean isNum(String num) {
		boolean isNum = num.matches("[0-9]+");
		return isNum;
	}

	/**
	 * ��ȡ�绰����
	 * 
	 * @param cursor
	 * @return
	 */
	private String getContactPhone(Cursor cursor) {

		int phoneColumn = cursor
				.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
		int phoneNum = cursor.getInt(phoneColumn);
		String phoneResult = "";
		// System.out.print(phoneNum);
		if (phoneNum > 0) {
			// ��ú����ID��
			int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
			String contactId = cursor.getString(idColumn);
			// ��ú���ĵ绰�����cursor;
			Cursor phones = getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			// int phoneCount = phones.getCount();
			// allPhoneNum = new ArrayList<String>(phoneCount);
			if (phones.moveToFirst()) {
				// �������еĵ绰����
				for (; !phones.isAfterLast(); phones.moveToNext()) {
					int index = phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
					int typeindex = phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
					int phone_type = phones.getInt(typeindex);
					String phoneNumber = phones.getString(index);
					switch (phone_type) {
					case 2:
						phoneResult = phoneNumber;
						break;
					}
					// allPhoneNum.add(phoneNumber);
				}
				if (!phones.isClosed()) {
					phones.close();
				}
			}
		}
		return phoneResult;
	}

}