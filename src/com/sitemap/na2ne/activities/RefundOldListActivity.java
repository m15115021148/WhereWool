package com.sitemap.na2ne.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.RefundOldListviewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.ListModel;
import com.sitemap.na2ne.models.RefundListItemModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.EmptyView;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * �˿��¼ҳ��
 * 
 * @author zhang create at 2015��12��24��
 */
public class RefundOldListActivity extends Activity implements
		OnScrollListener, OnClickListener {
	private static RefundOldListActivity context;// ����
	private LinearLayout base_back_lay;// ���ؼ�
	private TextView back;// ����
	private ListView recharge_old_list;// �˿��¼�б�
	private RefundOldListviewAdapter adapter;// �˿��¼�б�������
	private EditText baseDate;// �����·�
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;//����������������
	private View moreView;// ���ظ���ײ�
	private int num = 1;// ҳ��
	private List<RefundListItemModel> lrefund = new ArrayList<RefundListItemModel>();// �˿��¼����
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
	private List<RefundListItemModel> lr = new ArrayList<RefundListItemModel>();// ÿ����������ĵ�ǰҳ����
	private ListModel lm = new ListModel();// ÿ���������������
//	private TextView refund_old_phone;//�û��绰
//	private TextView refund_old_name;//�û���
	private TextView refund_btn;// �����˿ť
	private TextView date;//����ͼ��
	private boolean isfirst=true;//�Ƿ��һ�ν���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_refund_old);
		context = this;
		if(http == null){
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(RefundOldListActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		progressDialog = MyProgressDialog.createDialog(this);
		moreView = this.getLayoutInflater().inflate(R.layout.list_footer, null);
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
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
//		Log.i("TAG", "onRestart");
		handler.sendEmptyMessage(7);
		super.onRestart();
	}

	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// �رս�����
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// ����˿��¼
				if (msg.arg1 == 2) {
					// System.out.println(msg.obj.toString());
					lm = JSON.parseObject(msg.obj.toString(), ListModel.class);
					if (lm != null) {
						if (lm.getTotalCount() == 0) {
							maxnum = 0;
							lrefund.clear();
							recharge_old_list.setAdapter(null);
							if (recharge_old_list.getFooterViewsCount()>0) {
								recharge_old_list.removeFooterView(moreView);
							}
	                        View view=EmptyView.getView(context,"�����˿��¼");
	                        ((ViewGroup)recharge_old_list.getParent()).addView(view);  
	                        recharge_old_list.setEmptyView(view);
	                        
						} else {
							maxnum = lm.getTotalPage();
							if (lrefund.size() == 0) {
								lr = JSONObject.parseArray(lm.getPageList(),
										RefundListItemModel.class);
								if (lr != null && lr.size() > 0) {
									for (int i = 0; i < lr.size(); i++) {
										lrefund.add(lr.get(i));
									}
								}
								count = lrefund.size();
								adapter = new RefundOldListviewAdapter(context,
										lrefund);
								recharge_old_list.addFooterView(moreView);
								recharge_old_list.setAdapter(adapter);
								recharge_old_list
										.setOnScrollListener(RefundOldListActivity.this);
								if (num==maxnum) {
									recharge_old_list.removeFooterView(moreView);
								}
							} else {
								lr.clear();
								lr = JSONObject.parseArray(lm.getPageList(),
										RefundListItemModel.class);
								if (lr != null && lr.size() > 0) {
									for (int i = 0; i < lr.size(); i++) {
										lrefund.add(lr.get(i));
									}
								}
								count = lrefund.size();
								moreView.setVisibility(View.GONE);
								// recharge_old_list.removeFooterView(moreView);
								adapter.notifyDataSetChanged();
//								System.out.println("size:" + lrefund.size());
							}

						}
					} else {
						maxnum = 0;
						ToastUtil.showCenterShort(context, RequestCode.NULL);
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
					lrefund.clear();
					if (adapter!=null) {
						adapter.notifyDataSetChanged();
					}
					
					num = 1;
					if (progressDialog != null && !progressDialog.isShowing()) {
						progressDialog.setMessage("��ѯ�˿��¼��...");
						progressDialog.show();
					}
					
					String url = 
							WebUrlConfig.refundRecords(MyApplication.userModel
									.getUserID(), String.valueOf(num), baseDate
									.getText().toString());
					http.sendGet(2,url);
				} else {
					ToastUtil.showCenterShort(context, RequestCode.NONETWORK);
				}
				break;
			case 8:
				ToastUtil.showCenterShort(context, "�Ѿ���ȫ����¼��");
				recharge_old_list.removeFooterView(moreView);
				break;
			case 9:
				if (MyApplication.getNetObject().isNetConnected()) {
					String url = 
							WebUrlConfig.refundRecords(MyApplication.userModel
									.getUserID(), String.valueOf(num), baseDate
									.getText().toString());
					http.sendGet(2,url);
				} else {
					ToastUtil.showCenterShort(context, "�����޷����ӣ�");
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
		back = (TextView) findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		baseDate = (EditText) findViewById(R.id.baseDate);
		baseDate.setOnClickListener(this);
		baseDate.setFocusable(false);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		recharge_old_list = (ListView) findViewById(R.id.recharge_old_list);
		date=(TextView)findViewById(R.id.date);
		date.setOnClickListener(this);
//		refund_old_phone= (TextView) findViewById(R.id.refund_old_phone);
//		refund_old_phone.setText(MyApplication.userModel.getPhoneNumber());
//		refund_old_name= (TextView) findViewById(R.id.refund_old_name);
//		refund_old_name.setText(MyApplication.userModel.getRealName());
		refund_btn = (TextView) findViewById(R.id.refund_btn);
		refund_btn.setOnClickListener(this);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDateDisplay();

	}

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
		if (v == base_back_lay) {
			finish();
		}
		if (v == back) {
			finish();
		}
		if (v == date) {
			handler.sendEmptyMessage(6);
		}
		if (v == refund_btn) {
			Intent intent = new Intent(RefundOldListActivity.this, RefundActivity.class);
			context.startActivity(intent);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
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

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastItem = firstVisibleItem + visibleItemCount; // ��1����Ϊ������˸�addFooterView
	}

}