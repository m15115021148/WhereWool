package com.sitemap.na2ne.application;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.BuildConfig;
import org.xutils.x;

import com.baidu.mapapi.SDKInitializer;
import com.sitemap.na2ne.models.LocationSetModel;
import com.sitemap.na2ne.models.MoneyModel;
import com.sitemap.na2ne.models.PackageModel;
import com.sitemap.na2ne.models.UserFriendModel;
import com.sitemap.na2ne.models.UserModel;
import com.sitemap.na2ne.utils.NetworkState;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * 
 * @ClassName: MyApplication.java
 * @Description: 应用程序application类，配置一些全局变量或对象；
 * @author chenhao
 * @Date 2015-11-14
 */
public class MyApplication extends Application {
	private static MyApplication _instance;
	private static NetworkState netState;
	public static UserModel userModel;// 用户资料
	public static MoneyModel moneyModel;// 用户资产
	public static List<UserFriendModel> luserFriend = new ArrayList<UserFriendModel>();// 好友列表
	public static List<UserFriendModel> lOpenUserFriend = new ArrayList<UserFriendModel>();// 开通好友列表
	/** 是否登录*/ 
	public static int isLogin = -1;
	public static int fayzm = 60;// 验证码倒计时
	public static String shouji = "";// 注册用手机号
	public static boolean isupdate = true;// 是否请求更新
	private boolean isDownload;// 是否在下载
	public static String downUrl = "";// 下载地址
	public static String versionName;// 版本号
	public static double length;// apk大小
	public static LocationSetModel locationSet = new LocationSetModel();// 位置上报设置实体类
	public static Intent intent;// 服务意图
	public static double lat;// 纬度
	public static double lng;// 经度
	public static String city = "";// 所在城市
	public static List<PackageModel> lPackage = new ArrayList<PackageModel>();// 所有套餐	
	public static boolean isselect = false;// 是否选了好友
	public static String notiID = "0";//公告ID
	public static int width=0;//宽
	public static int height=0;//高

	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// //初始化极光推送
		// JPushInterface.setDebugMode(true);
		// JPushInterface.init(this);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		_instance = this;
		netState = new NetworkState(this.getApplicationContext());
		isDownload = false;
		initUmeng();
		initXutils();
	}
	
	 /**
     * 初始化xutils框架
     */
    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

	/**
	 * 初始化友盟统计
	 */
	private void initUmeng() {
		/** 设置是否对日志信息进行加密, 默认false(不加密). */
		AnalyticsConfig.enableEncrypt(true);
		MobclickAgent.setDebugMode(false);// 调试模式；默认关闭
	}

	public static MyApplication instance() {
		if (_instance != null) {
			return _instance;
		} else {
			return new MyApplication();
		}
	}

	/**
	 * 获取手机网络状态对象
	 * 
	 * @return
	 */
	public static NetworkState getNetObject() {
		if (netState != null) {
			return netState;
		} else {
			return new NetworkState(instance().getApplicationContext());
		}
	}

	/**
	 * 验证电信手机号
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isCTCCNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((133)|(153)|(177)|(173)|(18[0-1])|(189))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 验证移动手机号
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isCMCCNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((15[0-2])|(15[7-9])|(18[2-4])|(13[4-9])|(187)|(188)|(178)|(147))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}

	/**
	 * 验证联通手机号
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isCUCCNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-2])|(155)|(156)|(185)|(186)|(145)|(176))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean isPWD(String pwd) {
		Pattern p = Pattern.compile("^[A-Za-z0-9|_]{6,16}$");
		Matcher m = p.matcher(pwd);
		return m.matches();
	}

	/**
	 * 设置段落中个别字体的颜色
	 * @param tv         textview
	 * @param txt        段落
	 * @param lable      设置字体的颜色
	 */
	public static void setTextColor(TextView tv, String txt, String lable) {
		SpannableStringBuilder style = new SpannableStringBuilder(txt);
		String[] split = lable.split(";");
		if (split.length > 0) {
			for (int i = 0; i < split.length; i++) {
				if(txt.contains(split[i])){
					int start = txt.indexOf(split[i]);
					int end = start + split[i].length();
					ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#1cace9"));
					style.setSpan(span, start, end,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
					tv.setText(style);
				}else{
					tv.setText(style);
				}	
			}
		} else {
			tv.setText(style);
		}
	}
}
