package com.sitemap.na2ne.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.FriendSetListviewAdapter;
import com.sitemap.na2ne.adapters.PackageListviewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.CodeModel;
import com.sitemap.na2ne.models.MoneyModel;
import com.sitemap.na2ne.models.PackageModel;
import com.sitemap.na2ne.models.UserFriendModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.EmptyView;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

/**
 * 号码管理列表页面
 * 
 * @author zhang create at 2016年1月5日 下午2: 02:07
 */
public class FriendListActivity extends Activity implements OnClickListener {
	private static FriendListActivity context;// 本类
	private LinearLayout base_back_lay;// 返回键
	private TextView back;// 回退
	private SwipeMenuListView location_list;// 定位记录好友列表
	private FriendSetListviewAdapter adapter;// 定位记录好友列表适配器
	private static MyProgressDialog progressDialog;// 进度条
	private HttpUtil http = null;// 网络请求帮助类对象
	private TextView friend_add;// 添加好友按钮
	private final int zero = 0, one = 1, two = 2, six = 6, SEVEN = 7,
			EIGHT = 8, NINE = 9;// 0，1，2，3，4,5,6.7.8.9
	public List<PackageModel> buyPackage = new ArrayList<PackageModel>();// 购买套餐
	private int listPosition = 0;// 套餐下标

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_friend_list);
		progressDialog = MyProgressDialog.createDialog(this);
		context = this;
		if (http == null) {
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(FriendListActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		if (MyApplication.getNetObject().isNetConnected()) {
			if (progressDialog != null && !progressDialog.isShowing()) {
				progressDialog.setMessage("正在获取号码列表...");
				progressDialog.show();
			}
			http.sendGet(two, WebUrlConfig
					.getFriendList(MyApplication.userModel.getUserID()));
		} else {
			ToastUtil.showCenterShort(this, RequestCode.NONETWORK);
		}
		initview();
	}

	@Override
	public void onResume() {
		if (adapter != null) {
			adapter = new FriendSetListviewAdapter(context);
			location_list.setAdapter(adapter);
		}
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
				// 获取号码列表
				if (msg.arg1 == two) {
					if (MyApplication.luserFriend == null) {
						MyApplication.luserFriend = new ArrayList<UserFriendModel>();
					}
					MyApplication.luserFriend.clear();
					MyApplication.luserFriend = JSONObject.parseArray(
							msg.obj.toString(), UserFriendModel.class);
					if (MyApplication.luserFriend.size() > zero) {
						adapter = new FriendSetListviewAdapter(context);
						location_list.setAdapter(adapter);
					}
					http.sendGet(SEVEN, WebUrlConfig.getPackageInfo("", "4"));
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// 关闭进度条
					}
				}

				// 获得余额
				if (msg.arg1 == six) {
					MyApplication.moneyModel = JSON.parseObject(
							msg.obj.toString(), MoneyModel.class);
					if (MyApplication.moneyModel != null) {
					} else {
						ToastUtil.showCenterShort(context,
								"服务器异常,获取余额失败！");
					}

				}

				// 获得套餐列表
				if (msg.arg1 == SEVEN) {
					MyApplication.lPackage.clear();
					MyApplication.lPackage = JSONObject.parseArray(
							msg.obj.toString(), PackageModel.class);

				}
				// 订购优惠套餐
				if (msg.arg1 == EIGHT) {
					int status = -1;
					CodeModel cm = JSON.parseObject(msg.obj.toString(),
							CodeModel.class);
					if (cm != null) {
						status = cm.getStatus();
						switch (status) {
						case zero:
							ToastUtil.showCenterShort(context,
									"订购优惠套餐成功!");
							http.sendGet(six, WebUrlConfig
									.requestMoney(MyApplication.userModel
											.getUserID()));
							if (MyApplication.getNetObject().isNetConnected()) {
								if (progressDialog != null
										&& !progressDialog.isShowing()) {
									progressDialog.setMessage("正在刷新号码列表...");
									progressDialog.show();
								}

								http.sendGet(two, WebUrlConfig
										.getFriendList(MyApplication.userModel
												.getUserID()));
							} else {
								ToastUtil.showCenterShort(context,
										RequestCode.NONETWORK);
							}
							break;
						case one:
							ToastUtil.showCenterShort(context,
									"订购套餐失败，失败原因：" + cm.getErrorMessage());
							break;
						default:
							break;
						}
					}
				}
				// 重新发送短信
				if (msg.arg1 == NINE) {
					int status = -1;
					CodeModel cm = JSON.parseObject(msg.obj.toString(),
							CodeModel.class);
					if (cm != null) {
						status = cm.getStatus();
						switch (status) {
						case zero:
							ToastUtil.showCenterShort(context,
									"发送成功，请等待对方回复短信后再进行使用!");
							break;
						case one:
							ToastUtil.showCenterShort(context,
									cm.getErrorMessage());
							break;
						default:
							break;
						}
					}
				}
				break;
			case HttpUtil.EMPTY:
				// 获取号码列表
				if (msg.arg1 == two) {
					View view=EmptyView.getView(context, "暂无好友信息");
					((ViewGroup) location_list.getParent()).addView(view);
					location_list.setEmptyView(view);
				} // 获得余额
				if (msg.arg1 == six) {
					ToastUtil.showCenterShort(context, "服务器异常,获取余额失败！");
				}
				// 返回数据为null
				if (msg.arg1 == one) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}
				break;
			case HttpUtil.FAILURE:
				if (msg.arg1 == NINE) {
					ToastUtil.showCenterShort(context,
							"已经申请发送短信，如果没收到短信请再次点击重新发送！");
				} else {
					// ToastUtil.showCenterShort(context,
					// "服务器繁忙，请稍后重试");
					ToastUtil.showCenterShort(context,
							RequestCode.ERRORINFO);
				}

				break;
			case HttpUtil.LOADING:

				break;
			default:
				break;
			}

		}

	};

	// step 1. create a MenuCreator
	SwipeMenuCreator creator = new SwipeMenuCreator() {

		@Override
		public List<List<SwipeMenuItem>> addSwipeMenuList() {
			List<List<SwipeMenuItem>> list = new ArrayList<List<SwipeMenuItem>>();
			// create "open" item
			for (int i = 0; i < MyApplication.luserFriend.size(); i++) {
				if (MyApplication.isCTCCNO(MyApplication.luserFriend.get(i)
						.getFriendPhone())) {
					if (MyApplication.luserFriend.get(i).getIsAgree()
							.equals("0")) {
						List<SwipeMenuItem> itemList = new ArrayList<SwipeMenuItem>();
						// create "delete" item
						SwipeMenuItem deleteItem = new SwipeMenuItem(
								getApplicationContext());
						deleteItem.setBackground(new ColorDrawable(Color
								.parseColor("#FF7F00")));
						deleteItem.setWidth(dp2px(90));
						deleteItem.setTypeId("1");
						deleteItem.setTitle("重新发送短信");
						deleteItem.setFriendType(MyApplication.luserFriend.get(
								i).getFriendType());
						deleteItem.setTitleSize(14);
						deleteItem.setFriendId(MyApplication.luserFriend.get(i)
								.getFriendPhone());// 这里存的参数用的电话号码
						deleteItem.setTitleColor(Color.WHITE);
						// add to menu
						itemList.add(deleteItem);
						list.add(itemList);
						continue;
					} else {
						List<SwipeMenuItem> itemList = new ArrayList<SwipeMenuItem>();
						SwipeMenuItem deleteItem = new SwipeMenuItem(
								getApplicationContext());
						deleteItem.setBackground(new ColorDrawable(Color
								.parseColor("#FF7F00")));
						deleteItem.setWidth(dp2px(90));
						deleteItem.setTitle("购买套餐");
						deleteItem.setTitleSize(14);
						deleteItem.setTypeId("1");
						deleteItem.setFriendType(MyApplication.luserFriend.get(
								i).getFriendType());
						deleteItem.setOldPackage(MyApplication.luserFriend.get(
								i).getPackageID());
						deleteItem.setFriendId(MyApplication.luserFriend.get(i)
								.getFriendID());
						deleteItem.setTitleColor(Color.WHITE);
						// add to menu
						itemList.add(deleteItem);
						list.add(itemList);
						continue;
					}
				}
				if (MyApplication.isCMCCNO(MyApplication.luserFriend.get(i)
						.getFriendPhone())) {
					if (MyApplication.luserFriend.get(i).getIsAgree()
							.equals("0")) {
						List<SwipeMenuItem> itemList = new ArrayList<SwipeMenuItem>();
						SwipeMenuItem deleteItem = new SwipeMenuItem(
								getApplicationContext());
						deleteItem.setBackground(new ColorDrawable(Color
								.parseColor("#FF7F00")));
						deleteItem.setWidth(dp2px(90));
						deleteItem.setTitle("重新发送短信");
						deleteItem.setTypeId("2");
						deleteItem.setFriendType(MyApplication.luserFriend.get(
								i).getFriendType());
						deleteItem.setTitleSize(14);
						deleteItem.setFriendId(MyApplication.luserFriend.get(i)
								.getFriendPhone());// 这里存的参数用的电话号码
						deleteItem.setTitleColor(Color.WHITE);
						// add to menu
						itemList.add(deleteItem);
						list.add(itemList);
						continue;
					} else {
						List<SwipeMenuItem> itemList = new ArrayList<SwipeMenuItem>();
						SwipeMenuItem deleteItem = new SwipeMenuItem(
								getApplicationContext());
						deleteItem.setBackground(new ColorDrawable(Color
								.parseColor("#FF7F00")));
						deleteItem.setWidth(dp2px(90));
						deleteItem.setTitle("购买套餐");
						deleteItem.setTitleSize(14);
						deleteItem.setTypeId("2");
						deleteItem.setFriendType(MyApplication.luserFriend.get(
								i).getFriendType());
						deleteItem.setFriendId(MyApplication.luserFriend.get(i)
								.getFriendID());
						deleteItem.setOldPackage(MyApplication.luserFriend.get(
								i).getPackageID());
						deleteItem.setTitleColor(Color.WHITE);
						// add to menu
						itemList.add(deleteItem);
						list.add(itemList);
						continue;
					}
				}

				if (MyApplication.isCUCCNO(MyApplication.luserFriend.get(i)
						.getFriendPhone())) {
					if (MyApplication.luserFriend.get(i).getIsAgree()
							.equals("0")) {
						List<SwipeMenuItem> itemList = new ArrayList<SwipeMenuItem>();
						SwipeMenuItem deleteItem = new SwipeMenuItem(
								getApplicationContext());
						deleteItem.setBackground(new ColorDrawable(Color
								.parseColor("#FF7F00")));
						deleteItem.setWidth(dp2px(90));
						deleteItem.setTitle("重新发送短信");
						deleteItem.setFriendType(MyApplication.luserFriend.get(
								i).getFriendType());
						deleteItem.setTitleSize(14);
						deleteItem.setTypeId("3");
						deleteItem.setFriendId(MyApplication.luserFriend.get(i)
								.getFriendPhone());// 这里存的参数用的电话号码
						deleteItem.setTitleColor(Color.WHITE);
						// add to menu
						itemList.add(deleteItem);
						list.add(itemList);
						continue;
					} else {
						List<SwipeMenuItem> itemList = new ArrayList<SwipeMenuItem>();
						SwipeMenuItem deleteItem = new SwipeMenuItem(
								getApplicationContext());
						deleteItem.setBackground(new ColorDrawable(Color
								.parseColor("#FF7F00")));
						deleteItem.setWidth(dp2px(90));
						deleteItem.setTitle("购买套餐");
						deleteItem.setTitleSize(14);
						deleteItem.setTypeId("3");
						deleteItem.setFriendType(MyApplication.luserFriend.get(
								i).getFriendType());
						deleteItem.setOldPackage(MyApplication.luserFriend.get(
								i).getPackageID());
						deleteItem.setFriendId(MyApplication.luserFriend.get(i)
								.getFriendID());
						deleteItem.setTitleColor(Color.WHITE);
						// add to menu
						itemList.add(deleteItem);
						list.add(itemList);
						continue;
					}
				}
			}
			return list;
		}

	};

	/**
	 * 初始化控件
	 */
	private void initview() {
		back = (TextView) findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		location_list = (SwipeMenuListView) findViewById(R.id.friend_list);
		adapter = new FriendSetListviewAdapter(context);

		// set creator
		location_list.setMenuCreator(creator);
		location_list.setAdapter(adapter);
		friend_add = (TextView) findViewById(R.id.friend_add);
		friend_add.setOnClickListener(this);
		// 条目点击事件
		location_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(FriendListActivity.this,
						FriendInfoActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});

		// step 2. listener item click event
		// 滑块点击事件
		location_list.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(final int position,
					final SwipeMenu menu, final int index) {

				switch (Integer.parseInt(menu.getMenuItem(index).getTypeId())) {
				// 电信套餐
				case 1:
					if (MyApplication.lPackage.size() > 0) {
						buyPackage.clear();
						for (int i = 0; i < MyApplication.lPackage.size(); i++) {
							if (MyApplication.lPackage.get(i).getIsTry()
									.equals("1")) {
								continue;
							}
							if (MyApplication.lPackage.get(i).getPackageType()
									.equals("1")) {
								buyPackage.add(MyApplication.lPackage.get(i));
							}
							if (!menu.getMenuItem(index).getFriendType()
									.equals("0")) {
								if (MyApplication.lPackage.get(i)
										.getPackageType().equals("4")) {
									buyPackage.add(MyApplication.lPackage
											.get(i));
								}
							}
						}

					}
					break;
				// 移动套餐
				case 2:
					if (MyApplication.lPackage.size() > 0) {
						buyPackage.clear();
						for (int i = 0; i < MyApplication.lPackage.size(); i++) {
							if (MyApplication.lPackage.get(i).getIsTry()
									.equals("1")) {
								continue;
							}
							if (MyApplication.lPackage.get(i).getPackageType()
									.equals("2")) {
								buyPackage.add(MyApplication.lPackage.get(i));
							}
							if (!menu.getMenuItem(index).getFriendType()
									.equals("0")) {
								if (MyApplication.lPackage.get(i)
										.getPackageType().equals("4")) {
									buyPackage.add(MyApplication.lPackage
											.get(i));
								}
							}
						}
					}
					break;
				// 联通套餐
				case 3:
					if (MyApplication.lPackage.size() > 0) {
						buyPackage.clear();
						for (int i = 0; i < MyApplication.lPackage.size(); i++) {
							if (MyApplication.lPackage.get(i).getIsTry()
									.equals("1")) {
								continue;
							}
							if (MyApplication.lPackage.get(i).getPackageType()
									.equals("3")) {
								buyPackage.add(MyApplication.lPackage.get(i));
							}
							if (!menu.getMenuItem(index).getFriendType()
									.equals("0")) {
								if (MyApplication.lPackage.get(i)
										.getPackageType().equals("4")) {
									buyPackage.add(MyApplication.lPackage
											.get(i));
								}
							}
						}
					}
					break;
				default:
					break;
				}

				if (menu.getMenuItem(index).getTitle().equals("购买套餐")) {
					final Dialog dialog = new Dialog(FriendListActivity.this,
							R.style.ChangeDialog);
					dialog.setContentView(R.layout.alertdialog_discount);
					ListView packageList = (ListView) dialog
							.findViewById(R.id.package_list);

					final PackageListviewAdapter packageAdapter = new PackageListviewAdapter(
							context, buyPackage, Integer.parseInt(menu
									.getMenuItem(index).getOldPackage()));
					packageList.setAdapter(packageAdapter);
					packageList
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									listPosition = position;
									packageAdapter.setCheckPackage(Integer
											.parseInt(buyPackage.get(
													listPosition)
													.getPackageID()));
									packageAdapter.notifyDataSetChanged();
								}
							});
					final TextView close = (TextView) dialog
							.findViewById(R.id.change_close);
					TextView discountSub = (TextView) dialog
							.findViewById(R.id.discount_sub);

					discountSub.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {

							AlertDialog.Builder normalDia = new AlertDialog.Builder(
									FriendListActivity.this);
							normalDia.setTitle("提示");
							if (buyPackage.get(listPosition).getPackageType()
									.equals("4")) {
								normalDia
										.setMessage("是否购买好友套餐？(注：免费套餐不一定是实时数据，并不能保证一定能定位成功，请确保目标好友安装过哪儿呢APP，且开启APP才能使用免费套餐，收费套餐则不用安装APP，且定位准确，基本都能定位成功！)");
							} else {
								normalDia.setMessage("是否购买好友套餐？");
							}
							;

							normalDia.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											if (MyApplication.moneyModel != null) {
												if (Double
														.parseDouble(MyApplication.moneyModel
																.getBalance()) >= Double
														.parseDouble(buyPackage
																.get(listPosition)
																.getPackagePrice())) {
													if (MyApplication
															.getNetObject()
															.isNetConnected()) {
														if (progressDialog != null
																&& !progressDialog
																		.isShowing()) {
															progressDialog
																	.setMessage("正在订购套餐...");
															progressDialog
																	.show();
														}

														String url = WebUrlConfig
																.addLocatonTimes(
																		MyApplication.userModel
																				.getUserID(),
																		menu.getMenuItem(
																				index)
																				.getFriendId(),
																		buyPackage
																				.get(listPosition)
																				.getPackageID());
														http.sendGet(EIGHT, url);
														close.performClick();
													} else {
														ToastUtil.showCenterShort(
																		context,
																		RequestCode.NULL);
													}
												} else {
													ToastUtil.showCenterShort(
																	context,
																	"余额不足，请充值！");
													Intent intent = new Intent(
															FriendListActivity.this,
															RechargeActivity.class);
													intent.putExtra(
															"packageMoney",
															buyPackage
																	.get(listPosition)
																	.getPackagePrice());
													context.startActivity(intent);
												}
											}

										}
									});
							normalDia.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									});
							normalDia.create().show();

						}
					});

					close.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					dialog.show();
				} else if (menu.getMenuItem(index).getTitle().equals("重新发送短信")) {
					AlertDialog.Builder normalDia = new AlertDialog.Builder(
							FriendListActivity.this);
					normalDia.setTitle("提示");
					normalDia.setMessage("是否重新发送短信？");

					normalDia.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (MyApplication.getNetObject()
											.isNetConnected()) {
										if (progressDialog != null
												&& !progressDialog.isShowing()) {
											progressDialog
													.setMessage("正在发送短信...");
											progressDialog.show();
										}

										String url = WebUrlConfig.reopen(
												MyApplication.userModel
														.getUserID(),
												MyApplication.userModel
														.getPhoneNumber(), menu
														.getMenuItem(index)
														.getFriendId());
										Log.e("result", MyApplication.userModel
												.getPhoneNumber());
										http.sendGet(NINE, url);
									} else {
										ToastUtil.showCenterShort(
												context, RequestCode.NULL);
									}

								}
							});
					normalDia.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					normalDia.create().show();

				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v == base_back_lay) {
			finish();
		}
		if (v == back) {
			finish();
		}
		if (v == friend_add) {
			Intent intent = new Intent(FriendListActivity.this,
					AddFriendPhoneActivity.class);
			startActivity(intent);
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

}
