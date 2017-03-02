package com.sitemap.na2ne.models;

/**
 * com.sitemap.na2ne.models.PackageModel
 * @author zhang   
 * 套餐类型
 * create at 2016年2月29日 下午3:18:28
 */
public class PackageModel {
	private String packageID;//     套餐id
	private String packageName;//    套餐名称
	private String packagePrice;//   套餐价格
	private String packageType;//     套餐类型（0短信 1电信 2移动 3联通 4免费）
	private String isTry;//是否是试用 0不试用，1试用
	
	public String getIsTry() {
		return isTry;
	}
	public void setIsTry(String isTry) {
		this.isTry = isTry;
	}
	public String getPackageID() {
		return packageID;
	}
	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackagePrice() {
		return packagePrice;
	}
	public void setPackagePrice(String packagePrice) {
		this.packagePrice = packagePrice;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
}
