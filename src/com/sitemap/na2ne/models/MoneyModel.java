/**
 * 
 */
package com.sitemap.na2ne.models;

/**
 * 个人账户
 * @author zhang
 * create at 2015年12月25日 下午4:37:25
 */
public class MoneyModel {
	private String balance;//账户余额
	private String redBalance;//红包账户余额
//	private String expenseMoney;//消费金额
//	private String  f;//充值余额
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {   
		this.balance = balance;
	}
	public String getRedBalance() {
		return redBalance;
	}
	public void setRedBalance(String redBalance) {
		this.redBalance = redBalance;
	}
//	public String getExpenseMoney() {
//		return expenseMoney;
//	}
//	public void setExpenseMoney(String expenseMoney) {
//		this.expenseMoney = expenseMoney;
//	}
//	public String getPayMoney() {
//		return payMoney;
//	}
//	public void setPayMoney(String payMoney) {
//		this.payMoney = payMoney;
//	}
	
}
