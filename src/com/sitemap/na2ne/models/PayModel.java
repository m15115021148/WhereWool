package com.sitemap.na2ne.models;

/**
 * com.sitemap.na2ne.models.PayModel
 * @author zhang
 * 充值记录
 * create at 2015年12月29日 上午9:23:36
 */
public class PayModel {
	private String orderNumber;//订单号
	private String status;//交易状态
	private double payMoney;//支付金额
	private String payTime;//充值时间
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
