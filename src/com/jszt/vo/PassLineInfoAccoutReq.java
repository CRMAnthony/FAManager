/*
 * 文 件 名:  PassLineInfoReq.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月20日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 通行线路信息请求类 <功能详细描述>
 * 
 */
public class PassLineInfoAccoutReq
{
	/**
	 * 用户账号
	 */
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
