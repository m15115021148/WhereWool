package com.sitemap.na2ne.models;

/** 
 * com.sitemap.na2ne.models
 * @author chenmeng
 * @Description ��ȡ�ҵ���Ϣ ���ص�ʵ����
 * @date create at  2016��6��27�� ����3:33:05
 */
public class MessageModel {
	private String title;//       ����
	private String brief;//       ���
	private String content;//   ���ݣ�url��
	private String img;//       ͼƬ
	private String isRead;//    �Ƿ��Ѷ���1���ǣ�2��
	private String time;//ʱ��
	
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
}
