package com.sitemap.na2ne.models;

/**
 * com.sitemap.na2ne.models.UserFriendModel
 * ������Ϣʵ����
 * @author zhang
 * create at 2015��12��8�� ����4:07:41
 */
public class UserFriendModel {
	private String friendID;//����id
	private String friendName;//��������
	private String friendPhone;//�����ֻ���
	private String friendType;//0����APP�û���1��android<APP�û�>��2 ��ios<APP�û�>
	private String numberType;// �ֻ������ͣ�1���� 2�ƶ� 3��ͨ��
	
//	private String open;//       ��ͨ״̬��0δ��ͨ 1�ѿ�ͨ��
//	private String unsubscribe;//   �˶�״̬��0δ�˶� 1���˶���
	private String surplusTimes;// �ײ�����Ϊ�����շ�ʱ��ʣ�ඨλ������Ĭ��Ϊ0��
//	private String surplusDays;//   �˶�֮��ʣ���ʹ������
	private String isAgree;//     �����Ƿ���ͬ�⣨0��δͬ�⣬1����ͬ�⣩
	private String packageName;// �ײ�����
	private String packageID;//  �ײ�id
	private String packageType;//�ײ�����  4�����
	
	
	
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageID() {
		return packageID;
	}
	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}
	public String getSurplusTimes() {
		return surplusTimes;
	}
	public void setSurplusTimes(String surplusTimes) {
		this.surplusTimes = surplusTimes;
	}
	public String getNumberType() {
		return numberType;
	}
	public void setNumberType(String numberType) {
		this.numberType = numberType;
	}
	public String getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}
	public String getFriendID() {
		return friendID;
	}
	public void setFriendID(String friendID) {
		this.friendID = friendID;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getFriendPhone() {
		return friendPhone;
	}
	public void setFriendPhone(String friendPhone) {
		this.friendPhone = friendPhone;
	}
	public String getFriendType() {
		return friendType;
	}
	public void setFriendType(String friendType) {
		this.friendType = friendType;
	}
	
}