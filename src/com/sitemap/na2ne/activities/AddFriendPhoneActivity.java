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
 * @author zhang create at 2016年3月24日 下午4:52:43
 */
public class AddFriendPhoneActivity extends Activity implements OnClickListener {
	public static AddFriendPhoneActivity context;// 本类
	private LinearLayout base_back_lay;// 返回键
	private TextView back;// 回退
	private EditText addfriend_num_ed;// 号码电话
	private TextView phone_next;// 下一步
	private String numberType = "1";// 手机号类型
	private TextView tongxunlu;// 通讯录
	private final int PICK_CONTACT = 1001;// 跳转code
	private static MyProgressDialog progressDialog;// 进度条
	private HttpUtil http = null;// 网络请求帮助类对象
	/** 常量 */
	private static final int SUCCESS = 2, FIAL = 1, GETTIPMSG = 0X1003;

	/** 提示实体类 */
	private TipMsgModel tipModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
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
	 * 初始化控件
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
			progressDialog.setMessage("加载中...");
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
				progressDialog.dismiss();// 关闭进度条
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// 添加好友时，文字提示
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

					String lable = "";// 颜色的字体
					String txt = "";// 所有的字
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
					customDia.setPositiveButton("同意",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					customDia.setNegativeButton("不同意",
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
					ToastUtil.showCenterShort(context, "无法连接网络");
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
		// 友盟统计
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		// 友盟统计
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
				ToastUtil.showCenterShort(context, "被定位号码不能为空！");
				return;
			}
			if (addfriend_num_ed.getText().toString().trim().length() != 11) {
				ToastUtil.showCenterShort(context, "请输入11位手机号！");
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
				ToastUtil.showCenterShort(context, "请输入正确的手机号！");
				return;
			}

			AlertDialog.Builder customDia = new AlertDialog.Builder(context);
			customDia.setCancelable(false);
			final View viewDia = LayoutInflater.from(context).inflate(
					R.layout.add_frien_dialog_item, null);
			TextView tv1 = (TextView) viewDia.findViewById(R.id.tv_1);

			if (tipModel == null) {
				ToastUtil.showCenterShort(context, "突然找不到数据了，再给次机会吧>_<");
				return;
			}
			List<TipLableModel> msg2 = tipModel.getMsg2();

			String lable = "";// 颜色的字体
			String txt = "";// 所有的字
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
			customDia.setPositiveButton("同意",
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
			customDia.setNegativeButton("不同意",
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
							phoneNum = phoneNum.substring(3); // 去掉+86
						}
						if (!phoneNum.equals("")
								&& isNum(phoneNum.replace(" ", ""))) {
							addfriend_num_ed.setText(phoneNum.replace(" ", ""));
						} else {
							addfriend_num_ed.setText("");
							ToastUtil.showCenterShort(context,
									"读取不到该号码，请手动输入！");
						}
					} else {
						addfriend_num_ed.setText("");
						ToastUtil.showCenterShort(context,
								"读取不到该号码，请手动输入！");
					}

					Log.i("TAG", "" + phoneNum);
					Log.i("TAG", "" + isNum(phoneNum));
				} catch (Exception e) {
					addfriend_num_ed.setText("");
					ToastUtil.showCenterShort(context, "读取不到该号码，请手动输入！");
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
	 * 获取电话号码
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
			// 获得号码的ID号
			int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
			String contactId = cursor.getString(idColumn);
			// 获得号码的电话号码的cursor;
			Cursor phones = getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			// int phoneCount = phones.getCount();
			// allPhoneNum = new ArrayList<String>(phoneCount);
			if (phones.moveToFirst()) {
				// 遍历所有的电话号码
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
