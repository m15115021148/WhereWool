package com.sitemap.na2ne.models;

/**
 * com.sitemap.na2ne.models.BillModel
 * @author zhang
 * ����
 * create at 2015��12��29�� ����10:45:00
 */
public class BillModel {
	private String friendName;//���Ѷ�������
	private String friendPhone;//���Ѷ���绰
	private String money;//���ѽ��
	private String time;//����ʱ��
	private String type;//��������  ��0�����ܼƷѣ�1�����¼Ʒѣ�2������Ʒѣ�3�����ţ�
	private int friendType;//��0����APP�û���1��android<APP�û�>��2 ��ios<APP�û�>��
	private String process;//     ҵ��״̬��0���ѿۿ1�������У�2�����˿
	
	
	
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public int getFriendType() {
		return friendType;
	}
	public void setFriendType(int friendType) {
		this.friendType = friendType;
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
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
