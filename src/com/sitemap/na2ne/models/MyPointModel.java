package com.sitemap.na2ne.models;


import android.os.Parcel;
import android.os.Parcelable;
/**
 * 
 * com.sitemap.na2ne.models.MyPointModel
 * @author zhang
 * create at 2016年2月26日 上午9:38:20
 * 地图上取点的实体类
 */
public class MyPointModel implements Parcelable{
	private String lat;//纬度
	private String lng;//经度
	
	public MyPointModel() {
 	}
	public MyPointModel(String lat,String lng){
		this.lat=lat;
		this.lng=lng;
	}
	
	public static final Parcelable.Creator<MyPointModel> CREATOR  = new Creator<MyPointModel>(){

		@Override
		public MyPointModel createFromParcel(Parcel source) {
 			MyPointModel mp=new MyPointModel();
			mp.lat=source.readString();
			mp.lng=source.readString();
			return mp;
		}

		@Override
		public MyPointModel[] newArray(int size) {
 			return new MyPointModel[size];
		}
		
	};
	
	public final String getLat() {
		return lat;
	}
	public final void setLat(String lat) {
		this.lat = lat;
	}
	public final String getLng() {
		return lng;
	}
	public final void setLng(String lng) {
		this.lng = lng;
	}
	@Override
	public int describeContents() {
 		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
 		dest.writeString(lat);
		dest.writeString(lng);
	}
}