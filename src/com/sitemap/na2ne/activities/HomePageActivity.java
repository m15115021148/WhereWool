package com.sitemap.na2ne.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.xutils.x;
import org.xutils.http.RequestParams;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ZoomControls;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.FriendListviewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.CodeModel;
import com.sitemap.na2ne.models.FenceInfoModel;
import com.sitemap.na2ne.models.LocationInfoModel;
import com.sitemap.na2ne.models.MyPointModel;
import com.sitemap.na2ne.models.OldLocationModel;
import com.sitemap.na2ne.models.PackageModel;
import com.sitemap.na2ne.models.SystemInfo;
import com.sitemap.na2ne.models.UpdateModel;
import com.sitemap.na2ne.models.UserFriendModel;
import com.sitemap.na2ne.models.UserModel;
import com.sitemap.na2ne.utils.RecycleImgUtil;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.utils.VibratorUtil;
import com.sitemap.na2ne.utils.WheelView;
import com.sitemap.na2ne.views.EmptyView;
import com.sitemap.na2ne.views.LinkManPopuWindow;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @ClassName: HomePageActivity.java
 * @Description: app首页
 * @author chenhao
 * @Date 2015-11-14
 */

public class HomePageActivity extends Activity implements OnClickListener {
	public static boolean isForeground = false;// 判断页面是否打开
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	public static HomePageActivity context;// 当前页
	private static MyProgressDialog progressDialog;// 进度条
	private HttpUtil http = null;//网络请求帮助类对象
	private static View thisview;// 保存的弹出窗体的VIEW
	private LocationInfoModel locationInfo;
	private UserFriendModel userFriend;// 好友信息对象
	private long exitTime = 0;// 退出时间
	private final int update = 7;// 检测更新接口
	private String city = "";// 所在城市
	private PackageManager manager;// 应用程序包
	private PackageInfo info = null;// APP信息
	private String[] jiange = new String[] { "5分钟", "10分钟", "15分钟", "20分钟","30分钟", "60分钟" };	// 追踪间隔
	private String[] endjiange = new String[] { "30分钟", "1小时", "2小时", "3小时","4小时", "5小时" };	// 追踪时间
	private int[] jiangeTime = new int[] { 5 * 60 * 1000, 10 * 60 * 1000,15 * 60 * 1000, 20 * 60 * 1000, 30 * 60 * 1000, 60 * 60 * 1000, };	// 追踪间隔秒数
	private int[] endjiangeTime = new int[] { 30 * 60 * 1000, 60 * 60 * 1000,120 * 60 * 1000, 180 * 60 * 1000, 240 * 60 * 1000, 300 * 60 * 1000 };// 追踪时间秒数
	private TextView jiangesp, endjiangesp;// 追踪间隔的下拉框，追踪时间的下拉框
	private int jiangeindex = 0, endjiangeindex = 0;// 选中位置
	private String[] weilanType = new String[] { "离开监听", "进入监听" };// 围栏方式
	private int[] weilanIndex = new int[] { 0, 1 };// 围栏对应的index
	private TextView weilanSp;// 围栏下拉框
	private int weilanSelect = 0;// 选中位置
	private int beiweilanSelect = 0;	// 被围栏的选中位置
	private ArrayAdapter<String> jiangeAdapter, endjiangeAdapter;// 追踪间隔的适配器，追踪时间的适配器
	private TextView zhuizongBtn, guijiBtn;// 开始追踪和差轨迹按钮
	private boolean iszhuizong = false;	// 判断是否正在追踪
	private int count = -1, jiangeshi = jiangeTime[0],endjiangeshi = endjiangeTime[0];	// 追踪的次数，活动选择的间隔时间和追踪时间
	private EditText startDate, endDate;// 开始日期，结束日期
	private EditText startTime, endTime;// 开始时间，结束时间
	private int mYear, mYear1;	// 开始日期的年，结束日期的年
	private int mMonth, mMonth1;	// 开始日期的月，结束日期的月
	private int mDay, mDay1;	// 开始日期的日，结束日期的日
	private int mHour, mHour1;
	private int mMinute, mMinute1;
	private List<OldLocationModel> loldLocation = new ArrayList<OldLocationModel>();	// 轨迹的集合
	private TextView mapClear;// 清除地图上的标记
	private TextView mapStop;// 停止追踪
	private RelativeLayout title_info_lay;// 顶部好友布局
	private TextView title_img_tv, title_name_tv, title_phone_tv,title_close_tv;	// 顶部好友信息
	private Timer timer5;// 计时器
	private Timer timer6;// 免费用户接收到的定时计时器
	private final int one = 1;// 1
	private MyApplication app;// application对象
	private OnMarkerClickListener markClick;// 地图标注点击事件
	private boolean islocation = false;// 是否定位
	private final int login = 8;// 登录
	private LinearLayout main_lay;// 主布局
	private int mengcount = 0;// 蒙版加载次数
	private LinearLayout fugai;// 画圈时的背景
	private LinearLayout fugai1;// 用于隐藏好友列表
	private LinearLayout mengban1;// 蒙版1到5
	private boolean ischeck = false;// 是否选中人
	private RelativeLayout dingwei;// 定位按钮
	private boolean isdingwei = false;// 是否定位成功
	private final int locationset = 9;// 位置上报设置
	private List<MyPointModel> listPoint;// 圈选的点的集合
	private LatLng point1;// 画的点
	private Button drawMap;// 画圈按钮
	private String flag = "1";// 画圈确认按钮切换
	private LinearLayout bottom;// 底部确定
	private Button clean, submit;// 清除，提交按钮
	private Button tishi;// 提示
	private final int ZERO = 0, ONE = 1, TWO = 2, THREE = 3, FOUR = 4,FIVE = 5, TEN = 10, ELEVEN = 11, TWELVE = 12, THIRTEEN = 13,FOURTEEN = 14, FIFTEEN = 15, SIXTEEN = 16, SEVENTEEN = 17,
			EIGHTEEN = 18, NIGHTEEN = 19, TWENTY = 20, TWENTYONE = 21,TWENTYTWO = 22;// 1/2/3/4/5/10,11,12,13,14,15,16,17,18,19,20,21,22
	private RadioButton weilan;// 电子围栏
	private ImageView index_dzwl_draw;// 绘制区域按钮
	private LinearLayout index_weilan_layout, index_friend_list, add_friend;// 电子围栏底部布局,好友列表布局,添加好友按钮
	private int weilanStartHour = 0, weilanEndHou = 0, weilanStartMin = 0,weilanEndMin = 0, weilanTime = 0;;// 电子围栏的开始时分和结束时分、总分钟
	private int beiweilanStartHour = 0, beiweilanEndHou = 0,beiweilanStartMin = 0, beiweilanEndMin = 0, beiweilanTime = 0;;// 电子围栏的开始时分和结束时分、总分钟
	private boolean isWeilan = false;// 是否开启围栏
	private int weilanCount = 0, weilanAllCount = 0;// 围栏当前次数和总共次数
	private int beiweilanCount = 0, beiweilanAllCount = 0;// 被开启围栏的当前次数和总共次数
	private String area = "";// 电子围栏范围
	private List<LatLng> larea = new ArrayList<LatLng>();// 左边转百度面
	private int weilanJiange = 1000 * 60 * 5;// 围栏请求间隔
	private FenceInfoModel fenceInfo;// 保存的电子围栏对象
	private ImageView index_dzwl_old;// 历史围栏按钮
	private static final int MSG_SET_ALIAS = 1001;// 极光设置 别名
	// private MessageReceiver jPushReceiver;//接收极光广播
	public static final String MESSAGE_RECEIVED_ACTION = "com.site.na2ne.MESSAGE_RECEIVED_ACTION";
	private final int JPUSH = 1002;// 极光handler
	private double lat, lng;// APP用户的经纬度
	private String JPushInfo = "";// 收到的推送内容
	private String senderUserID = "";// 发送人的ID
	private String baojingMessage = "";// 报警消息
	private ListView popupwinow_listview;// 好友列表LIST
	private RelativeLayout system_info_lay;// 系统通知布局
	private SystemInfo systemInfo;// 公告对象
	private TextView mydingwei;// 定位按钮
	private Marker marker;// 自己的位置
	private ImageView image_linkman;// 号码
	public List<PackageModel> buyPackage = new ArrayList<PackageModel>();// 购买套餐
	private RelativeLayout zhuizong, guiji;// 追踪,轨迹布局
	private SharedPreferences preferences;// 储存器
	private ImageView msgPoint;	// /** 消息 未读 红点 */
	private RelativeLayout xiaoxi;// 消息按钮
	private DrawerLayout mDrawerLayout;// 抽屉布局
	private TextView toguiji, tozhuizong;// 轨迹和追踪切换

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);		// 隐藏应用程序的标题栏，即当前activity的标题栏
		setContentView(R.layout.activity_main);
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		MyApplication.width = wm.getDefaultDisplay().getWidth();
		MyApplication. height = wm.getDefaultDisplay().getHeight();
		progressDialog = MyProgressDialog.createDialog(this);
		context = this;
		if(http == null){
			http = new HttpUtil(handler);
		}
		jiangeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, jiange);
		endjiangeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, endjiange);
		jiangeAdapter.setDropDownViewResource(R.layout.drop_down_item);
		endjiangeAdapter.setDropDownViewResource(R.layout.drop_down_item);
		manager = this.getPackageManager();
		app = (MyApplication) getApplication();
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
			MyApplication.versionName = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
		MyApplication.notiID = preferences.getString("notiID", "0");
		initview();
		if (getIntent().getBooleanExtra("first", false)) {
			mengban1.setVisibility(View.VISIBLE);
			mengcount++;
			Editor editor = preferences.edit();
			editor.putString("versionName", info.versionName);
			editor.putBoolean("isfirst", true);
			editor.commit();
		} else {
			mengcount = 6;
			systemNotify();
		}
		if (preferences.getString("pwd", "") != null
				&& !preferences.getString("pwd", "").equals("")) {
			if (MyApplication.getNetObject().isNetConnected()) {
				progressDialog = MyProgressDialog.createDialog(context);
				if (progressDialog != null && !progressDialog.isShowing()) {
					progressDialog.setMessage("登陆中...");
					progressDialog.show();
				}
				String url = WebUrlConfig.login(
						preferences.getString("username", ""),
						preferences.getString("pwd", ""));
				http.sendGet(login,url);
			} else {
				ToastUtil.showCenterShort(this, RequestCode.NONETWORK);
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
		mLocationClient.stop();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		MobclickAgent.onResume(this);
		isForeground = true;
		if (MyApplication.isLogin > 0) {
			int msgID = preferences.getInt("msgID", 0);
			if (MyApplication.userModel.getMsgID() > msgID) {// 存在没有读的消息
				msgPoint.setVisibility(View.VISIBLE);
			} else {
				msgPoint.setVisibility(View.GONE);
			}
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		MobclickAgent.onPause(this);
		isForeground = false;
		super.onPause();
	}

	/**
	 * 初始化控件
	 */
	private void initview() {
		mMapView = (MapView) findViewById(R.id.bmapView);
		main_lay = (LinearLayout) findViewById(R.id.main_lay);
		thisview=main_lay;
		ImageView wode = (ImageView) findViewById(R.id.index_caidan);
		dingwei = (RelativeLayout) findViewById(R.id.radiobutton_index_dingwei);
		zhuizong = (RelativeLayout) findViewById(R.id.radiobutton_index_zhuizong);
		msgPoint = (ImageView) findViewById(R.id.user_point);
		xiaoxi = (RelativeLayout) findViewById(R.id.xiaoxi);
		xiaoxi.setOnClickListener(this);
		image_linkman = (ImageView) findViewById(R.id.image_linkman);
		image_linkman.setOnClickListener(this);
		title_info_lay = (RelativeLayout) findViewById(R.id.title_info_lay);
		title_img_tv = (TextView) findViewById(R.id.title_img_tv);
		title_name_tv = (TextView) findViewById(R.id.title_name_tv);
		title_phone_tv = (TextView) findViewById(R.id.title_phone_tv);
		title_close_tv = (TextView) findViewById(R.id.title_close_tv);
		title_close_tv.setOnClickListener(this);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); // 关闭手势滑动
		mDrawerLayout.setFocusableInTouchMode(false);//可以点击返回键
		mengban1 = (LinearLayout) findViewById(R.id.meng1);
		mengban1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (mengcount) {
				case 1:
					mengban1.setBackgroundResource(R.drawable.meng2);
					mengcount++;
					break;
				case 2:
					mengban1.setBackgroundResource(R.drawable.meng3);
					mengcount++;
					break;
				case 3:
					mengban1.setVisibility(View.GONE);
					// 强制回收图片资源，减少内存占用
					RecycleImgUtil.releaseImageResouce(mengban1);
					systemNotify();
					break;
				default:
					break;
				}
			}
		});
		mapClear = (TextView) findViewById(R.id.mapClear);
		mapClear.setOnClickListener(this);
		mapStop = (TextView) findViewById(R.id.mapStop);
		mapStop.setOnClickListener(this);
		index_weilan_layout = (LinearLayout) findViewById(R.id.index_weilan_layout);
		fugai = (LinearLayout) findViewById(R.id.fugai);
		fugai1 = (LinearLayout) findViewById(R.id.fugai1);
		drawMap = (Button) findViewById(R.id.drawMap);
		tishi = (Button) findViewById(R.id.tishi);
		drawMap.setOnClickListener(this);
		clean = (Button) findViewById(R.id.clean);
		clean.setOnClickListener(this);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		bottom = (LinearLayout) findViewById(R.id.bottom);
		index_friend_list = (LinearLayout) findViewById(R.id.index_friend_list);
		add_friend = (LinearLayout) findViewById(R.id.add_friend);
		add_friend.setOnClickListener(this);
		popupwinow_listview = (ListView) findViewById(R.id.popupwinow_listview);
		bottom.setOnClickListener(this);
		wode.setOnClickListener(this);
		dingwei.setOnClickListener(this);
		zhuizong.setOnClickListener(this);
		startTime = (EditText) findViewById(R.id.startTime);
		endTime = (EditText) findViewById(R.id.endTime);
		weilanSp = (TextView) findViewById(R.id.weilanSp);
		weilanSp.setText(weilanType[weilanSelect]);
		startTime.setFocusable(false);
		endTime.setFocusable(false);
		system_info_lay = (RelativeLayout) findViewById(R.id.system_info_lay);
		system_info_lay.setOnClickListener(this);
		mydingwei = (TextView) findViewById(R.id.dingwei);
		mydingwei.setOnClickListener(this);
		main_lay.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (index_friend_list.getVisibility() == View.VISIBLE) {
					mDrawerLayout.closeDrawers();
				}
				return false;
			}
		});
		fugai1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				fugai1.setVisibility(View.GONE);
				if (index_friend_list.getVisibility() == View.VISIBLE) {
					mDrawerLayout.closeDrawers();
				}
				return false;
			}
		});
		index_dzwl_draw = (ImageView) findViewById(R.id.index_dzwl_draw);
		startTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handler.sendEmptyMessage(8);
			}
		});
		endTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handler.sendEmptyMessage(9);
			}
		});
		weilanSp.setOnClickListener(this);
		index_dzwl_old = (ImageView) findViewById(R.id.index_dzwl_old);
		index_dzwl_old.setOnClickListener(this);
		index_dzwl_draw.setOnClickListener(this);
		listPoint = new ArrayList<MyPointModel>();
		try {
			mBaiduMap = mMapView.getMap();
			hidezoomView();	// 隐藏缩放控件
			mBaiduMap.setMyLocationEnabled(true);		// 开启定位图层
			mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
			mLocationClient.registerLocationListener(myListener);
		} catch (Exception e) {
		}
		initLocation();
		mMapView.removeViewAt(1);
		mMapView.removeViewAt(2);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mYear1 = c.get(Calendar.YEAR);
		mMonth1 = c.get(Calendar.MONTH);
		mDay1 = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		mHour1 = c.get(Calendar.HOUR_OF_DAY);
		mMinute1 = c.get(Calendar.MINUTE);
	}

	/**
	 * 查询是否更新
	 */
	private void update() {
		if (MyApplication.getNetObject().isNetConnected()) {
			String url = WebUrlConfig.updateVersion(String.valueOf(one), city);
			http.sendGet(update,url);
		} else {
			ToastUtil.showCenterShort(context, RequestCode.NONETWORK);
			// 将广告信息置空
			SharedPreferences preferences = getSharedPreferences("user",Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putString("ad_image", "");
			editor.putString("ad_link", "");
			editor.putString("lostLocation", "");
			editor.commit();
		}
	}

	/**
	 * 查询是否有系统公告
	 */
	private void systemNotify() {
		if (MyApplication.getNetObject().isNetConnected()) {
			String url = WebUrlConfig.systemNotify(MyApplication.notiID);
			http.sendGet(NIGHTEEN,url);
		} else {
			ToastUtil.showCenterShort(context, RequestCode.NONETWORK);
		}
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
				if (msg.arg1 == RequestCode.ADDLINKMAN) {
					MyApplication.luserFriend.clear();
					try {
						MyApplication.luserFriend = JSONObject.parseArray(msg.obj.toString(), UserFriendModel.class);
						if (userFriend != null) {
							for (int i = 0; i < MyApplication.luserFriend.size(); i++) {
								if (MyApplication.luserFriend.get(i).getFriendID().equals(userFriend.getFriendID())) {
									MyApplication.luserFriend.remove(i);
									MyApplication.luserFriend.add(0,userFriend);
									break;
								}
							}
						}
					} catch (Exception e) {
					}
					if (MyApplication.luserFriend.size() > 0) {
						showFriendList(1);
					} else {
						showFriendList();
					}
					mDrawerLayout.openDrawer(Gravity.RIGHT);
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// 关闭进度条
					}
				}
				// 点击好友列表定位
				if (msg.arg1 == RequestCode.FRIEND_POSITION) {
					mBaiduMap.clear();
					if(isdingwei ==false){
					isdingwei = true;
					new Handler().postDelayed(new Runnable() {
						public void run() {
							isdingwei = false;
						}
					}, 50 * 1000);
					}
					try {
						locationInfo = JSON.parseObject(msg.obj.toString(),LocationInfoModel.class);
					} catch (Exception e) {
					}
					if (locationInfo != null) {
						if (locationInfo.getErrorMsg() != null) {
							ToastUtil.showCenterShort(context,locationInfo.getErrorMsg());
							if (progressDialog != null&& progressDialog.isShowing()) {
								progressDialog.dismiss();// 关闭进度条
							}
							return;
						}
						if (locationInfo.getLatitude() == null) {
							ToastUtil.showCenterShort(context, "定位超时！");
							LinkManPopuWindow.dismmis();
							if (progressDialog != null&& progressDialog.isShowing()) {
								progressDialog.dismiss();// 关闭进度条
							}
							return;
						}
						if (locationInfo.getLatitude() != null&& !locationInfo.getLatitude().equals("null")&& !locationInfo.getLatitude().equals("")&& locationInfo.getLongitude() != null&& !locationInfo.getLongitude().equals("")&& !locationInfo.getLongitude().equals("null")) {
							setpoint(changebaidu(new LatLng(Double.parseDouble(locationInfo.getLatitude()),Double.parseDouble(locationInfo.getLongitude()))),locationInfo.getInfo(),locationInfo.getLocTime(), 0);
						} else {
							ToastUtil.showCenterShort(context,"暂无位置信息！");
						}
						LinkManPopuWindow.dismmis();
					}
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// 关闭进度条
					}
				}
				// 定位
				if (msg.arg1 == RequestCode.FRIENDPOSITION) {
					try {
						locationInfo = JSON.parseObject(msg.obj.toString(),LocationInfoModel.class);
					} catch (Exception e) {
					}
					if (locationInfo != null) {
						if (locationInfo.getErrorMsg() != null) {
							ToastUtil.showCenterShort(context,locationInfo.getErrorMsg());
							if (progressDialog != null&& progressDialog.isShowing()) {
								progressDialog.dismiss();// 关闭进度条
							}
							return;
						}
						if (locationInfo.getLatitude() == null) {
							ToastUtil.showCenterShort(context, "定位超时！");
							if (progressDialog != null&& progressDialog.isShowing()) {
								progressDialog.dismiss();// 关闭进度条
							}
							return;
						}
						if (locationInfo.getLatitude() != null&& !locationInfo.getLatitude().equals("null")&& !locationInfo.getLatitude().equals("")&& locationInfo.getLongitude() != null&& !locationInfo.getLongitude().equals("")&& !locationInfo.getLongitude().equals("null")) {
							setpoint(changebaidu(new LatLng(Double.parseDouble(locationInfo.getLatitude()),Double.parseDouble(locationInfo.getLongitude()))),locationInfo.getInfo(),locationInfo.getLocTime(), 1);
						} else {
							ToastUtil.showCenterShort(context,"暂无位置信息！");
						}
						LinkManPopuWindow.dismmis();
					}
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// 关闭进度条
					}
				}
				// 轨迹
				if (msg.arg1 == RequestCode.HISTOTYQUERY) {
					if (loldLocation.size() > 0) {
						loldLocation.clear();
					}
					loldLocation = JSONObject.parseArray(msg.obj.toString(), OldLocationModel.class);				
					if (loldLocation.size() > 0) {
						if (loldLocation.get(0).getErrorMsg() != null) {
							ToastUtil.showCenterShort(context,loldLocation.get(0).getErrorMsg());
							if (progressDialog != null&& progressDialog.isShowing()) {
								progressDialog.dismiss();// 关闭进度条
							}
							return;
						}
						// 构造折线点坐标
						final List<LatLng> points = new ArrayList<LatLng>();
						for (int i = 0; i < loldLocation.size(); i++) {
							if (loldLocation.get(i).getLat() == null|| loldLocation.get(i).getLat().equals("")) {
								loldLocation.remove(i);
								i = i - 1;
								continue;
							}
							if (loldLocation.get(i).getLng() == null|| loldLocation.get(i).getLat().equals("")) {
								loldLocation.remove(i);
								i = i - 1;
								continue;
							}
						}
						for (int i = 0; i < loldLocation.size(); i++) {
							points.add(changebaidu(new LatLng(Double.parseDouble(loldLocation.get(i).getLat()),Double.parseDouble(loldLocation.get(i).getLng()))));
						}
						mBaiduMap.clear();
						mBaiduMap.removeMarkerClickListener(markClick);
						mapClear.setVisibility(View.VISIBLE);
						for (int i = 0; i < points.size(); i++) {
							Marker marker = null;
							// 构建Marker图标
							BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.point);
							// 构建MarkerOption，用于在地图上添加Marker
							OverlayOptions option = new MarkerOptions().position(points.get(i)).icon(bitmap).zIndex(2);
							marker = (Marker) mBaiduMap.addOverlay(option);
							Bundle bundle = new Bundle();
							bundle.putSerializable("info", String.valueOf(i));
							marker.setExtraInfo(bundle);
							updatestatus(points.get(i), 14);
						}
						markClick = new OnMarkerClickListener() {
							@Override
							public boolean onMarkerClick(Marker arg0) {
								if (arg0.getZIndex() == 1) {
									return false;
								}
								View contentView = getLayoutInflater().inflate(R.layout.mapmark_layout, null);
								TextView location_time = (TextView) contentView.findViewById(R.id.location_time);
								TextView location_address = (TextView) contentView.findViewById(R.id.location_address);
								location_time.setText(loldLocation.get(Integer.parseInt((String) arg0.getExtraInfo().get("info"))).getTime());
								location_address.setText(loldLocation.get(Integer.parseInt((String) arg0.getExtraInfo().get("info"))).getAddress());
								location_address.setGravity(Gravity.CENTER);
								location_address.setOnClickListener(new OnClickListener() {
											@Override
											public void onClick(View v) {
												mBaiduMap.hideInfoWindow();
											}
										});
								// 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
								InfoWindow mInfoWindow = new InfoWindow(contentView, points.get(Integer.parseInt((String) arg0.getExtraInfo().get("info"))), -20);
								// 显示InfoWindow
								mBaiduMap.showInfoWindow(mInfoWindow);
								return false;
							}
						};
						mBaiduMap.setOnMarkerClickListener(markClick);
						if (points.size() > 1) {
							// 会多种颜色的折线覆盖物
							OverlayOptions ooPolyline = new PolylineOptions().width(5).color(Color.parseColor("#f13333")).points(points).zIndex(1);
							mBaiduMap.addOverlay(ooPolyline);
						}
					} else {
						ToastUtil.showCenterShort(context, "暂无轨迹信息！");
					}
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// 关闭进度条
					}
				}
				// 更新
				if (msg.arg1 == update) {
					final UpdateModel um = JSON.parseObject(msg.obj.toString(),UpdateModel.class);
					if (um != null) {
						// 将广告信息暂存在本地文件中
						SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
						Editor editor = preferences.edit();
						String ad_image = um.getAdImage();
						String ad_link = um.getAdUrl();
						if (ad_image != "" && ad_image != null) {
							editor.putString("ad_image", ad_image);
						} else {
							editor.putString("ad_image", "");
						}
						if (ad_link != "" && ad_link != null) {
							editor.putString("ad_link", ad_link);
						} else {
							editor.putString("ad_link", "");
						}
						editor.commit();
						MyApplication.isupdate = false;
						// 是否需要更新，true是，false否。
						if (um.getVersion() != null) {
							boolean isUpdate = (info.versionName).compareTo(um.getVersion()) < 0 ? true : false;
							if (!isUpdate) {
								return;
							} else {
								AlertDialog.Builder customDia = new AlertDialog.Builder(HomePageActivity.this);
								customDia.setCancelable(false);
								final View viewDia = LayoutInflater.from(HomePageActivity.this).inflate(R.layout.alertdialog_update, null);
								TextView update_con = (TextView) viewDia.findViewById(R.id.update_con);
								if (um.getContent() != null&& !um.getContent().equals("")) {//分号拆分
									String update_txt[] = um.getContent().split(";");
									StringBuffer sb = new StringBuffer("");
									for (int i = 0; i < update_txt.length; i++) {
										sb.append(update_txt[i] + "\n");
									}
									update_con.setText(sb.toString());
								}
								if (um.getDownloadUrl() != null&& um.getDownloadUrl() != "") {
									MyApplication.downUrl = um.getDownloadUrl();
								}
								if (um.getVersion() != null&& um.getVersion() != "") {
									MyApplication.versionName = um.getVersion();
								}
								customDia.setView(viewDia);
								customDia.setPositiveButton("现在更新",new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,int which) {
												Intent it = new Intent(HomePageActivity.this,NotificationUpdateActivity.class);
												if (um.getIsForceUpdate() != null&& um.getIsForceUpdate().equals("0")) {
													it.putExtra("isforce", "0");// 需要强制更新
												}
												startActivity(it);
												app.setDownload(true);
												dialog.dismiss();
											}
										});
								// 需要强制更新
								if (um.getIsForceUpdate() != null&& um.getIsForceUpdate().equals("0")) {
									customDia.setNegativeButton("关闭程序",new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog,int which) {
															exit();
															dialog.dismiss();
														}
													});
								} else {
									customDia.setNegativeButton("以后再说",new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog,int which) {
															dialog.dismiss();
														}
													});
								}
								customDia.create().show();
							}
						}
					}
				}
				// 自动登录
				if (msg.arg1 == login) {
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// 关闭进度条
					}
					int status = -1;
					MyApplication.userModel = JSON.parseObject(msg.obj.toString(), UserModel.class);
					if (MyApplication.userModel != null) {
						status = MyApplication.userModel.getStatus();
					}
					switch (status) {
					case 1:
						MyApplication.isLogin = 2;
						int msgID = preferences.getInt("msgID", 0);
						if (MyApplication.userModel.getMsgID() > msgID) {// 存在没有读的消息
							msgPoint.setVisibility(View.VISIBLE);
						} else {
							msgPoint.setVisibility(View.GONE);
						}
						break;
					case 2:
						ToastUtil.showCenterShort(context, "用户名或密码错误！");
						SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
						Editor editor = preferences.edit();
						editor.putString("pwd", "");
						break;
					case 3:
						ToastUtil.showCenterShort(context,"服务器异常,请重新登录！");
						break;
					case 4:
						ToastUtil.showCenterShort(context,"邮箱未验证，请先验证邮箱！");
						break;
					default:
						break;
					}

				}
				//公告
				if (msg.arg1 == NIGHTEEN) {
					if(msg.obj.toString()==null||msg.obj.toString().equals("")||msg.obj.toString().equals("null")){
						return;
					}
					systemInfo = JSON.parseObject(msg.obj.toString(),SystemInfo.class);
					if (systemInfo != null) {
						if(MyApplication.isLogin>0){
						Editor editor = preferences.edit();
						editor.putString("notiID", systemInfo.getNotiID());
						editor.commit();
						MyApplication.notiID=systemInfo.getNotiID();
						}
						final View viewDia = LayoutInflater.from(HomePageActivity.this).inflate(R.layout.alertdialog_gonggao, null);
						ImageView gonggao_img = (ImageView) viewDia.findViewById(R.id.gonggao_img);
						x.image().bind(gonggao_img, systemInfo.getSmallImg());
						TextView brief = (TextView) viewDia.findViewById(R.id.brief);
						
						if (systemInfo.getBrief()!= null&& !systemInfo.getBrief().equals("")) {//分号拆分
							String update_txt[] =systemInfo.getBrief().split(";");
							StringBuffer sb = new StringBuffer("");
							for (int i = 0; i < update_txt.length; i++) {
								sb.append(update_txt[i] + "\n");
							}
							brief.setText(sb.toString());
						}
//						brief.setText(systemInfo.getBrief());
						TextView zhidao = (TextView) viewDia.findViewById(R.id.zhidao);
						TextView gonggao_close = (TextView) viewDia.findViewById(R.id.gonggao_close);
						TextView gonggao_title = (TextView) viewDia.findViewById(R.id.gonggao_title);
						final TextView gonggaobg = (TextView) viewDia.findViewById(R.id.gonggaobg);
						gonggao_title.setText(systemInfo.getNotiTitile());
						if(systemInfo.getNotiUrl()==null||systemInfo.getNotiUrl().equals("")){
							zhidao.setText("知道了");
							zhidao.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									LinkManPopuWindow.dismmis();
								}
							});
						}else{
							zhidao.setText("查看详情");
							zhidao.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(HomePageActivity.this,
											MessageWebViewActivity.class);
									intent.putExtra("msgUrl", systemInfo.getNotiUrl());
									startActivity(intent);
									LinkManPopuWindow.dismmis();
								}
							});
						}
						gonggao_close.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								LinkManPopuWindow.dismmis();
							}
						});
						LinkManPopuWindow.showpopuwindow(HomePageActivity.this, thisview, viewDia, 0, 0, 3);
						gonggaobg.setVisibility(View.VISIBLE);
					}
				}
				break;
			case HttpUtil.EMPTY:
				// 返回数据为null
				if (msg.arg1 == 1) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}
				if (msg.arg1 == RequestCode.ADDLINKMAN) {//好友列表
					// 将json格式的解析成集合
					try {
						MyApplication.luserFriend = JSONObject.parseArray(msg.obj.toString(), UserFriendModel.class);
						if (userFriend != null) {
							for (int i = 0; i < MyApplication.luserFriend.size(); i++) {
								if (MyApplication.luserFriend.get(i).getFriendID().equals(userFriend.getFriendID())) {
									MyApplication.luserFriend.remove(i);MyApplication.luserFriend.add(0,userFriend);
									break;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					showFriendList();
					mDrawerLayout.openDrawer(Gravity.RIGHT);
				}	
				//轨迹 
				if(msg.arg1 == RequestCode.HISTOTYQUERY){
					ToastUtil.showCenterShort(context, "暂无轨迹信息！");
				}
				break;
			case HttpUtil.FAILURE:
				if (msg.arg1 != update || msg.arg1 != login|| msg.arg1 != NIGHTEEN) {
				}
				break;
			case HttpUtil.LOADING:
				break;
			case 5:// 追踪
				mapStop.setVisibility(View.VISIBLE);
				timer5 = new Timer();
				timer5.schedule(new TimerTask() {
					@Override
					public void run() {
						if (count * jiangeshi >= endjiangeshi) {
							count = -1;
							iszhuizong = false;
							handler.sendEmptyMessage(10);
							timer5.cancel();
							return;
						} else {
							if (MyApplication.getNetObject().isNetConnected()) {
								count++;
								String url = WebUrlConfig.getFriendPosition(userFriend.getFriendID(),userFriend.getFriendPhone(),userFriend.getNumberType(),userFriend.getPackageID());
								http.sendGet(RequestCode.FRIENDPOSITION,url);
							} else {
								handler.sendEmptyMessage(11);
							}
						}
					}
				}, 1000, jiangeshi);
				break;
			case 6:
				showDialog(0);
				break;
			case 7:
				showDialog(1);
				break;
			case 8:
				showDialog(2);
				break;
			case 9:
				showDialog(3);
				break;
			case 10:
				ToastUtil.showCenterShort(context, "追踪完成！");
				mapStop.setVisibility(View.GONE);
				break;
			case 11:
				ToastUtil.showCenterShort(context,RequestCode.NONETWORK);
				break;
			default:
				break;
		}
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// 关闭进度条
			}
		}
	};

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
	 * 结束日期选择监听
	 */
	private DatePickerDialog.OnDateSetListener ddl1 = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear1 = year;
			mMonth1 = monthOfYear;
			mDay1 = dayOfMonth;
			updateDateDisplay1();
		}
	};

	/**
	 * 开始时间选择监听
	 */
	private TimePickerDialog.OnTimeSetListener tsl = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateTimeDisplay();
		}
	};

	/**
	 * 结束时间选择监听
	 */
	private TimePickerDialog.OnTimeSetListener tsl1 = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateTimeDisplay1();
		}
	};

	/**
	 * 开始时间选择确定更新输入框`
	 */
	private void updateTimeDisplay(int... a) {
		StringBuilder etime = new StringBuilder("");
		// 第一次点开的时间
		if (a.length > 0) {
			if (mHour1 < 10) {
				startTime.setText(etime.append("0" + mHour1).append(":").append((mMinute1 < 10) ? "0" + mMinute1 : mMinute1));
			} else {
				startTime.setText(etime.append(mHour1).append(":").append((mMinute1 < 10) ? "0" + mMinute1 : mMinute1));
			}
			return;
		}
		// 判断输入时间是否大于现在时间
		if (isLargeTime((mHour < 10 ? "0" + mHour : mHour) + ":"+ ((mMinute < 10) ? "0" + mMinute : mMinute))) {
			if (mHour < 10) {
				startTime.setText(etime.append("0" + mHour).append(":").append((mMinute < 10) ? "0" + mMinute : mMinute));
			} else {
				startTime.setText(etime.append(mHour).append(":").append((mMinute < 10) ? "0" + mMinute : mMinute));
			}
		} else {
			ToastUtil.showCenterShort(context, "开始时间必须大于等于现在时间！");
			if (mHour1 < 10) {
				startTime.setText(etime.append("0" + mHour1).append(":").append((mMinute1 < 10) ? "0" + mMinute1 : mMinute1));
			} else {
				startTime.setText(etime.append(mHour1).append(":").append((mMinute1 < 10) ? "0" + mMinute1 : mMinute1));
			}
		}
	}

	/**
	 * 结束时间选择确定更新输入框
	 */
	private void updateTimeDisplay1(int... a) {
		StringBuilder etime = new StringBuilder("");
		// 第一次点开的时间
		if (a.length > 0) {
			if (mHour1 < 10) {
				endTime.setText(etime.append("0" + mHour1).append(":").append((mMinute1 < 10) ? "0" + mMinute1 : mMinute1));
			} else {
				endTime.setText(etime.append(mHour1).append(":").append((mMinute1 < 10) ? "0" + mMinute1 : mMinute1));
			}
			return;
		}
		if (mHour < 10) {
			endTime.setText(etime.append("0" + mHour).append(":").append((mMinute < 10) ? "0" + mMinute : mMinute));
		} else {
			endTime.setText(etime.append(mHour).append(":").append((mMinute < 10) ? "0" + mMinute : mMinute));
		}

	}

	/**
	 * 开始日期选择确定更新输入框
	 */
	private void updateDateDisplay() {
		startDate.setText(new StringBuilder().append(mYear).append("-").append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-").append((mDay < 10) ? "0" + mDay : mDay));
	}

	/**
	 * 结束日期选择确定更新输入框
	 */
	private void updateDateDisplay1() {
		endDate.setText(new StringBuilder().append(mYear1).append("-").append((mMonth1 + 1) < 10 ? "0" + (mMonth1 + 1): (mMonth1 + 1)).append("-").append((mDay1 < 10) ? "0" + mDay1 : mDay1));
	}

	/**
	 * 日期时间的dialog
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			return new DatePickerDialog(this, ddl, mYear, mMonth, mDay);
		case 1:
			return new DatePickerDialog(this, ddl1, mYear1, mMonth1, mDay1);
		case 2:
			return new TimePickerDialog(this, tsl, mHour, mMinute, true);
		case 3:
			return new TimePickerDialog(this, tsl1, mHour, mMinute, true);
		}
		return null;
	}

	/**
	 * 隐藏缩放控件
	 */
	private void hidezoomView() {
		final int count = mMapView.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = mMapView.getChildAt(i);
			if (child instanceof ZoomControls) {
				child.setVisibility(View.INVISIBLE);
			}
		}

	}

	/**
	 * 设置坐标点
	 * @param Latitude
	 * @param Longitude
	 * @param adress
	 * @param type
	 */
	public void setpoint(LatLng point, String adress, String time, int type) {
		TextView button = setpop(adress);
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.index_location);	// 构建Marker图标
		OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).zIndex(1);// 构建MarkerOption，用于在地图上添加Marker
		View contentView = getLayoutInflater().inflate(R.layout.mapmark_layout,null);
		TextView location_time = (TextView) contentView.findViewById(R.id.location_time);
		TextView location_address = (TextView) contentView.findViewById(R.id.location_address);
		location_time.setText(time);
		location_address.setText(adress);
		location_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mBaiduMap.hideInfoWindow();
			}
		});
		final InfoWindow mInfoWindow = new InfoWindow(contentView, point, -80);// 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
		if (type == 0) {
			mBaiduMap.clear();
		}
		mBaiduMap.addOverlay(option);// 在地图上添加Marker，并显示
		mBaiduMap.showInfoWindow(mInfoWindow);// 显示InfoWindow
		markClick = new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker arg0) {
				mBaiduMap.showInfoWindow(mInfoWindow);
				return false;
			}
		};
		mBaiduMap.setOnMarkerClickListener(markClick);
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);		// 正常显示
		CircleOptions circle = new CircleOptions();
		circle.center(point);
		circle.radius(500);
		circle.fillColor(Color.parseColor("#3c1cace9"));
		mBaiduMap.addOverlay(circle);
		updatestatus(point, 15);// 定义地图状态
		mapClear.setVisibility(View.VISIBLE);
	}

	/**
	 * update地图的状态与变化
	 * @param point
	 * @param zoom
	 */
	private void updatestatus(LatLng point, int zoom) {
		MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(zoom).build();
		MapStatusUpdate mMapStatusUpdate =MapStatusUpdateFactory.newMapStatus(mMapStatus);// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
		mBaiduMap.setMapStatus(mMapStatusUpdate);	// 改变地图状态
		islocation = true;
	}

	/**
	 * 自定义地图对话框，展示详情
	 * @param adress
	 * @return
	 */
	private TextView setpop(String adress) {
		TextView button = new TextView(getApplicationContext());
		button.setBackgroundColor(getResources().getColor(R.color.index_tm));
		button.setTextSize(14);
		button.setTextColor(R.color.black);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(R.dimen._200px_in720p, LayoutParams.MATCH_PARENT);
		button.setText(adress);
		layoutParams.setMargins(50, 0, 50, 0);
		button.setLayoutParams(layoutParams);
		return button;
	}

	/**
	 * 配置定位sdk的参数
	 */
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		option.setOpenGps(true);// 打开gps
		option.setAddrType("all");// 返回的定位结果包含地址信息
		// option.disableCache(false);// 禁止启用缓存定位
		// option.setPriority(LocationClientOption.GpsFirst);
		option.setScanSpan(1000 * 60 * 60 * 24);
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	/**
	 * 好友列表
	 * @param a      有参数显示列表，没参数不显示
	 */
	private void showFriendList(int... a) {
		if (preferences.getBoolean("firstfriend", false)) {
			ToastUtil.showCenterShort(context, "定位时将会产生费用，请合理使用！");
			Editor editor = preferences.edit();
			editor.putBoolean("firstfriend", false);
			editor.commit();
		}
		setListviewonclick(popupwinow_listview);
		FriendListviewAdapter adapter = new FriendListviewAdapter(this);
		popupwinow_listview.setAdapter(adapter);
		if (a.length > 0) {
		} else {
			View view=EmptyView.getView(context, "暂无号码");
			 ((ViewGroup)popupwinow_listview.getParent()).addView(view);  
			 popupwinow_listview.setEmptyView(view);
		}
		add_friend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawers();
				Intent intent = new Intent(HomePageActivity.this,AddFriendPhoneActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 设置首页点击监听
	 */
	@Override
	public void onClick(View v) {
		if (v == xiaoxi) {
			if (MyApplication.isLogin < 0) {
				ToastUtil.showCenterShort(context, "请先登录！");
				Intent UserActivityIntent = new Intent(this,LoginActivity.class);
				startActivity(UserActivityIntent);
				return;
			}
			Intent intent = new Intent(HomePageActivity.this,MessageListActivity.class);
			Editor edit = preferences.edit();
			edit.putInt("msgID", MyApplication.userModel.getMsgID());
			edit.commit();
			msgPoint.setVisibility(View.GONE);
			context.startActivity(intent);
		}
		if (v == title_close_tv) {
			if (iszhuizong || isWeilan) {
				AlertDialog.Builder normalDia = new AlertDialog.Builder(HomePageActivity.this);
				normalDia.setTitle("提示");
				normalDia.setMessage("正在使用追踪，是否要关闭使用？");
				normalDia.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								title_info_lay.setVisibility(View.GONE);
								MyApplication.isselect = false;
								ischeck = false;
								userFriend = null;
								mBaiduMap.clear();
								mapClear.setVisibility(View.GONE);
								mapStop.setVisibility(View.GONE);
								if (timer5 != null) {
									timer5.cancel();
								}
								if (timer6 != null) {
									timer6.cancel();
								}
								iszhuizong = false;
								isWeilan = false;
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
			} else {
				title_info_lay.setVisibility(View.GONE);
				MyApplication.isselect = false;
				ischeck = false;
				userFriend = null;
				mBaiduMap.clear();
				mapClear.setVisibility(View.GONE);
				mapStop.setVisibility(View.GONE);
			}
		}
		if (v == mapClear) {
			mBaiduMap.clear();
			mapClear.setVisibility(View.GONE);
		}
		if (v == mapStop) {
			AlertDialog.Builder normalDia = new AlertDialog.Builder(HomePageActivity.this);
			normalDia.setTitle("提示");
			normalDia.setMessage("是否要停止追踪？");
			normalDia.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mapStop.setVisibility(View.GONE);
							timer5.cancel();
							iszhuizong = false;
							mBaiduMap.clear();
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
		// 我的
		if (v.getId() == R.id.index_caidan) {
			if (MyApplication.isLogin < 0) {
				ToastUtil.showCenterShort(context, "请先登录！");
				Intent UserActivityIntent = new Intent(this,LoginActivity.class);
				startActivity(UserActivityIntent);
				finish();
			} else {
				if (iszhuizong || isWeilan) {
					AlertDialog.Builder normalDia = new AlertDialog.Builder(HomePageActivity.this);
					normalDia.setTitle("提示");
					normalDia.setMessage("正在使用追踪，是否要关闭使用？");
					normalDia.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									title_info_lay.setVisibility(View.GONE);
									MyApplication.isselect = false;
									ischeck = false;
									userFriend = null;
									mBaiduMap.clear();
									mapClear.setVisibility(View.GONE);
									mapStop.setVisibility(View.GONE);
									if (timer5 != null) {
										timer5.cancel();
									}
									if (timer6 != null) {
										timer6.cancel();
									}
									iszhuizong = false;
									isWeilan = false;
									MobclickAgent.onEvent(HomePageActivity.this, "mine");
									Intent UserActivityIntent = new Intent(HomePageActivity.this,UserActivity.class);
									startActivity(UserActivityIntent);
									mDrawerLayout.closeDrawers();
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
				} else {
					title_info_lay.setVisibility(View.GONE);
					MyApplication.isselect = false;
					ischeck = false;
					userFriend = null;
					mBaiduMap.clear();
					mapClear.setVisibility(View.GONE);
					mapStop.setVisibility(View.GONE);
					// 友盟统计
					MobclickAgent.onEvent(this, "mine");
					Intent UserActivityIntent = new Intent(this,UserActivity.class);
					startActivity(UserActivityIntent);
				}
			}
		}
		// 号码
		if (v == image_linkman) {
			if (MyApplication.isLogin < 0) {
				ToastUtil.showCenterShort(context, "请先登录！");
				Intent UserActivityIntent = new Intent(this,
						LoginActivity.class);
				startActivity(UserActivityIntent);
				finish();
			} else {
				if (MyApplication.getNetObject().isNetConnected()) {
					if (progressDialog != null && !progressDialog.isShowing()) {
						progressDialog = MyProgressDialog.createDialog(context);
						progressDialog.setMessage("正在获取号码列表...");
						progressDialog.show();
					}
					http.sendGet(RequestCode.ADDLINKMAN,WebUrlConfig.getFriendList(MyApplication.userModel
							.getUserID()));
				} else {
					ToastUtil.showCenterShort(this, "网络无法连接！");
				}
			}
		}
		// 定位
		if (v == dingwei) {
			if (MyApplication.isLogin < 0) {
				ToastUtil.showCenterShort(context, "请先登录！");
				Intent UserActivityIntent = new Intent(this,
						LoginActivity.class);
				startActivity(UserActivityIntent);
				finish();
			} else {
				if (!ischeck) {
					if (index_friend_list.getVisibility() == View.GONE) {
						image_linkman.callOnClick();
					}
					ToastUtil.showCenterShort(context,
							"请点击右上方的蓝色图标,选择被添加定位号码");
					return;
				}
				if (isdingwei) {
					ToastUtil.showCenterShort(context, "两次定位间隔需大于1分钟！");
					return;
				}
				if (MyApplication.getNetObject().isNetConnected()) {
					if (progressDialog != null && !progressDialog.isShowing()) {
						progressDialog = MyProgressDialog.createDialog(context);
						progressDialog.setMessage("正在定位...");
						progressDialog.show();
					}
					String url = WebUrlConfig.getFriendPosition(userFriend.getFriendID(),userFriend.getFriendPhone(),userFriend.getNumberType(),userFriend.getPackageID());
					http.sendGet(RequestCode.FRIEND_POSITION,url);
					// 友盟统计，定位
					MobclickAgent.onEvent(context, "location_now");
				} else {
					ToastUtil.showCenterShort(context, "网络无法连接！");
				}
			}
		}
		// 追踪
		if (v == zhuizong) {
			if (MyApplication.isLogin < 0) {
				ToastUtil.showCenterShort(context, "请先登录！");
				Intent UserActivityIntent = new Intent(this,
						LoginActivity.class);
				startActivity(UserActivityIntent);
				finish();
			} else {
				if (!ischeck) {
					ToastUtil.showCenterShort(context,
							"请点击右上方的列表选择或添加被定位号码！");
					return;
				}
				if (iszhuizong) {
					ToastUtil.showCenterShort(context,
							"已经在追踪了，请先停止当前追踪！");
					return;
				}
				View contentView = getLayoutInflater().inflate(R.layout.popupwinow_zhuizong, null);
				jiangesp = (TextView) contentView.findViewById(R.id.jiangesp);
				endjiangesp = (TextView) contentView.findViewById(R.id.endjiangesp);
				toguiji = (TextView) contentView.findViewById(R.id.toguiji);
				toguiji.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						toguiji();
					}
				});
				jiangesp.setText(jiange[jiangeindex]);
				endjiangesp.setText(endjiange[endjiangeindex]);
				jiangesp.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						View outerView = LayoutInflater.from(context).inflate(R.layout.wheel_view, null);
						WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
						wv.setOffset(2);
						wv.setItems(Arrays.asList(jiange));
						wv.setSeletion(jiangeindex);
						wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
							@Override
							public void onSelected(int selectedIndex,String item) {
								jiangeindex = selectedIndex - 2;
								jiangeshi = jiangeTime[jiangeindex];
								jiangesp.setText(jiange[jiangeindex]);

							}
						});
						new AlertDialog.Builder(context).setTitle("间隔时间")
								.setView(outerView)
								.setPositiveButton("确定", null).show();
					}
				});
				endjiangesp.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						View outerView = LayoutInflater.from(context).inflate(R.layout.wheel_view, null);
						WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
						wv.setOffset(2);
						wv.setItems(Arrays.asList(endjiange));
						wv.setSeletion(endjiangeindex);
						wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
							@Override
							public void onSelected(int selectedIndex,String item) {
								endjiangeindex = selectedIndex - 2;
								endjiangeshi = endjiangeTime[endjiangeindex];
								endjiangesp.setText(endjiange[endjiangeindex]);
							}
						});
						new AlertDialog.Builder(context).setTitle("追踪时间")
								.setView(outerView)
								.setPositiveButton("确定", null).show();
					}
				});
				zhuizongBtn = (TextView) contentView.findViewById(R.id.zhuizong);
				LinkManPopuWindow.showpopuwindow(this, v, contentView, 0, 0, 2);
				zhuizongBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (jiangeshi >= endjiangeshi) {
							ToastUtil.showCenterShort(context,"追踪时间必须大于间隔时间！");
							return;
						}
						if (!userFriend.getNumberType().equals("1")) {
							AlertDialog.Builder normalDia = new AlertDialog.Builder(HomePageActivity.this);
							normalDia.setTitle("提示");
							normalDia.setMessage("该好友为按次数计费用户，追踪期间将持续扣费，请确定是否追踪？");
							normalDia.setPositiveButton("确定",new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,int which) {
											ToastUtil.showCenterShort(context, "开始追踪！");
											iszhuizong = true;
											LinkManPopuWindow.dismmis();
											handler.sendEmptyMessage(5);
											// 友盟统计，追踪
											MobclickAgent.onEvent(context,"location_trace");
										}
									});
							normalDia.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,int which) {
											dialog.dismiss();
										}
									});
							normalDia.create().show();
						} else {
							ToastUtil.showCenterShort(context, "开始追踪！");
							iszhuizong = true;
							LinkManPopuWindow.dismmis();
							handler.sendEmptyMessage(5);
							// 友盟统计，追踪
							MobclickAgent.onEvent(context, "location_trace");
						}
					}
				});
			}
		}
		// 定位自己
		if (v == mydingwei) {
			mLocationClient.requestLocation();
			if (lat != 0) {
				marker.remove();
				LatLng zuobiao = changebaidu(new LatLng(lat, lng));
				BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.dingweimy);
				OverlayOptions option = new MarkerOptions().position(zuobiao).icon(bitmap).zIndex(2);// 构建MarkerOption，用于在地图上添加Marker
				marker = (Marker) mBaiduMap.addOverlay(option);
				updatestatus(zuobiao, 15);
			}
		}
	}
	/**
	 * listview点击监听
	 * @param listView
	 */
	private void setListviewonclick(ListView listView) {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if (iszhuizong) {
					ToastUtil.showCenterShort(context,"正在追踪，如要更改定位号码请先停止追踪！");
					return;
				}
				if (userFriend != null) {
					if (MyApplication.lOpenUserFriend.get(position).getFriendID().equals(userFriend.getFriendID())) {
						ToastUtil.showCenterShort(context,"同一定位号码需等待1分钟后才能继续定位！");
						return;
					}
				}
				if (MyApplication.getNetObject().isNetConnected()) {
					if (progressDialog != null && !progressDialog.isShowing()) {
						progressDialog = MyProgressDialog.createDialog(context);
						progressDialog.setMessage("正在定位...");
						progressDialog.show();
					}
					String url = WebUrlConfig.getFriendPosition(MyApplication.lOpenUserFriend.get(position).getFriendID(),MyApplication.lOpenUserFriend.get(position).getFriendPhone(),MyApplication.lOpenUserFriend.get(position).getNumberType(),MyApplication.lOpenUserFriend.get(position).getPackageID());
					http.sendGet(RequestCode.FRIEND_POSITION,url);
					// 友盟统计，定位
					MobclickAgent.onEvent(context, "location_now");
				} else {
					ToastUtil.showCenterShort(context, "网络无法连接！");
				}
				ischeck = true;
				userFriend = MyApplication.lOpenUserFriend.get(position);
				title_info_lay.setVisibility(View.VISIBLE);
				MyApplication.isselect = true;
				if (userFriend.getFriendType().equals("0")) {
					title_img_tv.setBackgroundResource(R.drawable.listimga);
				} else {
					title_img_tv.setBackgroundResource(R.drawable.listimgb);
				}
				title_name_tv.setText(userFriend.getFriendName());
				title_phone_tv.setText("");
				mDrawerLayout.closeDrawers();
			}
		});
	}

	/**
	 * 定位监听器
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			lat = location.getLatitude();
			lng = location.getLongitude();
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// 单位：公里每小时
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// 单位：米
				sb.append("\ndirection : ");
				sb.append(location.getDirection());// 单位度
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps定位成功");
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("网络定位成功");
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
				sb.append("\ndescribe : ");
				sb.append("离线定位成功，离线定位结果也是有效的");
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("网络不同导致定位失败，请检查网络是否通畅");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
			}
			// 这是第一次进来默认的地图
			// 定位当前位置之后，显示在地图的位置
			LatLng point = new LatLng(location.getLatitude(),location.getLongitude());
			city = location.getCity();
			if (MyApplication.isupdate) {
				update();
			}
			if (islocation) {
			} else {
				BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.dingweimy);
				// 构建MarkerOption，用于在地图上添加Marker
				OverlayOptions option = new MarkerOptions().position(changebaidu(point)).icon(bitmap).zIndex(2);
				marker = (Marker) mBaiduMap.addOverlay(option);
				updatestatus(changebaidu(point), 15);
			}
		}
	}

	/**
	 * 返回键，2秒内再次点击退出应用
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
				mDrawerLayout.closeDrawers();
				return true;
			}
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序!",Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 退出应用程序按钮功能
	 */
	@SuppressWarnings("deprecation")
	public  void exit() {
		// 设置程序完全退出
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			HomePageActivity.context.finish();
			System.exit(0);
		} else {// android2.1
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			am.restartPackage(getPackageName());
		}
	}

	/**
	 * GPS转百度坐标
	 * @param ll
	 * @return
	 */
	private LatLng changebaidu(LatLng ll) {
		// 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
		CoordinateConverter converter = new CoordinateConverter();
		converter.from(CoordType.COMMON);
		converter.coord(ll);
		LatLng desLatLng = converter.convert();
		return desLatLng;
	}

	/**
	 * 判断输入时间是否比现在时间大
	 * @param editTime
	 * @return
	 */
	public boolean isLargeTime(String editTime) {
		if (Integer.parseInt(editTime.substring(0, 2)) > mHour1) {
			return true;
		} else if (Integer.parseInt(editTime.substring(0, 2)) == mHour1) {
			if (Integer.parseInt(editTime.substring(3)) >= mMinute1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 点切换轨迹的事件
	 */
	private void toguiji() {
		LinkManPopuWindow.dismmis();
		if (MyApplication.isLogin < 0) {
			ToastUtil.showCenterShort(context, "请先登录！");
			Intent UserActivityIntent = new Intent(this, LoginActivity.class);
			startActivity(UserActivityIntent);
			finish();
		} else {
			if (!ischeck) {
				ToastUtil.showCenterShort(context,
						"请点击右上方的列表选择或添加被定位号码！");
				return;
			}
			View contentView = getLayoutInflater().inflate(R.layout.popupwinow_guiji, null);
			startDate = (EditText) contentView.findViewById(R.id.startDate);
			endDate = (EditText) contentView.findViewById(R.id.endDate);
			tozhuizong = (TextView) contentView.findViewById(R.id.tozhuizong);
			tozhuizong.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					tozhuizong();
				}
			});
			startDate.setFocusable(false);
			endDate.setFocusable(false);
			updateDateDisplay();
			updateDateDisplay1();
			guijiBtn = (TextView) contentView.findViewById(R.id.guiji);
			startDate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					handler.sendEmptyMessage(6);
				}
			});
			endDate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					handler.sendEmptyMessage(7);
				}
			});
			LinkManPopuWindow.showpopuwindow(this, thisview, contentView, 0, 0, 2);
			guijiBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (startDate.getText().toString().equals("")|| startDate.getText().toString() == null) {
						ToastUtil.showCenterShort(context, "开始日期不能为空！");
						return;
					}
					if (endDate.getText().toString().equals("")|| endDate.getText().toString() == null) {
						ToastUtil.showCenterShort(context, "结束日期不能为空！");
						return;
					}
					SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date d1 = f.parse(startDate.getText().toString());
						Date d2 = f.parse(endDate.getText().toString());
						if (d2.getTime() < d1.getTime()) {
							ToastUtil.showCenterShort(context,"结束日期不能小于开始日期！");
							return;
						}
						long day = (d2.getTime() - d1.getTime()) / 1000 / 60/ 60 / 24;
						if (day > 30) {
							ToastUtil.showCenterShort(context,"轨迹查询时间不能大于30天！");
							return;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (MyApplication.getNetObject().isNetConnected()) {
						if (progressDialog != null
								&& !progressDialog.isShowing()) {
							progressDialog = MyProgressDialog.createDialog(context);
							progressDialog.setMessage("正在查询轨迹...");
							progressDialog.show();
						}
						count++;
						String url = WebUrlConfig.getMovingStrack(userFriend.getFriendID(),startDate.getText().toString()+ " 00:00:01", endDate.getText().toString()+ " 23:59:59", userFriend.getFriendPhone(), userFriend.getFriendType(), userFriend.getNumberType(), userFriend.getIsAgree());
						LinkManPopuWindow.dismmis();
						http.sendGet(RequestCode.HISTOTYQUERY,url);
						// 友盟统计，轨迹
						MobclickAgent.onEvent(context, "location_history");
					} else {
						ToastUtil.showCenterShort(context, "网络无法连接！");
					}
				}
			});
		}
	}

	/**
	 * 点切换追踪的事件
	 */
	private void tozhuizong() {
		LinkManPopuWindow.dismmis();
		if (MyApplication.isLogin < 0) {
			ToastUtil.showCenterShort(context, "请先登录！");
			Intent UserActivityIntent = new Intent(this, LoginActivity.class);
			startActivity(UserActivityIntent);
			finish();
		} else {
			if (!ischeck) {
				ToastUtil.showCenterShort(context,
						"请点击右上方的列表选择或添加被定位号码！");
				return;
			}
			if (iszhuizong) {
				ToastUtil.showCenterShort(context, "已经在追踪了，请先停止当前追踪！");
				return;
			}
			View contentView = getLayoutInflater().inflate(R.layout.popupwinow_zhuizong, null);
			jiangesp = (TextView) contentView.findViewById(R.id.jiangesp);
			endjiangesp = (TextView) contentView.findViewById(R.id.endjiangesp);
			toguiji = (TextView) contentView.findViewById(R.id.toguiji);
			toguiji.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					toguiji();
				}
			});
			jiangesp.setText(jiange[jiangeindex]);
			endjiangesp.setText(endjiange[endjiangeindex]);
			jiangesp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					View outerView = LayoutInflater.from(context).inflate(R.layout.wheel_view, null);
					WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
					wv.setOffset(2);
					wv.setItems(Arrays.asList(jiange));
					wv.setSeletion(jiangeindex);
					wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
						@Override
						public void onSelected(int selectedIndex, String item) {
							jiangeindex = selectedIndex - 2;
							jiangeshi = jiangeTime[jiangeindex];
							jiangesp.setText(jiange[jiangeindex]);

						}
					});
					new AlertDialog.Builder(context).setTitle("间隔时间").setView(outerView).setPositiveButton("确定", null).show();
				}
			});
			endjiangesp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					View outerView = LayoutInflater.from(context).inflate(R.layout.wheel_view, null);
					WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
					wv.setOffset(2);
					wv.setItems(Arrays.asList(endjiange));
					wv.setSeletion(endjiangeindex);
					wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
						@Override
						public void onSelected(int selectedIndex, String item) {
							endjiangeindex = selectedIndex - 2;
							endjiangeshi = endjiangeTime[endjiangeindex];
							endjiangesp.setText(endjiange[endjiangeindex]);
						}
					});
					new AlertDialog.Builder(context).setTitle("追踪时间").setView(outerView).setPositiveButton("确定", null).show();
				}
			});
			zhuizongBtn = (TextView) contentView.findViewById(R.id.zhuizong);
			LinkManPopuWindow.showpopuwindow(this, thisview, contentView, 0, 0, 2);
			zhuizongBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (jiangeshi >= endjiangeshi) {
						ToastUtil.showCenterShort(context,"追踪时间必须大于间隔时间！");
						return;
					}
					if (!userFriend.getNumberType().equals("1")) {
						AlertDialog.Builder normalDia = new AlertDialog.Builder(HomePageActivity.this);
						normalDia.setTitle("提示");
						normalDia.setMessage("该好友为按次数计费用户，追踪期间将持续扣费，请确定是否追踪？");
						normalDia.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
										ToastUtil.showCenterShort(context, "开始追踪！");
										iszhuizong = true;
										LinkManPopuWindow.dismmis();
										handler.sendEmptyMessage(5);
										// 友盟统计，追踪
										MobclickAgent.onEvent(context,"location_trace");
									}
								});
						normalDia.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
										dialog.dismiss();
									}
								});
						normalDia.create().show();
					} else {
						ToastUtil.showCenterShort(context, "开始追踪！");
						iszhuizong = true;
						LinkManPopuWindow.dismmis();
						handler.sendEmptyMessage(5);
						// 友盟统计，追踪
						MobclickAgent.onEvent(context, "location_trace");
					}
				}
			});
		}
	}
}
