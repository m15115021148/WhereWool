package com.sitemap.na2ne.models;

/**
 * com.sitemap.na2ne.models.UserModel
 * �û�ʵ����
 * @author zhang
 * create at 2015��12��8�� ����11:32:45
 */
public class UserModel {
	private String balance;
	private String phoneNumber;
	private String registerTime;
	private int status;
	private String userID;
	private String realName;
	private int tryCount;//ʹ�ô���	
	private int msgID = 0;//��Ϣ��������һ��ID
	
	
	public int getMsgID() {
		return msgID;
	}
	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}
	public int getTryCount() {
		return tryCount;
	}
	public void setTryCount(int tryCount) {
		this.tryCount = tryCount;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}