package com.sitemap.na2ne.models;

/**
 * com.sitemap.na2ne.models.SystemInfo
 * @author zhang
 * create at 2016年3月24日 下午1:15:22
 */
public class SystemInfo {
	private String notiTitile;//     公告标题
	private String notiUrl;//       外联H5网页
	private String isOpen;//       是否打开（0：打开，1：关闭）
	private String smallImg;//     小图标
	private String brief;//        简介
	private String notiID;//       id
	
	
	public String getSmallImg() {
		return smallImg;
	}
	public void setSmallImg(String smallImg) {
		this.smallImg = smallImg;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getNotiID() {
		return notiID;
	}
	public void setNotiID(String notiID) {
		this.notiID = notiID;
	}
	public String getNotiTitile() {
		return notiTitile;
	}
	public void setNotiTitile(String notiTitile) {
		this.notiTitile = notiTitile;
	}
	public String getNotiUrl() {
		return notiUrl;
	}
	public void setNotiUrl(String notiUrl) {
		this.notiUrl = notiUrl;
	}
	
}
