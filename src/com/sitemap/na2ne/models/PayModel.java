package com.sitemap.na2ne.models;

/**
 * com.sitemap.na2ne.models.PayModel
 * @author zhang
 * ��ֵ��¼
 * create at 2015��12��29�� ����9:23:36
 */
public class PayModel {
	private String orderNumber;//������
	private String status;//����״̬
	private double payMoney;//֧�����
	private String payTime;//��ֵʱ��
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
}
