package com.sitemap.na2ne.config;

import com.sitemap.na2ne.application.MyApplication;

/**
 * 
 * @ClassName:     WebUrlConfig.java
 * @Description:   网络url（接口）配置文件
 * @author         chenhao
 * @Date           2015-11-14
 */
 
public class WebUrlConfig {
	private static final String HOST_NAME = WebHostConfig.getHostName();
	private static final String LOGIN = HOST_NAME + "userAction_login?";//登录
	private static final String GETFRIENDLIST=HOST_NAME+"linkmanAction_getFriendtable?";//获得好友列表
	private static final String GETFRIENDPOSITION=HOST_NAME+"locationAction_getFriendPosition?";//定位
	private static final String GETMOVINGTRACK=HOST_NAME+"trackAction_exerciseSTrack?";//历史轨迹
	private static final String UPDATENAME=HOST_NAME+"userAction_updateName?";//更改名称
	private static final String REQUESTCODE=HOST_NAME+"userAction_requestCode?";//请求验证码
	private static final String REGISTER=HOST_NAME+"userAction_register?";//注册
	private static final String SYSTEMNOTIFY=HOST_NAME+"userAction_systemNotify?";//系统公告
	private static final String REQUESTREFUND=HOST_NAME+"userAction_requestRefund?";//申请退款
	private static final String REFUNDRECORDS=HOST_NAME+"userAction_refundRecords?";//退款记录
	private static final String PAYMENTRECORDS=HOST_NAME+"userAction_paymentRecords?";//充值记录
	private static final String UPDATEVERSION=HOST_NAME+"userAction_updateVersion?";//更新接口
	private static final String SHOPRECORD=HOST_NAME+"userAction_shopRecord?";//计费账单
	private static final String LOCATIONRECORD=HOST_NAME+"userAction_locationRecord?";//定位记录
	private static final String REQUESTMONEY=HOST_NAME+"userAction_requestMoney?";//请求用户个人账户余额
	private static final String ADDFRIEND=HOST_NAME+"userAction_addFriend?";//添加好友
	private static final String UPDATEFRIENDTYPE=HOST_NAME+"userAction_updateFriendType?";//更改好友套餐
	private static final String FIXPASSWORD=HOST_NAME+"userAction_fixPassword?";//找回密码
	private static final String GETCODES=HOST_NAME+"userAction_getCodes?";//找回密码请求验证码
	private static final String COMPARECODE=HOST_NAME+"userAction_compareCode?";//对比验证码
	private static final String UPDATEPASSWORD=HOST_NAME+"userAction_updatePassword?";//修改密码
	private static final String SYSTEMCONFIG=HOST_NAME+"userAction_systemConfig";//位置上报设置
	private static final String UPLOADLOCATION=HOST_NAME+"userAction_uploadLocation";//位置上报
	private static final String GETPACKAGEINFO=HOST_NAME+"userAction_getPackageInfo?";//获得套餐列表
	private static final String ADDLOCATIONTIMES=HOST_NAME+"userAction_addLocationTimes?";//增加定位次数
	private static final String SAVEFENCE=HOST_NAME+"userAction_saveFence";//发送电子围栏
	private static final String SETFENCEEND=HOST_NAME+"userAction_setFenceEnd?";//关闭电子围栏
	private static final String GETFENCE=HOST_NAME+"userAction_getFence?";//获得历史电子围栏
	private static final String SETFENCESTART=HOST_NAME+"userAction_setFenceStart";//设置推送电子围栏
	private static final String SENDWARN=HOST_NAME+"userAction_sendWarn?";//电子围栏报警推送
	private static final String GETFRIENDINFO=HOST_NAME+"userAction_getFriendInfo?";//获得好友信息
	private static final String DELETEFRIEND=HOST_NAME+"userAction_deleteFriend?";//删除好友
	private static final String GETFUNCTIONINFO=HOST_NAME+"userAction_getFunctionInfo?";//APP功能介绍
	private static final String GETAPPINFO=HOST_NAME+"userAction_getAppInfo?";//APP版本介绍
	private static final String TOADVICE=HOST_NAME+"userAction_toAdvice";//用户反馈(问题和意见)
	private static final String SHAREINFO=HOST_NAME+"userAction_getShareInfo";//分享
	private static final String DELETEHISTORY=HOST_NAME+"userAction_deleteHistory?";//删除定位记录
	private static final String REOPEN=HOST_NAME+"userAction_reopen?";//重新开通(好友未同意时)
	private static final String GETMSG = HOST_NAME+"userAction_getMsg?";//我的消息
	private static final String GETTIPMSG = HOST_NAME+"userAction_getTipMsg?";//添加好友时，文字提示
	/**
	 * 登陆操作
	 * 
	 * @param userID
	 * @param password
	 * @return
	 */
	public static String login(String userID, String password) {
		return LOGIN + "username=" + userID + "&password=" + password+ "&source=1";
	}
	
	/**
	 * 获得好友列表
	 * 
	 * @param userID
	 * @return
	 */
	public static String getFriendList(String userID) {
		return GETFRIENDLIST + "userID=" + userID;
	}
	
	/**
	 * 获得好友位置
	 * 
	 * @param friendID
	 * @param friendPhone
	 * @param numberType
	 * @param packageID 
	 * @return
	 */
	public static String getFriendPosition(String friendID,String friendPhone,String numberType,String packageID) {
		return GETFRIENDPOSITION + "friendID=" + friendID+"&friendPhone="+friendPhone+"&userID="+MyApplication.userModel.getUserID()+"&numberType="+numberType+"&packageID="+packageID;
	}
	
	/**
	 * 运动轨迹
	 * 
	 * @param friendID
	 * @param startTime
	 * @param endTime
	 * @param friendPhone
	 * @param friendType
	 * @param numberType
	 * @param isAgree
	 * @return
	 */
	public static String getMovingStrack(String friendID,String startTime,String endTime,String friendPhone,String friendType,String numberType,String isAgree) {
		return GETMOVINGTRACK + "friendID=" + friendID+"&startTime="+startTime+"&endTime="+endTime+"&friendPhone="+friendPhone+"&friendType="+friendType+"&numberType="+numberType+"&isAgree="+isAgree;
	}
	
	
	/**
	 * 更改好友姓名
	 * 
	 * @param friendID
	 * @param friendName
	 * @return
	 */
	public static String updateName(String friendID , String friendName) {
		return UPDATENAME + "friendID=" + friendID  + "&friendName=" + friendName+"&type=1";
	}
	
	/**
	 * 请求验证码
	 * @param phoneNumber
	 * @return
	 */
	public static String requestCode(String phoneNumber) {
		return REQUESTCODE + "phoneNumber=" + phoneNumber;
	}
	
	/**
	 * 注册
	 * @param phoneNumber
	 * @param username
	 * @param password
	 * @param code
	 * @param numberType
	 * @param phoneType
	 * @return
	 */
	public static String register(String phoneNumber,String username,String password,String code,String numberType,String phoneType) {
		return REGISTER + "phoneNumber=" + phoneNumber+"&username="+username+"&password="+password+"&code="+code+"&source=1"+"&numberType="+numberType+"&phoneType="+phoneType;
	}
	
	/**
	 * 系统公告
	 * @return
	 */
	public static String systemNotify(String notiID) {
		return SYSTEMNOTIFY+ "platform=0"+"&versionCode="+MyApplication.versionName+"&notiID="+notiID;
	}
	
	/**
	 * 申请退款
	 * @param userID
	 * @param reason
	 * @param description
	 * @return
	 */
	public static String requestRefund(String userID,String reason,String description) {
		return REQUESTREFUND + "userID=" + userID+"&reason="+reason+"&description="+description+"&platform=1"+"&versionCode="+MyApplication.versionName;
	}
	
	/**
	 * 退款记录
	 * @param userID
	 * @param page
	 * @param date
	 * @return
	 */
	public static String refundRecords(String userID,String page,String date) {
		return REFUNDRECORDS + "userID=" + userID+"&page="+page+"&date="+date;
	}
	
	/**
	 * 充值记录
	 * @param userID
	 * @param page
	 * @param date
	 * @return
	 */
	public static String paymentRecords(String userID,String page,String date) {
		return PAYMENTRECORDS + "userID=" + userID+"&page="+page+"&date="+date;
	}
	
	/**
	 * 更新接口
	 * @param platform
	 * @param city
	 * @return
	 */
	public static String updateVersion(String platform,String city) {
		return UPDATEVERSION + "platform=" + platform+"&city="+city+"&versionCode="+MyApplication.versionName;
	}
	
	/**
	 * 计费账单
	 * @param userID
	 * @param page
	 * @param date
	 * @return
	 */
	public static String shopRecord(String userID,String page,String date,String phoneNumber) {
		return SHOPRECORD + "userID=" + userID+"&page="+page+"&date="+date+"&phoneNumber="+phoneNumber;
	}
	
	/**
	 * 定位记录
	 * @param userID
	 * @param page
	 * @param date
	 * @param friendID
	 * @return
	 */
	public static String locationRecord(String userID,String page,String friendID,String date) {
		return LOCATIONRECORD + "userID=" + userID+"&page="+page+"&friendID="+friendID+"&date="+date;
	}
	
	/**
	 * 请求用户个人账户余额
	 * 
	 * @param userID
	 * @return
	 */
	public static String requestMoney(String userID) {
		return REQUESTMONEY + "userID=" + userID;
	}
	
	/**
	 * 添加好友
	 * @param userID
	 * @param phoneNumber
	 * @param friendName
	 * @param packageID
	 * @param description
	 * @param numberType
	 * @return
	 */
	public static String addFriend(String userID,String phoneNumber,String friendName,String packageID,String description,String numberType) {
		return ADDFRIEND + "userID=" + userID+"&phoneNumber="+phoneNumber+"&friendName="+friendName+"&packageID="+packageID+"&description="+description+"&numberType="+numberType;
	}
	
	
	/**
	 * 更改好友套餐
	 * @param userID
	 * @param packageID
	 * @param friendID
	 * @param oldPackageID
	 * @return
	 */
	public static String updateFriendType(String userID,String packageID,String friendID,String oldPackageID) {
		return UPDATEFRIENDTYPE + "userID=" + userID+"&packageID="+packageID+"&friendID="+friendID+"&oldPackageID="+oldPackageID;
	}
	
	/**
	 * 找回密码
	 * @param phoneNumber
	 * @param newPassword
	 * @return
	 */
	public static String fixPassword(String phoneNumber,String newPassword) {
		return FIXPASSWORD + "phoneNumber=" +phoneNumber+"&newPassword="+newPassword;
	}
	
	/**
	 * 找回密码请求验证码
	 * @param phoneNumber
	 * @return
	 */
	public static String getCodes(String phoneNumber) {
		return GETCODES + "phoneNumber=" + phoneNumber;
	}
	
	/**
	 * 对比验证码
	 * @param phoneNumber
	 * @param friendID
	 * @return
	 */
	public static String compareCode(String phoneNumber,String code) {
		return COMPARECODE + "phoneNumber=" + phoneNumber+"&code="+code;
	}
	
	/**
	 * 修改密码
	 * @param userID
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public static String updatePassword(String userID,String oldPassword,String newPassword) {
		return UPDATEPASSWORD + "userID=" + userID+"&oldPassword="+oldPassword+"&newPassword="+newPassword;
	}
	
	/**
	 * 位置上报设置
	 * @return
	 */
	public static String systemConfig() {
		return SYSTEMCONFIG;
	}
	
	/**
	 * 位置上报
	 * @param userID
	 * @param locationInfo
	 * @return
	 */
	public static String uploadLocation(String userID,String locationInfo) {
//		return UPLOADLOCATION + "userID=" + userID+"&locationInfo="+locationInfo;
		return UPLOADLOCATION;
	}
	
	
	/**
	 * 获得套餐类型
	 * @param friendPhone
	 * @param numberType
	 * @return
	 */
	public static String getPackageInfo(String friendPhone,String numberType) {
		return GETPACKAGEINFO + "friendPhone=" + friendPhone+"&numberType="+numberType+"&userID="+MyApplication.userModel.getUserID();
	}
	
	
	/**
	 * 增加定位次数
	 * @param userID
	 * @param friendID
	 * @param packageID
	 * @return
	 */
	public static String addLocatonTimes(String userID,String friendID,String packageID) {
		return ADDLOCATIONTIMES + "userID=" + userID+"&friendID="+friendID+"&packageID="+packageID;
	}
	
	/**
	 * 发送电子围栏
	 * @param friendID
	 * @param fenceInfo
	 * @return
	 */
	public static String saveFence(String friendID,String fenceInfo) {
		return SAVEFENCE;
//		return SAVEFENCE + "friendID=" + friendID+"&fenceInfo="+fenceInfo;
	}
	
	/**
	 * 关闭电子围栏
	 * @param phoneNumber
	 * @return
	 */
	public static String setFenceEnd(String phoneNumber) {
		return SETFENCEEND + "phoneNumber=" + phoneNumber;
	}
	
	/**
	 * 获得历史电子围栏
	 * @param friendID
	 * @return
	 */
	public static String getFence(String friendID) {
		return GETFENCE + "friendID=" + friendID;
	}
	
	
	/**
	 * 推送电子围栏
	 * @param userID
	 * @param phoneNumber
	 * @param fenceInfo
	 * @param friendID
	 * @return
	 */
	public static String setFenceStart(String userID,String phoneNumber,String fenceInfo,String friendID) {
		return SETFENCESTART ;
	}
	
	/**
	 * 电子围栏报警推送
	 * @param senderUserID
	 * @param warnTime
	 * @return
	 */
	public static String sendWarn(String senderUserID,String warnTime) {
		return SENDWARN + "senderUserID=" + senderUserID+"&warnTime="+warnTime;
	}
	
	
	/**
	 * 获得好友套餐信息
	 * 
	 * @param friendID
	 * @return
	 */
	public static String getFriendInfo(String friendID) {
		return GETFRIENDINFO + "friendID=" + friendID;
	}
	
	
	/**
	 * 删除好友
	 * @param userID
	 * @param friendID
	 * @return
	 */
	public static String deleteFriend(String userID,String friendID) {
		return DELETEFRIEND + "userID=" + userID+"&friendID="+friendID;
	}
	
	/**
	 * APP功能介绍
	 * @param versionCode
	 * @return
	 */
	public static String getFunctionInfo(String versionCode) {
		return GETFUNCTIONINFO + "platform=1"+"&versionCode="+versionCode;
	}
	
	/**
	 * APP功能介绍
	 * @param versionCode
	 * @return
	 */
	public static String getAppInfo(String versionCode) {
		return GETAPPINFO + "platform=1"+"&versionCode="+versionCode;
	}
	
	
	/**
	 * 用户反馈(问题和意见)
	 * @return
	 */
	public static String toAdvice() {
		return TOADVICE ;
	}
	
	
	/**
	 * 获取分享信息
	 * @return
	 */
	public static String getShareinfo() {
		return SHAREINFO;
	}
	
	/**
	 * 删除定位记录
	 * @param userID
	 * @param phoneNumber
	 * @param historyID
	 * @return
	 */
	public static String deleteHistory(String phoneNumber,String historyID) {
		return DELETEHISTORY + "userID=" + MyApplication.userModel.getUserID()+"&phoneNumber="+phoneNumber+"&historyID="+historyID;
	}
	
	/**
	 * 重新开通(好友未同意时)
	 * @param userID             用户id
	 * @param userPhoneNumber    用户手机号
	 * @param phoneNumber        未同意好友电话号码
	 * @return
	 */
	public static String reopen(String userID,String userPhoneNumber,String phoneNumber) {
		return REOPEN + "userID="+userID+"&userPhoneNumber="+userPhoneNumber+"&phoneNumber="+phoneNumber;
	}
	
	/**
	 * 获取我的消息
	 * @param userID
	 * @return
	 */
	public static String getMessage(String userID){
		return GETMSG +"userID="+userID + "&platform=0";
	}
	
	/**
	 * 添加好友时，文字提示
	 * @param platform 平台（1：android， 2：IOS）
	 * @return
	 */
	public static String getTipMsg(String platform){
		return GETTIPMSG + "platform=" + platform;
	}
}

	