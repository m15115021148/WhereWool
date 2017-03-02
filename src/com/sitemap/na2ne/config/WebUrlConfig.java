package com.sitemap.na2ne.config;

import com.sitemap.na2ne.application.MyApplication;

/**
 * 
 * @ClassName:     WebUrlConfig.java
 * @Description:   ����url���ӿڣ������ļ�
 * @author         chenhao
 * @Date           2015-11-14
 */
 
public class WebUrlConfig {
	private static final String HOST_NAME = WebHostConfig.getHostName();
	private static final String LOGIN = HOST_NAME + "userAction_login?";//��¼
	private static final String GETFRIENDLIST=HOST_NAME+"linkmanAction_getFriendtable?";//��ú����б�
	private static final String GETFRIENDPOSITION=HOST_NAME+"locationAction_getFriendPosition?";//��λ
	private static final String GETMOVINGTRACK=HOST_NAME+"trackAction_exerciseSTrack?";//��ʷ�켣
	private static final String UPDATENAME=HOST_NAME+"userAction_updateName?";//��������
	private static final String REQUESTCODE=HOST_NAME+"userAction_requestCode?";//������֤��
	private static final String REGISTER=HOST_NAME+"userAction_register?";//ע��
	private static final String SYSTEMNOTIFY=HOST_NAME+"userAction_systemNotify?";//ϵͳ����
	private static final String REQUESTREFUND=HOST_NAME+"userAction_requestRefund?";//�����˿�
	private static final String REFUNDRECORDS=HOST_NAME+"userAction_refundRecords?";//�˿��¼
	private static final String PAYMENTRECORDS=HOST_NAME+"userAction_paymentRecords?";//��ֵ��¼
	private static final String UPDATEVERSION=HOST_NAME+"userAction_updateVersion?";//���½ӿ�
	private static final String SHOPRECORD=HOST_NAME+"userAction_shopRecord?";//�Ʒ��˵�
	private static final String LOCATIONRECORD=HOST_NAME+"userAction_locationRecord?";//��λ��¼
	private static final String REQUESTMONEY=HOST_NAME+"userAction_requestMoney?";//�����û������˻����
	private static final String ADDFRIEND=HOST_NAME+"userAction_addFriend?";//���Ӻ���
	private static final String UPDATEFRIENDTYPE=HOST_NAME+"userAction_updateFriendType?";//���ĺ����ײ�
	private static final String FIXPASSWORD=HOST_NAME+"userAction_fixPassword?";//�һ�����
	private static final String GETCODES=HOST_NAME+"userAction_getCodes?";//�һ�����������֤��
	private static final String COMPARECODE=HOST_NAME+"userAction_compareCode?";//�Ա���֤��
	private static final String UPDATEPASSWORD=HOST_NAME+"userAction_updatePassword?";//�޸�����
	private static final String SYSTEMCONFIG=HOST_NAME+"userAction_systemConfig";//λ���ϱ�����
	private static final String UPLOADLOCATION=HOST_NAME+"userAction_uploadLocation";//λ���ϱ�
	private static final String GETPACKAGEINFO=HOST_NAME+"userAction_getPackageInfo?";//����ײ��б�
	private static final String ADDLOCATIONTIMES=HOST_NAME+"userAction_addLocationTimes?";//���Ӷ�λ����
	private static final String SAVEFENCE=HOST_NAME+"userAction_saveFence";//���͵���Χ��
	private static final String SETFENCEEND=HOST_NAME+"userAction_setFenceEnd?";//�رյ���Χ��
	private static final String GETFENCE=HOST_NAME+"userAction_getFence?";//�����ʷ����Χ��
	private static final String SETFENCESTART=HOST_NAME+"userAction_setFenceStart";//�������͵���Χ��
	private static final String SENDWARN=HOST_NAME+"userAction_sendWarn?";//����Χ����������
	private static final String GETFRIENDINFO=HOST_NAME+"userAction_getFriendInfo?";//��ú�����Ϣ
	private static final String DELETEFRIEND=HOST_NAME+"userAction_deleteFriend?";//ɾ������
	private static final String GETFUNCTIONINFO=HOST_NAME+"userAction_getFunctionInfo?";//APP���ܽ���
	private static final String GETAPPINFO=HOST_NAME+"userAction_getAppInfo?";//APP�汾����
	private static final String TOADVICE=HOST_NAME+"userAction_toAdvice";//�û�����(��������)
	private static final String SHAREINFO=HOST_NAME+"userAction_getShareInfo";//����
	private static final String DELETEHISTORY=HOST_NAME+"userAction_deleteHistory?";//ɾ����λ��¼
	private static final String REOPEN=HOST_NAME+"userAction_reopen?";//���¿�ͨ(����δͬ��ʱ)
	private static final String GETMSG = HOST_NAME+"userAction_getMsg?";//�ҵ���Ϣ
	private static final String GETTIPMSG = HOST_NAME+"userAction_getTipMsg?";//���Ӻ���ʱ��������ʾ
	/**
	 * ��½����
	 * 
	 * @param userID
	 * @param password
	 * @return
	 */
	public static String login(String userID, String password) {
		return LOGIN + "username=" + userID + "&password=" + password+ "&source=1";
	}
	
	/**
	 * ��ú����б�
	 * 
	 * @param userID
	 * @return
	 */
	public static String getFriendList(String userID) {
		return GETFRIENDLIST + "userID=" + userID;
	}
	
	/**
	 * ��ú���λ��
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
	 * �˶��켣
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
	 * ���ĺ�������
	 * 
	 * @param friendID
	 * @param friendName
	 * @return
	 */
	public static String updateName(String friendID , String friendName) {
		return UPDATENAME + "friendID=" + friendID  + "&friendName=" + friendName+"&type=1";
	}
	
	/**
	 * ������֤��
	 * @param phoneNumber
	 * @return
	 */
	public static String requestCode(String phoneNumber) {
		return REQUESTCODE + "phoneNumber=" + phoneNumber;
	}
	
	/**
	 * ע��
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
	 * ϵͳ����
	 * @return
	 */
	public static String systemNotify(String notiID) {
		return SYSTEMNOTIFY+ "platform=0"+"&versionCode="+MyApplication.versionName+"&notiID="+notiID;
	}
	
	/**
	 * �����˿�
	 * @param userID
	 * @param reason
	 * @param description
	 * @return
	 */
	public static String requestRefund(String userID,String reason,String description) {
		return REQUESTREFUND + "userID=" + userID+"&reason="+reason+"&description="+description+"&platform=1"+"&versionCode="+MyApplication.versionName;
	}
	
	/**
	 * �˿��¼
	 * @param userID
	 * @param page
	 * @param date
	 * @return
	 */
	public static String refundRecords(String userID,String page,String date) {
		return REFUNDRECORDS + "userID=" + userID+"&page="+page+"&date="+date;
	}
	
	/**
	 * ��ֵ��¼
	 * @param userID
	 * @param page
	 * @param date
	 * @return
	 */
	public static String paymentRecords(String userID,String page,String date) {
		return PAYMENTRECORDS + "userID=" + userID+"&page="+page+"&date="+date;
	}
	
	/**
	 * ���½ӿ�
	 * @param platform
	 * @param city
	 * @return
	 */
	public static String updateVersion(String platform,String city) {
		return UPDATEVERSION + "platform=" + platform+"&city="+city+"&versionCode="+MyApplication.versionName;
	}
	
	/**
	 * �Ʒ��˵�
	 * @param userID
	 * @param page
	 * @param date
	 * @return
	 */
	public static String shopRecord(String userID,String page,String date,String phoneNumber) {
		return SHOPRECORD + "userID=" + userID+"&page="+page+"&date="+date+"&phoneNumber="+phoneNumber;
	}
	
	/**
	 * ��λ��¼
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
	 * �����û������˻����
	 * 
	 * @param userID
	 * @return
	 */
	public static String requestMoney(String userID) {
		return REQUESTMONEY + "userID=" + userID;
	}
	
	/**
	 * ���Ӻ���
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
	 * ���ĺ����ײ�
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
	 * �һ�����
	 * @param phoneNumber
	 * @param newPassword
	 * @return
	 */
	public static String fixPassword(String phoneNumber,String newPassword) {
		return FIXPASSWORD + "phoneNumber=" +phoneNumber+"&newPassword="+newPassword;
	}
	
	/**
	 * �һ�����������֤��
	 * @param phoneNumber
	 * @return
	 */
	public static String getCodes(String phoneNumber) {
		return GETCODES + "phoneNumber=" + phoneNumber;
	}
	
	/**
	 * �Ա���֤��
	 * @param phoneNumber
	 * @param friendID
	 * @return
	 */
	public static String compareCode(String phoneNumber,String code) {
		return COMPARECODE + "phoneNumber=" + phoneNumber+"&code="+code;
	}
	
	/**
	 * �޸�����
	 * @param userID
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public static String updatePassword(String userID,String oldPassword,String newPassword) {
		return UPDATEPASSWORD + "userID=" + userID+"&oldPassword="+oldPassword+"&newPassword="+newPassword;
	}
	
	/**
	 * λ���ϱ�����
	 * @return
	 */
	public static String systemConfig() {
		return SYSTEMCONFIG;
	}
	
	/**
	 * λ���ϱ�
	 * @param userID
	 * @param locationInfo
	 * @return
	 */
	public static String uploadLocation(String userID,String locationInfo) {
//		return UPLOADLOCATION + "userID=" + userID+"&locationInfo="+locationInfo;
		return UPLOADLOCATION;
	}
	
	
	/**
	 * ����ײ�����
	 * @param friendPhone
	 * @param numberType
	 * @return
	 */
	public static String getPackageInfo(String friendPhone,String numberType) {
		return GETPACKAGEINFO + "friendPhone=" + friendPhone+"&numberType="+numberType+"&userID="+MyApplication.userModel.getUserID();
	}
	
	
	/**
	 * ���Ӷ�λ����
	 * @param userID
	 * @param friendID
	 * @param packageID
	 * @return
	 */
	public static String addLocatonTimes(String userID,String friendID,String packageID) {
		return ADDLOCATIONTIMES + "userID=" + userID+"&friendID="+friendID+"&packageID="+packageID;
	}
	
	/**
	 * ���͵���Χ��
	 * @param friendID
	 * @param fenceInfo
	 * @return
	 */
	public static String saveFence(String friendID,String fenceInfo) {
		return SAVEFENCE;
//		return SAVEFENCE + "friendID=" + friendID+"&fenceInfo="+fenceInfo;
	}
	
	/**
	 * �رյ���Χ��
	 * @param phoneNumber
	 * @return
	 */
	public static String setFenceEnd(String phoneNumber) {
		return SETFENCEEND + "phoneNumber=" + phoneNumber;
	}
	
	/**
	 * �����ʷ����Χ��
	 * @param friendID
	 * @return
	 */
	public static String getFence(String friendID) {
		return GETFENCE + "friendID=" + friendID;
	}
	
	
	/**
	 * ���͵���Χ��
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
	 * ����Χ����������
	 * @param senderUserID
	 * @param warnTime
	 * @return
	 */
	public static String sendWarn(String senderUserID,String warnTime) {
		return SENDWARN + "senderUserID=" + senderUserID+"&warnTime="+warnTime;
	}
	
	
	/**
	 * ��ú����ײ���Ϣ
	 * 
	 * @param friendID
	 * @return
	 */
	public static String getFriendInfo(String friendID) {
		return GETFRIENDINFO + "friendID=" + friendID;
	}
	
	
	/**
	 * ɾ������
	 * @param userID
	 * @param friendID
	 * @return
	 */
	public static String deleteFriend(String userID,String friendID) {
		return DELETEFRIEND + "userID=" + userID+"&friendID="+friendID;
	}
	
	/**
	 * APP���ܽ���
	 * @param versionCode
	 * @return
	 */
	public static String getFunctionInfo(String versionCode) {
		return GETFUNCTIONINFO + "platform=1"+"&versionCode="+versionCode;
	}
	
	/**
	 * APP���ܽ���
	 * @param versionCode
	 * @return
	 */
	public static String getAppInfo(String versionCode) {
		return GETAPPINFO + "platform=1"+"&versionCode="+versionCode;
	}
	
	
	/**
	 * �û�����(��������)
	 * @return
	 */
	public static String toAdvice() {
		return TOADVICE ;
	}
	
	
	/**
	 * ��ȡ������Ϣ
	 * @return
	 */
	public static String getShareinfo() {
		return SHAREINFO;
	}
	
	/**
	 * ɾ����λ��¼
	 * @param userID
	 * @param phoneNumber
	 * @param historyID
	 * @return
	 */
	public static String deleteHistory(String phoneNumber,String historyID) {
		return DELETEHISTORY + "userID=" + MyApplication.userModel.getUserID()+"&phoneNumber="+phoneNumber+"&historyID="+historyID;
	}
	
	/**
	 * ���¿�ͨ(����δͬ��ʱ)
	 * @param userID             �û�id
	 * @param userPhoneNumber    �û��ֻ���
	 * @param phoneNumber        δͬ����ѵ绰����
	 * @return
	 */
	public static String reopen(String userID,String userPhoneNumber,String phoneNumber) {
		return REOPEN + "userID="+userID+"&userPhoneNumber="+userPhoneNumber+"&phoneNumber="+phoneNumber;
	}
	
	/**
	 * ��ȡ�ҵ���Ϣ
	 * @param userID
	 * @return
	 */
	public static String getMessage(String userID){
		return GETMSG +"userID="+userID + "&platform=0";
	}
	
	/**
	 * ���Ӻ���ʱ��������ʾ
	 * @param platform ƽ̨��1��android�� 2��IOS��
	 * @return
	 */
	public static String getTipMsg(String platform){
		return GETTIPMSG + "platform=" + platform;
	}
}

	