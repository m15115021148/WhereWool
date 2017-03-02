package com.sitemap.na2ne.activities;

import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.BillListviewAdapter;
import com.sitemap.na2ne.adapters.LocationDetailedListviewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.MoneyModel;
import com.sitemap.na2ne.models.RefundSubModel;
import com.sitemap.na2ne.models.UserModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.utils.TextViewSpanUtil;
import com.sitemap.na2ne.utils.WheelView;
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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
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
 * �����˿�ҳ��ҳ��
 * 
 * @author zhang create at 2015��12��24��
 */
public class RefundActivity extends Activity implements
		OnClickListener {
	private static RefundActivity context;//����
	private LinearLayout base_back_lay;//���ؼ�
	private TextView back;//����
	private TextView refund_old;//�˿��¼
	private static MyProgressDialog progressDialog;//������
	private HttpUtil http = null;//����������������
	private TextView refund_yue;//���
	private TextView refund_yuanyin;//�˿�ԭ��������
	private ArrayAdapter<String> yuanyinAdapter;
	private EditText refund_shuoming;//�˿�˵��
	private TextView refund_sub;//�ύ�˿�
	private String shuoming="";//˵��
	private TextView jieshi;//����
	// ѡ��λ��
			private int refundSelect = 0;
	//�˿�ԭ��
		private String[] yuanyin = new String[] { "��λ��׼ȷ", "��λ��Է�ͬ��", "�շѹ���","����û�ﵽҪ��","����" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		// ����androidϵͳ��״̬��
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_refund);
		progressDialog = MyProgressDialog.createDialog(this);
		context = this;
		if(http == null){
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(RefundActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		initview();
		
		if (MyApplication.getNetObject().isNetConnected()) {
			http.sendGet(2,WebUrlConfig.requestMoney(MyApplication.userModel.getUserID()));
		} else {
			ToastUtil.showCenterShort(this, "�����޷����ӣ�");
		}
//		yuanyinAdapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_item, yuanyin);
//		yuanyinAdapter.setDropDownViewResource(R.layout.drop_down_item);
//		refund_yuanyin.setAdapter(yuanyinAdapter);
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
	
	private final  Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// �رս�����
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// ������
				if (msg.arg1 == 2) {
//					System.out.println(msg.obj.toString());
					MyApplication.moneyModel=JSON.parseObject(msg.obj.toString(), MoneyModel.class);
					if (MyApplication.moneyModel!=null) {
						refund_yue.setText(MyApplication.moneyModel.getBalance());
					}else{
						ToastUtil.showCenterShort(context, "�������쳣,��ȡ���ʧ�ܣ�");
					}

				}
				if (msg.arg1==3) {
					RefundSubModel re=JSON.parseObject(msg.obj.toString(), RefundSubModel.class);
					if (re!=null) {
						int status = -1;
						status=re.getStatus();
						switch (status) {
						case 0:
							ToastUtil.showCenterShort(context, "�ύ�˿�ɹ����˿���Ϊ��"+re.getMoney());
							MyApplication.moneyModel.setBalance("0");
							refund_yue.setText(MyApplication.moneyModel.getBalance());
							finish();
							break;
						case 1:
							ToastUtil.showCenterShort(context, "�ύ�˿�ʧ�ܣ�");
							break;
						default:
							break;
						}
					}
				}
				break;
			case HttpUtil.EMPTY:
				// ��������Ϊnull
				if (msg.arg1 == 1) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}
				if (msg.arg1 == 2) {
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
	 * ��ʼ���ؼ�
	 */
	private void initview() {
		back=(TextView)findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		refund_sub=(TextView)findViewById(R.id.refund_sub);
		refund_sub.setOnClickListener(this);
		jieshi=(TextView)findViewById(R.id.jieshi);
		refund_shuoming=(EditText)findViewById(R.id.refund_shuoming);
		refund_yue=(TextView)findViewById(R.id.refund_yue);
//		refund_old=(TextView)findViewById(R.id.refund_old);
//		refund_old.setOnClickListener(this);
		base_back_lay=(LinearLayout)findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		refund_yuanyin=(TextView)findViewById(R.id.refund_yuanyin);
		refund_yuanyin.setOnClickListener(this);
		refund_yuanyin.setText(yuanyin[refundSelect]);
		SpannableStringBuilder builder = new SpannableStringBuilder(jieshi.getText().toString());  
		TextViewSpanUtil tvSpan=new TextViewSpanUtil("021-61269359", context, Color.parseColor("#1cace9"));
		builder.setSpan(tvSpan, 73, jieshi.getText().toString().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);  
		jieshi.setText(builder);
		jieshi.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public void onClick(View v) {
		if (v==base_back_lay) {
			finish();
		}
		if (v==back) {
			finish();
		}
		if (v==refund_old) {
			Intent intent=new Intent(RefundActivity.this,RefundOldListActivity.class);
			context.startActivity(intent);
		}
		if (v==refund_yuanyin) {
			View outerView = LayoutInflater.from(context).inflate(
					R.layout.wheel_view, null);
			WheelView wv = (WheelView) outerView
					.findViewById(R.id.wheel_view_wv);
			wv.setOffset(2);
			wv.setItems(Arrays.asList(yuanyin));
			wv.setSeletion(refundSelect);
			wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
				@Override
				public void onSelected(int selectedIndex,
						String item) {
					// Log.d(TAG, "[Dialog]selectedIndex: " +
					// selectedIndex + ", item: " + item);
					refundSelect = selectedIndex - 2;
					refund_yuanyin.setText(yuanyin[refundSelect]);

				}
			});

			new AlertDialog.Builder(context).setTitle("�˿�ԭ��")
					.setView(outerView)
					.setPositiveButton("ȷ��", null).show();
		}
		if (v==refund_sub) {
			if (refund_yue.getText().equals("0")) {
				ToastUtil.showCenterShort(this, "���Ϊ0������ʧ�ܣ�");
				return;
			}
			if (refund_yue.getText().equals("0.0")) {
				ToastUtil.showCenterShort(this, "���Ϊ0������ʧ�ܣ�");
				return;
			}
			if (refund_shuoming.getText().toString().trim()!=null&&!refund_shuoming.getText().toString().trim().equals("")) {
				shuoming=refund_shuoming.getText().toString().trim();
			}else {
				ToastUtil.showCenterShort(this, "����д�˿�˵����");
				return;
			}
			if (refund_yuanyin.getText().toString().trim().equals("")||refund_yuanyin.getText().toString().trim().equals("��ѡ��")||refund_yuanyin.getText().toString().trim()==null) {
				ToastUtil.showCenterShort(this, "��ѡ���˿�ԭ��");
				return;
			}
			AlertDialog.Builder normalDia = new AlertDialog.Builder(
					RefundActivity.this);
			normalDia.setTitle("��ʾ");
			normalDia.setMessage("�Ƿ�ȷ�������˿�˿���������ã�");

			normalDia.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (MyApplication.getNetObject().isNetConnected()) {
								if (progressDialog != null && !progressDialog.isShowing()) {
									progressDialog.setMessage("�����˿���...");
									progressDialog.show();
								}
								
								String url = WebUrlConfig.requestRefund(MyApplication.userModel.getUserID(),refund_yuanyin.getText().toString().trim(), shuoming);
								http.sendGet(3,url);
							} else {
								ToastUtil.showCenterShort(context, RequestCode.NONETWORK);
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


