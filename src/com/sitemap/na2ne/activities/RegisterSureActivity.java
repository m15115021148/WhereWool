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
 * ����ע��ҳ��
 * 
 * @author zhang create at 2015��12��22�� ����2:02:07
 */
public class RegisterSureActivity extends Activity implements
		OnClickListener {
	private static RegisterSureActivity context;//����
	private LinearLayout base_back_lay;//���ؼ�
	private TextView back;//����
	private TextView register_email_tophone_tv;//�ֻ�ע��
	private EditText register_email_name_ed;//����
	private EditText register_email_pwd_ed;//����
	private EditText register_email_repwd_ed;//ȷ������
	private TextView register_sub;//ע�ᰴť
	private static MyProgressDialog progressDialog;//������
	private HttpUtil http = null;//����������������
	private final int zero=0, one=1,two=2,three=3,four=4;//0,1,2,3,4
	private TextView xieyi;//ʹ��Э��
	private String numberType;//�ֻ�������
	String phoneName ;//�ֻ�Ʒ��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		// ����androidϵͳ��״̬��
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
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
				//ע��
				if (msg.arg1 ==three) {
					int status = -1;
					CodeModel cm=JSON.parseObject(msg.obj.toString(), CodeModel.class);
					if (cm!=null) {
						status=cm.getStatus();
					}
					switch (status) {
					case zero:
						ToastUtil.showCenterShort(context, "ע��ɹ���");
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
				// ��������Ϊnull
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
	 * ��ʼ���ؼ�
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
	 * ���ؼ�
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
			normalDia.setTitle("�û�Э��");
			normalDia.setMessage("ʹ�ù���\n1���ڹ���(http://www.na2ne.com)�����Ӧ���̵����ء��Ķ��ء�APPӦ�á�ע���˺š����˺Ž���ֵ��ʹ�ö�λ����\n2����˾��������ȫ�ɿ����û���Ϣ��ȫ�����ƶȡ���ʵ������ȫ���ش�ʩ�����û���ʹ�ö�λ����������漰����˽���ݼ��Ա�����\n3�������������������ԣ������������ڷ������ȶ������⡢�������繥����������˾�޷����Ƶ����Σ�����˾��Ȩ��ʱ�жϻ���ֹ��λ�����������жϻ���ֹ������������˾�������ܼ�ʱͨ����ҳ���桢ϵͳ֪ͨ��˽�š��������ѻ���������ʽ֪ͨ�ܵ�Ӱ����û���\n4����˾�ᶨ�ڻ򲻶��ڵĶԷ���ƽ̨���м��޻���ά�����������ɷ�����жϣ���˾���е��κ����Σ�����˾�ᾡ��������ͨ����ҳ���桢ϵͳ֪ͨ��˽�š��������ѻ���������ʽ֪ͨ�û���\n5���緢�������κ�һ�����Σ���˾����Ȩ��ʱ�жϻ���ֹ���û��ṩ�ķ��񣬶�������û���������е��κ����Σ�\n1) �û�Υ�����ɷ��桢�������߻�Э���й涨��ʹ�ù���\n2) �û���ʹ���շѷ���ʱ�˻����Ϊ0����û�м�ʱ��ֵ��\n3)���û������뿪ͨ���������90����δʵ��ʹ�ã���˾��Ȩ�Ը��˺Ž��ж��ᴦ�����û������¿�ͨ���˺ţ����Բ���ͷ��绰021-61132501��\n\n��������\n1�����û�Υ��ʹ�ù���2��5����д���ݣ���˾��Ȩ��ʱ�жϻ���ֹ�û��Զ�λ�����ʹ���Ҳ��е�ΥԼ���Ρ�\n2�����ڶ�λ���������û����ṩ���ʺ�Ϊ���ݣ������û���Ӧ�����ʺš�����ת�û����������ʹ�á����û������ʺŻ�λ���������˷Ƿ�ʹ�ã�Ӧ����֪ͨ��˾������û�м�ʱ֪ͨ��˾���ڿ���Ϊ���û�����ı��������������һ�о��ס���ʧ�Ͳ���������ҹ�˾���е��κ��������Ρ�\n3����ʹ�á��Ķ��ء���λ����ʱ���ƶ��ն˳����߱�������֪����ͬ�ⱻ��λ������δ�õ�����λ�ƶ��ն��û�ͬ�������½��ж�λ�������������һ����ʧ�����׺Ͳ������(����˽��Ϣй¶���Ƿ�λ�û�ȡ��)�������û����ге����ҹ�˾���е��κ��������Ρ�\n4���û����ý�ͨ����λ�����õ�λ����Ϣй¶������������ı��������˶�������һ�о��ס���ʧ�Ͳ���������ҹ�˾�����е��κ��������Ρ�\n5����λ����ʹ�������ƶ��ն˳�������ʹ�ù�������������һ�о��ס���ʧ�벻��������ҹ�˾�����е��������Ρ�\n6��һ����Υ��������ط��ɷ��桢���߶������ľ��ס���ʧ�벻��������ҹ�˾�����е��������Ρ�\n\n�Ϻ���ͼ������Ƽ����޹�˾ӵ�ж��������ݵ����ս���Ȩ��");

			normalDia.setPositiveButton("��֪����",
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
				ToastUtil.showCenterShort(context, "��������Ϊ�գ�");
				return;
			}
			if (register_email_pwd_ed.getText().toString().trim().equals("")
					|| register_email_pwd_ed.getText().toString().trim() == null) {
				ToastUtil.showCenterShort(context, "���벻��Ϊ�գ�");
				return;
			}
			if (register_email_repwd_ed.getText().toString().trim().equals("")
					|| register_email_repwd_ed.getText().toString().trim() == null) {
				ToastUtil.showCenterShort(context, "�ظ����벻��Ϊ�գ�");
				return;
			}

			if (!MyApplication.isPWD(register_email_pwd_ed.getText().toString().trim())) {
				ToastUtil.showCenterShort(context, "���볤��Ӧ6-16λ���֡���ĸ���»��ߣ�");
			return;
		}
		if (!MyApplication.isPWD(register_email_repwd_ed.getText().toString().trim())) {
			ToastUtil.showCenterShort(context, "���볤��Ӧ6-16λ���֡���ĸ���»��ߣ�");
			return;
		}
			
			if (!register_email_pwd_ed
					.getText()
					.toString()
					.trim()
					.equals(register_email_repwd_ed.getText().toString().trim())) {
				ToastUtil.showCenterShort(context, "�����������벻һ�£�");
				return;
			}

			if (MyApplication.getNetObject().isNetConnected()) {
				if (progressDialog != null && !progressDialog.isShowing()) {
					progressDialog.setMessage("ע����...");
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
	 * �Ի���
	 */
	private void showNormalDia() {
		AlertDialog.Builder normalDia = new AlertDialog.Builder(
				RegisterSureActivity.this);
		normalDia.setTitle("��ʾ");
		normalDia.setMessage("�Ƿ�Ҫ�˳�ע�᣿");

		normalDia.setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						context.finish();
						
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
