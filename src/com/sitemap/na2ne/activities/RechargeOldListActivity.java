package com.sitemap.na2ne.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.RechargeOldListviewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.ListModel;
import com.sitemap.na2ne.models.PayModel;
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
 * ��ֵ��¼ҳ��
 * 
 * @author zhang create at 2015��12��22�� ����2:02:07
 */
public class RechargeOldListActivity extends Activity implements OnScrollListener,
		OnClickListener {
	private static RechargeOldListActivity context;//����
	private LinearLayout base_back_lay;//���ؼ�
	private TextView back;//����
	private ListView recharge_old_list;//��ֵ��¼�б�
	private RechargeOldListviewAdapter adapter;//��ֵ��¼�б�������
	private EditText baseDate;// �����·�
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;//����������������
	private View moreView;// ���ظ���ײ�
	private int num = 1;// ҳ��
	private List<PayModel> lpay = new ArrayList<PayModel>();// ��ֵ��¼����
	// ��ʼ���ڵ��꣬�������ڵ���
	private int mYear;
	// ��ʼ���ڵ��£��������ڵ���
	private int mMonth;
	// ��ʼ���ڵ��գ��������ڵ���
	private int mDay;
	private int lastItem;// ���һ��
	private int maxnum = 0;// ��ҳ��
	private int count;// ��ǰ��������
	private List<PayModel> lp = new ArrayList<PayModel>();// ÿ����������ĵ�ǰҳ����
	private ListModel lm = new ListModel();// ÿ���������������
	private TextView date;//����ͼ��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_recharge_old);
		progressDialog = MyProgressDialog.createDialog(this);
		moreView = this.getLayoutInflater().inflate(R.layout.list_footer, null);
		context = this;
		if(http == null){
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(RechargeOldListActivity.this,HomePageActivity.class);
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
				// ��ó�ֵ��¼
				if (msg.arg1 == 2) {
					lm = null;
					lm = JSON.parseObject(msg.obj.toString(), ListModel.class);
					if (lm != null) {
						if (lm.getTotalCount() == 0) {
							maxnum = 0;
							lpay.clear();
							recharge_old_list.setAdapter(null);
							if (recharge_old_list.getFooterViewsCount()>0) {
								recharge_old_list.removeFooterView(moreView);
							}
							View view=EmptyView.getView(context, "���޳�ֵ��¼");
							 ((ViewGroup)recharge_old_list.getParent()).addView(view);  
	                        recharge_old_list.setEmptyView(view);
						} else {
							maxnum = lm.getTotalPage();
							if (lpay.size() == 0) {
								lp = JSONObject.parseArray(lm.getPageList(),
										PayModel.class);
								if (lp != null && lp.size() > 0) {
									for (int i = 0; i < lp.size(); i++) {
										lpay.add(lp.get(i));
									}
								}
								count = lpay.size();
								adapter = new RechargeOldListviewAdapter(context,
										lpay);
								recharge_old_list.addFooterView(moreView);
								recharge_old_list.setAdapter(adapter);
								recharge_old_list
										.setOnScrollListener(RechargeOldListActivity.this);
								if (num==maxnum) {
									recharge_old_list.removeFooterView(moreView);
								}
							} else {
								lp.clear();
								lp = JSONObject.parseArray(lm.getPageList(),
										PayModel.class);
								if (lp != null && lp.size() > 0) {
									for (int i = 0; i < lp.size(); i++) {
										lpay.add(lp.get(i));
									}
								}
								count = lpay.size();
								moreView.setVisibility(View.GONE);
								// recharge_old_list.removeFooterView(moreView);
								adapter.notifyDataSetChanged();
//								System.out.println("size:" + lpay.size());
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
					lpay.clear();
					num = 1;
					if (progressDialog != null && !progressDialog.isShowing()) {
						progressDialog.setMessage("��ѯ��ֵ��¼��...");
						progressDialog.show();
					}
					
					String url = 
							WebUrlConfig.paymentRecords(MyApplication.userModel
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
							WebUrlConfig.paymentRecords(MyApplication.userModel
									.getUserID(), String.valueOf(num), baseDate
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
		recharge_old_list=(ListView)findViewById(R.id.recharge_old_list);
		baseDate = (EditText) findViewById(R.id.baseDate);
		baseDate.setOnClickListener(this);
		baseDate.setFocusable(false);
		date=(TextView)findViewById(R.id.date);
		date.setOnClickListener(this);
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
		if (v==base_back_lay) {
			finish();
		}
		if (v==back) {
			finish();
		}
		if (v == date) {
			handler.sendEmptyMessage(6);
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