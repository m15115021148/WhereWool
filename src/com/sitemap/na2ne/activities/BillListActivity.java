package com.sitemap.na2ne.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.BillListviewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.BillModel;
import com.sitemap.na2ne.models.ListModel;
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
 * �Ʒ��˵��б�ҳ��
 * 
 * @author zhang create at 2015��12��22�� ����2:02:07
 */
public class BillListActivity extends Activity implements OnScrollListener,
		OnClickListener {
	public static BillListActivity context;//����
	private LinearLayout base_back_lay;//���ؼ�
	private TextView back;//����
	private ListView bill_list;//�Ʒ��˵��б�
	private BillListviewAdapter adapter;//�Ʒ��˵��б�������
	private EditText baseDate;// �����·�
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;//����������������
	private View moreView;// ���ظ���ײ�
	private int num = 1;// ҳ��
	private TextView date;//����ͼ��
	private List<BillModel> lbill = new ArrayList<BillModel>();// ���Ѽ�¼����
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
	private List<BillModel> lb = new ArrayList<BillModel>();// ÿ����������ĵ�ǰҳ����
	private ListModel lm = new ListModel();// ÿ���������������
//	private TextView bill_phone;//�û��绰
//	private TextView bill_jine;//�����ѽ��
//	private TextView bill_name;//�û�����
	private TextView shaixuan;//ɸѡ
	private int friendPosition=-1;//�����±�
	private String friendPhone="";//���ѵ绰
	private int isshaixuan=-1;//�Ƿ�����ɸѡ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		// ����androidϵͳ��״̬��
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bill_list);
		progressDialog = MyProgressDialog.createDialog(this);
		moreView = this.getLayoutInflater().inflate(R.layout.list_footer, null);
		context = this;
		if(http == null){
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(BillListActivity.this,HomePageActivity.class);
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
				// ������Ѽ�¼
				if (msg.arg1 == 2) {
					// System.out.println(msg.obj.toString());
					lm = null;
					lm = JSON.parseObject(msg.obj.toString(), ListModel.class);
					if (lm != null) {
						if (lm.getTotalCount() == 0) {
							maxnum = 0;
							lbill.clear();
							bill_list.setAdapter(null);
							if (bill_list.getFooterViewsCount()>0) {
								bill_list.removeFooterView(moreView);
							}
							View view=EmptyView.getView(context, "�������Ѽ�¼");
	                        ((ViewGroup)bill_list.getParent()).addView(view);  
	                        bill_list.setEmptyView(view);
						} else {
							maxnum = lm.getTotalPage();
							if (lbill.size() == 0) {
								lb = JSONObject.parseArray(lm.getPageList(),
										BillModel.class);
								if (lb != null && lb.size() > 0) {
									for (int i = 0; i < lb.size(); i++) {
										lbill.add(lb.get(i));
									}
								}
								count = lbill.size();
								adapter = new BillListviewAdapter(context,
										lbill);
								bill_list.addFooterView(moreView);
								bill_list.setAdapter(adapter);
								bill_list
										.setOnScrollListener(BillListActivity.this);
//								System.out.println("num:"+num);
								if (num==maxnum) {
									bill_list.removeFooterView(moreView);
								}
							} else {
								lb.clear();
								lb = JSONObject.parseArray(lm.getPageList(),
										BillModel.class);
								if (lb != null && lb.size() > 0) {
									for (int i = 0; i < lb.size(); i++) {
										lbill.add(lb.get(i));
									}
								}
								count = lbill.size();
								moreView.setVisibility(View.GONE);
								adapter.notifyDataSetChanged();
							}

						}
					} else {
						maxnum = 0;
						ToastUtil.showCenterShort(context, "�������쳣��");
					}

				}

				break;
			case HttpUtil.EMPTY:
				if(msg.arg1==2){
					maxnum = 0;
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}	
				// ��������Ϊnull
				if (msg.arg1 == 1) {
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
					lbill.clear();
					num = 1;
					if (progressDialog != null && !progressDialog.isShowing()) {
						progressDialog.setMessage("��ѯ���Ѽ�¼��...");
						progressDialog.show();
					}
				
					String url = 
							WebUrlConfig.shopRecord(MyApplication.userModel
									.getUserID(), String.valueOf(num), baseDate
									.getText().toString(),friendPhone);
					http.sendGet(2,url);
				} else {
					ToastUtil.showCenterShort(context, RequestCode.NONETWORK);
				}
				break;
			case 8:
				ToastUtil.showCenterShort(context, "�Ѿ���ȫ����¼��");
				bill_list.removeFooterView(moreView);
				break;
			case 9:
				if (MyApplication.getNetObject().isNetConnected()) {
					String url = 
							WebUrlConfig.shopRecord(MyApplication.userModel
									.getUserID(), String.valueOf(num), baseDate
									.getText().toString(),friendPhone);
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
		back=(TextView)findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		base_back_lay=(LinearLayout)findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		bill_list=(ListView)findViewById(R.id.bill_list);
		baseDate = (EditText) findViewById(R.id.baseDate);
		baseDate.setOnClickListener(this);
		baseDate.setFocusable(false);
		shaixuan=(TextView)findViewById(R.id.shaixuan);
		shaixuan.setOnClickListener(this);
		date=(TextView)findViewById(R.id.date);
		date.setOnClickListener(this);
//		bill_phone=(TextView)findViewById(R.id.bill_phone);
//		bill_phone.setText(MyApplication.userModel.getPhoneNumber());
//		bill_name=(TextView)findViewById(R.id.bill_name);
//		bill_name.setText(MyApplication.userModel.getRealName());
//		bill_jine=(TextView)findViewById(R.id.bill_jine);
//		if (MyApplication.moneyModel!=null&&MyApplication.moneyModel.getExpenseMoney()!=null&&!MyApplication.moneyModel.getExpenseMoney().equals("")) {
//			bill_jine.setText(MyApplication.moneyModel.getExpenseMoney()+"Ԫ");
//		}
		friendPosition=getIntent().getIntExtra("friendPosition", -1);
		isshaixuan=getIntent().getIntExtra("shaixuan", -1);
		if (friendPosition>-1) {
			friendPhone=MyApplication.lOpenUserFriend.get(friendPosition).getFriendPhone();
//			Log.i("TAG", "friendPosition:"+friendPosition);
//			Log.i("TAG", "friendPhone:"+friendPhone);
//			Log.i("TAG", "shifou:"+MyApplication.lOpenUserFriend.size());
//			Log.i("TAG", "shifou:"+MyApplication.lOpenUserFriend.get(friendPosition).getFriendName());
		}
		if (isshaixuan>0) {
			shaixuan.setVisibility(View.GONE);
		}else {
			shaixuan.setVisibility(View.VISIBLE);
		}
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
		if (v==shaixuan) {
			Intent intent=new Intent(BillListActivity.this,LocationFriendListActivity.class);
			intent.putExtra("bill", 1);
			startActivity(intent);
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
