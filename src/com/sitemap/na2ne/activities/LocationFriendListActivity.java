package com.sitemap.na2ne.activities;

import com.alibaba.fastjson.JSONObject;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.LocationFriendListviewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.UserFriendModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.EmptyView;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ��λ��¼�����б�ҳ��
 * 
 * @author zhang create at 2015��12��22�� ����2:02:07
 */
public class LocationFriendListActivity extends Activity implements
		OnClickListener {
	private static LocationFriendListActivity context;// ����
	private LinearLayout base_back_lay;// ���ؼ�
	private TextView back;// ����
	private ListView location_list;// ��λ��¼�����б�
	private LocationFriendListviewAdapter adapter;// ��λ��¼�����б�������
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;//����������������
	private int bill=-1;//�Ƿ���������ҳ��
	private TextView location_title;//����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		// ����androidϵͳ��״̬��
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_location_list);
		progressDialog = MyProgressDialog.createDialog(this);
		context = this;
		if(http == null){
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(LocationFriendListActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		if (MyApplication.getNetObject().isNetConnected()) {
			if (progressDialog != null && !progressDialog.isShowing()) {
				progressDialog.setMessage("���ڻ�ȡ�����б�...");
				progressDialog.show();
			}
			http.sendGet(2,WebUrlConfig.getFriendList(MyApplication.userModel
					.getUserID()));
		} else {
			ToastUtil.showCenterShort(this, RequestCode.NONETWORK);
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
				// ��ȡ�����б�
				if (msg.arg1 == 2) {
					if (MyApplication.luserFriend.size() > 0) {
						MyApplication.luserFriend.clear();
						try {
							MyApplication.luserFriend = JSONObject.parseArray(
									msg.obj.toString(), UserFriendModel.class);
						} catch (Exception e) {
						}
						
						if (MyApplication.luserFriend.size() > 0) {
							adapter = new LocationFriendListviewAdapter(context);
							location_list.setAdapter(adapter);
							location_list.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
									if (bill>0) {
										BillListActivity.context.finish();
										Intent intent = new Intent(LocationFriendListActivity.this,
												BillListActivity.class);
										intent.putExtra("friendPosition",position);
										intent.putExtra("shaixuan",1);
										context.startActivity(intent);
										context.finish();
									}else {
										Intent intent = new Intent(LocationFriendListActivity.this,
												LocationDetailedListActivity.class);
										intent.putExtra("friendPosition",position);
										context.startActivity(intent);
									}

								}
							});
							if(MyApplication.lOpenUserFriend.size()==0){
		                        View view=EmptyView.getView(context, "���޶�λ��Ϣ");
		                        ((ViewGroup)location_list.getParent()).addView(view);  
		                        location_list.setEmptyView(view);
							}
						} else {
//							ToastUtil.showCenterShort(context,
//									"���޺�����Ϣ��");
						}
					} else {
						
						// ��json��ʽ�Ľ����ɼ���
						try {
							MyApplication.luserFriend = JSONObject.parseArray(
									msg.obj.toString(), UserFriendModel.class);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (MyApplication.luserFriend.size() > 0) {
							adapter = new LocationFriendListviewAdapter(context);
							location_list.setAdapter(adapter);
							location_list.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
									if (bill>0) {
										
										BillListActivity.context.finish();
										Intent intent = new Intent(LocationFriendListActivity.this,
												BillListActivity.class);
										intent.putExtra("friendPosition",position);
										intent.putExtra("shaixuan",1);
										context.startActivity(intent);
										context.finish();
									}else {
										Intent intent = new Intent(LocationFriendListActivity.this,
												LocationDetailedListActivity.class);
										intent.putExtra("friendPosition",position);
										context.startActivity(intent);
									}
									

								}
							});
							if(MyApplication.lOpenUserFriend.size()==0){
								TextView emptyView = new TextView(LocationFriendListActivity.this);  
		                        emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
		                        emptyView.setText("���޺�����Ϣ��");  
		                        emptyView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
		                        emptyView.setVisibility(View.GONE);  
		                        ((ViewGroup)location_list.getParent()).addView(emptyView);  
		                        location_list.setEmptyView(emptyView);
							}
						} else {
//							ToastUtil.showCenterShort(context,
//									"���޺�����Ϣ��");
						}
					}

					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// �رս�����
					}
				}

				break;
			case HttpUtil.EMPTY:
				// ��������Ϊnull
				if (msg.arg1 == 1) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}
				if (msg.arg1 == 2) {
					View view=EmptyView.getView(context, "���޶�λ��Ϣ");
                    ((ViewGroup)location_list.getParent()).addView(view);  
                    location_list.setEmptyView(view);
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
		back = (TextView) findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		location_title=(TextView) findViewById(R.id.location_title);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		location_list = (ListView) findViewById(R.id.location_list);
		bill=getIntent().getIntExtra("bill", -1);
		if (bill>0) {
			location_title.setText("ɸѡ");
		}else {
			location_title.setText("��λ��¼");
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