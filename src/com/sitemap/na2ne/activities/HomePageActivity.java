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
 * @Description: app��ҳ
 * @author chenhao
 * @Date 2015-11-14
 */

public class HomePageActivity extends Activity implements OnClickListener {
	public static boolean isForeground = false;// �ж�ҳ���Ƿ��
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	public static HomePageActivity context;// ��ǰҳ
	private static MyProgressDialog progressDialog;// ������
	private HttpUtil http = null;//����������������
	private static View thisview;// ����ĵ��������VIEW
	private LocationInfoModel locationInfo;
	private UserFriendModel userFriend;// ������Ϣ����
	private long exitTime = 0;// �˳�ʱ��
	private final int update = 7;// �����½ӿ�
	private String city = "";// ���ڳ���
	private PackageManager manager;// Ӧ�ó����
	private PackageInfo info = null;// APP��Ϣ
	private String[] jiange = new String[] { "5����", "10����", "15����", "20����","30����", "60����" };	// ׷�ټ��
	private String[] endjiange = new String[] { "30����", "1Сʱ", "2Сʱ", "3Сʱ","4Сʱ", "5Сʱ" };	// ׷��ʱ��
	private int[] jiangeTime = new int[] { 5 * 60 * 1000, 10 * 60 * 1000,15 * 60 * 1000, 20 * 60 * 1000, 30 * 60 * 1000, 60 * 60 * 1000, };	// ׷�ټ������
	private int[] endjiangeTime = new int[] { 30 * 60 * 1000, 60 * 60 * 1000,120 * 60 * 1000, 180 * 60 * 1000, 240 * 60 * 1000, 300 * 60 * 1000 };// ׷��ʱ������
	private TextView jiangesp, endjiangesp;// ׷�ټ����������׷��ʱ���������
	private int jiangeindex = 0, endjiangeindex = 0;// ѡ��λ��
	private String[] weilanType = new String[] { "�뿪����", "�������" };// Χ����ʽ
	private int[] weilanIndex = new int[] { 0, 1 };// Χ����Ӧ��index
	private TextView weilanSp;// Χ��������
	private int weilanSelect = 0;// ѡ��λ��
	private int beiweilanSelect = 0;	// ��Χ����ѡ��λ��
	private ArrayAdapter<String> jiangeAdapter, endjiangeAdapter;// ׷�ټ������������׷��ʱ���������
	private TextView zhuizongBtn, guijiBtn;// ��ʼ׷�ٺͲ�켣��ť
	private boolean iszhuizong = false;	// �ж��Ƿ�����׷��
	private int count = -1, jiangeshi = jiangeTime[0],endjiangeshi = endjiangeTime[0];	// ׷�ٵĴ������ѡ��ļ��ʱ���׷��ʱ��
	private EditText startDate, endDate;// ��ʼ���ڣ���������
	private EditText startTime, endTime;// ��ʼʱ�䣬����ʱ��
	private int mYear, mYear1;	// ��ʼ���ڵ��꣬�������ڵ���
	private int mMonth, mMonth1;	// ��ʼ���ڵ��£��������ڵ���
	private int mDay, mDay1;	// ��ʼ���ڵ��գ��������ڵ���
	private int mHour, mHour1;
	private int mMinute, mMinute1;
	private List<OldLocationModel> loldLocation = new ArrayList<OldLocationModel>();	// �켣�ļ���
	private TextView mapClear;// �����ͼ�ϵı��
	private TextView mapStop;// ֹͣ׷��
	private RelativeLayout title_info_lay;// �������Ѳ���
	private TextView title_img_tv, title_name_tv, title_phone_tv,title_close_tv;	// ����������Ϣ
	private Timer timer5;// ��ʱ��
	private Timer timer6;// ����û����յ��Ķ�ʱ��ʱ��
	private final int one = 1;// 1
	private MyApplication app;// application����
	private OnMarkerClickListener markClick;// ��ͼ��ע����¼�
	private boolean islocation = false;// �Ƿ�λ
	private final int login = 8;// ��¼
	private LinearLayout main_lay;// ������
	private int mengcount = 0;// �ɰ���ش���
	private LinearLayout fugai;// ��Ȧʱ�ı���
	private LinearLayout fugai1;// �������غ����б�
	private LinearLayout mengban1;// �ɰ�1��5
	private boolean ischeck = false;// �Ƿ�ѡ����
	private RelativeLayout dingwei;// ��λ��ť
	private boolean isdingwei = false;// �Ƿ�λ�ɹ�
	private final int locationset = 9;// λ���ϱ�����
	private List<MyPointModel> listPoint;// Ȧѡ�ĵ�ļ���
	private LatLng point1;// ���ĵ�
	private Button drawMap;// ��Ȧ��ť
	private String flag = "1";// ��Ȧȷ�ϰ�ť�л�
	private LinearLayout bottom;// �ײ�ȷ��
	private Button clean, submit;// ������ύ��ť
	private Button tishi;// ��ʾ
	private final int ZERO = 0, ONE = 1, TWO = 2, THREE = 3, FOUR = 4,FIVE = 5, TEN = 10, ELEVEN = 11, TWELVE = 12, THIRTEEN = 13,FOURTEEN = 14, FIFTEEN = 15, SIXTEEN = 16, SEVENTEEN = 17,
			EIGHTEEN = 18, NIGHTEEN = 19, TWENTY = 20, TWENTYONE = 21,TWENTYTWO = 22;// 1/2/3/4/5/10,11,12,13,14,15,16,17,18,19,20,21,22
	private RadioButton weilan;// ����Χ��
	private ImageView index_dzwl_draw;// ��������ť
	private LinearLayout index_weilan_layout, index_friend_list, add_friend;// ����Χ���ײ�����,�����б���,��Ӻ��Ѱ�ť
	private int weilanStartHour = 0, weilanEndHou = 0, weilanStartMin = 0,weilanEndMin = 0, weilanTime = 0;;// ����Χ���Ŀ�ʼʱ�ֺͽ���ʱ�֡��ܷ���
	private int beiweilanStartHour = 0, beiweilanEndHou = 0,beiweilanStartMin = 0, beiweilanEndMin = 0, beiweilanTime = 0;;// ����Χ���Ŀ�ʼʱ�ֺͽ���ʱ�֡��ܷ���
	private boolean isWeilan = false;// �Ƿ���Χ��
	private int weilanCount = 0, weilanAllCount = 0;// Χ����ǰ�������ܹ�����
	private int beiweilanCount = 0, beiweilanAllCount = 0;// ������Χ���ĵ�ǰ�������ܹ�����
	private String area = "";// ����Χ����Χ
	private List<LatLng> larea = new ArrayList<LatLng>();// ���ת�ٶ���
	private int weilanJiange = 1000 * 60 * 5;// Χ��������
	private FenceInfoModel fenceInfo;// ����ĵ���Χ������
	private ImageView index_dzwl_old;// ��ʷΧ����ť
	private static final int MSG_SET_ALIAS = 1001;// �������� ����
	// private MessageReceiver jPushReceiver;//���ռ���㲥
	public static final String MESSAGE_RECEIVED_ACTION = "com.site.na2ne.MESSAGE_RECEIVED_ACTION";
	private final int JPUSH = 1002;// ����handler
	private double lat, lng;// APP�û��ľ�γ��
	private String JPushInfo = "";// �յ�����������
	private String senderUserID = "";// �����˵�ID
	private String baojingMessage = "";// ������Ϣ
	private ListView popupwinow_listview;// �����б�LIST
	private RelativeLayout system_info_lay;// ϵͳ֪ͨ����
	private SystemInfo systemInfo;// �������
	private TextView mydingwei;// ��λ��ť
	private Marker marker;// �Լ���λ��
	private ImageView image_linkman;// ����
	public List<PackageModel> buyPackage = new ArrayList<PackageModel>();// �����ײ�
	private RelativeLayout zhuizong, guiji;// ׷��,�켣����
	private SharedPreferences preferences;// ������
	private ImageView msgPoint;	// /** ��Ϣ δ�� ��� */
	private RelativeLayout xiaoxi;// ��Ϣ��ť
	private DrawerLayout mDrawerLayout;// ���벼��
	private TextView toguiji, tozhuizong;// �켣��׷���л�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
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
					progressDialog.setMessage("��½��...");
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
			if (MyApplication.userModel.getMsgID() > msgID) {// ����û�ж�����Ϣ
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
	 * ��ʼ���ؼ�
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
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); // �ر����ƻ���
		mDrawerLayout.setFocusableInTouchMode(false);//���Ե�����ؼ�
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
					// ǿ�ƻ���ͼƬ��Դ�������ڴ�ռ��
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
			hidezoomView();	// �������ſؼ�
			mBaiduMap.setMyLocationEnabled(true);		// ������λͼ��
			mLocationClient = new LocationClient(getApplicationContext()); // ����LocationClient��
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
	 * ��ѯ�Ƿ����
	 */
	private void update() {
		if (MyApplication.getNetObject().isNetConnected()) {
			String url = WebUrlConfig.updateVersion(String.valueOf(one), city);
			http.sendGet(update,url);
		} else {
			ToastUtil.showCenterShort(context, RequestCode.NONETWORK);
			// �������Ϣ�ÿ�
			SharedPreferences preferences = getSharedPreferences("user",Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putString("ad_image", "");
			editor.putString("ad_link", "");
			editor.putString("lostLocation", "");
			editor.commit();
		}
	}

	/**
	 * ��ѯ�Ƿ���ϵͳ����
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
				progressDialog.dismiss();// �رս�����
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// ��ȡ�����б�
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
						progressDialog.dismiss();// �رս�����
					}
				}
				// ��������б�λ
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
								progressDialog.dismiss();// �رս�����
							}
							return;
						}
						if (locationInfo.getLatitude() == null) {
							ToastUtil.showCenterShort(context, "��λ��ʱ��");
							LinkManPopuWindow.dismmis();
							if (progressDialog != null&& progressDialog.isShowing()) {
								progressDialog.dismiss();// �رս�����
							}
							return;
						}
						if (locationInfo.getLatitude() != null&& !locationInfo.getLatitude().equals("null")&& !locationInfo.getLatitude().equals("")&& locationInfo.getLongitude() != null&& !locationInfo.getLongitude().equals("")&& !locationInfo.getLongitude().equals("null")) {
							setpoint(changebaidu(new LatLng(Double.parseDouble(locationInfo.getLatitude()),Double.parseDouble(locationInfo.getLongitude()))),locationInfo.getInfo(),locationInfo.getLocTime(), 0);
						} else {
							ToastUtil.showCenterShort(context,"����λ����Ϣ��");
						}
						LinkManPopuWindow.dismmis();
					}
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// �رս�����
					}
				}
				// ��λ
				if (msg.arg1 == RequestCode.FRIENDPOSITION) {
					try {
						locationInfo = JSON.parseObject(msg.obj.toString(),LocationInfoModel.class);
					} catch (Exception e) {
					}
					if (locationInfo != null) {
						if (locationInfo.getErrorMsg() != null) {
							ToastUtil.showCenterShort(context,locationInfo.getErrorMsg());
							if (progressDialog != null&& progressDialog.isShowing()) {
								progressDialog.dismiss();// �رս�����
							}
							return;
						}
						if (locationInfo.getLatitude() == null) {
							ToastUtil.showCenterShort(context, "��λ��ʱ��");
							if (progressDialog != null&& progressDialog.isShowing()) {
								progressDialog.dismiss();// �رս�����
							}
							return;
						}
						if (locationInfo.getLatitude() != null&& !locationInfo.getLatitude().equals("null")&& !locationInfo.getLatitude().equals("")&& locationInfo.getLongitude() != null&& !locationInfo.getLongitude().equals("")&& !locationInfo.getLongitude().equals("null")) {
							setpoint(changebaidu(new LatLng(Double.parseDouble(locationInfo.getLatitude()),Double.parseDouble(locationInfo.getLongitude()))),locationInfo.getInfo(),locationInfo.getLocTime(), 1);
						} else {
							ToastUtil.showCenterShort(context,"����λ����Ϣ��");
						}
						LinkManPopuWindow.dismmis();
					}
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// �رս�����
					}
				}
				// �켣
				if (msg.arg1 == RequestCode.HISTOTYQUERY) {
					if (loldLocation.size() > 0) {
						loldLocation.clear();
					}
					loldLocation = JSONObject.parseArray(msg.obj.toString(), OldLocationModel.class);				
					if (loldLocation.size() > 0) {
						if (loldLocation.get(0).getErrorMsg() != null) {
							ToastUtil.showCenterShort(context,loldLocation.get(0).getErrorMsg());
							if (progressDialog != null&& progressDialog.isShowing()) {
								progressDialog.dismiss();// �رս�����
							}
							return;
						}
						// �������ߵ�����
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
							// ����Markerͼ��
							BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.point);
							// ����MarkerOption�������ڵ�ͼ�����Marker
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
								// ����InfoWindow , ���� view�� �������꣬ y ��ƫ����
								InfoWindow mInfoWindow = new InfoWindow(contentView, points.get(Integer.parseInt((String) arg0.getExtraInfo().get("info"))), -20);
								// ��ʾInfoWindow
								mBaiduMap.showInfoWindow(mInfoWindow);
								return false;
							}
						};
						mBaiduMap.setOnMarkerClickListener(markClick);
						if (points.size() > 1) {
							// �������ɫ�����߸�����
							OverlayOptions ooPolyline = new PolylineOptions().width(5).color(Color.parseColor("#f13333")).points(points).zIndex(1);
							mBaiduMap.addOverlay(ooPolyline);
						}
					} else {
						ToastUtil.showCenterShort(context, "���޹켣��Ϣ��");
					}
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// �رս�����
					}
				}
				// ����
				if (msg.arg1 == update) {
					final UpdateModel um = JSON.parseObject(msg.obj.toString(),UpdateModel.class);
					if (um != null) {
						// �������Ϣ�ݴ��ڱ����ļ���
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
						// �Ƿ���Ҫ���£�true�ǣ�false��
						if (um.getVersion() != null) {
							boolean isUpdate = (info.versionName).compareTo(um.getVersion()) < 0 ? true : false;
							if (!isUpdate) {
								return;
							} else {
								AlertDialog.Builder customDia = new AlertDialog.Builder(HomePageActivity.this);
								customDia.setCancelable(false);
								final View viewDia = LayoutInflater.from(HomePageActivity.this).inflate(R.layout.alertdialog_update, null);
								TextView update_con = (TextView) viewDia.findViewById(R.id.update_con);
								if (um.getContent() != null&& !um.getContent().equals("")) {//�ֺŲ��
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
								customDia.setPositiveButton("���ڸ���",new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,int which) {
												Intent it = new Intent(HomePageActivity.this,NotificationUpdateActivity.class);
												if (um.getIsForceUpdate() != null&& um.getIsForceUpdate().equals("0")) {
													it.putExtra("isforce", "0");// ��Ҫǿ�Ƹ���
												}
												startActivity(it);
												app.setDownload(true);
												dialog.dismiss();
											}
										});
								// ��Ҫǿ�Ƹ���
								if (um.getIsForceUpdate() != null&& um.getIsForceUpdate().equals("0")) {
									customDia.setNegativeButton("�رճ���",new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog,int which) {
															exit();
															dialog.dismiss();
														}
													});
								} else {
									customDia.setNegativeButton("�Ժ���˵",new DialogInterface.OnClickListener() {
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
				// �Զ���¼
				if (msg.arg1 == login) {
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();// �رս�����
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
						if (MyApplication.userModel.getMsgID() > msgID) {// ����û�ж�����Ϣ
							msgPoint.setVisibility(View.VISIBLE);
						} else {
							msgPoint.setVisibility(View.GONE);
						}
						break;
					case 2:
						ToastUtil.showCenterShort(context, "�û������������");
						SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
						Editor editor = preferences.edit();
						editor.putString("pwd", "");
						break;
					case 3:
						ToastUtil.showCenterShort(context,"�������쳣,�����µ�¼��");
						break;
					case 4:
						ToastUtil.showCenterShort(context,"����δ��֤��������֤���䣡");
						break;
					default:
						break;
					}

				}
				//����
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
						
						if (systemInfo.getBrief()!= null&& !systemInfo.getBrief().equals("")) {//�ֺŲ��
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
							zhidao.setText("֪����");
							zhidao.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									LinkManPopuWindow.dismmis();
								}
							});
						}else{
							zhidao.setText("�鿴����");
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
				// ��������Ϊnull
				if (msg.arg1 == 1) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}
				if (msg.arg1 == RequestCode.ADDLINKMAN) {//�����б�
					// ��json��ʽ�Ľ����ɼ���
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
				//�켣 
				if(msg.arg1 == RequestCode.HISTOTYQUERY){
					ToastUtil.showCenterShort(context, "���޹켣��Ϣ��");
				}
				break;
			case HttpUtil.FAILURE:
				if (msg.arg1 != update || msg.arg1 != login|| msg.arg1 != NIGHTEEN) {
				}
				break;
			case HttpUtil.LOADING:
				break;
			case 5:// ׷��
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
				ToastUtil.showCenterShort(context, "׷����ɣ�");
				mapStop.setVisibility(View.GONE);
				break;
			case 11:
				ToastUtil.showCenterShort(context,RequestCode.NONETWORK);
				break;
			default:
				break;
		}
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// �رս�����
			}
		}
	};

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
	 * ��������ѡ�����
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
	 * ��ʼʱ��ѡ�����
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
	 * ����ʱ��ѡ�����
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
	 * ��ʼʱ��ѡ��ȷ�����������`
	 */
	private void updateTimeDisplay(int... a) {
		StringBuilder etime = new StringBuilder("");
		// ��һ�ε㿪��ʱ��
		if (a.length > 0) {
			if (mHour1 < 10) {
				startTime.setText(etime.append("0" + mHour1).append(":").append((mMinute1 < 10) ? "0" + mMinute1 : mMinute1));
			} else {
				startTime.setText(etime.append(mHour1).append(":").append((mMinute1 < 10) ? "0" + mMinute1 : mMinute1));
			}
			return;
		}
		// �ж�����ʱ���Ƿ��������ʱ��
		if (isLargeTime((mHour < 10 ? "0" + mHour : mHour) + ":"+ ((mMinute < 10) ? "0" + mMinute : mMinute))) {
			if (mHour < 10) {
				startTime.setText(etime.append("0" + mHour).append(":").append((mMinute < 10) ? "0" + mMinute : mMinute));
			} else {
				startTime.setText(etime.append(mHour).append(":").append((mMinute < 10) ? "0" + mMinute : mMinute));
			}
		} else {
			ToastUtil.showCenterShort(context, "��ʼʱ�������ڵ�������ʱ�䣡");
			if (mHour1 < 10) {
				startTime.setText(etime.append("0" + mHour1).append(":").append((mMinute1 < 10) ? "0" + mMinute1 : mMinute1));
			} else {
				startTime.setText(etime.append(mHour1).append(":").append((mMinute1 < 10) ? "0" + mMinute1 : mMinute1));
			}
		}
	}

	/**
	 * ����ʱ��ѡ��ȷ�����������
	 */
	private void updateTimeDisplay1(int... a) {
		StringBuilder etime = new StringBuilder("");
		// ��һ�ε㿪��ʱ��
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
	 * ��ʼ����ѡ��ȷ�����������
	 */
	private void updateDateDisplay() {
		startDate.setText(new StringBuilder().append(mYear).append("-").append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-").append((mDay < 10) ? "0" + mDay : mDay));
	}

	/**
	 * ��������ѡ��ȷ�����������
	 */
	private void updateDateDisplay1() {
		endDate.setText(new StringBuilder().append(mYear1).append("-").append((mMonth1 + 1) < 10 ? "0" + (mMonth1 + 1): (mMonth1 + 1)).append("-").append((mDay1 < 10) ? "0" + mDay1 : mDay1));
	}

	/**
	 * ����ʱ���dialog
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
	 * �������ſؼ�
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
	 * ���������
	 * @param Latitude
	 * @param Longitude
	 * @param adress
	 * @param type
	 */
	public void setpoint(LatLng point, String adress, String time, int type) {
		TextView button = setpop(adress);
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.index_location);	// ����Markerͼ��
		OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).zIndex(1);// ����MarkerOption�������ڵ�ͼ�����Marker
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
		final InfoWindow mInfoWindow = new InfoWindow(contentView, point, -80);// ����InfoWindow , ���� view�� �������꣬ y ��ƫ����
		if (type == 0) {
			mBaiduMap.clear();
		}
		mBaiduMap.addOverlay(option);// �ڵ�ͼ�����Marker������ʾ
		mBaiduMap.showInfoWindow(mInfoWindow);// ��ʾInfoWindow
		markClick = new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker arg0) {
				mBaiduMap.showInfoWindow(mInfoWindow);
				return false;
			}
		};
		mBaiduMap.setOnMarkerClickListener(markClick);
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);		// ������ʾ
		CircleOptions circle = new CircleOptions();
		circle.center(point);
		circle.radius(500);
		circle.fillColor(Color.parseColor("#3c1cace9"));
		mBaiduMap.addOverlay(circle);
		updatestatus(point, 15);// �����ͼ״̬
		mapClear.setVisibility(View.VISIBLE);
	}

	/**
	 * update��ͼ��״̬��仯
	 * @param point
	 * @param zoom
	 */
	private void updatestatus(LatLng point, int zoom) {
		MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(zoom).build();
		MapStatusUpdate mMapStatusUpdate =MapStatusUpdateFactory.newMapStatus(mMapStatus);// ����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯
		mBaiduMap.setMapStatus(mMapStatusUpdate);	// �ı��ͼ״̬
		islocation = true;
	}

	/**
	 * �Զ����ͼ�Ի���չʾ����
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
	 * ���ö�λsdk�Ĳ���
	 */
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("gcj02");// ���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		option.setOpenGps(true);// ��gps
		option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
		// option.disableCache(false);// ��ֹ���û��涨λ
		// option.setPriority(LocationClientOption.GpsFirst);
		option.setScanSpan(1000 * 60 * 60 * 24);
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	/**
	 * �����б�
	 * @param a      �в�����ʾ�б�û��������ʾ
	 */
	private void showFriendList(int... a) {
		if (preferences.getBoolean("firstfriend", false)) {
			ToastUtil.showCenterShort(context, "��λʱ����������ã������ʹ�ã�");
			Editor editor = preferences.edit();
			editor.putBoolean("firstfriend", false);
			editor.commit();
		}
		setListviewonclick(popupwinow_listview);
		FriendListviewAdapter adapter = new FriendListviewAdapter(this);
		popupwinow_listview.setAdapter(adapter);
		if (a.length > 0) {
		} else {
			View view=EmptyView.getView(context, "���޺���");
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
	 * ������ҳ�������
	 */
	@Override
	public void onClick(View v) {
		if (v == xiaoxi) {
			if (MyApplication.isLogin < 0) {
				ToastUtil.showCenterShort(context, "���ȵ�¼��");
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
				normalDia.setTitle("��ʾ");
				normalDia.setMessage("����ʹ��׷�٣��Ƿ�Ҫ�ر�ʹ�ã�");
				normalDia.setPositiveButton("ȷ��",
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
				normalDia.setNegativeButton("ȡ��",
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
			normalDia.setTitle("��ʾ");
			normalDia.setMessage("�Ƿ�Ҫֹͣ׷�٣�");
			normalDia.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mapStop.setVisibility(View.GONE);
							timer5.cancel();
							iszhuizong = false;
							mBaiduMap.clear();
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
		// �ҵ�
		if (v.getId() == R.id.index_caidan) {
			if (MyApplication.isLogin < 0) {
				ToastUtil.showCenterShort(context, "���ȵ�¼��");
				Intent UserActivityIntent = new Intent(this,LoginActivity.class);
				startActivity(UserActivityIntent);
				finish();
			} else {
				if (iszhuizong || isWeilan) {
					AlertDialog.Builder normalDia = new AlertDialog.Builder(HomePageActivity.this);
					normalDia.setTitle("��ʾ");
					normalDia.setMessage("����ʹ��׷�٣��Ƿ�Ҫ�ر�ʹ�ã�");
					normalDia.setPositiveButton("ȷ��",
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
					normalDia.setNegativeButton("ȡ��",
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
					// ����ͳ��
					MobclickAgent.onEvent(this, "mine");
					Intent UserActivityIntent = new Intent(this,UserActivity.class);
					startActivity(UserActivityIntent);
				}
			}
		}
		// ����
		if (v == image_linkman) {
			if (MyApplication.isLogin < 0) {
				ToastUtil.showCenterShort(context, "���ȵ�¼��");
				Intent UserActivityIntent = new Intent(this,
						LoginActivity.class);
				startActivity(UserActivityIntent);
				finish();
			} else {
				if (MyApplication.getNetObject().isNetConnected()) {
					if (progressDialog != null && !progressDialog.isShowing()) {
						progressDialog = MyProgressDialog.createDialog(context);
						progressDialog.setMessage("���ڻ�ȡ�����б�...");
						progressDialog.show();
					}
					http.sendGet(RequestCode.ADDLINKMAN,WebUrlConfig.getFriendList(MyApplication.userModel
							.getUserID()));
				} else {
					ToastUtil.showCenterShort(this, "�����޷����ӣ�");
				}
			}
		}
		// ��λ
		if (v == dingwei) {
			if (MyApplication.isLogin < 0) {
				ToastUtil.showCenterShort(context, "���ȵ�¼��");
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
							"�������Ϸ�����ɫͼ��,ѡ����Ӷ�λ����");
					return;
				}
				if (isdingwei) {
					ToastUtil.showCenterShort(context, "���ζ�λ��������1���ӣ�");
					return;
				}
				if (MyApplication.getNetObject().isNetConnected()) {
					if (progressDialog != null && !progressDialog.isShowing()) {
						progressDialog = MyProgressDialog.createDialog(context);
						progressDialog.setMessage("���ڶ�λ...");
						progressDialog.show();
					}
					String url = WebUrlConfig.getFriendPosition(userFriend.getFriendID(),userFriend.getFriendPhone(),userFriend.getNumberType(),userFriend.getPackageID());
					http.sendGet(RequestCode.FRIEND_POSITION,url);
					// ����ͳ�ƣ���λ
					MobclickAgent.onEvent(context, "location_now");
				} else {
					ToastUtil.showCenterShort(context, "�����޷����ӣ�");
				}
			}
		}
		// ׷��
		if (v == zhuizong) {
			if (MyApplication.isLogin < 0) {
				ToastUtil.showCenterShort(context, "���ȵ�¼��");
				Intent UserActivityIntent = new Intent(this,
						LoginActivity.class);
				startActivity(UserActivityIntent);
				finish();
			} else {
				if (!ischeck) {
					ToastUtil.showCenterShort(context,
							"�������Ϸ����б�ѡ�����ӱ���λ���룡");
					return;
				}
				if (iszhuizong) {
					ToastUtil.showCenterShort(context,
							"�Ѿ���׷���ˣ�����ֹͣ��ǰ׷�٣�");
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
						new AlertDialog.Builder(context).setTitle("���ʱ��")
								.setView(outerView)
								.setPositiveButton("ȷ��", null).show();
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
						new AlertDialog.Builder(context).setTitle("׷��ʱ��")
								.setView(outerView)
								.setPositiveButton("ȷ��", null).show();
					}
				});
				zhuizongBtn = (TextView) contentView.findViewById(R.id.zhuizong);
				LinkManPopuWindow.showpopuwindow(this, v, contentView, 0, 0, 2);
				zhuizongBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (jiangeshi >= endjiangeshi) {
							ToastUtil.showCenterShort(context,"׷��ʱ�������ڼ��ʱ�䣡");
							return;
						}
						if (!userFriend.getNumberType().equals("1")) {
							AlertDialog.Builder normalDia = new AlertDialog.Builder(HomePageActivity.this);
							normalDia.setTitle("��ʾ");
							normalDia.setMessage("�ú���Ϊ�������Ʒ��û���׷���ڼ佫�����۷ѣ���ȷ���Ƿ�׷�٣�");
							normalDia.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,int which) {
											ToastUtil.showCenterShort(context, "��ʼ׷�٣�");
											iszhuizong = true;
											LinkManPopuWindow.dismmis();
											handler.sendEmptyMessage(5);
											// ����ͳ�ƣ�׷��
											MobclickAgent.onEvent(context,"location_trace");
										}
									});
							normalDia.setNegativeButton("ȡ��",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,int which) {
											dialog.dismiss();
										}
									});
							normalDia.create().show();
						} else {
							ToastUtil.showCenterShort(context, "��ʼ׷�٣�");
							iszhuizong = true;
							LinkManPopuWindow.dismmis();
							handler.sendEmptyMessage(5);
							// ����ͳ�ƣ�׷��
							MobclickAgent.onEvent(context, "location_trace");
						}
					}
				});
			}
		}
		// ��λ�Լ�
		if (v == mydingwei) {
			mLocationClient.requestLocation();
			if (lat != 0) {
				marker.remove();
				LatLng zuobiao = changebaidu(new LatLng(lat, lng));
				BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.dingweimy);
				OverlayOptions option = new MarkerOptions().position(zuobiao).icon(bitmap).zIndex(2);// ����MarkerOption�������ڵ�ͼ�����Marker
				marker = (Marker) mBaiduMap.addOverlay(option);
				updatestatus(zuobiao, 15);
			}
		}
	}
	/**
	 * listview�������
	 * @param listView
	 */
	private void setListviewonclick(ListView listView) {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if (iszhuizong) {
					ToastUtil.showCenterShort(context,"����׷�٣���Ҫ���Ķ�λ��������ֹͣ׷�٣�");
					return;
				}
				if (userFriend != null) {
					if (MyApplication.lOpenUserFriend.get(position).getFriendID().equals(userFriend.getFriendID())) {
						ToastUtil.showCenterShort(context,"ͬһ��λ������ȴ�1���Ӻ���ܼ�����λ��");
						return;
					}
				}
				if (MyApplication.getNetObject().isNetConnected()) {
					if (progressDialog != null && !progressDialog.isShowing()) {
						progressDialog = MyProgressDialog.createDialog(context);
						progressDialog.setMessage("���ڶ�λ...");
						progressDialog.show();
					}
					String url = WebUrlConfig.getFriendPosition(MyApplication.lOpenUserFriend.get(position).getFriendID(),MyApplication.lOpenUserFriend.get(position).getFriendPhone(),MyApplication.lOpenUserFriend.get(position).getNumberType(),MyApplication.lOpenUserFriend.get(position).getPackageID());
					http.sendGet(RequestCode.FRIEND_POSITION,url);
					// ����ͳ�ƣ���λ
					MobclickAgent.onEvent(context, "location_now");
				} else {
					ToastUtil.showCenterShort(context, "�����޷����ӣ�");
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
	 * ��λ������
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
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS��λ���
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// ��λ������ÿСʱ
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// ��λ����
				sb.append("\ndirection : ");
				sb.append(location.getDirection());// ��λ��
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps��λ�ɹ�");
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ���綨λ���
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// ��Ӫ����Ϣ
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("���綨λ�ɹ�");
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
				sb.append("\ndescribe : ");
				sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
			}
			// ���ǵ�һ�ν���Ĭ�ϵĵ�ͼ
			// ��λ��ǰλ��֮����ʾ�ڵ�ͼ��λ��
			LatLng point = new LatLng(location.getLatitude(),location.getLongitude());
			city = location.getCity();
			if (MyApplication.isupdate) {
				update();
			}
			if (islocation) {
			} else {
				BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.dingweimy);
				// ����MarkerOption�������ڵ�ͼ�����Marker
				OverlayOptions option = new MarkerOptions().position(changebaidu(point)).icon(bitmap).zIndex(2);
				marker = (Marker) mBaiduMap.addOverlay(option);
				updatestatus(changebaidu(point), 15);
			}
		}
	}

	/**
	 * ���ؼ���2�����ٴε���˳�Ӧ��
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
				mDrawerLayout.closeDrawers();
				return true;
			}
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����!",Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �˳�Ӧ�ó���ť����
	 */
	@SuppressWarnings("deprecation")
	public  void exit() {
		// ���ó�����ȫ�˳�
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
	 * GPSת�ٶ�����
	 * @param ll
	 * @return
	 */
	private LatLng changebaidu(LatLng ll) {
		// ��google��ͼ��soso��ͼ��aliyun��ͼ��mapabc��ͼ��amap��ͼ// ��������ת���ɰٶ�����
		CoordinateConverter converter = new CoordinateConverter();
		converter.from(CoordType.COMMON);
		converter.coord(ll);
		LatLng desLatLng = converter.convert();
		return desLatLng;
	}

	/**
	 * �ж�����ʱ���Ƿ������ʱ���
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
	 * ���л��켣���¼�
	 */
	private void toguiji() {
		LinkManPopuWindow.dismmis();
		if (MyApplication.isLogin < 0) {
			ToastUtil.showCenterShort(context, "���ȵ�¼��");
			Intent UserActivityIntent = new Intent(this, LoginActivity.class);
			startActivity(UserActivityIntent);
			finish();
		} else {
			if (!ischeck) {
				ToastUtil.showCenterShort(context,
						"�������Ϸ����б�ѡ�����ӱ���λ���룡");
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
						ToastUtil.showCenterShort(context, "��ʼ���ڲ���Ϊ�գ�");
						return;
					}
					if (endDate.getText().toString().equals("")|| endDate.getText().toString() == null) {
						ToastUtil.showCenterShort(context, "�������ڲ���Ϊ�գ�");
						return;
					}
					SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date d1 = f.parse(startDate.getText().toString());
						Date d2 = f.parse(endDate.getText().toString());
						if (d2.getTime() < d1.getTime()) {
							ToastUtil.showCenterShort(context,"�������ڲ���С�ڿ�ʼ���ڣ�");
							return;
						}
						long day = (d2.getTime() - d1.getTime()) / 1000 / 60/ 60 / 24;
						if (day > 30) {
							ToastUtil.showCenterShort(context,"�켣��ѯʱ�䲻�ܴ���30�죡");
							return;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (MyApplication.getNetObject().isNetConnected()) {
						if (progressDialog != null
								&& !progressDialog.isShowing()) {
							progressDialog = MyProgressDialog.createDialog(context);
							progressDialog.setMessage("���ڲ�ѯ�켣...");
							progressDialog.show();
						}
						count++;
						String url = WebUrlConfig.getMovingStrack(userFriend.getFriendID(),startDate.getText().toString()+ " 00:00:01", endDate.getText().toString()+ " 23:59:59", userFriend.getFriendPhone(), userFriend.getFriendType(), userFriend.getNumberType(), userFriend.getIsAgree());
						LinkManPopuWindow.dismmis();
						http.sendGet(RequestCode.HISTOTYQUERY,url);
						// ����ͳ�ƣ��켣
						MobclickAgent.onEvent(context, "location_history");
					} else {
						ToastUtil.showCenterShort(context, "�����޷����ӣ�");
					}
				}
			});
		}
	}

	/**
	 * ���л�׷�ٵ��¼�
	 */
	private void tozhuizong() {
		LinkManPopuWindow.dismmis();
		if (MyApplication.isLogin < 0) {
			ToastUtil.showCenterShort(context, "���ȵ�¼��");
			Intent UserActivityIntent = new Intent(this, LoginActivity.class);
			startActivity(UserActivityIntent);
			finish();
		} else {
			if (!ischeck) {
				ToastUtil.showCenterShort(context,
						"�������Ϸ����б�ѡ�����ӱ���λ���룡");
				return;
			}
			if (iszhuizong) {
				ToastUtil.showCenterShort(context, "�Ѿ���׷���ˣ�����ֹͣ��ǰ׷�٣�");
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
					new AlertDialog.Builder(context).setTitle("���ʱ��").setView(outerView).setPositiveButton("ȷ��", null).show();
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
					new AlertDialog.Builder(context).setTitle("׷��ʱ��").setView(outerView).setPositiveButton("ȷ��", null).show();
				}
			});
			zhuizongBtn = (TextView) contentView.findViewById(R.id.zhuizong);
			LinkManPopuWindow.showpopuwindow(this, thisview, contentView, 0, 0, 2);
			zhuizongBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (jiangeshi >= endjiangeshi) {
						ToastUtil.showCenterShort(context,"׷��ʱ�������ڼ��ʱ�䣡");
						return;
					}
					if (!userFriend.getNumberType().equals("1")) {
						AlertDialog.Builder normalDia = new AlertDialog.Builder(HomePageActivity.this);
						normalDia.setTitle("��ʾ");
						normalDia.setMessage("�ú���Ϊ�������Ʒ��û���׷���ڼ佫�����۷ѣ���ȷ���Ƿ�׷�٣�");
						normalDia.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
										ToastUtil.showCenterShort(context, "��ʼ׷�٣�");
										iszhuizong = true;
										LinkManPopuWindow.dismmis();
										handler.sendEmptyMessage(5);
										// ����ͳ�ƣ�׷��
										MobclickAgent.onEvent(context,"location_trace");
									}
								});
						normalDia.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
										dialog.dismiss();
									}
								});
						normalDia.create().show();
					} else {
						ToastUtil.showCenterShort(context, "��ʼ׷�٣�");
						iszhuizong = true;
						LinkManPopuWindow.dismmis();
						handler.sendEmptyMessage(5);
						// ����ͳ�ƣ�׷��
						MobclickAgent.onEvent(context, "location_trace");
					}
				}
			});
		}
	}
}
