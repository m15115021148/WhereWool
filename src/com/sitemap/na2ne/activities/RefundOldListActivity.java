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
 * 退款记录页面
 * 
 * @author zhang create at 2015年12月24日
 */
public class RefundOldListActivity extends Activity implements
		OnScrollListener, OnClickListener {
	private static RefundOldListActivity context;// 本类
	private LinearLayout base_back_lay;// 返回键
	private TextView back;// 回退
	private ListView recharge_old_list;// 退款记录列表
	private RefundOldListviewAdapter adapter;// 退款记录列表适配器
	private EditText baseDate;// 请求月份
	private static MyProgressDialog progressDialog;// 进度条
	private HttpUtil http = null;//网络请求帮助类对象
	private View moreView;// 加载更多底部
	private int num = 1;// 页数
	private List<RefundListItemModel> lrefund = new ArrayList<RefundListItemModel>();// 退款记录集合
	// 开始日期的年，结束日期的年
	private int mYear;
	// 开始日期的月，结束日期的月
	private int mMonth;
	// 开始日期的日，结束日期的日
	private int mDay;
	private int lastItem;// 最后一行
	private int rows = 10;// 行数
	private int maxnum = 0;// 总页数
	private int count;// 当前已有行数
	private List<RefundListItemModel> lr = new ArrayList<RefundListItemModel>();// 每次请求回来的当前页数据
	private ListModel lm = new ListModel();// 每次请求回来的数据
//	private TextView refund_old_phone;//用户电话
//	private TextView refund_old_name;//用户名
	private TextView refund_btn;// 申请退款按钮
	private TextView date;//日历图标
	private boolean isfirst=true;//是否第一次进入

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
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
		// 友盟统计
		MobclickAgent.onResume(this);
	}
	@Override
	public void onPause() {
		super.onPause();
		// 友盟统计
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
				progressDialog.dismiss();// 关闭进度条
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// 获得退款记录
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
	                        View view=EmptyView.getView(context,"暂无退款记录");
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
				// 返回数据为null
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
						progressDialog.setMessage("查询退款记录中...");
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
				ToastUtil.showCenterShort(context, "已经是全部记录！");
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
					ToastUtil.showCenterShort(context, "网络无法连接！");
				}
				break;
			default:
				break;
		}

		}

	};

	/**
	 * 初始化控件
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
	 * 日期时间的dialog
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
	 * 开始日期选择监听
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
	 * 开始日期选择确定更新输入框
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
		lastItem = firstVisibleItem + visibleItemCount; // 减1是因为上面加了个addFooterView
	}

}
