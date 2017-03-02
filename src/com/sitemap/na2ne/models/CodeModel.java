package com.sitemap.na2ne.models;

/**
 * 验证码请求类
 * @author zhang
 * create at 2015年12月25日 下午3:05:52
 */
public class CodeModel {
	private int status;//请求状态
	private String errorMessage;//错误信息
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
