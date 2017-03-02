package com.sitemap.na2ne.models;

/**
 * com.sitemap.na2ne.models.LocationDetailedModel
 * @author zhang
 * 定位记录
 * create at 2015年12月29日 下午2:57:04
 */
public class LocationDetailedModel {
	private String friendName;//好友名称
	private String friendPhone;//好友电话
	private String time;//定位时间
	private int status;//定位状态 （0：成功，1：失败）
	private String location;//定位地址
	private String historyID;//    历史记录ID
	
	
	public String getHistoryID() {
		return historyID;
	}
	public void setHistoryID(String historyID) {
		this.historyID = historyID;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
