/*
 * 文 件 名:  DimCardUserInfo.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2016年1月7日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 短信数量 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2016年1月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimNoticeCount extends EhsObject implements Serializable
{

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -528118564424039570L;

	/**
	 * 日期
	 */
	private int dateKey;

	/**
	 * 短信数量
	 */
	private int noticeCount;

	public int getDateKey() {
		return dateKey;
	}

	public void setDateKey(int dateKey) {
		this.dateKey = dateKey;
	}

	public int getNoticeCount() {
		return noticeCount;
	}

	public void setNoticeCount(int noticeCount) {
		this.noticeCount = noticeCount;
	}

	

}
