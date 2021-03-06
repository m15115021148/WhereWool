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
 * 定位记录明细列表页面
 * 
 * @author zhang create at 2015年12月22日 下午2:02:07
 */
public class LocationDetailedListActivity extends Activity implements OnScrollListener,
		OnClickListener {
	private static LocationDetailedListActivity context;//本类
	private LinearLayout base_back_lay;//返回键
	private TextView back;//回退
	private SwipeMenuListView location_detailed_list;//定位记录好友列表
	private LocationDetailedListviewAdapter adapter;//定位记录好友列表适配器
	private int friendPosition;//好友
	private EditText baseDate;// 请求月份
	private static MyProgressDialog progressDialog;// 进度条
	private HttpUtil http = null;//网络请求帮助类对象
	private View moreView;// 加载更多底部
	private int num = 1;// 页数
	private List<LocationDetailedModel> ldetailed = new ArrayList<LocationDetailedModel>();// 定位记录集合
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
	private List<LocationDetailedModel> ld = new ArrayList<LocationDetailedModel>();// 每次请求回来的当前页数据
	private ListModel lm = new ListModel();// 每次请求回来的数据
	private TextView location_detailed_name;//用户电话
	private TextView location_detailed_phone;//用户名字
	private TextView location_detailed_img;//用户图片
	private int delPosition=-1;//删除的下标
	private TextView clean;//清除
	private TextView date;//日历图标
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		// 隐藏android系统的状态栏
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
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
		// 友盟统计
		MobclickAgent.onResume(this);
	}
	@Override
	public void onPause() {
		super.onPause();
		// 友盟统计
		MobclickAgent.onPause(this);
	}
	
	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// 关闭进度条
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// 获得定位记录
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
							
	                        View view=EmptyView.getView(context, "暂无定位记录");
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
				
				// 删除定位记录
				if (msg.arg1 == 3) {
					int status = -1;
					CodeModel cm = JSON.parseObject(msg.obj.toString(),
							CodeModel.class);
					if (cm != null) {
						status = cm.getStatus();
						switch (status) {
						case 0:
							ToastUtil.showCenterShort(context,
									"删除记录成功！");
							ldetailed.remove(delPosition);
							adapter.notifyDataSetChanged();
//							httpUtil = new XUtilsHelper(
//									WebUrlConfig.requestMoney(MyApplication.userModel
//											.getUserID()), handler);
//							httpUtil.sendGet(six);
							break;
						case 1:
							ToastUtil.showCenterShort(context,
									"提交申请失败，失败原因：" + cm.getErrorMessage());
							break;
						case 2:
							ToastUtil.showCenterShort(context, RequestCode.NULL);
							break;
						default:
							break;
						}
					}
				}
				// 删除所有定位记录
				if (msg.arg1 == 4) {
					int status = -1;
					CodeModel cm = JSON.parseObject(msg.obj.toString(),
							CodeModel.class);
					if (cm != null) {
						status = cm.getStatus();
						switch (status) {
						case 0:
							ToastUtil.showCenterShort(context,
									"清除记录成功！");
							ldetailed.clear();
							adapter.notifyDataSetChanged();
//							httpUtil = new XUtilsHelper(
//									WebUrlConfig.requestMoney(MyApplication.userModel
//											.getUserID()), handler);
//							httpUtil.sendGet(six);
							break;
						case 1:
							ToastUtil.showCenterShort(context,
									"提交申请失败，失败原因：" + cm.getErrorMessage());
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
					ldetailed.clear();
					num = 1;
					if (progressDialog != null && !progressDialog.isShowing()) {
						progressDialog.setMessage("查询定位记录中...");
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
				ToastUtil.showCenterShort(context, "已经是全部记录！");
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
	 * 初始化控件
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
				normalDia.setTitle("提示");
				normalDia.setMessage("是否确认删除该条记录？");

				normalDia.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (MyApplication.getNetObject().isNetConnected()) {
									if (progressDialog != null && !progressDialog.isShowing()) {
										progressDialog.setMessage("删除记录中...");
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
				normalDia.setNegativeButton("取消",
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
		deleteItem.setTitle("删除");
		deleteItem.setTitleSize(18);
		deleteItem.setTitleColor(Color.WHITE);
		menu.addMenuItem(deleteItem);
	};
	};
	

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
			normalDia.setTitle("提示");
			normalDia.setMessage("是否确认清除该号码的所有记录？");

			normalDia.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (MyApplication.getNetObject().isNetConnected()) {
								if (progressDialog != null && !progressDialog.isShowing()) {
									progressDialog.setMessage("清除记录中...");
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
			normalDia.setNegativeButton("取消",
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
		lastItem = firstVisibleItem + visibleItemCount; // 减1是因为上面加了个addFooterView		
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
}
