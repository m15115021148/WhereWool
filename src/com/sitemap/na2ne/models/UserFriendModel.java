package com.sitemap.na2ne.models;

/**
 * com.sitemap.na2ne.models.UserFriendModel
 * 好友信息实体类
 * @author zhang
 * create at 2015年12月8日 下午4:07:41
 */
public class UserFriendModel {
	private String friendID;//好友id
	private String friendName;//好友姓名
	private String friendPhone;//好友手机号
	private String friendType;//0：非APP用户，1：android<APP用户>，2 ：ios<APP用户>
	private String numberType;// 手机号类型（1电信 2移动 3联通）
	
//	private String open;//       开通状态（0未开通 1已开通）
//	private String unsubscribe;//   退订状态（0未退订 1已退订）
	private String surplusTimes;// 套餐类型为按次收费时，剩余定位次数（默认为0）
//	private String surplusDays;//   退订之后剩余可使用天数
	private String isAgree;//     好友是否已同意（0：未同意，1：已同意）
	private String packageName;// 套餐名称
	private String packageID;//  套餐id
	private String packageType;//套餐类型  4是免费
	
	
	
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
