package com.sitemap.na2ne.models;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 *
 */
/** 
 * com.sitemap.na2ne.models
 * @author chenmeng
 * @Description 添加好友时，文字提示
 * @date create at  2016年7月8日 上午11:12:35
 */
public class TipMsgModel implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	private List<TipLableModel> msg1;
	private List<TipLableModel> msg2;
	private List<TipLableModel> msg3;
	private List<TipLableModel> msg4;
	
	public List<TipLableModel> getMsg1() {
		return msg1;
	}
	public void setMsg1(List<TipLableModel> msg1) {
		this.msg1 = msg1;
	}
	public List<TipLableModel> getMsg2() {
		return msg2;
	}
	public void setMsg2(List<TipLableModel> msg2) {
		this.msg2 = msg2;
	}
	public List<TipLableModel> getMsg3() {
		return msg3;
	}
	public void setMsg3(List<TipLableModel> msg3) {
		this.msg3 = msg3;
	}
	public List<TipLableModel> getMsg4() {
		return msg4;
	}
	public void setMsg4(List<TipLableModel> msg4) {
		this.msg4 = msg4;
	}
	
}
