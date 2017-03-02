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
 * ����ҳ�� com.sitemap.na2ne.activities.GongNengActivity
 * 
 * @author zhang create at 2016��1��19�� ����5:40:45
 */
public class GongNengDetailedActivity extends Activity implements OnClickListener {
	private LinearLayout base_back_lay;// ���ؼ�
	private TextView back;// ����
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;// ����������������
	private int wt=0;//�ڼ�������
	private TextView wt_title,wt_answer,wt_img;//���⣬�𰸣�ͼƬ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
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
		// ����ͳ��
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		// ����ͳ��
		MobclickAgent.onPause(this);
	}

	/**
	 * ��ʼ���ؼ�
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
			wt_title.setText("���ѡ��/�л����룬�Լ������Ӻ���");
			wt_answer.setText("������ҳ��Ҫ��λһ�����룬�ȵ�ѡ�񣬵����ͼ���Ͻǵĳ�����ϵ��ͼ�꣬���Ҳ໬������ϵ���б���ѡ�񣬻����л��������δ�����ϵ�ˣ�����б���Ϸ��������ϵ�˽�����ӡ�");
			wt_img.setBackgroundResource(R.drawable.wt1_03);
			break;
		case 2:
			wt_title.setText("�Ժ���Ķ�λ/׷�ټ�¼������");
			wt_answer.setText("�𣺶���ѡ���붨λ��׷�ٶ�λ�󣬵�ͼ�ϻ���ʾλ����Ϣ��������ú����·�������Ͱͼ�꣬���������Щ��Ϣ��¼�����ߵ����ѡ���븡���Ҳ�Ĳ�ֱ�ӹرնԸú���Ķ�λ��");
			wt_img.setBackgroundResource(R.drawable.wt2_03);
			break;
		case 3:
			wt_title.setText("��ζ�λ�Լ���λ��");
			wt_answer.setText("�𣺵����ͼ���½ǵĶ�λͼ�꼴��λ���Լ���ǰλ�á�");
			wt_img.setBackgroundResource(R.drawable.wt3_03);
			break;
		case 4:
			wt_title.setText("��ζԺ����Զ����г���׷�ٶ�λ");
			wt_answer.setText("����Ҫ����ѡ������һ��ʱ���ڽ��г���׷�ٶ�λ���͵����ͼ���½ǵ�׷�ٶ�λͼ�꣬ѡ��׷����ʱ��Ͷ�λ���ʱ�䣬���Զ�������λ�ú��롣�����Բ鿴׷�ٶ�λ����ʷ��¼��");
			wt_img.setBackgroundResource(R.drawable.wt4_03);
			break;
		case 5:
			wt_title.setText("��Ӻ��볤ʱ��δ�յ���������");
			wt_answer.setText("����Ӻ��볤ʱ��δ�յ����ţ������ٴ���ӣ�����ʾ���û��Ѵ��ڡ��뵽�������˵����ҵ���ӵĺ����б����󻬶��ú������ ���·��Ͷ��Ű�ť�ٴη��͡�");
			wt_img.setBackgroundResource(R.drawable.wt5_03);
			break;
		case 6:
			wt_title.setText("��α���ײ�");
			wt_answer.setText("���ں������ҳ���󻮸ú�����Ŀ����������ײͼ��ɹ���");
			wt_img.setBackgroundResource(R.drawable.wt6_03);
			break;
		case 7:
			wt_title.setText("��λʧ�ܣ��Ҳ�����������");
			wt_answer.setText("�������ƶ���ͨ����������Ӫ������4G�����»ᶨλʧ�ܣ���ȷ���Է��ѹر�4G���硣��λ��п���������ԭ�����Ժ����ԣ���λ���ɹ����۷ѣ���");
//			wt_img.setBackgroundResource(R.drawable.wt1_03);
			wt_img.setVisibility(View.GONE);
			break;
		case 8:
			wt_title.setText("�������������˿�");
			wt_answer.setText("���˻�������ȫ���˿�����˿��¼ҳ��󣬵�����Ͻǵ������˿ť�����ύ���룬�����10���������ڽ����ԭ·�˻ء�");
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
