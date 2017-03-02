package com.sitemap.na2ne.models;

/**
 * com.sitemap.na2ne.models.LocationSetModel
 * @author zhang
 * 位置上报设置实体类
 * create at 2016年2月18日 下午2:25:13
 */
public class LocationSetModel {
	private String locationSpace;  //定位间隔（单位：分钟，默认５分钟）
	private String uploadSpace;//   上报间隔（单位：分钟，默认３０分钟）
	private String locationStartTime;//  每天定位开始时间（默认8:00）
	private String locationEndTime;//   每天定位结束时间 （默认22:30）
	public String getLocationSpace() {
		return locationSpace;
	}
	public void setLocationSpace(String locationSpace) {
		this.locationSpace = locationSpace;
	}
	public String getUploadSpace() {
		return uploadSpace;
	}
	public void setUploadSpace(String uploadSpace) {
		this.uploadSpace = uploadSpace;
	}
	public String getLocationStartTime() {
		return locationStartTime;
	}
	public void setLocationStartTime(String locationStartTime) {
		this.locationStartTime = locationStartTime;
	}
	public String getLocationEndTime() {
		return locationEndTime;
	}
	public void setLocationEndTime(String locationEndTime) {
		this.locationEndTime = locationEndTime;
	}
	
}
