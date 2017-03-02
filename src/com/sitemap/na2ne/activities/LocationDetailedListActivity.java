package com.sitemap.na2ne.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.LocationDetailedListviewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.CodeModel;
import com.sitemap.na2ne.models.ListModel;
import com.sitemap.na2ne.models.LocationDetailedModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.EmptyView;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

/**
 * ��λ��¼��ϸ�б�ҳ��
 * 
 * @author zhang create at 2015��12��22�� ����2:02:07
 */
public class LocationDetailedListActivity extends Activity implements OnScrollListener,
		OnClickListener {
	private static LocationDetailedListActivity context;//����
	private LinearLayout base_back_lay;//���ؼ�
	private TextView back;//����
	private SwipeMenuListView location_detailed_list;//��λ��¼�����б�
	private LocationDetailedListviewAdapter adapter;//��λ��¼�����б�������
	private int friendPosition;//����
	private EditText baseDate;// �����·�
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;//����������������
	private View moreView;// ���ظ���ײ�
	private int num = 1;// ҳ��
	private List<LocationDetailedModel> ldetailed = new ArrayList<LocationDetailedModel>();// ��λ��¼����
	// ��ʼ���ڵ��꣬�������ڵ���
	private int mYear;
	// ��ʼ���ڵ��£��������ڵ���
	private int mMonth;
	// ��ʼ���ڵ��գ��������ڵ���
	private int mDay;
	private int lastItem;// ���һ��
	private int rows = 10;// ����
	private int maxnum = 0;// ��ҳ��
	private int count;// ��ǰ��������
	private List<LocationDetailedModel> ld = new ArrayList<LocationDetailedModel>();// ÿ����������ĵ�ǰҳ����
	private ListModel lm = new ListModel();// ÿ���������������
	private TextView location_detailed_name;//�û��绰
	private TextView location_detailed_phone;//�û�����
	private TextView location_detailed_img;//�û�ͼƬ
	private int delPosition=-1;//ɾ�����±�
	private TextView clean;//���
	private TextView date;//����ͼ��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		// ����androidϵͳ��״̬��
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_location_detailed);
		context = this;
		if(http == null){
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(LocationDetailedListActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		progressDialog = MyProgressDialog.createDialog(this);
		moreView = this.getLayoutInflater().inflate(R.layout.list_footer, null);
		friendPosition=getIntent().getIntExtra("friendPosition",0);
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
				// ��ö�λ��¼
				if (msg.arg1 == 2) {
					// System.out.println(msg.obj.toString());
					lm = null;
					lm = JSON.parseObject(msg.obj.toString(), ListModel.class);
					if (lm != null) {
						if (lm.getTotalCount() == 0) {
							maxnum = 0;
							ldetailed.clear();
//					
							adapter.notifyDataSetChanged();
							if (location_detailed_list.getFooterViewsCount()>0) {
								location_detailed_list.removeFooterView(moreView);
							}
							
	                        View view=EmptyView.getView(context, "���޶�λ��¼");
	                        ((ViewGroup)location_detailed_list.getParent()).addView(view);  
	                        location_detailed_list.setEmptyView(view);
							adapter.notifyDataSetChanged();
						} else {
							maxnum = lm.getTotalPage();
							if (ldetailed.size() == 0) {
								ld = JSONObject.parseArray(lm.getPageList(),
										LocationDetailedModel.class);
								if (ld != null && ld.size() > 0) {
									for (int i = 0; i < ld.size(); i++) {
										ldetailed.add(ld.get(i));
									}
								}
								count = ldetailed.size();
//								adapter = new LocationDetailedListviewAdapter(context,
//										ldetailed);
								location_detailed_list.addFooterView(moreView);
								location_detailed_list.setMenuCreator(creator);
								location_detailed_list.setAdapter(adapter);
								location_detailed_list
										.setOnScrollListener(LocationDetailedListActivity.this);
								if (num==maxnum) {
									location_detailed_list.removeFooterView(moreView);
								}
							} else {
								ld.clear();
								ld = JSONObject.parseArray(lm.getPageList(),
										LocationDetailedModel.class);
								if (ld != null && ld.size() > 0) {
									for (int i = 0; i < ld.size(); i++) {
										ldetailed.add(ld.get(i));
									}
								}
								count = ldetailed.size();
								moreView.setVisibility(View.GONE);
//								location_detailed_list.setMenuCreator(creator);
//								location_detailed_list.setAdapter(adapter);
								adapter.notifyDataSetChanged();
							}

						}
					} else {
						maxnum = 0;
						ToastUtil.showCenterShort(context, RequestCode.NULL);
					}

				}
				
				// ɾ����λ��¼
				if (msg.arg1 == 3) {
					int status = -1;
					CodeModel cm = JSON.parseObject(msg.obj.toString(),
							CodeModel.class);
					if (cm != null) {
						status = cm.getStatus();
						switch (status) {
						case 0:
							ToastUtil.showCenterShort(context,
									"ɾ����¼�ɹ���");
							ldetailed.remove(delPosition);
							adapter.notifyDataSetChanged();
//							httpUtil = new XUtilsHelper(
//									WebUrlConfig.requestMoney(MyApplication.userModel
//											.getUserID()), handler);
//							httpUtil.sendGet(six);
							break;
						case 1:
							ToastUtil.showCenterShort(context,
									"�ύ����ʧ�ܣ�ʧ��ԭ��" + cm.getErrorMessage());
							break;
						case 2:
							ToastUtil.showCenterShort(context, RequestCode.NULL);
							break;
						default:
							break;
						}
					}
				}
				// ɾ�����ж�λ��¼
				if (msg.arg1 == 4) {
					int status = -1;
					CodeModel cm = JSON.parseObject(msg.obj.toString(),
							CodeModel.class);
					if (cm != null) {
						status = cm.getStatus();
						switch (status) {
						case 0:
							ToastUtil.showCenterShort(context,
									"�����¼�ɹ���");
							ldetailed.clear();
							adapter.notifyDataSetChanged();
//							httpUtil = new XUtilsHelper(
//									WebUrlConfig.requestMoney(MyApplication.userModel
//											.getUserID()), handler);
//							httpUtil.sendGet(six);
							break;
						case 1:
							ToastUtil.showCenterShort(context,
									"�ύ����ʧ�ܣ�ʧ��ԭ��" + cm.getErrorMessage());
							break;
						case 2:
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
				if (msg.arg1 == 1) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}
				if (msg.arg1 == 2) {
					maxnum = 0;
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}		
				break;
			case HttpUtil.FAILURE:
				ToastUtil.showCenterShort(context, RequestCode.ERRORINFO);
				break;
			case HttpUtil.LOADING:
				
				break;
			case 6:
				showDialog(0);
				break;
			case 7:
				if (MyApplication.getNetObject().isNetConnected()) {
					ldetailed.clear();
					num = 1;
					if (progressDialog != null && !progressDialog.isShowing()) {
						progressDialog.setMessage("��ѯ��λ��¼��...");
						progressDialog.show();
					}
					
					String url =
							WebUrlConfig.locationRecord(MyApplication.userModel
									.getUserID(), String.valueOf(num),MyApplication.lOpenUserFriend.get(friendPosition).getFriendID(), baseDate
									.getText().toString());
					http.sendGet(2,url);
				} else {
					ToastUtil.showCenterShort(context, RequestCode.NONETWORK);
				}
				break;
			case 8:
				ToastUtil.showCenterShort(context, "�Ѿ���ȫ����¼��");
				location_detailed_list.removeFooterView(moreView);
				break;
			case 9:
				if (MyApplication.getNetObject().isNetConnected()) {
					String url = 
							WebUrlConfig.locationRecord(MyApplication.userModel
									.getUserID(), String.valueOf(num),MyApplication.lOpenUserFriend.get(friendPosition).getFriendID(), baseDate
									.getText().toString());
					http.sendGet(2,url);
				} else {
					ToastUtil.showCenterShort(context, RequestCode.NONETWORK);
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
		back=(TextView)findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		base_back_lay=(LinearLayout)findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		baseDate = (EditText) findViewById(R.id.baseDate);
		baseDate.setOnClickListener(this);
		baseDate.setFocusable(false);
		location_detailed_list=(SwipeMenuListView)findViewById(R.id.location_detailed_list);
		clean=(TextView)findViewById(R.id.clean);
		clean.setOnClickListener(this);
		date=(TextView)findViewById(R.id.date);
		date.setOnClickListener(this);
//		location_detailed_name=(TextView)findViewById(R.id.location_detailed_name);
//		location_detailed_name.setText(MyApplication.lOpenUserFriend.get(friendPosition).getFriendName());
//		location_detailed_phone=(TextView)findViewById(R.id.location_detailed_phone);
//		location_detailed_phone.setText(MyApplication.lOpenUserFriend.get(friendPosition).getFriendPhone());
//		location_detailed_img=(TextView)findViewById(R.id.location_detailed_img);
//		if (MyApplication.lOpenUserFriend.get(friendPosition).getFriendType().equals("0")) {
//			location_detailed_img.setBackgroundResource(R.drawable.listimga);
//		}else {
//			location_detailed_img.setBackgroundResource(R.drawable.listimgb);
//		}
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDateDisplay();
		location_detailed_list.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public void onMenuItemClick(final int position, final SwipeMenu menu, final int index) {
				AlertDialog.Builder normalDia = new AlertDialog.Builder(
						LocationDetailedListActivity.this);
				normalDia.setTitle("��ʾ");
				normalDia.setMessage("�Ƿ�ȷ��ɾ��������¼��");

				normalDia.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (MyApplication.getNetObject().isNetConnected()) {
									if (progressDialog != null && !progressDialog.isShowing()) {
										progressDialog.setMessage("ɾ����¼��...");
										progressDialog.show();
									}
									delPosition=position;
									http.sendGet(3,WebUrlConfig.deleteHistory("",ldetailed.get(position).getHistoryID()));
									dialog.dismiss();
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
		});
		adapter = new LocationDetailedListviewAdapter(context,
				ldetailed);
	}
	
	// step 1. create a MenuCreator
	SwipeMenuCreator creator = new SwipeMenuCreator() {

		
	public void create(SwipeMenu menu) {
		// create "delete" item
		SwipeMenuItem deleteItem = new SwipeMenuItem(
				getApplicationContext());
		// set item background
		deleteItem.setBackground(new ColorDrawable(Color
				.parseColor("#e75b5f")));
		// set item width
		deleteItem.setWidth(dp2px(90));
		deleteItem.setTitle("ɾ��");
		deleteItem.setTitleSize(18);
		deleteItem.setTitleColor(Color.WHITE);
		menu.addMenuItem(deleteItem);
	};
	};
	

	/**
	 * ����ʱ���dialog
	 */
	protected Dialog onCreateDialog(int id) {
		int SDKVersion;
		try {
			SDKVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			SDKVersion = 0;
		}
		switch (id) {
		case 0:

			DatePickerDialog dp = new DatePickerDialog(this, ddl, mYear,
					mMonth, mDay);
			if (SDKVersion < 11) {
				((ViewGroup) dp.getDatePicker().getChildAt(0)).getChildAt(2)
						.setVisibility(View.GONE);
			} else if (SDKVersion > 14) {
				View view2 = ((ViewGroup) ((ViewGroup) dp.getDatePicker()
						.getChildAt(0)).getChildAt(0)).getChildAt(2);
				view2.setVisibility(View.GONE);
			}
			return dp;
		}
		return null;
	}

	/**
	 * ��ʼ����ѡ�����
	 */
	private DatePickerDialog.OnDateSetListener ddl = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();

		}
	};

	/**
	 * ��ʼ����ѡ��ȷ�����������
	 */
	private void updateDateDisplay() {
		baseDate.setText(new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)));
		handler.sendEmptyMessage(7);

	}

	@Override
	public void onClick(View v) {
		if (v==base_back_lay) {
			finish();
		}
		if (v==back) {
			finish();
		}
		if (v == date) {
			handler.sendEmptyMessage(6);
		}
		if (v==clean) {
			AlertDialog.Builder normalDia = new AlertDialog.Builder(
					LocationDetailedListActivity.this);
			normalDia.setTitle("��ʾ");
			normalDia.setMessage("�Ƿ�ȷ������ú�������м�¼��");

			normalDia.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (MyApplication.getNetObject().isNetConnected()) {
								if (progressDialog != null && !progressDialog.isShowing()) {
									progressDialog.setMessage("�����¼��...");
									progressDialog.show();
								}
								String url = WebUrlConfig.deleteHistory(MyApplication.lOpenUserFriend.get(friendPosition).getFriendPhone(),"");
								http.sendGet(4,url);
								dialog.dismiss();
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

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (ldetailed.size()==0) {
			
		}else {
		
		if (lastItem >= count && scrollState == this.SCROLL_STATE_IDLE) {
			num += 1;
			if (num <= maxnum) {
				moreView.setVisibility(view.VISIBLE);

				handler.sendEmptyMessage(9);

			} else {
				handler.sendEmptyMessage(8);
			}
		}	
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
if (ldetailed.size()==0) {
			
		}else {
		lastItem = firstVisibleItem + visibleItemCount; // ��1����Ϊ������˸�addFooterView		
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
}