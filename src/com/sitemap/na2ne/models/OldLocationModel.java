package com.sitemap.na2ne.models;

/**
 * com.sitemap.na2ne.models.OldLocationModel ��ʷ�켣ʵ����
 * 
 * @author zhang create at 2015��12��10�� ����11:53:38
 */
public class OldLocationModel {
	private String address;
	private String lat;
	private String lng;
	private String time;
	private String errorMsg;
	
	

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

}
